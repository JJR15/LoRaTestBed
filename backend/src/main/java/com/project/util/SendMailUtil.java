package com.project.util;

import java.util.Calendar;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMailUtil {
    @SuppressWarnings("static-access")
    public static void sendMessage(String smtpHost, String from,
                                   String fromUserPassword, String to, String subject,
                                   String messageText, String messageType) throws MessagingException {
        // 第一步：配置javax.mail.Session对象
        final String smtpPort = "465";
        //System.out.println("为" + smtpHost + "配置mail session对象");
        Properties props = new Properties();
        props.setProperty("mail.smtp.port", smtpPort);
        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.socketFactory.port", smtpPort);
        props.put("mail.smtp.host", smtpHost);
        //props.put("mail.smtp.starttls.enable", "true");
        //props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        //props.put("mail.debug", "true");
        Session mailSession = Session.getInstance(props, new MyAuthenticator(from, fromUserPassword));

        InternetAddress fromAddress = new InternetAddress(from);
        InternetAddress toAddress = new InternetAddress(to);

        MimeMessage message = new MimeMessage(mailSession);

        message.setFrom(fromAddress);
        message.addRecipient(RecipientType.TO, toAddress);

        message.setSentDate(Calendar.getInstance().getTime());
        message.setSubject(subject);
        message.setContent(messageText, messageType);

        Transport transport = mailSession.getTransport("smtp");
        transport.connect(smtpHost, "thu_dandelion@163.com", fromUserPassword);
        transport.send(message, message.getRecipients(RecipientType.TO));
        //System.out.println("message yes");
        transport.close();
    }

    /*
     * use example as for sending mails
     */
    public static void main(String[] args) {
        try {
            SendMailUtil.sendMessage("smtp.163.com", "thu_dandelion@163.com",
                    "THUtestbed2017", "thu_dandelion@163.com", "哈哈哈",
                    "沃日<br>" + "哈哈哈！",
                    "text/html;charset=UTF-8");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}

class MyAuthenticator extends Authenticator {
    String username = "";
    String password = "";

    public MyAuthenticator(String username, String password) {
        this.username = username;
        this.password = password;
    }

    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(username, password);
    }
}  
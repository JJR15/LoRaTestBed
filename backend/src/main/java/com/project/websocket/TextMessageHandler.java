package com.project.websocket;

import com.alibaba.fastjson.JSONObject;
import com.project.model.Timeorder;
import com.project.service.JuraService;
import com.project.service.NodeOrderService;
import com.project.service.SystemInfoService;
import com.project.service.UserService;
import com.project.util.CONSTANT;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.*;

@Component
public class TextMessageHandler extends AbstractWebSocketHandler {

    private static Map<WebSocketSession, File> file_map = new HashMap<WebSocketSession, File>();

    @Autowired
    private SystemInfoService systemInfoService;
    @Autowired
    private NodeOrderService nodeOrderService;
    @Autowired
    private JuraService juraService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        /*
         * 链接成功后会触发此方法，可在此处对离线消息什么的进行处理
         */
        //1M
        session.setBinaryMessageSizeLimit(1048576);
        String username = (String) session.getAttributes().get(CONSTANT.DEFAULT_WEBSOCKET_USERNAME);
        //System.out.println(username + " connect websocket success ...");
        /*
        String filePath = "F:/JuRa/erase.bin";
        byte[] buffer = null;
        File file = new File(filePath);

        FileInputStream fis = null;
        ByteArrayOutputStream bos = null;

        try {
            fis = new FileInputStream(file);
            bos = new ByteArrayOutputStream();

            byte[] b = new byte[1024];

            int n;

            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }

            buffer = bos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != bos) {
                    bos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (null != fis) {
                        fis.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        session.sendMessage(new BinaryMessage(buffer));*/
        //session.sendMessage(new TextMessage("log file F:\\音乐\\Beyond - 大地.mp3"));
    }


    @Override
    protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws Exception {
        if(!file_map.containsKey(session)) return;

        byte[] file_data = message.getPayload().array();
        File file = file_map.get(session);

        OutputStream output = null;
        BufferedOutputStream bufferedOutput = null;

        try{
            output = new FileOutputStream(file, true);

            bufferedOutput = new BufferedOutputStream(output);

            bufferedOutput.write(file_data);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally{
            if(null!=bufferedOutput){
                try {
                    bufferedOutput.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if(null != output){
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        super.handleBinaryMessage(session, message);
    }


    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception{
        String tmp = message.getPayload();
        //System.out.println("message -> " + tmp);

        String username = (String)session.getAttributes().get(CONSTANT.DEFAULT_WEBSOCKET_USERNAME);


        //发送文件
        if(tmp.startsWith("file send start ")){
            String filename = tmp.substring(16);
            String path = CONSTANT.FILE_PATH + "\\usr\\"  + username;
            File file = new File(path);
            if(!file.exists()) file.mkdirs();
            file = new File(path +"\\"+ filename);
            while (file.exists()){
                String fp = file.getPath();
                int split = fp.lastIndexOf('.');
                String new_name = fp.substring(0,split)+"_new" + "." + fp.substring(split + 1);
                file = new File(new_name);
            }

            file_map.put(session, file);
        }
        else if(tmp.equals("file send end")){
            session.sendMessage(new TextMessage("file new name " + file_map.get(session).getName()));
            file_map.remove(session);
        }
        //要求获取返回信息
        else if(tmp.equals("nodes message")){
            juraService.addNodeMessageSession(session);
        }
        else if(tmp.startsWith("nodes message end")){
            juraService.removeNodeMessageSession(session);
        }
        //要求获取实时log
        /*else if(tmp.startsWith("node log ")){
            String node = tmp.substring(9);
            int n = Integer.parseInt(node);
            if(nodeOrderService.checkNodeAccess(n,username)){
                juraService.addNodeLogSession(n,session);
            }
            else {
                session.sendMessage(new TextMessage("don't have access to node " + node));
            }
        }*/
        //要求获取实时输出data
        else if(tmp.startsWith("node data ")){
            String node = tmp.substring(10);
            int n = Integer.parseInt(node);
            Timeorder nowOrder = nodeOrderService.getNowOrder();
            if(nowOrder == null || !nowOrder.getUsername().equals(username)){
                juraService.addNodeDataSession(n,session);
            }
            else {
                session.sendMessage(new TextMessage("don't have access to node " + node));
            }
        }
        /*else if(tmp.startsWith("start node ")){
            List<Integer> ids = StringToNumArray(tmp.substring(11));
        }
        else if(tmp.startsWith("stop node ")){
            List<Integer> ids = StringToNumArray(tmp.substring(10));
        }
        else if(tmp.startsWith("restart node ")){
            List<Integer> ids = StringToNumArray(tmp.substring(13));
        }
        else if(tmp.startsWith("log file ")){
            Integer id = Integer.parseInt(tmp.substring(9));
        }
        else if(tmp.startsWith("data file ")){
            Integer id = Integer.parseInt(tmp.substring(10));
        }*/
        super.handleTextMessage(session, message);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        if (session.isOpen()) {
            session.close();
        }
        //System.out.println("websocket connection error......");
        System.err.println(exception.getMessage());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        //System.out.println("websocket connection closed......");
        juraService.removeSession(session);
    }

    private List<Integer> StringToNumArray(String str) {
        List<Integer> num_array = new ArrayList<>();
        String[] s_ids = str.split(" ");
        for(String s_id:s_ids){
            num_array.add(Integer.parseInt(s_id));
        }
        return num_array;
    }
}

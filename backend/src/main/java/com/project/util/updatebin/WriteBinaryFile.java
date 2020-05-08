package com.project.util.updatebin;

import java.io.*;
import java.nio.ByteBuffer;

/**
 * Created by DongBaishun on 2017/7/31.
 */
public class WriteBinaryFile {
    private DataInputStream dis = null;
    private DataOutputStream dos = null;
    private String s_FilePath = "F:\\NewLoRaMote.bin";
    private byte[] m_datapadding = {0x00}; //填充空白，以补足字节位数.

    public WriteBinaryFile(String s_FilePath) {
        this.s_FilePath = s_FilePath;
        init();
    }

    private void init() {
        try {
            File file = new File(s_FilePath);
            if (!new File(s_FilePath).exists()) {
                new File(s_FilePath).createNewFile();
            }
            dos = new DataOutputStream(new FileOutputStream(file));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void writeBinaryStream(String str) {
        try {
            byte[] content = hexStringToBytes(str);
            if (dos != null) {
                for (byte con : content) {
                    dos.writeByte(con);
                }
                //dos.write(m_datapadding); //?补全字节?
                dos.flush();
                dos.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() >> 1;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i << 1;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }
}

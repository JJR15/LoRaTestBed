package com.project.util.updatebin;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;

/**
 * Created by DongBaishun on 2017/7/30.
 */
public class ReadBinaryFile {
    private DataInputStream dis = null;
    private String s_FilePath = "F:\\LoRaMote0.bin";

    public ReadBinaryFile(String filePath) {
        this.s_FilePath = filePath;
        init();
    }

    private void init() {
        try {
            File file = new File(s_FilePath);
            dis = new DataInputStream(new FileInputStream(file));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String readBinaryStream() {
        StringBuffer sb = new StringBuffer();
        try {
            if (dis != null) {
                int isLine = 0;
                while (dis.available() > 0) {
                    if (isLine++ == 8) {
                        //System.out.println();
                        isLine = 0;
                        continue;
                    }
                    byte tmp = dis.readByte();
                    //System.out.print(Hex2String(tmp));
                    sb.append(Hex2String(tmp));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return sb.toString();
        }
    }

    private String Hex2String(byte b) {
        String a = "";
        String hex = Integer.toHexString(b & 0xFF);
        if (hex.length() == 1) {
            hex = '0' + hex;
        }
        a = a + hex;
        return a;
    }
}

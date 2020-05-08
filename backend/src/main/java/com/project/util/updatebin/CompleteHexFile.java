package com.project.util.updatebin;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Created by DongBaishun on 2017/7/30.
 */
public class CompleteHexFile {
    private byte[] content = {0x00}; //填充空白，以补足字节位数.
    private String filepath = "";
    private int byteCount = 0;

    public CompleteHexFile(String filepath, int byteCount) {
        this.filepath = filepath;
        this.byteCount = byteCount;
    }

    public String complete() {
        String res = "";
        try {
            // 打开一个随机访问文件流，按读写方式
            RandomAccessFile randomFile = new RandomAccessFile(filepath, "rw");
            // 文件长度，字节数
            long fileLength = randomFile.length();
            if (fileLength == 0) {
                res = "文件为空";
            } else if (fileLength > 62e3) {
                res = "文件大于62KB";
            } else {
                if (byteCount < 0) {
                    res = "进制有误";
                } else {
                    if ((fileLength % byteCount) != 0) {
                        long tmp = byteCount - (fileLength % byteCount);
                        // 将写文件指针移到文件尾。
                        randomFile.seek(fileLength);
                        for (int i = 0; i < tmp; i++) {
                            randomFile.write(content);
                        }
                        randomFile.close();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    public static void main(String[] argv) {
        String filepath = "F:\\testBin\\LoRaMote.bin";
        CompleteHexFile completeHexFile = new CompleteHexFile(filepath, 16);
        completeHexFile.complete();
        completeHexFile.complete();
    }
}

package com.project.util.updatebin;

/**
 * Created by DongBaishun on 2017/8/2.
 */

import com.project.util.updatebin.CompleteHexFile;
import com.project.util.updatebin.ReadBinaryFile;
import com.project.util.updatebin.Sunday;
import com.project.util.updatebin.WriteBinaryFile;

import java.util.ArrayList;

public class updateMain {
    public static String updateBin(String filePath, String pattern) {
        //TODO 判别文件合法性  62KB 大小  非法名称
        String res = "Error: file cannot file";
        ReadBinaryFile readBinaryFile = new ReadBinaryFile(filePath);
        String fileString = "";
        fileString = readBinaryFile.readBinaryStream();
        //System.out.println();
        //System.out.println("file String length:" + fileString.length());
        if (fileString != null && !fileString.equals("")) {
            Sunday sunday = new Sunday();
            ArrayList<Integer> posList = new ArrayList<>(2);
            int countOfSunday = sunday.Sunday(fileString, pattern, posList);
            if (countOfSunday != 1 ) {
                res = "Error: bin file is invalid";
                /* Error: none or one more pattern"
                        + "fileString:" + fileString
                        + ";count:" + countOfSunday
                        + ";filePath" + filePath*/
            } else {
                int start = 0;
                start = posList.get(0);
                return String.valueOf(start);
            }
        }
        return res;
    }
}

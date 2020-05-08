package com.project.util;

import com.project.model.TimeorderExample;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DatePeriod {
/*    public static void generatePeriods() {
        //30分钟一次1天有48次
        int Ahoursmin = 24;
        //开始时间为0秒，第一次循环为900秒
        int AecondValueZero = 0;
        //开始时间为-900秒，第一次循环为0秒
        int AecondValueNineHundred = -1800;

        //循环时间为00：00~23：30
        for (int i = 0; i < Ahoursmin; i++) {
            Map map = new HashMap();
            //每次循环之前加15分钟
            AecondValueZero = AecondValueZero+1800;
            //每次循环之前加15分钟
            AecondValueNineHundred = AecondValueNineHundred+1800;
            //初始时间都为00：00
            String ATimeInitialFront = "00:00";
            String ATimeInitialAfter = "00:00";

            //判断初始秒树是否为0
            if(AecondValueZero != 0){
                //换算成小时
                int AHourst = (Integer.valueOf(AecondValueZero) / 3600);
                //换算成分钟
                int Aminute = (Integer.valueOf(AecondValueZero) % 3600 / 60);
                //换算成秒
                int Asecond = ((Integer.valueOf(AecondValueZero) % 3600 % 60 / 2));
                //把生成的时间转化成字符串
                String hourst = String.valueOf(AHourst);
                String minute = String.valueOf(Aminute);
                String second = String.valueOf(Asecond);
                //如果小时的长度等于1个，在其前面加个0
                if(hourst.length() == 1){
                    hourst = "0"+hourst;
                }
                //如果小时的到达24点让其初始化为00
                if(hourst.equals("24")){
                    hourst = "00";
                }
                //如果分钟的长度等于1个，在其前面加个0
                if(minute.length() == 1){
                    minute = "0"+minute;
                }
                //如果second的长度等于1个，在其前面加个0
                if(second.length() == 1){
                    second = "0"+second;
                }
                //拼接小时和分钟
                ATimeInitialAfter = hourst + ":" + minute + ":" + second;
            }
            //循环时间为00：30~00：00
            if(AecondValueNineHundred != 0){
                //换算成小时
                int AHourst = (Integer.valueOf(AecondValueNineHundred) / 3600);
                //换算成分钟
                int Aminute = (Integer.valueOf(AecondValueNineHundred) % 3600 / 60);
                //换算成秒
                int Asecond = ((Integer.valueOf(AecondValueNineHundred) % 3600 % 60 / 2));
                //把生成的时间转化成字符串
                String hourst = String.valueOf(AHourst);
                String minute = String.valueOf(Aminute);
                String second = String.valueOf(Asecond);
                //如果小时的长度等于1个，在其前面加个0
                if(hourst.length() == 1){
                    hourst = "0"+hourst;
                }
                //如果小时的到达24点让其初始化为00
                if(hourst.equals("24")){
                    hourst = "00";
                }
                //如果分钟的长度等于1个，在其前面加个0
                if(minute.length() == 1){
                    minute = "0"+minute;
                }
                //如果second的长度等于1个，在其前面加个0
                if(second.length() == 1){
                    second = "0"+second;
                }
                //拼接小时和分钟
                ATimeInitialFront = hourst + ":" + minute + ":" + second;
            }
            //将时间和所有字段放入map中初始为0
            map.put("section", ATimeInitialFront+"~"+ATimeInitialAfter);
            System.out.println(map);
        }
    }*/

    public static Map<Date, Date> generateSection() {
        Date now = new Date();
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.setTime(now);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.add(Calendar.HOUR,1);
        Date dateFront = cal.getTime();
        cal.add(Calendar.DATE,10);
        Date dateEnd = cal.getTime();
        Date iter = dateFront;
        Map<Date, Date> map = new HashMap<>();
        while (iter.before(dateEnd)) {
            Date key = iter;
            cal.setTime(iter);
            cal.add(Calendar.HOUR,1);
            iter = cal.getTime();
            map.put(key, iter);
        }
        System.out.println(map.size());
        return map;
    }

    public static void main(String[] args) {
        generateSection();
    }
}

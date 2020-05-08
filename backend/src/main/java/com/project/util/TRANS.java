package com.project.util;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by DongBaishun on 2017/7/26.
 */
public class TRANS {
    public static int String2Int(String no_id) {
        int res;
        String[] tmp = no_id.split("_");
        int tmp0 = Integer.parseInt(tmp[0]);
        int tmp1 = Integer.parseInt(tmp[1]);
        res = (tmp0 - 1) * 10 + tmp1;
        return res;
    }

    public static String Int2String(int id) {
        String res = "";
        int tmp0 = 0;
        int tmp1 = 0;
        if (id % 10 != 0) {
            tmp0 = id / 10 + 1;
            tmp1 = id % 10;
        } else {
            tmp0 = id / 10;
            tmp1 = 10;
        }
        res = tmp0 + "_" + tmp1;
        return res;
    }

    // 将时间戳转为字符串
    public static String TS2Time(String cc_time) {
        String re_StrTime = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");
        long lcc_time = Long.valueOf(cc_time);
        re_StrTime = sdf.format(new Date(lcc_time * 1000L));
        return re_StrTime;
    }

    /**
     * Java将Unix时间戳转换成指定格式日期字符串
     *
     * @param timestampString 时间戳 如："1473048265";
     * @param formats         要格式化的格式 默认："yyyy-MM-dd HH:mm:ss";
     * @return 返回结果 如："2016-09-05 16:06:42";
     */
    public static String TimeStamp2Date(String timestampString, String formats) {
        Long timestamp = Long.parseLong(timestampString) * 1000;
        String date = new SimpleDateFormat(formats, Locale.CHINA).format(new Date(timestamp));
        return date;
    }

    /**
     * 日期格式字符串转换成时间戳
     *
     * @param dateStr 字符串日期
     * @param format  如：yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String Date2TimeStamp(String dateStr, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return String.valueOf(sdf.parse(dateStr).getTime() / 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}

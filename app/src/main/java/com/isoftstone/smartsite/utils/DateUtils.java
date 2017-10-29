package com.isoftstone.smartsite.utils;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by zhangyinfu on 2017/10/22.
 */

public class DateUtils {

    public static String checkDataTime(String dataTime, boolean isBeginValue) {
        StringBuilder stringBuilder = new StringBuilder();
        Log.d("zyf", "===== checkDataTime =====" + dataTime.split(" ").length + "");
        String[] strs = dataTime.split(" ");
        if(strs.length > 2) {
            stringBuilder.append(strs[0].trim());
            stringBuilder.append(" ");
            stringBuilder.append(strs[1]);
        } else if (strs.length <= 1) {
            stringBuilder.append(strs[0].trim());
            stringBuilder.append(isBeginValue ? " 00:00:00" : " 23:59:59");
        } else {
            stringBuilder.append(dataTime);
        }
        Log.d("zyf", "===== stringBuilder.toString( =====" + stringBuilder.toString() + "  &" + isBeginValue);
        return stringBuilder.toString().trim();
    }

    public static long getTimeDifference(long newDateTime, long oldDtaeTime) {
        long between = 0;
        between = newDateTime - oldDtaeTime;
        long day = between / (24 * 60 * 60 * 1000);
        long hour = (between / (60 * 60 * 1000) - day * 24);
        long min = ((between / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long s = (between / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        long ms = (between - day * 24 * 60 * 60 * 1000 - hour * 60 * 60 * 1000
                - min * 60 * 1000 - s * 1000);
        //System.out.println(day + "天" + hour + "小时" + min + "分" + s + "秒" + ms + "毫秒");
        return between;
    }

    public static String getPauseTime(String videoBeginDateTime, String videoEndDateTime, long diffTime) {
        SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String resultTime = videoBeginDateTime;
        try {
            Date begin = dfs.parse(videoBeginDateTime);
            Date end = dfs.parse(videoEndDateTime);
            long time = begin.getTime() + diffTime;
            if (end.getTime() >= time) {
                Date date = new Date(time);
                resultTime = dfs.format(date);
            } else {
                resultTime = videoEndDateTime;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return  resultTime;
    }

    /**
     * 将int值转换为分钟和秒的格式
     *
     * @param value
     * @return
     */
    public static String formatToString(int value) {
        SimpleDateFormat ft = new SimpleDateFormat("HH:mm:ss");
        //需要减去时区差，否则计算结果不正确(中国时区会相差8个小时)
        value -= TimeZone.getDefault().getRawOffset();
        String result = ft.format(value);
        return result;
    }


    public static int getProgress(String videoBeginDateTime, String videoEndDateTime, long diffTime) {
        SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        int position = 0;
        try {
            Date begin = dfs.parse(videoBeginDateTime);
            Date end = dfs.parse(videoEndDateTime);
            position = (int) (diffTime/(end.getTime() - begin.getTime()));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return  position;
    }

    public static String getProgressTime(String videoBeginDateTime, String videoEndDateTime, int position) {
        SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String resultTime = videoBeginDateTime;
        try {
            Date begin = dfs.parse(videoBeginDateTime);
            Date end = dfs.parse(videoEndDateTime);
            long time = (long) ((end.getTime() - begin.getTime()) * (float)(position/100));
            Date date = new Date(begin.getTime() + time);
            resultTime = dfs.format(date);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return  resultTime;
    }
}

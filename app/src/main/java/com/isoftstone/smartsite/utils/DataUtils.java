package com.isoftstone.smartsite.utils;

import android.util.Log;

/**
 * Created by zhangyinfu on 2017/10/22.
 */

public class DataUtils {

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
}

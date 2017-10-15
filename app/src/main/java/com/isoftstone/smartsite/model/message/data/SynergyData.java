package com.isoftstone.smartsite.model.message.data;


import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by yanyongjun on 2017/10/15.
 */

public class SynergyData {
    //消息类型
    public final static int TYPE_DEFAULT = 0;
    public final static int TYPE_RECEIVE_REPORT = 1;//接收到一份巡查报告
    public final static int TYPE_SEND_REPORT = 2; //发送一份报告

    private int mType = TYPE_DEFAULT;
    private int mId = -1;
    private long mTime = -1;
    private String mName = "";

    private String mDate = "2017-10-15";//TODO just for test

    public void setType(int type) {
        mType = type;
    }

    public int getType() {
        return mType;
    }

    public void setId(int id) {
        mId = id;
    }

    public int getId() {
        return mId;
    }

    public void setTime(long time) {
        mTime = time;
    }

    public long getTime() {
        return mTime;
    }

    public void setName(String name){
        mName = name;
    }

    public String getName(){
        return mName;
    }

    public String getStringDate() {
        if (mDate != null) {
            return mDate;
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(mTime);
        return format.format(date);
    }

}


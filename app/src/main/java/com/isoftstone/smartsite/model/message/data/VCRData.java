package com.isoftstone.smartsite.model.message.data;


import java.io.Serializable;

/**
 * Created by yanyongjun on 2017/10/15.
 */

public class VCRData implements Serializable {
    //消息类型
    public final static int TYPE_DEFAULT = 0;
    public final static int TYPE_ERROR = 1;
    public final static int TYPE_NEED_REPAIR = 2;

    private int mType = TYPE_DEFAULT;
    private int mId = -1;
    private long mTime = -1;
    private String mLoc = "光谷中心城1号工地";

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

    public void setLoc(String loc) {
        mLoc = loc;
    }

    public String getLoc() {
        return mLoc;
    }

    public String getStringDate() {
/*        if (mDate != null) {
            return mDate;
        }*/
        return "昨天";
/*        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(mTime);
        return format.format(date);*/
    }

}


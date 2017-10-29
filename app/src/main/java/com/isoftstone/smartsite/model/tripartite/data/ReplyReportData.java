package com.isoftstone.smartsite.model.tripartite.data;

import android.graphics.Bitmap;
import android.net.Uri;

import java.util.ArrayList;

import static com.isoftstone.smartsite.R.string.msg;

/**
 * Created by yanyongjun on 2017/10/29.
 */

public class ReplyReportData {
    public static final int TYPE_REPOTER = 0;
    public static final int TYPE_CHECKER = 1;

    public static final int TYPE_STATUS_PENDING = 1;

    private String mTime = null;
    private String mMsg = null;
    private ArrayList<Uri> mBitmap = new ArrayList<>();
    private Bitmap mHead = null;
    private String mName = null;
    private int mStatus = -1;
    private int mType = 0;

    public ReplyReportData(String time, String msg, Bitmap head, String name, int status, int type) {
        mTime = time;
        mMsg = msg;
        mHead = head;
        mName = name;
        mStatus = status;
        mType = type;
    }

    public String getTime() {
        return mTime;
    }

    public int getType() {
        return mType;
    }

    public String getMsg() {
        return mMsg;
    }

    public Bitmap getHead() {
        return mHead;
    }

    public String getName() {
        return mName;
    }

    public int getStatus() {
        return mStatus;
    }

    public int getBitmapSize(){
        return mBitmap.size();
    }

    public void addBitmap(Uri bitmap){
        mBitmap.add(bitmap);
    }

}

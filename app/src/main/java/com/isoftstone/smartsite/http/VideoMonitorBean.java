package com.isoftstone.smartsite.http;

/**
 * Created by gone on 2017/10/17.
 * modifed by zhangyinfu on 2017/10/19
 */

public class VideoMonitorBean {
    private String resCode;
    private String resName;
    private int resType;
    private int resSubType;
    private boolean isOnline = false; //false 在线   true离线
    private boolean isShared = false; //false 不共享   true共享

    public VideoMonitorBean(String resCode, int resType, String resName, boolean isOnline) {
        this.resCode = resCode;
        this.resName = resName;
        this.resType = resType;
        this.isOnline = isOnline;
    }

    public VideoMonitorBean(String resCode, String resName, int resType, int resSubType, boolean isOnline, boolean isShared) {
        this.resCode = resCode;
        this.resName = resName;
        this.resType = resType;
        this.resSubType = resSubType;
        this.isOnline = isOnline;
        this.isShared = isShared;
    }

    public String getResCode() {
        return resCode;
    }

    public void setResCode(String resCode) {
        this.resCode = resCode;
    }

    public String getResName() {
        return resName;
    }

    public void setResName(String resName) {
        this.resName = resName;
    }

    public int getResType() {
        return resType;
    }

    public void setResType(int resType) {
        this.resType = resType;
    }

    public int getResSubType() {
        return resSubType;
    }

    public void setResSubType(int resSubType) {
        this.resSubType = resSubType;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    public boolean isShared() {
        return isShared;
    }

    public void setShared(boolean shared) {
        isShared = shared;
    }

    @Override
    public String toString() {
        return "VideoMonitorBean{" +
                "resCode='" + resCode + '\'' +
                ", resName='" + resName + '\'' +
                ", resType=" + resType +
                ", resSubType=" + resSubType +
                ", isOnline=" + isOnline +
                ", isShared=" + isShared +
                '}';
    }
}

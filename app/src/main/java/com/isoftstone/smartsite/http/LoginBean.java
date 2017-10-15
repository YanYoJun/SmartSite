package com.isoftstone.smartsite.http;

/**
 * Created by gone on 2017/10/14.
 */

public class LoginBean {
    private String mErrorInfo = "";
    private int mErrorCode = 0;
    private String mDate = "";
    private String mToken = "";
    private String mViodIp = "";
    private String mPort = "";
    private String mName = "";
    private String mPassword = "";

    public String getmErrorInfo() {
        return mErrorInfo;
    }

    public void setmErrorInfo(String mErrorInfo) {
        this.mErrorInfo = mErrorInfo;
    }

    public int getmErrorCode() {
        return mErrorCode;
    }

    public void setmErrorCode(int mErrorCode) {
        this.mErrorCode = mErrorCode;
    }

    public String getmDate() {
        return mDate;
    }

    public void setmDate(String mDate) {
        this.mDate = mDate;
    }

    public String getmToken() {
        return mToken;
    }

    public void setmToken(String mToken) {
        this.mToken = mToken;
    }

    public String getmViodIp() {
        return mViodIp;
    }

    public void setmViodIp(String mViodIp) {
        this.mViodIp = mViodIp;
    }

    public String getmPort() {
        return mPort;
    }

    public void setmPort(String mPort) {
        this.mPort = mPort;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmPassword() {
        return mPassword;
    }

    public void setmPassword(String mPassword) {
        this.mPassword = mPassword;
    }

}

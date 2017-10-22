package com.isoftstone.smartsite.http;

/**
 * Created by gone on 2017/10/14.
 */

public class LoginBean {
    private boolean isLoginSuccess = false;
    private String mErrorInfo = "";
    private int mErrorCode = 0;   //1验证码超时   2验证码不正确  3用户不存在    4密码错误   5用户已锁定  6密码过期   7未知错误
    private String mDate = "";
    private String mToken = "";
    private String mViodIp = "";
    private String mPort = "";
    private String mName = "";    //用户登录姓名
    private String mPassword = "";  //用户登录密码

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
        switch (mErrorCode){
            case 1: mErrorInfo = "验证码超时";break;
            case 2: mErrorInfo = "验证码不正确";break;
            case 3: mErrorInfo = "用户不存在";break;
            case 4: mErrorInfo = "密码错误";break;
            case 5: mErrorInfo = "用户已锁定";break;
            case 6: mErrorInfo = "密码过期";break;
            case 7: mErrorInfo = "未知错误";break;
        }
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

    public boolean isLoginSuccess() {
        return isLoginSuccess;
    }

    public void setLoginSuccess(boolean loginSuccess) {
        isLoginSuccess = loginSuccess;
    }
}

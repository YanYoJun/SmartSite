package com.isoftstone.smartsite.http;

/**
 * Created by gone on 2017/10/17.
 * modifed by zhangyinfu on 2017/10/19
 */

public class VideoMonitorBean {
    private String devicename = "";
    private String instarltime = "";
    private String address = "";
    private boolean state = false; //false 在线   true离线

    public VideoMonitorBean(String devicename, String instarltime, String address, boolean state) {
        this.devicename = devicename;
        this.instarltime = instarltime;
        this.address = address;
        this.state = state;
    }

    public String getDevicename() {
        return devicename;
    }

    public void setDevicename(String devicename) {
        this.devicename = devicename;
    }

    public String getInstarltime() {
        return instarltime;
    }

    public void setInstarltime(String instarltime) {
        this.instarltime = instarltime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean getState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }
}

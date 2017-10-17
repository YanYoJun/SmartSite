package com.isoftstone.smartsite.http;

/**
 * Created by gone on 2017/10/17.
 */

public class VideoMonitorBean {
    private String devicename = "";
    private String instarltime = "";
    private String address = "";
    private int state = 0; //1 在线   2离线

    public VideoMonitorBean(String devicename, String instarltime, String address, int state) {
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

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}

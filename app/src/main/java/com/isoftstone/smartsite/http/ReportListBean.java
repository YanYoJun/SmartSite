package com.isoftstone.smartsite.http;

import java.util.ArrayList;

/**
 * Created by gone on 2017/10/15.
 */

public class ReportListBean {
    private String errorinfo = "";
    private int errorcode = 0;
    private String date = "";
    private ArrayList<ReportBean> patrolRepoList = new ArrayList<ReportBean>();  //巡查报告
    private ArrayList<ReportBean> checkRepoList = new ArrayList<ReportBean>();   //验收报告

    public String getErrorinfo() {
        return errorinfo;
    }

    public void setErrorinfo(String errorinfo) {
        this.errorinfo = errorinfo;
    }

    public int getErrorcode() {
        return errorcode;
    }

    public void setErrorcode(int errorcode) {
        this.errorcode = errorcode;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<ReportBean> getPatrolRepoList() {
        return patrolRepoList;
    }

    public void setPatrolRepoList(ArrayList<ReportBean> patrolRepoList) {
        this.patrolRepoList = patrolRepoList;
    }

    public ArrayList<ReportBean> getCheckRepoList() {
        return checkRepoList;
    }

    public void setCheckRepoList(ArrayList<ReportBean> checkRepoList) {
        this.checkRepoList = checkRepoList;
    }
}

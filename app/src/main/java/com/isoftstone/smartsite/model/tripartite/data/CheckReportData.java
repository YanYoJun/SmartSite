package com.isoftstone.smartsite.model.tripartite.data;

/**
 * Created by yanyongjun on 2017/10/16.
 * 巡查报告数据
 */

public class CheckReportData extends ReportData {
    public CheckReportData(int id, String reportName, String name, String time, String address,int stats){
        super(id,reportName,name,time,address,stats);
    }

    @Override
    public int getReportType() {
        return REPORT_TYPE_CHECK;
    }
}

package com.isoftstone.smartsite.model.tripartite.data;

/**
 * Created by yanyongjun on 2017/10/16.
 */

public abstract class ReportData {
    public static final int STATS_DEALING = 1;//处理中
    public static final int STATS_VISITED = 2;//已回访

    public static final int REPORT_TYPE_INSPECT = 1;//巡查报告
    public static final int REPORT_TYPE_CHECK = 2;//验收报告
    private int mId;
    private String mReportName;
    private String mName;
    private String mTime;
    private String mAddress;
    private int mStats;//1:处理中，2：已回访

    protected ReportData(int id, String reportName, String name, String time, String address,int stats) {
        mId = id;
        mReportName = reportName;
        mName = name;
        mTime = time;
        mAddress = address;
        mStats = stats;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public int getId() {
        return mId;
    }

    public void setReportName(String reportName) {
        mReportName = reportName;
    }

    public String getReportName() {
        return mReportName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getName() {
        return mName;
    }

    public void setAddress(String address) {
        mAddress = address;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setTime(String time) {
        mTime = time;
    }

    public String getTime() {
        return mTime;
    }

    public void setStats(int stats) {
        mStats = stats;
    }

    public int getStats() {
        return mStats;
    }

    /**
     * 获取报告类型
     *
     * @return
     */
    public abstract int getReportType();
}

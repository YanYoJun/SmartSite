package com.isoftstone.smartsite.model.tripartite.fragment;

import android.os.Bundle;
import android.widget.ListView;

import com.isoftstone.smartsite.R;
import com.isoftstone.smartsite.base.BaseFragment;
import com.isoftstone.smartsite.model.tripartite.activity.TripartiteActivity;
import com.isoftstone.smartsite.model.tripartite.data.ReportData;

import java.util.ArrayList;

/**
 * Created by yanyongjun on 2017/10/16.
 * 验收报告Fragment
 */

public class CheckReportMainFragment extends BaseFragment {
    public static final String ITEM_TITLE = "lab_title";
    public static final String ITEM_NAME = "lab_name";
    public static final String ITEM_TIME = "lab_time";
    public static final String ITEM_COMPANY = "lab_company";
    public static final String ITEM_STATS = "lab_status";

    private TripartiteActivity mActivity = null;
    private ListView mListView = null;
    private ArrayList<ReportData> mDatas = new ArrayList<>();
    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_check_report;
    }

    @Override
    protected void afterCreated(Bundle savedInstanceState) {
        mActivity = (TripartiteActivity)getActivity();
        init();
    }

    public void onDataSetChanged(){
        mDatas.clear();
        //TODO 这个地方不应该是addAll，应该是巡查人员是我的人
        mDatas.addAll(mActivity.getDatas());
    }

    private void init() {
//        mListView = (ListView) mActivity.findViewById(R.id.listview_check_frag);
//        SimpleAdapter adapter = new CheckReportAdapter(mActivity, getData(), R.layout.listview_check_report_item,
//                new String[]{ITEM_TITLE, ITEM_NAME, ITEM_TIME, ITEM_COMPANY},
//                new int[]{R.id.lab_title, R.id.lab_name, R.id.lab_time, R.id.lab_company});
//        mListView.setAdapter(adapter);
//        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                //TODO
//            }
//        });
    }

  /*  *//**
     * 加载listview的数据源
     *
     * @return
     *//*
    private List<Map<String, Object>> getData() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Resources res = mActivity.getResources();

        List<ReportData> datas = readDataFromSDK();
        String address = res.getString(R.string.report_address);
        String stats = res.getString(R.string.report_stats);
        String name = res.getString(R.string.report_name);
        for (ReportData data : datas) {
            Log.e(TAG,"yanlog getData:"+data);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put(ITEM_TITLE, data.getReportName());
            map.put(ITEM_NAME, data.getName());
            map.put(ITEM_COMPANY, data.getAddress());
            if (data.getStats() == ReportData.STATS_DEALING) {
                map.put(ITEM_STATS, res.getString(R.string.dealing));
            } else if (data.getStats() == ReportData.STATS_VISITED) {
                map.put(ITEM_STATS, res.getString(R.string.rechecked));
            }
            map.put(ITEM_TIME, data.getTime());
            list.add(map);
        }
        return list;
    }

    *//**
     * 目前是测试使用，后续需要从网络侧读取
     *
     * @return
     *//*
    private List<ReportData> readDataFromSDK() {
        //TODO
        mDatas = new ArrayList<>();

        ReportData data = new CheckReportData(1234, "东湖高新区验收报告", "张三", "2017-09-19", "东湖高新区光谷六路", ReportData.STATS_DEALING);
        mDatas.add(data);

        data = new CheckReportData(1234, "东湖高新区验收报告", "李四", "2017-09-19", "江夏区未来科技城", ReportData.STATS_DEALING);
        mDatas.add(data);

        data = new CheckReportData(1234, "未来科技城验收报告", "张三", "2017-09-19", "江夏区未来科技城", ReportData.STATS_VISITED);
        mDatas.add(data);

        data = new CheckReportData(1234, "东湖高新区巡查报告", "张三", "2017-09-19", "东湖高新区光谷六路", ReportData.STATS_VISITED);
        mDatas.add(data);

        return mDatas;
    }*/
}

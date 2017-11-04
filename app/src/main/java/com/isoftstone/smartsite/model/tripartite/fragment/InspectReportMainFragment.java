package com.isoftstone.smartsite.model.tripartite.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.isoftstone.smartsite.R;
import com.isoftstone.smartsite.base.BaseFragment;
import com.isoftstone.smartsite.http.HttpPost;
import com.isoftstone.smartsite.model.tripartite.activity.TripartiteActivity;
import com.isoftstone.smartsite.model.tripartite.adapter.InspectReportAdapter;
import com.isoftstone.smartsite.model.tripartite.data.ReportData;

import java.util.ArrayList;

/**
 * Created by yanyongjun on 2017/10/16.
 * 巡查报告fragment
 */

public class InspectReportMainFragment extends BaseFragment {
    public static final String ITEM_TITLE = "lab_title";
    public static final String ITEM_NAME = "lab_name";
    public static final String ITEM_TIME = "lab_time";
    public static final String ITEM_COMPANY = "lab_company";
    public static final String ITEM_STATS = "lab_status";

    private TripartiteActivity mActivity = null;
    private ListView mListView = null;
    private ArrayList<ReportData> mDatas = new ArrayList<>();
    private BaseAdapter mAdapter = null;
    private HttpPost mHttpPost = null;
    private String mAccountName = "";
/*    private Button mAdd = null;*/

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_inspect_report;
    }

    @Override
    protected void afterCreated(Bundle savedInstanceState) {
        mActivity = (TripartiteActivity) getActivity();
        mHttpPost = new HttpPost();
        init();
    }

    public void onDataSetChanged() {
        Log.e(TAG, "onDataSetChanged");
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    mDatas.clear();
                    if (mAccountName == null || mAccountName.equals("")) {
                        mAccountName = mHttpPost.mLoginBean.getmName();
                    }
                    ArrayList<ReportData> sourceData = mActivity.getDatas();
                    for (ReportData temp : sourceData) {
                        if (mAccountName.equals(temp.getCreator())) {
                            mDatas.add(temp);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                mAdapter.notifyDataSetChanged();
            }
        }.execute();
    }

    private void init() {
        //初始化Listview
        mListView = (ListView) mActivity.findViewById(R.id.listview);
        mAdapter = new InspectReportAdapter(mActivity, mDatas);
        mListView.setAdapter(mAdapter);
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

        ReportData data = new InspectReportData(1234, "东湖高新区巡查报告", "张三", "2017-09-19", "东湖高新区光谷六路", ReportData.STATS_DEALING);
        mDatas.add(data);

        data = new InspectReportData(1234, "东湖高新区巡查报告", "李四", "2017-09-19", "江夏区未来科技城", ReportData.STATS_DEALING);
        mDatas.add(data);

        data = new InspectReportData(1234, "未来科技城巡查报告", "张三", "2017-09-19", "江夏区未来科技城", ReportData.STATS_VISITED);
        mDatas.add(data);

        data = new InspectReportData(1234, "东湖高新区巡查报告", "张三", "2017-09-19", "东湖高新区光谷六路", ReportData.STATS_VISITED);
        mDatas.add(data);

        return mDatas;
    }*/
}

package com.isoftstone.smartsite.model.Tripartite.ui;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.isoftstone.smartsite.R;
import com.isoftstone.smartsite.base.BaseFragment;
import com.isoftstone.smartsite.model.Tripartite.adapter.InspectReportAdapter;
import com.isoftstone.smartsite.model.Tripartite.data.InspectReportData;
import com.isoftstone.smartsite.model.Tripartite.data.ReportData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yanyongjun on 2017/10/16.
 * 巡查报告fragment
 */

public class InspectReportFragment extends BaseFragment {
    public static final String ITEM_REPORT_NAME = "lab_inspect_report_name";
    public static final String ITEM_NAME = "lab_inspect_name";
    public static final String ITEM_TIME = "lab_inspect_time";
    public static final String ITEM_ADDRESS = "lab_inspect_address";
    public static final String ITEM_STATS = "lab_inspect_stats";

    private Activity mActivity = null;
    private ListView mListView = null;
    private List<ReportData> mDatas = null;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_inspect_report;
    }

    @Override
    protected void afterCreated(Bundle savedInstanceState) {
        mActivity = getActivity();
        init();
    }

    private void init() {
        mListView = (ListView) mActivity.findViewById(R.id.listview_frag_inspect_report);
        SimpleAdapter adapter = new InspectReportAdapter(mActivity, getData(), R.layout.frag_inspect_report_item,
                new String[]{ITEM_REPORT_NAME, ITEM_NAME, ITEM_TIME, ITEM_ADDRESS, ITEM_STATS},
                new int[]{R.id.lab_inspect_report_name, R.id.lab_inspect_name, R.id.lab_inspect_time, R.id.lab_inspect_address, R.id.lab_inspect_stats});
        mListView.setAdapter(adapter);
    }

    /**
     * 加载listview的数据源
     *
     * @return
     */
    private List<Map<String, Object>> getData() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Resources res = mActivity.getResources();

        List<ReportData> datas = readDataFromSDK();
        String address = res.getString(R.string.report_address);
        String stats = res.getString(R.string.report_stats);
        String name = res.getString(R.string.report_name);
        for (ReportData data : datas) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put(ITEM_REPORT_NAME, data.getReportName());
            map.put(ITEM_NAME, String.format(name, data.getName()));
            map.put(ITEM_ADDRESS, String.format(address, data.getAddress()));
            if (data.getStats() == ReportData.STATS_DEALING) {
                map.put(ITEM_STATS, String.format(stats, res.getString(R.string.dealing)));
            } else if (data.getStats() == ReportData.STATS_VISITED) {
                map.put(ITEM_STATS, String.format(stats, res.getString(R.string.rechecked)));
            }
            map.put(ITEM_TIME, data.getTime());
            list.add(map);
        }
        return list;
    }

    /**
     * 目前是测试使用，后续需要从网络侧读取
     *
     * @return
     */
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
    }
}

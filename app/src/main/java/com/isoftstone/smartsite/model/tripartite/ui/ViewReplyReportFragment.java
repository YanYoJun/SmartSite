package com.isoftstone.smartsite.model.tripartite.ui;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ListView;

import com.isoftstone.smartsite.R;
import com.isoftstone.smartsite.base.BaseFragment;
import com.isoftstone.smartsite.model.tripartite.adapter.ReplyReportAdapter;
import com.isoftstone.smartsite.model.tripartite.data.ReplyReportData;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by yanyongjun on 2017/10/29.
 */

public class ViewReplyReportFragment extends BaseFragment {
    private ListView mListView = null;

    private ArrayList<ReplyReportData> mDatas = new ArrayList<ReplyReportData>();


    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_view_reply_report;
    }

    @Override
    protected void afterCreated(Bundle savedInstanceState) {
        initData();
        init();
    }

    private void init() {
        //初始化Listview
        mListView = (ListView) getView().findViewById(R.id.listview);

        ReplyReportAdapter adapter = new ReplyReportAdapter(getActivity(), mDatas);
        mListView.setAdapter(adapter);
        //mListView.setDividerHeight(0);
    }

    //TODO
    private void initData() {
        ReplyReportData data = new ReplyReportData("2017-10-02", "与事实不符，请知悉", null, "刘王孙", ReplyReportData.TYPE_STATUS_PENDING, ReplyReportData.TYPE_CHECKER);
        data.addBitmap(Uri.fromFile(new File("data.bat")));
        mDatas.add(data);

        data = new ReplyReportData("2017-10-03", "是符合的，请大人明察", null, "张三峰", ReplyReportData.TYPE_STATUS_PENDING, ReplyReportData.TYPE_REPOTER);
        mDatas.add(data);


        data = new ReplyReportData("2017-10-04", "OK，已通过", null, "刘王孙", ReplyReportData.TYPE_STATUS_PENDING, ReplyReportData.TYPE_CHECKER);
        mDatas.add(data);
    }

}

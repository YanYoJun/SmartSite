package com.isoftstone.smartsite.model.tripartite.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.isoftstone.smartsite.R;
import com.isoftstone.smartsite.base.BaseActivity;

/**
 * Created by yanyongjun on 2017/10/19.
 * 查看巡查报告界面
 */

public class InspectReportViewActivity extends BaseActivity {
    @Override
    protected int getLayoutRes() {
        return R.layout.activity_view_inspect_report;
    }

    @Override
    protected void afterCreated(Bundle savedInstanceState) {
        addRevisitFrag();

    }

    private void addRevisitFrag(){
        FragmentManager fragManager = getSupportFragmentManager();
        FragmentTransaction fragTrans = fragManager.beginTransaction();

        Fragment revisitFrag = new ViewRevisitInsepectReportFrag();
        fragTrans.add(R.id.linear_frag_group,revisitFrag);
        fragTrans.commit();


    }
}

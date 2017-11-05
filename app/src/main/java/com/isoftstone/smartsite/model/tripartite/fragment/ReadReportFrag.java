package com.isoftstone.smartsite.model.tripartite.fragment;

import android.os.Bundle;

import com.isoftstone.smartsite.R;
import com.isoftstone.smartsite.base.BaseFragment;

/**
 * Created by yanyongjun on 2017/10/19.
 * 查看巡查报告的fragment界面，可以嵌套在不同的activity中
 */

public class ReadReportFrag extends BaseFragment {


    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_read_report;
    }

    @Override
    protected void afterCreated(Bundle savedInstanceState) {

    }



}

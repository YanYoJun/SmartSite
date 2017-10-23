package com.isoftstone.smartsite.model.tripartite.ui;

import android.os.Bundle;

import com.isoftstone.smartsite.R;
import com.isoftstone.smartsite.base.BaseFragment;

/**
 * Created by yanyongjun on 2017/10/19.
 * 查看巡查报告的fragment界面，可以嵌套在不同的activity中
 */

public class InspectReportViewFrag extends BaseFragment {
    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_view_inspect_report;
    }

    @Override
    protected void afterCreated(Bundle savedInstanceState) {

    }
}

package com.isoftstone.smartsite.model.tripartite.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.isoftstone.smartsite.R;
import com.isoftstone.smartsite.base.BaseActivity;
import com.isoftstone.smartsite.base.BaseFragment;
import com.isoftstone.smartsite.http.PatrolBean;

/**
 * Created by yanyongjun on 2017/10/19.
 * 查看巡查报告的fragment界面，可以嵌套在不同的activity中
 */

public class ReadReportFrag extends BaseFragment {
    private BaseActivity mActivity = null;
    private PatrolBean mData = null;
    private TextView mLabAddress = null;
    private TextView mLabCompany = null;
    private TextView mLabStatus = null;
    private TextView mLabTypes = null;
    private TextView mBuildCompany = null;
    private TextView mCosCompany = null;
    private TextView mSupCompany = null;
    private TextView mLabVisit = null;
    private View mView = null;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_read_report;
    }

    @Override
    protected void afterCreated(Bundle savedInstanceState) {
        mActivity = (BaseActivity) getActivity();
        initView();
    }

    private void initView() {
        if (mView == null) {
            mView = getView();
        }
        mLabAddress = (TextView) mView.findViewById(R.id.inspect_report_address);
        mLabCompany = (TextView) mView.findViewById(R.id.inspect_report_company);
        mLabStatus = (TextView) mView.findViewById(R.id.inspect_report_status);
        mLabTypes = (TextView) mView.findViewById(R.id.inspect_report_types);
        mBuildCompany = (TextView) mView.findViewById(R.id.inspect_report_build_company_read);
        mCosCompany = (TextView) mView.findViewById(R.id.inspect_report_construction_company);
        mSupCompany = (TextView) mView.findViewById(R.id.inspect_report_supervision_company);
        mLabVisit = (TextView) mView.findViewById(R.id.lab_inspect_report_revisit_time);
    }

    private void initViewData() {
        mLabAddress.setText(mData.getAddress());
        mLabCompany.setText(mData.getCompany());
        int status = mData.getStatus();
        if (status > 1) {
            status--;
        }
        mLabStatus.setText(getActivity().getResources().getStringArray(R.array.status_array)[status]); //TODO
        //mLabTypes.setText(mData.get()); //TODO
        mBuildCompany.setText(mData.getDevelopmentCompany());
        mCosCompany.setText(mData.getConstructionCompany());
        mSupCompany.setText(mData.getSupervisionCompany());
        if(mData.isVisit()){
            mLabVisit.setText(mData.getVisitDate());
        }
    }

    public void notifyDataChanged() {
        mData = mActivity.getReportData();
        initViewData();
    }


}

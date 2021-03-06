package com.isoftstone.smartsite.model.tripartite.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.isoftstone.smartsite.R;
import com.isoftstone.smartsite.base.BaseActivity;
import com.isoftstone.smartsite.base.BaseFragment;
import com.isoftstone.smartsite.http.DictionaryBean;
import com.isoftstone.smartsite.http.PatrolBean;

import java.util.ArrayList;

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
    private ArrayList<String> mTypesList = new ArrayList<>();

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
        new QueryReportTypeTask().execute();
    }

    private void initViewData() {
        if (mData != null) {
            try {
                mLabAddress.setText(mData.getAddress());
                mLabCompany.setText(mData.getCompany());
                int status = mData.getStatus();
                if (status > 1) {
                    status--;
                }
                mLabStatus.setText(getActivity().getResources().getStringArray(R.array.status_array)[status]);

                String developmentcompany = mData.getDevelopmentCompany();
                if (TextUtils.isEmpty(developmentcompany)) {
                    mBuildCompany.setText("无");
                } else {
                    mBuildCompany.setText(mData.getDevelopmentCompany());
                }

                String cosCompany = mData.getConstructionCompany();
                if (TextUtils.isEmpty(cosCompany)) {
                    mCosCompany.setText("无");
                } else {
                    mCosCompany.setText(mData.getConstructionCompany());
                }

                String supCompany = mData.getSupervisionCompany();
                if (TextUtils.isEmpty(supCompany)) {
                    mSupCompany.setText("无");
                } else {
                    mSupCompany.setText(mData.getSupervisionCompany());
                }

                if (mData.isVisit()) {
                    mLabVisit.setText(mData.getVisitDate());
                }

                int category = Integer.parseInt(mData.getCategory());
                mLabTypes.setText(mTypesList.get(category - 1));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void notifyDataChanged() {
        mData = mActivity.getReportData();
        initViewData();
    }

    private class QueryReportTypeTask extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... params) {
//            ArrayList<MessageBean> msgs = mHttpPost.getPatrolReportList("", "", "", "1");
            ArrayList<DictionaryBean> tempLists = mHttpPost.getDictionaryList("zh");
            if (tempLists != null) {
                for (DictionaryBean temp : tempLists) {
                    mTypesList.add(temp.getContent());
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                int category = Integer.parseInt(mData.getCategory());
                mLabTypes.setText(mTypesList.get(category - 1));
            } catch (Exception e) {
                e.printStackTrace();
            }
            super.onPostExecute(s);
        }
    }


}

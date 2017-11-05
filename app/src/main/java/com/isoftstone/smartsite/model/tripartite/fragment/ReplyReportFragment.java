package com.isoftstone.smartsite.model.tripartite.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.isoftstone.smartsite.R;
import com.isoftstone.smartsite.base.BaseActivity;
import com.isoftstone.smartsite.base.BaseFragment;
import com.isoftstone.smartsite.http.ReportBean;
import com.isoftstone.smartsite.utils.DateUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yanyongjun on 2017/10/30.
 */

public class ReplyReportFragment extends BaseFragment {
    private GridView mAttachView = null;
    private SimpleAdapter mAttachAdapter = null;
    private ArrayList<Map<String, Object>> mData = null;

    private EditText mEditContent = null;
    private Button mSubButton = null;
    private BaseActivity mActivity = null;
    private TextView mLabName = null;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_reply_report;
    }

    @Override
    protected void afterCreated(Bundle savedInstanceState) {
        init();
    }

    private void init() {
        initView();
        initGridView();
    }

    private void initView() {
        mActivity = (BaseActivity) getActivity();
        mEditContent = (EditText) rootView.findViewById(R.id.edit_report_msg);
        mSubButton = (Button) rootView.findViewById(R.id.btn_add_report_submit);
        mSubButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAllMsgSetted()) {
                    new DealTask(getReportBean()).execute();
                } else {
                    Toast.makeText(getActivity(), "您还有未填写的信息", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mLabName = (TextView)rootView.findViewById(R.id.lab_report_people_name);
        mLabName.setText(mHttpPost.mLoginBean.getmName());
    }

    public void initGridView() {
        mAttachView = (GridView) getView().findViewById(R.id.grid_view);

        mData = new ArrayList<Map<String, Object>>();
        Map<String, Object> data = new HashMap<>();
        data.put("image", R.drawable.attachment);
        mData.add(data);
        mAttachAdapter = new SimpleAdapter(getActivity(), mData, R.layout.add_attach_grid_item, new String[]{"image"}, new int[]{R.id.image});
        mAttachView.setAdapter(mAttachAdapter);

        mAttachView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
/*                if (position == mData.size() - 1) {
                    //点击添加附件
                    Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                    i.setType("image*//*");
                    startActivityForResult(i, REQUEST_ACTIVITY_ATTACH);
                }*/
            }
        });
    }

    private boolean isAllMsgSetted() {
        if (TextUtils.isEmpty(mEditContent.getText())) {
            return false;
        }
        return true;
    }

    private ReportBean getReportBean() {
        ReportBean reportBean = new ReportBean();
        reportBean.setPatrol(mActivity.getReportData());
        reportBean.setCreator(mHttpPost.mLoginBean.getmName());
        reportBean.setContent(mEditContent.getText().toString()); //TODO
        reportBean.setDate(DateUtils.format2.format(new Date()));
        reportBean.setCategory(2);
        reportBean.setStatus(mActivity.getReportData().getStatus());
        return reportBean;
    }

    private class DealTask extends AsyncTask<Void, Void, Void> {
        private ReportBean mBean = null;

        public DealTask(ReportBean reportBean) {
            mBean = reportBean;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Log.e(TAG, "deal task begin");
            mHttpPost.addPatrolReply(mBean);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            getActivity().finish();
        }
    }
}

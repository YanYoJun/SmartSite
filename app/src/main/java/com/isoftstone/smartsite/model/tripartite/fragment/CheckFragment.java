package com.isoftstone.smartsite.model.tripartite.fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.isoftstone.smartsite.R;
import com.isoftstone.smartsite.base.BaseActivity;
import com.isoftstone.smartsite.base.BaseFragment;
import com.isoftstone.smartsite.http.PatrolBean;
import com.isoftstone.smartsite.http.ReportBean;
import com.isoftstone.smartsite.model.tripartite.activity.TripartiteActivity;
import com.isoftstone.smartsite.model.tripartite.adapter.AttachGridViewAdatper;
import com.isoftstone.smartsite.utils.DateUtils;
import com.isoftstone.smartsite.utils.FilesUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * 点击验收后，下面验收的编辑框
 * Created by yanyongjun on 2017/10/30.
 */

public class CheckFragment extends BaseFragment {
    private GridView mAttachView = null;
    private ArrayList<Object> mData = null;
    private AttachGridViewAdatper mAttachAdapter = null;

    private Resources mRes = null;
    private Drawable mWaittingAdd = null;
    private Drawable mWattingChanged = null;

    private TextView mLabRevisitTime = null;
    private TextView mEditRevisitTime = null;
    private EditText mEditContent = null;
    private TextView mLabCreator = null;
    private Button mBtnYes = null;
    private Button mBtnNo = null;

    private Calendar mCal = null;
    private BaseActivity mActivity = null;

    private RadioButton mRadioYes = null;
    private RadioButton mRadioNo = null;
    public final static int REQUEST_ACTIVITY_ATTACH = 0;//请求图片的request code
    private ArrayList<String> mFilesPath = new ArrayList<>();
    private PatrolBean mReportData = null;


    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_recheck_check_report;
    }

    @Override
    protected void afterCreated(Bundle savedInstanceState) {
        init();
    }

    public void init() {
        mRes = getResources();
        mWaittingAdd = mRes.getDrawable(R.drawable.addcolumn);
        mWaittingAdd.setBounds(0, 0, mWaittingAdd.getIntrinsicWidth(), mWaittingAdd.getIntrinsicHeight());
        mWattingChanged = mRes.getDrawable(R.drawable.editcolumn);
        mWattingChanged.setBounds(0, 0, mWattingChanged.getIntrinsicWidth(), mWattingChanged.getIntrinsicHeight());

        mCal = Calendar.getInstance();
        mActivity = (BaseActivity) getActivity();
        initView();
        initListener();
        initGridView();
    }

    public void initView() {
        mLabRevisitTime = (TextView) rootView.findViewById(R.id.lab_revisit_time);
        mEditRevisitTime = (TextView) rootView.findViewById(R.id.edit_lab_revisit_time);
        mLabCreator = (TextView) rootView.findViewById(R.id.lab_report_people_name);
        mLabCreator.setText(mHttpPost.mLoginBean.getmName());
        mBtnNo = (Button) rootView.findViewById(R.id.btn_no);
        mBtnYes = (Button) rootView.findViewById(R.id.btn_yes);
        mEditContent = (EditText) rootView.findViewById(R.id.edit_report_msg);

        mRadioNo = (RadioButton) getView().findViewById(R.id.radio_no);
        mRadioYes = (RadioButton) getView().findViewById(R.id.radio_yes);
    }

    public void initListener() {
        mBtnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAllMsgSetted()) {
                    ReportBean bean = getReportBean();
                    if (mRadioYes.isChecked()) {
                        bean.setStatus(3);
                    } else {
                        bean.setStatus(5);
                    }
                    new DealTask(bean).execute();
                } else {
                    Toast.makeText(getActivity(), "您还有未填写的信息", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mBtnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAllMsgSetted()) {
                    ReportBean bean = getReportBean();
                    bean.setStatus(4);
                    new DealTask(bean).execute();
                } else {
                    Toast.makeText(getActivity(), "您还有未填写的信息", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mRadioYes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                RelativeLayout relativeLayout = (RelativeLayout) getView().findViewById(R.id.relative_revisit_time);
                if (isChecked) {
                    relativeLayout.setVisibility(View.VISIBLE);
                } else {
                    relativeLayout.setVisibility(View.GONE);
                }
            }
        });

        //选时间
        mEditRevisitTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        mEditRevisitTime.setText("" + year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                        //mRevisitDate = new ITime(year, monthOfYear + 1, dayOfMonth);
                        mEditRevisitTime.setTextColor(mRes.getColor(R.color.main_text_color));
                        mLabRevisitTime.setCompoundDrawables(mWattingChanged, null, null, null);
                    }
                }, mCal.get(Calendar.YEAR), mCal.get(Calendar.MONTH), mCal.get(Calendar.DAY_OF_MONTH));
                dialog.show();
            }
        });
    }

    public void initGridView() {
        mAttachView = (GridView) getView().findViewById(R.id.grid_view);

        mData = new ArrayList<Object>();
        mData.add(R.drawable.attachment);
        mAttachAdapter = new AttachGridViewAdatper(getActivity(), mData);
        mAttachView.setAdapter(mAttachAdapter);

        mAttachView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == mData.size() - 1) {
                    //点击添加附件
                    Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                    i.setType("*/*");
                    startActivityForResult(i, REQUEST_ACTIVITY_ATTACH);
                }
            }
        });
    }

    public void notifyDataSetChanged() {
        mReportData = mActivity.getReportData();
        if (mReportData.isVisit()) {
            LinearLayout linearLayout = (LinearLayout) rootView.findViewById(R.id.linear_is_report);
            linearLayout.setVisibility(View.GONE);
        }
    }

    private boolean isAllMsgSetted() {
        if (TextUtils.isEmpty(mEditContent.getText())) {
            return false;
        }
        if (mRadioYes.isChecked()) {
            try {
                DateUtils.format2.format(DateUtils.format1.parse(mEditRevisitTime.getText().toString()));
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    private ReportBean getReportBean() {
        PatrolBean tempBeam = new PatrolBean();
        tempBeam.setId(mActivity.getReportData().getId());

        ReportBean reportBean = new ReportBean();
        reportBean.setPatrol(tempBeam);
        //reportBean.setCreator(mHttpPost.mLoginBean.getmName());
        reportBean.setContent(mEditContent.getText().toString()); //TODO
        reportBean.setVisit(mRadioYes.isChecked());
        reportBean.setDate(DateUtils.format2.format(new Date()));
        if (mRadioYes.isChecked()) {
            String visitTime = mEditRevisitTime.getText().toString();
            try {
                visitTime = DateUtils.format2.format(DateUtils.format1.parse(mEditRevisitTime.getText().toString()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            reportBean.setVisitDate(visitTime);
        }
        reportBean.setCategory(3);
        return reportBean;
    }

    private class DealTask extends AsyncTask<Void, Void, Boolean> {
        private ReportBean mBean = null;

        public DealTask(ReportBean reportBean) {
            mBean = reportBean;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            Log.e(TAG, "deal task begin");
            mHttpPost.addPatrolCheck(mBean);
            try {
                if (mFilesPath != null && mFilesPath.size() >= 1) {
                    PatrolBean report = mHttpPost.getPatrolReport(mBean.getPatrol().getId() + "");
                    ArrayList<ReportBean> reports = report.getReports();
                    int id = report.getReports().get(reports.size() - 1).getId();
                    for (String path : mFilesPath) {
                        mHttpPost.reportFileUpload(path, id);
                    }
                }
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Boolean temp) {
            super.onPostExecute(temp);
            if (temp == true) {
                //Toast.makeText(getActivity(), "验收报告成功", Toast.LENGTH_SHORT).show();
                getActivity().finish();
            } else {
                //Toast.makeText(getActivity(), "验收报告失败", Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_ACTIVITY_ATTACH: {
                Log.e(TAG, "onActivityResult:" + data);
                if (resultCode == Activity.RESULT_OK) {
                    Uri uri = data.getData();
                    Log.e(TAG, "yanlog uri:" + uri);
                    if ("file".equalsIgnoreCase(uri.getScheme())) {//使用第三方应用打开
                        //Toast.makeText(getActivity(), uri.getPath() + "11111", Toast.LENGTH_SHORT).show();
                        addAttach(uri.getPath(), uri.toString());
                        return;
                    }
                    String path = FilesUtils.getPath(getActivity(), uri);
                    //Toast.makeText(getActivity(), path, Toast.LENGTH_SHORT).show();
                    addAttach(path, uri.toString());
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    //add files
    public void addAttach(String path, String uri) {
        Log.e(TAG, "yanlog remove begin size:" + mData.size());
        String formatPath = FilesUtils.getFormatString(path);
        Log.e(TAG, "yanlog remove begin size at0:" + mData.get(0));
        mData.remove(mData.size() - 1);
        mFilesPath.add(path);
        if (TripartiteActivity.mImageList.contains(formatPath)) {
            mData.add(uri);
        } else if (TripartiteActivity.mXlsList.contains(formatPath)) {
            mData.add(TripartiteActivity.mAttach.get(".xls"));
        } else if (TripartiteActivity.mDocList.contains(formatPath)) {
            mData.add(TripartiteActivity.mAttach.get(".doc"));
        } else if (TripartiteActivity.mPdfList.contains(formatPath)) {
            mData.add(TripartiteActivity.mAttach.get(".pdf"));
        } else if (TripartiteActivity.mPptList.contains(formatPath)) {
            mData.add(TripartiteActivity.mAttach.get(".ppt"));
        } else if (TripartiteActivity.mVideoList.contains(formatPath)) {
            mData.add(TripartiteActivity.mAttach.get(".video"));
        } else {
            mData.add(TripartiteActivity.mAttach.get(".doc"));
        }

        mData.add(R.drawable.attachment);
        Log.e(TAG, "yanlog remove end size:" + mData.size());
        Log.e(TAG, "yanlog mData at 0:" + mData.get(0));
        mAttachAdapter.notifyDataSetChanged();
        mAttachView.requestLayout();
        mAttachView.setMinimumHeight(600);
    }
}

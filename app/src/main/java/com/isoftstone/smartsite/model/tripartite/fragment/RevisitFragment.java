package com.isoftstone.smartsite.model.tripartite.fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.isoftstone.smartsite.R;
import com.isoftstone.smartsite.User;
import com.isoftstone.smartsite.base.BaseActivity;
import com.isoftstone.smartsite.base.BaseFragment;
import com.isoftstone.smartsite.http.HttpPost;
import com.isoftstone.smartsite.http.PatrolBean;
import com.isoftstone.smartsite.http.ReportBean;
import com.isoftstone.smartsite.http.UserBean;
import com.isoftstone.smartsite.model.tripartite.activity.AddReportActivity;
import com.isoftstone.smartsite.model.tripartite.activity.TripartiteActivity;
import com.isoftstone.smartsite.model.tripartite.adapter.AttachGridViewAdatper;
import com.isoftstone.smartsite.model.tripartite.data.ITime;
import com.isoftstone.smartsite.model.tripartite.data.ReportData;
import com.isoftstone.smartsite.utils.DateUtils;
import com.isoftstone.smartsite.utils.FilesUtils;
import com.isoftstone.smartsite.utils.SPUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * 回访下面的白嫩及矿
 * Created by yanyongjun on 2017/10/30.
 */
public class RevisitFragment extends BaseFragment {
    private GridView mAttachView = null;
    private AttachGridViewAdatper mAttachAdapter = null;
    private ArrayList<Object> mData = null;
    private AddReportActivity mAddReportActivity = null;
    public final static int REQUEST_ACTIVITY_ATTACH = 0;//请求图片的request code

    private Resources mRes = null;
    private Drawable mWaittingAdd = null;
    private Drawable mWattingChanged = null;
    private HttpPost mHttpPost = null;

    private TextView mName = null;
    private TextView mReportName = null;
    private TextView mReportMsg = null;
    private TextView mBeginTime = null;
    private TextView mEndTime = null;
    private TextView mRevisitTime = null;
    private TextView mLabTime = null;
    private TextView mReportPeopleName = null;

    private EditText mEditName = null;
    private EditText mEditReportName = null;
    private EditText mEditReportMsg = null;
    private TextView mEditRevisitTime = null;
    private Button mSubButton = null;
    private RadioButton mRadioYes = null;
    private RadioButton mRadioNo = null;

    private ITime mBeginDate = null;
    private ITime mEndDate = null;
    private ITime mRevisitDate = null;

    private Calendar mCal = null;

    private ArrayList<String> mFilesPath = new ArrayList<>();


    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_revisit_report;
    }

    @Override
    protected void afterCreated(Bundle savedInstanceState) {
        init();
    }

    private void init() {
        mRes = getResources();
        mWaittingAdd = mRes.getDrawable(R.drawable.addcolumn);
        mWaittingAdd.setBounds(0, 0, mWaittingAdd.getIntrinsicWidth(), mWaittingAdd.getIntrinsicHeight());
        mWattingChanged = mRes.getDrawable(R.drawable.editcolumn);
        mWattingChanged.setBounds(0, 0, mWattingChanged.getIntrinsicWidth(), mWattingChanged.getIntrinsicHeight());

        mCal = Calendar.getInstance();
        mHttpPost = new HttpPost();
        if (getActivity() instanceof AddReportActivity) {
            mAddReportActivity = (AddReportActivity) getActivity();
        }
        initView();
        initListener();
        initGridView();
        restoreData();
    }

    private void initView() {
        mReportPeopleName = (TextView) getView().findViewById(R.id.lab_report_people_name);
        //mReportPeopleName.setText(mHttpPost.mLoginBean.getmName());
        mName = (TextView) getView().findViewById(R.id.lab_name);
        mReportName = (TextView) getView().findViewById(R.id.lab_report_name);
        mReportMsg = (TextView) getView().findViewById(R.id.lab_report_msg);
        mBeginTime = (TextView) getView().findViewById(R.id.lab_begin_time);
        mEndTime = (TextView) getView().findViewById(R.id.lab_end_time);
        mRevisitTime = (TextView) getView().findViewById(R.id.lab_revisit_time);
        mLabTime = (TextView) getView().findViewById(R.id.lab_inspect_report_time);

        mEditName = (EditText) getView().findViewById(R.id.edit_name);
        mEditReportName = (EditText) getView().findViewById(R.id.edit_report_name);
        mEditReportMsg = (EditText) getView().findViewById(R.id.edit_report_msg);
        mEditRevisitTime = (TextView) getView().findViewById(R.id.lab_edit_revisit_time);
        mSubButton = (Button) getView().findViewById(R.id.btn_add_report_submit);
        mRadioNo = (RadioButton) getView().findViewById(R.id.radio_no);
        mRadioYes = (RadioButton) getView().findViewById(R.id.radio_yes);
    }

    private String parseTime(String time) {
        try {
            Date date = DateUtils.format1.parse(time);
            String result = DateUtils.format2.format(date);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void saveData() {
        SPUtils.saveString("add_report_fragment_check_people_name", mEditName.getText().toString());
        String beginTime = mBeginTime.getText().toString();
        String endTime = mEndTime.getText().toString();
        SPUtils.saveString("add_report_fragment_report_name", mEditReportName.getText().toString());
        SPUtils.saveString("add_report_fragment_report_msg", mEditReportMsg.getText().toString());

        if (parseTime(beginTime) != null) {
            SPUtils.saveString("add_report_fragment_begin_time", beginTime);
        }
        if (parseTime(endTime) != null) {
            SPUtils.saveString("add_report_framgent_end_time", endTime);
        }
        boolean visit = mRadioYes.isChecked();
        String visitTime = mEditRevisitTime.getText().toString();
        SPUtils.saveBoolean("add_report_fragment_isVisit", visit);
        Log.e(TAG, "saveData:" + visitTime);
        if (parseTime(visitTime) != null) {
            Log.e(TAG, "saveData into sp");
            SPUtils.saveString("add_report_fragment_visit_time", visitTime);
        }
    }

    public void restoreData() {
        if (mAddReportActivity != null) {
            mEditName.setText(SPUtils.getString("add_report_fragment_check_people_name", ""));
            mEditReportName.setText(SPUtils.getString("add_report_fragment_report_name", ""));
            mEditReportMsg.setText(SPUtils.getString("add_report_fragment_report_msg", ""));

            String beginTime = SPUtils.getString("add_report_fragment_begin_time", null);
            String endTime = SPUtils.getString("add_report_framgent_end_time", null);
            String visitTime = SPUtils.getString("add_report_fragment_visit_time", null);
            Log.e(TAG, "visittime:" + visitTime);
            if (beginTime != null) {
                mBeginTime.setText(beginTime);
                mBeginTime.setTextColor(getActivity().getResources().getColor(R.color.main_text_color));
            }
            if (endTime != null) {
                mEndTime.setText(endTime);
                mEndTime.setTextColor(getActivity().getResources().getColor(R.color.main_text_color));
            }
            if (beginTime != null && endTime != null) {
                mLabTime.setCompoundDrawables(mWattingChanged, null, null, null);
            }
            boolean isVisit = SPUtils.getBoolean("add_report_fragment_isVisit", true);
            if (isVisit) {
                mRadioYes.setChecked(true);
                mRadioNo.setChecked(false);
                RelativeLayout relativeLayout = (RelativeLayout) getView().findViewById(R.id.relative_revisit_time);
                relativeLayout.setVisibility(View.VISIBLE);
            } else {
                mRadioNo.setChecked(true);
                mRadioYes.setChecked(false);
                RelativeLayout relativeLayout = (RelativeLayout) getView().findViewById(R.id.relative_revisit_time);
                relativeLayout.setVisibility(View.GONE);
            }
            if (visitTime != null) {
                mEditRevisitTime.setText(visitTime);
                mEditRevisitTime.setTextColor(getActivity().getResources().getColor(R.color.main_text_color));
                mRevisitTime.setCompoundDrawables(mWattingChanged, null, null, null);
            }
        }
    }

    private void initListener() {
        mSubButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PatrolBean reportData = new ReportData();
                if (mAddReportActivity != null) {
//                    private String company; //巡查单位
//                    private String developmentCompany;//	建设单位
//                    private String constructionCompany;//	施工单位
//                    private String supervisionCompany;//		监理单位
                    String address = mAddReportActivity.mEditAddress.getText().toString();
                    String company = mAddReportActivity.mEditCompany.getText().toString();
                    String type = mAddReportActivity.mTypes.getText().toString();
                    String developmentCompany = mAddReportActivity.mEditBuildCompany.getText().toString();
                    String constructionCompany = mAddReportActivity.mEditConsCompany.getText().toString();
                    String supervisionCompany = mAddReportActivity.mEditSuperCompany.getText().toString();
                    if (TextUtils.isEmpty(address) || TextUtils.isEmpty(company) || TextUtils.isEmpty(developmentCompany)
                            || TextUtils.isEmpty(constructionCompany) || TextUtils.isEmpty(supervisionCompany) || !mAddReportActivity.isSettedType) {
                        Toast.makeText(getActivity(), "还有未填写的数据", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    reportData.setAddress(address);
                    reportData.setCompany(company);
                    reportData.setDevelopmentCompany(developmentCompany);
                    reportData.setConstructionCompany(constructionCompany);
                    reportData.setSupervisionCompany(supervisionCompany);
                    reportData.setDate(DateUtils.format2.format(new Date()));
                    //
                    UserBean user = new UserBean();
                    reportData.setCreator(user);
                    //TODO type?
                    boolean visit = mRadioYes.isChecked();
                    String visitTime = mEditRevisitTime.getText().toString();
                    if (visit) {
                        reportData.setVisitDate(parseTime(visitTime));
                    }
                } else {
                    reportData = ((BaseActivity) getActivity()).getReportData();
                }
                String checkPeopleName = mEditName.getText().toString();
                String beginTime = mBeginTime.getText().toString();
                String endTime = mEndTime.getText().toString();
                String reportName = mEditReportName.getText().toString();
                String reportMsg = mEditReportMsg.getText().toString();
                boolean visit = mRadioYes.isChecked();
                String visitTime = mEditRevisitTime.getText().toString();
                Log.e(TAG, "visit:" + visit + ":" + visitTime);

                if (TextUtils.isEmpty(reportName) || TextUtils.isEmpty(checkPeopleName) || TextUtils.isEmpty(reportMsg) || TextUtils.isEmpty(reportName) ||
                        parseTime(beginTime) == null || parseTime(endTime) == null || (visit && parseTime(visitTime) == null)) {
                    Toast.makeText(getActivity(), "还有未填写的数据", Toast.LENGTH_SHORT).show();
                    return;
                }

                ReportBean reportBean = new ReportBean();
                reportBean.setPatrolUser(checkPeopleName);
                reportBean.setPatrolDateStart(parseTime(beginTime));
                reportBean.setPatrolDateEnd(parseTime(endTime));
                reportBean.setContent(reportMsg);
                reportBean.setCategory(1);
                reportBean.setName(reportName);
                reportBean.setDate(DateUtils.format2.format(new Date()));
                reportBean.setVisit(visit);
                reportData.setVisit(visit);
                if (visit) {
                    reportBean.setVisitDate(parseTime(visitTime));
                }
                new SubReport(mAddReportActivity != null, reportData, reportBean).execute();
                Toast.makeText(getActivity(), "提交巡查报告成功", Toast.LENGTH_SHORT).show();
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
        mBeginTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        mBeginTime.setText("" + year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                        mBeginDate = new ITime(year, monthOfYear + 1, dayOfMonth);
                        mBeginTime.setTextColor(mRes.getColor(R.color.main_text_color));
                        if (mBeginDate != null && mEndDate != null) {
                            mLabTime.setCompoundDrawables(mWattingChanged, null, null, null);
                        }
                    }
                }, mCal.get(Calendar.YEAR), mCal.get(Calendar.MONTH), mCal.get(Calendar.DAY_OF_MONTH));
                dialog.show();
            }

        });

        mEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        mEndTime.setText("" + year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                        mEndDate = new ITime(year, monthOfYear + 1, dayOfMonth);
                        mEndTime.setTextColor(mRes.getColor(R.color.main_text_color));
                        if (mBeginDate != null && mEndDate != null) {
                            mLabTime.setCompoundDrawables(mWattingChanged, null, null, null);
                        }
                    }
                }, mCal.get(Calendar.YEAR), mCal.get(Calendar.MONTH), mCal.get(Calendar.DAY_OF_MONTH));
                dialog.show();
            }

        });

        mEditRevisitTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
/*                final Calendar c = Calendar.getInstance();*/
                DatePickerDialog dialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        mEditRevisitTime.setText("" + year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                        mEditRevisitTime.setTextColor(mRes.getColor(R.color.main_text_color));
                        mRevisitDate = new ITime(year, monthOfYear + 1, dayOfMonth);
                        mRevisitTime.setCompoundDrawables(mWattingChanged, null, null, null);
                    }
                }, mCal.get(Calendar.YEAR), mCal.get(Calendar.MONTH), mCal.get(Calendar.DAY_OF_MONTH));
                dialog.show();
            }

        });
        mEditName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null && s.length() != 0) {
                    mName.setCompoundDrawables(mWattingChanged, null, null, null);
                } else {
                    mName.setCompoundDrawables(mWaittingAdd, null, null, null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mEditReportName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null && s.length() != 0) {
                    mReportName.setCompoundDrawables(mWattingChanged, null, null, null);
                } else {
                    mReportName.setCompoundDrawables(mWaittingAdd, null, null, null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mEditReportMsg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null && s.length() != 0) {
                    mReportMsg.setCompoundDrawables(mWattingChanged, null, null, null);
                } else {
                    mReportMsg.setCompoundDrawables(mWaittingAdd, null, null, null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private class SubReport extends AsyncTask<String, Integer, String> {
        private PatrolBean mReportData = null;
        private ReportBean mRevisitData = null;
        private boolean mIsAddReport = true;

        public SubReport(boolean isAddReport, PatrolBean data, ReportBean revisit) {
            mIsAddReport = isAddReport;
            mReportData = data;
            mRevisitData = revisit;
        }

        @Override
        protected String doInBackground(String... params) {
//            ArrayList<MessageBean> msgs = mHttpPost.getPatrolReportList("", "", "", "1");
            Log.e(TAG, "yanlog addReport");
            if (mIsAddReport) {
                PatrolBean reponse = mHttpPost.addPatrolReport(mReportData);
                mRevisitData.setPatrol(reponse);
                mRevisitData.setCreator(reponse.getCreator().getName());
            } else {
                mRevisitData.setPatrol(mReportData);
                mRevisitData.setCreator(mReportData.getCreator().getName());
            }
            mRevisitData.setDate(DateUtils.format2.format(new Date()));
            mRevisitData.setStatus(2);
            mHttpPost.addPatrolVisit(mRevisitData);
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            getActivity().finish();
        }
    }


    public void initGridView() {
        mAttachView = (GridView) getView().findViewById(R.id.grid_view);

        mData = new ArrayList<Object>();
        mData.add(R.drawable.attachment);
        //mAttachAdapter = new SimpleAdapter(getActivity(), mData, R.layout.add_attach_grid_item, new String[]{"image"}, new int[]{R.id.image});
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_ACTIVITY_ATTACH: {
                Log.e(TAG, "onActivityResult:" + data);
                if (resultCode == Activity.RESULT_OK) {
                    Uri uri = data.getData();
                    Log.e(TAG, "yanlog uri:" + uri);
                    if ("file".equalsIgnoreCase(uri.getScheme())) {//使用第三方应用打开
                        Toast.makeText(getActivity(), uri.getPath() + "11111", Toast.LENGTH_SHORT).show();
                        addAttach(uri.getPath(),uri.toString());
                        return;
                    }
                    String path = FilesUtils.getPath(getActivity(), uri);
                    Toast.makeText(getActivity(), path, Toast.LENGTH_SHORT).show();
                    addAttach(path,uri.toString());
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    //add files
    public void addAttach(String path,String uri) {
        Log.e(TAG, "yanlog remove begin size:" + mData.size());
        String formatPath = FilesUtils.getFormatString(path);
        Log.e(TAG, "yanlog remove begin size at0:" + mData.get(0));
        mData.remove(mData.size() - 1);
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

package com.isoftstone.smartsite.model.tripartite.fragment;

import android.app.DatePickerDialog;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
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
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.isoftstone.smartsite.R;
import com.isoftstone.smartsite.base.BaseActivity;
import com.isoftstone.smartsite.base.BaseFragment;
import com.isoftstone.smartsite.http.ReportBean;
import com.isoftstone.smartsite.model.tripartite.data.ITime;
import com.isoftstone.smartsite.utils.DateUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 点击验收后，下面验收的编辑框
 * Created by yanyongjun on 2017/10/30.
 */

public class CheckFragment extends BaseFragment {
    private GridView mAttachView = null;
    private SimpleAdapter mAttachAdapter = null;
    private ArrayList<Map<String, Object>> mData = null;

    private Resources mRes = null;
    private Drawable mWaittingAdd = null;
    private Drawable mWattingChanged = null;

    private TextView mLabRevisitTime = null;
    private TextView mEditRevisitTime = null;
    private EditText mEditContent = null;
    private TextView mLabCreator = null;
    private Button mBtnYes = null;
    private Button mBtnNo = null;
    private ITime mRevisitDate = null;

    private Calendar mCal = null;
    private BaseActivity mActivity = null;

    private RadioButton mRadioYes = null;
    private RadioButton mRadioNo = null;


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
                        mRevisitDate = new ITime(year, monthOfYear + 1, dayOfMonth);
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

    public void notifyDataSetChanged() {

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
        ReportBean reportBean = new ReportBean();
        reportBean.setPatrol(mActivity.getReportData());
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

    private class DealTask extends AsyncTask<Void, Void, Void> {
        private ReportBean mBean = null;

        public DealTask(ReportBean reportBean) {
            mBean = reportBean;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Log.e(TAG, "deal task begin");
            mHttpPost.addPatrolCheck(mBean);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            getActivity().finish();
        }
    }
}

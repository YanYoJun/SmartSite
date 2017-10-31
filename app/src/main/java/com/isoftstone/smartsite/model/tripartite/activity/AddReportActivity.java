package com.isoftstone.smartsite.model.tripartite.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListPopupWindow;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.isoftstone.smartsite.R;
import com.isoftstone.smartsite.base.BaseActivity;
import com.isoftstone.smartsite.model.tripartite.adapter.PopWindowListAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by yanyongjun on 2017/10/18.
 * 添加巡查报告界面
 */

public class AddReportActivity extends BaseActivity {
    private final static int REQUEST_ACTIVITY_ATTACH = 0;//请求图片的request code

    private final static int SPINNER_FLAG_ADDRESS = 0;
    private final static int SPINNER_FLAG_STATUS = 1;
    private final static int SPINNER_FLAG_TYPES = 2;

    private List<Uri> attach = new ArrayList<>();
    private SimpleAdapter mAttachAdapter = null;
    private ArrayList<Map<String, Object>> mData = null;
    private Resources mRes = null;
    private Drawable mWaittingAdd = null;
    private Drawable mWattingChanged = null;

    //the view in this activity
    private TextView mAddressSpinner = null;
    private TextView mStatusSpinner = null;
    private TextView mTypesSpinner = null;
    private TextView mAddress = null;
    private TextView mCompany = null;
    private TextView mStatus = null;
    private TextView mTypes = null;
    private TextView mBuildCompany = null;
    private TextView mConsCompany = null;
    private TextView mSuperCompany = null;
    private EditText mEditCompany = null;
    private EditText mEditBuildCompany = null;
    private EditText mEditConsCompany = null;
    private EditText mEditSuperCompany = null;


    @Override
    protected int getLayoutRes() {

        //TODO 这个界面还需要不少的润色
        return R.layout.activity_add_inspect_report;
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

        initView();
        initListener();
        //init Spinnner
        initSpinner();
        //initGridView();
    }

    public void initView() {
        mAddressSpinner = (TextView) findViewById(R.id.spinner_report_address);
        mStatusSpinner = (TextView) findViewById(R.id.spinner_report_status);
        mTypesSpinner = (TextView) findViewById(R.id.spinner_report_types);
        mAddress = (TextView) findViewById(R.id.lab_address);
        mCompany = (TextView) findViewById(R.id.lab_company);
        mStatus = (TextView) findViewById(R.id.lab_status);
        mTypes = (TextView) findViewById(R.id.lab_types);
        mBuildCompany = (TextView) findViewById(R.id.lab_build_company);
        mConsCompany = (TextView) findViewById(R.id.lab_cons_company);
        mSuperCompany = (TextView) findViewById(R.id.lab_super_company);

        mEditCompany = (EditText) findViewById(R.id.edit_company);
        mEditBuildCompany = (EditText) findViewById(R.id.edit_build_company);
        mEditConsCompany = (EditText) findViewById(R.id.edit_cons_company);
        mEditSuperCompany = (EditText) findViewById(R.id.edit_super_company);
    }

    public void initListener() {
        mEditCompany.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null && s.length() != 0) {
                    mCompany.setCompoundDrawables(mWattingChanged, null, null, null);
                } else {
                    mCompany.setCompoundDrawables(mWaittingAdd, null, null, null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mEditBuildCompany.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null && s.length() != 0) {
                    mBuildCompany.setCompoundDrawables(mWattingChanged, null, null, null);
                } else {
                    mBuildCompany.setCompoundDrawables(mWaittingAdd, null, null, null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mEditConsCompany.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null && s.length() != 0) {
                    mConsCompany.setCompoundDrawables(mWattingChanged, null, null, null);
                } else {
                    mConsCompany.setCompoundDrawables(mWaittingAdd, null, null, null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mEditSuperCompany.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null && s.length() != 0) {
                    mSuperCompany.setCompoundDrawables(mWattingChanged, null, null, null);
                } else {
                    mSuperCompany.setCompoundDrawables(mWaittingAdd, null, null, null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void initSpinner() {
        //address spinner

        ArrayList<String> strings = new ArrayList<String>();
        strings.add("未来科技城");
        strings.add("软件园");
        strings.add("花山新区");
        initSpinner(mAddressSpinner, strings, mAddress);

        //status spinner

        strings = new ArrayList<String>();
        strings.add("已处理");
        strings.add("已验收");
        strings.add("结束");
        initSpinner(mStatusSpinner, strings, mStatus);

        //tyes spinner

        strings = new ArrayList<String>();
        strings.add("工地报告");
        strings.add("啊啊报告");
        strings.add("验收报告");
        strings.add("验收报告2");
        initSpinner(mTypesSpinner, strings, mTypes);
    }

    /**
     * 初始化Spinner使用
     *
     * @param textView
     * @param strings
     */
    private void initSpinner(final TextView textView, final ArrayList<String> strings, final TextView tagView) {
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "initSpinner onclick");
                PopWindowListAdapter adapter = new PopWindowListAdapter(AddReportActivity.this, strings);
                final ListPopupWindow popupWindow = new ListPopupWindow(AddReportActivity.this);
                popupWindow.setAdapter(adapter);
                popupWindow.setAnchorView(textView);
                popupWindow.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
                popupWindow.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
                popupWindow.setModal(false);
                popupWindow.setAnimationStyle(0);
                popupWindow.setPromptView(null);
                popupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        tagView.setCompoundDrawables(mWattingChanged, null, null, null);
                        textView.setText(strings.get(position));
                        textView.setTextColor(getResources().getColor(R.color.main_text_color));
                        popupWindow.dismiss();
                    }
                });
                popupWindow.clearListSelection();
                popupWindow.show();
                textView.requestLayout();
            }
        });
    }

    /**
     * 点击保存按钮
     *
     * @param v
     */
    public void onBtnSaveClick(View v) {
        //TODO
    }

    /**
     * 点击返回按钮
     *
     * @param v
     */
    public void onBtnBackClick(View v) {
        finish();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_ACTIVITY_ATTACH: {
                Log.e(TAG, "onactivityresult:" + data.getData());
                break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void addImageAttach(Uri uri) {

    }

/*    public void onSpinnerReportAddressClicked(View v){
        final TextView textView = (TextView)v;




        popupWindow.show();
    }*/
}

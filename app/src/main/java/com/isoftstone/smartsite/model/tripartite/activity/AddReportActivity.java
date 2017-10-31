package com.isoftstone.smartsite.model.tripartite.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListPopupWindow;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.isoftstone.smartsite.R;
import com.isoftstone.smartsite.base.BaseActivity;
import com.isoftstone.smartsite.model.tripartite.adapter.PopWindowListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
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

    private ImageButton mAddAttach = null;
    private GridLayout mAttachTable = null;
    private List<Uri> attach = new ArrayList<>();
    private GridView mAttachView = null;
    private SimpleAdapter mAttachAdapter = null;
    private ArrayList<Map<String, Object>> mData = null;

    private TextView mAddressSpinner = null;
    private TextView mStatusSpinner = null;
    private TextView mTypesSpinner = null;


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
        //init Spinnner
        initSpinner();
        initGridView();
    }

    public void initGridView(){
        mAttachView = (GridView) findViewById(R.id.grid_view);

        mData = new ArrayList<Map<String, Object>>();
        Map<String, Object> data = new HashMap<>();
        data.put("image", R.drawable.attachment);
        mData.add(data);
        mAttachAdapter = new SimpleAdapter(this, mData, R.layout.add_attach_grid_item, new String[]{"image"}, new int[]{R.id.image});
        mAttachView.setAdapter(mAttachAdapter);

        mAttachView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == mData.size() - 1) {
                    //点击添加附件
                    Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                    i.setType("image/*");
                    startActivityForResult(i, REQUEST_ACTIVITY_ATTACH);
                }
            }
        });
    }

    private void initSpinner(){
        //address spinner
        mAddressSpinner = (TextView)findViewById(R.id.spinner_report_address);
        ArrayList<String> strings = new ArrayList<String>();
        strings.add("未来科技城");
        strings.add("软件园");
        strings.add("花山新区");
        initSpinner(mAddressSpinner,strings);

        //status spinner
        mStatusSpinner = (TextView)findViewById(R.id.spinner_report_status);
        strings = new ArrayList<String>();
        strings.add("已处理");
        strings.add("已验收");
        strings.add("结束");
        initSpinner(mStatusSpinner, strings);

        //tyes spinner
        mStatusSpinner = (TextView) findViewById(R.id.spinner_report_types);
        strings = new ArrayList<String>();
        strings.add("工地报告");
        strings.add("啊啊报告");
        strings.add("验收报告");
        initSpinner(mStatusSpinner, strings);
    }

    /**
     * 初始化Spinner使用
     * @param textView
     * @param strings
     */
    private void initSpinner(final TextView textView,final ArrayList<String> strings){
        PopWindowListAdapter adapter = new PopWindowListAdapter(this,strings);

        final ListPopupWindow popupWindow = new ListPopupWindow(this);
        popupWindow.setAdapter(adapter);
        popupWindow.setAnchorView(textView);
        popupWindow.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setModal(true);
        popupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                textView.setText(strings.get(position));
                textView.setTextColor(getResources().getColor(R.color.main_text_color));
                popupWindow.dismiss();
            }
        });

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.show();
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

package com.isoftstone.smartsite.model.tripartite.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.SimpleAdapter;

import com.isoftstone.smartsite.R;
import com.isoftstone.smartsite.base.BaseActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yanyongjun on 2017/10/18.
 * 添加巡查报告界面
 */

public class AddInspectReportActivity extends BaseActivity {
    private final static int REQUEST_ACTIVITY_ATTACH = 0;//请求图片的request code

    private ImageButton mAddAttach = null;
    private GridLayout mAttachTable = null;
    private List<Uri> attach = new ArrayList<>();
    private GridView mAttachView = null;
    private SimpleAdapter mAttachAdapter = null;
    private ArrayList<Map<String,Object>> mData = null;

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
/*        mAddAttach = (ImageButton) findViewById(R.id.btn_add_attach);
        mAttachTable = (GridLayout) findViewById(R.id.attach);*/
        mAttachView = (GridView)findViewById(R.id.grid_view);

        mData = new ArrayList<Map<String,Object>>();
        Map<String,Object> data = new HashMap<>();
        data.put("image",R.drawable.ic_stop);
        mData.add(data);
        mAttachAdapter = new SimpleAdapter(this,mData,R.layout.add_attach_grid_item,new String[]{"image"},new int[]{R.id.image});
        mAttachView.setAdapter(mAttachAdapter);

        mAttachView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == mData.size() -1){
                    //点击添加附件
                    Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                    i.setType("image/*");
                    startActivityForResult(i, REQUEST_ACTIVITY_ATTACH);
                }
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
                Log.e(TAG,"onactivityresult:"+data.getData());
                break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void addImageAttach(Uri uri){

    }
}

package com.isoftstone.smartsite.model.main.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Layout;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.isoftstone.smartsite.R;
import com.isoftstone.smartsite.http.DevicesBean;
import com.isoftstone.smartsite.http.HttpPost;
import com.isoftstone.smartsite.http.PMDevicesDataInfoBean;

import java.util.ArrayList;

/**
 * Created by gone on 2017/10/21.
 */

public class PMDevicesListActivity extends Activity{

    private ListView mListView = null;
    public static  final  int HANDLER_GET_DATA_START = 1;
    public static  final  int HANDLER_GET_DATA_END = 2;
    private HttpPost mHttpPost = new HttpPost();
    private ArrayList<DevicesBean> mList = null;
    private ImageButton mImageView_back = null;
    private ImageButton mImageView_serch = null;
    private View oneIconLayout = null;
    private View searchLayout = null;
    private TextView mtitleTextView = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pmdeviceslist);

        init();
        setOnCliceked();
        mHandler.sendEmptyMessage(HANDLER_GET_DATA_START);
    }

    private void init(){
        mImageView_back = (ImageButton)findViewById(R.id.btn_back);
        mImageView_serch = (ImageButton)findViewById(R.id.btn_icon);
        oneIconLayout = (View)findViewById(R.id.one_icon);
        searchLayout = (View)findViewById(R.id.serch);
        mtitleTextView = (TextView) findViewById(R.id.toolbar_title);

        mListView = (ListView)findViewById(R.id.listview);

        mtitleTextView.setText("设备列表");
        mImageView_serch.setImageResource(R.drawable.search);
    }

    private void setOnCliceked(){
        mImageView_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mImageView_serch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //oneIconLayout.setVisibility(View.GONE);
                //searchLayout.setVisibility(View.VISIBLE);
            }
        });
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case HANDLER_GET_DATA_START:{
                     Thread thread = new Thread(){
                         @Override
                         public void run() {
                             getDevices();
                         }
                     };
                     thread.start();
                }
                break;
                case  HANDLER_GET_DATA_END:{
                    setmListViewData();
                }
                break;
            }
        }
    };

    private  void getDevices(){
        mList = mHttpPost.getDevices("0","","","");
        mHandler.sendEmptyMessage(HANDLER_GET_DATA_END);
    }

    private void setmListViewData(){
        PMDevicesListAdapter adapter = new PMDevicesListAdapter(getBaseContext());
        adapter.setData(mList);
        mListView.setAdapter(adapter);
    }
}

package com.isoftstone.smartsite.model.main.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.Utils;
import com.isoftstone.smartsite.R;
import com.isoftstone.smartsite.http.DataQueryVoBean;
import com.isoftstone.smartsite.http.HttpPost;
import com.isoftstone.smartsite.http.PMDevicesDataBean;
import com.isoftstone.smartsite.http.PMDevicesDataInfoBean;
import com.isoftstone.smartsite.model.main.listener.OnConvertViewClickListener;
import com.isoftstone.smartsite.model.map.ui.VideoMonitorMapActivity;

import java.util.ArrayList;

/**
 * Created by gone on 2017/10/21.
 */

public class PMHistoryInfoActivity extends Activity {
    private ImageButton mImageView_back = null;
    private ImageButton mImageView_icon = null;
    private TextView toolbar_title = null;

    private ListView mListView = null;
    private TextView mDevicesName = null;
    private TextView mMap = null;
    private LinearLayout mGotoMap = null;
    public static  final  int HANDLER_GET_DATA_START = 1;
    public static  final  int HANDLER_GET_DATA_END = 2;
    private HttpPost mHttpPost = new HttpPost();
    ArrayList<DataQueryVoBean> list = null;
    private int devicesId ;
    private String address;
    private String[] data = {"5分钟","1小时","24小时","1个月"};
    private Spinner mJiangeSpinner;
    private String type = "1";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_pmhistoryinfo);
        devicesId = getIntent().getIntExtra("id",0);
        address = getIntent().getStringExtra("address");
        init();
        setOnCliceked();
        mHandler.sendEmptyMessage(HANDLER_GET_DATA_START);
    }

    private void init(){
        mImageView_back = (ImageButton)findViewById(R.id.btn_back);
        mImageView_icon = (ImageButton)findViewById(R.id.btn_icon);
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        toolbar_title.setText("历史数据");
        mImageView_icon.setVisibility(View.INVISIBLE);
        mImageView_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mImageView_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mListView = (ListView)findViewById(R.id.listview);
        mDevicesName = (TextView)findViewById(R.id.textView1);
        mMap = (TextView)findViewById(R.id.textView4);
        mMap.setText(address);
        mGotoMap = (LinearLayout)findViewById(R.id.gotomap);
        mJiangeSpinner = (Spinner)findViewById(R.id.jiange_name);
        ArrayAdapter adapter = new ArrayAdapter<String>(this,R.layout.spinner_item,data);
        mJiangeSpinner.setAdapter(adapter);
        mJiangeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mHandler.sendEmptyMessage(HANDLER_GET_DATA_START);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(list != null){
                    Intent intent = new Intent();
                    intent.setClass(getApplicationContext(),PMDataInfoActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("devicesbean",list.get(position));
                    getApplicationContext().startActivity(intent);
                }
            }
        });
    }

    private void setOnCliceked(){

        mGotoMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(),VideoMonitorMapActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(intent);
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

    private void getDevices(){
        int index = mJiangeSpinner.getSelectedItemPosition();
        list = mHttpPost.onePMDevicesDataList("["+devicesId+"]",(index+1)+"","","");
        mHandler.sendEmptyMessage(HANDLER_GET_DATA_END);
    }
    private void setmListViewData(){

        if(list != null && list.size() > 0){
            mDevicesName.setText(list.get(0).getDeviceName());
            PMHistoryinfoAdapter adapter = new PMHistoryinfoAdapter(getBaseContext());
            adapter.setData(list);
            mListView.setAdapter(adapter);
        }

    }

}

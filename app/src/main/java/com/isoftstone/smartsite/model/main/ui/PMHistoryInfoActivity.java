package com.isoftstone.smartsite.model.main.ui;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import com.isoftstone.smartsite.http.PMDevicesDataInfoBean;
import com.isoftstone.smartsite.model.main.listener.OnConvertViewClickListener;

import java.util.ArrayList;

/**
 * Created by gone on 2017/10/21.
 */

public class PMHistoryInfoActivity extends Activity {
    private ImageView mImageView_back = null;
    private ImageView mImageView_devices = null;
    private ListView mListView = null;
    private TextView mDevicesName = null;
    private TextView mMap = null;
    private LinearLayout mGotoMap = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_pmhistoryinfo);
        init();
        setOnCliceked();
        setData();
    }

    private void init(){
        mImageView_back = (ImageView)findViewById(R.id.image_back);
        mImageView_devices = (ImageView)findViewById(R.id.image_devices);
        mListView = (ListView)findViewById(R.id.listview);
        mDevicesName = (TextView)findViewById(R.id.textView1);
        mMap = (TextView)findViewById(R.id.textView4);
        mGotoMap = (LinearLayout)findViewById(R.id.gotomap);
    }

    private void setOnCliceked(){
        mImageView_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mImageView_devices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mGotoMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PMHistoryInfoActivity.this,"进入地图显示单个设备",2000).show();
            }
        });
    }

    private void setData(){

        mDevicesName.setText("dv5823");
        mMap.setText("高新大道53825号");

        PMDevicesDataInfoBean info = new PMDevicesDataInfoBean();
        info.setTime("2017-5-12");
        info.setPM10("25");
        info.setPM25("25");
        info.setO3("555");
        info.setNO2("25");
        ArrayList list = new ArrayList();
        list.add(info);
        list.add(info);
        list.add(info);
        list.add(info);
        list.add(info);
        PMHistoryinfoAdapter adapter = new PMHistoryinfoAdapter(getBaseContext());
        adapter.setData(list);
        mListView.setAdapter(adapter);
    }

}

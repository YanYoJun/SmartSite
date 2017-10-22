package com.isoftstone.smartsite.model.main.ui;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ListView;

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

import java.util.ArrayList;

/**
 * Created by gone on 2017/10/21.
 */

public class PMHistoryInfoActivity extends Activity {
    private ListView mListView = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_pmhistoryinfo);
        init();
        setData();
    }

    private void init(){
        mListView = (ListView)findViewById(R.id.listview);
    }

    private void setData(){
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

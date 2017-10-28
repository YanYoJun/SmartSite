package com.isoftstone.smartsite.model.main.ui;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.maps.model.Text;
import com.github.mikephil.charting.charts.LineChart;
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
import com.isoftstone.smartsite.model.main.listener.OnConvertViewClickListener;

import java.util.ArrayList;

/**
 * Created by gone on 2017/10/21.
 */

public class PMDataInfoActivity extends Activity {
    private LineChart mLineChart = null;
    private TextView mDevicesName = null;
    private TextView mMap = null;
    private ImageView mImageView_back = null;
    private ImageView mImageView_devices = null;
    private LinearLayout mGotoMap = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_pmdatainfo);
        init();
        setOnCliceked();
        setData();
        setLineChart();
    }

    private void init(){
        mImageView_back = (ImageView)findViewById(R.id.image_back);
        mImageView_devices = (ImageView)findViewById(R.id.image_devices);
        mLineChart = (LineChart)findViewById(R.id.chart3);
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
                Toast.makeText(PMDataInfoActivity.this,"进入地图显示单个设备",2000).show();
            }
        });
    }

    private void setData(){
        mDevicesName.setText("dv5823");
        mMap.setText("高新大道53825号");
    }

    private void setLineChart(){
        mLineChart.setDrawGridBackground(false);

        // no description text
        mLineChart.getDescription().setEnabled(false);

        // enable touch gestures
        mLineChart.setTouchEnabled(true);

        // enable scaling and dragging
        mLineChart.setDragEnabled(true);
        mLineChart.setScaleEnabled(true);
        // mChart.setScaleXEnabled(true);
        // mChart.setScaleYEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        mLineChart.setPinchZoom(true);
        mLineChart.setExtraOffsets(20,20,20,20);

        // set an alternative background color
        // mChart.setBackgroundColor(Color.GRAY);

        // create a custom MarkerView (extend MarkerView) and specify the layout
        // to use for it
        /*
        MyMarkerView mv = new MyMarkerView(this, R.layout.custom_marker_view);
        mv.setChartView(mChart); // For bounds control
        mLineChart.setMarker(mv); // Set the marker to the chart
        */

        // x-axis limit line
        LimitLine llXAxis = new LimitLine(10f, "Index 10");
        llXAxis.setLineWidth(4f);
        llXAxis.enableDashedLine(10f, 10f, 0f);
        llXAxis.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        llXAxis.setTextSize(10f);

        XAxis xAxis = mLineChart.getXAxis();
        xAxis.enableGridDashedLine(10f, 10f, 0f);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //xAxis.setValueFormatter(new MyCustomXAxisValueFormatter());
        //xAxis.addLimitLine(llXAxis); // add x-axis limit line


        //Typeface tf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");

        /*
        LimitLine ll1 = new LimitLine(150f, "Upper Limit");
        ll1.setLineWidth(4f);
        ll1.enableDashedLine(10f, 10f, 0f);
        ll1.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        ll1.setTextSize(10f);
        //ll1.setTypeface(tf);

        LimitLine ll2 = new LimitLine(-30f, "Lower Limit");
        ll2.setLineWidth(4f);
        ll2.enableDashedLine(10f, 10f, 0f);
        ll2.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        ll2.setTextSize(10f);
        //ll2.setTypeface(tf);
        */

        YAxis leftAxis = mLineChart.getAxisLeft();
        leftAxis.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines
        //leftAxis.addLimitLine(ll1);
        //leftAxis.addLimitLine(ll2);
        leftAxis.setAxisMaximum(50f);
        leftAxis.setAxisMinimum(0f);
        //leftAxis.setYOffset(20f);
        //leftAxis.setEnabled(false);
        leftAxis.enableGridDashedLine(10f, 10f, 0f);
        leftAxis.setDrawZeroLine(false);

        // limit lines are drawn behind data (and not on top)
        leftAxis.setDrawLimitLinesBehindData(false);

        mLineChart.getAxisRight().setEnabled(false);

        //mChart.getViewPortHandler().setMaximumScaleY(2f);
        //mChart.getViewPortHandler().setMaximumScaleX(2f);

        // add data
        setLineChartData(15, 30);
//        mChart.setVisibleXRange(20);
//        mChart.setVisibleYRange(20f, AxisDependency.LEFT);
//        mChart.centerViewTo(20, 50, AxisDependency.LEFT);

        mLineChart.animateX(2500);
        //mChart.invalidate();

        // get the legend (only possible after setting data)
        Legend l = mLineChart.getLegend();

        // modify the legend ...
        l.setForm(Legend.LegendForm.LINE);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        // // dont forget to refresh the drawing
        // mChart.invalidate();
    }

    private void setLineChartData(int count, float range) {

        ArrayList<Entry> values = new ArrayList<Entry>();

        for (int i = 0; i < count; i++) {

            float val = (float) (Math.random() * range) + 3;
            Entry entry = new Entry(i,val);
            values.add(entry);
        }



        ArrayList<Entry> values_2 = new ArrayList<Entry>();

        for (int i = 0; i < count; i++) {

            float val = (float) (Math.random() * range) + 3;
            Entry entry = new Entry(i,val);
            values_2.add(entry);
        }

        LineDataSet set2 = new LineDataSet(values_2, "DataSet 2");
        set2.setDrawIcons(false);
        // set the line to be drawn like this "- - - - - -"
        //set2.enableDashedLine(10f, 5f, 0f);//设置连线样式
        set2.setColor(Color.BLACK);
        set2.setCircleColor(Color.RED);
        set2.setDrawCircleHole(false);
        set2.setFormLineWidth(1f);
        set2.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
        set2.setFormSize(15.f);
        set2.setLineWidth(1f);//设置线宽
        set2.setCircleRadius(3f);//设置焦点圆心的大小
        set2.enableDashedHighlightLine(10f, 5f, 0f);//点击后的高亮线的显示样式
        set2.setHighlightLineWidth(2f);//设置点击交点后显示高亮线宽
        set2.setHighlightEnabled(true);//是否禁用点击高亮线
        set2.setHighLightColor(Color.RED);//设置点击交点后显示交高亮线的颜色
        set2.setValueTextSize(9f);//设置显示值的文字大小
        set2.setDrawFilled(false);//设置禁用范围背景填充

        if (Utils.getSDKInt() >= 18) {
            // fill drawable only supported on api level 18 and above
            //Drawable drawable = ContextCompat.getDrawable(this, R.drawable.fade_red);
            //set1.setFillDrawable(drawable);
        }
        else {
            set2.setFillColor(Color.BLACK);
        }

        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        dataSets.add(set2); // add the datasets

        // create a data object with the datasets
        LineData data = new LineData(dataSets);

        // set data
        mLineChart.setData(data);

    }
}

package com.isoftstone.smartsite.model.main.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.isoftstone.smartsite.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gone on 2017/10/17.
 * modifed by zhangyinfu on 2017/10/19
 */

public class AirMonitoringActivity extends Activity {
    private HorizontalBarChart mBarChart = null;
    private PieChart mPieChart = null;
    private LineChart mLineChart = null;
    private ImageView mImageView_back = null;
    private ImageView mImageView_devices = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_airmonitoring);
        init();
        setOnCliceked();
        setHorizontalBarChart();
        setPieChart();
        setLineChart();
    }
    private void init(){
        mImageView_back = (ImageView)findViewById(R.id.image_back);
        mImageView_devices = (ImageView)findViewById(R.id.image_devices);
        mBarChart = (HorizontalBarChart)findViewById(R.id.chart1);
        mPieChart = (PieChart)findViewById(R.id.chart2);
        mLineChart = (LineChart)findViewById(R.id.chart3);
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
                Intent intent = new Intent();
                intent.setClass(AirMonitoringActivity.this,PMDevicesListActivity.class);
                AirMonitoringActivity.this.startActivity(intent);
            }
        });
    }


    private void setHorizontalBarChart(){
        mBarChart.setDrawBarShadow(true);

        mBarChart.setDrawValueAboveBar(false);

        mBarChart.getDescription().setEnabled(false);

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        mBarChart.setMaxVisibleValueCount(60);

        // scaling can now only be done on x- and y-axis separately
        mBarChart.setPinchZoom(false);

        // draw shadows for each bar that show the maximum value
        // mChart.setDrawBarShadow(true);

        mBarChart.setDrawGridBackground(false);
        mBarChart.setExtraBottomOffset(10);
        mBarChart.setExtraLeftOffset(10);
        mBarChart.setExtraTopOffset(10);
        mBarChart.setExtraRightOffset(20);

        final String lable[]={"1","2","3","4","5"};
        XAxis xl = mBarChart.getXAxis();
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
        //xl.setTypeface(mTfLight);
        xl.setDrawAxisLine(false);
        xl.setDrawGridLines(false);
        xl.setGranularity(5f);
        xl.setLabelCount(lable.length);
        xl.setDrawLabels(true);
        //xl.setXOffset(30);
        xl.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return lable[(int)value/10];
            }
        });

        YAxis yl = mBarChart.getAxisLeft();
        //yl.setTypeface(mTfLight);
        yl.setDrawAxisLine(false);
        yl.setDrawTopYLabelEntry(false);
        yl.setEnabled(false);   //设置上边不划线
        yl.setDrawGridLines(false);
        yl.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        //yl.setInverted(true);

        YAxis yr = mBarChart.getAxisRight();
        //yr.setTypeface(mTfLight);
        yr.setDrawAxisLine(false);
        yr.setDrawGridLines(false);
        yr.setAxisMinimum(0f);
        yr.setEnabled(false);
        //yr.setInverted(true);

        setHorizontalBarChartData(5, 5);
        mBarChart.setFitBars(true);
        mBarChart.animateY(2500);
        mBarChart.setClickable(false);
        mBarChart.setFocusable(false);

        Legend l = mBarChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setFormSize(8f);
        l.setXEntrySpace(4f);
        l.setEnabled(false);  //设置不显示
    }

    private void setHorizontalBarChartData(int count, float range) {

        float barWidth = 2f;  //线条粗细
        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();

        BarEntry entry_1 = new BarEntry(0,10);
        BarEntry entry_2 = new BarEntry(10,8);
        BarEntry entry_3 = new BarEntry(20,6);
        BarEntry entry_4 = new BarEntry(30,5);
        BarEntry entry_5 = new BarEntry(40,2);
        yVals1.add(entry_1);
        yVals1.add(entry_2);
        yVals1.add(entry_3);
        yVals1.add(entry_4);
        yVals1.add(entry_5);
        BarDataSet set1;

        if (mBarChart.getData() != null &&
                mBarChart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet)mBarChart.getData().getDataSetByIndex(0);
            set1.setValues(yVals1);
            mBarChart.getData().notifyDataChanged();
            mBarChart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(yVals1, "DataSet 1");

            set1.setDrawIcons(false);

            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add(set1);

            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
            //data.setValueTypeface(mTfLight);
            data.setBarWidth(barWidth);
            data.setValueFormatter(new CustomerPercentFormatter(yVals1));
            mBarChart.setData(data);
        }
    }

    private void setPieChart(){
        mPieChart.setUsePercentValues(true);
        mPieChart.getDescription().setEnabled(false);
        mPieChart.setExtraOffsets(10, 30, 10, 30);

        mPieChart.setDragDecelerationFrictionCoef(0.95f);

        //mPieChart.setCenterTextTypeface(mTfLight);
        mPieChart.setCenterText("");

        mPieChart.setDrawHoleEnabled(true);
        mPieChart.setHoleColor(Color.WHITE);

        mPieChart.setTransparentCircleColor(Color.WHITE);
        mPieChart.setTransparentCircleAlpha(110);

        mPieChart.setHoleRadius(65f);
        mPieChart.setTransparentCircleRadius(65f);

        mPieChart.setDrawCenterText(true);

        mPieChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        mPieChart.setRotationEnabled(false);  //转动
        mPieChart.setHighlightPerTapEnabled(true);

        // mChart.setUnit(" €");
        // mChart.setDrawUnitsInChart(true);

        // add a selection listener
        //mPieChart.setOnChartValueSelectedListener(this);

        setPieChartData(4, 10);

        mPieChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        // mChart.spin(2000, 0, 360);


        Legend l = mPieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setTextSize(12);//文字颜色
        l.setFormSize(12);
        l.setFormToTextSpace(6); //图标和文字距离
        l.setXEntrySpace(20f);  //左右间隔
        l.setYEntrySpace(0f); //
        l.setXOffset(10f);  //距离左边距离
        l.setYOffset(16f); //距离底部距离

        // entry label styling
        mPieChart.setEntryLabelColor(Color.WHITE);//分区内部文字颜色
        mPieChart.setEntryLabelTextSize(12f);//分区内部文字大小
        mPieChart.setDrawEntryLabels(false); //分区不显示文字

        mPieChart.setClickable(false);
        mPieChart.setEnabled(false);


    }

    private void setPieChartData(int count, float range) {

        float mult = range;

        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        for (int i = 0; i < count ; i++) {

        }

        PieEntry entry_1 = new PieEntry(10);
        entry_1.setLabel("优");
        entry_1.setData("10天");
        entries.add(entry_1);
        PieEntry entry_2 = new PieEntry(20);
        entry_2.setLabel("良");
        entry_2.setData("20天");
        entries.add(entry_2);
        PieEntry entry_3 = new PieEntry(30);
        entry_3.setLabel("轻度污染");
        entry_3.setData("30天");
        entries.add(entry_3);
        PieEntry entry_4 = new PieEntry(40);
        entry_4.setLabel("重度污染");
        entry_4.setData("40天");
        entries.add(entry_4);

        PieDataSet dataSet = new PieDataSet(entries, "");

        dataSet.setDrawIcons(false);

        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(3f);

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<Integer>();

        colors.add((Integer)getColor(R.color.huanjin_you));
        colors.add((Integer)getColor(R.color.huanjin_liang));
        colors.add((Integer)getColor(R.color.huanjin_qingdu));
        colors.add((Integer)getColor(R.color.huanjin_zhongdu));
        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);

        dataSet.setValueLinePart1OffsetPercentage(75.f);
        dataSet.setValueLinePart1Length(0.2f);
        dataSet.setValueLinePart2Length(0.6f);
        dataSet.setValueTextColor(Color.RED);
        dataSet.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float v, Entry entry, int i, ViewPortHandler viewPortHandler) {
                return entry.getData().toString();
            }
        });
        //dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);

        PieData data = new PieData(dataSet);
        //data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(12f);
        data.setValueTextColor(Color.BLACK);
        //data.setValueTypeface(mTfLight);
        mPieChart.setData(data);

        // undo all highlights
        mPieChart.highlightValues(null);

        mPieChart.invalidate();
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

        LineDataSet set1 = new LineDataSet(values, "DataSet 1");
        set1.setDrawIcons(false);
        // set the line to be drawn like this "- - - - - -"
        set1.enableDashedLine(10f, 5f, 0f);
        set1.enableDashedHighlightLine(10f, 5f, 0f);
        set1.setColor(Color.BLACK);
        set1.setCircleColor(Color.BLACK);
        set1.setLineWidth(1f);
        set1.setCircleRadius(3f);
        set1.setDrawCircleHole(false);
        set1.setValueTextSize(9f);
        set1.setDrawFilled(false);
        set1.setFormLineWidth(1f);
        set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
        set1.setFormSize(15.f);

        if (Utils.getSDKInt() >= 18) {
            // fill drawable only supported on api level 18 and above
            //Drawable drawable = ContextCompat.getDrawable(this, R.drawable.fade_red);
            //set1.setFillDrawable(drawable);
        }
        else {
            set1.setFillColor(Color.BLACK);
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
        dataSets.add(set1); // add the datasets

        // create a data object with the datasets
        LineData data = new LineData(dataSets);

        // set data
        mLineChart.setData(data);

    }

    public class CustomerPercentFormatter implements IValueFormatter {

        protected DecimalFormat mFormat;
        protected ArrayList<BarEntry> mXVals;

        public CustomerPercentFormatter(ArrayList<BarEntry> data) {
            mFormat = new DecimalFormat("###,###,##0.0");
            mXVals = data;
        }

        /**
         * Allow a custom decimalformat
         *
         * @param format
         */
        public CustomerPercentFormatter(DecimalFormat format) {
            this.mFormat = format;
        }


        @Override
        public String getFormattedValue(float v, Entry entry, int i, ViewPortHandler viewPortHandler) {

            return "QIA" + " : "+mXVals.get(i).getX();
        }
    }
}

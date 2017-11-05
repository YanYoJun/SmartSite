package com.isoftstone.smartsite.model.main.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

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
import com.isoftstone.smartsite.http.EQIRankingBean;
import com.isoftstone.smartsite.http.HttpPost;
import com.isoftstone.smartsite.http.MonthlyComparisonBean;
import com.isoftstone.smartsite.http.WeatherConditionBean;
import com.isoftstone.smartsite.model.tripartite.view.MyListView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by gone on 2017/10/17.
 * modifed by zhangyinfu on 2017/10/19
 */

public class AirMonitoringActivity extends Activity {
    private HttpPost mHttpPost = new HttpPost();
    private String getEqiDataRankingTime;    //
    private String geteqiDataRankingarchid = "1";
    private String getWeatherDaysArchId;  //查询优良天气传参数
    private String getWeatherDaysTime;    //查询优良时间传参数
    private String getComparisonarchid;
    private String getComparisontime ;
    private EQIRankingBean mEQIRankingBean = null;
    private ArrayList<WeatherConditionBean> mWeatherList = null;
    private PieChart mPieChart = null;
    private LineChart mLineChart = null;
    private ImageView mImageView_back = null;
    private ImageView mImageView_devices = null;
    private TextView mRankTime = null;
    private TextView mYouliangTime = null;
    private TextView mTongqiTime = null;
    private Spinner mRankSpinner = null;
    private Spinner mTongqiSpinner = null;
    private Spinner mYouliangSpinner = null;

    public static  final  int HANDLER_GET_RANKING_START = 1;
    public static  final  int HANDLER_GET_RANKING_END = 2;
    public static  final  int HANDLER_GET_DAYS_PROPORTION_START = 3;//
    public static  final  int HANDLER_GET_DAYS_PROPORTION_END = 4;//
    public static  final  int HANDLER_GET_COMPARISON_START = 5;
    public static  final  int HANDLER_GET_COMPARISON_END = 6;
    private static final String[] m={"PM2.5","CO2","PM10","AQI"};
    private String address[];
    private MonthlyComparisonBean mMonthlyComparisonBean = null;
    private MyListView myListView = null;
    private TextView toolbar_title = null;

    private  TextView text_pushtime = null;
    private  TextView text_before = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_airmonitoring);
        init();
        setOnCliceked();
        mHandler.sendEmptyMessage(HANDLER_GET_RANKING_START);
    }
    private void init(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
        mRankTime = (TextView)findViewById(R.id.date);
        mRankTime.setText(df.format(new Date()));
        mRankSpinner = (Spinner)findViewById(R.id.name);
        ArrayAdapter adapter = new ArrayAdapter<String>(this,R.layout.spinner_item,m);
        mRankSpinner.setAdapter(adapter);
        mRankSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setHorizontalBarChart();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mYouliangTime = (TextView)findViewById(R.id.youliang_datedate);
        mYouliangTime.setText(df.format(new Date()));
        mYouliangSpinner = (Spinner)findViewById(R.id.youliang_name);
        mYouliangSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getWeatherDaysArchId = mEQIRankingBean.getArchs().get(position).getArchId();
                mHandler.sendEmptyMessage(HANDLER_GET_DAYS_PROPORTION_START);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        mTongqiTime = (TextView)findViewById(R.id.tongqi_date);
        mTongqiTime.setText(df.format(new Date()));
        mTongqiSpinner = (Spinner)findViewById(R.id.tongqi_name);
        mTongqiSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getComparisonarchid = mEQIRankingBean.getArchs().get(position).getArchId();
                mHandler.sendEmptyMessage(HANDLER_GET_COMPARISON_START);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        mImageView_back = (ImageView)findViewById(R.id.btn_back);
        mImageView_devices = (ImageView)findViewById(R.id.btn_icon);
        mPieChart = (PieChart)findViewById(R.id.chart2);
        mLineChart = (LineChart)findViewById(R.id.chart3);
        myListView = (MyListView) findViewById(R.id.lv);
        mImageView_devices.setImageResource(R.drawable.environmentlist);
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        toolbar_title.setText("环境监控");

        text_before = (TextView) findViewById(R.id.text_before);
        text_pushtime = (TextView) findViewById(R.id.text_pushtime);
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

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case HANDLER_GET_RANKING_START:{
                    Thread thread = new Thread(){
                        @Override
                        public void run() {
                            getEqiDataRankingTime = mRankTime.getText().toString();
                            Log.i("test",getEqiDataRankingTime+" "+geteqiDataRankingarchid);
                            mEQIRankingBean = mHttpPost.eqiDataRanking(geteqiDataRankingarchid,getEqiDataRankingTime);
                            mHandler.sendEmptyMessage(HANDLER_GET_RANKING_END);
                        }
                    };
                    thread.start();
                }
                break;
                case HANDLER_GET_RANKING_END:{
                    setHorizontalBarChart();
                    setSpinnerData();
                }
                break;
                case HANDLER_GET_DAYS_PROPORTION_START:{
                    Thread thread = new Thread(){
                        @Override
                        public void run() {
                            getWeatherDaysTime = mYouliangTime.getText().toString();
                            mWeatherList = mHttpPost.getWeatherConditionDay(getWeatherDaysArchId,getWeatherDaysTime);
                            mHandler.sendEmptyMessage(HANDLER_GET_DAYS_PROPORTION_END);
                        }
                    };
                    thread.start();
                }
                break;
                case HANDLER_GET_DAYS_PROPORTION_END:{
                     setPieChart();
                }
                break;
                case HANDLER_GET_COMPARISON_START:{
                    Thread thread = new Thread(){
                        @Override
                        public void run() {
                            getComparisontime = mTongqiTime.getText().toString();
                            mMonthlyComparisonBean = mHttpPost.carchMonthlyComparison(getComparisonarchid,getComparisontime,1+"");
                            mHandler.sendEmptyMessage(HANDLER_GET_COMPARISON_END);
                        }
                    };
                    thread.start();
                }
                break;
                case HANDLER_GET_COMPARISON_END:{
                    setLineChart();
                }
            }
        }
    };

    private void setSpinnerData(){
        if(mEQIRankingBean == null){
            return;
        }
        address = new String[mEQIRankingBean.getArchs().size()];
        for (int i = 0; i < mEQIRankingBean.getArchs().size() ;i ++){
            address[i] = mEQIRankingBean.getArchs().get(i).getArchName();
        }
        ArrayAdapter adapter_youliang = new ArrayAdapter<String>(this,R.layout.spinner_item,address);
        mYouliangSpinner.setAdapter(adapter_youliang);
        mYouliangSpinner.setSelection(0);
        ArrayAdapter adapter_tongqi = new ArrayAdapter<String>(this,R.layout.spinner_item,address);
        mTongqiSpinner.setAdapter(adapter_tongqi);
        mTongqiSpinner.setSelection(0);
    }

    private void setHorizontalBarChart(){
        if(mEQIRankingBean == null){
            return;
        }
        int index = mRankSpinner.getSelectedItemPosition();
        AirMonitoringRankAdapter adapter = new AirMonitoringRankAdapter(this);
        switch (index){
            case  0:
                adapter.setList(mEQIRankingBean.getPM2_5());
                break;
            case  1:
                adapter.setList(mEQIRankingBean.getCO2());
                break;
            case  2:
                adapter.setList(mEQIRankingBean.getPM10());
                break;
            case  3:
                adapter.setList(mEQIRankingBean.getAQI());
                break;
        }
        myListView.setAdapter(adapter);
    }

    private void setPieChart(){
        if(mWeatherList == null){
            return;
        }
        mPieChart.setUsePercentValues(true);
        mPieChart.getDescription().setEnabled(false);
        mPieChart.setExtraOffsets(40, 20, 40, 20);

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



        mPieChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        // mChart.spin(2000, 0, 360);


        Legend l = mPieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setTextSize(12);//文字颜色
        l.setFormSize(12);
        l.setFormToTextSpace(6); //图标和文字距离
        l.setXEntrySpace(20f);  //左右间隔
        l.setYEntrySpace(0f); //
        l.setXOffset(5f);  //距离左边距离
        l.setYOffset(5f); //距离底部距离

        // entry label styling
        mPieChart.setEntryLabelColor(Color.WHITE);//分区内部文字颜色
        mPieChart.setEntryLabelTextSize(12f);//分区内部文字大小
        mPieChart.setDrawEntryLabels(false); //分区不显示文字

        mPieChart.setClickable(false);
        mPieChart.setEnabled(false);


        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();
        for (int i = 0; i < mWeatherList.size(); i ++) {
            int value = mWeatherList.get(i).getValue()+5;
            PieEntry entry = new PieEntry(value);
            entry.setLabel(mWeatherList.get(i).getName());
            entry.setData(value+"天");
            entries.add(entry);
        }

        PieDataSet dataSet = new PieDataSet(entries, "");

        dataSet.setDrawIcons(false);

        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(3f);

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<Integer>();

        colors.add((Integer)getBaseContext().getColor(R.color.huanjin_you));
        colors.add((Integer)getBaseContext().getColor(R.color.huanjin_liang));
        colors.add((Integer)getBaseContext().getColor(R.color.huanjin_qingdu));
        colors.add((Integer)getBaseContext().getColor(R.color.huanjin_zhong1du));
        colors.add((Integer)getBaseContext().getColor(R.color.huanjin_zhongdu));
        colors.add((Integer)getBaseContext().getColor(R.color.huanjin_yanzhong));
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
        String time = mTongqiTime.getText().toString();
        String beforetime = time.replace("2017","2016");
        text_before.setText(beforetime);
        text_pushtime.setText(time);
        if(mMonthlyComparisonBean == null){
            return;
        }
        mLineChart.setDrawGridBackground(false);

        // no description text
        mLineChart.getDescription().setEnabled(false);

        // enable touch gestures
        mLineChart.setTouchEnabled(true);

        // enable scaling and dragging
        mLineChart.setDragEnabled(false);  //是否可以缩放
        mLineChart.setScaleEnabled(false);  //是否可以缩放
        // mChart.setScaleXEnabled(true);
        // mChart.setScaleYEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        mLineChart.setPinchZoom(true);
        mLineChart.setExtraOffsets(10,20,10,20);



        XAxis xAxis = mLineChart.getXAxis();
        xAxis.enableGridDashedLine(10f, 10f, 0f);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setAxisMaximum(31);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);


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
        leftAxis.setAxisMaximum(100f);
        leftAxis.setAxisMinimum(0f);
        //leftAxis.setYOffset(20f);
        //leftAxis.setEnabled(false);
        leftAxis.enableGridDashedLine(10f, 0f, 0f);
        leftAxis.setDrawZeroLine(false);

        // limit lines are drawn behind data (and not on top)
        leftAxis.setDrawLimitLinesBehindData(false);

        mLineChart.getAxisRight().setEnabled(false);

        //mChart.getViewPortHandler().setMaximumScaleY(2f);
        //mChart.getViewPortHandler().setMaximumScaleX(2f);

        // add data
//        mChart.setVisibleXRange(20);
//        mChart.setVisibleYRange(20f, AxisDependency.LEFT);
//        mChart.centerViewTo(20, 50, AxisDependency.LEFT);

        mLineChart.animateX(2500);
        //mChart.invalidate();

        // get the legend (only possible after setting data)
        Legend l = mLineChart.getLegend();
        l.setForm(Legend.LegendForm.LINE);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setEnabled(false);

        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        int beforeMonthSize = mMonthlyComparisonBean.getBeforeMonth().size();
        beforeMonthSize = 30;
        if(beforeMonthSize > 0){
            ArrayList<Entry> values = new ArrayList<Entry>();

            /*for (int i = 0; i < beforeMonthSize; i++) {
                String value = mMonthlyComparisonBean.getBeforeMonth().get(i).getAqi();
                Entry entry = new Entry(i,Float.parseFloat(value));
                values.add(entry);
            }*/
            for (int i = 0; i < 30; i++) {
                double val = (Math.random() * 88) + 3;
                Entry entry = new Entry(i,(float) val);
                values.add(entry);
            }

            LineDataSet set1 = new LineDataSet(values, "DataSet 1");
            set1.setDrawIcons(false);
            // set the line to be drawn like this "- - - - - -"
            //set1.enableDashedLine(10f, 10f, 0f);
            set1.setColor(Color.parseColor("#ff9e5d"));
            set1.setCircleColor(Color.parseColor("#ff9e5d"));
            set1.setLineWidth(1f);
            set1.setCircleRadius(4f);//设置焦点圆心的大小
            set1.setDrawCircleHole(true);
            set1.setCircleHoleRadius(2);
            set1.setCircleColorHole(Color.WHITE);
            set1.setValueTextSize(9f);
            set1.setDrawFilled(false);
            set1.setFormLineWidth(1f);
            set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 10f}, 0f));
            set1.setFormSize(15.f);
            set1.setDrawFilled(false);
            set1.setHighlightEnabled(false);

            if (Utils.getSDKInt() >= 18) {
                // fill drawable only supported on api level 18 and above
                //Drawable drawable = ContextCompat.getDrawable(this, R.drawable.fade_red);
                //set1.setFillDrawable(drawable);
            }
            else {
                set1.setFillColor(Color.BLACK);
            }
            dataSets.add(set1); // add the datasets
        }


        int pushtimeMonthSize = mMonthlyComparisonBean.getCurrentMonth().size();
        if(pushtimeMonthSize > 0){
            ArrayList<Entry> values_2 = new ArrayList<Entry>();

            /*for (int i = 0; i < pushtimeMonthSize; i++) {
                String value = mMonthlyComparisonBean.getCurrentMonth().get(i).getAqi();
                Entry entry = new Entry(i,Float.parseFloat(value));
                values_2.add(entry);
            }*/

            for (int i = 0; i < 30; i++) {
                double val = (Math.random() * 88) + 3;
                Entry entry = new Entry(i,(float) val);
                values_2.add(entry);
            }
            LineDataSet set2 = new LineDataSet(values_2, "DataSet 2");
            set2.setDrawIcons(false);
            // set the line to be drawn like this "- - - - - -"
            set2.enableDashedLine(10f, 0f, 0f);//设置连线样式
            set2.setColor(Color.parseColor("#599fff"));
            set2.setDrawCircleHole(false);
            set2.setFormLineWidth(1f);
            set2.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 10f}, 0f));
            set2.setFormSize(15.f);
            set2.setLineWidth(1f);//设置线宽
            set2.setCircleColor(Color.parseColor("#599fff"));
            set2.setCircleRadius(4f);//设置焦点圆心的大小
            set2.setDrawCircleHole(true);
            set2.setCircleHoleRadius(2);
            set2.setCircleColorHole(Color.WHITE);
            set2.enableDashedHighlightLine(10f, 5f, 0f);//点击后的高亮线的显示样式
            set2.setHighlightLineWidth(2f);//设置点击交点后显示高亮线宽
            set2.setHighlightEnabled(false);//是否禁用点击高亮线
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

            dataSets.add(set2); // add the datasets
        }
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
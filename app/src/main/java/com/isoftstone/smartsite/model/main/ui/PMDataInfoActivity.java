package com.isoftstone.smartsite.model.main.ui;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import com.google.gson.internal.bind.DateTypeAdapter;
import com.isoftstone.smartsite.R;
import com.isoftstone.smartsite.http.DataQueryVoBean;
import com.isoftstone.smartsite.http.HttpPost;
import com.isoftstone.smartsite.model.main.listener.OnConvertViewClickListener;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by gone on 2017/10/21.
 */

public class PMDataInfoActivity extends Activity {
    private HttpPost mHttpPsot = new HttpPost();
    private LineChart mLineChart = null;
    private TextView mDevicesName = null;
    private TextView mMap = null;
    private ImageView mImageView_back = null;
    private ImageView mImageView_devices = null;
    private LinearLayout mGotoMap = null;
    public static  final  int HANDLER_GET_DATA_START = 1;
    public static  final  int HANDLER_GET_DATA_END = 2;
    public static  final  int HANDLER_GET_24DATA_START = 3;
    public static  final  int HANDLER_GET_24DATA_END = 4;
    private String devicesId ;
    private String address ;
    private ArrayList<DataQueryVoBean> list = null;
    private ArrayList<DataQueryVoBean> list_24 = null;
    private TextView text_pm10 ;
    private TextView text_pm25 ;
    private TextView text_so2 ;
    private TextView text_no2 ;
    private TextView text_o3 ;
    private TextView text_co ;

    private TextView text_indoortemp ;
    private TextView text_windspeed ;
    private TextView text_winddirection ;
    private TextView text_airpressure ;
    private TextView text_temp ;
    private TextView text_humidity ;
    private TextView text_precipitation ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_pmdatainfo);
        devicesId = getIntent().getStringExtra("id");
        address = getIntent().getStringExtra("address");
        init();
        setOnCliceked();
        //setData();
        //setLineChart();
        mHandler.sendEmptyMessage(HANDLER_GET_DATA_START);
        mHandler.sendEmptyMessage(HANDLER_GET_24DATA_START);
    }

    private void init(){
        mImageView_back = (ImageView)findViewById(R.id.image_back);
        mImageView_devices = (ImageView)findViewById(R.id.image_devices);
        mLineChart = (LineChart)findViewById(R.id.chart3);

        mDevicesName = (TextView)findViewById(R.id.textView1);
        mMap = (TextView)findViewById(R.id.textView4);

        text_pm10 = (TextView)findViewById(R.id.text_pm10);
        text_pm25 = (TextView)findViewById(R.id.text_pm25);
        text_so2 = (TextView)findViewById(R.id.text_so2);
        text_no2 = (TextView)findViewById(R.id.text_no2);
        text_o3 = (TextView)findViewById(R.id.text_o3);
        text_co = (TextView)findViewById(R.id.text_co);

        text_indoortemp = (TextView)findViewById(R.id.text_indoortemp);
        text_windspeed = (TextView)findViewById(R.id.text_windspeed);
        text_winddirection = (TextView)findViewById(R.id.text_winddirection);
        text_airpressure = (TextView)findViewById(R.id.text_airpressure);
        text_temp = (TextView)findViewById(R.id.text_temp);
        text_humidity = (TextView)findViewById(R.id.text_humidity);
        text_precipitation = (TextView)findViewById(R.id.text_precipitation);



        mGotoMap = (LinearLayout)findViewById(R.id.gotomap);
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case HANDLER_GET_DATA_START:{
                    Thread thread = new Thread(){
                        @Override
                        public void run() {
                            getDataInfo();
                        }
                    };
                    thread.start();
                }
                break;
                case  HANDLER_GET_DATA_END:{
                    setData();
                }
                break;
                case HANDLER_GET_24DATA_START:{
                    Thread thread = new Thread(){
                        @Override
                        public void run() {
                            get24DataInfo();
                        }
                    };
                    thread.start();
                }
                break;
                case  HANDLER_GET_24DATA_END:{
                    setLineChart();
                }
                break;
            }
        }
    };

    private void getDataInfo(){
        list =  mHttpPsot.onePMDevicesDataList("["+devicesId+"]","0","","");
        mHandler.sendEmptyMessage(HANDLER_GET_DATA_END);
    }

    private void get24DataInfo(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        Calendar calendar = Calendar.getInstance();
        String startTime = df.format(calendar.getTime());
        calendar.add(Calendar.DATE, -1);
        String endTime = df.format(calendar.getTime());
        list_24 =  mHttpPsot.onePMDevicesDataList("["+devicesId+"]","2",startTime,endTime);
        //list_24 = mHttpPsot.onePMDevices24Data(""+devicesId,startTime);
        mHandler.sendEmptyMessage(HANDLER_GET_24DATA_END);
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
        if(address != null){
            mMap.setText(address);
        }
        if(list == null || list.size() <= 0){
            return;
        }
        DataQueryVoBean dataQueryVoBean = list.get(0);
        if(dataQueryVoBean == null){
            return;
        }
        mDevicesName.setText(dataQueryVoBean.getDeviceName());
        text_pm10.setText("PM10:"+dataQueryVoBean.getPm10()+"");
        text_pm25.setText("PM2.5:"+dataQueryVoBean.getPm2_5()+"");
        text_so2.setText("O2:"); ;
        text_no2.setText("no2:"); ;
        text_o3.setText("O3:"); ;
        text_co.setText("CO2:"+ dataQueryVoBean.getCo2()); ;

        DecimalFormat df = new DecimalFormat("#.0");
        text_indoortemp.setText("室内:");
        text_windspeed.setText("风速:"+String.format("%.2f",(dataQueryVoBean.getWindSpeed())));
        text_winddirection.setText("风向:"+dataQueryVoBean.getWindDirection());
        text_airpressure.setText("气压:"+String.format("%.1f",(dataQueryVoBean.getAtmosphericPressure())));
        text_temp.setText("温度:"+String.format("%.1f",(dataQueryVoBean.getAirTemperature())));
        text_humidity.setText("气压:"+String.format("%.1f",(dataQueryVoBean.getAirHumidity())));
        text_precipitation.setText("雨量:"+String.format("%.2f",(dataQueryVoBean.getRainfall())));

    }

    private void setLineChart(){
        if(list_24 == null || list_24.size() <= 0){
            return;
        }
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
        xAxis.setAxisMaximum(24);
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
        leftAxis.setAxisMaximum(100f);
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

        ArrayList<Entry> values = new ArrayList<Entry>();

        int count = list_24.size();
        for (int i = 0; i < count; i++) {

            Double value = list_24.get(i).getPm2_5();
            Entry entry = new Entry(i,Float.parseFloat(value.toString()));
            values.add(entry);
        }

        LineDataSet set2 = new LineDataSet(values, "DataSet 2");
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

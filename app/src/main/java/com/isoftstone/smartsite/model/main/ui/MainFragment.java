package com.isoftstone.smartsite.model.main.ui;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.constraint.solver.ArrayLinkedVariables;
import android.widget.ImageView;
import android.widget.TextView;

import com.isoftstone.smartsite.R;
import com.isoftstone.smartsite.base.BaseFragment;

import java.util.ArrayList;


/**
 * Created by zw on 2017/10/11.
 *
 *
 * 开发版SHA1 ： 17:02:19:67:57:D4:F4:AF:3E:AE:22:1F:95:65:9A:27:FD:F7:8D:D0
 *
 *             17:02:19:67:57:D4:F4:AF:3E:AE:22:1F:95:65:9A:27:FD:F7:8D:D0;com.isoftstone.smartsite
 *
 *
 * 发布版SHA1 ： C3:83:83:56:68:FD:2B:BC:EE:BB:16:AF:BA:52:EC:6A:C9:24:19:D5
 *
 *              C3:83:83:56:68:FD:2B:BC:EE:BB:16:AF:BA:52:EC:6A:C9:24:19:D5;com.isoftstone.smartsite
 */

public class MainFragment extends BaseFragment{

    private TextView mCityTestView = null;
    private TextView mWeatherTextView = null;
    private TextView mTemperatureTextView = null;
    private ImageView mWeatherImageView = null;
    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_main;
    }

    @Override
    protected void afterCreated(Bundle savedInstanceState) {
        initView();
    }

    private void initView(){
        mCityTestView = (TextView) rootView.findViewById(R.id.text_city);
        mWeatherTextView = (TextView) rootView.findViewById(R.id.text_weather);
        mTemperatureTextView = (TextView)  rootView.findViewById(R.id.text_temperature);

        mCityTestView.setText("city wuhan");
        mWeatherTextView.setText("weather  qingtian");
        mTemperatureTextView.setText("temperature 26");
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private String getCityName(){
        LocationManager  lm=(LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
        //List<String> names = lm.getAllProviders();//获取所有的位置提供者，一般三种

        //Criteria  criteria=new Criteria();//查询条件，如果设置了海拔，则定位方式只能是GPS;
        //criteria.setCostAllowed(true);//是否产生开销，比如流量费
       //String provider=lm.getBaseProvider(criteria,true);//获取最好的位置提供者，第二个参数为true，表示只获取那些被打开的位置提供者

        //lm.requestLocationUpdates(provier,0,0,new MyLocationListener(){});//获取位置。第二个参数表示每隔多少时间返回一次数据，第三个参数表示被定位的物体移动每次多少米返回一次数据。
        return "";
    }

    private class MyLocationListener implements LocationListener {
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onLocationChanged(Location location) {
            float accuracy = location.getAccuracy(); // 精确度
            double altitude = location.getAltitude(); // 海拔
            double latitude = location.getLatitude(); // 纬度
            double longitude = location.getLongitude(); // 经度
        }

        public void onProviderDisabled(String provider) {

        }
    }
}

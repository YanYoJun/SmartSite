package com.isoftstone.smartsite.model.map.ui;

import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;

import com.amap.api.maps.SupportMapFragment;
import com.amap.api.maps.TextureMapView;
import com.amap.api.maps.model.MyLocationStyle;


//import com.amap.api.maps2d.AMap;
//import com.amap.api.maps2d.CameraUpdateFactory;
//import com.amap.api.maps2d.MapView;
//import com.amap.api.maps2d.SupportMapFragment;
//import com.amap.api.maps2d.model.MyLocationStyle;

import com.isoftstone.smartsite.R;
import com.isoftstone.smartsite.base.BaseFragment;
import com.isoftstone.smartsite.utils.LogUtils;


/**
 * Created by zw on 2017/10/15.
 */

public class MapTestFragment extends BaseFragment {

    private TextureMapView mapView;
    private AMap mMap;


    private MyLocationStyle myLocationStyle;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_map_test;
    }

    @Override
    protected void afterCreated(Bundle savedInstanceState) {
        initView(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(mapView != null){
            mapView.onCreate(savedInstanceState);
            mMap = mapView.getMap();
        }

    }

    private void initView(Bundle savedInstanceState) {
        /*mMap = ((SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.fragment)).getMap();
        initLocation();*/

        mapView = (TextureMapView) rootView.findViewById(R.id.map_view);


    }

    private void initLocation(){
        //初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle = new MyLocationStyle();
        myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        mMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        //aMap.getUiSettings().setMyLocationButtonEnabled(true);设置默认定位按钮是否显示，非必需设置。
        mMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
        mMap.moveCamera(CameraUpdateFactory.zoomTo(15f));
    }

    @Override
    public void onResume() {
        super.onResume();
//        setUpMapIfNeeded();
        mapView.onResume();

        mMap.setOnMyLocationChangeListener(new AMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
//                myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER);
                mMap.setMyLocationStyle(myLocationStyle);
                mMap.setOnMyLocationChangeListener(null);
            }
        });
    }

    /*private void setUpMapIfNeeded() {
        if (mMap == null) {
            mMap = ((SupportMapFragment) getChildFragmentManager()
                    .findFragmentById(R.id.fragment)).getMap();
        }
    }*/

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LogUtils.i(TAG,"onDestroyView");
        mapView.onDestroy();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtils.i(TAG,"onDestroy");

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
        LogUtils.i(TAG,"onSaveInstanceState");
    }
}

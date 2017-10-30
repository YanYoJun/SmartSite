package com.isoftstone.smartsite.model.map.ui;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.isoftstone.smartsite.R;
import com.isoftstone.smartsite.base.BaseActivity;
import com.isoftstone.smartsite.utils.DensityUtils;

public class VideoMonitorMapActivity extends BaseActivity implements View.OnClickListener, AMap.OnMarkerClickListener {


    private MapView mMapView;
    private AMap aMap;

    private LatLng aotiLatLon = new LatLng(30.479736,114.476322);
    private PopupWindow mPopWindow;


    @Override
    protected int getLayoutRes() {
        return R.layout.activity_video_monitor_map;
    }

    @Override
    protected void afterCreated(Bundle savedInstanceState) {
        initView(savedInstanceState);
    }

    private void initView(Bundle savedInstanceState){
        TextView tv_title = (TextView) findViewById(R.id.toolbar_title);
        tv_title.setText("视频监控地图");

        findViewById(R.id.btn_back).setOnClickListener(this);

        mMapView = (MapView) findViewById(R.id.map_view);
        mMapView.onCreate(savedInstanceState);
        initMapView();
        initPopWindow();
    }

    private void initMapView(){
        aMap = mMapView.getMap();
        aMap.setOnMarkerClickListener(this);

        CameraUpdate update = CameraUpdateFactory.newCameraPosition(new CameraPosition(aotiLatLon,12f,0,0));
        aMap.moveCamera(update);

        initMarker();
    }

    private void initMarker(){
        MarkerOptions markerOption = new MarkerOptions();
        markerOption.position(aotiLatLon);
        markerOption.visible(true);

        markerOption.draggable(false);//设置Marker可拖动
            markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                    .decodeResource(getResources(),R.drawable.camera_gray)));

        // 将Marker设置为贴地显示，可以双指下拉地图查看效果
        markerOption.setFlat(true);//设置marker平贴地图效果

        Marker marker = aMap.addMarker(markerOption);
        marker.setAnchor(0.5f,1.2f);

        marker.setObject("center");

        View centerView = LayoutInflater.from(this).inflate(R.layout.layout_map_video_monitor_center,null);
        MarkerOptions markerOption1 = new MarkerOptions();
        markerOption1.position(aotiLatLon);
        markerOption1.visible(true);

        markerOption1.draggable(false);//设置Marker可拖动
        markerOption1.icon(BitmapDescriptorFactory.fromView(centerView));

        // 将Marker设置为贴地显示，可以双指下拉地图查看效果
        markerOption1.setFlat(true);//设置marker平贴地图效果

        Marker marker1 = aMap.addMarker(markerOption1);
        marker1.setAnchor(0.5f,0.5f);

    }

    private void initPopWindow(){
        View popWindowView = LayoutInflater.from(this).inflate(R.layout.layout_map_video_monitor_popwindow,null);

        mPopWindow = new PopupWindow(this);
        mPopWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        mPopWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopWindow.setContentView(popWindowView);
        mPopWindow.setOutsideTouchable(false);
        mPopWindow.setFocusable(true);
        mPopWindow.setTouchable(true);
        mPopWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_back:
                this.finish();
                break;
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if("center".equals(marker.getObject())){
            mPopWindow.showAtLocation(mMapView, Gravity.BOTTOM,0,DensityUtils.dip2px(this,-8));
        }
        return true;
    }
}

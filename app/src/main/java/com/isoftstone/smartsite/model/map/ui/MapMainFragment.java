package com.isoftstone.smartsite.model.map.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTabHost;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.TextureMapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.isoftstone.smartsite.MainActivity;
import com.isoftstone.smartsite.R;
import com.isoftstone.smartsite.base.BaseFragment;
import com.isoftstone.smartsite.model.map.adapter.ChooseCameraAdapter;
import com.isoftstone.smartsite.utils.DensityUtils;
import com.isoftstone.smartsite.utils.LogUtils;
import com.isoftstone.smartsite.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zw on 2017/10/14.
 */

public class MapMainFragment extends BaseFragment implements AMap.OnMarkerClickListener, View.OnClickListener {

    private TextureMapView mMapView;
    private AMap mAMap;
    private MyLocationStyle myLocationStyle;

    private List<Marker> markers = new ArrayList<>();
    private PopupWindow chooseCameraPopWindow;
    private TextView tvDeviceName;
    private TextView tvDeviceAddress;
    private TextView tvDeviceDate;
    private Button btnDeviceInfo;
    private Button btnDeviceCancel;
    private PopupWindow deviceInfoPopWindow;
    private FrameLayout mapContentView;
    private LatLng aotiLatLon;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_map_main;
    }

    @Override
    protected void afterCreated(Bundle savedInstanceState) {
        initView(savedInstanceState);
    }

    private void initView(Bundle savedInstanceState){

        initToolbar();

        initChooseCameraPopWindow();
        initDeviceInfoPopWindow();

        aotiLatLon = new LatLng(30.479736,114.476322);

        mapContentView = (FrameLayout) rootView.findViewById(R.id.map_content);

    }

    private void initToolbar(){
        TextView tv_title = (TextView) rootView.findViewById(R.id.toolbar_title);
        tv_title.setText("地图");

        rootView.findViewById(R.id.btn_back).setOnClickListener(this);
        ImageButton imageButton = (ImageButton) rootView.findViewById(R.id.btn_icon);
        imageButton.setImageResource(R.drawable.search);
        imageButton.setOnClickListener(this);
    }

    private void initChooseCameraPopWindow(){
        View chooseCameraView = LayoutInflater.from(mContext).inflate(R.layout.layout_pop_choose_camera,null);
        View chooseCameraHeader = LayoutInflater.from(mContext).inflate(R.layout.layout_choose_camera_head,null);

        ListView chooseCameraListView = (ListView) chooseCameraView.findViewById(R.id.lv);
        chooseCameraListView.setAdapter(new ChooseCameraAdapter(getActivity()));
        chooseCameraListView.addHeaderView(chooseCameraHeader,null,false);
        chooseCameraListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ToastUtils.showShort(((TextView)view).getText().toString());
                getActivity().startActivity(new Intent(getActivity(),VideoMonitorMapActivity.class));
                chooseCameraPopWindow.dismiss();
            }
        });

        chooseCameraPopWindow = new PopupWindow(getActivity());
        chooseCameraPopWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        chooseCameraPopWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        chooseCameraPopWindow.setContentView(chooseCameraView);
        chooseCameraPopWindow.setOutsideTouchable(false);
        chooseCameraPopWindow.setFocusable(true);
        chooseCameraPopWindow.setTouchable(true);
        chooseCameraPopWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

    }

    private void initDeviceInfoPopWindow(){
        View deviceInfoView = LayoutInflater.from(mContext).inflate(R.layout.layout_map_device_info,null);
        tvDeviceName = (TextView) deviceInfoView.findViewById(R.id.device_name);
        tvDeviceAddress = (TextView) deviceInfoView.findViewById(R.id.device_address);
        tvDeviceDate = (TextView) deviceInfoView.findViewById(R.id.device_date);
        btnDeviceInfo = (Button) deviceInfoView.findViewById(R.id.device_info);
        btnDeviceCancel = (Button) deviceInfoView.findViewById(R.id.device_cancel);
        btnDeviceCancel.setOnClickListener(this);
        btnDeviceInfo.setOnClickListener(this);


        deviceInfoPopWindow = new PopupWindow(getActivity());
        deviceInfoPopWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        deviceInfoPopWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        deviceInfoPopWindow.setContentView(deviceInfoView);

        deviceInfoPopWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        deviceInfoPopWindow.setOutsideTouchable(false);
        deviceInfoPopWindow.setFocusable(true);
        deviceInfoPopWindow.setTouchable(true);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mMapView = new TextureMapView(getActivity());
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        mMapView.setLayoutParams(params);
        mapContentView.addView(mMapView,0);

        mMapView.onCreate(savedInstanceState);
        mAMap = mMapView.getMap();
        mAMap.setOnMarkerClickListener(this);

        UiSettings settings = mAMap.getUiSettings();
        settings.setLogoBottomMargin(DensityUtils.dip2px(getActivity(),52));
        settings.setZoomControlsEnabled(false);

        initLocation();


    }

    private void initLocation(){
        /*//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle = new MyLocationStyle();
        myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        mAMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        //aMap.getUiSettings().setMyLocationButtonEnabled(true);设置默认定位按钮是否显示，非必需设置。
        mAMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
        mAMap.moveCamera(CameraUpdateFactory.zoomTo(15f));

        mAMap.setOnMyLocationChangeListener(new AMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER);
                mAMap.setMyLocationStyle(myLocationStyle);
                mAMap.setOnMyLocationChangeListener(null);
                lat = location.getLatitude();
                lon = location.getLongitude();
                initMarker();
            }
        });*/

        CameraUpdate update = CameraUpdateFactory.newCameraPosition(new CameraPosition(aotiLatLon,12f,0,0));
        mAMap.moveCamera(update);
        initMarker();

    }

    private void initMarker(){
        for (int i = 0 ;i < 5 ; i++){
            MarkerOptions markerOption = new MarkerOptions();
            markerOption.position(new LatLng(30.479736 + 0.0075  ,114.476322 + ((float)(i-2)/50)));
            markerOption.visible(true);

            markerOption.draggable(false);//设置Marker可拖动
            if(i == 0){
                markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                        .decodeResource(getResources(),R.drawable.camera_gray)));
            } else if(i == 1){
                markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                        .decodeResource(getResources(),R.drawable.camera_normal)));
            } else if(i == 2){
                markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                        .decodeResource(getResources(),R.drawable.camera_red)));
            } else if(i == 3){
                markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                        .decodeResource(getResources(),R.drawable.environment_orange)));
            } else if(i == 4){
                markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                        .decodeResource(getResources(),R.drawable.environment_pink)));
            }

            // 将Marker设置为贴地显示，可以双指下拉地图查看效果
            markerOption.setFlat(true);//设置marker平贴地图效果

            Marker marker = mAMap.addMarker(markerOption);

            marker.setObject(""+i);
            markers.add(marker);
        }

    }


    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mMapView.onDestroy();
        mapContentView.removeView(mMapView);
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
    public boolean onMarkerClick(Marker marker) {
        if(marker.getObject() != null){
            if(marker.getObject().equals("3")){
                tvDeviceName.setText("设备名称：130004 轻度污染");
                tvDeviceName.setTextColor(Color.BLACK);
                btnDeviceInfo.setClickable(true);
                btnDeviceInfo.setTextColor(Color.BLACK);
                deviceInfoPopWindow.showAtLocation(mMapView,Gravity.CENTER,0,0);
            }else if(marker.getObject().equals("4")){
                tvDeviceName.setText("设备名称：130004 污染严重");
                tvDeviceName.setTextColor(Color.RED);
                btnDeviceInfo.setClickable(false);
                btnDeviceInfo.setTextColor(Color.GRAY);
                deviceInfoPopWindow.showAtLocation(mMapView,Gravity.CENTER,0,0);
            } else {
                chooseCameraPopWindow.showAtLocation(mMapView,Gravity.CENTER,0,0);
            }
        }

        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.device_info:
                deviceInfoPopWindow.dismiss();
                ToastUtils.showShort("详细数据");
                break;
            case R.id.device_cancel:
                deviceInfoPopWindow.dismiss();
                break;
            case R.id.btn_back:
                FragmentTabHost tabHost = (FragmentTabHost) getActivity().findViewById(R.id.tab_host);
                tabHost.setCurrentTab(0);
                break;
            case R.id.btn_icon:
                startActivity(new Intent(getActivity(),MapSearchActivity.class));
                break;
        }
    }
}

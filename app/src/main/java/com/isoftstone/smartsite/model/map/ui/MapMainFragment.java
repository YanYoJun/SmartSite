package com.isoftstone.smartsite.model.map.ui;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.TextureMapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.isoftstone.smartsite.R;
import com.isoftstone.smartsite.base.BaseFragment;
import com.isoftstone.smartsite.model.map.adapter.ChooseCameraAdapter;
import com.isoftstone.smartsite.utils.LogUtils;
import com.isoftstone.smartsite.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zw on 2017/10/14.
 */

public class MapMainFragment extends BaseFragment implements AMap.OnMarkerClickListener {

    private TextureMapView mMapView;
    private AMap mAMap;
    private MyLocationStyle myLocationStyle;

    private double lat,lon;
    private List<Marker> markers = new ArrayList<>();
    private PopupWindow chooseCameraPopWindow;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_map_main;
    }

    @Override
    protected void afterCreated(Bundle savedInstanceState) {
        initView(savedInstanceState);

    }

    private void initView(Bundle savedInstanceState){
//        mMapView = (TextureMapView) rootView.findViewById(R.id.map_view);
        initPopWindow();

    }

    private void initPopWindow(){
        View chooseCameraView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_pop_choose_camera,null);
        View chooseCameraHeader = LayoutInflater.from(getActivity()).inflate(R.layout.layout_choose_camera_head,null);

        ListView chooseCameraListView = (ListView) chooseCameraView.findViewById(R.id.lv);
        chooseCameraListView.setAdapter(new ChooseCameraAdapter(getActivity()));
        chooseCameraListView.addHeaderView(chooseCameraHeader,null,false);
        chooseCameraListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ToastUtils.showShort(((TextView)view).getText().toString());
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

//        chooseCameraPopWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        /*//设置popupWindow消失时的监听
        chooseCameraPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            //在dismiss中恢复透明度
            public void onDismiss() {
                WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
                lp.alpha = 1f;
                getActivity().getWindow().setAttributes(lp);
            }
        });*/
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mMapView = new TextureMapView(getActivity());
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        mMapView.setLayoutParams(params);
        ((ViewGroup)rootView).addView(mMapView);

        mMapView.onCreate(savedInstanceState);
        mAMap = mMapView.getMap();
        mAMap.setOnMarkerClickListener(this);
        initLocation();


    }

    private void initLocation(){
        //初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle = new MyLocationStyle();
        myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        mAMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        //aMap.getUiSettings().setMyLocationButtonEnabled(true);设置默认定位按钮是否显示，非必需设置。
        mAMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
        mAMap.moveCamera(CameraUpdateFactory.zoomTo(15f));
    }

    private void initMarker(){
        for (int i = 0 ;i < 5 ; i++){
            MarkerOptions markerOption = new MarkerOptions();
            markerOption.position(new LatLng(lat + 0.0075  ,lon + ((float)(i-2)/200)));
            markerOption.visible(true);

            markerOption.draggable(false);//设置Marker可拖动
            if(i%2 == 0){
                markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                        .decodeResource(getResources(),R.mipmap.video_red)));
            } else {
                markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                        .decodeResource(getResources(),R.mipmap.video_black)));
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
        ((ViewGroup)rootView).removeView(mMapView);
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtils.i(TAG,"onResume");
        mMapView.onResume();
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
        });

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
        chooseCameraPopWindow.showAtLocation(mMapView,Gravity.CENTER,0,0);
        return true;
    }
}

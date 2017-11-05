package com.isoftstone.smartsite.model.map.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.isoftstone.smartsite.http.DevicesBean;
import com.isoftstone.smartsite.model.video.VideoPlayActivity;
import com.isoftstone.smartsite.model.video.VideoRePlayListActivity;
import com.isoftstone.smartsite.utils.DensityUtils;
import com.isoftstone.smartsite.utils.ToastUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class VideoMonitorMapActivity extends BaseActivity implements View.OnClickListener, AMap.OnMarkerClickListener {


    private MapView mMapView;
    private AMap aMap;

    private LatLng aotiLatLon = new LatLng(30.479736,114.476322);
    private PopupWindow mPopWindow;
    private List<DevicesBean> devices;
    private TextView tv_deviceNumber;
    private TextView tv_isOnline;
    private TextView tv_deviceTime;
    private TextView tv_deviceAddress;
    private View videoView;
    private ImageView iv_video;
    private TextView tv_video;
    private View historyView;
    private ImageView iv_history;
    private TextView tv_history;
    private View galleryView;
    private ImageView iv_gallery;
    private TextView tv_gallery;
    private DevicesBean currentDevice;
    private Marker roundMarker;


    @Override
    protected int getLayoutRes() {
        return R.layout.activity_video_monitor_map;
    }

    @Override
    protected void afterCreated(Bundle savedInstanceState) {
        devices = (ArrayList<DevicesBean>) getIntent().getSerializableExtra("devices");
        if(devices == null || devices.size() == 0 ){
            ToastUtils.showLong("没有获取到设备地址信息！");
        } /*else {
            aotiLatLon = new LatLng(Double.parseDouble(devices.get(0).getArch().getLatitude()),
                    Double.parseDouble(devices.get(0).getArch().getLongitude()));
        }*/
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

        if(devices != null && devices.size() != 0){
            initMarker();
        }

    }

    private void initMarker(){
        for (int i = 0; i < devices.size(); i++){
            DevicesBean device = devices.get(i);
            LatLng latLng = new LatLng(Double.parseDouble(device.getLatitude()),Double.parseDouble(device.getLongitude()));
            MarkerOptions markerOption = new MarkerOptions();
            markerOption.position(latLng);
            markerOption.visible(true);

            markerOption.draggable(false);//设置Marker可拖动

            //0在线，1离线，2故障
            if("0".equals(device.getDeviceStatus())){
                markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                        .decodeResource(getResources(),R.drawable.camera_normal)));
            }else if("1".equals(device.getDeviceStatus())){
                markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                        .decodeResource(getResources(),R.drawable.camera_gray)));
            } else  if("2".equals(device.getDeviceStatus())){
                markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                        .decodeResource(getResources(),R.drawable.camera_red)));
            }

            // 将Marker设置为贴地显示，可以双指下拉地图查看效果
            markerOption.setFlat(true);//设置marker平贴地图效果

            Marker marker = aMap.addMarker(markerOption);
            marker.setAnchor(0.5f,1.2f);

            marker.setObject(device);
        }
    }

    private void addAndRemoveRoundMarker(){
        MarkerOptions markerOption1 = new MarkerOptions();
        markerOption1.position(new LatLng(Double.parseDouble(currentDevice.getLatitude()
                               ), Double.parseDouble(currentDevice.getLongitude())));
        markerOption1.visible(true);

        markerOption1.draggable(false);//设置Marker可拖动
        View centerView = LayoutInflater.from(this).inflate(R.layout.layout_map_video_monitor_center,null);
        markerOption1.icon(BitmapDescriptorFactory.fromView(centerView));

        // 将Marker设置为贴地显示，可以双指下拉地图查看效果
        markerOption1.setFlat(true);//设置marker平贴地图效果

        roundMarker = aMap.addMarker(markerOption1);
        roundMarker.setAnchor(0.5f,0.5f);

    }

    private void initPopWindow(){
        View popWindowView = LayoutInflater.from(this).inflate(R.layout.layout_map_video_monitor_popwindow,null);
        popWindowView.findViewById(R.id.iv_dismiss).setOnClickListener(this);
        tv_deviceNumber = (TextView) popWindowView.findViewById(R.id.tv_device_number);
        tv_isOnline = (TextView) popWindowView.findViewById(R.id.tv_isonline);
        tv_deviceTime = (TextView) popWindowView.findViewById(R.id.tv_device_time);
        tv_deviceAddress = (TextView) popWindowView.findViewById(R.id.tv_address);

        videoView = popWindowView.findViewById(R.id.video);
        iv_video = (ImageView) popWindowView.findViewById(R.id.iv_video);
        tv_video = (TextView) popWindowView.findViewById(R.id.tv_video);
        historyView = popWindowView.findViewById(R.id.history);
        iv_history = (ImageView) popWindowView.findViewById(R.id.iv_history);
        tv_history = (TextView) popWindowView.findViewById(R.id.tv_history);
        galleryView = popWindowView.findViewById(R.id.gallery);
        iv_gallery = (ImageView) popWindowView.findViewById(R.id.iv_gallery);
        tv_gallery = (TextView) popWindowView.findViewById(R.id.tv_gallery);
        videoView.setOnClickListener(this);
        historyView.setOnClickListener(this);
        galleryView.setOnClickListener(this);


        mPopWindow = new PopupWindow(this);
        mPopWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        mPopWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopWindow.setContentView(popWindowView);
        mPopWindow.setOutsideTouchable(false);
        mPopWindow.setFocusable(true);
        mPopWindow.setTouchable(true);
        mPopWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if(roundMarker != null){
                    roundMarker.remove();
                }
            }
        });
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
            case R.id.iv_dismiss:
                mPopWindow.dismiss();
                break;
            case R.id.video:
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("resCode", currentDevice.getDeviceCoding());
                bundle.putInt("resSubType", currentDevice.getDeviceType());
                intent.putExtras(bundle);
                intent.setClass(this, VideoPlayActivity.class);
                startActivity(intent);
                break;
            case R.id.history:
                Date now = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                String beginTime = formatter.format(now) + " 00:00:00";
                String endTime = formatter.format(now) + " 23:59:59";

                Intent intent1 = new Intent();
                Bundle bundle1 = new Bundle();
                bundle1.putString("resCode", currentDevice.getDeviceCoding());
                bundle1.putInt("resSubType", currentDevice.getDeviceType());
                bundle1.putString("resName", currentDevice.getDeviceName());
                bundle1.putBoolean("isOnline", "0".equals(currentDevice.getDeviceStatus()));
                bundle1.putString("beginTime", beginTime);
                bundle1.putString("endTime", endTime);
                //Toast.makeText(mContext, "ViewHolder: " +  ((ViewHolder)rootView.getTag()).name.getText().toString(), Toast.LENGTH_SHORT).show();
                intent1.putExtras(bundle1);
                intent1.setClass(this, VideoRePlayListActivity.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                this.startActivity(intent1);
                break;
            case R.id.gallery:
                //打开系统相册浏览照片  
                Intent intent2 = new Intent(Intent.ACTION_VIEW, Uri.parse("content://media/internal/images/media"));
                intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent2);
                break;
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if(marker.getObject() != null){
            DevicesBean device = (DevicesBean) marker.getObject();
            currentDevice = device;
            tv_deviceNumber.setText(device.getDeviceId());
            if("0".equals(device.getDeviceStatus())){
                tv_isOnline.setText("在线");
                tv_isOnline.setBackgroundResource(R.drawable.shape_map_online);
            } else if("1".equals(device.getDeviceStatus())){
                tv_isOnline.setText("离线");
                tv_isOnline.setBackgroundResource(R.drawable.shape_offline);
            } else if("2".equals(device.getDeviceStatus())){
                tv_isOnline.setText("故障");
                tv_isOnline.setBackgroundResource(R.drawable.shape_map_bad);
            }
            tv_deviceTime.setText("安装日期：" + device.getInstallTime().substring(0,10));
            tv_deviceAddress.setText(device.getArch().getName());
            if("0".equals(device.getDeviceStatus())){
                videoView.setClickable(true);
                videoView.setEnabled(true);
                historyView.setClickable(true);
                historyView.setEnabled(true);
                galleryView.setClickable(true);
                galleryView.setEnabled(true);
                iv_video.setImageResource(R.drawable.time);
                iv_history.setImageResource(R.drawable.history);
                iv_gallery.setImageResource(R.drawable.capture);
                tv_video.setTextColor(getResources().getColor(R.color.colorPrimary));
                tv_history.setTextColor(getResources().getColor(R.color.colorPrimary));
                tv_gallery.setTextColor(getResources().getColor(R.color.colorPrimary));
            } else {
                videoView.setClickable(false);
                videoView.setEnabled(false);
                historyView.setClickable(false);
                historyView.setEnabled(false);
                galleryView.setClickable(false);
                galleryView.setEnabled(false);
                iv_video.setImageResource(R.drawable.time);
                iv_history.setImageResource(R.drawable.history);
                iv_gallery.setImageResource(R.drawable.capture);
                tv_video.setTextColor(getResources().getColor(R.color.gray_9999));
                tv_history.setTextColor(getResources().getColor(R.color.gray_9999));
                tv_gallery.setTextColor(getResources().getColor(R.color.gray_9999));
            }
            addAndRemoveRoundMarker();

            mPopWindow.showAtLocation(mMapView, Gravity.BOTTOM,0,DensityUtils.dip2px(this,-8));
        }
        return true;
    }
}

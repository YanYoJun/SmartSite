package com.isoftstone.smartsite.model.main.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.isoftstone.smartsite.R;
import com.isoftstone.smartsite.http.DevicesBean;
import com.isoftstone.smartsite.http.PMDevicesDataInfoBean;
import com.isoftstone.smartsite.http.VideoMonitorBean;
import com.isoftstone.smartsite.model.main.listener.OnConvertViewClickListener;
import com.isoftstone.smartsite.model.map.ui.VideoMonitorMapActivity;
import com.isoftstone.smartsite.model.video.VideoPlayActivity;
import com.isoftstone.smartsite.model.video.VideoRePlayActivity;
import com.isoftstone.smartsite.utils.ToastUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by gone on 2017/10/16.
 * modifed by zhangyinfu on 2017/10/19
 * modifed by zhangyinfu on 2017/10/20
 * modifed by zhangyinfu on 2017/10/21
 */

public class PMDevicesListAdapter extends BaseAdapter {


    private LayoutInflater mInflater;
    private ArrayList<DevicesBean> mData = new ArrayList<DevicesBean>();
    private Context mContext = null;
    private final String IMAGE_TYPE = "image/*";

    public PMDevicesListAdapter(Context context){
        this.mInflater = LayoutInflater.from(context);
        mContext = context;
    }

    public void setData(ArrayList<DevicesBean> list){
        mData = list;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if (null == convertView) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.pmdeviceslist_adapter, null);
            holder.resName = (TextView)convertView.findViewById(R.id.textView1);
            holder.isOnline = (TextView)convertView.findViewById(R.id.textView2);
            holder.installTime = (TextView)convertView.findViewById(R.id.textView3);
            holder.address = (TextView)convertView.findViewById(R.id.textView4);
            holder.PM10 = (TextView)convertView.findViewById(R.id.textView5);
            holder.PM25 = (TextView)convertView.findViewById(R.id.textView6);
            holder.SO2 = (TextView)convertView.findViewById(R.id.textView7);
            holder.NO2 = (TextView)convertView.findViewById(R.id.textView8);
            holder.button_1 = (LinearLayout)convertView.findViewById(R.id.button1);
            holder.button_2 = (LinearLayout)convertView.findViewById(R.id.button2);
            holder.gotomap = (LinearLayout) convertView.findViewById(R.id.gotomap);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder)convertView.getTag();
        }
        final  DevicesBean devices = mData.get(position);
        holder.resName.setText(devices.getDeviceName());
        TextPaint paint = holder.resName.getPaint();
        paint.setFakeBoldText(true);
        if(devices.getDeviceStatus().equals("1")){
            holder.isOnline.setBackground(mContext.getResources().getDrawable(R.drawable.online));
        }else{
            holder.isOnline.setBackground(mContext.getResources().getDrawable(R.drawable.offline));
        }
        holder.installTime.setText("安装时间: "+devices.getInstallTime());
        holder.address.setText("地址: "+devices.getArch().getName());
        //holder.PM10.setText("PM10 "+devices);
        //holder.PM25.setText("PM2.5 "+devices.getPM25());
        //holder.SO2.setText("SO2 "+devices.getO3());
        //holder.NO2.setText("NO2 "+devices.getNO2());
        holder.button_1.setOnClickListener(new OnConvertViewClickListener(convertView, position) {

            @Override
            public void onClickCallBack(View registedView, View rootView, int position) {
                //Toast.makeText(mContext, "ViewHolder: " +  ((ViewHolder)rootView.getTag()).toString(), Toast.LENGTH_SHORT).show();
                ViewHolder viewHolder = (ViewHolder)rootView.getTag();
                if(null != viewHolder) {
                    //实时数据
                    Intent intent = new Intent();
                    intent.putExtra("id",devices.getDeviceId());
                    intent.putExtra("address",devices.getArch().getName());
                    intent.setClass(mContext, PMDataInfoActivity.class);
                    mContext.startActivity(intent);
                } else {
                    Toast.makeText(mContext, "errorException:  ViewHolder is null", Toast.LENGTH_SHORT).show();
                }
            }
        });

        holder.button_2.setOnClickListener(new OnConvertViewClickListener(convertView, position) {
            @Override
            public void onClickCallBack(View registedView, View rootView, int position) {
                ViewHolder viewHolder = (ViewHolder)rootView.getTag();

                if(null != viewHolder) {
                    //历史数据
                    Intent intent = new Intent();
                    intent.setClass(mContext, PMHistoryInfoActivity.class);
                    intent.putExtra("id",devices.getDeviceId());
                    intent.putExtra("address",devices.getArch().getName());
                    mContext.startActivity(intent);
                } else {
                    Toast.makeText(mContext, "errorException:  ViewHolder is null", Toast.LENGTH_SHORT).show();
                }
            }
        });

        holder.gotomap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到地图
                Intent intent = new Intent();
                intent.setClass(mContext,VideoMonitorMapActivity.class);
                mContext.startActivity(intent);
            }
        });
        return convertView;
    }


    public final class ViewHolder{
        public TextView resName;//资源名称
        public TextView isOnline;//是否在线
        public TextView  installTime;//安装日期
        public TextView  address;//安装日期
        public TextView  PM10;//PM10
        public TextView  PM25;//PM2.5
        public TextView  SO2;//SO2
        public TextView  NO2;//NO2

        public LinearLayout button_1;//实时数据
        public LinearLayout button_2;//历史数据

        public LinearLayout gotomap ; //跳转到地图
    }


}

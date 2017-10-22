package com.isoftstone.smartsite.model.main.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.isoftstone.smartsite.R;
import com.isoftstone.smartsite.http.PMDevicesDataInfoBean;
import com.isoftstone.smartsite.http.VideoMonitorBean;
import com.isoftstone.smartsite.model.main.listener.OnConvertViewClickListener;
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
    private ArrayList<PMDevicesDataInfoBean> mData = new ArrayList<PMDevicesDataInfoBean>();
    private Context mContext = null;
    private final String IMAGE_TYPE = "image/*";

    public PMDevicesListAdapter(Context context){
        this.mInflater = LayoutInflater.from(context);
        mContext = context;
    }

    public void setData(ArrayList<PMDevicesDataInfoBean> list){
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
            holder.button_1 = (Button)convertView.findViewById(R.id.button1);
            holder.button_2 = (Button)convertView.findViewById(R.id.button2);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder)convertView.getTag();
        }

        holder.resName.setText("设备名称"+mData.get(position).getName());
        if(mData.get(position).getState() == 1){
            holder.isOnline.setText("在线");
        }else{
            holder.isOnline.setText("离线");
        }
        holder.installTime.setText("安装时间 "+mData.get(position).getTime());
        holder.address.setText("地址 "+mData.get(position).getAddress());
        holder.PM10.setText("PM10 "+mData.get(position).getPM10());
        holder.PM25.setText("PM2.5 "+mData.get(position).getPM25());
        holder.SO2.setText("SO2 "+mData.get(position).getO3());
        holder.NO2.setText("NO2 "+mData.get(position).getNO2());
        holder.button_1.setOnClickListener(new OnConvertViewClickListener(convertView, position) {

            @Override
            public void onClickCallBack(View registedView, View rootView) {
                //Toast.makeText(mContext, "ViewHolder: " +  ((ViewHolder)rootView.getTag()).toString(), Toast.LENGTH_SHORT).show();
                ViewHolder viewHolder = (ViewHolder)rootView.getTag();
                if(null != viewHolder) {
                    //实时数据
                    Intent intent = new Intent();
                    //Bundle bundle = new Bundle();
                    //bundle.putString("resCode", viewHolder.resCode.getText().toString());
                    //Toast.makeText(mContext, "ViewHolder: " +  ((ViewHolder)rootView.getTag()).name.getText().toString(), Toast.LENGTH_SHORT).show();
                    //intent.putExtras(bundle);
                    intent.setClass(mContext, PMDataInfoActivity.class);
                    mContext.startActivity(intent);
                } else {
                    Toast.makeText(mContext, "errorException:  ViewHolder is null", Toast.LENGTH_SHORT).show();
                }
            }
        });

        holder.button_2.setOnClickListener(new OnConvertViewClickListener(convertView, position) {
            @Override
            public void onClickCallBack(View registedView, View rootView) {
                ViewHolder viewHolder = (ViewHolder)rootView.getTag();

                if(null != viewHolder) {
                    //历史数据
                    Intent intent = new Intent();
                    intent.setClass(mContext, PMHistoryInfoActivity.class);
                    mContext.startActivity(intent);
                } else {
                    Toast.makeText(mContext, "errorException:  ViewHolder is null", Toast.LENGTH_SHORT).show();
                }
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

        public Button button_1;//实时数据
        public Button button_2;//历史数据
    }


}

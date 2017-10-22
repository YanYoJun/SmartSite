package com.isoftstone.smartsite.model.main.ui;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.isoftstone.smartsite.R;
import com.isoftstone.smartsite.http.PMDevicesDataInfoBean;
import com.isoftstone.smartsite.model.main.listener.OnConvertViewClickListener;

import java.util.ArrayList;

/**
 * Created by gone on 2017/10/16.
 * modifed by zhangyinfu on 2017/10/19
 * modifed by zhangyinfu on 2017/10/20
 * modifed by zhangyinfu on 2017/10/21
 */

public class PMHistoryinfoAdapter extends BaseAdapter {


    private LayoutInflater mInflater;
    private ArrayList<PMDevicesDataInfoBean> mData = new ArrayList<PMDevicesDataInfoBean>();
    private Context mContext = null;

    public PMHistoryinfoAdapter(Context context){
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
            convertView = mInflater.inflate(R.layout.pmhistoryinfo_adapter, null);
            holder.time = (TextView)convertView.findViewById(R.id.text_1);
            holder.PM10 = (TextView)convertView.findViewById(R.id.text_2);
            holder.PM25 = (TextView)convertView.findViewById(R.id.text_3);
            holder.SO2 = (TextView)convertView.findViewById(R.id.text_4);
            holder.NO2 = (TextView)convertView.findViewById(R.id.text_5);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder)convertView.getTag();
        }
        holder.time.setText("安装时间 "+mData.get(position).getTime());
        holder.PM10.setText("PM10 "+mData.get(position).getPM10());
        holder.PM25.setText("PM2.5 "+mData.get(position).getPM25());
        holder.SO2.setText("SO2 "+mData.get(position).getO3());
        holder.NO2.setText("NO2 "+mData.get(position).getNO2());
        return convertView;
    }


    public final class ViewHolder{
        public TextView time;//资源名称
        public TextView  PM10;//PM10
        public TextView  PM25;//PM2.5
        public TextView  SO2;//SO2
        public TextView  NO2;//NO2
    }


}

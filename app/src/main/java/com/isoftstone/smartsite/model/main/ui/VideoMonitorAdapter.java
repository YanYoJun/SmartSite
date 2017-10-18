package com.isoftstone.smartsite.model.main.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.isoftstone.smartsite.R;
import com.isoftstone.smartsite.http.MessageListBean;
import com.isoftstone.smartsite.http.VideoMonitorBean;
import com.isoftstone.smartsite.model.video.VideoPlayActivity;

import java.util.ArrayList;

/**
 * Created by gone on 2017/10/16.
 * modifed by zhangyinfu on 2017/10/19
 */

public class VideoMonitorAdapter extends BaseAdapter {


    private LayoutInflater mInflater;
    private ArrayList<VideoMonitorBean> mData = new ArrayList<VideoMonitorBean>();
    private Context mContext = null;
    public VideoMonitorAdapter(Context context){
        this.mInflater = LayoutInflater.from(context);
        mContext = context;
    }

    public void setData(ArrayList<VideoMonitorBean> list){
        mData = list;
    }


    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mData.size();
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return mData.get(arg0);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if (convertView == null) {
            holder=new ViewHolder();
            convertView = mInflater.inflate(R.layout.videomonitor_adapter, null);
            holder.name = (TextView)convertView.findViewById(R.id.textView_1);
            holder.time = (TextView)convertView.findViewById(R.id.textView_2);
            holder.address = (TextView)convertView.findViewById(R.id.textView_3);
            holder.state = (TextView)convertView.findViewById(R.id.textView_4);
            holder.button_1 = (Button)convertView.findViewById(R.id.button_1);
            holder.button_2 = (Button)convertView.findViewById(R.id.button_2);
            holder.button_3 = (Button)convertView.findViewById(R.id.button_3);

            convertView.setTag(holder);
        }else {

            holder = (ViewHolder)convertView.getTag();
        }

        holder.name.setText("设备名字："+mData.get(position).getDevicename());
        holder.time.setText("安装时间"+mData.get(position).getInstarltime());
        holder.address.setText("地址："+mData.get(position).getAddress());
        if(mData.get(position).getState()){
            holder.state.setText("在线:在线");
        }else {
            holder.state.setText("在线:离线");
        }

        final ViewHolder finalHolder = holder;
        Log.i("zyf_test","---------------------" + finalHolder.name.getText().toString());
        holder.button_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("ResCode", "123456_1");
                intent.putExtras(bundle);
                intent.setClass(mContext, VideoPlayActivity.class);
                mContext.startActivity(intent);
            }
        });
        holder.button_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        holder.button_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return convertView;
    }

    public final class ViewHolder{
        public TextView name;
        public TextView time;
        public TextView address;
        public TextView state;
        public Button button_1;
        public Button button_2;
        public Button button_3;

        public TextView getName() {
            return name;
        }

        public void setName(TextView name) {
            this.name = name;
        }

        public TextView getTime() {
            return time;
        }

        public void setTime(TextView time) {
            this.time = time;
        }

        public TextView getAddress() {
            return address;
        }

        public void setAddress(TextView address) {
            this.address = address;
        }

        public TextView getState() {
            return state;
        }

        public void setState(TextView state) {
            this.state = state;
        }
    }
}

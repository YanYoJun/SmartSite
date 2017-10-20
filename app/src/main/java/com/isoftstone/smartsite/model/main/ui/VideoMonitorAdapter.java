package com.isoftstone.smartsite.model.main.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.isoftstone.smartsite.R;
import com.isoftstone.smartsite.http.MessageListBean;
import com.isoftstone.smartsite.http.VideoMonitorBean;
import com.isoftstone.smartsite.model.main.listener.OnConvertViewClickListener;
import com.isoftstone.smartsite.model.video.RePlayVideoActivity;
import com.isoftstone.smartsite.model.video.VideoPlayActivity;
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

public class VideoMonitorAdapter extends BaseAdapter {


    private LayoutInflater mInflater;
    private ArrayList<VideoMonitorBean> mData = new ArrayList<VideoMonitorBean>();
    private Context mContext = null;
    private final String IMAGE_TYPE = "image/*";

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
            convertView = mInflater.inflate(R.layout.videomonitor_adapter, null);
            holder.name = (TextView)convertView.findViewById(R.id.textView_1);
            holder.time = (TextView)convertView.findViewById(R.id.textView_2);
            holder.address = (TextView)convertView.findViewById(R.id.textView_3);
            holder.state = (TextView)convertView.findViewById(R.id.textView_4);
            holder.button_1 = (Button)convertView.findViewById(R.id.button_1);
            holder.button_2 = (Button)convertView.findViewById(R.id.button_2);
            holder.button_3 = (Button)convertView.findViewById(R.id.button_3);

            convertView.setTag(holder);
            //convertView.setTag(R.id.ab_id_adapter_item_position, position);
        }else {
            holder = (ViewHolder)convertView.getTag();
        }

        holder.name.setText(mData.get(position).getDevicename());
        holder.time.setText(mData.get(position).getInstarltime());
        holder.address.setText(mData.get(position).getAddress());
        if(mData.get(position).getState()){
            holder.state.setText("在线");
        }else {
            holder.state.setText("离线");
        }

        final ViewHolder finalHolder = holder;
        Log.i("zyf_test","---------------------" + finalHolder.name.getText().toString());
        holder.button_1.setOnClickListener(new OnConvertViewClickListener(convertView, position) {

            @Override
            public void onClickCallBack(View registedView, View rootView) {
                //Toast.makeText(mContext, "ViewHolder: " +  ((ViewHolder)rootView.getTag()).toString(), Toast.LENGTH_SHORT).show();
                ViewHolder viewHolder = (ViewHolder)rootView.getTag();
                if(null != viewHolder) {
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putString("ResCode", viewHolder.name.getText().toString());
                    //Toast.makeText(mContext, "ViewHolder: " +  ((ViewHolder)rootView.getTag()).name.getText().toString(), Toast.LENGTH_SHORT).show();
                    intent.putExtras(bundle);
                    intent.setClass(mContext, VideoPlayActivity.class);
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
                    //模拟一个查询回放起始和结束的时间
                    Date now = new Date();
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    String beginTime = "2017-01-10" + " 00:00:00";
                    String endTime = formatter.format(now) + " 23:59:59";

                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putString("ResCode", viewHolder.name.getText().toString());
                    bundle.putString("beginTime", beginTime);
                    bundle.putString("endTime", endTime);
                    //Toast.makeText(mContext, "ViewHolder: " +  ((ViewHolder)rootView.getTag()).name.getText().toString(), Toast.LENGTH_SHORT).show();
                    intent.putExtras(bundle);
                    intent.setClass(mContext, RePlayVideoActivity.class);
                    mContext.startActivity(intent);
                } else {
                    Toast.makeText(mContext, "errorException:  ViewHolder is null", Toast.LENGTH_SHORT).show();
                }
            }
        });

        holder.button_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAlbum();
            }
        });

        return convertView;
    }

    public void openAlbum(){
        //Intent intent = new Intent();
        //intent.addCategory(Intent.CATEGORY_OPENABLE);
        //intent.setType(IMAGE_TYPE);
        //if (Build.VERSION.SDK_INT <19) {
            //intent.setAction(Intent.ACTION_GET_CONTENT);
        //}else {
        //    intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        //}
        try {
            Intent intentFromGallery = new Intent("com.android.gallery");
            mContext.startActivity(intentFromGallery);
        } catch (Exception e) {
            ToastUtils.showShort("Exception:" + e.getMessage());
        }
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

        @Override
        public String toString() {
            return "ViewHolder{" +
                    "name=" + name.getText().toString() +
                    ", time=" + time.getText().toString() +
                    ", address=" + address.getText().toString() +
                    ", state=" + state.getText().toString() +
                    '}';
        }
    }


}

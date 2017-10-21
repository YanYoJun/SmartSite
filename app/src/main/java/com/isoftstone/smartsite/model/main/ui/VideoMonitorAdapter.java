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
import android.widget.Toast;

import com.isoftstone.smartsite.R;
import com.isoftstone.smartsite.http.VideoMonitorBean;
import com.isoftstone.smartsite.model.main.listener.OnConvertViewClickListener;
import com.isoftstone.smartsite.model.video.VideoRePlayActivity;
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
            holder.resCode = (TextView)convertView.findViewById(R.id.textView_1);
            holder.resSubType = (TextView)convertView.findViewById(R.id.textView_2);
            holder.resName = (TextView)convertView.findViewById(R.id.textView_3);
            holder.isOnline = (TextView)convertView.findViewById(R.id.textView_4);
            holder.button_1 = (Button)convertView.findViewById(R.id.button_1);
            holder.button_2 = (Button)convertView.findViewById(R.id.button_2);
            holder.button_3 = (Button)convertView.findViewById(R.id.button_3);

            convertView.setTag(holder);
        }else {
            holder = (ViewHolder)convertView.getTag();
        }

        holder.resCode.setText(mData.get(position).getResCode());
        holder.resName.setText(mData.get(position).getResName());
        setCameraType(holder.resSubType, mData.get(position).getResSubType());
        if(mData.get(position).isOnline()){
            holder.isOnline.setText("在线");
            holder.button_1.setEnabled(true);
        }else {
            holder.isOnline.setText("离线");
            holder.button_1.setEnabled(false);
        }

        holder.button_1.setOnClickListener(new OnConvertViewClickListener(convertView, position) {

            @Override
            public void onClickCallBack(View registedView, View rootView) {
                //Toast.makeText(mContext, "ViewHolder: " +  ((ViewHolder)rootView.getTag()).toString(), Toast.LENGTH_SHORT).show();
                ViewHolder viewHolder = (ViewHolder)rootView.getTag();
                if(null != viewHolder) {
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putString("resCode", viewHolder.resCode.getText().toString());
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
                    bundle.putString("resCode", viewHolder.resCode.getText().toString());
                    bundle.putString("beginTime", beginTime);
                    bundle.putString("endTime", endTime);
                    //Toast.makeText(mContext, "ViewHolder: " +  ((ViewHolder)rootView.getTag()).name.getText().toString(), Toast.LENGTH_SHORT).show();
                    intent.putExtras(bundle);
                    intent.setClass(mContext, VideoRePlayActivity.class);
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

    private void setCameraType(TextView textView,int resSubType) {
        if (1 == resSubType) {
            textView.setText(R.string.camera_type_1);
        } else if (2 == resSubType) {
            textView.setText(R.string.camera_type_2);
        } else if (3 == resSubType) {
            textView.setText(R.string.camera_type_3);
        } else if (4 == resSubType) {
            textView.setText(R.string.camera_type_4);
        } else if (5 == resSubType) {
            textView.setText(R.string.camera_type_5);
        } else if (6 == resSubType) {
            textView.setText(R.string.camera_type_6);
        } else if (7 == resSubType) {
            textView.setText(R.string.camera_type_7);
        } else {
            textView.setText("");
        }
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
        public TextView resCode;//资源编码
        public TextView resSubType;//资源类型
        public TextView resName;//资源名称
        public TextView isOnline;//是否在线

        public Button button_1;//视频监控Btn
        public Button button_2;//环境监控
        public Button button_3 ;//三方协同

        public TextView getResCode() {
            return resCode;
        }

        public void setResCode(TextView resCode) {
            this.resCode = resCode;
        }

        public TextView getResSubType() {
            return resSubType;
        }

        public void setResSubType(TextView resSubType) {
            this.resSubType = resSubType;
        }

        public TextView getResName() {
            return resName;
        }

        public void setResName(TextView resName) {
            this.resName = resName;
        }

        public TextView getIsOnline() {
            return isOnline;
        }

        public void setIsOnline(TextView isOnline) {
            this.isOnline = isOnline;
        }

        @Override
        public String toString() {
            return "ViewHolder{" +
                    "resCode=" + resCode.getText().toString() +
                    ", resSubType=" + resSubType.getText().toString()  +
                    ", resName=" + resName.getText().toString()  +
                    ", isOnline=" + isOnline.getText().toString()  +
                    '}';
        }
    }


}

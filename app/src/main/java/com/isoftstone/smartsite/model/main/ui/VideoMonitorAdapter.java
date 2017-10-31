package com.isoftstone.smartsite.model.main.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
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
import java.util.Calendar;
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
    private final String IMAGE_TYPE = "image/*";
    private Context mContext = null;
    private AdapterViewOnClickListener listener;

    public VideoMonitorAdapter(Context context){
        this.mInflater = LayoutInflater.from(context);
        mContext = context;
        listener = (AdapterViewOnClickListener)context;
    }

    public interface AdapterViewOnClickListener {
        //postionType means   true ? onclick button 2 : onclick button 3
        public void viewOnClickListener(ViewHolder viewHolder, boolean isFormOneType);
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
            holder.resCodeTv = (TextView)convertView.findViewById(R.id.textView_1);
            holder.resSubTypeTv = (TextView)convertView.findViewById(R.id.textView_2);
            holder.resNameTv = (TextView)convertView.findViewById(R.id.textView_3);
            holder.isOnlineTv = (TextView)convertView.findViewById(R.id.textView_4);
            holder.button_1 = (Button)convertView.findViewById(R.id.button_1);
            holder.button_2 = (Button)convertView.findViewById(R.id.button_2);
            holder.button_3 = (Button)convertView.findViewById(R.id.button_3);

            convertView.setTag(holder);
        }else {
            holder = (ViewHolder)convertView.getTag();
        }

        Paint paint = holder.resCodeTv.getPaint();
        paint.setFakeBoldText(true);
        holder.resCodeTv.setText(mData.get(position).getResCode());
        holder.resNameTv.setText(mData.get(position).getResName());
        setCameraType(holder.resSubTypeTv, mData.get(position).getResSubType());
        holder.resType = mData.get(position).getResType();
        holder.resSubType = mData.get(position).getResSubType();
        holder.isOnline = mData.get(position).isOnline();
        if(holder.isOnline){
            holder.isOnlineTv.setText("在线");
            holder.button_1.setEnabled(true);
        }else {
            holder.isOnlineTv.setText("离线");
            holder.button_1.setEnabled(false);
        }
        holder.isShared = mData.get(position).isShared();

        holder.button_1.setOnClickListener(new OnConvertViewClickListener(convertView, position) {

            @Override
            public void onClickCallBack(View registedView, View rootView,int position) {
                //Toast.makeText(mContext, "ViewHolder: " +  ((ViewHolder)rootView.getTag()).toString(), Toast.LENGTH_SHORT).show();
                ViewHolder viewHolder = (ViewHolder)rootView.getTag();
                if(null != viewHolder) {
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putString("resCode", viewHolder.resCodeTv.getText().toString());
                    bundle.putInt("resSubType", viewHolder.resSubType);
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
            public void onClickCallBack(View registedView, View rootView,int position) {

                ViewHolder viewHolder = (ViewHolder)rootView.getTag();
                if (null != viewHolder) {
                    listener.viewOnClickListener(viewHolder, true);
                }
                /**ViewHolder viewHolder = (ViewHolder)rootView.getTag();

                if(null != viewHolder) {
                    //模拟一个查询回放起始和结束的时间
                    Date now = new Date();
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    String beginTime = "2017-01-10" + " 00:00:00";
                    String endTime = formatter.format(now) + " 23:59:59";

                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putString("resCode", viewHolder.resCodeTv.getText().toString());
                    bundle.putString("beginTime", beginTime);
                    bundle.putString("endTime", endTime);
                    //Toast.makeText(mContext, "ViewHolder: " +  ((ViewHolder)rootView.getTag()).name.getText().toString(), Toast.LENGTH_SHORT).show();
                    intent.putExtras(bundle);
                    intent.setClass(mContext, VideoRePlayActivity.class);
                    mContext.startActivity(intent);
                } else {
                    Toast.makeText(mContext, "errorException:  ViewHolder is null", Toast.LENGTH_SHORT).show();
                }*/
            }
        });

        final Calendar sCalendar = Calendar.getInstance();
        holder.button_3.setOnClickListener(new OnConvertViewClickListener(convertView, position)  {
            /**@Override
            public void onClick(View v) {
                //openAlbum();

                DatePickerDialog dialog = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            sCalendar.set(year, monthOfYear, dayOfMonth);
                            ToastUtils.showShort(DateFormat.format("yyy-MM-dd", sCalendar).toString());
                        }
                    }, sCalendar.get(Calendar.YEAR), sCalendar.get(Calendar.MONTH), sCalendar.get(Calendar.DAY_OF_MONTH));
                dialog.show();
                }*/

            @Override
            public void onClickCallBack(View registedView, View rootView,int position) {
                ViewHolder viewHolder = (ViewHolder)rootView.getTag();
                if (null != viewHolder) {
                    listener.viewOnClickListener(viewHolder, false);
                }
            }
        });

        return convertView;
    }

    /**
     * 固定摄像机：1; 云台摄像机：2; 高清固定摄像机：3; 高清云台摄像机：4; 车载摄像机：5; 不可控标清摄像机：6; 不可控高清摄像机：7;
     * @param textView
     * @param resSubType
     */
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
        public TextView resCodeTv;//资源编码
        public TextView resSubTypeTv;//资源子类型
        public TextView resNameTv;//资源名称
        public TextView isOnlineTv;//是否在线
        public int resType;
        public int resSubType;
        public Boolean isOnline;
        public Boolean isShared;

        public Button button_1;//视频监控Btn
        public Button button_2;//环境监控
        public Button button_3 ;//三方协同

        public TextView getResCodeTv() {
            return resCodeTv;
        }

        public void setResCodeTv(TextView resCodeTv) {
            this.resCodeTv = resCodeTv;
        }

        public TextView getResSubTypeTv() {
            return resSubTypeTv;
        }

        public void setResSubTypeTv(TextView resSubTypeTv) {
            this.resSubTypeTv = resSubTypeTv;
        }

        public TextView getResNameTv() {
            return resNameTv;
        }

        public void setResNameTv(TextView resNameTv) {
            this.resNameTv = resNameTv;
        }

        public TextView getIsOnlineTv() {
            return isOnlineTv;
        }

        public void setIsOnlineTv(TextView isOnlineTv) {
            this.isOnlineTv = isOnlineTv;
        }

        public int getResType() {
            return resType;
        }

        public void setResType(int resType) {
            this.resType = resType;
        }

        public int getResSubType() {
            return resSubType;
        }

        public void setResSubType(int resSubType) {
            this.resSubType = resSubType;
        }

        public Boolean getOnline() {
            return isOnline;
        }

        public void setOnline(Boolean online) {
            isOnline = online;
        }

        public Boolean getShared() {
            return isShared;
        }

        public void setShared(Boolean shared) {
            isShared = shared;
        }

        @Override
        public String toString() {
            return "ViewHolder{" +
                    "resCodeTv=" + resCodeTv.getText().toString() +
                    ", resSubTypeTv=" + resSubTypeTv.getText().toString() +
                    ", resNameTv=" + resNameTv.getText().toString() +
                    ", isOnlineTv=" + isOnlineTv.getText().toString() +
                    ", resType=" + resType +
                    ", resSubType=" + resSubType +
                    ", isOnline=" + isOnline +
                    ", isShared=" + isShared +
                    '}';
        }
    }


}

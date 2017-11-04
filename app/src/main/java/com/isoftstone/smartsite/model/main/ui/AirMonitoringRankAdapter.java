package com.isoftstone.smartsite.model.main.ui;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.isoftstone.smartsite.R;

/**
 * Created by zw on 2017/11/4.
 */

public class AirMonitoringRankAdapter extends BaseAdapter {

    private Context mContext;
    private int backgroundColor;

    public AirMonitoringRankAdapter(Context context){
        this.mContext = context;
        backgroundColor = Color.parseColor("#FE5A5A");
    }

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.layout_rank_item,parent,false);
            holder = new ViewHolder(convertView);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        if(position <= 6){
            GradientDrawable drawable = new GradientDrawable();
            drawable.setColor(backgroundColor);
            drawable.setCornerRadius(5);
            int alpha  = (int) (((float)(10 - position))/10 * 255);
            drawable.setAlpha(alpha);
            holder.tv_rank.setBackground(drawable);
            holder.tv_rank.setText(position + "");
        }else {
            GradientDrawable drawable = new GradientDrawable();
            drawable.setColor(backgroundColor);
            drawable.setCornerRadius(5);
            int alpha  = (int) (0.3 * 255);
            drawable.setAlpha(alpha);
            holder.tv_rank.setBackground(drawable);
            holder.tv_rank.setText(position + "");
        }

        holder.tv_address.setText("光谷" + position + "路");
        holder.tv_aqi.setText(""+ (100 - position * 5));
        holder.pb.setProgress(100 - position * 5);

        return convertView;
    }

    private class ViewHolder{
        private TextView tv_rank;
        private TextView tv_address;
        private TextView tv_aqi;
        private ProgressBar pb;

        public ViewHolder(View convertView){
            this.tv_address = (TextView) convertView.findViewById(R.id.tv_address);
            this.tv_aqi = (TextView) convertView.findViewById(R.id.tv_aqi);
            this.tv_rank = (TextView) convertView.findViewById(R.id.tv_rank);
            this.pb = (ProgressBar) convertView.findViewById(R.id.pb);
            convertView.setTag(this);
        }
    }
}

package com.isoftstone.smartsite.model.main.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.isoftstone.smartsite.R;
import com.isoftstone.smartsite.http.MessageListBean;

import java.util.ArrayList;

/**
 * Created by gone on 2017/10/16.
 */

public class InstantMessageAdapter extends BaseAdapter {


    private LayoutInflater mInflater;
    private ArrayList<MessageListBean> mData = new ArrayList<MessageListBean>();

    public InstantMessageAdapter(Context context){
        this.mInflater = LayoutInflater.from(context);
    }

    public void setData(ArrayList<MessageListBean> list){
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
            convertView = mInflater.inflate(R.layout.instantmsg_adapter, null);
            holder.map = (TextView)convertView.findViewById(R.id.textView_map);
            holder.info = (TextView)convertView.findViewById(R.id.textView_info);
            holder.time = (TextView)convertView.findViewById(R.id.textView_time);
            convertView.setTag(holder);
        }else {

            holder = (ViewHolder)convertView.getTag();
        }
        holder.info.setText(mData.get(position).getDetail());
        holder.map.setText(mData.get(position).getTitle());
        holder.time.setText(mData.get(position).getTime());

        return convertView;
    }

    public final class ViewHolder{
        public TextView map;
        public TextView info;
        public TextView time;
    }
}

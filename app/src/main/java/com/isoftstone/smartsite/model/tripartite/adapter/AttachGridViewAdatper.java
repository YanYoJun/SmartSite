package com.isoftstone.smartsite.model.tripartite.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.isoftstone.smartsite.R;

import java.util.ArrayList;

/**
 * Created by yanyongjun on 2017/11/6.
 */

public class AttachGridViewAdatper extends BaseAdapter {
    private ArrayList<Object> mDatas = new ArrayList<>();
    private Context mContext = null;
    private Resources mRes = null;

    public AttachGridViewAdatper(Context context, ArrayList<Object> datas) {
        mDatas = datas;
        mContext = context;
        mRes = mContext.getResources();
    }

    @Override
    public int getCount() {
        return mDatas.size();
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
        Object data = mDatas.get(position);
        if (data instanceof ImageView) {
            return (View) data;
        }
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.add_attach_grid_item, null);
        }
        ((ImageView) convertView).setImageDrawable(mRes.getDrawable((Integer) data, null));
        return convertView;
    }
}

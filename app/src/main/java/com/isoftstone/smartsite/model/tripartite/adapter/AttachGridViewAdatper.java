package com.isoftstone.smartsite.model.tripartite.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.isoftstone.smartsite.R;
import com.isoftstone.smartsite.utils.ImageUtils;

import java.util.ArrayList;

/**
 * Created by yanyongjun on 2017/11/6.
 */

public class AttachGridViewAdatper extends BaseAdapter {
    private ArrayList<Object> mDatas = null;
    private Context mContext = null;
    private Resources mRes = null;
    private final static String TAG = "AttachGridViewAdapter";

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
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.add_attach_grid_item, null);
        }
        Object data = mDatas.get(position);
        Log.e(TAG, "yanlog:position:" + position + "getView:" + data +"convertview:"+convertView);
        ImageView imgView = (ImageView) convertView.findViewById(R.id.image);
        if (data instanceof Integer) {
            Log.e(TAG,"yanlog load int view:"+data);
            imgView.setImageDrawable(mRes.getDrawable((Integer) data, null));
        } else {
            Log.e(TAG,"yanlog load string view:"+data);
            ImageUtils.loadImage(imgView, (String) data);
        }
        return convertView;
    }
}

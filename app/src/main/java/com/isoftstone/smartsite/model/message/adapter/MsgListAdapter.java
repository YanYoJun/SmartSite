package com.isoftstone.smartsite.model.message.adapter;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.isoftstone.smartsite.R;
import com.isoftstone.smartsite.model.message.data.MsgData;

import java.util.List;
import java.util.Map;

/**
 * Created by yanyongjun on 2017/10/28.
 */


public class MsgListAdapter extends SimpleAdapter {
    private Context mContext = null;
    private List<MsgData> mDatas = null;

    public MsgListAdapter(Context context, List<? extends Map<String, ?>> data,
                          @LayoutRes int resource, String[] from, @IdRes int[] to, List<MsgData> dataList) {
        super(context, data, resource, from, to);
        mContext = context;
        mDatas = dataList;
    }

    public MsgListAdapter(Context context, List<? extends Map<String, ?>> data,
                          @LayoutRes int resource, String[] from, @IdRes int[] to) {
        super(context, data, resource, from, to);
        mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        if (view != null) {
            RelativeLayout relativeTime = (RelativeLayout) view.findViewById(R.id.relative_year);
            RelativeLayout relativeNormal = (RelativeLayout) view.findViewById(R.id.relative_normal);
            TextView labYear = (TextView) view.findViewById(R.id.lab_year);
            if (mDatas.get(position).getType() == MsgData.TYPE_YEAR) {
                relativeNormal.setVisibility(View.GONE);
                relativeTime.setVisibility(View.VISIBLE);
                labYear.setText(mDatas.get(position).getStringDate());
            } else {
                relativeNormal.setVisibility(View.VISIBLE);
                relativeTime.setVisibility(View.GONE);
            }
        }
        return view;
    }
}
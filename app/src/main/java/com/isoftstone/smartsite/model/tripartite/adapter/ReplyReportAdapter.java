package com.isoftstone.smartsite.model.tripartite.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.isoftstone.smartsite.R;
import com.isoftstone.smartsite.model.tripartite.data.ReplyReportData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yanyongjun on 2017/10/29.
 */

public class ReplyReportAdapter extends BaseAdapter {
    private ArrayList<ReplyReportData> mData = null;
    private Context mContext = null;


    public ReplyReportAdapter(Context context, ArrayList<ReplyReportData> data) {
        mContext = context;
        mData = data;
    }

    @Override
    public int getCount() {
        return mData.size();
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
        View v = null;
        ReplyReportData data = mData.get(position);
        if (data.getType() == ReplyReportData.TYPE_REPOTER) {
            v = LayoutInflater.from(mContext).inflate(R.layout.listview_reply_report_left, null);
        } else {
            v = LayoutInflater.from(mContext).inflate(R.layout.listview_reply_report_right, null);
        }
        TextView time = (TextView) v.findViewById(R.id.lab_time);
        time.setText(data.getTime());
        TextView name = (TextView) v.findViewById(R.id.lab_name);
        name.setText(data.getName());
        TextView msg = (TextView) v.findViewById(R.id.lab_msg);
        msg.setText(data.getMsg());

        GridView gridView = (GridView) v.findViewById(R.id.grid_view);
        if (data.getBitmapSize() == 0) {
            gridView.setVisibility(View.GONE);
        } else {
            //TODO
            initGridView(v, gridView);
        }
        return v;
    }

    public void initGridView(View farent, GridView gridView) {
        gridView = (GridView) farent.findViewById(R.id.grid_view);

        ArrayList<Map<String, Object>> mData = new ArrayList<Map<String, Object>>();
        Map<String, Object> data = new HashMap<>();
        data.put("image", R.drawable.attachment);
        mData.add(data);
        SimpleAdapter mAttachAdapter = new SimpleAdapter(mContext, mData, R.layout.add_attach_grid_item, new String[]{"image"}, new int[]{R.id.image});
        gridView.setAdapter(mAttachAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /*if (position == mData.size() - 1) {*/
                    //点击添加附件
/*                    Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                    i.setType("image*//*");
                    startActivityForResult(i, REQUEST_ACTIVITY_ATTACH);*/

            }
        });
    }
}

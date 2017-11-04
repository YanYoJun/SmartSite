package com.isoftstone.smartsite.model.tripartite.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.isoftstone.smartsite.R;
import com.isoftstone.smartsite.model.message.data.MsgData;
import com.isoftstone.smartsite.model.tripartite.activity.ReadReportActivity;
import com.isoftstone.smartsite.model.tripartite.activity.ReplyReportActivity;
import com.isoftstone.smartsite.model.tripartite.activity.RevistReportActivity;
import com.isoftstone.smartsite.model.tripartite.activity.TripartiteActivity;
import com.isoftstone.smartsite.model.tripartite.data.ReportData;

import java.util.ArrayList;

/**
 * Created by yanyongjun 2017/10/17.
 */

public class InspectReportAdapter extends BaseAdapter {
    private Context mContext = null;
    private ArrayList<ReportData> mDatas;
    Resources mRes = null;

    public InspectReportAdapter(Context context, ArrayList<ReportData> datas) {
        mContext = context;
        mDatas = datas;
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
    public View getView(int position, View view, ViewGroup parent) {
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.listview_inspect_report_item, null);
        }
        if (view != null) {
            TextView time = (TextView) view.findViewById(R.id.lab_time);
            TextView title = (TextView) view.findViewById(R.id.lab_title);
            ImageView imageStatus = (ImageView) view.findViewById(R.id.img_status);
            TextView name = (TextView) view.findViewById(R.id.lab_name);
            TextView company = (TextView) view.findViewById(R.id.lab_company);
            ReportData reportData = mDatas.get(position);
            time.setText(MsgData.format3.format(reportData.getFormatDate()));
            title.setText(reportData.getAddress());
            name.setText(reportData.getCreator());
            company.setText(reportData.getCompany());
            imageStatus.setImageDrawable(mRes.getDrawable(TripartiteActivity.STATUS_IMG[reportData.getStatus()-1]));

            View btnView = view.findViewById(R.id.linear_read_report);
            if (btnView != null) {
                btnView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, ReadReportActivity.class);
                        mContext.startActivity(intent);
                    }
                });
            }
            View btnReply = view.findViewById(R.id.linear_reply_report);
            if (reportData.getStatus() != ReportData.STATUS_CHECKED) {
                btnReply.setClickable(true);
                if (btnReply != null) {
                    btnReply.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(mContext, ReplyReportActivity.class);
                            mContext.startActivity(intent);
                        }
                    });
                }
            }else{
                btnReply.setClickable(false);
            }
            View btnRevisit = view.findViewById(R.id.linear_revisit_report);
            if (reportData.getStatus() == ReportData.STATUS_WAITTING_REVISIT) {
                btnRevisit.setClickable(true);
                btnRevisit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, RevistReportActivity.class);
                        mContext.startActivity(intent);
                    }
                });
            }else{
                btnRevisit.setClickable(false);
            }

        }
        return view;
    }
}

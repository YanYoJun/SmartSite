package com.isoftstone.smartsite.model.tripartite.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.isoftstone.smartsite.R;
import com.isoftstone.smartsite.model.tripartite.ui.ReadReportActivity;
import com.isoftstone.smartsite.model.tripartite.ui.ReplyReportActivity;
import com.isoftstone.smartsite.model.tripartite.ui.RevisitFragment;
import com.isoftstone.smartsite.model.tripartite.ui.RevistReportActivity;

import java.util.List;
import java.util.Map;

/**
 * Created by yanyongjun 2017/10/17.
 */

public class InspectReportAdapter extends SimpleAdapter {
    private Context mContext = null;

    public InspectReportAdapter(Context context, List<? extends Map<String, ?>> data,
                                @LayoutRes int resource, String[] from, @IdRes int[] to) {
        super(context, data, resource, from, to);
        mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        if (view != null) {
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
            if (btnReply != null) {
                btnReply.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, ReplyReportActivity.class);
                        mContext.startActivity(intent);
                    }
                });
            }
            View btnRevisit = view.findViewById(R.id.linear_revisit_report);
            btnRevisit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, RevistReportActivity.class);
                    mContext.startActivity(intent);
                }
            });
        }
        return view;
    }
}

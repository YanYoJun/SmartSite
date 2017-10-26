package com.isoftstone.smartsite.model.tripartite.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SimpleAdapter;

import com.isoftstone.smartsite.R;
import com.isoftstone.smartsite.model.tripartite.ui.CheckReportActivity;
import com.isoftstone.smartsite.model.tripartite.ui.ViewCheckReportActivity;

import java.util.List;
import java.util.Map;

/**
 * Created by yanyongjun 2017/10/17.
 */

public class CheckReportAdapter extends SimpleAdapter {
    private Context mContext = null;

    public CheckReportAdapter(Context context, List<? extends Map<String, ?>> data,
                                @LayoutRes int resource, String[] from, @IdRes int[] to) {
        super(context, data, resource, from, to);
        mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        if (view != null) {
            Button btnView = (Button) view.findViewById(R.id.btn_check_view);
            if (btnView != null) {
                btnView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, ViewCheckReportActivity.class);
                        mContext.startActivity(intent);
                    }
                });
            }
            Button btnCheck = (Button) view.findViewById(R.id.btn_check_check);
            if (btnCheck != null) {
                btnCheck.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, CheckReportActivity.class);
                        mContext.startActivity(intent);
                    }
                });
            }
        }
        return view;
    }
}
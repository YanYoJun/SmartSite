package com.isoftstone.smartsite.model.Tripartite.adapter;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SimpleAdapter;

import com.isoftstone.smartsite.R;

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
            Button btnView = (Button) view.findViewById(R.id.btn_inspect_view);
            if (btnView != null) {
                btnView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //TODO
                    }
                });
            }
            Button btnReply = (Button) view.findViewById(R.id.btn_inspect_reply);
            if (btnReply != null) {
                btnReply.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //TODO
                    }
                });
            }

            Button btnReCheck = (Button) view.findViewById(R.id.btn_inspect_recheck);
            if (btnReCheck != null) {
                btnReCheck.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //TODO
                    }
                });
            }
        }
        return view;
    }
}

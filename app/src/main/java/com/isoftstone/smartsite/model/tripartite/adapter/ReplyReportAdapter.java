package com.isoftstone.smartsite.model.tripartite.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.isoftstone.smartsite.R;
import com.isoftstone.smartsite.http.PatrolBean;
import com.isoftstone.smartsite.http.ReportBean;
import com.isoftstone.smartsite.model.tripartite.activity.TripartiteActivity;
import com.isoftstone.smartsite.model.tripartite.data.ReplyReportData;
import com.isoftstone.smartsite.utils.DateUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yanyongjun on 2017/10/29.
 */

public class ReplyReportAdapter extends BaseAdapter {
    //private ArrayList<ReplyReportData> mData = null;
    private ReplyReportData replyReportData = null;
    private ArrayList<ReportBean> mData = null;
    private PatrolBean mReportData = null;
    private Context mContext = null;
    private String mReportCreator = null;


    public ReplyReportAdapter(Context context, ReplyReportData data) {
        mContext = context;
        replyReportData = data;
        if (data.getPatrolBean() == null) {
            return;
        }
        mData = data.getPatrolBean().getReports();
        mReportCreator = data.getPatrolBean().getCreator().getName();
        mReportData = data.getPatrolBean();
    }

    @Override
    public int getCount() {
        if (mData == null) {
            return 0;
        }
        return mData.size();
    }

    @Override
    public void notifyDataSetChanged() {
        if (replyReportData.getPatrolBean() == null) {
            return;
        }
        mData = replyReportData.getPatrolBean().getReports();
        mReportCreator = replyReportData.getPatrolBean().getCreator().getName();
        mReportData = replyReportData.getPatrolBean();
        super.notifyDataSetChanged();
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
        ReportBean data = mData.get(position);
        if (mReportCreator.equals(data.getCreator())) {
            if (data.getCategory() == 1) {
                v = initVisitView(data);
            } else {
                v = initCreatorReplyView(data);
            }
        } else {
            v = initCheckerReplyView(data);
        }
        return v;
    }

    /**
     * 添加回访报告
     *
     * @param data
     * @return
     */
    private View initVisitView(ReportBean data) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.fragment_read_visit_report, null);
        TextView time = (TextView) v.findViewById(R.id.lab_sub_time);
        String date = data.getDate();
        try {
            date = DateUtils.format1.format(DateUtils.format2.parse(date));
        } catch (Exception e) {
            e.printStackTrace();
        }
        time.setText(date);

        TextView lab_creator_name = (TextView) v.findViewById(R.id.lab_creator_name);
        lab_creator_name.setText(data.getCreator());

        TextView checkpeople = (TextView) v.findViewById(R.id.inspect_report_check_people_read);
        checkpeople.setText(data.getPatrolUser());

        TextView lab_begin_time = (TextView) v.findViewById(R.id.lab_begin_time);
        lab_begin_time.setText(data.getPatrolDateStart());

        TextView lab_end_time = (TextView) v.findViewById(R.id.lab_end_time);
        lab_end_time.setText(data.getPatrolDateEnd());

        TextView lab_report_name = (TextView) v.findViewById(R.id.lab_report_name);
        lab_report_name.setText(data.getName());

        TextView lab_report_content = (TextView) v.findViewById(R.id.lab_report_content);
        lab_report_content.setText(data.getContent());

        GridView gridView = (GridView) v.findViewById(R.id.grid_view_source_report_temp);
        gridView.setVisibility(View.GONE);//TODO

        TextView lab_next_visit_time = (TextView) v.findViewById(R.id.lab_next_visit_time);
        String visitTime = "下次回访时间：" + mReportData.getVisitDate();
        try {
            visitTime = "下次回访时间：" + DateUtils.format3.format(DateUtils.format2.parse(mReportData.getVisitDate()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        lab_next_visit_time.setText(visitTime);
        return v;
    }

    /**
     * 添加左侧的回复报告
     *
     * @param data
     * @return
     */
    private View initCreatorReplyView(ReportBean data) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.listview_reply_report_left, null);
        TextView time = (TextView) v.findViewById(R.id.lab_time);
        String date = data.getDate();
        try {
            date = DateUtils.format1.format(DateUtils.format2.parse(date));
        } catch (Exception e) {
            e.printStackTrace();
        }
        time.setText(date);

        TextView msg = (TextView) v.findViewById(R.id.lab_msg);
        msg.setText(data.getContent());

        TextView lab_creator_name = (TextView) v.findViewById(R.id.lab_creator_name);
        lab_creator_name.setText(data.getCreator());

        GridView gridView = (GridView) v.findViewById(R.id.grid_view);
        if (data.getReportFiles() == null || true) {
            gridView.setVisibility(View.GONE);
        } else {
            //TODO
            initGridView(v, gridView);
        }
        return v;
    }

    /**
     * 添加右侧的回复报告或验收报告
     *
     * @param data
     * @return
     */
    private View initCheckerReplyView(ReportBean data) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.listview_reply_report_right, null);
        TextView time = (TextView) v.findViewById(R.id.lab_time);
        String date = data.getDate();
        try {
            date = DateUtils.format1.format(DateUtils.format2.parse(date));
        } catch (Exception e) {
            e.printStackTrace();
        }
        TextView lab_creator_name = (TextView) v.findViewById(R.id.lab_creator_name);
        lab_creator_name.setText(data.getCreator());

        time.setText(date);
        ImageView img = (ImageView) v.findViewById(R.id.img_status);
        int status = data.getStatus();
        if (status > 1) {
            status--;
        }
        img.setImageDrawable(mContext.getResources().getDrawable(TripartiteActivity.STATUS_IMG[status]));

        TextView msg = (TextView) v.findViewById(R.id.lab_msg);
        msg.setText(data.getContent());

        GridView gridView = (GridView) v.findViewById(R.id.grid_view);
        if (data.getReportFiles() == null || true) {
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

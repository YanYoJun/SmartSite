package com.isoftstone.smartsite.model.tripartite.adapter;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.isoftstone.smartsite.R;
import com.isoftstone.smartsite.http.HttpPost;
import com.isoftstone.smartsite.http.PatrolBean;
import com.isoftstone.smartsite.http.ReportBean;
import com.isoftstone.smartsite.model.tripartite.activity.TripartiteActivity;
import com.isoftstone.smartsite.model.tripartite.data.ReplyReportData;
import com.isoftstone.smartsite.utils.DateUtils;
import com.isoftstone.smartsite.utils.FilesUtils;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by yanyongjun on 2017/10/29.
 */

public class ReplyReportAdapter extends BaseAdapter {
    //private ArrayList<ReplyReportData> mData = null;
    private ReplyReportData replyReportData = null;
    private ArrayList<ReportBean> mData = null;
    private PatrolBean mReportData = null;
    private Context mContext = null;
    private final static String TAG = "ReplyReportAdapter";
    private String mReportCreator = null;
    private HttpPost mHttpPost = new HttpPost();


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
        Log.e(TAG, "yanlog getView report:" + data.getFiles());
        if (data.getReportFiles() == null) {
            Gson gson = new Gson();
            ArrayList<String> temp = gson.fromJson(data.getFiles(), ArrayList.class);
            data.setReportFiles(temp);
        }
        if (data.getCategory() == 3) {
            v = initCheckerReplyView(data);
        } else if (data.getCategory() == 1) {
            v = initVisitView(data);
        } else {
            v = initCreatorReplyView(data);
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
        lab_creator_name.setText(data.getCreator().getAccount());

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
        initGridView(null, data, gridView);

//        TextView lab_next_visit_time = (TextView) v.findViewById(R.id.lab_next_visit_time);
//        String visitTime = "下次回访时间：" + mReportData.getVisitDate();
//        try {
//            visitTime = "下次回访时间：" + DateUtils.format3.format(DateUtils.format2.parse(mReportData.getVisitDate()));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        lab_next_visit_time.setText(visitTime);


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
        lab_creator_name.setText(data.getCreator().getAccount());

        GridView gridView = (GridView) v.findViewById(R.id.grid_view);
        LinearLayout linearLayout = (LinearLayout) v.findViewById(R.id.linear_grid_view);
        initGridView(linearLayout, data, gridView);
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
        lab_creator_name.setText(data.getCreator().getAccount());

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
        LinearLayout linearLayout = (LinearLayout) v.findViewById(R.id.linear_grid_view);
        initGridView(linearLayout, data, gridView);
        return v;
    }


    public void initGridView(LinearLayout farent, final ReportBean data, GridView gridView) {
        if (data.getReportFiles() == null || data.getReportFiles().size() == 0) {
            gridView.setVisibility(View.GONE);
            return;
        }
        final ArrayList<Object> datas = new ArrayList<Object>();
        final ArrayList<String> path = data.getReportFiles();
        if (farent != null) {
            ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) farent.getLayoutParams();
            switch (path.size()) {
                case 1:
                    params.width = 400 > params.width ? 400 : params.width;
                    break;
                case 2:
                    params.width = 600 > params.width ? 600 : params.width;
                    break;
                case 3:
                    params.width = 800 > params.width ? 800 : params.width;
                    break;
                case 4:
                    params.width = ViewGroup.LayoutParams.MATCH_PARENT;
                default:
            }
            farent.setLayoutParams(params);
        }

        for (String temp : path) {
            String formatPath = FilesUtils.getFormatString(temp);
            if (TripartiteActivity.mImageList.contains(formatPath)) {
                String filePath = mHttpPost.getReportPath(data.getId(), temp);
                if (new File(filePath).exists()) {
                    Log.e(TAG, "yanlog file exists,no need to download");
                    datas.add(mHttpPost.getReportPath(data.getId(), temp));
                } else {
                    datas.add(TripartiteActivity.mAttach.get(".image"));
                }

            } else if (TripartiteActivity.mXlsList.contains(formatPath)) {
                datas.add(TripartiteActivity.mAttach.get(".xls"));
            } else if (TripartiteActivity.mDocList.contains(formatPath)) {
                datas.add(TripartiteActivity.mAttach.get(".doc"));
            } else if (TripartiteActivity.mPdfList.contains(formatPath)) {
                datas.add(TripartiteActivity.mAttach.get(".pdf"));
            } else if (TripartiteActivity.mPptList.contains(formatPath)) {
                datas.add(TripartiteActivity.mAttach.get(".ppt"));
            } else if (TripartiteActivity.mVideoList.contains(formatPath)) {
                datas.add(TripartiteActivity.mAttach.get(".video"));
            } else {
                datas.add(TripartiteActivity.mAttach.get(".doc"));
            }
        }
        //mAttachAdapter = new SimpleAdapter(getActivity(), mData, R.layout.add_attach_grid_item, new String[]{"image"}, new int[]{R.id.image});
        final AttachGridViewAdatper mAttachAdapter = new AttachGridViewAdatper(mContext, datas, true);
        gridView.setAdapter(mAttachAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                new AsyncTask<Void, Void, String>() {
                    @Override
                    protected String doInBackground(Void... voids) {
                        Object object = datas.get(position);
                        try {
                            String localPath = mHttpPost.getReportPath(data.getId(), path.get(position));
                            if (new File(localPath).exists()) {
                                return localPath;
                            } else {
                                mHttpPost.downloadReportFile(data.getId(), path.get(position));
                                return localPath;
                            }
                        } catch (Exception e) {
                            return null;
                        }
                    }

                    @Override
                    protected void onPostExecute(String aBoolean) {
                        super.onPostExecute(aBoolean);
                        if (aBoolean == null) {
                            Toast.makeText(mContext, "文件下载失败，请重试", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(mContext, "文件已下载至:" + aBoolean, Toast.LENGTH_LONG).show();
                        }
                    }
                }.execute();
            }
        });

        new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... voids) {
                for (int i = 0; i < datas.size(); i++) {
                    Object object = datas.get(i);
                    if (object instanceof Integer) {
                        if ((Integer) object == TripartiteActivity.mAttach.get(".image")) {
                            mHttpPost.downloadReportFile(data.getId(), path.get(i));
                            datas.remove(i);
                            datas.add(i, mHttpPost.getReportPath(data.getId(), path.get(i)));
                        }
                    }
                }
                return null;
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                super.onPostExecute(aBoolean);
                mAttachAdapter.notifyDataSetChanged();
            }
        }.execute();
    }
}

package com.isoftstone.smartsite.model.tripartite.adapter;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
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

import com.autonavi.amap.mapcore.FileUtil;
import com.google.gson.Gson;
import com.isoftstone.smartsite.R;
import com.isoftstone.smartsite.common.App;
import com.isoftstone.smartsite.http.HttpPost;
import com.isoftstone.smartsite.http.PatrolBean;
import com.isoftstone.smartsite.http.ReportBean;
import com.isoftstone.smartsite.model.tripartite.activity.AddReportActivity;
import com.isoftstone.smartsite.model.tripartite.activity.TripartiteActivity;
import com.isoftstone.smartsite.model.tripartite.data.ReplyReportData;
import com.isoftstone.smartsite.utils.DateUtils;
import com.isoftstone.smartsite.utils.FilesUtils;
import com.isoftstone.smartsite.utils.ToastUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

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
    private Handler mHandler = new Handler();
    private AttachContentObserver mObserver = new AttachContentObserver();
    private DownloadManager mDownloadManager = null;


    public ReplyReportAdapter(Context context, ReplyReportData data) {
        mContext = context;
        replyReportData = data;
        mContext.getContentResolver().registerContentObserver(Uri.parse("content://downloads/my_downloads"), true,
                mObserver);
        mDownloadManager = (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);
        Log.e(TAG, "yanlog replyReportAdapter mDownloadManager =:" + mDownloadManager);
        if (data.getPatrolBean() == null) {
            Log.e(TAG, "yanlog data.getPatrolBean == null,return");
            return;
        }
        mData = data.getPatrolBean().getReports();
        mReportCreator = data.getPatrolBean().getCreator().getName();
        mReportData = data.getPatrolBean();


    }

    public void unRegister() {
        try {
            Log.e(TAG, "yanlog unregister content observer");
            mContext.getContentResolver().unregisterContentObserver(mObserver);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
            date = DateUtils.format1.format(DateUtils.format_yyyy_MM_dd_HH_mm_ss.parse(date));
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
            date = DateUtils.format1.format(DateUtils.format_yyyy_MM_dd_HH_mm_ss.parse(date));
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
            date = DateUtils.format1.format(DateUtils.format_yyyy_MM_dd_HH_mm_ss.parse(date));
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
        final ArrayList<String> relativePath = data.getReportFiles();
        // if (farent != null) {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) gridView.getLayoutParams();
        switch (relativePath.size()) {
            case 1:
                gridView.setNumColumns(1);
                params.width = 160;
                break;
            case 2:
                gridView.setNumColumns(2);
                params.width = 380;
                break;
            case 3:
                gridView.setNumColumns(3);
                params.width = 580;
                break;
            case 4:
            default:
                gridView.setNumColumns(4);
                //params.width = 700;
        }
        gridView.setLayoutParams(params);
        // }

        for (String temp : relativePath) {
            String formatPath = FilesUtils.getFormatString(temp);
            if (TripartiteActivity.mImageList.contains(formatPath)) {
                String filePath = mHttpPost.getReportPath(data.getId(), temp);
                if (new File(filePath).exists()) {
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
        final AttachGridViewAdatper attachAdapter = new AttachGridViewAdatper(mContext, datas);
        gridView.setAdapter(attachAdapter);
        attachAdapter.setAllPath(relativePath);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                Log.e(TAG, "yanlog click:" + position);
                new AsyncTask<Void, Void, String>() {
                    @Override
                    protected String doInBackground(Void... voids) {
                        try {
                            String absPath = mHttpPost.getReportPath(data.getId(), relativePath.get(position));
                            if (new File(absPath).exists()) {
                                Intent intent = FilesUtils.getOpenIntent(mContext, new File(absPath), absPath);
                                mContext.startActivity(intent);
                                return absPath;
                            } else {
                                mHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(mContext, "开始下载附件", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                long id = mHttpPost.downloadReportFile(data.getId(), relativePath.get(position));
                                mObserver.addPath(id, attachAdapter, relativePath.get(position));
                                return absPath;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            return null;
                        }
                    }

                    @Override
                    protected void onPostExecute(String aBoolean) {
                        super.onPostExecute(aBoolean);
                        if (aBoolean == null) {
                            //Toast.makeText(mContext, "文件下载失败，请重试", Toast.LENGTH_LONG).show();
                        } else {
                            //Toast.makeText(mContext, "文件开始下载，路径为:" + aBoolean, Toast.LENGTH_LONG).show();
                        }
                    }
                }.execute();
            }
        });

//        new AsyncTask<Void, Void, Boolean>() {
//            @Override
//            protected Boolean doInBackground(Void... voids) {
//                for (int i = 0; i < datas.size(); i++) {
//                    Object object = datas.get(i);
//                    if (object instanceof Integer) {
//                        if ((Integer) object == TripartiteActivity.mAttach.get(".image")) {
//                            Log.e(TAG,"yanlog begin to download image reportFile"+path.get(i) +" "+this);
//                            mHttpPost.downloadReportFile(data.getId(), path.get(i));
//                            datas.remove(i);
//                            datas.add(i, mHttpPost.getReportPath(data.getId(), path.get(i)));
//                        }
//                    }
//                }
//                return null;
//            }
//
//            @Override
//            protected void onPostExecute(Boolean aBoolean) {
//                super.onPostExecute(aBoolean);
//                Log.e(TAG,"notifyDatasetChanged "+this);
//                attachAdapter.notifyDataSetChanged();
//            }
//        }.execute();
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }

    public class AttachContentObserver extends ContentObserver {
        HashMap<Long, AttachGridViewAdatper> mMap = new HashMap<>();
        HashMap<Long, String> mPath = new HashMap<>();

        public AttachContentObserver() {
            super(mHandler);
        }

        /**
         * @param downloadId 绝对路径
         * @param adapter
         * @param oPath      相对路径
         */
        public synchronized void addPath(Long downloadId, AttachGridViewAdatper adapter, String oPath) {
            mMap.put(downloadId, adapter);
            mPath.put(downloadId, oPath);
        }

        @Override
        public void onChange(boolean selfChange, Uri uri) {
            try {
                Log.e(TAG, "yanlog onChange:" + uri);
                List<String> list = uri.getPathSegments();
                Long uriId = Long.parseLong(list.get(list.size() - 1));
                if (mDownloadManager.getUriForDownloadedFile(uriId) != null) {
                    ArrayList<Object> allData = mMap.get(uriId).getAllData();
                    ArrayList<String> allPath = mMap.get(uriId).getAllPath();
                    for (int i = 0; i < allPath.size(); i++) {
                        String relativePath = allPath.get(i);
                        String formatStr = FilesUtils.getFormatString(relativePath);
                        if (relativePath.equals(mPath.get(uriId))) {
                            if (TripartiteActivity.mImageList.contains(formatStr)) {
                                allData.remove(i);
                                allData.add(i, uri.toString());
                                mMap.get(uriId).notifyDataSetChanged();
                            }
                            ToastUtils.showShort("下载完成");
                            break;
                        }

                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            super.onChange(selfChange, uri);
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
        }
    }
}

package com.isoftstone.smartsite.model.main.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.isoftstone.smartsite.R;
import com.isoftstone.smartsite.http.HttpPost;
import com.isoftstone.smartsite.http.VideoMonitorBean;
import com.uniview.airimos.listener.OnQueryResourceListener;
import com.uniview.airimos.manager.ServiceManager;
import com.uniview.airimos.obj.QueryCondition;
import com.uniview.airimos.obj.ResourceInfo;
import com.uniview.airimos.parameter.QueryResourceParam;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gone on 2017/10/17.
 * modifed by zhangyinfu on 2017/10/19
 */

public class VideoMonitoringActivity extends Activity {
    private static final String TAG = "zyf_VideoMonitoring";
    private HttpPost mHttpPost = new HttpPost();
    private ListView mListView = null;
    private Context mContext;

    @Override
    protected void onStart() {
        super.onStart();
        mContext = getApplicationContext();
        //查询设备列表
        queryResourceList();
    }

    private void queryResourceList() {
        //查询资源参数
        QueryResourceParam params = new QueryResourceParam("", "", new QueryCondition(0, 200, true));

        //查询资源结果监听
        OnQueryResourceListener sListener = new OnQueryResourceListener() {
            @Override
            public void onQueryResourceResult(long errorCode, String errorDesc, List<ResourceInfo> resList) {

                if (errorCode != 0 || resList == null){
                    Toast.makeText(mContext,"error info: " + errorDesc,Toast.LENGTH_SHORT).show();
                    return;
                }
                if (resList.size() == 0){
                    Toast.makeText(mContext,"查询不到摄像机资源...",Toast.LENGTH_SHORT).show();
                    return;
                }

                StringBuffer strBuf = new StringBuffer();
                int size = resList.size();

                ArrayList<VideoMonitorBean> sList = new ArrayList<VideoMonitorBean>();
                VideoMonitorBean video;
                for (int i = 0; i < size; i++) {
                    strBuf.append(resList.get(i).getResCode() + ",");
                    strBuf.append(resList.get(i).getResName() + ",");
                    strBuf.append(resList.get(i).getResType() + ",");
                    strBuf.append(resList.get(i).getResSubType() + ",");
                    strBuf.append(resList.get(i).getIsOnline() + "\n");

                    /**if (resList.get(i).getResType() == ResourceInfo.ResType.CAMERA && resList.get(i).getIsOnline()) {
                        mEditCamCode.setText(resList.get(i).getResCode());
                        break;
                    }*/
                    video = new VideoMonitorBean(resList.get(i).getResCode(),"2017-5-8",resList.get(i).getResName(),resList.get(i).getIsOnline());
                    sList.add(video);
                }

                Log.i(TAG, strBuf.toString());
                VideoMonitorAdapter adapter = new VideoMonitorAdapter(VideoMonitoringActivity.this);
                adapter.setData(sList);
                mListView.setAdapter(adapter);
            }
        };

        //查询资源接口调用
        ServiceManager.queryResource(params, sListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videomonitoring);
        init();
        //setData();
    }

    private void init(){
        mListView = (ListView) findViewById(R.id.list);
    }

    private void setData(){
        VideoMonitorAdapter adapter = new VideoMonitorAdapter(VideoMonitoringActivity.this);
        VideoMonitorBean video = new VideoMonitorBean("TX_001","2017-5-8","洪山广场大新路",true);
        ArrayList<VideoMonitorBean> list = new ArrayList<VideoMonitorBean>();
        list.add(video);
        list.add(video);
        list.add(video);
        list.add(video);
        list.add(video);
        adapter.setData(list);
        mListView.setAdapter(adapter);
    }
}

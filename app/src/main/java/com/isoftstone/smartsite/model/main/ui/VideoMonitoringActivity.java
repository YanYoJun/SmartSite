package com.isoftstone.smartsite.model.main.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ListView;

import com.isoftstone.smartsite.R;
import com.isoftstone.smartsite.http.HttpPost;
import com.isoftstone.smartsite.http.VideoMonitorBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gone on 2017/10/17.
 */

public class VideoMonitoringActivity extends Activity {

    private HttpPost mHttpPost = new HttpPost();
    private ListView mListView = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videomonitoring);
        init();
        setData();
    }

    private void init(){
        mListView = (ListView) findViewById(R.id.list);
    }

    private void setData(){
        VideoMonitorAdapter adapter = new VideoMonitorAdapter(VideoMonitoringActivity.this);
        VideoMonitorBean video = new VideoMonitorBean("TX_001","2017-5-8","洪山广场大新路",1);
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

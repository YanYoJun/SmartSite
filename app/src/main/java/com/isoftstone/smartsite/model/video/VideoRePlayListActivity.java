package com.isoftstone.smartsite.model.video;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.isoftstone.smartsite.R;
import com.isoftstone.smartsite.common.widget.AlertView;
import com.isoftstone.smartsite.http.HttpPost;
import com.isoftstone.smartsite.http.VideoMonitorBean;
import com.isoftstone.smartsite.model.main.ui.VideoMonitorAdapter;
import com.isoftstone.smartsite.utils.ToastUtils;
import com.uniview.airimos.listener.OnQueryReplayListener;
import com.uniview.airimos.listener.OnQueryResourceListener;
import com.uniview.airimos.manager.ServiceManager;
import com.uniview.airimos.obj.QueryCondition;
import com.uniview.airimos.obj.RecordInfo;
import com.uniview.airimos.obj.ResourceInfo;
import com.uniview.airimos.parameter.QueryReplayParam;
import com.uniview.airimos.parameter.QueryResourceParam;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by zhangyinfu on 2017/10/22.
 */

public class VideoRePlayListActivity extends Activity{
    private static final String TAG = "zyf_VideoRePlayList";
    private ListView mListView = null;
    private Context mContext;

    @Override
    protected void onStart() {
        super.onStart();
        mContext = getApplicationContext();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_replay_list);
        init();
    }

    private void init(){
        mListView = (ListView) findViewById(R.id.list);
    }

}

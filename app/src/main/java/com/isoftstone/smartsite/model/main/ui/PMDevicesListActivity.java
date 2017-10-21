package com.isoftstone.smartsite.model.main.ui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.isoftstone.smartsite.R;
import com.isoftstone.smartsite.http.PMDevicesDataInfoBean;

import java.util.ArrayList;

/**
 * Created by gone on 2017/10/21.
 */

public class PMDevicesListActivity extends Activity{

    private ListView mListView = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pmdeviceslist);

        init();
        setData();
    }

    private void init(){
        mListView = (ListView)findViewById(R.id.listview);
    }

    private void setData(){
        PMDevicesDataInfoBean info = new PMDevicesDataInfoBean();
        info.setName("DEV_123123");
        info.setState(1);
        info.setTime("2017-5-12");
        info.setAddress("东湖技术开发区");
        info.setPM10("25");
        info.setPM25("25");
        info.setO3("555");
        info.setNO2("25");
        ArrayList list = new ArrayList();
        list.add(info);
        list.add(info);
        list.add(info);
        list.add(info);
        list.add(info);
        PMDevicesListAdapter adapter = new PMDevicesListAdapter(getBaseContext());
        adapter.setData(list);
        mListView.setAdapter(adapter);
    }
}

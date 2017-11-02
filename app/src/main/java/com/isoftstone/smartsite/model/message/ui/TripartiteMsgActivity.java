package com.isoftstone.smartsite.model.message.ui;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.isoftstone.smartsite.R;
import com.isoftstone.smartsite.base.BaseActivity;
import com.isoftstone.smartsite.model.message.adapter.MsgListAdapter;
import com.isoftstone.smartsite.model.message.data.MsgData;
import com.isoftstone.smartsite.model.message.data.ThreePartyData;
import com.isoftstone.smartsite.model.message.data.VCRData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yanyongjun on 2017/10/15.
 */

public class TripartiteMsgActivity extends BaseActivity {
    //listView中各项的名称
    public static final String ITEM_DATE = "lab_time";
    public static final String ITEM_TITLE = "lab_title";
    public static final String ITEM_DETAILS = "lab_details";

    private Activity mActivity = null;
    private ListView mListView = null;
    private List<MsgData> mDatas = null;
    @Override
    protected int getLayoutRes() {
        return R.layout.activity_msg_thirpartite;
    }

    @Override
    protected void afterCreated(Bundle savedInstanceState) {
        mActivity = this;
        init();
    }

    private void init() {
        mListView = (ListView) mActivity.findViewById(R.id.listview_frag_synergy);
        SimpleAdapter adapter = new MsgListAdapter(mActivity, getData(), R.layout.listview_msg_item, new String[]{ITEM_DATE,ITEM_TITLE,ITEM_DETAILS,  },
                new int[]{R.id.lab_time, R.id.lab_title,R.id.lab_details},mDatas);
        mListView.setAdapter(adapter);
    }

    /**
     * 加载listview中的内容
     * @return
     */
    private List<Map<String, Object>> getData() {
        //TODO
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Resources res = mActivity.getResources();

        List<MsgData> datas = readDataFromSDK();
        String sendReport = res.getString(R.string.send_report);
        String receiveReport = res.getString(R.string.receive_report);
        for (MsgData temp : datas) {
            ThreePartyData data = (ThreePartyData)temp;
            Map<String, Object> map = new HashMap<String, Object>();
            if (data.getType() == ThreePartyData.TYPE_SEND_REPORT) {
                map.put(ITEM_TITLE,"收到张三一份巡查报告");
                map.put(ITEM_DETAILS, sendReport);
                map.put(ITEM_DATE, data.getStringDate());
                list.add(map);
            } else if(data.getType() == ThreePartyData.TYPE_RECEIVE_REPORT) {
                map.put(ITEM_TITLE,"您发送了一份巡查报告");
                map.put(ITEM_DETAILS, String.format(receiveReport, data.getName()));
                map.put(ITEM_DATE, data.getStringDate());
                list.add(map);
            }
        }
        return list;
    }

    /**
     * 测试专用，后续需要从SDK数据源中读取
     * @return
     */
    private List<MsgData> readDataFromSDK() {
        //TODO
        mDatas = new ArrayList<>();

        ThreePartyData data = new ThreePartyData();
        data.setType(ThreePartyData.TYPE_RECEIVE_REPORT);
        data.setName("张珊");
        mDatas.add(data);

        data = new ThreePartyData();
        data.setType(ThreePartyData.TYPE_SEND_REPORT);
        data.setName("王五");
        mDatas.add(data);

        data = new ThreePartyData();
        data.setType(ThreePartyData.TYPE_RECEIVE_REPORT);
        data.setName("赵六");
        mDatas.add(data);

        data = new ThreePartyData();
        data.setType(VCRData.TYPE_YEAR);
        data.setId(13005);
        mDatas.add(data);

        data = new ThreePartyData();
        data.setType(ThreePartyData.TYPE_SEND_REPORT);
        data.setName("李四");
        mDatas.add(data);

        data = new ThreePartyData();
        data.setType(VCRData.TYPE_YEAR);
        data.setId(13005);
        mDatas.add(data);

        data = new ThreePartyData();
        data.setType(ThreePartyData.TYPE_RECEIVE_REPORT);
        data.setName("Steve");
        mDatas.add(data);

        return mDatas;
    }
}

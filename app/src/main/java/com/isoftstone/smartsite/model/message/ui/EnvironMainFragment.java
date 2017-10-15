package com.isoftstone.smartsite.model.message.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.isoftstone.smartsite.R;
import com.isoftstone.smartsite.base.BaseFragment;
import com.isoftstone.smartsite.model.message.data.EnvironData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yanyongjun on 2017/10/15.
 */

public class EnvironMainFragment extends BaseFragment {
    //listview中各项的名称
    public static final String FRAG_MSG_ITEM_DETAILS = "frag_environ_msg_details";
    public static final String FRAG_MSG_ITEM_DATE = "frag_msg_environ_date";

    private Activity mActivity = null;
    private ListView mListView = null;
    private List<EnvironData> mDatas = null;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_msg_environ;
    }

    @Override
    protected void afterCreated(Bundle savedInstanceState) {
        mActivity = getActivity();
        init();
    }

    private void init() {
        mListView = (ListView) mActivity.findViewById(R.id.listview_frag_environ);
        SimpleAdapter adapter = new SimpleAdapter(mActivity, getData(), R.layout.frag_environ_item, new String[]{FRAG_MSG_ITEM_DETAILS, FRAG_MSG_ITEM_DATE},
                new int[]{R.id.frag_environ_msg_details, R.id.frag_msg_environ_date});
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mActivity, DetailsActivity.class);
                intent.putExtra(MsgFragment.FRAGMENT_TYPE, MsgFragment.FRAGMENT_TYPE_ENVIRON);
                intent.putExtra(MsgFragment.FRAGMENT_DATA, mDatas.get(position));
                mActivity.startActivity(intent);
            }
        });
    }

    /**
     * 加载listview中的内容
     *
     * @return
     */
    private List<Map<String, Object>> getData() {
        //TODO
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Resources res = mActivity.getResources();

        List<EnvironData> environDatas = readDataFromSDK();
        String pmExtends = res.getString(R.string.environ_pm_exceeded);
        String needRepair = res.getString(R.string.environ_need_repair);
        for (EnvironData data : environDatas) {
            Map<String, Object> map = new HashMap<String, Object>();
            if (data.getType() == EnvironData.TYPE_PM_EXTENDS) {
                map.put(FRAG_MSG_ITEM_DETAILS, String.format(pmExtends, data.getId()));
                map.put(FRAG_MSG_ITEM_DATE, data.getStringDate());
                list.add(map);
            } else if (data.getType() == EnvironData.TYPE_NEED_REPAIR) {
                map.put(FRAG_MSG_ITEM_DETAILS, String.format(needRepair, data.getId()));
                map.put(FRAG_MSG_ITEM_DATE, data.getStringDate());
                list.add(map);
            }
        }
        return list;
    }

    /**
     * 测试专用，后续需要从SDK数据源中读取
     *
     * @return
     */
    private List<EnvironData> readDataFromSDK() {
        //TODO
        mDatas = new ArrayList<>();

        EnvironData data = new EnvironData();
        data.setType(EnvironData.TYPE_PM_EXTENDS);
        data.setId(13001);
        mDatas.add(data);

        data = new EnvironData();
        data.setType(EnvironData.TYPE_NEED_REPAIR);
        data.setId(13002);
        mDatas.add(data);

        data = new EnvironData();
        data.setType(EnvironData.TYPE_NEED_REPAIR);
        data.setId(13003);
        mDatas.add(data);

        data = new EnvironData();
        data.setType(EnvironData.TYPE_PM_EXTENDS);
        data.setId(13004);
        mDatas.add(data);

        data = new EnvironData();
        data.setType(EnvironData.TYPE_NEED_REPAIR);
        data.setId(13005);
        mDatas.add(data);

        return mDatas;
    }
}

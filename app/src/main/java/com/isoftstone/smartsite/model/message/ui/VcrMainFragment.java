package com.isoftstone.smartsite.model.message.ui;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.isoftstone.smartsite.R;
import com.isoftstone.smartsite.base.BaseFragment;
import com.isoftstone.smartsite.model.message.data.VCRData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yanyongjun on 2017/10/15.
 */

public class VcrMainFragment extends BaseFragment {
    //listview中各item项的名称
    public static final String FRAG_MSG_ITEM_DETAILS = "frag_msg_details";
    public static final String FRAG_MSG_ITEM_DATE = "frag_msg_date";

    private Activity mActivity = null;
    private ListView mListView = null;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_msg_vcr;
    }

    @Override
    protected void afterCreated(Bundle savedInstanceState) {
        mActivity = getActivity();
        init();
    }

    private void init() {
        mListView = (ListView) mActivity.findViewById(R.id.listview_frag_vcr);
        SimpleAdapter adapter = new SimpleAdapter(mActivity, getData(), R.layout.frag_vcr_item, new String[]{FRAG_MSG_ITEM_DETAILS, FRAG_MSG_ITEM_DATE},
                new int[]{R.id.frag_msg_details, R.id.frag_msg_date});
        mListView.setAdapter(adapter);
    }

    /**
     * 加载listview的数据源
     * @return
     */
    private List<Map<String, Object>> getData() {
        //TODO
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Resources res = mActivity.getResources();

        List<VCRData> vcrDatas = readDataFromSDK();
        String vcrError = res.getString(R.string.vcr_error);
        String vcrNeedRepair = res.getString(R.string.vcr_need_repair);
        for (VCRData data : vcrDatas) {
            Map<String, Object> map = new HashMap<String, Object>();
            if (data.getType() == VCRData.TYPE_ERROR) {
                map.put(FRAG_MSG_ITEM_DETAILS, String.format(vcrError, data.getId()));
                map.put(FRAG_MSG_ITEM_DATE, data.getStringDate());
                list.add(map);
            } else if(data.getType() == VCRData.TYPE_NEED_REPAIR) {
                map.put(FRAG_MSG_ITEM_DETAILS, String.format(vcrNeedRepair, data.getId()));
                map.put(FRAG_MSG_ITEM_DATE, data.getStringDate());
                list.add(map);
            }
        }
        return list;
    }

    /**
     * 目前是测试使用，后续需要从网络侧读取
     * @return
     */
    private List<VCRData> readDataFromSDK() {
        //TODO
        List<VCRData> list = new ArrayList<>();

        VCRData data = new VCRData();
        data.setType(VCRData.TYPE_ERROR);
        data.setId(13001);
        list.add(data);

        data = new VCRData();
        data.setType(VCRData.TYPE_NEED_REPAIR);
        data.setId(13002);
        list.add(data);

        data = new VCRData();
        data.setType(VCRData.TYPE_NEED_REPAIR);
        data.setId(13003);
        list.add(data);

        data = new VCRData();
        data.setType(VCRData.TYPE_ERROR);
        data.setId(13004);
        list.add(data);

        data = new VCRData();
        data.setType(VCRData.TYPE_NEED_REPAIR);
        data.setId(13005);
        list.add(data);

        return list;
    }
}

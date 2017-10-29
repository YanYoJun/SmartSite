package com.isoftstone.smartsite.model.tripartite.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.isoftstone.smartsite.R;
import com.isoftstone.smartsite.base.BaseFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yanyongjun on 2017/10/30.
 */

public class CheckFragment extends BaseFragment {
    private GridView mAttachView = null;
    private SimpleAdapter mAttachAdapter = null;
    private ArrayList<Map<String, Object>> mData = null;

    private TextView mAddressSpinner = null;
    private TextView mStatusSpinner = null;
    private TextView mTypesSpinner = null;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_recheck_check_report;
    }

    @Override
    protected void afterCreated(Bundle savedInstanceState) {
        initGridView();
    }
    public void initGridView(){
        mAttachView = (GridView) getView().findViewById(R.id.grid_view);

        mData = new ArrayList<Map<String, Object>>();
        Map<String, Object> data = new HashMap<>();
        data.put("image", R.drawable.attachment);
        mData.add(data);
        mAttachAdapter = new SimpleAdapter(getActivity(), mData, R.layout.add_attach_grid_item, new String[]{"image"}, new int[]{R.id.image});
        mAttachView.setAdapter(mAttachAdapter);

        mAttachView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
/*                if (position == mData.size() - 1) {
                    //点击添加附件
                    Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                    i.setType("image*//*");
                    startActivityForResult(i, REQUEST_ACTIVITY_ATTACH);
                }*/
            }
        });
    }
}

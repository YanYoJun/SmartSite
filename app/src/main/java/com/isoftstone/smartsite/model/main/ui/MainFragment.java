package com.isoftstone.smartsite.model.main.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.isoftstone.smartsite.R;
import com.isoftstone.smartsite.base.BaseFragment;
import com.isoftstone.smartsite.http.HomeBean;
import com.isoftstone.smartsite.http.HttpPost;


/**
 * Created by zw on 2017/10/11.
 *
 *
 * 开发版SHA1 ： 17:02:19:67:57:D4:F4:AF:3E:AE:22:1F:95:65:9A:27:FD:F7:8D:D0
 *
 *             17:02:19:67:57:D4:F4:AF:3E:AE:22:1F:95:65:9A:27:FD:F7:8D:D0;com.isoftstone.smartsite
 *
 *
 * 发布版SHA1 ： C3:83:83:56:68:FD:2B:BC:EE:BB:16:AF:BA:52:EC:6A:C9:24:19:D5
 *
 *              C3:83:83:56:68:FD:2B:BC:EE:BB:16:AF:BA:52:EC:6A:C9:24:19:D5;com.isoftstone.smartsite
 */

public class MainFragment extends BaseFragment{

    private TextView mCityTestView = null;
    private TextView mWeatherTextView = null;
    private TextView mTemperatureTextView = null;
    private HttpPost mHttpPost = null;
    private HomeBean mHomeBean = null;
    private Button mThirdPartReport = null; //三方协同按钮
    private ListView mListView = null;
    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_main;
    }

    @Override
    protected void afterCreated(Bundle savedInstanceState) {
        initView();
        getHomeData();
    }

    private void initView(){
        mCityTestView = (TextView) rootView.findViewById(R.id.text_city);
        mTemperatureTextView = (TextView)  rootView.findViewById(R.id.text_temperature);
        mThirdPartReport = (Button)rootView.findViewById(R.id.button_3);
        mThirdPartReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),ReportActivity.class);
                getActivity().startActivity(intent);
            }
        });
        mListView = (ListView) rootView.findViewById(R.id.list);
    }

    private void getHomeData(){
        mHttpPost = new HttpPost();
        mHomeBean =  mHttpPost.getHomeDate();
        mCityTestView.setText(mHomeBean.getCity());
        mTemperatureTextView.setText(mHomeBean.getTemperature());
        InstantMessageAdapter adapter = new InstantMessageAdapter(getContext());
        adapter.setData(mHomeBean.getMessagelist());
        mListView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}

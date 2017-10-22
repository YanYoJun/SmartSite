package com.isoftstone.smartsite.model.main.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.isoftstone.smartsite.MainActivity;
import com.isoftstone.smartsite.R;
import com.isoftstone.smartsite.base.BaseFragment;
import com.isoftstone.smartsite.http.HomeBean;
import com.isoftstone.smartsite.http.HttpPost;
import com.isoftstone.smartsite.model.message.data.SynergyData;
import com.isoftstone.smartsite.model.message.ui.DetailsActivity;
import com.isoftstone.smartsite.model.message.ui.MsgFragment;


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
    private Button mVideoMonitoring = null; //视频监控
    private Button mAirMonitoring = null; //环境监测
    private Button mThirdPartReport = null; //三方协同按钮
    private TextView mVideoMonitoringMsg = null;
    private TextView mAirMonitoringMsg = null;
    private TextView mUnCheckMsg = null;
    private TextView mUntreatedReport = null;
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
        mUnCheckMsg = (TextView) rootView.findViewById(R.id.textView10);
        mUnCheckMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterUnChekMsg();
            }
        });
        mUntreatedReport = (TextView) rootView.findViewById(R.id.textView11);
        mUntreatedReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterUntreatedReport();
            }
        });
        mVideoMonitoringMsg = (TextView) rootView.findViewById(R.id.textView12);
        mVideoMonitoringMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterVideoMonitoring();
            }
        });
        mAirMonitoringMsg = (TextView) rootView.findViewById(R.id.textView13);
        mAirMonitoringMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterAirMonitoringMsg();
            }
        });
        mVideoMonitoring = (Button)rootView.findViewById(R.id.button_1);
        mVideoMonitoring.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterVideoMonitoring();
            }
        });
        mAirMonitoring = (Button)rootView.findViewById(R.id.button_2);
        mAirMonitoring.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterAirMonitoring();
            }
        });
        mThirdPartReport = (Button)rootView.findViewById(R.id.button_3);
        mThirdPartReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterThirdPartReport();
            }
        });
        mListView = (ListView) rootView.findViewById(R.id.list);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onItemClicked();
            }
        });
    }

    private void onItemClicked(){
        SynergyData data = new SynergyData();
        data.setType(SynergyData.TYPE_RECEIVE_REPORT);
        data.setName("张珊");
        Intent intent = new Intent(getActivity(), DetailsActivity.class);
        intent.putExtra(MsgFragment.FRAGMENT_TYPE, MsgFragment.FRAGMENT_TYPE_SYNERGY);
        intent.putExtra(MsgFragment.FRAGMENT_DATA, data);
        getActivity().startActivity(intent);
    }

    private void enterUnChekMsg(){
          //进入未查看消息
        ((MainActivity)getActivity()).setCurrentTab(2);
    }

    private void enterUntreatedReport(){
        //进入未处理报告
        ((MainActivity)getActivity()).setCurrentTab(2);
    }

    private void enterAirMonitoringMsg(){
        Intent intent = new Intent(getActivity(),PMDevicesListActivity.class);
        getActivity().startActivity(intent);
    }
    private void enterThirdPartReport(){
        //进入三方协同
        ((MainActivity)getActivity()).setCurrentTab(2);
    }

    private void enterVideoMonitoring(){
        //进入视屏监控
        Intent intent = new Intent(getActivity(),VideoMonitoringActivity.class);
        getActivity().startActivity(intent);
    }

    private void enterAirMonitoring(){
        //进入环境监控
        Intent intent = new Intent(getActivity(),AirMonitoringActivity.class);
        getActivity().startActivity(intent);
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

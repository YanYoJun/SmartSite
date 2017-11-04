package com.isoftstone.smartsite.model.main.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.isoftstone.smartsite.MainActivity;
import com.isoftstone.smartsite.R;
import com.isoftstone.smartsite.base.BaseFragment;
import com.isoftstone.smartsite.http.HomeBean;
import com.isoftstone.smartsite.http.HttpPost;
import com.isoftstone.smartsite.http.MobileHomeBean;
import com.isoftstone.smartsite.model.message.data.ThreePartyData;
import com.isoftstone.smartsite.model.message.ui.DetailsActivity;
import com.isoftstone.smartsite.model.message.ui.MsgFragment;
import com.isoftstone.smartsite.model.tripartite.activity.TripartiteActivity;


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
    private TextView lab_main_unread_num = null;  //未查看消息数目
    private TextView lab_report_unread_num = null;  //未查看报告数目
    private TextView lab_vcr_unread_num = null;//视屏监控设备数
    private TextView lab_air_unread_num = null;//环境监控数目
    private HttpPost mHttpPost = new HttpPost();
    private View mVideoMonitoring = null; //视频监控
    private View mAirMonitoring = null; //环境监测
    private View mThirdPartReport = null; //三方协同按钮
    private LinearLayout mVideoMonitoringMsg = null;
    private LinearLayout mAirMonitoringMsg = null;
    private LinearLayout mUnCheckMsg = null;
    private LinearLayout mUntreatedReport = null;
    private ListView mListView = null;

    public static final  int HANDLER_GET_HOME_DATA_START = 1;
    public static final  int HANDLER_GET_HOME_DATA_END = 2;
    public static final  int HANDLER_GET_MESSAGE_START = 3;
    public static final  int HANDLER_GET_MESSAGE_END = 4;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_main;
    }

    @Override
    protected void afterCreated(Bundle savedInstanceState) {
        initView();
        mHandler.sendEmptyMessage(HANDLER_GET_HOME_DATA_START);
    }

    private void initView(){
        mCityTestView = (TextView) rootView.findViewById(R.id.text_city);
        lab_main_unread_num = (TextView) rootView.findViewById(R.id.lab_main_unread_num);  //未查看消息数目
        lab_report_unread_num = (TextView) rootView.findViewById(R.id.lab_report_unread_num);  //未查看报告数目
        lab_vcr_unread_num = (TextView) rootView.findViewById(R.id.lab_vcr_unread_num);//视屏监控设备数
        lab_air_unread_num = (TextView) rootView.findViewById(R.id.lab_air_unread_num);//环境监控数目
        mTemperatureTextView = (TextView)  rootView.findViewById(R.id.text_temperature);
        mUnCheckMsg = (LinearLayout) rootView.findViewById(R.id.textView10);
        mUnCheckMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterUnChekMsg();
            }
        });
        mUntreatedReport = (LinearLayout) rootView.findViewById(R.id.textView11);
        mUntreatedReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterUntreatedReport();
            }
        });
        mVideoMonitoringMsg = (LinearLayout) rootView.findViewById(R.id.textView12);
        mVideoMonitoringMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterVideoMonitoring();
            }
        });
        mAirMonitoringMsg = (LinearLayout) rootView.findViewById(R.id.textView13);
        mAirMonitoringMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterAirMonitoringMsg();
            }
        });
        mVideoMonitoring = rootView.findViewById(R.id.button_1);
        mVideoMonitoring.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterVideoMonitoring();
            }
        });
        mAirMonitoring = rootView.findViewById(R.id.button_2);
        mAirMonitoring.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterAirMonitoring();
            }
        });
        mThirdPartReport = rootView.findViewById(R.id.button_3);
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

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case HANDLER_GET_HOME_DATA_START:
                {
                    getMobileHomeData();
                }
                    break;
                case HANDLER_GET_HOME_DATA_END:
                {
                    setMobileHomeDataToView();
                }
                    break;
                case HANDLER_GET_MESSAGE_START:
                    break;
                case HANDLER_GET_MESSAGE_END:
                    break;
            }
        }
    };

    private MobileHomeBean mMobileHomeBean = null;
    private  void getMobileHomeData(){
        Thread thread = new Thread(){
            @Override
            public void run() {
                mMobileHomeBean = mHttpPost.getMobileHomeData();
                mHandler.sendEmptyMessage(HANDLER_GET_HOME_DATA_END);
            }
        };
        thread.start();
    }

    private void setMobileHomeDataToView(){
        mCityTestView.setText("武汉");
        if(mMobileHomeBean == null){
            return;
        }
        int unreadMessage = mMobileHomeBean.getUnreadMessages();
        if(unreadMessage > 0){
            lab_main_unread_num.setText(unreadMessage+"");//未查看消息数目
            lab_main_unread_num.setVisibility(View.VISIBLE);
        }else {
            lab_main_unread_num.setVisibility(View.INVISIBLE);
        }
        int unreadPatrols = mMobileHomeBean.getUntreatedPatrols();
        if(unreadPatrols > 0){
            lab_report_unread_num.setText(unreadPatrols+"");//未查看报告数目
            lab_report_unread_num.setVisibility(View.VISIBLE);
        }else {
            lab_report_unread_num.setVisibility(View.INVISIBLE);
        }
        lab_vcr_unread_num.setText(mMobileHomeBean.getAllVses()+"");//视屏监控设备数
        lab_air_unread_num.setText(mMobileHomeBean.getAllEmes()+"");//环境监控数目

        InstantMessageAdapter adapter = new InstantMessageAdapter(getContext());
        adapter.setData(mMobileHomeBean.getMessages());
        mListView.setAdapter(adapter);
    }



    private void onItemClicked(){
//        ThreePartyData data = new ThreePartyData();
//        data.setType(ThreePartyData.TYPE_RECEIVE_REPORT);
//        data.setName("张珊");
//        Intent intent = new Intent(getActivity(), DetailsActivity.class);
//        intent.putExtra(MsgFragment.FRAGMENT_TYPE, MsgFragment.FRAGMENT_TYPE_SYNERGY);
//        intent.putExtra(MsgFragment.FRAGMENT_DATA, data);
//        getActivity().startActivity(intent);
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
        /*((MainActivity)getActivity()).setCurrentTab(2);*/
        //yanyongjun 这个地方应该是进到三方协同界面，而不是到消息fragment页
        Intent intent = new Intent(getActivity(), TripartiteActivity.class);
        startActivity(intent);
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

package com.isoftstone.smartsite.model.system.ui;

import android.Manifest;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.isoftstone.smartsite.R;
import com.isoftstone.smartsite.base.BaseFragment;


/**
 * Created by zyf on 2017/10/15 8:00.
 */

public class SystemFragment extends BaseFragment{

    private LinearLayout linearLayout;//用户头像父节点LL
    private ImageView imageView;//用户头像IV
    String picPath;//头像路径
    private LinearLayout individualCenterLinearLayout;//个人中心Btn
    private LinearLayout aboutUsLinearLayout;//关于我们Btn
    private LinearLayout logOffLinearLayout;//退出Btn
    private PermissionsChecker mPermissionsChecker;

    private Fragment mCurrentFrame;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_system;
    }

    @Override
    protected void afterCreated(Bundle savedInstanceState) {
        init();
    }

    /**
     * 实例化
     */
    private void init() {
        mPermissionsChecker = new PermissionsChecker(getActivity().getApplicationContext());
        picPath = Environment.getExternalStorageDirectory() + "/smartsite/";
        imageView = (ImageView) rootView.findViewById(R.id.head_iv);
        individualCenterLinearLayout = (LinearLayout) rootView.findViewById(R.id.individual_center);
        aboutUsLinearLayout = (LinearLayout) rootView.findViewById(R.id.about_us);
        logOffLinearLayout = (LinearLayout) rootView.findViewById(R.id.log_off);

        registerLinearLayoutOnClickListener();
        mCurrentFrame = SystemFragment.this;
    }

    private void registerLinearLayoutOnClickListener() {
        individualCenterLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment individualCenterFrame = new IndividualCenterFragment();
                changeToAnotherFragment(individualCenterFrame);
            }
        });
        aboutUsLinearLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

            }
        } );
        logOffLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
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


    private void changeToAnotherFragment(Fragment toFragment){
        //如果是不是用的v4的包，则用getActivity().getFragmentManager();
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        //注意v4包的配套使用
        if(mCurrentFrame != toFragment) {
            if(!toFragment.isAdded()) {
                transaction.hide(mCurrentFrame).add(R.id.fl_system_content, toFragment).commitAllowingStateLoss();
            } else{
                transaction.hide(mCurrentFrame).show(toFragment).commitAllowingStateLoss();
            }
            mCurrentFrame = toFragment;
        }
    }

}

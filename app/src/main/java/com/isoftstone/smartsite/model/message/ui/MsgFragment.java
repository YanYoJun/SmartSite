package com.isoftstone.smartsite.model.message.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.isoftstone.smartsite.R;
import com.isoftstone.smartsite.base.BaseFragment;

/**
 * Created by yanyongjun on 2017/10/15.
 */

public class MsgFragment extends BaseFragment {
/*    private ViewPager mViewPager = null;
    ArrayList<Fragment> mFragList = new ArrayList<Fragment>();
    private SparseArray<TextView> mSwitchLab = new SparseArray<>();*/

    public static final int FRAGMENT_TYPE_VCR = 0;
    public static final int FRAGMENT_TYPE_ENVIRON = 1;
    public static final int FRAGMENT_TYPE_SYNERGY = 2;


    private RelativeLayout mVcr = null;
    private RelativeLayout mEnviron = null;
    private RelativeLayout mTripartite = null;
    private Activity mActivity = null;

    public static final String FRAGMENT_TYPE = "type";
    public static final String FRAGMENT_DATA = "data";

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_msg;
    }

    @Override
    protected void afterCreated(Bundle savedInstanceState) {
        init();
    }

    private void init(){
        mActivity = getActivity();
        mVcr = (RelativeLayout)mActivity.findViewById(R.id.conlayout_vcr);
        mEnviron = (RelativeLayout)mActivity.findViewById(R.id.conlayout_environ);
        mTripartite = (RelativeLayout)mActivity.findViewById(R.id.conlayout_thirpartite);

        mVcr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mActivity,VcrActivity.class);
                mActivity.startActivity(i);
            }
        });
        mEnviron.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mActivity,EnvironActivity.class);
                mActivity.startActivity(i);
            }
        });
        mTripartite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mActivity,TripartiteMsgActivity.class);
                mActivity.startActivity(i);
            }
        });
    }
}

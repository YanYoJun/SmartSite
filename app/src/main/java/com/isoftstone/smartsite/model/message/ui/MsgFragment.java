package com.isoftstone.smartsite.model.message.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.View;

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


    private ConstraintLayout mVcr = null;
    private ConstraintLayout mEnviron = null;
    private ConstraintLayout mTripartite = null;
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
        mVcr = (ConstraintLayout)mActivity.findViewById(R.id.conlayout_vcr);
        mEnviron = (ConstraintLayout)mActivity.findViewById(R.id.conlayout_environ);
        mTripartite = (ConstraintLayout)mActivity.findViewById(R.id.conlayout_thirpartite);

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
/*

    private void init() {
        mTitle = (TextView)getActivity().findViewById(R.id.lab_title_name);
        mViewPager = (ViewPager) getActivity().findViewById(R.id.main_view_pager);
        Fragment watchDevice = new VcrActivity();
        Fragment watchEnviron = new EnvironActivity();
        Fragment synergy = new TripartiteMsgActivity();

        mFragList.add(watchDevice);
        mFragList.add(watchEnviron);
        mFragList.add(synergy);

        FragmentPagerAdapter pagerAdapter = new FragmentPagerAdapter(getActivity().getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragList.get(position);
            }

            @Override
            public int getCount() {
                return mFragList.size();
            }
        };
        mViewPager.setAdapter(pagerAdapter);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //TODO
            }

            @Override
            public void onPageSelected(int position) {
                chooseFrag(position);
            }


            @Override
            public void onPageScrollStateChanged(int state) {
                //TODO
            }
        });

        TextView v1 = (TextView) getActivity().findViewById(R.id.lab_device);
        TextView v2 = (TextView) getActivity().findViewById(R.id.lab_environ);
        TextView v3 = (TextView) getActivity().findViewById(R.id.lab_synergy);


        v1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseFrag(0);
                mViewPager.setCurrentItem(0);
            }
        });
        v2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseFrag(1);
                mViewPager.setCurrentItem(1);
            }
        });
        v3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseFrag(2);
                mViewPager.setCurrentItem(2);
            }
        });

        mSwitchLab.put(0, v1);
        mSwitchLab.put(1, v2);
        mSwitchLab.put(2, v3);
        chooseFrag(0);
        mViewPager.setCurrentItem(0);
    }

    private void chooseFrag(int position) {
        switch(position){
            case FRAGMENT_TYPE_VCR:
                mTitle.setText(R.string.vcr_msg);
                break;
            case FRAGMENT_TYPE_ENVIRON:
                mTitle.setText(R.string.environ_msg);
                break;
            case FRAGMENT_TYPE_SYNERGY:
                mTitle.setText(R.string.synergy_msg);
                break;
        }
        Resources res = getActivity().getResources();
        for (int i = 0; i < mSwitchLab.size(); i++) {
            TextView v = mSwitchLab.get(i);
            if (i == position) {
                v.setTextColor(res.getColor(R.color.white));
                v.setBackgroundColor(res.getColor(R.color.tab_text_normal));
            } else {
                v.setTextColor(res.getColor(R.color.tab_text_normal));
                v.setBackgroundColor(res.getColor(R.color.white));
            }
        }
    }
*/


}

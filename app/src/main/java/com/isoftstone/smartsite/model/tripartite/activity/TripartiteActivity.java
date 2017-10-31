package com.isoftstone.smartsite.model.tripartite.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.isoftstone.smartsite.R;
import com.isoftstone.smartsite.base.BaseActivity;
import com.isoftstone.smartsite.model.tripartite.fragment.CheckReportMainFragment;
import com.isoftstone.smartsite.model.tripartite.fragment.InspectReportMainFragment;

import java.util.ArrayList;

/**
 * Created by yanyongjun on 2017/10/16.
 * 三方协同的主页面
 */

public class TripartiteActivity extends BaseActivity {
    ArrayList<Fragment> mFragList = new ArrayList<Fragment>();
    private SparseArray<TextView> mSwitchLab = new SparseArray<>();
    private SparseArray<ImageView> mSwitchImg = new SparseArray<>();

    public static final int FRAGMENT_TYPE_INSPECT_REPORT = 0;
    public static final int FRAGMENT_TYPE_CHECK_REPORT = 1;

    public static final String FRAGMENT_TYPE = "type";
    public static final String FRAGMENT_DATA = "data";

    //View in this activity
    private ViewPager mViewPager = null;
    private View mDefaultBar = null; //the default bar at the title
    private View mSearchBar = null; // the search bar show if click the search button in default bar



    @Override
    protected int getLayoutRes() {
        return R.layout.activity_tripartite;
    }

    @Override
    protected void afterCreated(Bundle savedInstanceState) {
        init();
    }

    private void initView(){
        mViewPager = (ViewPager) findViewById(R.id.report_view_pager);
        mDefaultBar = findViewById(R.id.toolbar_default);
        mSearchBar = findViewById(R.id.toolbar_search);

        //init the default view state
        mDefaultBar.setVisibility(View.VISIBLE);
        mSearchBar.setVisibility(View.GONE);
    }

    private void init() {
        initView();
        Fragment inspectFrag = new InspectReportMainFragment();
        Fragment checkFrag = new CheckReportMainFragment();

        mFragList.add(inspectFrag);
        mFragList.add(checkFrag);

        FragmentPagerAdapter pagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
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
                initTitleOnClickListener(position);
            }


            @Override
            public void onPageScrollStateChanged(int state) {
                //TODO
            }
        });

        TextView v1 = (TextView) findViewById(R.id.lab_inspect);
        TextView v2 = (TextView) findViewById(R.id.lab_check);


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

        mSwitchLab.put(0, v1);
        mSwitchLab.put(1, v2);
        ImageView img1 = (ImageView)findViewById(R.id.img_inspect);
        mSwitchImg.put(0,img1);
        ImageView img2 = (ImageView)findViewById(R.id.img_check);
        mSwitchImg.put(1,img2);

        chooseFrag(0);
        initTitleOnClickListener(0);
        mViewPager.setCurrentItem(0);
    }

    private void chooseFrag(int position) {
        Resources res = getResources();
        Drawable drawable = res.getDrawable(R.drawable.shape_threeparty_lab);
        drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
        for (int i = 0; i < mSwitchLab.size(); i++) {
            TextView v = mSwitchLab.get(i);
            if (i == position) {
                v.setTextColor(res.getColor(R.color.mainColor));
                mSwitchImg.get(i).setVisibility(View.VISIBLE);
            } else {
                v.setTextColor(res.getColor(R.color.main_text_color));
                mSwitchImg.get(i).setVisibility(View.INVISIBLE);
            }
        }
    }

    private void initTitleOnClickListener(int position){
        switch (position){
            case FRAGMENT_TYPE_INSPECT_REPORT:
                break;
            case FRAGMENT_TYPE_CHECK_REPORT:
                break;
            default:
                break;
        }
    }

    /**
     * 点击返回键之后的操作
     * @param v
     */
    public void onBackBtnClick(View v){
        finish();
    }

    /**
     * 点击新增报告界面按钮
     * @param v
     */
    public void onAddReportBtnClick(View v){
        Intent intent = new Intent(this,AddReportActivity.class);
        startActivity(intent);
    }

    /**
     * 点击搜索按钮
     * @param v
     */
    public void onSearchBtnClick(View v){
        mSearchBar.setVisibility(View.VISIBLE);
        mDefaultBar.setVisibility(View.INVISIBLE);
    }

    /**
     * 当位于搜索状态时，点击toolbar上面的取消按钮
     * @param v
     */
    public void onCancelSearchBtnClick(View v){
        mSearchBar.setVisibility(View.GONE);
        mDefaultBar.setVisibility(View.VISIBLE);
    }
}

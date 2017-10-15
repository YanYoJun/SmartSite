package com.isoftstone.smartsite;

import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.isoftstone.smartsite.base.BaseActivity;
import com.isoftstone.smartsite.model.main.ui.MainFragment;
import com.isoftstone.smartsite.model.map.ui.MapFragment;
import com.isoftstone.smartsite.model.message.ui.MsgFragment;

public class MainActivity extends BaseActivity {

    private String[] tabTitles;
    private FragmentTabHost tabHost;

    private int[] tabIcons = new int[]{R.drawable.selector_tab_home,R.drawable.selector_tab_round,
            R.drawable.selector_tab_me,R.drawable.selector_tab_more};

    private Class[] fragments = new Class[]{MainFragment.class,MapFragment.class,
            MsgFragment.class,MapFragment.class};

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void afterCreated(Bundle savedInstanceState) {
        tabTitles = this.getResources().getStringArray(R.array.tab_title);

        initView();
    }

    private void initView(){
        tabHost = (FragmentTabHost) findViewById(R.id.tab_host);

        initTabWidget();
    }

    private void initTabWidget(){
        //设置Fragment的容器
        tabHost.setup(this,getSupportFragmentManager(),android.R.id.tabcontent);
        //设置有多少Item
        for (int i = 0; i < fragments.length; i++) {
            View view = getLayoutInflater().inflate(R.layout.layout_tab,null);
            ImageView iv = (ImageView) view.findViewById(R.id.iv_tab);
            iv.setImageResource(tabIcons[i]);
            TextView tv = (TextView) view.findViewById(R.id.tv_tab);
            tv.setText(tabTitles[i]);
            tabHost.addTab(tabHost.newTabSpec("" + i).setIndicator(view),fragments[i],null);
        }
        tabHost.setCurrentTab(0);
    }
}

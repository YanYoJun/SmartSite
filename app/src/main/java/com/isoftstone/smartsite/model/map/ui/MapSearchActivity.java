package com.isoftstone.smartsite.model.map.ui;

import android.os.Bundle;
import android.view.View;

import com.isoftstone.smartsite.R;
import com.isoftstone.smartsite.base.BaseActivity;

/**
 * Created by zw on 2017/10/30.
 */

public class MapSearchActivity extends BaseActivity implements View.OnClickListener {


    @Override
    protected int getLayoutRes() {
        return R.layout.activity_map_search;
    }

    @Override
    protected void afterCreated(Bundle savedInstanceState) {
        initView();
    }

    private void initView() {
        findViewById(R.id.btn_back).setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_back:
                this.finish();
                break;

        }
    }
}

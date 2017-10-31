/*
 * Copyright (c) 2013, ZheJiang Uniview Technologies Co., Ltd. All rights reserved.
 * <http://www.uniview.com/>
 *------------------------------------------------------------------------------
 * Product     : IMOS
 * Module Name : 
 * Date Created: 2017-10-19
 * Creator     : zhangyinfu
 * Description : 
 *
 *------------------------------------------------------------------------------
 * Modification History
 * DATE        NAME             DESCRIPTION
 * 
 *------------------------------------------------------------------------------
 */
package com.isoftstone.smartsite.model.system.ui;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.isoftstone.smartsite.R;
import com.isoftstone.smartsite.model.map.ui.MapSearchActivity;
import com.isoftstone.smartsite.utils.ToastUtils;

/**
 * 意见反馈界面
 * created by zhangyinfu 2017-10-31
 */
public class SettingsActivity extends Activity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        initToolbar();
    }

    private void initToolbar(){
        TextView tv_title = (TextView) findViewById(R.id.toolbar_title);
        tv_title.setText(R.string.settings);

        findViewById(R.id.btn_back).setOnClickListener(SettingsActivity.this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_back:
                SettingsActivity.this.finish();
                break;
            default:
                break;
        }
    }
}

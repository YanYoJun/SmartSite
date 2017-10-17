package com.isoftstone.smartsite.model.video;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.isoftstone.smartsite.R;

/**
 * Created by gone on 2017/10/17.
 */

public class VideoPlayActivity extends Activity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videoplay);
    }
}

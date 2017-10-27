package com.isoftstone.smartsite.common;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDexApplication;


/**
 * Created by zw on 2017/10/11.
 */

public class App extends Application {

    private static App mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static Context getAppContext(){
        return mInstance.getApplicationContext();
    }
}

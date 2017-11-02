package com.isoftstone.smartsite.common;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.isoftstone.smartsite.LoginActivity;
import com.isoftstone.smartsite.LoginActivity2;

/**
 * Description:
 * Created by L02465 on 2017.01.13.
 */
public class NewKeepAliveService extends KeepAliveService {


    @Override
    public void keepAliveSuccess() {
        Log.e("eee"," keepAlive Success ");
    }

    @Override
    public void keepAliveFailure(String error) {
        Log.e("eee"," keepAlive Failure " + error);

        Toast.makeText(this, "保活失败，请重新登录...",Toast.LENGTH_SHORT).show();
        Intent toLogin = new Intent(this, LoginActivity.class);
        toLogin.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        this.startActivity(toLogin);

    }
}

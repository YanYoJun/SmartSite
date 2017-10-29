package com.isoftstone.smartsite.http;

import android.util.Log;

import com.isoftstone.smartsite.utils.LogUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by gone on 2017/10/29.
 */

public class UserLogin {
    private static String TAG = "UserLogin";

    public static LoginBean login(String strurl, OkHttpClient mClient, String username, String password, String mobileDeviceId){
        LoginBean loginBean = null;
        FormBody body = new FormBody.Builder()
                .add("username", username)
                .add("password", password)
                .add("mobileDeviceId",mobileDeviceId)
                .build();
        Request request = new Request.Builder()
                .url(strurl)
                .post(body)
                .build();
        Response response = null;
        try {
            response = mClient.newCall(request).execute();
            LogUtils.i(TAG,"login response code "+response.code());
            if(response.isSuccessful()){
                loginBean = new LoginBean();
                String responsebody = response.body().string();
                LogUtils.i(TAG,"login response "+responsebody);
                JSONObject json = new JSONObject(responsebody);
                boolean success = json.getBoolean("success");
                if(success){
                    loginBean.setmName(username);
                    loginBean.setmPassword(password);
                    loginBean.setLoginSuccess(true);
                }else{
                    int errorinfo = json.getInt("reason");
                    loginBean.setmErrorCode(errorinfo);
                    loginBean.setLoginSuccess(false);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return loginBean;
    }
}

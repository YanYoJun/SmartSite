package com.isoftstone.smartsite.http;

import android.util.Log;

import com.isoftstone.smartsite.utils.LogUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by gone on 2017/10/29.
 */

public class MessageOperation {
    private static  String TAG = "EQIMonitoring";

    public static  ArrayList<MessageBean> getMessage(String strurl, OkHttpClient mClient, String title, String type, String status, String module){
        //获取消息列表  MESSAGE_LIST
        ArrayList<MessageBean> list = null;
        String funName = "getDevicesList";
        FormBody body = new FormBody.Builder()
                .add("title", title)
                .add("type", type)
                .add("status", status)
                .add("module", module)
                .build();
        Request request = new Request.Builder()
                .url(strurl)
                .post(body)
                .build();
        Response response = null;
        try {
            response = mClient.newCall(request).execute();
            LogUtils.i(TAG,funName+" response code "+response.code());
            if(response.isSuccessful()){

                String responsebody = response.body().string();
                LogUtils.i(TAG,funName+" responsebody  "+responsebody);
                String content = null;
                content = new JSONObject(responsebody).getString("content");
                list = HttpPost.stringToList(content,MessageBean.class);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }catch (JSONException e) {
            e.printStackTrace();
        }
        return  list;
    }
}

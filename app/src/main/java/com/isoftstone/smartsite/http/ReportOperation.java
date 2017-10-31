package com.isoftstone.smartsite.http;

import com.google.gson.Gson;
import com.isoftstone.smartsite.utils.LogUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by gone on 2017/10/30.
 */

public class ReportOperation {
    private static  String TAG = "ReportOperation";

    public static ArrayList<PatrolBean> getPatrolReportList(String strurl, OkHttpClient mClient, int status){
        ArrayList<PatrolBean> list = null;
        String funName = "getPatrolList";
        FormBody body = new FormBody.Builder()
                .add("status", ""+status)
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

                String content = new JSONObject(responsebody).getString("content");
                list = HttpPost.stringToList(content,PatrolBean.class);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }


    public static  PatrolBean addPatrolReport(String strurl, OkHttpClient mClient, PatrolBean reportBean){

        PatrolBean patrolReportBean = null;
        String funName = "addPatrolReport";
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("address",reportBean.getAddress());
        builder.add("company",reportBean.getCompany());
        String developmentCompany = reportBean.getDevelopmentCompany();
        if(developmentCompany != null && !developmentCompany.equals("")){
            builder.add("developmentCompany",developmentCompany);
        }
        String constructionCompany = reportBean.getConstructionCompany();
        if(constructionCompany != null && !constructionCompany.equals("")){
            builder.add("constructionCompany",constructionCompany);
        }
        String supervisionCompany = reportBean.getSupervisionCompany();
        if(supervisionCompany != null && !supervisionCompany.equals("")){
            builder.add("supervisionCompany",supervisionCompany);
        }

        builder.add("isVisit",reportBean.isVisit()+"");

        String visitDate = reportBean.getVisitDate();
        if(visitDate != null && !visitDate.equals("")){
            builder.add("visitDate",visitDate);
        }
        FormBody body =builder.build();
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

                Gson gson = new Gson();
                patrolReportBean = gson.fromJson(responsebody,PatrolBean.class);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  patrolReportBean;
    }


    public static PatrolBean getPatrolReport(String strurl, OkHttpClient mClient, String id){
        PatrolBean patrolReportBean = null;
        String funName = "getPatrolReport";
        Request request = new Request.Builder()
                .url(strurl+id)
                .get()
                .build();
        Response response = null;
        try {
            response = mClient.newCall(request).execute();
            LogUtils.i(TAG,funName+" response code "+response.code());
            if(response.isSuccessful()){

                String responsebody = response.body().string();
                LogUtils.i(TAG,funName+" responsebody  "+responsebody);
                Gson gson = new Gson();
                patrolReportBean = gson.fromJson(responsebody,PatrolBean.class);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  patrolReportBean;
    }

    public static void addPatrolVisit(String strurl, OkHttpClient mClient, ReportBean reportBean){
        String funName = "addVisitReport";
        try {
            Gson gson = new Gson();
            String json = gson.toJson(reportBean);
            RequestBody body = RequestBody.create(HttpPost.JSON, json);

            Request request = new Request.Builder()
                    .url(strurl)
                    .post(body)
                    .build();
            Response response = mClient.newCall(request).execute();
            LogUtils.i(TAG,funName+" response code "+response.code());
            if(response.isSuccessful()){

                String responsebody = response.body().string();
                LogUtils.i(TAG,funName+" responsebody  "+responsebody);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addPatrolReply(String strurl, OkHttpClient mClient, ReportBean reportBean){
        String funName = "addVisitReport";

        try {
            Gson gson = new Gson();
            String json = gson.toJson(reportBean);
            RequestBody body = RequestBody.create(HttpPost.JSON, json);

            Request request = new Request.Builder()
                    .url(strurl)
                    .post(body)
                    .build();
            Response response = mClient.newCall(request).execute();
            LogUtils.i(TAG,funName+" response code "+response.code());
            if(response.isSuccessful()){

                String responsebody = response.body().string();
                LogUtils.i(TAG,funName+" responsebody  "+responsebody);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addPatrolCheck(String strurl, OkHttpClient mClient, ReportBean reportBean){
        String funName = "addVisitReport";

        try {
            Gson gson = new Gson();
            String json = gson.toJson(reportBean);
            RequestBody body = RequestBody.create(HttpPost.JSON, json);

            Request request = new Request.Builder()
                    .url(strurl)
                    .post(body)
                    .build();
            Response response = mClient.newCall(request).execute();
            LogUtils.i(TAG,funName+" response code "+response.code());
            if(response.isSuccessful()){

                String responsebody = response.body().string();
                LogUtils.i(TAG,funName+" responsebody  "+responsebody);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void imageUpload(String strurl, OkHttpClient mClient, String filepath,int id){

        String funName = "imageUpload";
        MultipartBody.Builder builder=  new MultipartBody.Builder().setType(MultipartBody.FORM);
        RequestBody body=builder.build();
        Request request = new Request.Builder()
                .url(strurl)
                .post(body)
                .build();
        try {
            Response response = mClient.newCall(request).execute();
            LogUtils.i(TAG, funName + " response code " + response.code());
            if (response.isSuccessful()) {

                String responsebody = response.body().string();
                LogUtils.i(TAG, funName + " responsebody  " + responsebody);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

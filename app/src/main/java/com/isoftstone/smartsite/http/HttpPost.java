package com.isoftstone.smartsite.http;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.R.attr.name;
import static android.R.attr.password;
import static android.R.attr.port;

/**
 * Created by guowei on 2017/10/14.
 */

public class HttpPost {
    private static OkHttpClient  mClient = null;
    private static String mUserName = "";
    private static String mToken = "";
    private String URL = "http://publicobject.com/helloworld.txt";

    public HttpPost(){
        if (mClient == null){
            mClient = new OkHttpClient();
        }
    }

    public LoginBean Login(String username,String password){
        LoginBean loginBean = null;
        /*RequestBody requestBody = new FormBody.Builder().add("username", username).add("password", password).build();
        Request request = new Request.Builder()
                .url(URL)
                .post(requestBody)
                .build();
        Response response = null;
        try {
            response = mClient.newCall(request).execute();
            if(response.isSuccessful()){
                String responsebody = response.body().toString();
                JSONObject json = new JSONObject(responsebody);
                String errorinfo = json.getString("errorinfo");
                int errorcode = json.getInt("errorcode");
                String date = json.getString("date");
                String results = json.getString("results");
                JSONObject resultJson = new JSONObject(results);
                String token = resultJson.getString("token");
                String viod_ip = resultJson.getString("viod_ip");
                String port = resultJson.getString("port");
                String name = resultJson.getString("name");
                String password = resultJson.getString("password");
                loginBean = new LoginBean();
                loginBean.setmErrorInfo(errorinfo);
                loginBean.setmErrorCode(errorcode);
                loginBean.setmDate(date);
                loginBean.setmToken(token);
                loginBean.setmViodIp(viod_ip);
                loginBean.setmPort(port);
                loginBean.setmName(name);
                loginBean.setmPassword(password);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }*/
        loginBean = new LoginBean();
        loginBean.setmErrorInfo("");
        loginBean.setmErrorCode(100);
        loginBean.setmDate("2014-06-13 14:19:00");
        loginBean.setmToken("abadaaddac");
        loginBean.setmViodIp("192.168.1.1");
        loginBean.setmPort("8080");
        loginBean.setmName("adb");
        loginBean.setmPassword("123");

        mUserName  = "abc";
        mToken = "abadaaddac";
        return loginBean;
    }

    public HomeBean getHomeDate(){
        HomeBean homeBean = null;
        /*RequestBody requestBody = new FormBody.Builder().add("username", mUserName).add("token", mToken).build();
        Request request = new Request.Builder()
                .url(URL)
                .post(requestBody)
                .build();
        Response response = null;
        try {
            response = mClient.newCall(request).execute();
            if(response.isSuccessful()){
                String responsebody = response.body().toString();
                JSONObject json = new JSONObject(responsebody);
                String errorinfo = json.getString("errorinfo");
                int errorcode = json.getInt("errorcode");
                String date = json.getString("date");
                String results = json.getString("results");
                JSONObject resultJson = new JSONObject(results);
                String city = resultJson.getString("city");
                String weather = resultJson.getString("weather");
                String weather_image = resultJson.getString("weather_image");
                String wind = resultJson.getString("wind");
                String wind_image = resultJson.getString("wind_image");
                String temperature = resultJson.getString("temperature");
                int message = resultJson.getInt("message");
                int report = resultJson.getInt("report");
                String video_device = resultJson.getString("video_device");
                String environment_device = resultJson.getString("environment_device");
                String message_list = resultJson.getString("message_list");
                JSONArray messagelistJson = new JSONArray(message_list);
                ArrayList<MessageListBean> list = new ArrayList<MessageListBean>();
                for (int i = 0; i < messagelistJson.length(); i ++){
                    JSONObject messageJsonObje = messagelistJson.getJSONObject(i);
                    String title = messageJsonObje.getString("title");
                    String detail = messageJsonObje.getString("detail");
                    int state = messageJsonObje.getInt("state");
                    String time = messageJsonObje.getString("time");
                    MessageListBean messageListBean = new MessageListBean();
                    messageListBean.setTime(title);
                    messageListBean.setDetail(detail);
                    messageListBean.setState(state);
                    messageListBean.setTime(time);
                    list.add(messageListBean);
                }
                homeBean = new HomeBean();
                homeBean.setErrorinfo(errorinfo);
                homeBean.setErrorcode(errorcode);
                homeBean.setDate(date);
                homeBean.setCity(city);
                homeBean.setWeather(weather);
                homeBean.setWeather_image(weather_image);
                homeBean.setWind(wind);
                homeBean.setWind_image(wind_image);
                homeBean.setTemperature(temperature);
                homeBean.setMessage(message);
                homeBean.setReport(report);
                homeBean.setVideo_device(video_device);
                homeBean.setEnvironment_device(environment_device);
                homeBean.setMessagelist(list);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }*/
        homeBean = new HomeBean();
        homeBean.setErrorinfo("");
        homeBean.setErrorcode(100);
        homeBean.setDate("2014-06-13 14:19:00");
        homeBean.setCity("武汉");
        homeBean.setWeather("晴天");
        homeBean.setWeather_image("http://www.baidu.com");
        homeBean.setWind("东风1级");
        homeBean.setWind_image("http://www.baidu.com");
        homeBean.setTemperature("26°C");
        homeBean.setMessage(17);
        homeBean.setReport(10);
        homeBean.setVideo_device("3/4");
        homeBean.setEnvironment_device("3/4");
        ArrayList<MessageListBean> list = new ArrayList<MessageListBean>();
        MessageListBean listBean_1 = new MessageListBean();
        listBean_1.setTitle("东湖高新区光谷六路");
        listBean_1.setDetail("视屏监控设备损坏");
        listBean_1.setState(1);
        listBean_1.setTime("14:19:00");
        list.add(listBean_1);
        list.add(listBean_1);
        list.add(listBean_1);
        list.add(listBean_1);
        list.add(listBean_1);
        list.add(listBean_1);
        list.add(listBean_1);
        list.add(listBean_1);
        list.add(listBean_1);
        list.add(listBean_1);
        list.add(listBean_1);
        list.add(listBean_1);
        list.add(listBean_1);
        homeBean.setMessagelist(list);
        return  homeBean;
    }


    public  PMDevicesListBean getPMDevicesList(String token,String username){
        PMDevicesListBean pmdeviceslist = null;
        /*RequestBody requestBody = new FormBody.Builder().add("username", mUserName).add("token", mToken).build();
        Request request = new Request.Builder()
                .url(URL)
                .post(requestBody)
                .build();
        Response response = null;
        try {
            response = mClient.newCall(request).execute();
            if(response.isSuccessful()){
                String responsebody = response.body().toString();
                JSONObject json = new JSONObject(responsebody);
                String errorinfo = json.getString("errorinfo");
                int errorcode = json.getInt("errorcode");
                String date = json.getString("date");
                String results = json.getString("results");
                JSONArray pmdevicesListJson = new JSONArray(results);
                ArrayList<PMDevicesBean> list = new ArrayList<PMDevicesBean>();
                for (int i = 0; i < pmdevicesListJson.length(); i ++){
                    JSONObject pmdevicesJsonObje = pmdevicesListJson.getJSONObject(i);
                    String name = pmdevicesJsonObje.getString("name");
                    String time = pmdevicesJsonObje.getString("time");
                    String address = pmdevicesJsonObje.getString("address");
                    int state = pmdevicesJsonObje.getInt("state");
                    String PM10 = pmdevicesJsonObje.getString("PM10");
                    String PM25 = pmdevicesJsonObje.getString("PM25");
                    String NO2 = pmdevicesJsonObje.getString("NO2");
                    String device_id = pmdevicesJsonObje.getString("device_id");
                    double longitude = pmdevicesJsonObje.getDouble("longitude");
                    double latitude = pmdevicesJsonObje.getDouble("latitude");
                    PMDevicesBean pmDevicesBean = new PMDevicesBean();
                    pmDevicesBean.setName(name);
                    pmDevicesBean.setTime(time);
                    pmDevicesBean.setAddress(address);
                    pmDevicesBean.setState(state);
                    pmDevicesBean.setPM10(PM10);
                    pmDevicesBean.setPM25(PM25);
                    pmDevicesBean.setNO2(NO2);
                    pmDevicesBean.setDevice_id(device_id);
                    pmDevicesBean.setLongitude(longitude);
                    pmDevicesBean.setLatitude(latitude);
                    list.add(pmDevicesBean);
                }

                pmdeviceslist = new PMDevicesListBean();
                pmdeviceslist.setErrorcode(errorcode);
                pmdeviceslist.setErrorinfo(errorinfo);
                pmdeviceslist.setDate(date);
                pmdeviceslist.setLsit(list);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }*/
        ArrayList<PMDevicesBean> list = new ArrayList<PMDevicesBean>();
        PMDevicesBean pmdevices_1 = new PMDevicesBean();
        pmdevices_1.setName("NCX-05");
        pmdevices_1.setTime("2017-09-29");
        pmdevices_1.setAddress("东湖高新区");
        pmdevices_1.setState(1);
        pmdevices_1.setPM10("32");
        pmdevices_1.setPM25("25");
        pmdevices_1.setNO2("123");
        pmdevices_1.setDevice_id("1332");
        pmdevices_1.setLongitude(123.321);
        pmdevices_1.setLatitude(178.321);
        list.add(pmdevices_1);
        PMDevicesBean pmdevices_2 = new PMDevicesBean();
        pmdevices_2.setName("NCX-06");
        pmdevices_2.setTime("2017-09-29");
        pmdevices_2.setAddress("东湖高新区");
        pmdevices_2.setState(1);
        pmdevices_2.setPM10("32");
        pmdevices_2.setPM25("25");
        pmdevices_2.setNO2("123");
        pmdevices_2.setDevice_id("1233");
        pmdevices_2.setLongitude(123.381);
        pmdevices_2.setLatitude(178.301);
        list.add(pmdevices_2);
        pmdeviceslist = new PMDevicesListBean();
        pmdeviceslist.setErrorinfo("");
        pmdeviceslist.setErrorcode(100);
        pmdeviceslist.setDate("2014-10-02 14:03:00");
        pmdeviceslist.setLsit(list);
        return  pmdeviceslist;
    }

    public PMDevicesDataInfoBean getPMDevicesDataInfo(String device_id){
        PMDevicesDataInfoBean pmdevicesDatainfo = null;
        /*RequestBody requestBody = new FormBody.Builder().add("username", mUserName).add("token", mToken).add("device_id", device_id).build();
        Request request = new Request.Builder()
                .url(URL)
                .post(requestBody)
                .build();
        Response response = null;
        try {
            response = mClient.newCall(request).execute();
            if(response.isSuccessful()){
                String responsebody = response.body().toString();
                JSONObject json = new JSONObject(responsebody);
                String errorinfo = json.getString("errorinfo");
                int errorcode = json.getInt("errorcode");
                String date = json.getString("date");
                String results = json.getString("results");
                JSONObject pmdevicesdatainfojson = new JSONObject(results);
                String name = pmdevicesdatainfojson.getString("name");
                String time = pmdevicesdatainfojson.getString("time");
                String address  = pmdevicesdatainfojson.getString("address");
                int state   = pmdevicesdatainfojson.getInt("state");
                String PM10 = pmdevicesdatainfojson.getString("PM10");
                String PM25 = pmdevicesdatainfojson.getString("PM2.5");
                String NO2  = pmdevicesdatainfojson.getString("NO2");
                String device_id  = pmdevicesdatainfojson.getString("device_id");
                String O3  = pmdevicesdatainfojson.getString("O3");
                String temperature  = pmdevicesdatainfojson.getString("temperature");
                String windSpeed  = pmdevicesdatainfojson.getString("windSpeed");
                String pressure  = pmdevicesdatainfojson.getString("pressure");
                String humidity  = pmdevicesdatainfojson.getString("humidity");
                String Precipitation  = pmdevicesdatainfojson.getString("Precipitation");

                pmdevicesDatainfo = new PMDevicesDataInfoBean();
                pmdevicesDatainfo.setErrorinfo(errorinfo);
                pmdevicesDatainfo.setErrorcode(errorcode);
                pmdevicesDatainfo.setDate(date);
                pmdevicesDatainfo.setName(name);
                pmdevicesDatainfo.setTime(time);
                pmdevicesDatainfo.setAddress(address);
                pmdevicesDatainfo.setState(state);
                pmdevicesDatainfo.setPM10(PM10);
                pmdevicesDatainfo.setPM25(PM25);
                pmdevicesDatainfo.setNO2(NO2);
                pmdevicesDatainfo.setDevice_id(device_id);
                pmdevicesDatainfo.setO3(O3);
                pmdevicesDatainfo.setTemperature(temperature);
                pmdevicesDatainfo.setWindSpeed(windSpeed);
                pmdevicesDatainfo.setPressure(pressure);
                pmdevicesDatainfo.setHumidity(humidity);
                pmdevicesDatainfo.setPrecipitation(Precipitation);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }*/
        pmdevicesDatainfo = new PMDevicesDataInfoBean();
        pmdevicesDatainfo.setErrorinfo("");
        pmdevicesDatainfo.setErrorcode(100);
        pmdevicesDatainfo.setDate("2014-10-12 15:22:57");
        pmdevicesDatainfo.setName("NCX-05");
        pmdevicesDatainfo.setTime("2017-09-29");
        pmdevicesDatainfo.setAddress("东湖高新区");
        pmdevicesDatainfo.setState(1);
        pmdevicesDatainfo.setPM10("32");
        pmdevicesDatainfo.setPM25("25");
        pmdevicesDatainfo.setNO2("49");
        pmdevicesDatainfo.setDevice_id("1332");
        pmdevicesDatainfo.setO3("46");
        pmdevicesDatainfo.setTemperature("0.426");
        pmdevicesDatainfo.setWindSpeed("0.426");
        pmdevicesDatainfo.setPressure("0.426");
        pmdevicesDatainfo.setHumidity("0.426");
        pmdevicesDatainfo.setPrecipitation("0.426");
        return pmdevicesDatainfo;
    }

    /*public PMDevicesDataListBean getPMDevicesDataList(String device_id){
        PMDevicesDataListBean pmdevicesdatalist = null;
        RequestBody requestBody = new FormBody.Builder().add("username", mUserName).add("token", mToken).add("device_id", device_id).build();
        Request request = new Request.Builder()
                .url(URL)
                .post(requestBody)
                .build();
        Response response = null;
        try {
            response = mClient.newCall(request).execute();
            if(response.isSuccessful()){
                String responsebody = response.body().toString();
                JSONObject json = new JSONObject(responsebody);
                String errorinfo = json.getString("errorinfo");
                int errorcode = json.getInt("errorcode");
                String date = json.getString("date");
                String results = json.getString("results");
                JSONObject pmdevicesdatalistjson = new JSONObject(results);
                String name = pmdevicesdatalistjson.getString("name");
                String time = pmdevicesdatalistjson.getString("time");
                String address  = pmdevicesdatalistjson.getString("address");
                int state   = pmdevicesdatalistjson.getInt("state");
                String data_list = pmdevicesdatalistjson.getString("data_list");
                JSONArray dataListJson = new JSONArray(data_list);
                ArrayList<PMDevicesDataInfoBean> list = new ArrayList<PMDevicesDataInfoBean>();
                for (int i = 0; i < dataListJson.length(); i ++) {
                    JSONObject datainfoJsonObje = dataListJson.getJSONObject(i);
                    String datainf_time = datainfoJsonObje.getString("time");
                    String PM10 = datainfoJsonObje.getString("PM10");
                    String PM25  = datainfoJsonObje.getString("PM2.5");
                    String NO2   = datainfoJsonObje.getString("NO2");
                    String datainfo_device_id   = datainfoJsonObje.getString("device_id");
                    PMDevicesDataInfoBean datainfo = new PMDevicesDataInfoBean();
                    datainfo.setTime(datainf_time);
                    datainfo.setPM10(PM10);
                    datainfo.setPM25(PM25);
                    datainfo.setNO2(NO2);
                    datainfo.setDevice_id(datainfo_device_id);
                    list.add(datainfo);
                }
                pmdevicesdatalist = new PMDevicesDataListBean();
                pmdevicesdatalist.setErrorinfo(errorinfo);
                pmdevicesdatalist.setErrorcode(errorcode);
                pmdevicesdatalist.setDate(date);
                pmdevicesdatalist.setAddress(address);
                pmdevicesdatalist.setName(name);
                pmdevicesdatalist.setState(state);
                pmdevicesdatalist.setTime(time);
                pmdevicesdatalist.setLsit(list);

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ArrayList<PMDevicesDataInfoBean> list = new ArrayList<PMDevicesDataInfoBean>();
        PMDevicesDataInfoBean datainfo_1 = new PMDevicesDataInfoBean();
        datainfo_1.setTime("2017-09-01 20:56");
        datainfo_1.setPM10("32");
        datainfo_1.setPM25("25");
        datainfo_1.setNO2("123");
        datainfo_1.setDevice_id("1332");
        list.add(datainfo_1);
        PMDevicesDataInfoBean datainfo_2 = new PMDevicesDataInfoBean();
        datainfo_2.setTime("2017-09-01 20:56");
        datainfo_2.setPM10("32");
        datainfo_2.setPM25("25");
        datainfo_2.setNO2("122");
        datainfo_2.setDevice_id("1332");
        list.add(datainfo_2);
        pmdevicesdatalist = new PMDevicesDataListBean();
        pmdevicesdatalist.setErrorinfo(errorinfo);
        pmdevicesdatalist.setErrorcode(errorcode);
        pmdevicesdatalist.setDate(date);
        pmdevicesdatalist.setAddress(address);
        pmdevicesdatalist.setName(name);
        pmdevicesdatalist.setState(state);
        pmdevicesdatalist.setTime(time);
        pmdevicesdatalist.setLsit(list);
        return pmdevicesdatalist;
    }*/

    public ReportListBean getReportList(){
        ReportListBean reportlist = null;
        /*RequestBody requestBody = new FormBody.Builder().add("username", mUserName).add("token", mToken).build();
        Request request = new Request.Builder()
                .url(URL)
                .post(requestBody)
                .build();
        Response response = null;
        try {
            response = mClient.newCall(request).execute();
            if(response.isSuccessful()){
                String responsebody = response.body().toString();
                JSONObject json = new JSONObject(responsebody);
                String errorinfo = json.getString("errorinfo");
                int errorcode = json.getInt("errorcode");
                String date = json.getString("date");
                String results = json.getString("results");
                JSONObject reportlistjson = new JSONObject(results);
                String reportList = reportlistjson.getString("reportList");
                String checkreport_list = reportlistjson.getString("checkreport_list");
                ArrayList<ReportBean> patrolList = new ArrayList<ReportBean>();
                JSONArray patrolJSONArray = new JSONArray(reportList);
                for (int i =0 ; i < patrolJSONArray.length(); i ++)
                {
                    JSONObject patrolJSON = patrolJSONArray.getJSONObject(i);
                    String report_id = patrolJSON.getString("report_id");
                    String report_name = patrolJSON.getString("report_name");
                    String name = patrolJSON.getString("name");
                    String time = patrolJSON.getString("time");
                    String address = patrolJSON.getString("address");
                    int stat = patrolJSON.getInt("stat");
                    ReportBean reportBean = new ReportBean();
                    reportBean.setReport_id(report_id);
                    reportBean.setReport_name(report_name);
                    reportBean.setName(name);
                    reportBean.setTime(time);
                    reportBean.setAddress(address);
                    reportBean.setStat(stat);
                    patrolList.add(reportBean);
                }
                ArrayList<ReportBean> checkList = new ArrayList<ReportBean>();
                JSONArray checkJSONArray = new JSONArray(checkreport_list);
                for (int i =0 ; i < checkJSONArray.length(); i ++)
                {
                    JSONObject checkJSON = checkJSONArray.getJSONObject(i);
                    String report_id = checkJSON.getString("report_id");
                    String report_name = checkJSON.getString("report_name");
                    String name = checkJSON.getString("name");
                    String time = checkJSON.getString("time");
                    String address = checkJSON.getString("address");
                    int stat = checkJSON.getInt("stat");
                    ReportBean reportBean = new ReportBean();
                    reportBean.setReport_id(report_id);
                    reportBean.setReport_name(report_name);
                    reportBean.setName(name);
                    reportBean.setTime(time);
                    reportBean.setAddress(address);
                    reportBean.setStat(stat);
                    checkList.add(reportBean);
                }
                reportlist = new ReportListBean();
                reportlist.setErrorcode(errorcode);
                reportlist.setErrorinfo(errorinfo);
                reportlist.setDate(date);
                reportlist.setPatrolRepoList(patrolList);
                reportlist.setCheckRepoList(checkList);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }*/
        ArrayList<ReportBean> patrolList = new ArrayList<ReportBean>();
        ArrayList<ReportBean> checkList = new ArrayList<ReportBean>();
        ReportBean reportBean = new ReportBean();
        reportBean.setReport_id("123456");
        reportBean.setReport_name("东湖高新巡查报告");
        reportBean.setName("zhangshan");
        reportBean.setTime("2017-09-20");
        reportBean.setAddress("东湖高新区光谷六路");
        reportBean.setStat(1);
        patrolList.add(reportBean);
        patrolList.add(reportBean);
        checkList.add(reportBean);
        checkList.add(reportBean);
        reportlist = new ReportListBean();
        reportlist.setErrorcode(100);
        reportlist.setErrorinfo("");
        reportlist.setDate("2014-10-02 12:45:41");
        reportlist.setPatrolRepoList(patrolList);
        reportlist.setCheckRepoList(checkList);
        return reportlist;
    }

    public ReportBean getPatrolRepo(String report_id){
        ReportBean reportBean = null;
        /*RequestBody requestBody = new FormBody.Builder().add("username", mUserName).add("token", mToken).add("report_id", report_id).build();
        Request request = new Request.Builder()
                .url(URL)
                .post(requestBody)
                .build();
        Response response = null;
        try {
            response = mClient.newCall(request).execute();
            if(response.isSuccessful()){
                String responsebody = response.body().toString();
                JSONObject json = new JSONObject(responsebody);
                String errorinfo = json.getString("errorinfo");
                int errorcode = json.getInt("errorcode");
                String date = json.getString("date");
                String results = json.getString("results");
                JSONObject reportjson = new JSONObject(results);
                String address = reportjson.getString("address");
                String name  = reportjson.getString("name");
                String image_list  = reportjson.getString("image_list");
                JSONArray imagelistJSONArray = new JSONArray(image_list);
                ArrayList<String> list = new ArrayList<String>();
                for (int i =0 ; i < imagelistJSONArray.length(); i ++) {
                    JSONObject imageJSON = imagelistJSONArray.getJSONObject(i);
                    String image_1 = checkJSON.getString("image_1");
                    String image_2 = checkJSON.getString("image_2");
                    String image_3 = checkJSON.getString("image_3");
                    list.add(image_1);
                    list.add(image_2);
                    list.add(image_3);
                }
                reportBean = new ReportBean();
                reportBean.setErrorinfo(errorinfo);
                reportBean.setErrorcode(errorcode);
                reportBean.setDate(date);
                reportBean.setAddress(address);
                reportBean.setName(name);
                reportBean.setImage_list(list);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }*/
        ArrayList<String> list = new ArrayList<String>();
        list.add("http://www.baodu.com");
        list.add("http://www.baodu.com");
        list.add("http://www.baodu.com");
        reportBean = new ReportBean();
        reportBean.setErrorinfo("");
        reportBean.setErrorcode(100);
        reportBean.setDate("2014-10-02 12:45:41");
        reportBean.setAddress("光谷中心城1号工地光谷中心城1号工地");
        reportBean.setName("zhangsan");
        reportBean.setImage_list(list);
        return reportBean;
    }
}
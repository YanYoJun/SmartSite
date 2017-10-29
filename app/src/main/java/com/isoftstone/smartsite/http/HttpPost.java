package com.isoftstone.smartsite.http;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.isoftstone.smartsite.common.App;
import com.isoftstone.smartsite.utils.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.R.attr.name;
import static android.R.attr.password;
import static android.R.attr.port;
import static android.R.attr.radioButtonStyle;

/**
 * Created by guowei on 2017/10/14.
 */

public class HttpPost {
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static OkHttpClient  mClient = null;
    public static String URL = "http://61.160.82.83:19090/ctess";
    private String LOGIN_URL = URL + "/login";                        //登录
    private String GET_VIDEO_CONFIG = URL + "/mobile/video/config";
    private String EQI_DATA_RANKING = URL + "/eqi/dataRanking";      //区域月度综合排名
    private String EQI_DATA_COMPARISON = URL + "/eqi/dataComparison";  //区域月度数据对比
    private String EQI_DAYS_PROPORTION = URL + "/eqi/daysProportion";  //优良天数占比
    private String EQI_WEATHER_LIVE = URL + "/eqi/weatherLive";
    private String EQI_LIST = URL + "/eqi/list";                       //单设备PM数据列表
    private String EQI_BYDEVICE_HISTORY = URL + "/eqi/byDevice/history/";
    private String EQI_BYDEVICE_DAYS = URL + "/eqi/byDevice/days";
    private String ESS_DEVICE_LIST = URL + "/EssDevice/list";
    private String MESSAGE_LIST = URL + "/message/list";
    private String MESSAGE_ID_READ = URL + "/message/{id}/read";
    private String USER_GET_LOGINUSER = URL + "/user/getLoginUser";
    private String USER_UPDATE_USER = URL + "/user/updateUser";



    private LoginBean mLoginBean = null;
    private static HashMap<HttpUrl, List<Cookie>> cookieStore = new HashMap<>();
    public HttpPost(){
        if (mClient == null){
            mClient = new OkHttpClient.Builder()
                    .cookieJar(new CookiesManager(App.getAppContext()))
                    .build();
        }
    }

    public static <T>  ArrayList<T> stringToList(String json ,Class<T> cls  ){
        Gson gson = new Gson();
        ArrayList<T> list = new ArrayList<T>();
        JsonArray array = new JsonParser().parse(json).getAsJsonArray();
        for(final JsonElement elem : array){
            list.add(gson.fromJson(elem, cls));
        }
        return list ;
    }

    public void test12(){
        //更新用户信息
        FormBody  body = new FormBody.Builder()
                .add("id", "7")
                .build();
        Request request = new Request.Builder()
                .url(USER_UPDATE_USER)
                .patch(body)
                .build();
        Response response = null;
        try {
            response = mClient.newCall(request).execute();
            Log.i("text10","------------------------------------"+response.code());
            if(response.isSuccessful()){

                String responsebody = response.body().string();
                Log.i("text","------------------------------------"+responsebody);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void test11(){
        //获取用户信息
        FormBody  body = new FormBody.Builder()
                .add("id", "7")
                .build();
        Request request = new Request.Builder()
                .url(USER_GET_LOGINUSER)
                .patch(body)
                .build();
        Response response = null;
        try {
            response = mClient.newCall(request).execute();
            Log.i("text10","------------------------------------"+response.code());
            if(response.isSuccessful()){

                String responsebody = response.body().string();
                Log.i("text","------------------------------------"+responsebody);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void test10(){
        //阅读消息
        FormBody  body = new FormBody.Builder()
                .add("id", "256")
                .build();
        Request request = new Request.Builder()
                .url(MESSAGE_ID_READ)
                .patch(body)
                .build();
        Response response = null;
        try {
            Log.i("text10","------------------------------------"+request.url().toString());
            response = mClient.newCall(request).execute();
            Log.i("text10","------------------------------------"+response.code());
            if(response.isSuccessful()){

                String responsebody = response.body().string();
                Log.i("text","------------------------------------"+responsebody);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void test4(){
        try {
            //天气实况
            JSONObject object = new JSONObject();
            //object.put("archId",47);
            //object.put("time","2017-10");
            Log.i("text","------------------------------------"+object.toString());
            RequestBody body = RequestBody.create(JSON, object.toString());
            Request request = new Request.Builder()
                    .url(EQI_WEATHER_LIVE)
                    .post(body)
                    .build();


            Response response = null;
            response = mClient.newCall(request).execute();
            Log.i("text","------------------------------------"+response.code());
            if(response.isSuccessful()){
                String responsebody = response.body().string();
                Log.i("text","------------------------------------"+responsebody);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } /*catch (JSONException e) {
            e.printStackTrace();
        }*/
    }






    public void getVideoConfig(){
        Request request = new Request.Builder()
                .url(GET_VIDEO_CONFIG)
                .get()
                .build();
        Response response = null;
        try {
            response = mClient.newCall(request).execute();
            if(response.isSuccessful()){
                mLoginBean = new LoginBean();
                String responsebody = response.body().string();
                Log.i("text","------------------------------------"+responsebody);
                JSONObject json = new JSONObject(responsebody);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
        listBean_1.setTitle("EEBS设备坏了");
        listBean_1.setDetail("视屏监控设备损坏请及时维修视屏监控设备损坏请及时维修视屏监控设备损坏请及时维修视屏监控设备损坏请及时维修");
        listBean_1.setState(1);
        listBean_1.setTime("14:19");
        list.add(listBean_1);
        listBean_1 = new MessageListBean();
        listBean_1.setTitle("E的设EBS备坏了");
        listBean_1.setDetail("视屏监控设备损坏请及时维修视屏监控设备损坏请及时维修视屏监控设备损坏请及时维修视屏监控设备损坏请及时维修");
        listBean_1.setState(2);
        listBean_1.setTime("14:19");
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


    public ReportListBean getReportList(){
        ReportListBean reportlist = null;
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


    public boolean isConnected(){
        return NetworkUtils.isConnected();
    }


    /*
    登录专用接口，传入用户名，密码，以及设备ID号
     */
    public LoginBean login(String username, String password, String mobileDeviceId){

        if (mLoginBean == null)
        {
            mLoginBean = new LoginBean();
        }

        LoginBean loginBean = UserLogin.login(LOGIN_URL,mClient,username,password,mobileDeviceId);
        if(loginBean != null){
            if(loginBean.isLoginSuccess()){
                mLoginBean.setLoginSuccess(true);
                mLoginBean.setmName(username);
                mLoginBean.setmPassword(password);
            }else{
                mLoginBean.setmErrorCode(loginBean.getmErrorCode());
            }
        }

        return  mLoginBean;
    }




    /*
     区域月度综合排名  测试object.put("archId",21); object.put("time","2017-10");
   */
    public EQIRankingBean  eqiDataRanking(String archId,String time){
        return EQIMonitoring.eqiDataRanking(EQI_DATA_RANKING,mClient,archId,time);
    }


    /*
     区域月度数据对比 测试数据 "29","2017-10","1"
     */
    public MonthlyComparisonBean carchMonthlyComparison(String archId,String time,String type){
        return  EQIMonitoring.carchMonthlyComparison(EQI_DATA_COMPARISON,mClient,archId,time,type);
    }

    /*
    优良天数占比
    "29","2017-10"
     */
    public ArrayList<WeatherConditionBean> getWeatherConditionDay(String archId,String time){
        return  EQIMonitoring.getWeatherConditionDay(EQI_DAYS_PROPORTION,mClient,archId,time);
    }

    /*
    2.2	单设备PM数据列表  "[1,2]","0","2017-10-01 00:00:00","2017-10-11 00:00:00"
    */
    public  OnePMDevicesData onePMDevicesDataList(String deviceIdsStr,String dataType,String beginTime,String endTime) {
          return EQIMonitoring.onePMDevicesDataList(EQI_LIST,mClient,deviceIdsStr,dataType,beginTime,endTime);
    }


   /*
   2.3	单设备PM历史数据    测试数据 "1"
    */
    public  ArrayList<OnePMDevicesData.PDDevicesData> getOneDevicesHistoryData(String id){
       return EQIMonitoring.getOneDevicesHistoryData(EQI_BYDEVICE_HISTORY,mClient,id);
    }


    /*2.5	单设备某天24小时数据  测试数据
     */

    /*public void onePMDevices24Data(String deviceIdsStr,String pushTime){
        EQIMonitoring.onePMDevices24Data(EQI_BYDEVICE_DAYS,mClient,deviceIdsStr,pushTime);
    }*/

    /*
    //获取设备列表—文昊炅  测试 "","","",""
     */
    public  ArrayList<DevicesBean>  getDevices(String deviceType,String deviceName,String archId,String deviceStatus){
        return EQIMonitoring.getDevicesList(ESS_DEVICE_LIST,mClient,deviceType,deviceName,archId,deviceStatus);
    }

    /*
     //获取消息列表   测试"","","","2"
     */
    public ArrayList<MessageBean> getMessage(String title, String type, String status, String module) {

        return  MessageOperation.getMessage(MESSAGE_LIST,mClient,title,type,status,module);
    }
}
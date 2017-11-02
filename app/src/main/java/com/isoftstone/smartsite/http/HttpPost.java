package com.isoftstone.smartsite.http;

import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.isoftstone.smartsite.User;
import com.isoftstone.smartsite.common.App;
import com.isoftstone.smartsite.utils.NetworkUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;

/**
 * Created by guowei on 2017/10/14.
 */

public class HttpPost {
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static OkHttpClient  mClient = null;
    public static String URL = "http://61.160.82.83:19090/ctess";

    private String LOGIN_URL = URL + "/login";                        //登录
    private String  GET_LOGIN_USER = URL + "/user/getLoginUser";      //获取登录用户信息
    private String   USER_UPDATE = URL + "/user/update";              //用户信息更改
    private String   USER_UPLOAD = URL + "/user/upload";              //用户头像修改

    private String GET_VIDEO_CONFIG = URL + "/mobile/video/config";   //获取视屏服务器参数
    private String MOBILE_HOME = URL + "/mobile/home";                //获取首页数据

    private String EQI_DATA_RANKING = URL + "/eqi/dataRanking";      //区域月度综合排名
    private String EQI_DATA_COMPARISON = URL + "/eqi/dataComparison";  //区域月度数据对比
    private String EQI_DAYS_PROPORTION = URL + "/eqi/daysProportion";  //优良天数占比
    private String EQI_WEATHER_LIVE = URL + "/eqi/weatherLive";        //获取实时天气情况
    private String EQI_LIST = URL + "/eqi/list";                       //单设备PM数据列表
    private String EQI_BYDEVICE_HISTORY = URL + "/eqi/byDevice/history/";  //获取一台设备历史参数
    private String EQI_BYDEVICE_DAYS = URL + "/eqi/byDevice/days";        //获取一台设备24小时数据


    private String ESS_DEVICE_LIST = URL + "/EssDevice/list";           //获取设备数据


    private String MESSAGE_LIST = URL + "/message/list";                //获取消息列表
    private String MESSAGE_ID_READ = URL + "/message/{id}/read";        //消息读取

    private String PATROL_LIST = URL + "/patrol/list";        //获取报告列表
    private String ADD_PATROL_REPORT  = URL + "/patrol";      //新增巡查报告
    private String GET_PATROL_REPORT = URL + "/patrol/";      //获取巡查报告
    private String ADD_REPORT  = URL + "/report";            //新增巡查报告回复 回访  验收
    private String IMAGE_UPLOAD  = URL + "/report/image/mobile";  //图片上传




    public static  LoginBean mLoginBean = null;
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

    public HomeBean getHomeDate(){
        HomeBean homeBean = null;

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
        ArrayList<ReportBeanBak> patrolList = new ArrayList<ReportBeanBak>();
        ArrayList<ReportBeanBak> checkList = new ArrayList<ReportBeanBak>();
        ReportBeanBak reportBean = new ReportBeanBak();
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

    public ReportBeanBak getPatrolRepo(String report_id){
        ReportBeanBak reportBean = null;
        ArrayList<String> list = new ArrayList<String>();
        list.add("http://www.baodu.com");
        list.add("http://www.baodu.com");
        list.add("http://www.baodu.com");
        reportBean = new ReportBeanBak();
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
    public  ArrayList<DataQueryVoBean> onePMDevicesDataList(String deviceIdsStr,String dataType,String beginTime,String endTime) {
          return EQIMonitoring.onePMDevicesDataList(EQI_LIST,mClient,deviceIdsStr,dataType,beginTime,endTime);
    }


   /*
   2.3	单设备PM历史数据    测试数据 "1"
    */
    public  ArrayList<PMDevicesDataBean> getOneDevicesHistoryData(String id){
       return EQIMonitoring.getOneDevicesHistoryData(EQI_BYDEVICE_HISTORY,mClient,id);
    }


    /*
    2.5	单设备某天24小时数据  测试数据 "2","2017-10-10 10:10:10"
     */

    public ArrayList<DataQueryVoBean> onePMDevices24Data(String deviceIdsStr,String pushTime){
        return  EQIMonitoring.onePMDevices24Data(EQI_BYDEVICE_DAYS,mClient,deviceIdsStr,pushTime);
    }

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

    /*
    消息阅读已经完成
     */
    public void readMessage(String id){
        MessageOperation.readMessage(MESSAGE_ID_READ,mClient,id);
    }

    /*
     2.2	获取视频配置
     */
    public boolean getVideoConfig(){
        boolean flag = false;
        LoginBean.VideoParameter videoParameter = UserLogin.getVideoConfig(GET_VIDEO_CONFIG,mClient);
        if(videoParameter != null){
            mLoginBean.setmVideoParameter(videoParameter);
            flag = true;
        }
        return flag;
    }


    /*
    天气实况  测试数据"47","2017-10"
     */
    public WeatherLiveBean getWeatherLive(String archId,String time){
        return  EQIMonitoring.getWeatherLive(EQI_WEATHER_LIVE,mClient,archId,time);
    }


    /*
     获取巡查报告列表  测试数据 1
     */
    public ArrayList<PatrolBean>  getPatrolReportList(int status){
        return  ReportOperation.getPatrolReportList(PATROL_LIST,mClient,status);
    }

    /*
    新增巡查报告
     */
    public PatrolBean addPatrolReport(PatrolBean reportBean){
        return ReportOperation.addPatrolReport(ADD_PATROL_REPORT,mClient,reportBean);
    }


    /*
    获取一个巡查报告   测试"75"
     */
    public PatrolBean getPatrolReport(String id){
        return  ReportOperation.getPatrolReport(GET_PATROL_REPORT,mClient,id);
    }

    /*
    新增巡查回访
     */
    public  void addPatrolVisit(ReportBean reportBean){
         ReportOperation.addPatrolVisit(ADD_REPORT,mClient,reportBean);
    }

    /*
    新增巡查回复
     */
    public void addPatrolReply(ReportBean reportBean){
        ReportOperation.addPatrolReply(ADD_REPORT,mClient,reportBean);
    }

    /*
    新增巡查验收
     */
    public void addPatrolCheck(ReportBean reportBean){
        ReportOperation.addPatrolCheck(ADD_REPORT,mClient,reportBean);
    }

    /*
    报告上传图片
     */
    public  void reportImageUpload(String filepath,int id){
        ReportOperation.reportImageUpload(IMAGE_UPLOAD,mClient,filepath,id);
    }
    /*
    报告上传文件
     */
    public void reportFileUpload(String filepath,int id){
        ReportOperation.reportFileUpload(IMAGE_UPLOAD,mClient,filepath,id);
    }


    //下载报告图片，需要传入id和服务器获取的到路径
    public void downloadReportFile(int id,String filename){

        String url = getFileUrl(filename);
        String name = getFileName(filename);
        String sdpath = Environment.getExternalStorageDirectory().getPath();
        String storagePath = sdpath + "/isoftstone/"+mLoginBean.getmName()+"/report/"+id;
        File file = new File(storagePath);
        if(!file.exists()){
            file.mkdirs();
        }
        if (file.exists())Log.i("Test","url  "+url+"  storagePath  "+storagePath+" name "+name+"   "+getReportPath(id,filename));
        ReportOperation.downloadfile(url,storagePath,name);
    }

    /*
    获取用户信息
    */
    public UserBean getLoginUser(){
        UserBean userBean = new UserBean();
        userBean.setAccount(mLoginBean.getmName());
        userBean.setPassword(mLoginBean.getmPassword());
        return  UserLogin.getLoginUser(GET_LOGIN_USER,mClient,userBean);
    }
    //更改用户信息
    public void userUpdate(UserBean userBean){
        UserLogin.userUpdate(USER_UPDATE,mClient,userBean);
    }

    //上传用户头像
    public void userImageUpload(Bitmap bit,Bitmap.CompressFormat format){
        UserLogin.userImageUpload(USER_UPLOAD,mClient,bit,format);
    }
    //下载用户图片  服务器获取的到路径
    public void downloadUserImage(String filename){

        String url = getFileUrl(filename);
        String name = getFileName(filename);
        String sdpath = Environment.getExternalStorageDirectory().getPath();
        String storagePath = sdpath + "/isoftstone/"+mLoginBean.getmName()+"/usericon";
        File file = new File(storagePath);
        if(!file.exists()){
            file.mkdirs();
        }

        if (file.exists())Log.i("Test","url  "+url+"  storagePath  "+storagePath+" name "+name+"   "+getImagePath(filename));
        ReportOperation.downloadfile(url,storagePath,name);
    }
    /*
    获取主界面数据
     */
    public  MobileHomeBean getMobileHomeData(){
       return UserLogin.getMobileHomeData(MOBILE_HOME,mClient);
    }



    //获取下载文件、图片的URL
    public  String  getFileUrl(String filename){
        filename = filename.replaceAll("\\\\","/");
        return URL + "/"+filename;
    }

    //获取用户图片保存绝对路径
    public String getImagePath(String imageName){

        String sdpath = Environment.getExternalStorageDirectory().getPath();
        String storagePath = sdpath + "/isoftstone/"+mLoginBean.getmName()+"/usericon/"+getFileName(imageName);
        return  storagePath;
    }

    //获取用户图片保存绝对路径
    public String getReportPath(int id,String imageName){

        String sdpath = Environment.getExternalStorageDirectory().getPath();
        String storagePath = sdpath + "/isoftstone/"+mLoginBean.getmName()+"/report/"+id+"/"+getFileName(imageName); ;
        return  storagePath;
    }


    private String  getFileName(String filename){
        int index = filename.lastIndexOf("\\");
        if(index > 0){
            return  filename.substring(index+1);
        }
        return  filename;
    }
}
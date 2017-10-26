package com.isoftstone.smartsite.model.main.ui;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.isoftstone.smartsite.R;
import com.isoftstone.smartsite.common.widget.AlertView;
import com.isoftstone.smartsite.http.HttpPost;
import com.isoftstone.smartsite.http.VideoMonitorBean;
import com.isoftstone.smartsite.model.video.VideoRePlayListActivity;
import com.isoftstone.smartsite.utils.ToastUtils;
import com.uniview.airimos.listener.OnQueryReplayListener;
import com.uniview.airimos.listener.OnQueryResourceListener;
import com.uniview.airimos.manager.ServiceManager;
import com.uniview.airimos.obj.QueryCondition;
import com.uniview.airimos.obj.RecordInfo;
import com.uniview.airimos.obj.ResourceInfo;
import com.uniview.airimos.parameter.QueryReplayParam;
import com.uniview.airimos.parameter.QueryResourceParam;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by gone on 2017/10/17.
 * modifed by zhangyinfu on 2017/10/19
 */

public class VideoMonitoringActivity extends Activity implements VideoMonitorAdapter.AdapterViewOnClickListener, View.OnClickListener{
    private static final String TAG = "zyf_VideoMonitoring";
    private HttpPost mHttpPost = new HttpPost();
    private ListView mListView = null;
    private Context mContext;
    private VideoMonitorAdapter.ViewHolder mViewHolder;

    @Override
    protected void onStart() {
        super.onStart();
        mContext = getApplicationContext();
    }

    private void setResourceDate() {
        //查询资源参数
        QueryResourceParam params = new QueryResourceParam("", "", new QueryCondition(0, 200, true));

        //查询资源结果监听
        OnQueryResourceListener sListener = new OnQueryResourceListener() {
            @Override
            public void onQueryResourceResult(long errorCode, String errorDesc, List<ResourceInfo> resList) {

                if (errorCode != 0 || resList == null){
                    Toast.makeText(mContext,"error info: " + errorDesc,Toast.LENGTH_SHORT).show();
                    return;
                }
                if (resList.size() == 0){
                    Toast.makeText(mContext,"查询不到摄像机资源...",Toast.LENGTH_SHORT).show();
                    return;
                }

                StringBuffer strBuf = new StringBuffer();
                int size = resList.size();

                ArrayList<VideoMonitorBean> sList = new ArrayList<VideoMonitorBean>();
                VideoMonitorBean video;
                for (int i = 0; i < size; i++) {
                    strBuf.append(resList.get(i).getResCode() + ",");
                    strBuf.append(resList.get(i).getResName() + ",");
                    strBuf.append(resList.get(i).getResType() + ",");
                    strBuf.append(resList.get(i).getResSubType() + ",");
                    strBuf.append(resList.get(i).getIsOnline() + "\n");

                    /**if (resList.get(i).getResType() == ResourceInfo.ResType.CAMERA && resList.get(i).getIsOnline()) {
                        mEditCamCode.setText(resList.get(i).getResCode());
                        break;
                    }*/
                    video = new VideoMonitorBean(resList.get(i).getResCode(), resList.get(i).getResName()
                            ,resList.get(i).getResType(), resList.get(i).getResSubType(),resList.get(i).getIsOnline(),resList.get(i).getIsShared());
                    sList.add(video);
                }

                //Log.i(TAG, strBuf.toString());
                VideoMonitorAdapter adapter = new VideoMonitorAdapter(VideoMonitoringActivity.this);
                adapter.setData(sList);
                mListView.setAdapter(adapter);
            }
        };

        //查询资源接口调用
        ServiceManager.queryResource(params, sListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videomonitoring);
        init();
        //查询设备列表,并填充ListView数据
        setResourceDate();
    }

    private void init(){
        mListView = (ListView) findViewById(R.id.list);
    }

    @Override
    public void viewOnClickListener(VideoMonitorAdapter.ViewHolder viewHolder, boolean isFormOneType) {
        mViewHolder = viewHolder;
        if (isFormOneType) {
            initAlertView();
            alertShowExt();
        } else {
            //打开系统相册浏览照片  
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("content://media/internal/images/media"));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }

    }

    private AlertView mAlertViewExt;//窗口拓展例子
    private ViewGroup mViewGrounp;
    private ImageView mCancleDialogIV;
    private EditText mBeginTimeET;
    private ImageView mBeginTimeIV;
    private EditText mEndTimeET;
    private ImageView mEndTimeIV;
    private Button mOkBtn;

    public void initAlertView() {
        //拓展窗口
        mAlertViewExt = new AlertView(null,null, null, null, null, this, AlertView.Style.Alert, null);
        mViewGrounp = (ViewGroup) LayoutInflater.from(this).inflate(R.layout.alertext_form,null);
        mCancleDialogIV = (ImageView)mViewGrounp.findViewById(R.id.canlce_dialog_iv);
        mBeginTimeET = (EditText)mViewGrounp.findViewById(R.id.begin_time_et);
        mBeginTimeIV = (ImageView)mViewGrounp.findViewById(R.id.begin_time_iv);
        mEndTimeET = (EditText)mViewGrounp.findViewById(R.id.end_time_et);
        mEndTimeIV = (ImageView)mViewGrounp.findViewById(R.id.end_time_iv);
        mOkBtn = (Button)mViewGrounp.findViewById(R.id.ok_btn);

        mBeginTimeIV.setOnClickListener(this);
        mEndTimeIV.setOnClickListener(this);
        mCancleDialogIV.setOnClickListener(this);
        mOkBtn.setOnClickListener(this);

        Date now = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String endTime = formatter.format(now);
        mEndTimeET.setText(endTime);

        mAlertViewExt.addExtView(mViewGrounp);
    }

    public void alertShowExt() {
        mAlertViewExt.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.begin_time_iv:
                showDatePickerDialog(mBeginTimeET);
                break;
            case R.id.end_time_iv:
                showDatePickerDialog(mEndTimeET);
                break;
            case R.id.canlce_dialog_iv:
                Toast.makeText(this, "canlce", Toast.LENGTH_SHORT).show();
                mAlertViewExt.dismiss();
                break;
            case R.id.ok_btn:
                String beginTime = mBeginTimeET.getText().toString();
                String endTime =  mEndTimeET.getText().toString();

                if ((null == beginTime) || (beginTime.length() <= 0)) {
                    ToastUtils.showShort(getText(R.string.data_pick_dialog_error_msg).toString());
                    return;
                }

                if (isDateOneBigger(beginTime, endTime)) {
                    ToastUtils.showShort(getText(R.string.data_pick_dialog_error_msg2).toString());
                    return;
                };

                String cameraCode = mViewHolder.resCodeTv.getText().toString();
                //ToastUtils.showShort(cameraCode);
                queryReplayVideo(cameraCode,beginTime + " 00:00:00", endTime + " 23:59:59");
                break;
            default:
                break;
        }
    }

    public void showDatePickerDialog(final EditText editText) {
        final Calendar sCalendar = Calendar.getInstance();
        ToastUtils.showShort("showDatePickerDialog");
        DatePickerDialog dialog = new DatePickerDialog(VideoMonitoringActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                sCalendar.set(year, monthOfYear, dayOfMonth);
                editText.setText(DateFormat.format("yyy-MM-dd", sCalendar).toString());
            }
        }, sCalendar.get(Calendar.YEAR), sCalendar.get(Calendar.MONTH), sCalendar.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }

    @Override
    public void onBackPressed() {
        if (null != mAlertViewExt && mAlertViewExt.isShowing()) {
            mAlertViewExt.dismiss();
        } else {
            super.onBackPressed();
        }
    }

    /**
     * 比较两个日期的大小，日期格式为yyyy-MM-dd
     *
     * @param str1 the first date
     * @param str2 the second date
     * @return true <br/>false
     */
    public static boolean isDateOneBigger(String str1, String str2) {
        boolean isBigger = false;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dt1 = null;
        Date dt2 = null;
        try {
            dt1 = sdf.parse(str1);
            dt2 = sdf.parse(str2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (dt1.getTime() > dt2.getTime()) {
            isBigger = true;
        } else if (dt1.getTime() < dt2.getTime()) {
            isBigger = false;
        }
        return isBigger;
    }

    public void queryReplayVideo(final String cameraCode, final String beginTime, final String endTime) {
        //查询回放记录参数
        QueryReplayParam p = new QueryReplayParam(cameraCode, beginTime, endTime, new QueryCondition(0, 100, true));

        //查询回放记录结果监听
        OnQueryReplayListener queryListener = new OnQueryReplayListener() {
            @Override
            public void onQueryReplayResult(long errorCode, String errorDesc, List<RecordInfo> recordList) {
                if (errorCode != 0 || recordList == null ){
                    Toast.makeText(mContext,"error info: " + errorDesc, Toast.LENGTH_SHORT).show();
                    return;
                }

                if (recordList.size() <= 0) {
                    Toast.makeText(mContext,"此时段没有录像...",Toast.LENGTH_SHORT).show();
                    return;
                }

                for (int i=0; i < recordList.size(); i++) {
                    ToastUtils.showShort(recordList.get(i).getFileName());
                }

                mAlertViewExt.dismiss();

                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("resCode", mViewHolder.resCodeTv.getText().toString());
                bundle.putInt("resSubType", mViewHolder.resSubType);
                bundle.putString("resName", mViewHolder.resNameTv.getText().toString());
                bundle.putBoolean("isOnline", mViewHolder.isOnline);
                bundle.putString("beginTime", beginTime);
                bundle.putString("endTime", endTime);
                //Toast.makeText(mContext, "ViewHolder: " +  ((ViewHolder)rootView.getTag()).name.getText().toString(), Toast.LENGTH_SHORT).show();
                intent.putExtras(bundle);
                intent.setClass(mContext, VideoRePlayListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        };

        //先查询指定时间段内有的回放记录
        ServiceManager.queryReplay(p, queryListener);
    }
}

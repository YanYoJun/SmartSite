package com.isoftstone.smartsite.model.video;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import com.isoftstone.smartsite.R;
import com.uniview.airimos.Player;
import com.uniview.airimos.listener.OnQueryReplayListener;
import com.uniview.airimos.listener.OnStartReplayListener;
import com.uniview.airimos.manager.ServiceManager;
import com.uniview.airimos.obj.QueryCondition;
import com.uniview.airimos.obj.RecordInfo;
import com.uniview.airimos.parameter.QueryReplayParam;
import com.uniview.airimos.parameter.StartReplayParam;
import com.uniview.airimos.thread.RecvStreamThread;

import java.util.List;

/**
 * Created by zhangyinfu on 2017/10/20.
 */

public class VideoRePlayActivity extends Activity{
    private static final String TAG = "zyf_RePlayVideoActivity";

    private SurfaceView mSurfaceView;
    private Player mPlayer;
    private Context mContext;
    private RecvStreamThread mRecvStreamThread = null;

    //static {
    //    System.loadLibrary("imosplayer");
    //}

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        setContentView(R.layout.activity_video_replay);
        mContext = this;
        //SurfaceView用于渲染
        mSurfaceView = (SurfaceView) findViewById(R.id.surface_view);

        //监听SurfaceView的变化
        mSurfaceView.getHolder().addCallback(new surfaceCallback());

        //初始化一个Player对象
        mPlayer = new Player();
        mPlayer.AVInitialize(mSurfaceView.getHolder());

        /*获取Intent中的Bundle对象*/
        if(bundle == null) {
            bundle = this.getIntent().getExtras();
        }

        /*获取Bundle中的数据，注意类型和key*/
        String resCode = bundle.getString("resCode");
        Log.i(TAG,"--------------resCode-------" + resCode);
        String beginTime = bundle.getString("beginTime");
        Log.i(TAG,"--------------beginTime-------" + beginTime);
        String endTime = bundle.getString("endTime");
        Log.i(TAG,"--------------endTime-------" + endTime);
        startReplay(resCode,beginTime,endTime);
    }

    /**
     * 启动实况
     *
     * @param cameraCode 摄像机编码
     */
    private void startReplay(final String cameraCode, String beginTime, String endTime) {


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


                //取第一条回放记录测试回放
                RecordInfo firstRecord = recordList.get(0);
                Toast.makeText(mContext,"此时段有录像...size =" + recordList.size(),Toast.LENGTH_SHORT).show();
                //启动回放的参数
                StartReplayParam p = new StartReplayParam();
                p.setCameraCode(cameraCode);
                p.setRecodeInfo(firstRecord);
                p.setBitrate(64 * 8);  //64KB码率
                p.setFramerate(20);     //20帧率
                p.setResolution(2);     //4CIF分辨率


                OnStartReplayListener listener = new OnStartReplayListener() {
                    @Override
                    public void onStartReplayResult(long errorCode, String errorDesc, String playSession) {
                        //设播放会话给Player
                        mPlayer.setPlaySession(playSession);

                        //先停掉已有的播放
                        if (mRecvStreamThread != null) {
                            mPlayer.AVStopPlay();
                            mRecvStreamThread.interrupt();
                            mRecvStreamThread = null;
                        }

                        //启动播放解码
                        mPlayer.AVStartPlay();

                        //启动收流线程
                        mRecvStreamThread = new RecvStreamThread(mPlayer, playSession);
                        mRecvStreamThread.start();
                    }
                };

                //启动回放
                ServiceManager.startReplay(p, listener);
            }
        };

        //先查询指定时间段内有的回放记录
        ServiceManager.queryReplay(p, queryListener);

    }

    @Override
    protected void onDestroy() {
        //销毁Player
        if (null != mPlayer) {
            mPlayer.AVFinalize();
            mPlayer = null;
        }

        super.onDestroy();
    }

    class surfaceCallback implements SurfaceHolder.Callback {

        public void surfaceCreated(SurfaceHolder holder) {
            Log.d(TAG, "===== surfaceCreated =====");
        }

        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            Log.d(TAG, "===== surfaceChanged =====");
            if (mPlayer != null) {
                mPlayer.changeDisplaySize(width, height);
            }
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder arg0) {
            Log.d(TAG, "===== surfaceDestroyed =====");
        }
    }
}

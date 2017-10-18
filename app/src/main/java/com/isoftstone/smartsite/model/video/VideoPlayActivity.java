package com.isoftstone.smartsite.model.video;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import com.isoftstone.smartsite.R;
import com.uniview.airimos.Player;
import com.uniview.airimos.listener.OnStartLiveListener;
import com.uniview.airimos.listener.OnStopLiveListener;
import com.uniview.airimos.manager.ServiceManager;
import com.uniview.airimos.parameter.StartLiveParam;
import com.uniview.airimos.thread.RecvStreamThread;

/**
 * Created by gone on 2017/10/17.
 * modifed by zhangyinfu on 2017/10/19
 */

public class VideoPlayActivity extends Activity{
    private static final String TAG = "zyf_VideoPlayActivity";

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

        setContentView(R.layout.activity_videoplay);
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
        String resCode = bundle.getString("ResCode");
        Log.i(TAG,"--------------resCode-------" + resCode);
        startLive(resCode);
    }

    /**
     * 启动实况
     *
     * @param cameraCode 摄像机编码
     */
    public void startLive(String cameraCode) {
        try {
            //启动实况的结果监听
            OnStartLiveListener listener = new OnStartLiveListener() {
                @Override
                public void onStartLiveResult(long errorCode, String errorDesc, String playSession) {
                    if (errorCode == 0){

                        //先停掉别的接收流线程
                        if (mRecvStreamThread != null) {
                            mPlayer.AVStopPlay();
                            mRecvStreamThread.interrupt();
                            mRecvStreamThread = null;
                        }

                        //将播放会话设给Player
                        mPlayer.setPlaySession(playSession);
                        //启动播放解码
                        mPlayer.AVStartPlay();

                        //收流线程启动
                        mRecvStreamThread = new RecvStreamThread(mPlayer, playSession);
                        mRecvStreamThread.start();
                    }else{
                        Toast.makeText(VideoPlayActivity.this,errorDesc,Toast.LENGTH_SHORT).show();
                    }

                }

            };

            //设置启动实况的参数
            StartLiveParam p = new StartLiveParam();
            p.setCameraCode(cameraCode);
            p.setUseSecondStream(true); //使用辅流
            p.setBitrate(32 * 8);   //64KB的码率
            p.setFramerate(12);     //25帧率
            p.setResolution(2);     //4CIF分辨率

            //启动实况
            ServiceManager.startLive(p, listener);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopLive() {
        //停止实况，第二个参数是null表示不接收结果
        ServiceManager.stopLive(mPlayer.getPlaySession(), new OnStopLiveListener() {
            @Override
            public void onStopLiveResult(long errorCode, String errorMsg) {
                if (errorCode == 0){
                    if (mRecvStreamThread != null){
                        mRecvStreamThread.interrupt();
                        mRecvStreamThread = null;
                    }

                    //停止Player播放解码
                    mPlayer.AVStopPlay();
                }
            }
        });

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

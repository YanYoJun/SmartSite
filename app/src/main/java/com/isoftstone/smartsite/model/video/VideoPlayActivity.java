package com.isoftstone.smartsite.model.video;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import com.isoftstone.smartsite.R;
import com.isoftstone.smartsite.model.main.view.RoundMenuView;
import com.isoftstone.smartsite.utils.ToastUtils;
import com.uniview.airimos.Player;
import com.uniview.airimos.listener.OnPtzCommandListener;
import com.uniview.airimos.listener.OnStartLiveListener;
import com.uniview.airimos.listener.OnStopLiveListener;
import com.uniview.airimos.manager.ServiceManager;
import com.uniview.airimos.parameter.PtzCommandParam;
import com.uniview.airimos.parameter.StartLiveParam;
import com.uniview.airimos.thread.RecvStreamThread;

/**
 * Created by gone on 2017/10/17.
 * modifed by zhangyinfu on 2017/10/19
 */

public class VideoPlayActivity extends Activity implements View.OnClickListener{
    private static final String TAG = "zyf_VideoPlayActivity";

    private SurfaceView mSurfaceView;
    private Player mPlayer;
    private Context mContext;
    private RecvStreamThread mRecvStreamThread = null;
    private RoundMenuView mRoundMenuView;
    private ImageView mImageView;
    private String mCameraCode;
    private static  final int GRAY_9999 = Color.GREEN;
    private static  final int GRAY_F2F2 = Color.BLUE;

    private int mSurfaceViewWidth;
    private int mSurfaceViewHeight;
    //固定摄像机：1; 云台摄像机：2; 高清固定摄像机：3; 高清云台摄像机：4; 车载摄像机：5; 不可控标清摄像机：6; 不可控高清摄像机：7;
    private static final int CAMERA_TYPE_TOW = 2;
    private static final int CAMERA_TYPE_FOUR = 4;


    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        setContentView(R.layout.activity_video_play);
        mContext = this;
        //SurfaceView用于渲染
        mSurfaceView = (SurfaceView) findViewById(R.id.surface_view);

        //监听SurfaceView的变化
        mSurfaceView.getHolder().addCallback(new surfaceCallback());

        mSurfaceView.setZOrderOnTop(true);
        mSurfaceView.setZOrderMediaOverlay(true);
        //初始化一个Player对象
        mPlayer = new Player();
        mPlayer.AVInitialize(mSurfaceView.getHolder());

        mRoundMenuView = (RoundMenuView)findViewById(R.id.round_menu_view);

        //获取设备屏幕大小信息
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        mSurfaceViewWidth = dm.widthPixels;
        mSurfaceViewHeight = dm.heightPixels;

        mImageView = (ImageView) findViewById(R.id.capture_view);
        mImageView.setOnClickListener(this);


        /*获取Intent中的Bundle对象*/
        if(bundle == null) {
            bundle = this.getIntent().getExtras();
        }
        /*获取Bundle中的数据，注意类型和key*/
        mCameraCode = bundle.getString("resCode");
        int resSubType = bundle.getInt("resSubType");
        Log.i(TAG,"--------------mCameraCode-------" + mCameraCode + ";   resSubType = " +  resSubType);

        if( (CAMERA_TYPE_TOW == resSubType) || (CAMERA_TYPE_FOUR ==  resSubType)) {
            Log.i(TAG,"--------------zyf----VISIBLE---");
            //初始化摇杆控件
            mRoundMenuView.setVisibility(View.VISIBLE);
            initRoundMenuView();
        } else {
            Log.i(TAG,"--------------zyf----GONE---");
            mRoundMenuView.setVisibility(View.GONE);
        }
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
                        //修改監控界面大小為當前屏幕大小
                        mPlayer.changeDisplaySize(mSurfaceViewWidth, mSurfaceViewHeight);

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
                    mImageView.setEnabled(false);
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

    @Override
    protected void onPause() {
        if (null != mPlayer && mPlayer.AVIsPlaying()) {
            stopLive();
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        if (null != mPlayer && !mPlayer.AVIsPlaying()) {
            startLive(mCameraCode);
        }
        super.onResume();
    }

    public void initRoundMenuView() {
        RoundMenuView.RoundMenu roundMenu = new RoundMenuView.RoundMenu();
        roundMenu.selectSolidColor = GRAY_9999;//Integer.parseInt(toHexEncoding(Color.GRAY));
        roundMenu.strokeColor = GRAY_F2F2;//Integer.parseInt(toHexEncoding(Color.GRAY));//ColorUtils.getColor(mContext, R.color.gray_9999);
        roundMenu.icon= drawableToBitmap(mContext,R.drawable.star);
        roundMenu.onClickListener=new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.showShort("点击了down");
                ptzCommand(mCameraCode, PtzCommandParam.PTZ_CMD.TILTDOWN);
            }
        };
        mRoundMenuView.addRoundMenu(roundMenu);

        roundMenu = new RoundMenuView.RoundMenu();
        roundMenu.selectSolidColor = GRAY_9999;//ColorUtils.getColor(getActivity(), R.color.gray_9999);
        roundMenu.strokeColor = GRAY_F2F2;//ColorUtils.getColor(getActivity(), R.color.gray_9999);
        roundMenu.icon= drawableToBitmap(mContext,R.drawable.star);//ImageUtils.drawable2Bitmap(getActivity(),R.drawable.ic_right);
        roundMenu.onClickListener=new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.showShort("点击了left");
                ptzCommand(mCameraCode, PtzCommandParam.PTZ_CMD.PANLEFT);
            }
        };
        mRoundMenuView.addRoundMenu(roundMenu);

        roundMenu = new RoundMenuView.RoundMenu();
        roundMenu.selectSolidColor = GRAY_9999;//Integer.parseInt(toHexEncoding(R.color.gray_9999));//ColorUtils.getColor(getActivity(), R.color.gray_9999);
        roundMenu.strokeColor = GRAY_F2F2;//Integer.parseInt(toHexEncoding(R.color.gray_9999));//ColorUtils.getColor(getActivity(), R.color.gray_9999);
        roundMenu.icon= drawableToBitmap(mContext,R.drawable.star);//ImageUtils.drawable2Bitmap(getActivity(),R.drawable.ic_right);
        roundMenu.onClickListener=new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.showShort("点击了up");
                ptzCommand(mCameraCode, PtzCommandParam.PTZ_CMD.TILTUP);
            }
        };
        mRoundMenuView.addRoundMenu(roundMenu);

        roundMenu = new RoundMenuView.RoundMenu();
        roundMenu.selectSolidColor = GRAY_9999;//Integer.parseInt(toHexEncoding(R.color.gray_9999));//ColorUtils.getColor(getActivity(), R.color.gray_9999);
        roundMenu.strokeColor = GRAY_F2F2;//Integer.parseInt(toHexEncoding(R.color.gray_9999));//ColorUtils.getColor(getActivity(), R.color.gray_9999);
        roundMenu.icon= drawableToBitmap(mContext,R.drawable.star);//ImageUtils.drawable2Bitmap(getActivity(),R.drawable.ic_right);
        roundMenu.onClickListener=new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.showShort("点击了right");
                ptzCommand(mCameraCode, PtzCommandParam.PTZ_CMD.PANRIGHT);
            }
        };
        mRoundMenuView.addRoundMenu(roundMenu);

        /**mRoundMenuView.setCoreMenu(ColorUtils.getColor(getActivity(), R.color.gray_f2f2),
                ColorUtils.getColor(getActivity(), R.color.gray_9999), ColorUtils.getColor(getActivity(), R.color.gray_9999)
                , 1, 0.43,ImageUtils.drawable2Bitmap(getActivity(),R.drawable.ic_ok), new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ToastUtils.showToast(getActivity(),"点击了中心圆圈");
                    }
                });*/

        mRoundMenuView.setCoreMenu(GRAY_F2F2, GRAY_9999, GRAY_F2F2
                , 1, 0.43, drawableToBitmap(mContext, R.drawable.ic_stop)
                , new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ToastUtils.showShort("点击了中心圆圈");
                        ptzCommand(mCameraCode,PtzCommandParam.PTZ_CMD.ALLSTOP);
                    }
        });
    }

    /**
     * 云台控制
     * */
    public void ptzCommand(String cameraCode, int directionCode){

        //云台命令参数
        PtzCommandParam param = new PtzCommandParam();
        param.setCameraCode(cameraCode);
        param.setCmd(directionCode);
        param.setSpeed1(3);
        param.setSpeed2(3);

        try {
            OnPtzCommandListener listener = new OnPtzCommandListener() {
                @Override
                public void onPtzCommandResult(long errorCode, String errorDesc) {
                    if (errorCode != 0) {
                        Toast.makeText(VideoPlayActivity.this, errorDesc, Toast.LENGTH_SHORT).show();
                    }
                }
            };
            //云台控制接口
            ServiceManager.ptzCommand(param, listener);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public static String toHexEncoding(int color) {
        String R, G, B;
        StringBuffer sb = new StringBuffer();
        R = Integer.toHexString(Color.red(color));
        G = Integer.toHexString(Color.green(color));
        B = Integer.toHexString(Color.blue(color));
        //判断获取到的R,G,B值的长度 如果长度等于1 给R,G,B值的前边添0
        R = R.length() == 1 ? "0" + R : R;
        G = G.length() == 1 ? "0" + G : G;
        B = B.length() == 1 ? "0" + B : B;
        sb.append("0x");
        sb.append(R);
        sb.append(G);
        sb.append(B);
        return sb.toString();
    }

    public static Bitmap drawableToBitmap(Context context, int resId) {
        Drawable drawable = context.getDrawable(resId);
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);

        Canvas canvas = new Canvas(bitmap);
        //canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.capture_view:
                //boolean sdCardExist = Environment.getExternalStorageState().equals("mounted");
                //ToastUtils.showShort("sdCardExist ? " + sdCardExist);
                //抓拍图片，返回路径
                String path = mPlayer.snatch(null);
                if (null != path) {
                    Toast.makeText(VideoPlayActivity.this, path, Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }

    }


    class surfaceCallback implements SurfaceHolder.Callback {

        public void surfaceCreated(SurfaceHolder holder) {
            Log.d(TAG, "===== surfaceCreated =====");
            if (null != mPlayer && !mPlayer.AVIsPlaying()) {
                startLive(mCameraCode);
            }
        }

        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            //Log.d(TAG, "===== surfaceChanged =====");
            if (mPlayer != null) {//mPlayer.changeDisplaySize(width, height);
            }
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder arg0) {
            Log.d(TAG, "===== surfaceDestroyed =====");
            stopLive();
        }
    }
}

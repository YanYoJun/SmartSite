package com.isoftstone.smartsite.model.system.ui;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.isoftstone.smartsite.R;
import com.isoftstone.smartsite.base.BaseFragment;
import com.isoftstone.smartsite.utils.LogUtils;
import com.isoftstone.smartsite.utils.ToastUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
/**
 * Created by zyf on 2017/10/13 20:00.
 * 个人中心页面
 * modified by zyf on 2017/10/21
 */
public class IndividualCenterFragment extends BaseFragment implements UploadUtil.OnUploadProcessListener, View.OnClickListener{
    private boolean mHandledPress = false;
    private LinearLayout mLinearLayout;//用户头像父节点LL
    private ImageView mImageView;//用户头像IV
    private Bitmap mHeadBitmap;//裁剪后得图片
    String picPath;//头像路径
    private PermissionsChecker mPermissionsChecker;
    private static final int REQUEST_CODE = 100; // 权限检查请求码

    private Button mOkBtn;
    private Button mCancleBtn;
    private Fragment mCurrentFrame;
    private EditText mUserFullNameET;
    private EditText mUserRoleET;
    private EditText mUserSexET;
    private EditText mUserAccountET;
    private EditText mUserPhoneNumET;
    private EditText mUserCompanyET;
    private EditText mUserAutographET;

    /* 请求识别码 选择图库*/
    private static final int IMAGE_REQUEST_CODE = 1;
    /* 请求识别码 照相机*/
    private static final int CAMERA_REQUEST_CODE = 2;
    /* 请求识别码 裁剪*/
    private static final int RESULECODE = 3;
    // 所需的全部权限
    static final String[] PERMISSIONS = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    public void onStart() {
        super.onStart();
        backHandlerInterface.setSelectedFragment(this);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_individual_center;
    }

    @Override
    protected void afterCreated(Bundle savedInstanceState) {
        init();
        mCurrentFrame = IndividualCenterFragment.this;
        if (!(getActivity() instanceof BackHandlerInterface)) {
            throw new ClassCastException("Hosting activity must implement BackHandlerInterface");
        } else {
            // 强转为接口实例  
            backHandlerInterface = (BackHandlerInterface) getActivity();
        }
    }

    /**
     * 实例化
     */
    private void init() {
        mPermissionsChecker = new PermissionsChecker(getActivity().getApplicationContext());
        picPath = Environment.getExternalStorageDirectory() + "/smartsite/";
        mImageView = (ImageView) rootView.findViewById(R.id.head_iv);
        mOkBtn = (Button) rootView.findViewById(R.id.ok);
        mCancleBtn = (Button) rootView.findViewById(R.id.cancle);
        mUserFullNameET = (EditText) rootView.findViewById(R.id.user_full_name);
        mUserRoleET = (EditText) rootView.findViewById(R.id.user_role);
        mUserSexET = (EditText) rootView.findViewById(R.id.user_sex);
        mUserAccountET = (EditText) rootView.findViewById(R.id.user_account);
        mUserPhoneNumET = (EditText) rootView.findViewById(R.id.user_phoneNum);
        mUserCompanyET = (EditText) rootView.findViewById(R.id.user_company);
        mUserAutographET = (EditText) rootView.findViewById(R.id.user_autograph);
        //获取服务器数据显示..... zyf modifed.....

        mOkBtn.setOnClickListener(this);
        mCancleBtn.setOnClickListener(this);

        mLinearLayout = (LinearLayout) rootView.findViewById(R.id.head_linearlayout);
        mLinearLayout.setOnClickListener(new LinearLayout.OnClickListener(){
            @Override
            public void onClick(View v) {
                new ActionSheetDialog(getContext())
                        .builder()
                        .setCancelable(true)
                        .setCanceledOnTouchOutside(true)
                        .addSheetItem("相册",
                                ActionSheetDialog.SheetItemColor.Blue,
                                new ActionSheetDialog.OnSheetItemClickListener() {

                                    @Override
                                    public void onClick(int which) {
                                        choseHeadImageFromGallery();
                                    }
                                })
                        .addSheetItem("拍照",
                                ActionSheetDialog.SheetItemColor.Blue,
                                new ActionSheetDialog.OnSheetItemClickListener() {

                                    @Override
                                    public void onClick(int which) {
                                        choseHeadImageFromCameraCapture();
                                    }
                                }).show();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        // 拒绝时, 关闭页面, 缺少主要权限, 无法运行
        if (requestCode == REQUEST_CODE && resultCode == PermissionsActivity.PERMISSIONS_DENIED) {
            ToastUtils.showShort("缺少主要权限, 无法运行");
            return;
        }

        if (resultCode != Activity.RESULT_CANCELED) {
            switch (requestCode) {
                // 如果是直接从相册获取
                case IMAGE_REQUEST_CODE:
                    cropPhoto(intent.getData());//裁剪图片
                    break;
                // 如果是调用相机拍照时
                case CAMERA_REQUEST_CODE:
                    File temp = new File(Environment.getExternalStorageDirectory()
                            + "/head.jpg");
                    cropPhoto(Uri.fromFile(temp));//裁剪图片
                    break;
                case RESULECODE:
                    if (intent != null) {
                        Bundle extras = intent.getExtras();
                        mHeadBitmap = extras.getParcelable("data");
                        mImageView.setImageBitmap(mHeadBitmap);
                        if (mHeadBitmap != null) {
                            /**
                             * 上传服务器代码
                             */
//                        setPicToView(head);//保存在SD卡中
                        }
                    }
                    break;
                default:
                    break;

            }
            super.onActivityResult(requestCode, resultCode, intent);
        }
    }

    // 从本地相册选取图片作为头像
    private void choseHeadImageFromGallery() {

        Intent intentFromGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // 设置文件类型
        intentFromGallery.setType("image/*");
        startActivityForResult(intentFromGallery, IMAGE_REQUEST_CODE);
    }

    // 启动手机相机拍摄照片作为头像
    private void choseHeadImageFromCameraCapture() {

        if (PhoneInfoUtils.hasSdcard()) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory(),
                    "head.jpg")));
            startActivityForResult(intent, CAMERA_REQUEST_CODE);//采用ForResult打开
        }
    }

    /**
     * 裁剪图片
     *
     * @param uri 照片的url地址
     */
    public void cropPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, RESULECODE);
    }

    /**
     * 将裁剪得到的图片保存到本地
     */
    private void setPicToView(Bitmap mBitmap) {
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
            return;
        }
        FileOutputStream b = null;
        File file = new File(picPath);
        // 创建文件夹
        file.mkdirs();
        String fileName = picPath + "head.jpg";//图片名字
        try {
            b = new FileOutputStream(fileName);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                //关闭流
                if (b != null) {
                    b.flush();
                    b.close();
                }
                toUploadFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    //上传图片
    private void toUploadFile() {
        String fileKey = "icon";
        UploadUtil uploadUtil = UploadUtil.getInstance();
        uploadUtil.setOnUploadProcessListener(this);  //设置监听器监听上传状态
        Map<String, String> params = new HashMap<>();//请求参数
        uploadUtil.uploadFile(picPath + "head.jpg", fileKey, "下载链接", params);
    }

    @Override
    public void onResume() {
        super.onResume();

        // 缺少权限时, 进入权限配置页面
        if (mPermissionsChecker.lacksPermissions(PERMISSIONS)) {
            startPermissionsActivity();
        }
    }

    private void startPermissionsActivity() {
        PermissionsActivity.startActivityForResult(getActivity(), REQUEST_CODE, PERMISSIONS);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mHeadBitmap != null) {
            mHeadBitmap.recycle();
            mHeadBitmap = null;
            System.gc();
        }
    }




    /**
     * 去上传文件
     */
    protected static final int TO_UPLOAD_FILE = 1;
    /**
     * 上传文件响应
     */
    protected static final int UPLOAD_FILE_DONE = 2;  //
    /**
     * 上传初始化
     */
    private static final int UPLOAD_INIT_PROCESS = 3;
    /**
     * 上传中
     */
    private static final int UPLOAD_IN_PROCESS = 4;

    @Override
    public void onUploadDone(int responseCode, String message) {
        Message msg = Message.obtain();
        msg.what = UPLOAD_FILE_DONE;
        msg.arg1 = responseCode;
        msg.obj = message;
        handler.sendMessage(msg);
    }

    @Override
    public void onUploadProcess(int uploadSize) {
        Message msg = Message.obtain();
        msg.what = UPLOAD_IN_PROCESS;
        msg.arg1 = uploadSize;
        handler.sendMessage(msg);
    }

    @Override
    public void initUpload(int fileSize) {
        Message msg = Message.obtain();
        msg.what = UPLOAD_INIT_PROCESS;
        msg.arg1 = fileSize;
        handler.sendMessage(msg);
    }

    private static Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case TO_UPLOAD_FILE:
                    break;
                case UPLOAD_INIT_PROCESS:
                    break;
                case UPLOAD_IN_PROCESS:
                    break;
                case UPLOAD_FILE_DONE:
                    String result = msg.obj + "";
//                    paserResult(result);
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.ok:
                //保存用戶數據 上傳到服务器..... zyf modifed.....
            case R.id.cancle:
                /**FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Fragment systemFragment = new SystemFragment();
                if(mCurrentFrame != systemFragment){
                    if(!systemFragment.isAdded()) {
                        fragmentTransaction.hide(mCurrentFrame).add(R.id.fl_system_content, systemFragment).commitAllowingStateLoss();
                    } else{
                        fragmentTransaction.hide(mCurrentFrame).show(systemFragment).commitAllowingStateLoss();
                    }
                    mCurrentFrame = systemFragment;
                }*/
                Fragment systemFragment = new SystemFragment();
                changeToAnotherFragment(mCurrentFrame, systemFragment);
            default:
                break;
        }
    }


    protected BackHandlerInterface backHandlerInterface;
    public interface BackHandlerInterface {
        public void setSelectedFragment(IndividualCenterFragment backHandledFragment);
    }
    /**
     * 其返回一个布尔值;意思是,如果对返回事件进行了处理就返回TRUE,如果不做处理就返回FALSE,让上层进行处理
     */
    public boolean onFragmentBackPressed() {
        if (!mHandledPress) {
            mHandledPress = true;
            ToastUtils.showShort("onFragmentBackPressed");
            Fragment systemFragment = new SystemFragment();
            changeToAnotherFragment(mCurrentFrame, systemFragment);
            return true;
        }
        return false;
    }

    private void changeToAnotherFragment(Fragment currentFrame,Fragment toFragment){
        //如果是不是用的v4的包，则用getActivity().getFragmentManager();
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        //注意v4包的配套使用;
        if(currentFrame != toFragment) {
            if(!toFragment.isAdded()) {
                transaction.hide(currentFrame).add(R.id.fl_system_content, toFragment).commitAllowingStateLoss();
            } else{
                transaction.hide(currentFrame).show(toFragment).commitAllowingStateLoss();
            }
            mCurrentFrame = toFragment;
        }
    }
}

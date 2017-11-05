package com.isoftstone.smartsite.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.isoftstone.smartsite.common.AppManager;
import com.isoftstone.smartsite.http.PatrolBean;
import com.isoftstone.smartsite.utils.StatusViewUtils;

public abstract class BaseActivity extends AppCompatActivity {

    protected String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppManager.getAppManager().addToActivities(this);

        setContentView(getLayoutRes());

        StatusViewUtils.initStatusBar(this);

        afterCreated(savedInstanceState);

    }

    @Override
    protected void onDestroy() {
        StatusViewUtils.removeStatusBar(this);
        super.onDestroy();
        AppManager.getAppManager().finishActivity(this);
    }

    /**
     * 返回布局UI的ID
     * @return
     */
    protected abstract int getLayoutRes();

    /**
     * 布局加载完毕后的逻辑操作
     * @param savedInstanceState
     */
    protected abstract void afterCreated(Bundle savedInstanceState);

    /**
     * 跳转Activity
      * @param activity
     * @param bundle
     */
    public void openActivity(Class<?> activity,Bundle bundle){
        Intent intent = new Intent(this,activity);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * 点击返回按钮
     */
    public void onBackBtnClick(View v){
        finish();;
    }

    /**
     * 获取巡查报告的数据，非三方协同界面不用关注
     * @return
     */
    public PatrolBean getReportData(){
        return null;
    }
}

package com.isoftstone.smartsite.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by zw on 2017/10/11.
 */

public abstract class BaseFragment extends Fragment {

    protected String TAG = this.getClass().getSimpleName();

    protected View rootView;

    protected Context mContext;

    //第一次加载才初始化View，避免重复调用onCreateView初始化数据
    private boolean isFirstCreate = true;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(rootView == null){
            rootView = inflater.inflate(getLayoutRes(),container,false);
        }

        //避免重复初始化rootViewDensityUtils
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if(parent != null){
            parent.removeView(rootView);
        }

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(isFirstCreate){
            afterCreated(savedInstanceState);
            isFirstCreate = false;
        }
    }

    /**
     * 返回布局UI的ID
     * @return
     */
    protected abstract int getLayoutRes();

    /**
     * 布局填充后的逻辑操作
     * @param savedInstanceState
     */
    protected abstract void afterCreated(Bundle savedInstanceState);
}

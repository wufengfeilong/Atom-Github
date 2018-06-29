package com.fcn.park.base.fragment;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fcn.park.base.BaseFragment;
import com.fcn.park.base.BasePresenter;
import com.fcn.park.base.BaseView;

/**
 * Created by 860115001 on 2018/04/03.
 * 类描述：用于Fragment做懒加载的父类
 */

public abstract class LazyFragment<D extends ViewDataBinding, V extends BaseView, P extends BasePresenter<V>> extends BaseFragment<D, V, P>  {
    /**
     * 判断控件是否初始化完成
     */
    private boolean isViewCreated;
    /**
     * 判断数据是否已加载完成
     */
    private boolean isLoadDataCompleted;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isViewCreated && !isLoadDataCompleted) {
            isLoadDataCompleted = true;
            loadData();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        isViewCreated = true;
        isLoadDataCompleted = false;
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getUserVisibleHint()) {
            isLoadDataCompleted = true;
            loadData();
        }
    }

    protected abstract void loadData();
}

package com.fcn.park.base;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;

/**
 * Created by 860115001 on 2018/04/02.
 * 类描述：MVP模式的View接口
 */

public interface BaseView {
    Context getContext();

    void actionStartActivity(Class cls);

    void showToast(String text);

    void closeActivity();

    void showProgressDialog(String message);

    void hindProgressDialog();

    @ColorInt
    int getResColor(@ColorRes int color);
}

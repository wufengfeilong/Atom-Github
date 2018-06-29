package com.fcn.park.base.utils;

/**
 * Created by liuyq on 2017/04/10.
 * 类描述：
 */

public interface OnDataGetCallback<T> {
    /**
     * 当数据执行成功时的回调方法
     *
     * @param data
     */
    void onSuccessResult(T data);
}

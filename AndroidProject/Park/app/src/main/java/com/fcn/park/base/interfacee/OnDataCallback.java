package com.fcn.park.base.interfacee;

/**
 * Created by 860115001 on 2018/04/10.
 */

public interface OnDataCallback<T> {
    /**
     * 当数据执行成功时的回调方法
     *
     * @param data
     */
    void onSuccessResult(T data);

    /**
     * 当错误时执行的方法
     */
    void onError(String errMsg);
}

package com.fcn.park.property.utils;

/**
 * 作者：吕振鹏
 * 创建时间：12月15日
 * 时间：12:35
 * 版本：v1.0.0
 * 类描述：当支付状态发生改变时的回掉
 * 修改时间：
 */

public interface OnPayResultListener {

    void onSuccess();

    void onFailure();


}

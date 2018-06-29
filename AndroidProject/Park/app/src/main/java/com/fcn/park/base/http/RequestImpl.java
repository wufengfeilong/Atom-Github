package com.fcn.park.base.http;

/**
 * Created by 860115001 on 2018/04/10.
 * 用于数据请求的回调
 */

public interface RequestImpl<T> {
    void onNext(T result);
}

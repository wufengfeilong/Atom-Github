package com.fcn.park.info.module;

import android.content.Context;
import android.util.Log;

import com.fcn.park.base.http.ApiClient;
import com.fcn.park.base.http.HttpResult;
import com.fcn.park.base.http.ProgressSubscriber;
import com.fcn.park.base.http.RequestImpl;
import com.fcn.park.base.http.RetrofitManager;
import com.fcn.park.base.interfacee.OnDataCallback;

/**
 * Created by liuyq on 2018/04/24.
 * 需求发布用——新增企业需求module
 */

public class ManagerDemandAddModule {

    public void managerDemandAdd(Context context, String title, String content, String source, String contact, String tel, String address, int category, String insertUser, String updateUser, final OnDataCallback<String> callback) {
        RetrofitManager.toSubscribe(ApiClient.getApiService().insertDemandInfo(title, content, source, contact, tel, address, category, insertUser, updateUser), new ProgressSubscriber<HttpResult<String>>(context, new RequestImpl<HttpResult<String>>() {
            @Override
            public void onNext(HttpResult<String> result) {
                callback.onSuccessResult(result.getMsg());
            }
        }));
    }
}

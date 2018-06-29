package com.fcn.park.info.module;

import android.content.Context;

import com.fcn.park.base.http.ApiClient;
import com.fcn.park.base.http.HttpResult;
import com.fcn.park.base.http.ProgressSubscriber;
import com.fcn.park.base.http.RequestImpl;
import com.fcn.park.base.http.RetrofitManager;
import com.fcn.park.base.interfacee.OnDataCallback;

/**
 * Created by liuyq on 2018/04/26.
 * 需求详情编辑页面module
 */

public class ManagerDemandEditModule {
    public void managerDemandEdit(Context context, String demandId, String title, String content, String source, String contact, String tel, String address, String updateUser, final OnDataCallback<String> callback) {
        RetrofitManager.toSubscribe(ApiClient.getApiService().managerDemandEdit(demandId, title, content, source, contact, tel, address, updateUser), new ProgressSubscriber<HttpResult<String>>(context, new RequestImpl<HttpResult<String>>() {
            @Override
            public void onNext(HttpResult<String> result) {
                if (result.isResult()) {
                    callback.onSuccessResult(result.getData());
                } else {
                    callback.onSuccessResult(result.getMsg());
                }

            }
        }));
    }
}

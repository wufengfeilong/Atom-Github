package com.fcn.park.login.module;

import android.content.Context;

import com.fcn.park.base.http.ApiClient;
import com.fcn.park.base.http.HttpResult;
import com.fcn.park.base.http.ProgressSubscriber;
import com.fcn.park.base.http.RequestImpl;
import com.fcn.park.base.http.RetrofitManager;
import com.fcn.park.base.interfacee.OnDataCallback;
import com.fcn.park.login.bean.CodeBean;

/**
 * Created by 860115001 on 2018/04/10.
 */

public class PhoneRegisterModule {

    public void getVerify(final Context context, String phoneNum, final OnDataCallback<String> callback){
        RetrofitManager.toSubscribe(ApiClient.getApiService().personSendPhoneCode(phoneNum),
                new ProgressSubscriber<>(context, new RequestImpl<HttpResult<CodeBean>>() {
                    @Override
                    public void onNext(HttpResult<CodeBean> codeBeanHttpResult) {
                        callback.onSuccessResult(codeBeanHttpResult.getMsg());
                    }
                }));
    }

    public void userReg(final Context context, String password, String phoneNum,
                          String verify, final OnDataCallback<String> callback){
        RetrofitManager.toSubscribe(ApiClient.getApiService().userReg(phoneNum, password, phoneNum,verify),
                new ProgressSubscriber<>(context, new RequestImpl<HttpResult<String>>() {
                    @Override
                    public void onNext(HttpResult<String> stringHttpResult) {
                        callback.onSuccessResult(stringHttpResult.getMsg());
                    }
                }));
    }
}

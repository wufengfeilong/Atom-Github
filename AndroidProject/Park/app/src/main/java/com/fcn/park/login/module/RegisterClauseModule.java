package com.fcn.park.login.module;

import android.content.Context;

import com.fcn.park.base.http.ApiClient;
import com.fcn.park.base.http.HttpResult;
import com.fcn.park.base.http.RetrofitManager;
import com.fcn.park.base.interfacee.OnDataCallback;
import com.fcn.park.base.utils.NetworkUtils;
import com.fcn.park.login.bean.RegisterClauseBean;

import rx.Subscriber;

/**
 * Created by 860115001 on 2018/04/10.
 */

public class RegisterClauseModule {

    public void requestRegisterClause(Context context, final OnDataCallback<RegisterClauseBean> callback) {
        RetrofitManager.toSubscribe(
                ApiClient.getApiService().registerClause()
                , new Subscriber<HttpResult<RegisterClauseBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(NetworkUtils.getErrorMsg(e));
                    }

                    @Override
                    public void onNext(HttpResult<RegisterClauseBean> result) {
                        if (result.isResult()) {
                            callback.onSuccessResult(result.getData());
                        } else {
                            callback.onError(result.getMsg());
                        }
                    }
                }
        );
    }
}

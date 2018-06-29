package com.fcn.park.manager.module;

import android.content.Context;
import android.util.Log;

import com.fcn.park.base.http.ApiClient;
import com.fcn.park.base.http.HttpResult;
import com.fcn.park.base.http.ProgressSubscriber;
import com.fcn.park.base.http.RequestImpl;
import com.fcn.park.base.http.RetrofitManager;
import com.fcn.park.base.utils.OnDataGetCallback;

/**
 * 管理员用户重置登录密码用Module.
 */
public class ManagerResetPasswordModule {

    private String TAG = "=== ManagerResetPasswordModule ===";

    public void resetPassword(Context context, String userId, String oldPassword, String newPassword, final OnDataGetCallback<String> callback) {
        RetrofitManager.toSubscribe(
                ApiClient.getApiService().resetManagerPassword(userId, oldPassword, newPassword)
                , new ProgressSubscriber<HttpResult<String>>(context, new RequestImpl<HttpResult<String>>() {
                    @Override
                    public void onNext(HttpResult<String> result) {
                        Log.d(TAG, "====== result = " + result.getMsg());
                        callback.onSuccessResult(result.getMsg());
                    }
                })
        );
    }
}

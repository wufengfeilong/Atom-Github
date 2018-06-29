package com.fcn.park.me;

import android.content.Context;
import com.fcn.park.base.http.*;
import com.fcn.park.base.interfacee.OnDataCallback;
import com.fcn.park.base.utils.OnDataGetCallback;

/**
 * create by 860115039
 * date      2018/04/19
 * time      15:41
 * 我的Tab页用module
 */
public class MeModule {
    /**
     * 查询企业认证结果
     */
    public void selectAuthResult(Context context, String userId, final OnDataCallback<String> callback) {
        RetrofitManager.toSubscribe(ApiClient.getApiService().selectAuthResult(userId),
                new ProgressSubscriber<>(context, new RequestImpl<HttpResult<String>>() {
                    @Override
                    public void onNext(HttpResult<String> stringHttpResult) {
                        if (stringHttpResult.isResult()) {
                            callback.onSuccessResult(stringHttpResult.getMsg());
                        } else {
                            callback.onError(stringHttpResult.getMsg());
                        }
                    }
                }));
    }
    /**
     * 查询是否有未读消息
     */
    public void selectHasMsg(Context context, String userId, final OnDataGetCallback<String> callback){
        RetrofitManager.toSubscribe(ApiClient.getApiService().selectHasMsg(userId),
                new ProgressSubscriber<HttpResult<String>>(context, new RequestImpl<HttpResult<String>>() {
                    @Override
                    public void onNext(HttpResult<String> result) {
                        if (result.isResult()) {
                            callback.onSuccessResult(result.getMsg());
                        }
                    }
                }));
    }


}

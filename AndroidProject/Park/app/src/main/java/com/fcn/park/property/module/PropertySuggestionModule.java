package com.fcn.park.property.module;

import android.content.Context;
import android.util.Log;

import com.fcn.park.base.http.ApiClient;
import com.fcn.park.base.http.HttpResult;
import com.fcn.park.base.http.ProgressSubscriber;
import com.fcn.park.base.http.RequestImpl;
import com.fcn.park.base.http.RetrofitManager;
import com.fcn.park.base.interfacee.OnDataCallback;


/**
 * Created by 860117073 on 2018/4/24.
 */
public class PropertySuggestionModule {
    /**
     * 发送投诉建议数据
     * @param context
     * @param userId
     * @param describeContent
     * @param callback
     */
    private String TAG = "=== PropertySuggestionModule ===";
    public void requestSendSuggestionInformation(final Context context,String userId,String describeContent,
                                             final OnDataCallback<String> callback) {
        RetrofitManager.toSubscribe(ApiClient.getApiService().suggestion(userId,describeContent),
                new ProgressSubscriber<>(context, new RequestImpl<HttpResult<String>>(){
                    @Override
                    public void onNext(HttpResult<String> result) {
                        Log.d(TAG, "========= result = " + result.getMsg());
                        callback.onSuccessResult(result.getMsg());
                    }
                }));
    }
}

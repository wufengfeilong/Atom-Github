package com.fcn.park.manager.module;

import android.content.Context;
import android.util.Log;

import com.fcn.park.base.http.ApiClient;
import com.fcn.park.base.http.HttpResult;
import com.fcn.park.base.http.ProgressSubscriber;
import com.fcn.park.base.http.RequestImpl;
import com.fcn.park.base.http.RetrofitManager;
import com.fcn.park.base.interfacee.OnDataCallback;

import java.util.Map;

import okhttp3.MultipartBody;

/**
 * 管理中心编辑新闻条目功能用Module.
 */

public class ManagerPostNewsDetailEditModule {

    private String TAG = "=== ManagerPostNewsListModule ===";

    /**
     * 更新新闻、公告、活动内容后，将内容数据同步更新到数据库
     * @param context
     * @param reqNewsBeanMap
     * @param newsThumbnail
     * @param callback
     */
    public void updateNewsDetailInfo(Context context, Map reqNewsBeanMap, MultipartBody.Part newsThumbnail, final OnDataCallback<String> callback) {
        RetrofitManager.toSubscribe(ApiClient.getApiService().updateNewsDetailInfo(reqNewsBeanMap, newsThumbnail), new ProgressSubscriber<>(context, new RequestImpl<HttpResult<String>>() {
            @Override
            public void onNext(HttpResult<String> result) {
                Log.d(TAG, "========= result = " + result.getMsg());
                if (result.isResult()) {
                    callback.onSuccessResult(result.getData());
                } else {
                    callback.onSuccessResult(result.getMsg());
                }
            }
        }));
    }
}

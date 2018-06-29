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
 * 管理中心的新闻、公告、活动发布功能用Module.
 */
public class ManagerPostNewsAddModule {

    private String TAG = "=== ManagerPostNewsListModule ===";

    /**
     * 发布新闻、公告、活动内容，将内容（图片文件和数据Bean）追加到数据库中
     * @param context
     * @param reqNewsBeanMap
     * @param newsThumbnail
     * @param callback
     */
    public void addPostNewsInfo(Context context, Map reqNewsBeanMap, MultipartBody.Part newsThumbnail, final OnDataCallback<String> callback) {
        RetrofitManager.toSubscribe(ApiClient.getApiService().insertNewsInfo(reqNewsBeanMap, newsThumbnail), new ProgressSubscriber<>(context, new RequestImpl<HttpResult<String>>() {
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

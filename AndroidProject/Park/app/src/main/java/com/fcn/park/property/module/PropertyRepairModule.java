package com.fcn.park.property.module;

import android.content.Context;
import android.util.Log;

import com.fcn.park.base.http.ApiClient;
import com.fcn.park.base.http.HttpResult;
import com.fcn.park.base.http.ProgressSubscriber;
import com.fcn.park.base.http.RequestImpl;
import com.fcn.park.base.http.RetrofitManager;
import com.fcn.park.base.interfacee.OnDataCallback;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;

/**
 * Created by 860117073 on 2018/4/12.
 */


public class PropertyRepairModule {
    /**
     * 发送报修数据
     * @param context
     * @param parts
     * @param reqJson
     * @param callback
     */
    private String TAG = "=== PropertyRepairListModule ===";

    public void requestSendRepairInformation(final Context context, List<MultipartBody.Part> parts, Map reqJson,
                                             final OnDataCallback<String> callback) {
        RetrofitManager.toSubscribe(ApiClient.getApiService().repair(parts,reqJson),
                new ProgressSubscriber<>(context, new RequestImpl<HttpResult<String>>(){
                    @Override
                    public void onNext(HttpResult<String> result) {
                        Log.d(TAG, "========= result = " + result.getMsg());
                        callback.onSuccessResult(result.getMsg());
                    }
                }));
    }
    }

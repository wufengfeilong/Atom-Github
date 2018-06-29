package com.fcn.park.home.advertisement.module;

import android.content.Context;

import com.fcn.park.base.http.ApiClient;
import com.fcn.park.base.http.HttpResult;
import com.fcn.park.base.http.ProgressSubscriber;
import com.fcn.park.base.http.RequestImpl;
import com.fcn.park.base.http.RetrofitManager;
import com.fcn.park.base.interfacee.OnDataCallback;

import okhttp3.MultipartBody;

/**
 * Created by 860115001 on 2018/04/25.
 */

public class AdvertisementModule {

    /**
     * 提交广告
     * @param context
     * @param part
     * @param content
     * @param userName
     * @param callback
     */
    public void submitAdvertisement(Context context, MultipartBody.Part part, String content, int payType, String userName,
                                    final OnDataCallback<String> callback){
        RetrofitManager.toSubscribe(ApiClient.getApiService().addAdvertisement(part, content, payType, userName),
                new ProgressSubscriber<>(context, new RequestImpl<HttpResult<String>>(){
                    @Override
                    public void onNext(HttpResult<String> result) {
                        callback.onSuccessResult(result.getMsg());
                    }
                }));
    }

}

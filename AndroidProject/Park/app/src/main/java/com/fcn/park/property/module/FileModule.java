package com.fcn.park.property.module;

import android.content.Context;

import com.fcn.park.base.http.ApiClient;
import com.fcn.park.base.http.HttpResult;
import com.fcn.park.base.http.ProgressSubscriber;
import com.fcn.park.base.http.RequestImpl;
import com.fcn.park.base.http.RetrofitManager;
import com.fcn.park.base.interfacee.OnDataCallback;

import java.io.File;

/**
 * Created by 860617024 on 18/04/2018.
 */

public class FileModule {

    public static void fileUpload(final Context context, File file, final OnDataCallback callback) {

        RetrofitManager.toSubscribe(ApiClient.getApiService().fileUpload(ApiClient.getFileBody("file", file)),
                new ProgressSubscriber<>(context, new RequestImpl<HttpResult<String>>() {
                    @Override
                    public void onNext(HttpResult<String> stringHttpResult) {
                        callback.onSuccessResult(stringHttpResult.getData());
                    }
                }));
    }
}

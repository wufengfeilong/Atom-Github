package com.fcn.park.manager.module;

import android.content.Context;
import android.util.Log;

import com.fcn.park.base.http.ApiClient;
import com.fcn.park.base.http.HttpResult;
import com.fcn.park.base.http.ProgressSubscriber;
import com.fcn.park.base.http.RequestImpl;
import com.fcn.park.base.http.RetrofitManager;
import com.fcn.park.base.interfacee.OnDataCallback;
import com.fcn.park.base.utils.OnDataGetCallback;
import com.fcn.park.manager.bean.ManagerParkIntroductionBean;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;

/**
 * 园区简介用Module.
 */
public class ManagerParkIntroductionModule {

    private String TAG = "=== ManagerParkIntroductionModule ===";

    /**
     * 获取园区简介的内容
     * @param context
     * @param callback
     */
    public void getParkIntroductionInfo(Context context, String parkId, final OnDataGetCallback<ManagerParkIntroductionBean> callback) {
        RetrofitManager.toSubscribe(ApiClient.getApiService().getParkIntroductionInfo(parkId), new ProgressSubscriber<>(context, new RequestImpl<HttpResult<ManagerParkIntroductionBean>>() {
            @Override
            public void onNext(HttpResult<ManagerParkIntroductionBean> result) {
                Log.d(TAG, "========= result = " + result.getData());
                callback.onSuccessResult(result.getData());
            }
        }));
    }

    /**
     * 上传园区简介中的图片文件
     * @param context
     * @param parts
     * @param reqJson
     * @param callback
     */
    public void uploadParkInfoImg(final Context context, List<MultipartBody.Part> parts, Map reqJson, final OnDataCallback<String> callback) {
        RetrofitManager.toSubscribe(ApiClient.getApiService().uploadParkInfoImg(parts,reqJson), new ProgressSubscriber<>(context, new RequestImpl<HttpResult<String>>() {
                    @Override
                    public void onNext(HttpResult<String> stringHttpResult) {
                        if (stringHttpResult.isResult()) {
                            callback.onSuccessResult(stringHttpResult.getData());
                        } else {
                            callback.onError(stringHttpResult.getMsg());
                        }
                    }
                })
        );
    }
}
package com.fcn.park.me.module;

import android.content.Context;

import com.fcn.park.base.http.ApiClient;
import com.fcn.park.base.http.HttpResult;
import com.fcn.park.base.http.ProgressSubscriber;
import com.fcn.park.base.http.RequestImpl;
import com.fcn.park.base.http.RetrofitManager;
import com.fcn.park.base.interfacee.OnDataCallback;

/**
 * Created by 860117073 on 2018/4/27.
 */

public class MeAddCarModule {
    /**请求追加车辆信息
     * @param context
     * @param CarOwner
     * @param PlateNumber
     * @param Phone
     * @param callback
     */
    public  void requestSendAddCar(final Context context, String CarOwner, String PlateNumber,
                                      String Phone, final OnDataCallback<String> callback){
        RetrofitManager.toSubscribe(ApiClient.getApiService().AddCar(CarOwner,PlateNumber,Phone),
                new ProgressSubscriber<>(context, new RequestImpl<HttpResult<String>>(){
                    @Override
                    public void onNext(HttpResult<String> result) {
                        callback.onSuccessResult(result.getMsg());
                    }
                }));
    }
}

package com.fcn.park.manager.module;

import android.content.Context;

import com.fcn.park.base.http.ApiClient;
import com.fcn.park.base.http.HttpResult;
import com.fcn.park.base.http.ProgressSubscriber;
import com.fcn.park.base.http.RequestImpl;
import com.fcn.park.base.http.RetrofitManager;
import com.fcn.park.base.interfacee.OnDataCallback;

/**
 * Created by 860116042 on 2018/04/20.
 */

public class ElectricFeeEditModule {

    public void editElectricFee(final Context context, String id, String startnum, String endnum,
                                String costnum, String recorddate,String unitprice,
                                String fee, final OnDataCallback<String> callback){
        RetrofitManager.toSubscribe(ApiClient.getApiService().editElectricFee(id,startnum,endnum,costnum,recorddate,unitprice,fee),
                new ProgressSubscriber<>(context, new RequestImpl<HttpResult<String>>() {
                    @Override
                    public void onNext(HttpResult<String> stringHttpResult) {
                        callback.onSuccessResult(stringHttpResult.getMsg());
                    }
                }));
    }
}

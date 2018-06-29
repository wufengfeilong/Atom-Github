package com.fcn.park.manager.module;

import android.content.Context;

import com.fcn.park.base.http.ApiClient;
import com.fcn.park.base.http.HttpResult;
import com.fcn.park.base.http.ProgressSubscriber;
import com.fcn.park.base.http.RequestImpl;
import com.fcn.park.base.http.RetrofitManager;
import com.fcn.park.base.interfacee.OnDataCallback;
import com.fcn.park.login.bean.CodeBean;

/**
 * Created by 860116042 on 2018/04/12.
 */

public class PropertyFeeEditModule {

    public void editPropertyFee(final Context context, String id, String companyspace, String unitprice,
                                String startDate, String endDate,String discount,
                                String fee, String comment, final OnDataCallback<String> callback){
        RetrofitManager.toSubscribe(ApiClient.getApiService().editPropertyFee(id,companyspace,unitprice,startDate,endDate,discount,fee,comment),
                new ProgressSubscriber<>(context, new RequestImpl<HttpResult<String>>() {
                    @Override
                    public void onNext(HttpResult<String> stringHttpResult) {
                        callback.onSuccessResult(stringHttpResult.getMsg());
                    }
                }));
    }
}

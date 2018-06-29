package com.fcn.park.info.module;

import android.content.Context;

import com.fcn.park.base.http.ApiClient;
import com.fcn.park.base.http.HttpResult;
import com.fcn.park.base.http.ProgressSubscriber;
import com.fcn.park.base.http.RequestImpl;
import com.fcn.park.base.http.RetrofitManager;
import com.fcn.park.base.utils.OnDataGetCallback;
import com.fcn.park.info.bean.EnterpriseInfoBean;

/**
 * Created by liuyq on 2018/04/17.
 * 企业详情使用module
 */

public class InfoEnterpriseDetailModule {
    public void requestEnterpriseInfo(Context context, String enterpriseId, final OnDataGetCallback<EnterpriseInfoBean> callback) {
        RetrofitManager.toSubscribe(
                ApiClient.getApiService().getEnterpriseInfo(enterpriseId)
                , new ProgressSubscriber<HttpResult<EnterpriseInfoBean>>(context, new RequestImpl<HttpResult<EnterpriseInfoBean>>() {
                    @Override
                    public void onNext(HttpResult<EnterpriseInfoBean> result) {
                        callback.onSuccessResult(result.getData());
                    }
                })
        );
    }
}

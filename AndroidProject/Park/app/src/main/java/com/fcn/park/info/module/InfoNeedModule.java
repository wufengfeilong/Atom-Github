package com.fcn.park.info.module;

import android.content.Context;

import com.fcn.park.base.http.ApiClient;
import com.fcn.park.base.http.HttpResult;
import com.fcn.park.base.http.ProgressSubscriber;
import com.fcn.park.base.http.RequestImpl;
import com.fcn.park.base.http.RetrofitManager;
import com.fcn.park.base.utils.OnDataGetCallback;
import com.fcn.park.info.bean.NeedInfoBean;

/**
 * Created by liuyq on 2018/04/20.
 * 企业需求详情使用module
 */

public class InfoNeedModule {
    public void requestNeedInfo(Context context, String demandId, final OnDataGetCallback<NeedInfoBean> callback) {
        RetrofitManager.toSubscribe(
                ApiClient.getApiService().getNeedInfo(demandId), new ProgressSubscriber<HttpResult<NeedInfoBean>>(context, new RequestImpl<HttpResult<NeedInfoBean>>() {
                    @Override
                    public void onNext(HttpResult<NeedInfoBean> result) {
                        NeedInfoBean data = result.getData();
                        callback.onSuccessResult(data);
                    }
                })
        );
    }

}

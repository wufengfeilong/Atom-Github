package com.fcn.park.info.module;

import android.content.Context;

import com.fcn.park.base.http.ApiClient;
import com.fcn.park.base.http.HttpResult;
import com.fcn.park.base.http.ProgressSubscriber;
import com.fcn.park.base.http.RequestImpl;
import com.fcn.park.base.http.RetrofitManager;
import com.fcn.park.base.utils.OnDataGetCallback;
import com.fcn.park.info.bean.DemandDetailInfoBean;

/**
 * Created by liuyq on 2018/04/25.
 * 发布企业需求详情用module
 */

public class ManagerDemandDetailModule {
    public void requestManagerDemandDetail(Context context, String demandId, final OnDataGetCallback<DemandDetailInfoBean> callback) {
        RetrofitManager.toSubscribe(
                ApiClient.getApiService().getDemandDetail(demandId), new ProgressSubscriber<HttpResult<DemandDetailInfoBean>>(context, new RequestImpl<HttpResult<DemandDetailInfoBean>>() {
                    @Override
                    public void onNext(HttpResult<DemandDetailInfoBean> result) {
                        DemandDetailInfoBean data = result.getData();
                        callback.onSuccessResult(data);
                    }
                })
        );

    }
}

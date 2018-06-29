package com.fcn.park.manager.module.car;

import android.content.Context;

import com.fcn.park.base.http.ApiClient;
import com.fcn.park.base.http.HttpResult;
import com.fcn.park.base.http.ProgressSubscriber;
import com.fcn.park.base.http.RequestImpl;
import com.fcn.park.base.http.RetrofitManager;
import com.fcn.park.base.utils.OnDataGetCallback;
import com.fcn.park.manager.bean.car.ParkFeeDetailInfoBean;

/**
 * 管理中心的停车付费详情功能用Module
 */
public class ManagerParkFeeDetailInfoModule {

    /**
     * 获取停车付费详情信息
     * @param context
     * @param parkPay_id
     * @param callback
     */
    public void requestParkFeeDetailInfo(Context context, String parkPay_id, final OnDataGetCallback<ParkFeeDetailInfoBean> callback) {

                RetrofitManager.toSubscribe(
                        ApiClient.getApiService().parkFeeDetailInfo(parkPay_id)
                        , new ProgressSubscriber<HttpResult<ParkFeeDetailInfoBean>>(context, new RequestImpl<HttpResult<ParkFeeDetailInfoBean>>() {
                            @Override
                            public void onNext(HttpResult<ParkFeeDetailInfoBean> result) {
                                callback.onSuccessResult(result.getData());
                            }
                        })
                );


    }
}

package com.fcn.park.manager.module.car;

import android.content.Context;

import com.fcn.park.base.http.ApiClient;
import com.fcn.park.base.http.HttpResult;
import com.fcn.park.base.http.ProgressSubscriber;
import com.fcn.park.base.http.RequestImpl;
import com.fcn.park.base.http.RetrofitManager;
import com.fcn.park.base.utils.OnDataGetCallback;
import com.fcn.park.login.LoginHelper;
import com.fcn.park.manager.bean.car.CarWaitCheckDetailInfoBean;

/**
 * 月租车辆待审批详情功能用类描述：
 */
public class ManagerCarWaitCheckDetailInfoModule {

    /**
     * 月租车辆待审批详情
     * @param context
     * @param parkPay_id
     * @param callback
     */
    public void requestCarWaitCheckDetailInfo(Context context, String parkPay_id, final OnDataGetCallback<CarWaitCheckDetailInfoBean> callback) {

                RetrofitManager.toSubscribe(
                        ApiClient.getApiService().carWaitCheckDetail(parkPay_id)
                        , new ProgressSubscriber<HttpResult<CarWaitCheckDetailInfoBean>>(context, new RequestImpl<HttpResult<CarWaitCheckDetailInfoBean>>() {
                            @Override
                            public void onNext(HttpResult<CarWaitCheckDetailInfoBean> result) {
                                callback.onSuccessResult(result.getData());
                            }
                        })
                );
    }

    /**
     * 月租车辆审批通过
     * @param context
     * @param parkPay_id
     * @param UserId
     * @param callback
     */
    public void updateCheckStatusInfo(Context context, String parkPay_id,String UserId, final OnDataGetCallback<String> callback) {

        RetrofitManager.toSubscribe(
//                ApiClient.getApiService().updateCheckStatus(parkPay_id,UserId, LoginHelper.getInstance().getUserToken())
                ApiClient.getApiService().updateCheckStatus(parkPay_id)
                , new ProgressSubscriber<HttpResult<String>>(context, new RequestImpl<HttpResult<String>>() {
                    @Override
                    public void onNext(HttpResult<String> result) {
                        callback.onSuccessResult(result.getMsg());
                    }
                })
        );
    }

    /**
     * 月租车辆审批驳回
     * @param context
     * @param parkPay_id
     * @param UserId
     * @param checkinfo
     * @param callback
     */
    public void onTurnClick(Context context,String parkPay_id, String UserId, String checkinfo, final OnDataGetCallback<String> callback) {

        RetrofitManager.toSubscribe(
//                ApiClient.getApiService().onTurnClick(parkPay_id,UserId,checkinfo)
                ApiClient.getApiService().onTurnClick(parkPay_id, checkinfo)
                , new ProgressSubscriber<HttpResult<String>>(context, new RequestImpl<HttpResult<String>>() {
                    @Override
                    public void onNext(HttpResult<String> result) {
                        callback.onSuccessResult(result.getMsg());
                    }
                })
        );
    }
}

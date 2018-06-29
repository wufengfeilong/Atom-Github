package com.fcn.park.property.module;

import android.content.Context;

import com.fcn.park.base.http.ApiClient;
import com.fcn.park.base.http.HttpResult;
import com.fcn.park.base.http.RetrofitManager;
import com.fcn.park.base.interfacee.OnDataCallback;
import com.fcn.park.property.bean.PropertyMainBean;

import java.util.List;

import rx.Subscriber;

/**
 * 绿色物管的月租车辆审核列表画面用Module
 */

public class PropertyRentParkListModule {

    /**
     * 请求获取月租车辆申请信息列表
     * @param userId
     *         用户ID
     */
    public void requestRentParkList(Context context, String userId, final OnDataCallback<List<PropertyMainBean>> callback) {
        RetrofitManager.toSubscribe(
                ApiClient.getApiService().getRentParkList(userId)
                , new Subscriber<HttpResult<List<PropertyMainBean>>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(e.getMessage());
                    }

                    @Override
                    public void onNext(HttpResult<List<PropertyMainBean>> result) {
                        callback.onSuccessResult(result.getData());
                    }
                }
        );
    }


    /**
     * 请求获取需要缴费的月租车辆申请信息列表
     * @param userId
     *         用户ID
     */
    public void requestPassedRentParkList(Context context, String userId, final OnDataCallback<List<PropertyMainBean>> callback) {
        RetrofitManager.toSubscribe(
                ApiClient.getApiService().getPassedRentParkList(userId)
                , new Subscriber<HttpResult<List<PropertyMainBean>>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(e.getMessage());
                    }

                    @Override
                    public void onNext(HttpResult<List<PropertyMainBean>> result) {
                        callback.onSuccessResult(result.getData());
                    }
                }
        );
    }

    /**
     * 请求删除月租车辆申请信息
     * @param parkPayId
     *         月租车辆申请表的ID
     */
    public void deleteRentParkItem(Context context, int parkPayId, final OnDataCallback<String> callback) {
        RetrofitManager.toSubscribe(
                ApiClient.getApiService().deleteRentParkItem(parkPayId)
                , new Subscriber<HttpResult<String>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(e.getMessage());
                    }

                    @Override
                    public void onNext(HttpResult<String> result) {
                        if (result.isResult()) {
                            callback.onSuccessResult(result.getData());
                        } else {
                            callback.onError(result.getMsg());
                        }
                    }
                }
        );
    }


}

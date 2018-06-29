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
 * 绿色物管的缴费列表画面用Module
 */

public class PropertyPaymentListModule {

    /**
     * 获取水费缴费列表
     *
     * @param context
     * @param userId   用户ID
     * @param callback
     */
    public void requestWaterFeePaymentList(Context context, String userId, final OnDataCallback<List<PropertyMainBean>> callback) {
        RetrofitManager.toSubscribe(
                ApiClient.getApiService().getWaterFeePaymentList(userId)
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

                        if (result.isResult()) {
                            callback.onSuccessResult(result.getData());
                        } else {
                            callback.onError(result.getMsg());
                        }
                    }
                });
    }

    /**
     * 获取电费缴费列表
     *
     * @param context
     * @param userId   用户ID
     * @param callback
     */
    public void requestElectricFeePaymentList(Context context, String userId, final OnDataCallback<List<PropertyMainBean>> callback) {
        RetrofitManager.toSubscribe(
                ApiClient.getApiService().getElectricFeePaymentList(userId)
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

                        if (result.isResult()) {
                            callback.onSuccessResult(result.getData());
                        } else {
                            callback.onError(result.getMsg());
                        }
                    }
                });
    }

    /**
     * 获取物业费缴费列表
     *
     * @param context
     * @param userId   用户ID
     * @param callback
     */
    public void requestPropertyFeePaymentList(Context context, String userId, final OnDataCallback<List<PropertyMainBean>> callback) {
        RetrofitManager.toSubscribe(
                ApiClient.getApiService().getPropertyFeePaymentList(userId)
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

                        if (result.isResult()) {
                            callback.onSuccessResult(result.getData());
                        } else {
                            callback.onError(result.getMsg());
                        }
                    }
                });
    }

    /**
     * 获取租赁费缴费列表
     *
     * @param context
     * @param userId   用户ID
     * @param callback
     */
    public void requestRentFeePaymentList(Context context, String userId, final OnDataCallback<List<PropertyMainBean>> callback) {
        RetrofitManager.toSubscribe(
                ApiClient.getApiService().getRentFeePaymentList(userId)
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

                        if (result.isResult()) {
                            callback.onSuccessResult(result.getData());
                        } else {
                            callback.onError(result.getMsg());
                        }
                    }
                });
    }

    /**
     * 获取停车缴费列表
     *
     * @param context
     * @param userId   用户ID
     * @param callback
     */
    public void requestParkingPaymentList(Context context, String userId, final OnDataCallback<List<PropertyMainBean>> callback) {
        RetrofitManager.toSubscribe(
                ApiClient.getApiService().getParkingPaymentList(userId)
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

                        if (result.isResult()) {
                            callback.onSuccessResult(result.getData());
                        } else {
                            callback.onError(result.getMsg());
                        }
                    }
                });

    }

}

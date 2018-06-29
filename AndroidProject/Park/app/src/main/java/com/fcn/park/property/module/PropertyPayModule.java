package com.fcn.park.property.module;

import android.content.Context;

import com.fcn.park.base.http.ApiClient;
import com.fcn.park.base.http.HttpResult;
import com.fcn.park.base.http.RetrofitManager;
import com.fcn.park.base.interfacee.OnDataCallback;
import com.fcn.park.property.bean.PropertyMainBean;

import rx.Subscriber;

/**
 * 绿色物管的缴费画面用Module
 */

public class PropertyPayModule {

    /**
     * 请求获取水费缴费信息
     *  @param context
     * @param companyId
     * @param payStatus
     * @param callback
     */
    public void requestPropertyWaterPayInfo(Context context, String companyId, int payStatus, final OnDataCallback<PropertyMainBean> callback) {
        RetrofitManager.toSubscribe(
                ApiClient.getApiService().getPropertyWaterPayInfo(companyId, payStatus)
                , new Subscriber<HttpResult<PropertyMainBean>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(e.getMessage());
                    }

                    @Override
                    public void onNext(HttpResult<PropertyMainBean> result) {
                        callback.onSuccessResult(result.getData());
                    }
                }
        );
    }

    /**
     * 请求获取电费缴费信息
     *  @param context
     * @param companyId
     * @param payStatus
     * @param callback
     */
    public void requestPropertyElectricPayInfo(Context context, String companyId, int payStatus, final OnDataCallback<PropertyMainBean> callback) {
        RetrofitManager.toSubscribe(
                ApiClient.getApiService().getPropertyElectricPayInfo(companyId, payStatus)
                , new Subscriber<HttpResult<PropertyMainBean>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(e.getMessage());
                    }

                    @Override
                    public void onNext(HttpResult<PropertyMainBean> result) {
                        callback.onSuccessResult(result.getData());
                    }
                }
        );
    }

    /**
     * 请求获取物业费缴费信息
     *  @param context
     * @param companyId
     * @param payStatus
     * @param callback
     */
    public void requestPropertyFeePayInfo(Context context, String companyId, int payStatus, final OnDataCallback<PropertyMainBean> callback) {
        RetrofitManager.toSubscribe(
                ApiClient.getApiService().getPropertyFeePayInfo(companyId, payStatus)
                , new Subscriber<HttpResult<PropertyMainBean>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(e.getMessage());
                    }

                    @Override
                    public void onNext(HttpResult<PropertyMainBean> result) {
                        callback.onSuccessResult(result.getData());
                    }
                }
        );
    }

    /**
     * 请求获取租赁费缴费信息
     *  @param context
     * @param companyId
     * @param payStatus
     * @param callback
     */
    public void requestRentFeePayInfo(Context context, String companyId, int payStatus, final OnDataCallback<PropertyMainBean> callback) {
        RetrofitManager.toSubscribe(
                ApiClient.getApiService().getRentFeePayInfo(companyId, payStatus)
                , new Subscriber<HttpResult<PropertyMainBean>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(e.getMessage());
                    }

                    @Override
                    public void onNext(HttpResult<PropertyMainBean> result) {
                        callback.onSuccessResult(result.getData());
                    }
                }
        );
    }

    /**
     * 请求获取临时停车费缴费信息
     *  @param context
     * @param carNumber
     * @param payStatus
     * @param callback
     */
    public void requestTemporaryParkInfo(Context context, String carNumber, int payStatus, final OnDataCallback<PropertyMainBean> callback) {
        RetrofitManager.toSubscribe(
                ApiClient.getApiService().getTemporaryParkInfo(carNumber, payStatus)
                , new Subscriber<HttpResult<PropertyMainBean>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(e.getMessage());
                    }

                    @Override
                    public void onNext(HttpResult<PropertyMainBean> result) {
                        callback.onSuccessResult(result.getData());
                    }
                }
        );
    }

    /**
     * 请求获取临时停车费缴费信息
     *  @param context
     * @param parkPayId
     * @param payStatus
     * @param callback
     */
    public void requestRentParkInfo(Context context, String userId, int parkPayId, int payStatus, final OnDataCallback<PropertyMainBean> callback) {
        RetrofitManager.toSubscribe(
                ApiClient.getApiService().getRentParkInfo(userId, parkPayId, payStatus)
                , new Subscriber<HttpResult<PropertyMainBean>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(e.getMessage());
                    }

                    @Override
                    public void onNext(HttpResult<PropertyMainBean> result) {
                        callback.onSuccessResult(result.getData());
                    }
                }
        );
    }
}

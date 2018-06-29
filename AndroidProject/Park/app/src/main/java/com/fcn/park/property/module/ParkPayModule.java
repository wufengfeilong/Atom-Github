package com.fcn.park.property.module;

import android.content.Context;
import com.fcn.park.base.http.ApiClient;
import com.fcn.park.base.http.HttpResult;
import com.fcn.park.base.http.ProgressSubscriber;
import com.fcn.park.base.http.RequestImpl;
import com.fcn.park.base.http.RetrofitManager;
import com.fcn.park.base.interfacee.OnDataCallback;
import com.fcn.park.property.bean.PropertyParkPayBean;
import com.fcn.park.property.bean.PropertyParkPayTypeBean;
import java.util.List;
import java.util.Map;
import okhttp3.MultipartBody;


/**
 * Created by 860617024 on 12/04/2018.
 */

public class ParkPayModule {

    /**
     * 获取月租申请页面初始化数据
     * @param context
     * @param callback
     */
    public void getParkPayInitData(final Context context, final OnDataCallback<List<PropertyParkPayTypeBean>> callback) {
        RetrofitManager.toSubscribe(
                ApiClient.getApiService().getParkPayInitData()
                , new ProgressSubscriber<>(context, new RequestImpl<HttpResult<List<PropertyParkPayTypeBean>>>() {
                    @Override
                    public void onNext(HttpResult<List<PropertyParkPayTypeBean>> stringHttpResult) {
                        if (stringHttpResult.isResult()) {
                            callback.onSuccessResult(stringHttpResult.getData());
                        } else {
                            callback.onError(stringHttpResult.getMsg());
                        }
                    }
                })
        );
    }

    /**
     * 获取月租申请驳回页面初始化数据
     * @param context
     * @param parkPayId
     * @param callback
     */
    public void getParkPayRejectInitData(final Context context,int parkPayId, final OnDataCallback<PropertyParkPayBean> callback) {
        RetrofitManager.toSubscribe(
                ApiClient.getApiService().getParkPayRejectInitData(parkPayId)
                , new ProgressSubscriber<>(context, new RequestImpl<HttpResult<PropertyParkPayBean>>() {
                    @Override
                    public void onNext(HttpResult<PropertyParkPayBean> stringHttpResult) {
                        if (stringHttpResult.isResult()) {
                            callback.onSuccessResult(stringHttpResult.getData());
                        } else {
                            callback.onError(stringHttpResult.getMsg());
                        }
                    }
                })
        );
    }

    /**
     * 提交申请
     * @param context
     * @param parts
     * @param reqJson
     * @param callback
     */
    public void parkPayApply(final Context context, List<MultipartBody.Part> parts, Map reqJson, final OnDataCallback<String> callback) {
        RetrofitManager.toSubscribe(
                ApiClient.getApiService().parkPayApply(parts,reqJson)
                , new ProgressSubscriber<>(context, new RequestImpl<HttpResult<String>>() {
                    @Override
                    public void onNext(HttpResult<String> stringHttpResult) {
                        if (stringHttpResult.isResult()) {
                            callback.onSuccessResult(stringHttpResult.getData());
                        } else {
                            callback.onError(stringHttpResult.getMsg());
                        }
                    }
                })
        );
    }
}

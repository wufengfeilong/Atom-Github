package com.fcn.park.manager.module;

import android.content.Context;
import android.util.Log;

import com.fcn.park.base.http.ApiClient;
import com.fcn.park.base.http.HttpResult;
import com.fcn.park.base.http.ProgressSubscriber;
import com.fcn.park.base.http.RequestImpl;
import com.fcn.park.base.http.RetrofitManager;
import com.fcn.park.base.interfacee.OnDataCallback;
import com.fcn.park.base.utils.OnDataGetCallback;
import com.fcn.park.manager.bean.ManagerAdvertisingFeeEditBean;

import java.util.List;

/**
 * 广告费用编辑用Module.
 */
public class ManagerAdvertisingFeeEditModule {

    private String TAG = "=== ManagerAdvertisingFeeEditModule ===";

    /**
     * 更新广告套餐类型的费用的数据
     * @param context
     * @param callback
     */
    public void updateAdvertisingSetFeeData(Context context, String set1Fee, String set2Fee, String set3Fee, final OnDataCallback<String> callback) {
        RetrofitManager.toSubscribe(ApiClient.getApiService().updateAdvertisingSetFeeData(set1Fee, set2Fee, set3Fee), new ProgressSubscriber<>(context, new RequestImpl<HttpResult<String>>() {
            @Override
            public void onNext(HttpResult<String> result) {
                Log.d(TAG, "========= result = " + result.getMsg());
                callback.onSuccessResult(result.getMsg());
            }
        }));
    }

    /**
     * 获取广告套餐类型的费用的数据
     * @param context
     * @param callback
     */
    public void getAdvertisingSetFeeData(Context context, final OnDataCallback<List<ManagerAdvertisingFeeEditBean.AdvertisingFeeList>> callback) {
        RetrofitManager.toSubscribe(ApiClient.getApiService().getAdvertisingSetFeeData(), new ProgressSubscriber<>(context, new RequestImpl<HttpResult<ManagerAdvertisingFeeEditBean>>() {
            @Override
            public void onNext(HttpResult<ManagerAdvertisingFeeEditBean> result) {
                Log.d(TAG, "====== result = " + result.getMsg());
                callback.onSuccessResult(result.getData().getAdvertisingFeeList());
            }
        }));
    }
}

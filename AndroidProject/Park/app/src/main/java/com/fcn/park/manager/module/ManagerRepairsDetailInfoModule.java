package com.fcn.park.manager.module;

import android.content.Context;

import com.fcn.park.base.http.ApiClient;
import com.fcn.park.base.http.HttpResult;
import com.fcn.park.base.http.ProgressSubscriber;
import com.fcn.park.base.http.RequestImpl;
import com.fcn.park.base.http.RetrofitManager;
import com.fcn.park.base.utils.OnDataGetCallback;

import com.fcn.park.manager.bean.ManagerRepairsDetailInfoBean;

/**
 * Created by 丁胜胜 on 2018/04/24.
 */

public class ManagerRepairsDetailInfoModule {

    /**
     * 获取报修详情的处理
     * @param context
     * @param repairId
     * @param repairPhone
     * @param callback
     */
    public void requestRepairsDetailInfo(Context context, String repairId, String repairPhone,final OnDataGetCallback<ManagerRepairsDetailInfoBean> callback) {
        RetrofitManager.toSubscribe(
                ApiClient.getApiService().managerRepairsDetail(repairId,repairPhone)
                , new ProgressSubscriber<HttpResult<ManagerRepairsDetailInfoBean>>(context, new RequestImpl<HttpResult<ManagerRepairsDetailInfoBean>>() {
                    @Override
                    public void onNext(HttpResult<ManagerRepairsDetailInfoBean> result) {
                        callback.onSuccessResult(result.getData());
                    }
                })
        );

    }
}

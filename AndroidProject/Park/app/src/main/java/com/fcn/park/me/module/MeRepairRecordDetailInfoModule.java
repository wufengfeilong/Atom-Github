package com.fcn.park.me.module;

import android.content.Context;

import com.fcn.park.base.http.ApiClient;
import com.fcn.park.base.http.HttpResult;
import com.fcn.park.base.http.ProgressSubscriber;
import com.fcn.park.base.http.RequestImpl;
import com.fcn.park.base.http.RetrofitManager;
import com.fcn.park.base.utils.OnDataGetCallback;
import com.fcn.park.manager.bean.DetailInfoBean;
import com.fcn.park.me.bean.RepairRecordDetailBean;

/**
 * 类描述：报修详情画面用Module
 */
public class MeRepairRecordDetailInfoModule {
    public void MeRepairRecordDetailInfo(Context context, String repairId, final OnDataGetCallback<RepairRecordDetailBean> callback) {
        RetrofitManager.toSubscribe(
                ApiClient.getApiService().getRepairDetailById(repairId)
                , new ProgressSubscriber<HttpResult<RepairRecordDetailBean>>(context, new RequestImpl<HttpResult<RepairRecordDetailBean>>() {
                    @Override
                    public void onNext(HttpResult<RepairRecordDetailBean> result) {
                        callback.onSuccessResult(result.getData());
                    }
                })
        );

    }
}

package com.fcn.park.me.module;

import android.content.Context;
import com.fcn.park.base.http.*;
import com.fcn.park.base.utils.OnDataGetCallback;
import com.fcn.park.me.bean.MeMsgDetailBean;

/**
 * create by 860115039
 * date      2018/04/23
 * time      13:06
 * 个人中心-消息详情
 */
public class MeMyMsgDetailModule {
    /**
     * 获取消息详情
     */
    public void getMsgDetail(final Context context,String id, final OnDataGetCallback<MeMsgDetailBean> callback) {
        RetrofitManager.toSubscribe(ApiClient.getApiService().getMeMyMsgDetail(id), new ProgressSubscriber<>(context, new RequestImpl<HttpResult<MeMsgDetailBean>>() {
            @Override
            public void onNext(HttpResult<MeMsgDetailBean> result) {
                callback.onSuccessResult(result.getData());
            }
        }));

    }



}

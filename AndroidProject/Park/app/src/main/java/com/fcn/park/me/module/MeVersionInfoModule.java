package com.fcn.park.me.module;

import android.content.Context;
import com.fcn.park.base.http.*;
import com.fcn.park.base.utils.OnDataGetCallback;
import com.fcn.park.me.bean.VersionInfoBean;

/**
 * create by 860115039
 * date      2018/04/23
 * time      13:06
 * 个人中心-版本更新
 */
public class MeVersionInfoModule {
    /**
     * 获取版本信息
     */
    public void getVersionInfo(final Context context, final OnDataGetCallback<VersionInfoBean> callback) {
        RetrofitManager.toSubscribe(ApiClient.getApiService().getVersionInfo(), new ProgressSubscriber<>(context, new RequestImpl<HttpResult<VersionInfoBean>>() {
            @Override
            public void onNext(HttpResult<VersionInfoBean> result) {
                callback.onSuccessResult(result.getData());
            }
        }));
    }

}


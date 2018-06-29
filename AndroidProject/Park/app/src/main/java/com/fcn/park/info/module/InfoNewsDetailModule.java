package com.fcn.park.info.module;

import android.content.Context;

import com.fcn.park.base.http.ApiClient;
import com.fcn.park.base.http.HttpResult;
import com.fcn.park.base.http.ProgressSubscriber;
import com.fcn.park.base.http.RequestImpl;
import com.fcn.park.base.http.RetrofitManager;
import com.fcn.park.base.utils.OnDataGetCallback;
import com.fcn.park.info.bean.InfoNewsBean;

/**
 * Created by liuyq on 2018/04/12.
 * 公告，新闻，活动详情使用module
 */

public class InfoNewsDetailModule {
    public void requestNewsInfo(Context context, String id, final OnDataGetCallback<InfoNewsBean> callback) {
        RetrofitManager.toSubscribe(
                ApiClient.getApiService().newsDetail(id)
                , new ProgressSubscriber<HttpResult<InfoNewsBean>>(context, new RequestImpl<HttpResult<InfoNewsBean>>() {
                    @Override
                    public void onNext(HttpResult<InfoNewsBean> result) {
                        callback.onSuccessResult(result.getData());
                    }

                })
        );
    }

}

package com.fcn.park.manager.module;

import android.content.Context;

import com.fcn.park.base.http.ApiClient;
import com.fcn.park.base.http.HttpResult;
import com.fcn.park.base.http.ProgressSubscriber;
import com.fcn.park.base.http.RequestImpl;
import com.fcn.park.base.http.RetrofitManager;
import com.fcn.park.base.utils.OnDataGetCallback;
import com.fcn.park.manager.bean.DetailInfoBean;

/**
 * 类描述：新闻、公告、活动详情画面用Module
 */
public class ManagerPostNewsDetailInfoModule {

    public void requestPostNewsDetailInfo(Context context, String id, final OnDataGetCallback<DetailInfoBean> callback) {
        RetrofitManager.toSubscribe(
                ApiClient.getApiService().managerNewsDetail(id)
                , new ProgressSubscriber<HttpResult<DetailInfoBean>>(context, new RequestImpl<HttpResult<DetailInfoBean>>() {
                    @Override
                    public void onNext(HttpResult<DetailInfoBean> result) {
                        callback.onSuccessResult(result.getData());
                    }
                })
        );
    }
}
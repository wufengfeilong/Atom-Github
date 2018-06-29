package com.fcn.park.info.module;

import com.fcn.park.base.http.ApiClient;
import com.fcn.park.base.http.HttpResult;
import com.fcn.park.base.http.RetrofitManager;
import com.fcn.park.info.bean.NewsTypeBean;

import java.util.List;

import rx.Subscriber;

/**
 * Created by liuyq on 2018/04/12.
 * 标题请求
 * 公告，新闻，活动，企业，需求title
 */

public class InfoTabLayoutModel {

    public void newsType(Subscriber<HttpResult<List<NewsTypeBean>>> subscriber) {
        RetrofitManager.toSubscribe(
                ApiClient.getApiService().newsType()
                , subscriber
        );
    }
}

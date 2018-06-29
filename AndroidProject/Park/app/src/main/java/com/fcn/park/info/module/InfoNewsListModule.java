package com.fcn.park.info.module;


import android.content.Context;

import com.fcn.park.base.http.ApiClient;
import com.fcn.park.base.http.HttpResult;
import com.fcn.park.base.http.ProgressSubscriber;
import com.fcn.park.base.http.RequestImpl;
import com.fcn.park.base.http.RetrofitManager;
import com.fcn.park.base.utils.OnDataGetCallback;
import com.fcn.park.info.bean.NewsListBean;

import java.util.List;

import rx.Subscriber;


/**
 * Created by liuyq on 2017/04/10.
 * 类描述：取得新闻列表活动列表公告列表用module
 */

public class InfoNewsListModule {
    private List<NewsListBean.GetlistNewsBean> mNewsList;
    private boolean isEnd;

    public void requestNewsList(String newsId, Context context, int page, final OnDataGetCallback<List<NewsListBean.GetlistNewsBean>> callback) {
        RetrofitManager.toSubscribe(
                ApiClient.getApiService().newsList(newsId, page)
                , new ProgressSubscriber<HttpResult<NewsListBean>>(context, new RequestImpl<HttpResult<NewsListBean>>() {
                    @Override
                    public void onNext(HttpResult<NewsListBean> result) {
                        NewsListBean bean = result.getData();
                        isEnd = !bean.isIsNext();
                        if (mNewsList == null) {
                            mNewsList = bean.getGetlistNews();
                        } else {
                            mNewsList.addAll(bean.getGetlistNews());
                        }
                        callback.onSuccessResult(mNewsList);
                    }
                })
        );
    }

    public List<NewsListBean.GetlistNewsBean> getNewsList() {
        return mNewsList;
    }

    public boolean isEnd() {
        return isEnd;
    }
}

package com.fcn.park.home;

import com.fcn.park.base.http.ApiClient;
import com.fcn.park.base.http.HttpResult;
import com.fcn.park.base.http.RetrofitManager;
import com.fcn.park.base.utils.OnDataGetCallback;
import com.fcn.park.info.bean.NewsListBean;

import java.util.List;

import rx.Subscriber;

/**
 * Created by 860115001 on 2018/04/17.
 */

public class HomeModule {

    private List<NewsListBean.GetlistNewsBean> mNewsList;

    public List<NewsListBean.GetlistNewsBean> getAfficheList() {
        return mNewsList;
    }

    /**
     * 获取公告公示标题列表
     * @param callback
     */
    public void getNewsTitle(final OnDataGetCallback<List<NewsListBean.GetlistNewsBean>> callback) {
        RetrofitManager.toSubscribe(
            ApiClient.getApiService().newsList("0", 1)
            , new Subscriber<HttpResult<NewsListBean>>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onNext(HttpResult<NewsListBean> listHttpResult) {
                    NewsListBean bean = listHttpResult.getData();
                    if (mNewsList == null) {
                        mNewsList = bean.getGetlistNews();
                    } else {
                        mNewsList.addAll(bean.getGetlistNews());
                    }
                    callback.onSuccessResult(mNewsList);
                }
            });
    }

}

package com.fcn.park.manager.module;

import android.content.Context;
import android.util.Log;

import com.fcn.park.base.http.ApiClient;
import com.fcn.park.base.http.HttpResult;
import com.fcn.park.base.http.ProgressSubscriber;
import com.fcn.park.base.http.RequestImpl;
import com.fcn.park.base.http.RetrofitManager;
import com.fcn.park.base.utils.OnDataGetCallback;
import com.fcn.park.manager.bean.ManagerPostNewsListBean;

import java.util.List;

/**
 * 类描述：管理中心的新闻、公告、活动列表用Module.
 */

public class ManagerPostNewsListModule {

    private String TAG = "=== ManagerPostNewsListModule ===";
    private List<ManagerPostNewsListBean.ListNewsBean> mNewsList;

    private boolean isEnd;

    /**
     * 获取新闻列表的处理
     *
     * @param context
     * @param page
     * @param callback
     */
    public void requestNewsList(final Context context, String newsType, int page, final OnDataGetCallback<List<ManagerPostNewsListBean.ListNewsBean>> callback) {
        RetrofitManager.toSubscribe(
                ApiClient.getApiService().managerNewsList(newsType, page)
                , new ProgressSubscriber<>(context, new RequestImpl<HttpResult<ManagerPostNewsListBean>>() {

                    @Override
                    public void onNext(HttpResult<ManagerPostNewsListBean> result) {
                        ManagerPostNewsListBean bean = result.getData();
                        Log.d(TAG, "====== onNext() bean = " + bean.getListNews());
                        Log.d(TAG, "====== onNext() msg = " + result.getMsg());
                        Log.d(TAG, "====== onNext() flag = " + result.getFlag());
                        isEnd = !bean.isNext();
                        if (mNewsList == null) {
                            mNewsList = bean.getListNews();
                        } else {
                            mNewsList.addAll(bean.getListNews());
                        }
                        callback.onSuccessResult(mNewsList);
                    }
                }));
    }

    public List<ManagerPostNewsListBean.ListNewsBean> getNewsList() {
        return mNewsList;
    }

    public boolean isEnd() {
        return isEnd;
    }

    /**
     * 根据新闻的news_id，删除相应的新闻条目
     *
     * @param context
     * @param newsId
     * @param callback
     */
    public void deleteNewsListItem(Context context, String newsId, final OnDataGetCallback<String> callback) {
        RetrofitManager.toSubscribe(ApiClient.getApiService().deleteNewsDetail(newsId)
                , new ProgressSubscriber<HttpResult<String>>(context, new RequestImpl<HttpResult<String>>() {
                    @Override
                    public void onNext(HttpResult<String> result) {
                        callback.onSuccessResult(result.getMsg());
                    }
                }));
    }
}
package com.fcn.park.info.presenter;

import android.support.v7.widget.RecyclerView;

import com.fcn.park.base.BasePresenter;
import com.fcn.park.base.utils.OnDataGetCallback;
import com.fcn.park.info.contract.InfoNewsListContract;
import com.fcn.park.info.bean.NewsListBean;
import com.fcn.park.info.module.InfoNewsListModule;
import com.fcn.park.info.view.InfoNewsDetailActivity;

import java.util.List;

/**
 * Created by liuyq on 2018/04/10.
 * 取得新闻列表活动列表公告列表presenter
 */

public class InfoNewsListPresenter extends BasePresenter<InfoNewsListContract.View> implements  InfoNewsListContract.Presenter{

    private InfoNewsListModule mModule;

    @Override
    public void attach(InfoNewsListContract.View view) {
        super.attach(view);
        mModule =  new InfoNewsListModule();
        createItemClickListener(getView().getRecyclerView());

    }

    /**
     * 加载更多的数据
     * 只需要根据相应的页码加载相应的数据，无需关心刷新和加载更多
     *
     * @param page
     */
    public void onLoadMore(int page) {
        loadListData(page);
    }

    @Override
    public void loadListData() {
        getView().initRecyclerView();
        loadListData(1);
    }

    private void loadListData(int page) {
        mModule.requestNewsList(getView().getNewsId(),getView().getContext(), page, new OnDataGetCallback<List<NewsListBean.GetlistNewsBean>>() {
            @Override
            public void onSuccessResult(List<NewsListBean.GetlistNewsBean> getListNewsBeen) {
                getView().updateIsEnd(mModule.isEnd());
                getView().bindListData(getListNewsBeen);
            }
        });
    }

    /**
     * 点击item跳转到公告/新闻/活动详情页面
     * @param vh
     * @param position
     */
    @Override
    public void onItemClick(RecyclerView.ViewHolder vh, int position) {
        String newsId = mModule.getNewsList().get(position).getNewsId();
        InfoNewsDetailActivity.actionStart(getView().getContext(), newsId);
    }
}

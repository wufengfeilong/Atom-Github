package com.fcn.park.manager.presenter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.fcn.park.base.BasePresenter;
import com.fcn.park.base.utils.OnDataGetCallback;
import com.fcn.park.manager.activity.ManagerPostNewsDetailActivity;
import com.fcn.park.manager.bean.ManagerPostNewsListBean;
import com.fcn.park.manager.contract.ManagerPostNewsContract;
import com.fcn.park.manager.module.ManagerPostNewsListModule;

import java.util.List;

/**
 * 管理中心的发布新闻、公告、活动用Presenter.
 */
public class ManagerPostNewsPresenter
        extends BasePresenter<ManagerPostNewsContract.View>
        implements ManagerPostNewsContract.Presenter {

    private String TAG = "=== ManagerPostNewsPresenter ===";
    private ManagerPostNewsListModule mManagerPostNewsListModule;

    @Override
    public void attach(ManagerPostNewsContract.View view) {
        super.attach(view);
        mManagerPostNewsListModule = new ManagerPostNewsListModule();
        createItemClickListener(getView().getRecyclerView());
    }

    @Override
    public void loadListData() {
        getView().initRecyclerView();
        loadListData(getView().getNewsType(), 1);
    }

    /**
     * 加载更多的数据
     * 只需要根据相应的页码加载相应的数据，无需关心刷新和加载更多
     *
     * @param page
     */
    @Override
    public void onLoadMore(int page) {
        loadListData(getView().getNewsType(), page);
    }

    private void loadListData(String newsType, int page) {
        Log.d(TAG, "====== loadListData() page = " + page);
        mManagerPostNewsListModule.requestNewsList(getView().getContext(), newsType, page, new OnDataGetCallback<List<ManagerPostNewsListBean.ListNewsBean>>() {
            @Override
            public void onSuccessResult(List<ManagerPostNewsListBean.ListNewsBean> listNewsBeen) {
                Log.d(TAG, "====== loadListData() listNewsBeen = " + listNewsBeen);
                getView().updateIsEnd(mManagerPostNewsListModule.isEnd());
                getView().bindListData(listNewsBeen);
            }
        });
    }

    @Override
    public void onItemClick(RecyclerView.ViewHolder vh, int position) {
        Log.d(TAG, "====== onItemClick() position = " + position);
        String newsId = mManagerPostNewsListModule.getNewsList().get(position).getNewsId();
        Log.d(TAG, "====== onItemClick() newsId = " + newsId);
        Log.d(TAG, "====== onItemClick() newsType = " + getView().getNewsType());
        ManagerPostNewsDetailActivity.actionStart(getView().getContext(), getView().getNewsType(), newsId);
    }

    /**
     * 长按删除事件
     */
    @Override
    public void onItemLongClick() {
        final int position = getView().getClickPosition();
        if (position > mManagerPostNewsListModule.getNewsList().size() || position < 0) return;
        final ManagerPostNewsListBean.ListNewsBean listNewsBean = mManagerPostNewsListModule.getNewsList().get(position);
        mManagerPostNewsListModule.deleteNewsListItem(getView().getContext(), listNewsBean.getNewsId(), new OnDataGetCallback<String>() {
            @Override
            public void onSuccessResult(String data) {
                getView().showToast(data);
                mManagerPostNewsListModule.getNewsList().remove(listNewsBean);
                getView().deleteListItem(position);
            }
        });
    }
}

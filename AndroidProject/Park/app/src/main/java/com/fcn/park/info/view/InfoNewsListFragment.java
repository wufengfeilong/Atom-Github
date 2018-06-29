package com.fcn.park.info.view;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fcn.park.BR;
import com.fcn.park.R;
import com.fcn.park.base.BaseFragment;
import com.fcn.park.base.adapter.SimpleBindingAdapter;
import com.fcn.park.base.recycler_listener.OnRecyclerItemClickListener;
import com.fcn.park.databinding.InfoNewsListBinding;
import com.fcn.park.info.bean.NewsListBean;
import com.fcn.park.info.contract.InfoNewsListContract;
import com.fcn.park.info.presenter.InfoNewsListPresenter;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;

import java.util.List;

/**
 * 新闻活动公告的列表界面
 */
public class InfoNewsListFragment extends BaseFragment<InfoNewsListBinding, InfoNewsListContract.View, InfoNewsListPresenter>
        implements InfoNewsListContract.View, SpringView.OnFreshListener {

    private SimpleBindingAdapter<NewsListBean.GetlistNewsBean> mAdapter;
    private static final String NEWS_TAG = "news_tag";

    private String mNewsId;

    private boolean isEnd;

    public static InfoNewsListFragment newInstance(String newsId) {
        InfoNewsListFragment fragment = new InfoNewsListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(NEWS_TAG, newsId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Bundle bundle = getArguments();
            mNewsId = bundle.getString(NEWS_TAG);
        }
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.info_news_list;
    }

    @Override
    protected void initViews() {
        mPresenter.loadListData();
    }

    @Override
    protected InfoNewsListPresenter createPresenter() {
        return new InfoNewsListPresenter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public String getNewsId() {
        return mNewsId;
    }

    @Override
    public void initRecyclerView() {
        mDataBinding.springView.setListener(this);
        mDataBinding.springView.setHeader(new DefaultHeader(mContext));
        mDataBinding.springView.setFooter(new DefaultFooter(mContext));

        mDataBinding.recyclerView.addOnItemTouchListener(new OnItemClickListener(mDataBinding.recyclerView));
        mDataBinding.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mDataBinding.recyclerView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        mDataBinding.recyclerView.setRefrshView(mDataBinding.springView);
        mDataBinding.recyclerView.setEmptyView(mDataBinding.getRoot().findViewById(R.id.empty_view));
        mAdapter = new SimpleBindingAdapter<>(R.layout.info_item_layout_news_list, BR.newsListBean);
        mDataBinding.recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void updateIsEnd(boolean isEnd) {
        this.isEnd = isEnd;
    }

    private class OnItemClickListener extends OnRecyclerItemClickListener {

        public OnItemClickListener(RecyclerView recyclerView) {
            super(recyclerView);
        }

        @Override
        public void onItemClick(RecyclerView.ViewHolder vh) {
            mPresenter.onItemClick(vh, vh.getLayoutPosition());
        }
    }


    @Override
    public RecyclerView getRecyclerView() {
        return mDataBinding.recyclerView;
    }

    @Override
    public void bindListData(List<NewsListBean.GetlistNewsBean> beanList) {
        setListData(beanList);
        mDataBinding.springView.onFinishFreshAndLoad();
        mAdapter.setupData(beanList);
    }

    @Override
    protected void loadListData(int page) {
        mPresenter.onLoadMore(page);
    }

    /**
     * 下拉刷新，回调接口
     */
    @Override
    public void onRefresh() {
        onRefresh(isEnd);
    }

    /**
     * 上拉加载，回调接口
     */
    @Override
    public void onLoadmore() {
        onLoadMore(isEnd);
    }


    @Override
    protected ViewGroup getRefreshView() {
        return mDataBinding.springView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}

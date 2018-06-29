package com.fcn.park.info.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fcn.park.R;
import com.fcn.park.base.BaseFragment;
import com.fcn.park.base.recycler_listener.OnRecyclerItemClickListener;
import com.fcn.park.databinding.InfoDemandListBinding;
import com.fcn.park.info.bean.DemandListBean;
import com.fcn.park.info.contract.InfoDemandListContract;
import com.fcn.park.info.holder.InfoDemandListAdapter;
import com.fcn.park.info.presenter.InfoDemandListPresenter;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;

import java.util.List;

/**
 * Created by liuyq on 2018/04/19.
 * 园区动态企业需求列表
 */

public class InfoDemandListFragment extends BaseFragment<InfoDemandListBinding, InfoDemandListContract.View, InfoDemandListPresenter>
        implements InfoDemandListContract.View, SpringView.OnFreshListener {

    private OnItemClickListener mOnItemClickListener;

    private InfoDemandListAdapter mAdapter;
    private boolean isEnd;

    public static InfoDemandListFragment newInstance() {
        InfoDemandListFragment fragment = new InfoDemandListFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.info_demand_list;
    }

    @Override
    protected void initViews() {
        mPresenter.loadListData();
        mOnItemClickListener = new OnItemClickListener(getRecyclerView());
        mDataBinding.recyclerView.addOnItemTouchListener(mOnItemClickListener);
    }

    @Override
    protected InfoDemandListPresenter createPresenter() {
        return new InfoDemandListPresenter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void initRecyclerView() {
        mDataBinding.springView.setListener(this);
        mDataBinding.springView.setHeader(new DefaultHeader(mContext));
        mDataBinding.springView.setFooter(new DefaultFooter(mContext));

        mDataBinding.recyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));
        mAdapter = new InfoDemandListAdapter(R.layout.item_layout_demand_list);
        mDataBinding.recyclerView.setRefrshView(mDataBinding.springView);
        mDataBinding.recyclerView.setEmptyView(mDataBinding.getRoot().findViewById(R.id.empty_view));
        mDataBinding.recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void updateIsEnd(boolean isEnd) {
        this.isEnd = isEnd;
    }

    private class OnItemClickListener extends OnRecyclerItemClickListener {

        OnItemClickListener(RecyclerView recyclerView) {
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
    public void bindListData(List<DemandListBean.DemandsListBean> beanList) {
        setListData(beanList);
        mDataBinding.springView.onFinishFreshAndLoad();
        mAdapter.setupData(beanList);
    }

    @Override
    protected void loadListData(int page) {
        mPresenter.loadListData(page);
    }

    @Override
    protected ViewGroup getRefreshView() {
        return mDataBinding.springView;
    }

    /**
     * 上拉刷新
     */
    @Override
    public void onRefresh() {
        onRefresh(isEnd);
    }

    /**
     * 下拉加载
     */
    @Override
    public void onLoadmore() {
        onLoadMore(isEnd);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}

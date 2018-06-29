package com.fcn.park.home.advertisement.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.android.databinding.library.baseAdapters.BR;
import com.fcn.park.R;
import com.fcn.park.base.BaseActivity;
import com.fcn.park.base.adapter.SimpleBindingAdapter;
import com.fcn.park.base.recycler_listener.OnRecyclerItemClickListener;
import com.fcn.park.databinding.ActivityAdvertisementListBinding;
import com.fcn.park.home.advertisement.contract.AdvertisementListContract;
import com.fcn.park.home.advertisement.presenter.AdvertisementListPresenter;
import com.fcn.park.manager.bean.ManagerAdvertisingApprovalListBean;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;

import java.util.List;

/**
 * Created by 860115001 on 2018/04/27.
 */

public class AdvertisementListActivity
        extends BaseActivity<ActivityAdvertisementListBinding, AdvertisementListContract.View, AdvertisementListPresenter>
        implements AdvertisementListContract.View, SpringView.OnFreshListener{

    private SimpleBindingAdapter<ManagerAdvertisingApprovalListBean.ListAdvertisingApprovalBean> mSimpleBindingAdapter;
    // 广告位待审核列表中点击项目的Position值
    private int mClickPosition;

    private boolean isEnd;

    /**
     * 设置标题
     */
    @Override
    protected void setupTitle() {
        setTitleText("广告列表");
        openTitleLeftView(true);
    }

    /**
     * 画面初期化
     */
    @Override
    protected void initViews() {
        mPresenter.loadListData();
    }

    /**
     * 分页加载数据
     * @param page
     */
    @Override
    protected void loadListData(int page) {
        mPresenter.loadListData(page);
    }

    /**
     * 获取LayoutId(
     * @return
     */
    @Override
    protected int getLayoutId() {
        return R.layout.activity_advertisement_list;
    }

    /**
     * 获取Presenter实例
     * @return
     */
    @Override
    protected AdvertisementListPresenter createPresenter() {
        return new AdvertisementListPresenter();
    }

    @Override
    public void initRecyclerView() {
        mDataBinding.springViewAdvertisement.setListener(this);
        mDataBinding.springViewAdvertisement.setHeader(new DefaultHeader(mContext));
        mDataBinding.springViewAdvertisement.setFooter(new DefaultFooter(mContext));

        mDataBinding.recyclerViewAdvertisement.addOnItemTouchListener(new OnItemClickListener(mDataBinding.recyclerViewAdvertisement));
        mDataBinding.recyclerViewAdvertisement.setLayoutManager(new LinearLayoutManager(mContext));
//        mDataBinding.recyclerViewAdvertisement.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        mDataBinding.recyclerViewAdvertisement.setRefrshView(mDataBinding.springViewAdvertisement);
        mDataBinding.recyclerViewAdvertisement.setEmptyView(mDataBinding.getRoot().findViewById(R.id.empty_view));
        mSimpleBindingAdapter = new SimpleBindingAdapter<>(R.layout.item_layout_advertisement_list, BR.listAdvertisingApprovalBean);
        mDataBinding.recyclerViewAdvertisement.setAdapter(mSimpleBindingAdapter);
    }

    /**
     * 绑定列表数据
     */
    @Override
    public void bindListData(List<ManagerAdvertisingApprovalListBean.ListAdvertisingApprovalBean> beanList) {
        setListData(beanList);
        mDataBinding.springViewAdvertisement.onFinishFreshAndLoad();
        mSimpleBindingAdapter.setupData(beanList);
    }

    @Override
    protected ViewGroup getRefreshView() {
        return mDataBinding.springViewAdvertisement;
    }

    @Override
    public void updateIsEnd(boolean isEnd) {
        this.isEnd = isEnd;
    }

    @Override
    public RecyclerView getRecyclerView() {
        return mDataBinding.recyclerViewAdvertisement;
    }

    /**
     * 获取点击位置
     * @return
     */
    @Override
    public int getClickPosition() {
        return mClickPosition;
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

    /**
     * 广告位待审核列表的Item的点击事件
     */
    private class OnItemClickListener extends OnRecyclerItemClickListener {

        public OnItemClickListener(RecyclerView recyclerView) {
            super(recyclerView);
        }

        @Override
        public void onItemClick(RecyclerView.ViewHolder vh) {
            mClickPosition = vh.getAdapterPosition();
            mPresenter.onItemClick(vh, vh.getLayoutPosition());
        }
    }
}

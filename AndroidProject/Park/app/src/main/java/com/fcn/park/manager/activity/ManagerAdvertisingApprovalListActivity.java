package com.fcn.park.manager.activity;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;

import com.android.databinding.library.baseAdapters.BR;
import com.fcn.park.R;
import com.fcn.park.base.BaseActivity;
import com.fcn.park.base.adapter.SimpleBindingAdapter;
import com.fcn.park.base.recycler_listener.OnRecyclerItemClickListener;
import com.fcn.park.databinding.ManagerAdvertisingApprovalListBinding;
import com.fcn.park.manager.bean.ManagerAdvertisingApprovalListBean;
import com.fcn.park.manager.contract.ManagerAdvertisingApprovalListContract;
import com.fcn.park.manager.presenter.ManagerAdvertisingApprovalListPresenter;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;

import java.util.List;

/**
 * 广告位待审核列表画面
 */
public class ManagerAdvertisingApprovalListActivity
        extends BaseActivity<ManagerAdvertisingApprovalListBinding, ManagerAdvertisingApprovalListContract.View, ManagerAdvertisingApprovalListPresenter>
        implements ManagerAdvertisingApprovalListContract.View, SpringView.OnFreshListener {

    private String TAG = "=== ManagerAdvertisingApprovalListActivity ===";

    private SimpleBindingAdapter<ManagerAdvertisingApprovalListBean.ListAdvertisingApprovalBean> mSimpleBindingAdapter;

    // 广告位待审核列表中点击项目的Position值
    private int mClickPosition;

    private boolean isEnd;

    /**
     * 重写的方法，用来设置标题栏要显示的文字
     */
    @Override
    protected void setupTitle() {
        setTitleText(getString(R.string.manager_advertising_position_management_check));
        openTitleLeftView(true);
    }

    /**
     * 重写的方法，用来加载定义画面的Layout
     *
     * @return
     */
    @Override
    protected int getLayoutId() {
        return R.layout.manager_advertising_approval_list;
    }

    @Override
    protected ManagerAdvertisingApprovalListPresenter createPresenter() {
        return new ManagerAdvertisingApprovalListPresenter();
    }

    /**
     * 画面初始化时，调用此方法，向后台请求画面要显示的数据
     */
    @Override
    protected void initViews() {
        mPresenter.loadListData();
    }

    /**
     * 当前列表画面处于后台状态时，将isPause置True
     */
    private boolean isPause = false;
    @Override
    protected void onPause() {
        super.onPause();
        isPause = true;
    }

    /**
     * 从广告详情画面返回后，重新请求当前列表画面的数据
     */
    @Override
    protected void onResume() {
        super.onResume();
        if (isPause) {
            mPresenter.loadListData();
        }
    }

    @Override
    public void updateIsEnd(boolean isEnd) {
        this.isEnd = isEnd;
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
        return mDataBinding.springViewManagerAdvertisingApprovalList;
    }

    /**
     * 下拉刷新，回调接口
     */
    @Override
    public void onRefresh() {
        onRefresh(isEnd);
    }

    /**
     * 分页加载数据
     * @param page
     */
    @Override
    protected void loadListData(int page) {
        mPresenter.onLoadMore(page);
    }

    /**
     * 重写的RecyclerViewContract的方法
     * @return
     */
    @Override
    public RecyclerView getRecyclerView() {
        return mDataBinding.recyclerViewManagerAdvertisingApprovalList;
    }

    @Override
    public int getClickPosition() {
        return mClickPosition;
    }

    /**
     * 重写的RecyclerViewContract的方法，将查询后台返回的数据显示到画面上
     * @param beanList
     */
    @Override
    public void bindListData(List<ManagerAdvertisingApprovalListBean.ListAdvertisingApprovalBean> beanList) {
        Log.d(TAG, "===== bindListData() =====");
        int beanSize = beanList.size();
        Log.d(TAG, "===== bindListData() beanSize = " + beanSize);
        // 设置列表画面中的“序号”字段值，从1开始
        if (beanList != null && beanSize > 0) {
            for (int i = 0; i < beanSize; i++) {
                beanList.get(i).setNo(String.valueOf(i + 1));
            }
        }
        setListData(beanList);
        mDataBinding.springViewManagerAdvertisingApprovalList.onFinishFreshAndLoad();
        mSimpleBindingAdapter.setupData(beanList);
    }

    /**
     * 重写的RecyclerViewContract的方法，初始化RecyclerView
     */
    @Override
    public void initRecyclerView() {
        mDataBinding.springViewManagerAdvertisingApprovalList.setListener(this);
        mDataBinding.springViewManagerAdvertisingApprovalList.setHeader(new DefaultHeader(mContext));
        mDataBinding.springViewManagerAdvertisingApprovalList.setFooter(new DefaultFooter(mContext));

        mDataBinding.recyclerViewManagerAdvertisingApprovalList.addOnItemTouchListener(new OnItemClickListener(mDataBinding.recyclerViewManagerAdvertisingApprovalList));
        mDataBinding.recyclerViewManagerAdvertisingApprovalList.setLayoutManager(new LinearLayoutManager(mContext));
        mDataBinding.recyclerViewManagerAdvertisingApprovalList.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        mDataBinding.recyclerViewManagerAdvertisingApprovalList.setRefrshView(mDataBinding.springViewManagerAdvertisingApprovalList);
        mDataBinding.recyclerViewManagerAdvertisingApprovalList.setEmptyView(mDataBinding.getRoot().findViewById(R.id.empty_view_manager_advertising_approval_list));
        mSimpleBindingAdapter = new SimpleBindingAdapter<>(R.layout.item_layout_advertising_approval_list, BR.listAdvertisingApprovalBean);
        mDataBinding.recyclerViewManagerAdvertisingApprovalList.setAdapter(mSimpleBindingAdapter);
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
package com.fcn.park.rent.activity;
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
import com.fcn.park.databinding.RentReleasedHouseListBinding;
import com.fcn.park.rent.bean.RentReleasedHouseListBean;
import com.fcn.park.rent.contract.RentReleasedHouseListContract;
import com.fcn.park.rent.presenter.RentReleasedHouseListPresenter;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;

import java.util.List;

public class RentReleasedHouseListActivity  extends BaseActivity<RentReleasedHouseListBinding, RentReleasedHouseListContract.View, RentReleasedHouseListPresenter>
        implements RentReleasedHouseListContract.View, SpringView.OnFreshListener {

    private String TAG = "=== RentReleasedHouseListActivity ===";
    private SimpleBindingAdapter<RentReleasedHouseListBean.ListReleasedHouseBean> mSimpleBindingAdapter;

    private boolean isEnd;
    private int mClickPosition;

    @Override
    protected int getLayoutId() {
        return R.layout.rent_released_house_list;
    }

    @Override
    protected void setupTitle() {
        setTitleText("已发布房屋列表");
        openTitleLeftView(true);

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
     * 页面初始化
     */
    @Override
    protected void initViews() {
        mPresenter.loadListData();
    }

    /**
     * 上拉加载，回调接口
     */
    @Override
    public void onLoadmore() {
        Log.d(TAG, "====== onLoadmore() 上拉加载 isEnd = " + isEnd);
        onLoadMore(isEnd);
    }

    /**
     * 下拉刷新，回调接口
     */
    @Override
    public void onRefresh() {
        Log.d(TAG, "====== onRefresh() 上拉加载 isEnd = " + isEnd);
        onRefresh(isEnd);
    }

    @Override
    public void bindListData(List<RentReleasedHouseListBean.ListReleasedHouseBean> beanList) {
        Log.d(TAG, "====== bindListData() beanList = " + beanList);
        setListData(beanList);
        mDataBinding.springViewRentReleasedHouseList.onFinishFreshAndLoad();
        mSimpleBindingAdapter.setupData(beanList);
    }

    @Override
    protected ViewGroup getRefreshView() {
        return mDataBinding.springViewRentReleasedHouseList;
    }

    @Override
    public RecyclerView getRecyclerView() {
        return mDataBinding.recyclerViewRentReleasedHouseList;
    }

    @Override
    protected RentReleasedHouseListPresenter createPresenter() {
        return new RentReleasedHouseListPresenter();
    }


    @Override
    public void updateIsEnd(boolean isEnd) {
        this.isEnd = isEnd;
    }
    @Override
    public void initRecyclerView() {
        Log.d(TAG, "====== initRecyclerView() Start ======");

        mDataBinding.springViewRentReleasedHouseList.setListener(this);
        mDataBinding.springViewRentReleasedHouseList.setHeader(new DefaultHeader(mContext));
        mDataBinding.springViewRentReleasedHouseList.setFooter(new DefaultFooter(mContext));
        mDataBinding.recyclerViewRentReleasedHouseList.addOnItemTouchListener(new RentReleasedHouseListActivity.OnItemClickListener(mDataBinding.recyclerViewRentReleasedHouseList));
        mDataBinding.recyclerViewRentReleasedHouseList.setLayoutManager(new LinearLayoutManager(mContext));
        mDataBinding.recyclerViewRentReleasedHouseList.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        mDataBinding.recyclerViewRentReleasedHouseList.setRefrshView(mDataBinding.recyclerViewRentReleasedHouseList);
        mDataBinding.recyclerViewRentReleasedHouseList.setEmptyView(mDataBinding.getRoot().findViewById(R.id.empty_view_rent_post_house_list));
        mSimpleBindingAdapter = new SimpleBindingAdapter<>(R.layout.item_layout_released_house_list, BR.houseReleasedListBean);
        mDataBinding.recyclerViewRentReleasedHouseList.setAdapter(mSimpleBindingAdapter);
        Log.d(TAG, "====== initRecyclerView() End ======");
    }

    @Override
    public int getClickPosition() {
        return mClickPosition;
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
}

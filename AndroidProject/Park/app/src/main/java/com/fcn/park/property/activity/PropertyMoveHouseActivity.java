package com.fcn.park.property.activity;

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
import com.fcn.park.databinding.PropertyMoveHouseLayoutBinding;
import com.fcn.park.property.bean.PropertyMoveHouseBean;
import com.fcn.park.property.contract.PropertyMoveHouseContract;
import com.fcn.park.property.presenter.PropertyMoveHousePresenter;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;

import java.util.List;

/**
 * 搬家服务
 */

public class PropertyMoveHouseActivity extends BaseActivity<PropertyMoveHouseLayoutBinding, PropertyMoveHouseContract.View, PropertyMoveHousePresenter>
        implements PropertyMoveHouseContract.View, SpringView.OnFreshListener {
    private SimpleBindingAdapter<PropertyMoveHouseBean.MoveHouseBean> mSimpleBindingAdapter;
    private String TAG = "=== ManagerPostNewsListActivity ===";
    private boolean isEnd;

    @Override
    protected PropertyMoveHousePresenter createPresenter() {
        return new PropertyMoveHousePresenter();
    }

    @Override
    protected void setupTitle() {
        String titleStr = getString(R.string.property_move_house_title);
        setTitleText(titleStr);
        openTitleLeftView(true);
    }

    @Override
    protected void initViews() {
        mPresenter.loadListData();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.property_move_house_layout;
    }

    //设置bean数据源
    @Override
    public void bindListData(List<PropertyMoveHouseBean.MoveHouseBean> beanList) {
        setListData(beanList);
        mDataBinding.springViewMoveHouseList.onFinishFreshAndLoad();
        mSimpleBindingAdapter.setupData(beanList);
    }

    @Override
    protected void loadListData(int page) {
        mPresenter.loadListData(page);
    }

    @Override
    public RecyclerView getRecyclerView() {
        return mDataBinding.propertyMoveHouse;
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
    protected ViewGroup getRefreshView() {
        return mDataBinding.springViewMoveHouseList;
    }

    @Override
    public void updateIsEnd(boolean isEnd) {
        this.isEnd = isEnd;
    }

    //RecyclerView初始化进行的操作
    @Override
    public void initRecyclerView() {
        mDataBinding.springViewMoveHouseList.setListener(this);
        mDataBinding.springViewMoveHouseList.setHeader(new DefaultHeader(mContext));
        mDataBinding.springViewMoveHouseList.setFooter(new DefaultFooter(mContext));
        mDataBinding.propertyMoveHouse.addOnItemTouchListener(new PropertyMoveHouseActivity.OnItemClickListener(mDataBinding.propertyMoveHouse));
        mDataBinding.propertyMoveHouse.setLayoutManager(new LinearLayoutManager(mContext));
        mDataBinding.propertyMoveHouse.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        //设置刷新的样式
        mDataBinding.propertyMoveHouse.setRefrshView(mDataBinding.springViewMoveHouseList);
        //设置没有数据的样式
        mDataBinding.propertyMoveHouse.setEmptyView(mDataBinding.getRoot().findViewById(R.id.empty_move_house_list));
        mSimpleBindingAdapter = new SimpleBindingAdapter<>(R.layout.property_move_house_item, BR.moveHouse);
        mDataBinding.propertyMoveHouse.setAdapter(mSimpleBindingAdapter);
    }

    //ListView的点击事件
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


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
import com.fcn.park.databinding.PropertyPlantLeaseLayoutBinding;
import com.fcn.park.property.bean.PropertyPlantLeaseBean;
import com.fcn.park.property.contract.PropertyPlantLeaseContract;
import com.fcn.park.property.presenter.PropertyPlantLeasePresenter;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;

import java.util.List;

/**
 * 绿植租赁服务
 */
public class PropertyPlantLeaseActivity extends BaseActivity<PropertyPlantLeaseLayoutBinding, PropertyPlantLeaseContract.View, PropertyPlantLeasePresenter>
        implements PropertyPlantLeaseContract.View, SpringView.OnFreshListener {
    private SimpleBindingAdapter<PropertyPlantLeaseBean.PlantLeaseBean> mSimpleBindingAdapter;
    private String TAG = "=== ManagerPostNewsListActivity ===";
    private boolean isEnd;

    @Override
    protected PropertyPlantLeasePresenter createPresenter() {
        return new PropertyPlantLeasePresenter();
    }

    @Override
    protected void setupTitle() {
        String titleStr = getString(R.string.property_plant_lease_title);
        setTitleText(titleStr);
        openTitleLeftView(true);
    }

    @Override
    protected void initViews() {
        mPresenter.loadListData();

    }

    @Override
    protected int getLayoutId() {
        return R.layout.property_plant_lease_layout;
    }

    //设置bean数据源
    @Override
    public void bindListData(List<PropertyPlantLeaseBean.PlantLeaseBean> beanList) {
        setListData(beanList);
        mDataBinding.springViewPlantLeaseList.onFinishFreshAndLoad();
        mSimpleBindingAdapter.setupData(beanList);
    }

    @Override
    protected void loadListData(int page) {
        mPresenter.loadListData(page);
    }

    @Override
    public RecyclerView getRecyclerView() {
        return mDataBinding.propertyPlantLease;
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
        return mDataBinding.springViewPlantLeaseList;
    }

    //刷新结束
    @Override
    public void updateIsEnd(boolean isEnd) {
        this.isEnd = isEnd;
    }

    //RecyclerView初始化进行的操作
    @Override
    public void initRecyclerView() {
        mDataBinding.springViewPlantLeaseList.setListener(this);
        mDataBinding.springViewPlantLeaseList.setHeader(new DefaultHeader(mContext));
        mDataBinding.springViewPlantLeaseList.setFooter(new DefaultFooter(mContext));
        mDataBinding.propertyPlantLease.addOnItemTouchListener(new PropertyPlantLeaseActivity.OnItemClickListener(mDataBinding.propertyPlantLease));
        mDataBinding.propertyPlantLease.setLayoutManager(new LinearLayoutManager(mContext));
        mDataBinding.propertyPlantLease.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        //设置刷新的样式
        mDataBinding.propertyPlantLease.setRefrshView(mDataBinding.springViewPlantLeaseList);
        //设置没有数据的样式
        mDataBinding.propertyPlantLease.setEmptyView(mDataBinding.getRoot().findViewById(R.id.empty_plant_lease_list));
        mSimpleBindingAdapter = new SimpleBindingAdapter<>(R.layout.property_plant_lease_item, BR.plantLease);
        mDataBinding.propertyPlantLease.setAdapter(mSimpleBindingAdapter);
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

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
import com.fcn.park.databinding.PropertyDeliverWaterLayoutBinding;
import com.fcn.park.property.bean.PropertyDeliverWaterBean;
import com.fcn.park.property.contract.PropertyDeliverWaterContract;
import com.fcn.park.property.presenter.PropertyDeliverWaterPresenter;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;

import java.util.List;

/**
 * 送水服务
 */
public class PropertyDeliverWaterActivity extends BaseActivity<PropertyDeliverWaterLayoutBinding, PropertyDeliverWaterContract.View, PropertyDeliverWaterPresenter>
        implements PropertyDeliverWaterContract.View, SpringView.OnFreshListener {
    private SimpleBindingAdapter<PropertyDeliverWaterBean.DeliverWaterBean> mSimpleBindingAdapter;
    private String TAG = "=== ManagerPostNewsListActivity ===";
    private boolean isEnd;

    @Override
    protected PropertyDeliverWaterPresenter createPresenter() {
        return new PropertyDeliverWaterPresenter();
    }

    @Override
    protected void setupTitle() {
        String titleStr = getString(R.string.property_deliver_water_title);
        setTitleText(titleStr);
        openTitleLeftView(true);
    }

    @Override
    protected void initViews() {
        mPresenter.loadListData();
    }

    @Override
    protected void loadListData(int page) {
        mPresenter.loadListData(page);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.property_deliver_water_layout;
    }

    //设置bean数据源
    @Override
    public void bindListData(List<PropertyDeliverWaterBean.DeliverWaterBean> beanList) {
        setListData(beanList);
        mDataBinding.springViewDeliverVaterList.onFinishFreshAndLoad();
        mSimpleBindingAdapter.setupData(beanList);
    }

    @Override
    public RecyclerView getRecyclerView() {
        return mDataBinding.propertyDeliverWater;
    }

    @Override
    protected ViewGroup getRefreshView() {
        return mDataBinding.springViewDeliverVaterList;
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

    //刷新结束
    @Override
    public void updateIsEnd(boolean isEnd) {
        this.isEnd = isEnd;
    }

    //RecyclerView初始化进行的操作
    @Override
    public void initRecyclerView() {
        mDataBinding.springViewDeliverVaterList.setListener(this);
        mDataBinding.springViewDeliverVaterList.setHeader(new DefaultHeader(mContext));
        mDataBinding.springViewDeliverVaterList.setFooter(new DefaultFooter(mContext));
        mDataBinding.propertyDeliverWater.addOnItemTouchListener(new OnItemClickListener(mDataBinding.propertyDeliverWater));
        mDataBinding.propertyDeliverWater.setLayoutManager(new LinearLayoutManager(mContext));
        mDataBinding.propertyDeliverWater.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        //设置刷新的样式
        mDataBinding.propertyDeliverWater.setRefrshView(mDataBinding.springViewDeliverVaterList);
        //设置没有数据的样式
        mDataBinding.propertyDeliverWater.setEmptyView(mDataBinding.getRoot().findViewById(R.id.empty_deliver_water_list));
        mSimpleBindingAdapter = new SimpleBindingAdapter<>(R.layout.property_deliver_water_item, BR.deliverWater);
        mDataBinding.propertyDeliverWater.setAdapter(mSimpleBindingAdapter);
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

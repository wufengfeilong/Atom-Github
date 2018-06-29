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
import com.fcn.park.databinding.PropertyCleanLayoutBinding;
import com.fcn.park.property.bean.PropertyCleanBean;
import com.fcn.park.property.contract.PropertyCleanContract;
import com.fcn.park.property.presenter.PropertyCleanPresenter;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;

import java.util.List;

/**
 * 清洗服务
 */
public class PropertyCleanActivity extends BaseActivity<PropertyCleanLayoutBinding, PropertyCleanContract.View, PropertyCleanPresenter>
        implements PropertyCleanContract.View, SpringView.OnFreshListener {

    private SimpleBindingAdapter<PropertyCleanBean.CleanBean> mSimpleBindingAdapter;
    private String TAG = "=== ManagerPostNewsListActivity ===";
    private boolean isEnd;

    @Override
    protected PropertyCleanPresenter createPresenter() {
        return new PropertyCleanPresenter();
    }

    @Override
    protected void setupTitle() {
        String titleStr = getString(R.string.property_clean_title);
        setTitleText(titleStr);
        openTitleLeftView(true);
    }

    @Override
    protected void initViews() {
        mPresenter.loadListData();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.property_clean_layout;
    }

    @Override
    public void bindListData(List<PropertyCleanBean.CleanBean> beanList) {
        setListData(beanList);
        mDataBinding.springViewCleanList.onFinishFreshAndLoad();
        mSimpleBindingAdapter.setupData(beanList);
    }

    @Override
    protected ViewGroup getRefreshView() {
        return mDataBinding.springViewCleanList;
    }

    @Override
    protected void loadListData(int page) {
        mPresenter.loadListData(page);
    }

    @Override
    public RecyclerView getRecyclerView() {
        return mDataBinding.propertyClean;
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
        mDataBinding.springViewCleanList.setListener(this);
        mDataBinding.springViewCleanList.setHeader(new DefaultHeader(mContext));
        mDataBinding.springViewCleanList.setFooter(new DefaultFooter(mContext));
        mDataBinding.propertyClean.addOnItemTouchListener(new PropertyCleanActivity.OnItemClickListener(mDataBinding.propertyClean));
        mDataBinding.propertyClean.setLayoutManager(new LinearLayoutManager(mContext));
        mDataBinding.propertyClean.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        //设置刷新的样式
        mDataBinding.propertyClean.setRefrshView(mDataBinding.springViewCleanList);
        //设置没有数据的样式
        mDataBinding.propertyClean.setEmptyView(mDataBinding.getRoot().findViewById(R.id.empty_clean_list));
        mSimpleBindingAdapter = new SimpleBindingAdapter<>(R.layout.property_clean_item, BR.clean);
        mDataBinding.propertyClean.setAdapter(mSimpleBindingAdapter);
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

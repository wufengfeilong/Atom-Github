package com.fcn.park.rent.activity;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.android.databinding.library.baseAdapters.BR;
import com.fcn.park.R;
import com.fcn.park.base.BaseActivity;
import com.fcn.park.base.adapter.SimpleBindingAdapter;
import com.fcn.park.base.constant.Constant;
import com.fcn.park.base.recycler_listener.OnRecyclerItemClickListener;
import com.fcn.park.databinding.RentPostHouseListBinding;
import com.fcn.park.rent.bean.RentHouseListBean;
import com.fcn.park.rent.contract.RentHouseListContract;
import com.fcn.park.rent.presenter.RentHousePresenter;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;

import java.util.List;


/**
 * 房屋列表用Activity.
 */
public class RentHouseListActivity
        extends BaseActivity<RentPostHouseListBinding, RentHouseListContract.View, RentHousePresenter>
        implements RentHouseListContract.View, SpringView.OnFreshListener, View.OnClickListener {

    private String TAG = "=== RentHouseListActivity ===";
    private SimpleBindingAdapter<RentHouseListBean.ListHouseBean> mSimpleBindingAdapter;

    private boolean isEnd;

    @Override
    protected int getLayoutId() {
        return R.layout.rent_post_house_list;
    }

    @Override
    protected void setupTitle() {
        setTitleText("房屋列表");
        openTitleLeftView(true);
    }

    /**
     * 获取画面来源
     *
     * @return
     */
    @Override
    public String getFromId() {
        String id = getIntent().getStringExtra(Constant.RENT_FROM);
        return id;
    }

    /**
     * 页面初始化
     */
    @Override
    protected void initViews() {
        mPresenter.loadListData();
        mDataBinding.setPresenter(mPresenter);
        mSearchView.setVisibility(View.VISIBLE);
        mSearchView.setImageResource(R.drawable.ic_vector_post_news_add);
        mSearchView.setOnClickListener(this);
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
        return mDataBinding.springViewRentPostHouseList;
    }

    @Override
    public void bindListData(List<RentHouseListBean.ListHouseBean> beanList) {
        Log.d(TAG, "====== bindListData() beanList = " + beanList);
        setListData(beanList);
        mSimpleBindingAdapter.setupData(beanList);
    }
    @Override
    protected void loadListData(int page) {
        mPresenter.onLoadMore(page);
    }

    @Override
    public RecyclerView getRecyclerView() {
        return mDataBinding.recyclerViewRentPostHouseList;
    }

    @Override
    protected RentHousePresenter createPresenter() {
        return new RentHousePresenter();
    }

    @Override
    public void updateIsEnd(boolean isEnd) {
        this.isEnd = isEnd;
    }

    /**
     * 初始化列表信息
     */
    @Override
    public void initRecyclerView() {
        Log.d(TAG, "====== initRecyclerView() Start ======");
        mDataBinding.springViewRentPostHouseList.setListener(this);
        mDataBinding.springViewRentPostHouseList.setHeader(new DefaultHeader(mContext));
        mDataBinding.springViewRentPostHouseList.setFooter(new DefaultFooter(mContext));
        mDataBinding.recyclerViewRentPostHouseList.addOnItemTouchListener(new OnItemClickListener(mDataBinding.recyclerViewRentPostHouseList));
        mDataBinding.recyclerViewRentPostHouseList.setLayoutManager(new LinearLayoutManager(mContext));
        mDataBinding.recyclerViewRentPostHouseList.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        mDataBinding.recyclerViewRentPostHouseList.setRefrshView(mDataBinding.recyclerViewRentPostHouseList);
        mDataBinding.recyclerViewRentPostHouseList.setEmptyView(mDataBinding.getRoot().findViewById(R.id.empty_view_rent_post_house_list));
        mSimpleBindingAdapter = new SimpleBindingAdapter<>(R.layout.item_layout_post_house_list, BR.houseListBean);
        mDataBinding.recyclerViewRentPostHouseList.setAdapter(mSimpleBindingAdapter);
        Log.d(TAG, "====== initRecyclerView() End ======");
    }

    /**
     * 房屋追加按钮点击事件
     * @param view
     */
    @Override
    public void onClick(View view) {
        mPresenter.houseAdd();
    }


    /**
     * 列表条目点击事件
     */
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
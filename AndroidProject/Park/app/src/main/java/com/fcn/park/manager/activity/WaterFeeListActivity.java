package com.fcn.park.manager.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.fcn.park.R;
import com.fcn.park.base.BaseActivity;
import com.fcn.park.base.adapter.BindingViewHolder;
import com.fcn.park.databinding.ItemLayoutWaterFeeListBinding;
import com.fcn.park.databinding.ManagerWaterfeeListBinding;
import com.fcn.park.manager.adapter.WaterFeeListAdapter;
import com.fcn.park.manager.bean.WaterFeeListBean;
import com.fcn.park.manager.contract.WaterFeeListContract;
import com.fcn.park.manager.presenter.WaterFeeListPresenter;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;

import java.util.List;

/**
 * 水费列表画面用Activity
 */
public class WaterFeeListActivity
        extends BaseActivity<ManagerWaterfeeListBinding, WaterFeeListContract.View, WaterFeeListPresenter>
        implements WaterFeeListContract.View, SpringView.OnFreshListener {

    private static final int PUSH_MANAGE_REQUEST_CODE = 0x0000101e;

    private WaterFeeListAdapter mWaterFeeListAdapter;
    private boolean isEnd;
    private int mClickPosition;

    /**
     * @param context
     */
    public static void actionStart(Context context) {
        Intent intent = new Intent(context, WaterFeeListActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void setupTitle() {
        setTitleText(getString(R.string.manager_water_fee_list));
        openTitleLeftView(true);
    }

    @Override
    protected void initViews() {
        mPresenter.loadListData();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.manager_waterfee_list;
    }

    @Override
    protected WaterFeeListPresenter createPresenter() {
        return new WaterFeeListPresenter();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initRecyclerView() {
        mDataBinding.springViewWaterFee.setHeader(new DefaultHeader(mContext));
        mDataBinding.springViewWaterFee.setFooter(new DefaultFooter(mContext));
        mDataBinding.springViewWaterFee.setListener(this);

        mDataBinding.recyclerViewWaterFee.setLayoutManager(new LinearLayoutManager(mContext));
        //mDataBinding.recyclerView.addOnItemTouchListener(new OnItemClickListener());
        mDataBinding.recyclerViewWaterFee.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        mWaterFeeListAdapter = new WaterFeeListAdapter(R.layout.item_layout_water_fee_list) {
            @Override
            public void bindingViews(BindingViewHolder<ItemLayoutWaterFeeListBinding> holder, int position, WaterFeeListBean.ListWaterBean waterFeeListBean) {
                super.bindingViews(holder, position, waterFeeListBean);
                ItemLayoutWaterFeeListBinding binding = (ItemLayoutWaterFeeListBinding) holder.getBinding();
                final int fPosition = holder.getAdapterPosition();
                // 水费条目点击事件
                binding.getRoot().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mClickPosition = fPosition;
                        mPresenter.onItemClick();
                    }
                });
                // 催缴费按钮点击事件
                binding.btnDoWith.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mClickPosition = fPosition;
                        mPresenter.onMailSendClick();
                    }
                });
            }
        };
        mDataBinding.recyclerViewWaterFee.setAdapter(mWaterFeeListAdapter);
        mDataBinding.recyclerViewWaterFee.setRefrshView(mDataBinding.springViewWaterFee);
        mDataBinding.recyclerViewWaterFee.setEmptyView(findViewById(R.id.empty_view));
    }

    @Override
    public void bindListData(List<WaterFeeListBean.ListWaterBean> beanList) {
        setListData(beanList);
        mWaterFeeListAdapter.setupData(beanList);
    }

    @Override
    public void updateIsEnd(boolean isEnd) {
        this.isEnd = isEnd;
    }

    @Override
    public RecyclerView getRecyclerView() {
        return mDataBinding.recyclerViewWaterFee;
    }

    @Override
    protected ViewGroup getRefreshView() {
        return mDataBinding.springViewWaterFee;
    }

    @Override
    public void onRefresh() {
        onRefresh(isEnd);
    }

    @Override
    public void onLoadmore() {
        onLoadMore(isEnd);
    }

    @Override
    protected void loadListData(int page) {
        mPresenter.onLoadMore(page);
    }

    @Override
    public int getClickPosition() {
        return mClickPosition;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PUSH_MANAGE_REQUEST_CODE:
                    onRefresh();
                    break;
            }
        }
    }
}
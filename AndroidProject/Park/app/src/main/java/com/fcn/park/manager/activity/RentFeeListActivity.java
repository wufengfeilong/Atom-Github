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
import com.fcn.park.databinding.ItemLayoutRentFeeListBinding;
import com.fcn.park.databinding.ManagerRentfeeListBinding;
import com.fcn.park.manager.adapter.RentFeeListAdapter;
import com.fcn.park.manager.bean.RentFeeListBean;
import com.fcn.park.manager.contract.RentFeeListContract;
import com.fcn.park.manager.presenter.RentFeeListPresenter;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;

import java.util.List;

/**
 * 租赁费列表画面用Activity
 */
public class RentFeeListActivity
        extends BaseActivity<ManagerRentfeeListBinding, RentFeeListContract.View, RentFeeListPresenter>
        implements RentFeeListContract.View, SpringView.OnFreshListener {

    private static final int PUSH_MANAGE_REQUEST_CODE = 0x0000101e;

    private RentFeeListAdapter mRentFeeListAdapter;
    private boolean isEnd;
    private int mClickPosition;

    /**
     * @param context
     */
    public static void actionStart(Context context) {
        Intent intent = new Intent(context, RentFeeListActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void setupTitle() {
        setTitleText(getString(R.string.manager_rent_list));
        openTitleLeftView(true);
    }

    @Override
    protected void initViews() {
        mPresenter.loadListData();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.manager_rentfee_list;
    }

    @Override
    protected RentFeeListPresenter createPresenter() {
        return new RentFeeListPresenter();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initRecyclerView() {
        mDataBinding.springViewRentFee.setHeader(new DefaultHeader(mContext));
        mDataBinding.springViewRentFee.setFooter(new DefaultFooter(mContext));
        mDataBinding.springViewRentFee.setListener(this);

        mDataBinding.recyclerViewRentFee.setLayoutManager(new LinearLayoutManager(mContext));
        //mDataBinding.recyclerView.addOnItemTouchListener(new OnItemClickListener());
        mDataBinding.recyclerViewRentFee.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        mRentFeeListAdapter = new RentFeeListAdapter(R.layout.item_layout_rent_fee_list) {
            @Override
            public void bindingViews(BindingViewHolder<ItemLayoutRentFeeListBinding> holder, int position, RentFeeListBean.ListRentBean rentFeeListBean) {
                super.bindingViews(holder, position, rentFeeListBean);
                ItemLayoutRentFeeListBinding binding = (ItemLayoutRentFeeListBinding)holder.getBinding();
                final int fPosition = holder.getAdapterPosition();
                // 租赁费条目点击事件
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
        mDataBinding.recyclerViewRentFee.setAdapter(mRentFeeListAdapter);
        mDataBinding.recyclerViewRentFee.setRefrshView(mDataBinding.springViewRentFee);
        mDataBinding.recyclerViewRentFee.setEmptyView(findViewById(R.id.empty_view));
    }

    @Override
    public void bindListData(List<RentFeeListBean.ListRentBean> beanList) {
        setListData(beanList);
        mRentFeeListAdapter.setupData(beanList);
    }

    @Override
    public void updateIsEnd(boolean isEnd) {
        this.isEnd = isEnd;
    }

    @Override
    public RecyclerView getRecyclerView() {
        return mDataBinding.recyclerViewRentFee;
    }

    @Override
    protected ViewGroup getRefreshView() {
        return mDataBinding.springViewRentFee;
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
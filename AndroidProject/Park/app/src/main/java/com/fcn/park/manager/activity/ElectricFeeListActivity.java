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
import com.fcn.park.databinding.ItemLayoutElectricFeeListBinding;
import com.fcn.park.databinding.ManagerElectricfeeListBinding;
import com.fcn.park.manager.adapter.ElectricFeeListAdapter;
import com.fcn.park.manager.bean.ElectricFeeListBean;
import com.fcn.park.manager.contract.ElectricFeeListContract;
import com.fcn.park.manager.presenter.ElectricFeeListPresenter;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;

import java.util.List;

/**
 * 电费列表画面用Activity
 */
public class ElectricFeeListActivity
        extends BaseActivity<ManagerElectricfeeListBinding, ElectricFeeListContract.View, ElectricFeeListPresenter>
        implements ElectricFeeListContract.View, SpringView.OnFreshListener {

    private static final int PUSH_MANAGE_REQUEST_CODE = 0x0000101e;

    private ElectricFeeListAdapter mElectricFeeListAdapter;
    private boolean isEnd;
    private int mClickPosition;

    /**
     * @param context
     */
    public static void actionStart(Context context) {
        Intent intent = new Intent(context, ElectricFeeListActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void setupTitle() {
        setTitleText(getString(R.string.manager_electricity_fee_list));
        openTitleLeftView(true);
    }

    @Override
    protected void initViews() {
        mPresenter.loadListData();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.manager_electricfee_list;
    }

    @Override
    protected ElectricFeeListPresenter createPresenter() {
        return new ElectricFeeListPresenter();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initRecyclerView() {
        mDataBinding.springViewElectricFee.setHeader(new DefaultHeader(mContext));
        mDataBinding.springViewElectricFee.setFooter(new DefaultFooter(mContext));
        mDataBinding.springViewElectricFee.setListener(this);

        mDataBinding.recyclerViewElectricFee.setLayoutManager(new LinearLayoutManager(mContext));
        //mDataBinding.recyclerView.addOnItemTouchListener(new OnItemClickListener());
        mDataBinding.recyclerViewElectricFee.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        mElectricFeeListAdapter = new ElectricFeeListAdapter(R.layout.item_layout_electric_fee_list) {
            @Override
            public void bindingViews(BindingViewHolder<ItemLayoutElectricFeeListBinding> holder, int position, ElectricFeeListBean.ListElectricBean electricFeeListBean) {
                super.bindingViews(holder, position, electricFeeListBean);
                ItemLayoutElectricFeeListBinding binding = (ItemLayoutElectricFeeListBinding)holder.getBinding();
                final int fPosition = holder.getAdapterPosition();
                // 电费条目点击事件
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
        mDataBinding.recyclerViewElectricFee.setAdapter(mElectricFeeListAdapter);
        mDataBinding.recyclerViewElectricFee.setRefrshView(mDataBinding.springViewElectricFee);
        mDataBinding.recyclerViewElectricFee.setEmptyView(findViewById(R.id.empty_view));
    }

    @Override
    public void bindListData(List<ElectricFeeListBean.ListElectricBean> beanList) {
        setListData(beanList);
        mElectricFeeListAdapter.setupData(beanList);
    }

    @Override
    public void updateIsEnd(boolean isEnd) {
        this.isEnd = isEnd;
    }

    @Override
    public RecyclerView getRecyclerView() {
        return mDataBinding.recyclerViewElectricFee;
    }

    @Override
    protected ViewGroup getRefreshView() {
        return mDataBinding.springViewElectricFee;
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

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
import com.fcn.park.databinding.ItemLayoutPropertyFeeListBinding;
import com.fcn.park.databinding.ManagerPropertyfeeListBinding;
import com.fcn.park.manager.adapter.PropertyFeeListAdapter;
import com.fcn.park.manager.bean.PropertyFeeListBean;
import com.fcn.park.manager.contract.PropertyFeeListContract;
import com.fcn.park.manager.presenter.PropertyFeeListPresenter;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;

import java.util.List;

/**
 * 物业费收支列表画面用Activity
 */
public class PropertyFeeListActivity
        extends BaseActivity<ManagerPropertyfeeListBinding, PropertyFeeListContract.View, PropertyFeeListPresenter>
        implements PropertyFeeListContract.View, SpringView.OnFreshListener {

    private static final int PUSH_MANAGE_REQUEST_CODE = 0x0000101e;

    private PropertyFeeListAdapter mPropertyFeeListAdapter;
    private boolean isEnd;
    private int mClickPosition;

    /**
     * @param context
     */
    public static void actionStart(Context context) {
        Intent intent = new Intent(context, PropertyFeeListActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void setupTitle() {
        setTitleText(getString(R.string.manager_property_management_fee_list));
        openTitleLeftView(true);
    }

    @Override
    protected void initViews() {
        mPresenter.loadListData();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.manager_propertyfee_list;
    }

    @Override
    protected PropertyFeeListPresenter createPresenter() {
        return new PropertyFeeListPresenter();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initRecyclerView() {
        mDataBinding.springViewPropertyFee.setHeader(new DefaultHeader(mContext));
        mDataBinding.springViewPropertyFee.setFooter(new DefaultFooter(mContext));
        mDataBinding.springViewPropertyFee.setListener(this);

        mDataBinding.recyclerViewPropertyFee.setLayoutManager(new LinearLayoutManager(mContext));
        //mDataBinding.recyclerViewPropertyFee.addOnItemTouchListener(new OnItemClickListener());
        mDataBinding.recyclerViewPropertyFee.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        mPropertyFeeListAdapter = new PropertyFeeListAdapter(R.layout.item_layout_property_fee_list) {
            @Override
            public void bindingViews(BindingViewHolder<ItemLayoutPropertyFeeListBinding> holder, int position, PropertyFeeListBean.ListPropertyBean propertyFeeListBean) {
                super.bindingViews(holder, position, propertyFeeListBean);
                ItemLayoutPropertyFeeListBinding binding = (ItemLayoutPropertyFeeListBinding)holder.getBinding();
                final int fPosition = holder.getAdapterPosition();
                // 物业费条目点击事件
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
        mDataBinding.recyclerViewPropertyFee.setAdapter(mPropertyFeeListAdapter);
        mDataBinding.recyclerViewPropertyFee.setRefrshView(mDataBinding.springViewPropertyFee);
        mDataBinding.recyclerViewPropertyFee.setEmptyView(findViewById(R.id.empty_view_property_fee));
    }

    @Override
    public void bindListData(List<PropertyFeeListBean.ListPropertyBean> beanList) {
        setListData(beanList);
        mPropertyFeeListAdapter.setupData(beanList);
    }

    @Override
    public void updateIsEnd(boolean isEnd) {
        this.isEnd = isEnd;
    }

    @Override
    public RecyclerView getRecyclerView() {
        return mDataBinding.recyclerViewPropertyFee;
    }

    @Override
    protected ViewGroup getRefreshView() {
        return mDataBinding.springViewPropertyFee;
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

//    private class OnItemClickListener extends OnRecyclerItemClickListener {
//        public OnItemClickListener() {
//            super(mDataBinding.recyclerViewPropertyFee);
//        }
//        @Override
//        public void onItemClick(RecyclerView.ViewHolder vh) {
//            mClickItemPosition = vh.getLayoutPosition();
//            mPresenter.onItemClick(vh, mClickItemPosition);
//        }
//
//    }
}
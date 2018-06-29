package com.fcn.park.info.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.fcn.park.R;
import com.android.databinding.library.baseAdapters.BR;
import com.fcn.park.base.BaseActivity;
import com.fcn.park.base.adapter.SimpleBindingAdapter;
import com.fcn.park.base.recycler_listener.OnRecyclerItemClickListener;
import com.fcn.park.databinding.ManagerDemandListBinding;
import com.fcn.park.info.bean.ManagerDemandListBean;
import com.fcn.park.info.contract.ManagerDemandListContract;
import com.fcn.park.info.presenter.ManagerDemandListPresenter;
import com.fcn.park.manager.utils.DialogUtils;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;

import java.util.List;

/**
 * Created by liuyq on 2018/04/23.
 * 企业需求的列表页面
 */

public class ManagerDemandListActivity extends BaseActivity<ManagerDemandListBinding, ManagerDemandListContract.View, ManagerDemandListPresenter>
        implements ManagerDemandListContract.View, SpringView.OnFreshListener {

    private SimpleBindingAdapter<ManagerDemandListBean.DemandListBean> mSimpleBindingAdapter;

    private boolean isEnd;
    private int mClickPosition;

    @Override
    protected void setupTitle() {
        setTitleText("企业需求列表");
        openTitleLeftView(true);
        mLayoutTitleRight.setVisibility(View.VISIBLE);
        mTvTitleRight.setText(R.string.manager_publish_title);
        mLayoutTitleRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                mActivity.finish();
                intent.setClass(getContext(), ManagerDemandAddActivity.class);
                getContext().startActivity(intent);
            }
        });
    }

    @Override
    protected void initViews() {
        mPresenter.loadListData();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.manager_demand_list;
    }

    @Override
    protected ManagerDemandListPresenter createPresenter() {
        return new ManagerDemandListPresenter();
    }


    @Override
    protected void loadListData(int page) {
        mPresenter.loadListData(page);
    }

    @Override
    protected ViewGroup getRefreshView() {
        return mDataBinding.springView;
    }

    /**
     * 上拉刷新
     */
    @Override
    public void onRefresh() {
        onRefresh(isEnd);
    }

    /**
     * 下拉加载
     */
    @Override
    public void onLoadmore() {
        onLoadMore(isEnd);
    }


    @Override
    public void updateIsEnd(boolean isEnd) {
        this.isEnd = isEnd;
    }

    @Override
    public void initRecyclerView() {
        mDataBinding.springView.setListener(this);
        mDataBinding.springView.setHeader(new DefaultHeader(mContext));
        mDataBinding.springView.setFooter(new DefaultFooter(mContext));
        mDataBinding.recyclerView.addOnItemTouchListener(new OnItemClickListener(mDataBinding.recyclerView));
        mDataBinding.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mDataBinding.recyclerView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        mDataBinding.recyclerView.setRefrshView(mDataBinding.springView);
        mDataBinding.recyclerView.setEmptyView(mDataBinding.getRoot().findViewById(R.id.empty_view));
        mSimpleBindingAdapter = new SimpleBindingAdapter<>(R.layout.item_manager_demand, BR.demandListBean);
        mDataBinding.recyclerView.setAdapter(mSimpleBindingAdapter);
    }

    private class OnItemClickListener extends OnRecyclerItemClickListener {

        public OnItemClickListener(RecyclerView recyclerView) {
            super(recyclerView);
        }

        /**
         * 点击跳转
         * @param vh
         */
        @Override
        public void onItemClick(RecyclerView.ViewHolder vh) {
            mPresenter.onItemClick(vh, vh.getLayoutPosition());
        }

        /**
         * 长按删除
         * @param vh
         * @return
         */
        @Override
        public boolean onItemLongClick(final RecyclerView.ViewHolder vh) {
            DialogUtils.buildAlertDialogWithCancel(mContext, "温馨提示", "您是否要删除该项目需求条目")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mClickPosition = vh.getAdapterPosition();
                            mPresenter.onItemLongClick();
                        }
                    }).show();
            return true;
        }
    }


    @Override
    public void bindListData(List<ManagerDemandListBean.DemandListBean> beanList) {
        setListData(beanList);
        mDataBinding.springView.onFinishFreshAndLoad();
        mSimpleBindingAdapter.setupData(beanList);
    }


    @Override
    public RecyclerView getRecyclerView() {
        return mDataBinding.recyclerView;
    }


    @Override
    public int getClickPosition() {
        return mClickPosition;
    }

    /**
     * 删除item
     * @param position
     */
    @Override
    public void deleteListItem(int position) {
        mSimpleBindingAdapter.notifyItemRemoved(position);
    }
}

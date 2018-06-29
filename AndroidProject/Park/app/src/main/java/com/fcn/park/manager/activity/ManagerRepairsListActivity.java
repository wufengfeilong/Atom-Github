package com.fcn.park.manager.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;

import com.fcn.park.BR;
import com.fcn.park.R;
import com.fcn.park.base.BaseActivity;
import com.fcn.park.base.adapter.SimpleBindingAdapter;
import com.fcn.park.base.recycler_listener.OnRecyclerItemClickListener;
import com.fcn.park.databinding.ManagerRepairsListBinding;
import com.fcn.park.manager.bean.ManagerRepairsListBean;
import com.fcn.park.manager.contract.ManagerRepairsListContract;
import com.fcn.park.manager.presenter.ManagerRepairsListPresenter;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import java.util.List;

/**
 * Created by 丁胜胜 on 2018/04/12
 * 类描述：管理中心：报修一览画面用Activity
 */

public class ManagerRepairsListActivity
            extends BaseActivity<ManagerRepairsListBinding,ManagerRepairsListContract.View,ManagerRepairsListPresenter>
            implements ManagerRepairsListContract.View, SpringView.OnFreshListener{

        private SimpleBindingAdapter<ManagerRepairsListBean.RepairsListBean> mSimpleBindingAdapter;

        private String mRepairId;
        private boolean isEnd;
        // 报修一览列表中点击项目的Position值
        private int mClickPosition;
        private String TAG = "== ManagerRepairsListActivity ==";


    /**
         * 重写的方法，用来加载定义画面的Layout
         * @return
         */
        @Override
        protected int getLayoutId() {
            return R.layout.manager_repairs_list;
        }

        /**
         * 设置标题以及返回按钮显示
         */
        @Override
        protected void setupTitle() {
                setTitleText(getString(R.string.manager_publish_repairs_list));
                openTitleLeftView(true);
        }

        /**
         * 画面初始化操作：调用此方法，向后台请求画面要显示的数据
         */
        @Override
        protected void initViews() {
            mPresenter.loadListData();
        }

        /**
         * 上拉加载，回调接口
         */
        @Override
        public void onLoadmore() {
            Log.d(TAG, "onLoadmore() 上拉加载 isEnd = " + isEnd);
            onLoadMore(isEnd);
        }

        @Override
        protected void loadListData(int page) {
            mPresenter.loadListData(page);
        }

        /**
         * 下拉刷新，回调接口
         */
        @Override
        public void onRefresh() {
            Log.d(TAG, "onRefresh() 下拉刷新 isEnd = " + isEnd);
            onRefresh(isEnd);
        }

        /**
         * 重写的RecyclerViewContract的方法，将查询后台返回的数据显示到画面上
         * @param beanList
         */
        @Override
        public void bindListData(List<ManagerRepairsListBean.RepairsListBean> beanList) {
            setListData(beanList);
            mDataBinding.springViewRepairs.onFinishFreshAndLoad();
            mSimpleBindingAdapter.setupData(beanList);
        }

        @Override
        protected ViewGroup getRefreshView() {
            return mDataBinding.springViewRepairs;
        }


        /**
         * 获取RecyclerView
         * @return
         */
        @Override
        public RecyclerView getRecyclerView() {
            return mDataBinding.recyclerViewRepairs;
        }

        @Override
        protected ManagerRepairsListPresenter createPresenter() {
            return new ManagerRepairsListPresenter();
        }

        /**
         * 是否已加载全部
         */
        @Override
        public void updateIsEnd(boolean isEnd) {
            this.isEnd = isEnd;
        }

        /**
         *RecyclerView初始化进行的操作
         */
        @Override
        public void initRecyclerView() {
            mDataBinding.springViewRepairs.setHeader(new DefaultHeader(mContext));
            mDataBinding.springViewRepairs.setFooter(new DefaultFooter(mContext));
            mDataBinding.springViewRepairs.setListener(this);

            //item点击事件
            mDataBinding.recyclerViewRepairs.addOnItemTouchListener(new OnItemClickListener(mDataBinding.recyclerViewRepairs));
            //创建布局管理器，LinearLayoutManager，线性排列
            mDataBinding.recyclerViewRepairs.setLayoutManager(new LinearLayoutManager(mContext));
            mDataBinding.recyclerViewRepairs.setRefrshView(mDataBinding.recyclerViewRepairs);
            //实例化RecycleView
            mDataBinding.recyclerViewRepairs.setEmptyView(mDataBinding.getRoot().findViewById(R.id.empty_view_repairs));
            mSimpleBindingAdapter = new SimpleBindingAdapter<>(R.layout.item_layout_repairs_list, BR.bean);
            mDataBinding.recyclerViewRepairs.setAdapter(mSimpleBindingAdapter);
        }

        /**
         * 获取报修列表id
         * @return
         */
        @Override
        public String getRepairId() {
            return mRepairId;
        }

        /**
         * 获取点击位置
         * @return
         */
        @Override
        public int getClickPosition() {
            return mClickPosition;
        }

        @Override
        public void deleteListItem(int position) {

        }

        /**
         * 列表的点击事件
         */
        private class OnItemClickListener extends OnRecyclerItemClickListener {
            public OnItemClickListener(RecyclerView recyclerView) {
                super(recyclerView);
            }
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                mClickPosition = vh.getAdapterPosition();
                mPresenter.onItemClick(vh, vh.getLayoutPosition());
            }
        }
}

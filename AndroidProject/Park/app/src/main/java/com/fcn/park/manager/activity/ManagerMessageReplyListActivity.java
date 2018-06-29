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
import com.fcn.park.databinding.ManagerMessageReplyListBinding;
import com.fcn.park.manager.bean.ManagerMessageReplyListBean;
import com.fcn.park.manager.contract.ManagerMessageReplyListContract;
import com.fcn.park.manager.presenter.ManagerMessageReplyListPresenter;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;

import java.util.List;

/**
 * Created by 丁胜胜 on 2018/04/25
 * 类描述：管理中心的留言列表用Activity
 */

public class ManagerMessageReplyListActivity
        extends BaseActivity<ManagerMessageReplyListBinding,ManagerMessageReplyListContract.View,ManagerMessageReplyListPresenter>
        implements ManagerMessageReplyListContract.View, SpringView.OnFreshListener{


        private boolean isEnd;
        private SimpleBindingAdapter<ManagerMessageReplyListBean.ManagerReplyBean> mSimpleBindingAdapter;
        private int mClickPosition;

        private String TAG = "== ManagerMessageReplyListActivity ==";

        /**
         * 画面初始化时，调用此方法，向后台请求画面要显示的数据
         */
        @Override
        public void initRecyclerView() {
                mDataBinding.springViewMessageReply.setHeader(new DefaultHeader(mContext));
                mDataBinding.springViewMessageReply.setFooter(new DefaultFooter(mContext));
                mDataBinding.springViewMessageReply.setListener(this);

                //item点击事件
                mDataBinding.recyclerViewMessageReply.addOnItemTouchListener(new ManagerMessageReplyListActivity.OnItemClickListener(mDataBinding.recyclerViewMessageReply));

                mDataBinding.recyclerViewMessageReply.setLayoutManager(new LinearLayoutManager(mContext));
                //设置刷新的样式
                mDataBinding.recyclerViewMessageReply.setRefrshView(mDataBinding.recyclerViewMessageReply);
                //实例化RecycleView，设置没有数据的样式
                mDataBinding.recyclerViewMessageReply.setEmptyView(mDataBinding.getRoot().findViewById(R.id.empty_view_Message_Reply));
                mSimpleBindingAdapter = new SimpleBindingAdapter<>(R.layout.item_layout_message_reply_list, BR.bean);
                mDataBinding.recyclerViewMessageReply.setAdapter(mSimpleBindingAdapter);
        }

        /**
         * 重写的RecyclerViewContract的方法，将查询后台返回的数据显示到画面上
         * @param beanList
         */
        @Override
        public void bindListData(List<ManagerMessageReplyListBean.ManagerReplyBean> beanList) {
                setListData(beanList);
                mDataBinding.springViewMessageReply.onFinishFreshAndLoad();
                mSimpleBindingAdapter.setupData(beanList);
        }

        /**
         * 获取RecyclerView
         */
        @Override
        public RecyclerView getRecyclerView() {
                return mDataBinding.recyclerViewMessageReply;
        }

        /**
         * 设置标题以及返回按钮显示
         */
        @Override
        protected void setupTitle() {
                setTitleText(getString(R.string.manager_reply_message_list));
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
         * 重写的方法，用来加载定义画面的Layout
         * @return
         */
        @Override
        protected int getLayoutId() {
                return R.layout.manager_message_reply_list;
        }

        @Override
        protected ManagerMessageReplyListPresenter createPresenter() {
                return new ManagerMessageReplyListPresenter();
        }

        /**
         * 获取点击位置
         * @return
         */
        @Override
        public int getClickPosition() {
             return mClickPosition;
        }

        /**
         * 上拉加载，回调接口
         */
        @Override
        public void onLoadmore() {
                Log.d(TAG, "onLoadmore() 上拉加载 isEnd = " + isEnd);
                onLoadMore(isEnd);
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
         * 是否已加载全部
         */
        @Override
        public void updateIsEnd(boolean isEnd) {
                this.isEnd = isEnd;
        }

        @Override
        protected void loadListData(int page) {
                mPresenter.loadListData(page);
        }

        @Override
        protected ViewGroup getRefreshView() {
                return mDataBinding.springViewMessageReply;
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
                        mPresenter.onItemClick(vh, vh.getLayoutPosition());
                }
        }
}

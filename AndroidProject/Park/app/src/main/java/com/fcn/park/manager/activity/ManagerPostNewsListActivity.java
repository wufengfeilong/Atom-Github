package com.fcn.park.manager.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.fcn.park.R;
import com.android.databinding.library.baseAdapters.BR;
import com.fcn.park.base.BaseActivity;
import com.fcn.park.base.adapter.SimpleBindingAdapter;
import com.fcn.park.base.constant.Constant;
import com.fcn.park.base.recycler_listener.OnRecyclerItemClickListener;
import com.fcn.park.databinding.ManagerPostNewsListBinding;
import com.fcn.park.manager.bean.ManagerPostNewsListBean;
import com.fcn.park.manager.contract.ManagerPostNewsContract;
import com.fcn.park.manager.presenter.ManagerPostNewsPresenter;
import com.fcn.park.manager.utils.DialogUtils;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;

import java.util.List;

/**
 * 管理中心的新闻、公告、活动列表用Activity.
 */
public class ManagerPostNewsListActivity
        extends BaseActivity<ManagerPostNewsListBinding, ManagerPostNewsContract.View, ManagerPostNewsPresenter>
        implements ManagerPostNewsContract.View, SpringView.OnFreshListener {

    private String TAG = "=== ManagerPostNewsListActivity ===";

    private SimpleBindingAdapter<ManagerPostNewsListBean.ListNewsBean> mSimpleBindingAdapter;

    private String mNewsType;
    private boolean isEnd;
    private int mClickPosition;

    /**
     * 重写的方法，用来加载定义画面的Layout
     * @return
     */
    @Override
    protected int getLayoutId() {
        return R.layout.manager_post_news_list;
    }

    /**
     * 重写的方法，用来设置标题栏要显示的文字
     */
    @Override
    protected void setupTitle() {
        mNewsType = getIntent().getStringExtra(Constant.NEWS_TYPE);
        if (mNewsType.equals(Constant.NEWS_TYPE_0)) {// 公告公示
            setTitle(getString(R.string.manager_make_announcement_list), Constant.NEWS_TYPE_0);
        } else if (mNewsType.equals(Constant.NEWS_TYPE_1)) {// 园区新闻
            setTitle(getString(R.string.manager_post_news_list), Constant.NEWS_TYPE_1);
        } else if (mNewsType.equals(Constant.NEWS_TYPE_2)) {// 园区活动
            setTitle(getString(R.string.manager_publish_activities_list), Constant.NEWS_TYPE_2);
        } else {
            showToast("未知的新闻类别！");
        }
    }

    private void setTitle(String titleText, final String newsType) {
        setTitleText(titleText);
        openTitleLeftView(true);
        mLayoutTitleRight.setVisibility(View.VISIBLE);
        mTvTitleRight.setText(R.string.manager_publish_title);
        mLayoutTitleRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra(Constant.NEWS_TYPE, newsType);
                intent.setClass(getContext(), ManagerPostNewsAddActivity.class);
                getContext().startActivity(intent);
            }
        });
    }

    @Override
    protected void initViews() {
        mPresenter.loadListData();
    }

    @Override
    public String getNewsType() {
        return mNewsType;
    }

    @Override
    public void updateIsEnd(boolean isEnd) {
        this.isEnd = isEnd;
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
        return mDataBinding.springViewManagerPostNewsList;
    }

    /**
     * 下拉刷新，回调接口
     */
    @Override
    public void onRefresh() {
        onRefresh(isEnd);
    }

    @Override
    protected void loadListData(int page) {
        mPresenter.onLoadMore(page);
    }

    @Override
    public void bindListData(List<ManagerPostNewsListBean.ListNewsBean> beanList) {
        Log.d(TAG, "====== bindListData() beanList = " + beanList);
        setListData(beanList);
        mSimpleBindingAdapter.setupData(beanList);
    }

    @Override
    public RecyclerView getRecyclerView() {
        return mDataBinding.recyclerViewManagerPostNewsList;
    }

    @Override
    protected ManagerPostNewsPresenter createPresenter() {
        return new ManagerPostNewsPresenter();
    }

    @Override
    public void initRecyclerView() {
        Log.d(TAG, "====== initRecyclerView() Start ======");
        mDataBinding.springViewManagerPostNewsList.setListener(this);
        mDataBinding.springViewManagerPostNewsList.setHeader(new DefaultHeader(mContext));
        mDataBinding.springViewManagerPostNewsList.setFooter(new DefaultFooter(mContext));

        mDataBinding.recyclerViewManagerPostNewsList.addOnItemTouchListener(new OnItemClickListener(mDataBinding.recyclerViewManagerPostNewsList));
        mDataBinding.recyclerViewManagerPostNewsList.setLayoutManager(new LinearLayoutManager(mContext));
        mDataBinding.recyclerViewManagerPostNewsList.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        mDataBinding.recyclerViewManagerPostNewsList.setRefrshView(mDataBinding.springViewManagerPostNewsList);
        mDataBinding.recyclerViewManagerPostNewsList.setEmptyView(mDataBinding.getRoot().findViewById(R.id.empty_view_manager_post_news_list));
        mSimpleBindingAdapter = new SimpleBindingAdapter<>(R.layout.item_layout_post_news_list, BR.newsListBean);
        mDataBinding.recyclerViewManagerPostNewsList.setAdapter(mSimpleBindingAdapter);
        Log.d(TAG, "====== initRecyclerView() End ======");
    }

    @Override
    public int getClickPosition() {
        return mClickPosition;
    }

    private class OnItemClickListener extends OnRecyclerItemClickListener {

        public OnItemClickListener(RecyclerView recyclerView) {
            super(recyclerView);
        }

        @Override
        public void onItemClick(RecyclerView.ViewHolder vh) {
            mPresenter.onItemClick(vh, vh.getLayoutPosition());
        }

        @Override
        public boolean onItemLongClick(final RecyclerView.ViewHolder vh) {
            DialogUtils.buildAlertDialogWithCancel(mContext, "温馨提示", "您是否要删除该新闻条目")
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
    public void deleteListItem(int position) {
        mSimpleBindingAdapter.notifyItemRemoved(position);
    }
}
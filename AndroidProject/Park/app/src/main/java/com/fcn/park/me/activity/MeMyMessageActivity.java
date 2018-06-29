package com.fcn.park.me.activity;

import android.content.Intent;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import com.android.databinding.library.baseAdapters.BR;
import com.fcn.park.R;
import com.fcn.park.base.BaseActivity;
import com.fcn.park.base.adapter.SimpleBindingAdapter;
import com.fcn.park.base.recycler_listener.OnRecyclerItemClickListener;
import com.fcn.park.databinding.MeMyMessageActivityBinding;
import com.fcn.park.me.bean.MeMessageRecordBean;
import com.fcn.park.me.contract.MeMyMessageContract;
import com.fcn.park.me.presenter.MeMyMessagePresenter;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;

import java.util.List;
/**
 * 个人中心-我的消息列表
 */
public class MeMyMessageActivity extends
        BaseActivity<MeMyMessageActivityBinding, MeMyMessageContract.View, MeMyMessagePresenter>
        implements  MeMyMessageContract.View, SpringView.OnFreshListener {

    private SimpleBindingAdapter<MeMessageRecordBean.ListMessageBean> mmAdapter;
    private boolean isEnd;
    private int mClickPosition;
    /**
     * 判断数据是否显示到了最后
     */
    public boolean isEnd() {
        return isEnd;
    }

    /**
     * 设置标题以及返回按钮显示
     */
    @Override
    protected void setupTitle() {
        setTitleText("消息列表");
        openTitleLeftView(true);
    }
    /**
     * 初期化视图
     */
    @Override
    protected void initViews() {
        mPresenter.loadListData();
    }
    /**
     * 设置视图布局
     */
    @Override
    protected int getLayoutId() {
        return R.layout.me_my_message_activity;
    }
    /**
     * 实例化presenter
     */
    @Override
    protected MeMyMessagePresenter createPresenter() {
        return new MeMyMessagePresenter();
    }

    /**
     * 获取点击位置
     */
    @Override
    public int getClickPosition() {
        return mClickPosition;
    }
    /**
     * 是否已加载全部
     * @param isEnd
     */
    @Override
    public void updateIsEnd(boolean isEnd) {
        this.isEnd = isEnd;
    }

    /**
     * 初始化RecyclerView
     */
    @Override
    public void initRecyclerView() {
        mDataBinding.springViewRecordList.setListener(this);
        mDataBinding.springViewRecordList.setHeader(new DefaultHeader(this));
        mDataBinding.springViewRecordList.setFooter(new DefaultFooter(this));

        mDataBinding.recyclerViewRecordList.addOnItemTouchListener(new OnItemClickListener(getRecyclerView()));
        mDataBinding.recyclerViewRecordList.setLayoutManager(new LinearLayoutManager(mContext));
        mDataBinding.recyclerViewRecordList.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        mDataBinding.recyclerViewRecordList.setRefrshView(mDataBinding.springViewRecordList);
        mDataBinding.recyclerViewRecordList.setEmptyView(findViewById(R.id.record_check_list_empty_view));
        mmAdapter  = new SimpleBindingAdapter<>(R.layout.item_me_my_message_list_activity, BR.listMessageBean);
        mDataBinding.recyclerViewRecordList.setAdapter(mmAdapter);
    }
    /**
     * 绑定list数据
     */
    @Override
    public void bindListData(List<MeMessageRecordBean.ListMessageBean> beanList) {
        setListData(beanList);
        mDataBinding.springViewRecordList.onFinishFreshAndLoad();
        mmAdapter.setupData(beanList);
    }
    /**
     * 获取RecyclerView
     */
    @Override
    public RecyclerView getRecyclerView() {
        return mDataBinding.recyclerViewRecordList;
    }
    /**
     * 刷新画面
     */
    @Override
    public void onRefresh() {
        onRefresh(isEnd);
    }
    /**
     * 上拉加载更多
     */
    @Override
    public void onLoadmore() {
        onLoadMore(isEnd);
    }

    @Override
    protected ViewGroup getRefreshView() {
        return mDataBinding.springViewRecordList;
    }

    @Override
    protected void loadListData(int page) {
        mPresenter.loadListData(page);
    }

    /**
     * 自定义Item监听
     */
    private class OnItemClickListener extends OnRecyclerItemClickListener {
        @Override
        public void onItemClick(RecyclerView.ViewHolder vh) {
            mPresenter.onItemClick(vh, vh.getLayoutPosition());
        }

        public OnItemClickListener(RecyclerView recyclerView) {
            super(recyclerView);
        }

    }
    /**
     * 带参数跳转画面
     */
    @Override
    public void jumpActivity(Class cl,String id){
        Intent i = new Intent(this,cl);
        i.putExtra("msgId",id);
        startActivity(i);
    }

    @Override
    protected void onResume() {
        super.onResume();
        onRefresh();
    }
}

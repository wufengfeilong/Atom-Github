package com.fcn.park.me.activity;
import android.content.Context;
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
import com.fcn.park.databinding.MeRepairRecordListActivityBinding;
import com.fcn.park.me.bean.RepairRecordBean;
import com.fcn.park.me.contract.MeRepairRecordContract;
import com.fcn.park.me.presenter.MeRepairRecordPresenter;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import java.util.List;
/**
 * 类描述：个人中心-报修一览画面用Activity
 */
public class MeRepairRecordListActivity extends
        BaseActivity<MeRepairRecordListActivityBinding, MeRepairRecordContract.View, MeRepairRecordPresenter>
        implements  MeRepairRecordContract.View, SpringView.OnFreshListener{


    private SimpleBindingAdapter<RepairRecordBean.ListRecordBean> mmAdapter;
    private static final String ID_TAG = "repairId";
    private boolean isEnd;
    private int mClickPosition;

    public SimpleBindingAdapter<RepairRecordBean.ListRecordBean> getMmAdapter() {
        return mmAdapter;
    }

    public boolean isEnd() {
        return isEnd;
    }

    public int getmClickPosition() {
        return mClickPosition;
    }
    /**
     * 重写的方法，用来加载定义画面的Layout
     * @return
     */
    @Override
    protected int getLayoutId() {
        return R.layout.me_repair_record_list_activity;
    }

    //设置标题为"报修列表"
    @Override
    protected void setupTitle() {
        setTitleText("报修列表");
        openTitleLeftView(true);
    }
    /**
     * 画面初始化操作：调用此方法，向后台请求画面要显示的数据
     */
    @Override
    protected void initViews() {
        mPresenter.loadListData();
    }

    @Override
    protected void loadListData(int page) {
        mPresenter.loadListData(page);
    }
    /**
     * 上拉加载，回调接口
     */
    @Override
    public void onLoadmore() {
        onLoadMore(isEnd);
    }
    /**
     * 下拉刷新，回调接口
     */
    @Override
    public void onRefresh() {
        onRefresh(isEnd);
    }


    /**
     * 重写的RecyclerViewContract的方法，将查询后台返回的数据显示到画面上
     * @param beanList
     */
    @Override
    public void bindListData(List<RepairRecordBean.ListRecordBean> beanList) {
        setListData(beanList);
        mDataBinding.springViewRecordList.onFinishFreshAndLoad();
        mmAdapter.setupData(beanList);
    }

    @Override
    public RecyclerView getRecyclerView() {
        return mDataBinding.recyclerViewRecordList;
    }

    @Override
    protected ViewGroup getRefreshView() {
        return mDataBinding.springViewRecordList;
    }


    @Override
    protected MeRepairRecordPresenter createPresenter() {
        return  new MeRepairRecordPresenter();
    }
    /**
     * 刷新结束
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
        mDataBinding.springViewRecordList.setListener(this);
        mDataBinding.springViewRecordList.setHeader(new DefaultHeader(this));
        mDataBinding.springViewRecordList.setFooter(new DefaultFooter(this));
        //item点击事件
        mDataBinding.recyclerViewRecordList.addOnItemTouchListener(new MeRepairRecordListActivity.OnItemClickListener(getRecyclerView()));
        //创建布局管理器，LinearLayoutManager，线性排列
        mDataBinding.recyclerViewRecordList.setLayoutManager(new LinearLayoutManager(mContext));
        mDataBinding.recyclerViewRecordList.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        mDataBinding.recyclerViewRecordList.setRefrshView(mDataBinding.springViewRecordList);
        //实例化RecycleView
        mDataBinding.recyclerViewRecordList.setEmptyView(findViewById(R.id.record_check_list_empty_view));
        mmAdapter  = new SimpleBindingAdapter<>(R.layout.item_me_repair_record_list_activity, BR.listRecordBean);
        mDataBinding.recyclerViewRecordList.setAdapter(mmAdapter);
    }

    @Override
    public int getClickPosition() {
        return mClickPosition;
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
    public static void actionStart(Context context, String repairId) {
        Intent intent = new Intent(context, MeRepairRecordDetailActivity.class);
        intent.putExtra(ID_TAG, repairId);
        context.startActivity(intent);
    }
}
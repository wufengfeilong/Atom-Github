package com.fcn.park.manager.activity;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;

import com.android.databinding.library.baseAdapters.BR;
import com.fcn.park.R;
import com.fcn.park.base.BaseActivity;
import com.fcn.park.base.adapter.SimpleBindingAdapter;
import com.fcn.park.base.recycler_listener.OnRecyclerItemClickListener;
import com.fcn.park.databinding.ManagerAdvertisingListBinding;
import com.fcn.park.manager.bean.ManagerAdvertisingApprovalListBean;
import com.fcn.park.manager.contract.ManagerAdvertisingListContract;
import com.fcn.park.manager.presenter.ManagerAdvertisingListPresenter;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 广告位已审核列表画面
 */
public class ManagerAdvertisingListActivity
        extends BaseActivity<ManagerAdvertisingListBinding, ManagerAdvertisingListContract.View, ManagerAdvertisingListPresenter>
        implements ManagerAdvertisingListContract.View, SpringView.OnFreshListener {

    private String TAG = "=== ManagerAdvertisingListActivity ===";

    private SimpleBindingAdapter<ManagerAdvertisingApprovalListBean.ListAdvertisingApprovalBean> mSimpleBindingAdapter;

    // 广告位待审核列表中点击项目的Position值
    private int mClickPosition;

    // 套餐一的名字与天数
    private String SET_TYPE1 = "套餐一";
    private int SET_TYPE1_DAYS = 30;// 一个月的天数
    // 套餐二的名字与天数
    private String SET_TYPE2 = "套餐二";
    private int SET_TYPE2_DAYS = 90;// 三个月的天数
    // 套餐三的名字与天数
    private String SET_TYPE3 = "套餐三";
    private int SET_TYPE3_DAYS = 365;// 一年的天数
    private boolean isPause = false;
    private boolean isEnd;

    /**
     * 重写的方法，用来设置标题栏要显示的文字
     */
    @Override
    protected void setupTitle() {
        setTitleText(getString(R.string.manager_advertising_position_management_list));
        openTitleLeftView(true);
    }

    /**
     * 重写的方法，用来加载定义画面的Layout
     *
     * @return
     */
    @Override
    protected int getLayoutId() {
        return R.layout.manager_advertising_list;
    }

    @Override
    protected ManagerAdvertisingListPresenter createPresenter() {
        return new ManagerAdvertisingListPresenter();
    }

    /**
     * 画面初始化时，调用此方法，向后台请求画面要显示的数据
     */
    @Override
    protected void initViews() {
        mPresenter.loadListData();
    }

    /**
     * 当前列表画面处于后台状态时，将isPause置True
     */
    @Override
    protected void onPause() {
        super.onPause();
        isPause = true;
    }

    /**
     * 从广告详情画面返回后，重新请求当前列表画面的数据
     */
    @Override
    protected void onResume() {
        super.onResume();
        if (isPause) {
            mPresenter.loadListData();
        }
    }

    /**
     * 重写的RecyclerViewContract的方法
     * @return
     */
    @Override
    public RecyclerView getRecyclerView() {
        return mDataBinding.recyclerViewManagerAdvertisingList;
    }

    @Override
    public int getClickPosition() {
        return mClickPosition;
    }

    /**
     * 重写的RecyclerViewContract的方法，将查询后台返回的数据显示到画面上
     * @param beanList
     */
    @Override
    public void bindListData(List<ManagerAdvertisingApprovalListBean.ListAdvertisingApprovalBean> beanList) {
        Log.d(TAG, "====== bindListData() beanList = " + beanList);
        int beanSize = beanList.size();

        if (beanList != null && beanSize > 0) {
            for (int i = 0; i < beanSize; i++) {
                // 设置列表画面中的“序号”字段值，从1开始
                beanList.get(i).setNo(String.valueOf(i + 1));

                if (beanList.get(i).getApprovalStatus().equals("1") && beanList.get(i).getPayStatus() == 0) {
                    // 如果审批状态为1（已审批）且付费状态为0（未付费）时，画面的“审批状况”显示“待付费”
                    beanList.get(i).setApprovalStatus("待付费");
                    // “开始日期”与“终止日期”都显示“-”
                    beanList.get(i).setPayTime("-");
                    beanList.get(i).setEndDays("-");

                } else if (beanList.get(i).getApprovalStatus().equals("1") && beanList.get(i).getPayStatus() == 1) {
                    // 如果审批状态为1（已审批）且付费状态为1（已付费）时，画面的“审批状况”显示“已付费”
                    beanList.get(i).setApprovalStatus("已付费");
                    // 计算剩余天数，并显示到“终止日期”项目中
                    if (beanList.get(i).getSetType().contains(SET_TYPE1)) {// 套餐一时，终止日期加上30天
                        beanList.get(i).setEndDays(getEndData(beanList.get(i).getPayTime(), SET_TYPE1_DAYS));
                    } else if (beanList.get(i).getSetType().contains(SET_TYPE2)) {// 套餐二时，终止日期加上90天
                        beanList.get(i).setEndDays(getEndData(beanList.get(i).getPayTime(), SET_TYPE2_DAYS));
                    } else if (beanList.get(i).getSetType().contains(SET_TYPE3)) {// 套餐三时，终止日期加上365天
                        beanList.get(i).setEndDays(getEndData(beanList.get(i).getPayTime(), SET_TYPE3_DAYS));
                    }

                } else if (beanList.get(i).getApprovalStatus().equals("2")) {
                    // 如果审批状态为2（拒绝）时，画面的“审批状况”显示“拒绝”
                    beanList.get(i).setApprovalStatus("拒绝");
                    // “开始日期”与“终止日期”都显示“-”
                    beanList.get(i).setPayTime("-");
                    beanList.get(i).setEndDays("-");
                }
            }
        }

        setListData(beanList);
        mDataBinding.springViewManagerAdvertisingList.onFinishFreshAndLoad();
        mSimpleBindingAdapter.setupData(beanList);
    }

    /**
     * 在一个已经日期的基础上加上一个天数，得到相应的日期
     * @param startDay：已知的日期
     * @param addDays：需要加上的天数
     * @return
     */
    private String getEndData(String startDay, int addDays) {
        String endDay = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            // 得到开始的日期
            Date date = sdf.parse(startDay);
            Calendar cl = Calendar.getInstance();
            cl.setTime(date);
            // 开始的日期基础上加指定的天数
            cl.add(Calendar.DATE, addDays);
            // 将加完后的天数格式化成日期
            endDay = sdf.format(cl.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return endDay;
    }

    /**
     * 重写的RecyclerViewContract的方法，初始化RecyclerView
     */
    @Override
    public void initRecyclerView() {
        mDataBinding.springViewManagerAdvertisingList.setListener(this);
        mDataBinding.springViewManagerAdvertisingList.setHeader(new DefaultHeader(mContext));
        mDataBinding.springViewManagerAdvertisingList.setFooter(new DefaultFooter(mContext));

        mDataBinding.recyclerViewManagerAdvertisingList.addOnItemTouchListener(new ManagerAdvertisingListActivity.OnItemClickListener(mDataBinding.recyclerViewManagerAdvertisingList));
        mDataBinding.recyclerViewManagerAdvertisingList.setLayoutManager(new LinearLayoutManager(mContext));
        mDataBinding.recyclerViewManagerAdvertisingList.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        mDataBinding.recyclerViewManagerAdvertisingList.setRefrshView(mDataBinding.springViewManagerAdvertisingList);
        mDataBinding.recyclerViewManagerAdvertisingList.setEmptyView(mDataBinding.getRoot().findViewById(R.id.empty_view_manager_advertising_list));
        mSimpleBindingAdapter = new SimpleBindingAdapter<>(R.layout.item_layout_advertising_list, BR.listAdvertisingBean);
        mDataBinding.recyclerViewManagerAdvertisingList.setAdapter(mSimpleBindingAdapter);
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

    @Override
    protected ViewGroup getRefreshView() {
        return mDataBinding.springViewManagerAdvertisingList;
    }

    /**
     * 分页加载数据
     * @param page
     */
    @Override
    protected void loadListData(int page) {
        mPresenter.loadListData(page);
    }

    /**
     * 广告位已审核列表的Item的点击事件
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
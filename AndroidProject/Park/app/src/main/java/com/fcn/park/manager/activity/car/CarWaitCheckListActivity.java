package com.fcn.park.manager.activity.car;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.fcn.park.R;
import com.android.databinding.library.baseAdapters.BR;
import com.fcn.park.base.BaseActivity;
import com.fcn.park.base.adapter.SimpleBindingAdapter;
import com.fcn.park.base.recycler_listener.OnRecyclerItemClickListener;

import com.fcn.park.databinding.ManagerCarwaitchecklistBinding;
import com.fcn.park.manager.bean.car.CarWaitCheckBean;
import com.fcn.park.manager.contract.car.CarWaitCheckListContract;
import com.fcn.park.manager.presenter.car.CarWaitCheckListPresenter;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;

import java.util.List;

/**
 * 管理中心的月租车辆待审批功能用Activity.
 */
public class CarWaitCheckListActivity extends BaseActivity<ManagerCarwaitchecklistBinding, CarWaitCheckListContract.View, CarWaitCheckListPresenter>
        implements CarWaitCheckListContract.View, SpringView.OnFreshListener {

    private SimpleBindingAdapter<CarWaitCheckBean.CarWaitCheckListBean> mAdapter;
    private boolean isEnd;
    private boolean Flg=false;

    /**
     * 重写的方法，用来加载定义画面的Layout
     * @return
     */
    @Override
    protected int getLayoutId() {
        return R.layout.manager_carwaitchecklist;
    }

    /**
     * 重写的方法，用来设置标题栏要显示的文字
     */
    @Override
    protected void setupTitle() {
        setTitleText(getString(R.string.manager_monthly_vehicle_check_list));
        openTitleLeftView(true);
    }

    /**
     * 当前列表画面处于后台状态时，将isPause置True
     */
    @Override
    protected void onPause() {
        Flg = true;
        super.onPause();
    }

    /**
     * 从月租车辆审批详情画面返回后，重新请求当前列表画面的数据
     */
    @Override
    protected void onResume() {
       if (Flg) {
           mPresenter.loadListData();
       }
       super.onResume();
    }

    /**
     * 画面初始化时，调用此方法，向后台请求画面要显示的数据
     */
    @Override
    protected void initViews() {
        mPresenter.loadListData();
        mDataBinding.springViewManagerCarwaitchecklist.setListener(this);
        mDataBinding.springViewManagerCarwaitchecklist.setHeader(new DefaultHeader(this));
        mDataBinding.springViewManagerCarwaitchecklist.setFooter(new DefaultFooter(this));

        mDataBinding.recyclerViewManagerCarwaitchecklist.addOnItemTouchListener(new OnItemClickListener(getRecyclerView()));
        mDataBinding.recyclerViewManagerCarwaitchecklist.setLayoutManager(new LinearLayoutManager(mContext));
        mDataBinding.recyclerViewManagerCarwaitchecklist.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        mDataBinding.recyclerViewManagerCarwaitchecklist.setRefrshView(mDataBinding.springViewManagerCarwaitchecklist);
        mDataBinding.recyclerViewManagerCarwaitchecklist.setEmptyView(findViewById(R.id.car_wait_check_list_empty_view));
        mAdapter  = new SimpleBindingAdapter<>(R.layout.item_car_wait_check_list, BR.carWaitCheckListBean);
        mDataBinding.recyclerViewManagerCarwaitchecklist.setAdapter(mAdapter);
    }

    /**
     * 重写的方法，用来获取画面的id
     * @return
     */
    @Override
    public String getParkPay_id() {
        return null;
    }

    /**
     * 下拉刷新，回调接口
     */
    @Override
    public void onRefresh() {
        onRefresh(isEnd);
    }

    /**
     * 上拉加载，回调接口
     */
    @Override
    public void onLoadmore() {
        onLoadMore(isEnd);
    }

    /**
     * 绑定列表数据
     */
    @Override
    public void bindListData(List<CarWaitCheckBean.CarWaitCheckListBean> beanList) {
        setListData(beanList);
        mDataBinding.springViewManagerCarwaitchecklist.onFinishFreshAndLoad();
        mAdapter.setupData(beanList);
    }

    @Override
    public RecyclerView getRecyclerView() {
        return mDataBinding.recyclerViewManagerCarwaitchecklist;
    }

    @Override
    protected CarWaitCheckListPresenter createPresenter() {
        return  new CarWaitCheckListPresenter();
    }

     @Override
    public void initRecyclerView() {

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
     * 月租车辆审批列表的Item的点击事件
     */
    private class OnItemClickListener extends OnRecyclerItemClickListener {

        /**
         * 定义一个设置点击监听器的方法
         * @param vh
         */
        @Override
        public void onItemClick(RecyclerView.ViewHolder vh) {
            mPresenter.onItemClick(vh, vh.getLayoutPosition());
        }

        public OnItemClickListener(RecyclerView recyclerView) {
            super(recyclerView);
        }

    }

}

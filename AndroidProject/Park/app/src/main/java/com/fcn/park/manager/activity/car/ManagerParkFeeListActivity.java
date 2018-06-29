package com.fcn.park.manager.activity.car;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.databinding.library.baseAdapters.BR;
import com.fcn.park.R;
import com.fcn.park.base.BaseActivity;
import com.fcn.park.base.adapter.SimpleBindingAdapter;
import com.fcn.park.base.recycler_listener.OnRecyclerItemClickListener;
import com.fcn.park.databinding.ManagerParkfeelistBinding;
import com.fcn.park.manager.bean.car.ParkFeeBean;
import com.fcn.park.manager.bean.car.ParkFeeDetailInfoBean;
import com.fcn.park.manager.contract.car.ManagerParkFeeListContract;
import com.fcn.park.manager.presenter.car.ManagerParkFeeListPresenter;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;

import java.util.List;

/**
 * 管理中心的月租车辆审批功能用Activity.
 */
public class ManagerParkFeeListActivity extends BaseActivity<ManagerParkfeelistBinding, ManagerParkFeeListContract.View, ManagerParkFeeListPresenter>
        implements ManagerParkFeeListContract.View, SpringView.OnFreshListener {

    private SimpleBindingAdapter<ParkFeeBean.ParkFeeListBean> mAdapter;
    private boolean isEnd;


    @Override
    protected int getLayoutId() {
        return R.layout.manager_parkfeelist;
    }

    /**
     * 重写的方法，用来设置标题栏要显示的文字
     */
    @Override
    protected void setupTitle() {
        setTitleText(getString(R.string.manager_parking_fee_list));
        openTitleLeftView(true);
    }

    /**
     * 更新列表信息
     * @param bean
     */
    @Override
    public void updateInfo(ParkFeeDetailInfoBean bean) {

    }

    /**
     * 画面初始化时，调用此方法，向后台请求画面要显示的数据
     */
    @Override
    protected void initViews() {
        mPresenter.loadListData();
        mDataBinding.springViewManagerParkfeelist.setListener(this);
        mDataBinding.springViewManagerParkfeelist.setHeader(new DefaultHeader(this));
        mDataBinding.springViewManagerParkfeelist.setFooter(new DefaultFooter(this));

        mDataBinding.recyclerViewManagerParkfeelist.addOnItemTouchListener(new OnItemClickListener(getRecyclerView()));
        mDataBinding.recyclerViewManagerParkfeelist.setLayoutManager(new LinearLayoutManager(mContext));
        mDataBinding.recyclerViewManagerParkfeelist.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        mDataBinding.recyclerViewManagerParkfeelist.setRefrshView(mDataBinding.springViewManagerParkfeelist);
        mDataBinding.recyclerViewManagerParkfeelist.setEmptyView(findViewById(R.id.parking_fee_list_empty_view));
        mAdapter  = new SimpleBindingAdapter<>(R.layout.item_park_fee_list, BR.parkFeeListBean);
        mDataBinding.recyclerViewManagerParkfeelist.setAdapter(mAdapter);
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
     * 绑定数据列表
     * @param beanList
     */
    @Override
    public void bindListData(List<ParkFeeBean.ParkFeeListBean> beanList) {
        setListData(beanList);
        mDataBinding.springViewManagerParkfeelist.onFinishFreshAndLoad();
        mAdapter.setupData(beanList);
    }

    @Override
    public RecyclerView getRecyclerView() {
        return mDataBinding.recyclerViewManagerParkfeelist;
    }

    @Override
    protected ManagerParkFeeListPresenter createPresenter() {
        return  new ManagerParkFeeListPresenter();
    }

    /**
     * 画面初始化时，调用此方法，向后台请求画面要显示的数据
     */
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
     * 获取点击监听事件
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

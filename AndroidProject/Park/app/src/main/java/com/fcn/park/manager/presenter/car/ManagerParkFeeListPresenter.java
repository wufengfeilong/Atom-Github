package com.fcn.park.manager.presenter.car;

import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.fcn.park.base.BasePresenter;
import com.fcn.park.base.utils.OnDataGetCallback;
import com.fcn.park.manager.activity.car.ManagerParKFeeDetailActivity;
import com.fcn.park.manager.bean.car.CarWaitCheckBean;
import com.fcn.park.manager.bean.car.ParkFeeBean;
import com.fcn.park.manager.contract.car.CarWaitCheckListContract;
import com.fcn.park.manager.contract.car.ManagerParkFeeListContract;
import com.fcn.park.manager.module.car.CarWaitCheckListModule;
import com.fcn.park.manager.module.car.ManagerParkFeeListModule;

import java.util.List;

/**
 * 月租车辆待审批功能用Presenter
 */
public class ManagerParkFeeListPresenter extends BasePresenter<ManagerParkFeeListContract.View> implements ManagerParkFeeListContract.Presenter {

    private ManagerParkFeeListModule cModule;
    private String userId;

    @Override
    public void attach(ManagerParkFeeListContract.View view) {
        super.attach(view);
        cModule = new ManagerParkFeeListModule();
        createItemClickListener(getView().getRecyclerView());
    }

    /**
     * 加载停车付费列表数据
     */
    @Override
    public void loadListData() {
        getView().initRecyclerView();
        loadListData(1);
    }

    /**
     * 加载更多数据
     * @param page
     */
    @Override
    public void onLoadMore(int page) {
        loadListData(page);
    }

    /**
     * 加载数据列表
     * @param page
     */
    private void loadListData(int page) {
        cModule.getList(getView().getContext(),  new OnDataGetCallback<List<ParkFeeBean.ParkFeeListBean>>() {
            @Override
            public void onSuccessResult(List<ParkFeeBean.ParkFeeListBean> parkFeeListBean) {
                getView().updateIsEnd(cModule.isEnd());
                getView().bindListData(parkFeeListBean);
            }
        });
}

    /**
     * 在停车付费列表通过点击item条目跳转到详情页面
     * @param vh
     * @param position
     */
    @Override
    public void onItemClick(RecyclerView.ViewHolder vh, int position) {
        String ParkPay_id = cModule.getListData().get(position).getParkPay_id();
        ManagerParKFeeDetailActivity.actionStart(getView().getContext(), getView(), ParkPay_id);

    }
}
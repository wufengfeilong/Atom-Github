package com.fcn.park.manager.presenter.car;

import android.support.v7.widget.RecyclerView;
import com.fcn.park.base.BasePresenter;
import com.fcn.park.base.utils.OnDataGetCallback;
import com.fcn.park.manager.activity.car.ManagerCarWaitCheckDetailActivity;
import com.fcn.park.manager.bean.car.CarWaitCheckBean;
import com.fcn.park.manager.contract.car.CarWaitCheckListContract;
import com.fcn.park.manager.module.car.CarWaitCheckListModule;

import java.util.List;

/**
 * 管理中心的月租车辆待审批功能用Presenter
 */
public class CarWaitCheckListPresenter extends BasePresenter<CarWaitCheckListContract.View> implements CarWaitCheckListContract.Presenter {

    private CarWaitCheckListModule carWaitCheckListModule;

    @Override
    public void attach(CarWaitCheckListContract.View view) {
        super.attach(view);
        carWaitCheckListModule = new CarWaitCheckListModule();
        createItemClickListener(getView().getRecyclerView());
    }

    /**
     * 加载数据列表
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
        carWaitCheckListModule.getList(getView().getContext(),  new OnDataGetCallback<List<CarWaitCheckBean.CarWaitCheckListBean>>() {
            @Override
            public void onSuccessResult(List<CarWaitCheckBean.CarWaitCheckListBean> carWaitCheckListBean) {
                getView().updateIsEnd(carWaitCheckListModule.isEnd());
                getView().bindListData(carWaitCheckListBean);
            }
        });
    }

    /**
     * 在月租车辆待审批列表通过点击item条目跳转到详情页面
     * @param vh
     * @param position
     */
    @Override
    public void onItemClick(RecyclerView.ViewHolder vh, int position) {
        String ParkPay_id = carWaitCheckListModule.getListData().get(position).getParkPay_id();
        ManagerCarWaitCheckDetailActivity.actionStart(getView().getContext(), getView(), ParkPay_id);
    }
}
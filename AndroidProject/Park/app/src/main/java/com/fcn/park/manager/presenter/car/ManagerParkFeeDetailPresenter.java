package com.fcn.park.manager.presenter.car;

import com.fcn.park.base.BasePresenter;
import com.fcn.park.base.utils.OnDataGetCallback;

import com.fcn.park.manager.bean.car.ParkFeeDetailInfoBean;

import com.fcn.park.manager.contract.car.ManagerParkFeeDetailContract;

import com.fcn.park.manager.module.car.ManagerParkFeeDetailInfoModule;


/**
 * 新闻详情用Presenter.
 */

public class ManagerParkFeeDetailPresenter extends BasePresenter<ManagerParkFeeDetailContract.View> implements ManagerParkFeeDetailContract.Presenter {

    private ManagerParkFeeDetailInfoModule managerParkFeeDetailInfoModule;

    @Override
    public void attach(ManagerParkFeeDetailContract.View view) {
        super.attach(view);
        managerParkFeeDetailInfoModule = new ManagerParkFeeDetailInfoModule();
    }

    /**
     * 加载停车付费详情信息
     */
    @Override
    public void loadInfo() {
        managerParkFeeDetailInfoModule.requestParkFeeDetailInfo(getView().getContext(), getView().getParkPay_id(), new OnDataGetCallback<ParkFeeDetailInfoBean>() {

             @Override
            public void onSuccessResult(ParkFeeDetailInfoBean data) {
                getView().updateInfo(data);
            }
        });
    }

}

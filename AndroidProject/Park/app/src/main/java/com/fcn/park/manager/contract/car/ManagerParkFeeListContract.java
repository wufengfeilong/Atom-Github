package com.fcn.park.manager.contract.car;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.fcn.park.base.BaseView;
import com.fcn.park.base.contract.PullToRefeshContract;
import com.fcn.park.base.contract.RecyclerViewContract;
import com.fcn.park.manager.bean.car.CarWaitCheckBean;
import com.fcn.park.manager.bean.car.ParkFeeBean;
import com.fcn.park.manager.bean.car.ParkFeeDetailInfoBean;

import java.util.List;

/**
 * 月租车辆待审批功能用Contract.
 */
public interface ManagerParkFeeListContract {

    interface View extends BaseView, RecyclerViewContract.View<ParkFeeBean.ParkFeeListBean>, PullToRefeshContract.View, ManagerParkFeeDetailContract.View {

        //接口：获取停车付费车辆id
        String getParkPay_id();
    }

    interface Presenter extends RecyclerViewContract.Presenter, PullToRefeshContract.Presenter {
    }

}



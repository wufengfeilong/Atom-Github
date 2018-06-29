package com.fcn.park.manager.contract.car;

import com.fcn.park.base.BaseView;
import com.fcn.park.base.contract.PullToRefeshContract;
import com.fcn.park.base.contract.RecyclerViewContract;
import com.fcn.park.manager.bean.car.CarWaitCheckBean;

/**
 * 管理中心的月租车辆待审批功能用Contract.
 */
public interface CarWaitCheckListContract {

    interface View extends BaseView, RecyclerViewContract.View<CarWaitCheckBean.CarWaitCheckListBean>, PullToRefeshContract.View {
        //接口：获取车辆id
        String getParkPay_id();
    }

    interface Presenter extends RecyclerViewContract.Presenter, PullToRefeshContract.Presenter {
    }

}



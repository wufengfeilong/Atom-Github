package com.fcn.park.manager.contract.car;

import com.fcn.park.base.BaseView;
import com.fcn.park.manager.bean.car.CarWaitCheckDetailInfoBean;

/**
 * 管理中心的月租车辆待审批详情用Contract.
 */
public interface ManagerCarWaitCheckDetailContract {

    interface View extends BaseView {

        void updateInfo(CarWaitCheckDetailInfoBean bean);

        // 接口：获取待审批车辆id
        String getParkPay_id();
        // 接口：获取审批详情
        String getCheckInfo();
        // 接口：获取UserId
        String getUserId();
    }

    interface Presenter {
        // 接口：加载月租车辆审批详情信息
        void loadInfo();
        // 接口：月租车辆审核通过
        void onPassClick();
        // 接口：月租车辆审核驳回
        void onTurnClick();
    }
}

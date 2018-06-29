package com.fcn.park.manager.contract.car;

import com.fcn.park.base.BaseView;
import com.fcn.park.manager.bean.car.ParkFeeDetailInfoBean;

/**
 * 管理中心的新闻详情用Contract.
 */

public interface ManagerParkFeeDetailContract {

    interface View extends BaseView {

        void updateInfo(ParkFeeDetailInfoBean bean);
        //接口:获取车辆id
        String getParkPay_id();
    }

    interface Presenter {
        //接口：加载信息
        void loadInfo();
    }
}

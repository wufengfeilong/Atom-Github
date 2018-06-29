package com.fcn.park.info.contract;

import com.fcn.park.base.BaseView;
import com.fcn.park.info.bean.DemandDetailInfoBean;

/**
 * Created by liuyq on 2018/04/25.
 * 需求详情发布contract
 */

public interface ManagerDemandDetailContract {
    interface View extends BaseView {
        void bindData(DemandDetailInfoBean demandDetailInfoBean);

        String getDemandDetailId();
    }

    interface Presenter {
        void loadInfo();
    }
}

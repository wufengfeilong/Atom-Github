package com.fcn.park.manager.contract;

import com.fcn.park.base.BaseView;
import com.fcn.park.manager.bean.ManagerAdvertisingApprovalBean;

/**
 * 广告位详情已审批完了用Contract.
 */
public interface ManagerAdvertisingContract {

    interface View extends BaseView {
        // 将取得的数据显示到画面上
        void showDataToView(ManagerAdvertisingApprovalBean bean);

    }
    interface Presenter {

        // 通过广告Id，获取广告位的详情内容
        void getAdvertisingInfoById(String advertisingId);
    }
}
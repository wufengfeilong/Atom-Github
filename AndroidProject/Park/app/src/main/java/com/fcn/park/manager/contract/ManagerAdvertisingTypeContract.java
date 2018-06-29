package com.fcn.park.manager.contract;

import com.fcn.park.base.BaseView;

/**
 * 广告位待审核/已审核分类画面用Contract.
 */
public interface ManagerAdvertisingTypeContract {

    interface View extends BaseView {

    }

    interface Presenter {
        /**
         * 点击进入广告位待审核列表画面
         */
        void goAdvertisingUnApprovalList();

        /**
         * 点击进入广告位已审核列表画面
         */
        void goAdvertisingApprovaledList();
    }
}

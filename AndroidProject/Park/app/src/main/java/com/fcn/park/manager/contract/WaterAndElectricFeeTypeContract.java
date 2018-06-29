package com.fcn.park.manager.contract;

import com.fcn.park.base.BaseView;

/**
 * 水电费分支用Contract.
 */
public interface WaterAndElectricFeeTypeContract {

    interface View extends BaseView {

    }

    interface Presenter {
        /**
         * 点击进入水费列表画面
         */
        void goWaterFeeList();

        /**
         * 点击进入电费列表画面
         */
        void goElectricFeeList();
    }
}

package com.fcn.park.manager.contract;

import com.fcn.park.base.BaseView;

/**
 * Created by 860116042 on 2018/04/10.
 */

public interface ElectricFeeDetailContract {
    interface View extends BaseView {
        // 起始电量取得
        String getStartNum();
        // 截止电量取得
        String getEndNum();
        // 总电量取得
        String getCostNum();
        // 录表日期取得
        String getRecordDate();
        // 单价取得
        String getUnitPrice();
        // 电费取得
        String getElectricFee();
        // 电费ID取得
        String getElectricFeeId();
    }

    interface Presenter {
        // 编辑按钮的click事件
        void onClickSubmit();
    }
}

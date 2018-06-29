package com.fcn.park.manager.contract;

import com.fcn.park.base.BaseView;

/**
 * Created by 860116042 on 2018/04/10.
 */

public interface WaterFeeDetailContract {
    interface View extends BaseView {
        // 起始水量取得
        String getStartNum();
        // 截止水量取得
        String getEndNum();
        // 总水量取得
        String getCostNum();
        // 录表日期取得
        String getRecordDate();
        // 单价取得
        String getUnitPrice();
        // 水费取得
        String getWaterFee();
        // 水费ID取得
        String getWaterFeeId();
    }

    interface Presenter {
        // 编辑按钮的click事件
        void onClickSubmit();
    }
}

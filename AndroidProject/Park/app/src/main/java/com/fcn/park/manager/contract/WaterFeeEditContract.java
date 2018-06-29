package com.fcn.park.manager.contract;

import com.fcn.park.base.BaseView;

/**
 * Created by 860116042 on 2018/04/10.
 */

public interface WaterFeeEditContract {
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
        // 检查起始水量是否输入
        boolean checkStartNumEmpty();
        // 检查输入的起始水量是否为数值
        boolean checkStartNumNotNum();
        // 检查截止水量是否输入
        boolean checkEndNumEmpty();
        // 检查输入的截止水量是否为数值
        boolean checkEndNumNotNum();
        // 检查水费单价是否输入
        boolean checkUnitPriceEmpty();
        // 检查输入的水费单价是否为数值
        boolean checkUnitPriceNotNum();
        // 显示日期选择框
        void showDateSelectDialog(long currentTime);
        // 设置截止日期
        void setWaterFeeRecordDate(String recordDate);
    }

    interface Presenter {
        // 编辑按钮的点击事件
        void onClickSubmit();
        // 截止日期的点击事件
        void onRecordDateClick();
    }
}

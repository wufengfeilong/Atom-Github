package com.fcn.park.manager.contract;

import com.fcn.park.base.BaseView;

/**
 * Created by 860116042 on 2018/04/10.
 */

public interface RentFeeEditContract {
    interface View extends BaseView {
        // 建筑面积取得
        String getCompanySpace();
        // 物业单价取得
        String getRentFeeUnitPrice();
        // 开始日期取得
        String getRentFeeStartDate();
        // 截止日期取得
        String getRentFeeEndDate();
        // 物业折扣取得
        String getRentFeeDiscount();
        // 物业费扣取得
        String getRentFee();
        // 备注取得
        String getRentFeeComment();
        // 检查建筑面积是否输入
        boolean checkCompanySpaceEmpty();
        // 检查输入的建筑面积是否为数值
        boolean checkCompanySpaceNotNum();
        // 检查物业费单价是否输入
        boolean checkUnitPriceEmpty();
        // 检查输入的物业费单价是否为数值
        boolean checkUnitPriceNotNum();
        // 显示日期选择框
        void showDateSelectDialog(long currentTime);
        // 设置开始日期
        void setRentFeeStartDate(String startDate);
        // 设置截止日期
        void setRentFeeEndDate(String endDate);
    }

    interface Presenter {
        // 编辑按钮的点击事件
        void onClickSubmit();
        // 开始日期的点击事件
        void onStartDateClick();
        // 截止日期的点击事件
        void onEndDateClick();
    }
}

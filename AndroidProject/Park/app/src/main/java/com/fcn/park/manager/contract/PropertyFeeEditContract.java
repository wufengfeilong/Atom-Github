package com.fcn.park.manager.contract;

import com.fcn.park.base.BaseView;

/**
 * 物业费用管理用Contract.
 */
public interface PropertyFeeEditContract {
    interface View extends BaseView {

        // 物业费Id
        String getPropertyId();

        // 建筑面积取得
        String getCompanySpace();

        // 物业单价取得
        String getPropertyFeeUnitPrice();

        // 开始日期取得
        String getPropertyFeeStartDate();

        // 截止日期取得
        String getPropertyFeeEndDate();

        // 物业折扣取得
        String getPropertyFeeDiscount();

        // 物业费扣取得
        String getPropertyFee();

        // 备注取得
        String getPropertyFeeComment();

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
        void setPropertyFeeStartDate(String startDate);

        // 设置截止日期
        void setPropertyFeeEndDate(String endDate);
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

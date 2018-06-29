package com.fcn.park.manager.contract;

import com.fcn.park.base.BaseView;

/**
 * 物业费用管理用Contract.
 */
public interface PropertyFeeDetailContract {
    interface View extends BaseView {
        // 建筑面积取得
        String getCompanySpace();
        // 物业单价取得
        String getPropertyFeeUnitPrice();
        // 开始年月取得
        String getPropertyFeeStartDate();
        // 截止年月取得
        String getPropertyFeeEndDate();
        // 物业折扣取得
        String getPropertyFeeDiscount();
        // 物业费扣取得
        String getPropertyFee();
        // 备注取得
        String getPropertyFeeComment();
        // 物业费ID取得
        String getPropertyFeeId();
    }

    interface Presenter {
        // 编辑按钮的click事件
        void onClickEdit();
    }
}

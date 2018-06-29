package com.fcn.park.manager.contract;

import com.fcn.park.base.BaseView;

/**
 * Created by 860116042 on 2018/04/10.
 */

public interface RentFeeDetailContract {
    interface View extends BaseView {
        // 建筑面积取得
        String getCompanySpace();
        // 物业单价取得
        String getRentFeeUnitPrice();
        // 开始年月取得
        String getRentFeeStartDate();
        // 截止年月取得
        String getRentFeeEndDate();
        // 租赁费折扣取得
        String getRentFeeDiscount();
        // 租赁费取得
        String getRentFee();
        // 备注取得
        String getRentFeeComment();
        // 租赁费ID取得
        String getRentFeeId();
    }

    interface Presenter {
        // 编辑按钮的click事件
        void onClickSubmit();
    }
}

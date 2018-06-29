package com.fcn.park.property.contract;


import com.fcn.park.base.BaseView;

/**
 * Created by 860617003 on 2017/5/19.
 */

public interface PropertyPayStatusContract {

    interface View extends BaseView {

    }

    interface Presenter {
        /**
         * 支付状态成功时，完成按钮点击事件
         */
        void onClickComplete();

        /**
         * 支付状态成功时，需要发票点击事件
         */
        void onNeedInvoiceClick();

        /**
         * 支付状态失败时，确定按钮点击事件
         */
        void onClickConfirm();
    }


}

package com.fcn.park.manager.contract;

import com.fcn.park.base.BaseView;
import com.fcn.park.base.contract.PullToRefeshContract;
import com.fcn.park.base.contract.RecyclerViewContract;
import com.fcn.park.manager.bean.RentFeeListBean;

/**
 * 租赁费列表用Contract.
 */

public interface RentFeeListContract {
    interface View extends BaseView, RecyclerViewContract.View<RentFeeListBean.ListRentBean>, PullToRefeshContract.View {
        /**
         * 获取点击条目的位置
         * @return
         */
        int getClickPosition();
    }

    interface Presenter extends RecyclerViewContract.Presenter, PullToRefeshContract.Presenter {
        /**
         * 租赁费条目点击事件
         */
        void onItemClick();

        /**
         * 催缴费按钮点击事件
         */
        void onMailSendClick();
    }
}

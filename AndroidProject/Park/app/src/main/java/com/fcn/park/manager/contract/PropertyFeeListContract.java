package com.fcn.park.manager.contract;

import android.support.v7.widget.RecyclerView;

import com.fcn.park.base.BaseView;
import com.fcn.park.base.contract.PullToRefeshContract;
import com.fcn.park.base.contract.RecyclerViewContract;
import com.fcn.park.manager.bean.PropertyFeeListBean;

/**
 * 物业费列表用Contract.
 */

public interface PropertyFeeListContract {
    interface View extends BaseView, RecyclerViewContract.View<PropertyFeeListBean.ListPropertyBean>, PullToRefeshContract.View {
        /**
         * 获取点击条目的位置
         * @return
         */
        int getClickPosition();
    }

    interface Presenter extends RecyclerViewContract.Presenter, PullToRefeshContract.Presenter {
        /**
         * 物业费条目点击事件
         */
        void onItemClick();

        /**
         * 催缴费按钮点击事件
         */
        void onMailSendClick();
    }
}

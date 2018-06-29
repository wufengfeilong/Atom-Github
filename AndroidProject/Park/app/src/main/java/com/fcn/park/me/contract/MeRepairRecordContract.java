package com.fcn.park.me.contract;

import com.fcn.park.base.BaseView;
import com.fcn.park.base.contract.PullToRefeshContract;
import com.fcn.park.base.contract.RecyclerViewContract;
import com.fcn.park.me.bean.RepairRecordBean;
/**
 * 类描述：报修列表用Contract.
 */

public interface MeRepairRecordContract {

    interface View extends BaseView, RecyclerViewContract.View<RepairRecordBean.ListRecordBean>, PullToRefeshContract.View {
        int getClickPosition();
    }

    interface Presenter extends RecyclerViewContract.Presenter, PullToRefeshContract.Presenter {
        /**
         * 条目的长按事件
         */
        void onItemLongClick();
    }

}

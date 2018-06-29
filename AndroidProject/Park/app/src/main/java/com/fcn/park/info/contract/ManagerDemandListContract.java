package com.fcn.park.info.contract;

import com.fcn.park.base.BaseView;
import com.fcn.park.base.contract.PullToRefeshContract;
import com.fcn.park.base.contract.RecyclerViewContract;
import com.fcn.park.info.bean.ManagerDemandListBean;

/**
 * Created by liuyq on 2018/04/23.
 * 管理企业需求企业需求列表页面
 */

public interface ManagerDemandListContract {
    interface View extends BaseView, RecyclerViewContract.View<ManagerDemandListBean.DemandListBean>, PullToRefeshContract.View {
        /**
         *  点击item
         * @return
         */
        int getClickPosition();

        /**
         * 删除列表的item
         * @param position
         */
        void deleteListItem(int position);
    }

    interface Presenter extends RecyclerViewContract.Presenter, PullToRefeshContract.Presenter {
        /**
         * 条目的长按事件
         */
        void onItemLongClick();
    }

}

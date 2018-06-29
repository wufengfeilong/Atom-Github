package com.fcn.park.manager.contract;

import com.fcn.park.base.BaseView;
import com.fcn.park.base.contract.PullToRefeshContract;
import com.fcn.park.base.contract.RecyclerViewContract;
import com.fcn.park.manager.bean.ManagerPostNewsListBean;

/**
 * 管理中心的发布新闻用Contract.
 */
public interface ManagerPostNewsContract {

    interface View extends BaseView, RecyclerViewContract.View<ManagerPostNewsListBean.ListNewsBean>, PullToRefeshContract.View {

        String getNewsType();
        int getClickPosition();
        void deleteListItem(int position);
    }

    interface Presenter extends RecyclerViewContract.Presenter, PullToRefeshContract.Presenter {
        /**
         * 条目的长按事件
         */
        void onItemLongClick();
    }
}

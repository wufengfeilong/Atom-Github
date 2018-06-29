package com.fcn.park.rent.contract;

import com.fcn.park.base.BaseView;
import com.fcn.park.base.contract.PullToRefeshContract;
import com.fcn.park.base.contract.RecyclerViewContract;
import com.fcn.park.rent.bean.RentHouseListBean;

/**
 * 管理中心的发布新闻用Contract.
 */
public interface RentHouseListContract {

    interface View extends BaseView, RecyclerViewContract.View<RentHouseListBean.ListHouseBean>, PullToRefeshContract.View {

        /**
         * 获取画面来源
         * @return
         */
        String getFromId();

    }

    interface Presenter extends RecyclerViewContract.Presenter, PullToRefeshContract.Presenter {

        /**
         * 房屋添加页面跳转
         */
        void houseAdd();
    }
}

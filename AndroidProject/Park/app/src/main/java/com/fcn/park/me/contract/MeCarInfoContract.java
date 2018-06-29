package com.fcn.park.me.contract;

import com.fcn.park.base.BaseView;
import com.fcn.park.base.contract.PullToRefeshContract;
import com.fcn.park.base.contract.RecyclerViewContract;
import com.fcn.park.me.bean.MeCarInfoBean;
import com.fcn.park.property.bean.PropertyMoveHouseBean;

/**
 * Created by 860117073 on 2018/4/25.
 */

public interface MeCarInfoContract {
    interface View extends BaseView, RecyclerViewContract.View<MeCarInfoBean.CarInfoBean>,PullToRefeshContract.View{
        int getClickPosition();
        void deleteListItem(int position);
    }
    interface Presenter extends RecyclerViewContract.Presenter{
        /**
         * 条目的长按事件
         */
        void onItemLongClick();
    }

}

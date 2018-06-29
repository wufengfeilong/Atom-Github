package com.fcn.park.rent.contract;

import com.fcn.park.base.BaseView;
import com.fcn.park.base.contract.PullToRefeshContract;
import com.fcn.park.base.contract.RecyclerViewContract;
import com.fcn.park.rent.bean.RentReleasedHouseListBean;

/**
 * Created by 860117066 on 2018/04/25.
 */

public interface RentReleasedHouseListContract {
    interface View extends BaseView, RecyclerViewContract.View<RentReleasedHouseListBean.ListReleasedHouseBean>, PullToRefeshContract.View {
        int getClickPosition();
    }

    interface Presenter extends RecyclerViewContract.Presenter, PullToRefeshContract.Presenter {
    }
}

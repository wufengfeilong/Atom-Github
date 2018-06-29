package com.fcn.park.property.contract;

import com.fcn.park.base.BaseView;
import com.fcn.park.base.contract.PullToRefeshContract;
import com.fcn.park.base.contract.RecyclerViewContract;
import com.fcn.park.property.bean.PropertyMoveHouseBean;

/**
 * Created by 860117073 on 2018/4/18.
 */

public interface PropertyMoveHouseContract {
    interface View extends BaseView, RecyclerViewContract.View<PropertyMoveHouseBean.MoveHouseBean>, PullToRefeshContract.View{

    }
    interface Presenter extends RecyclerViewContract.Presenter,PullToRefeshContract.Presenter{

    }
}

package com.fcn.park.property.contract;

import com.fcn.park.base.BaseView;
import com.fcn.park.base.contract.PullToRefeshContract;
import com.fcn.park.base.contract.RecyclerViewContract;
import com.fcn.park.property.bean.PropertyCleanBean;

/**
 * Created by 860117073 on 2018/4/19.
 */

public interface PropertyCleanContract {

    interface View extends BaseView, RecyclerViewContract.View<PropertyCleanBean.CleanBean>, PullToRefeshContract.View {

    }
    interface Presenter extends RecyclerViewContract.Presenter,PullToRefeshContract.Presenter{

    }
}

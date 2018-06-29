package com.fcn.park.property.contract;

import com.fcn.park.base.BaseView;
import com.fcn.park.base.contract.PullToRefeshContract;
import com.fcn.park.base.contract.RecyclerViewContract;
import com.fcn.park.property.bean.PropertyPlantLeaseBean;

/**
 * Created by 860117073 on 2018/4/24.
 */

public interface PropertyPlantLeaseContract {
    interface View extends BaseView, RecyclerViewContract.View<PropertyPlantLeaseBean.PlantLeaseBean>, PullToRefeshContract.View{

    }
    interface Presenter extends RecyclerViewContract.Presenter,PullToRefeshContract.Presenter{
    }
}

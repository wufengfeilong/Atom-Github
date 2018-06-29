package com.fcn.park.home.advertisement.contract;

import com.fcn.park.base.BaseView;
import com.fcn.park.base.contract.PullToRefeshContract;
import com.fcn.park.base.contract.RecyclerViewContract;
import com.fcn.park.manager.bean.ManagerAdvertisingApprovalListBean;

/**
 * Created by 860115001 on 2018/04/24.
 */

public interface AdvertisementListContract {

    interface View extends BaseView, RecyclerViewContract.View<ManagerAdvertisingApprovalListBean.ListAdvertisingApprovalBean>, PullToRefeshContract.View {
        /**
         * 获取点击位置
         * @return
         */
        int getClickPosition();
    }

    interface Presenter extends RecyclerViewContract.Presenter{

    }
}

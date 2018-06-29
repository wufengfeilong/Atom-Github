package com.fcn.park.manager.contract;

import com.fcn.park.base.BaseView;
import com.fcn.park.base.contract.PullToRefeshContract;
import com.fcn.park.base.contract.RecyclerViewContract;
import com.fcn.park.manager.bean.ManagerAdvertisingApprovalListBean;

/**
 * 广告位已审核列表用Contract.
 */
public interface ManagerAdvertisingListContract {

    interface View extends BaseView, RecyclerViewContract.View<ManagerAdvertisingApprovalListBean.ListAdvertisingApprovalBean>, PullToRefeshContract.View {
        int getClickPosition();
    }

    interface Presenter extends RecyclerViewContract.Presenter {
    }
}

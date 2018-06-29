package com.fcn.park.manager.contract;

import com.fcn.park.base.BaseView;
import com.fcn.park.base.contract.PullToRefeshContract;
import com.fcn.park.base.contract.RecyclerViewContract;
import com.fcn.park.manager.bean.ManagerAdvertisingApprovalListBean;

/**
 * 广告位待审核列表用Contract.
 */
public interface ManagerAdvertisingApprovalListContract {

    interface View extends BaseView, RecyclerViewContract.View<ManagerAdvertisingApprovalListBean.ListAdvertisingApprovalBean>, PullToRefeshContract.View {
        // 获取点击列表中项目的position值
        int getClickPosition();
    }

    interface Presenter extends RecyclerViewContract.Presenter, PullToRefeshContract.Presenter {
    }
}

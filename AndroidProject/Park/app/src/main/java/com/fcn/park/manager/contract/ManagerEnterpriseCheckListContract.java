package com.fcn.park.manager.contract;

import com.fcn.park.base.BaseView;
import com.fcn.park.base.contract.PullToRefeshContract;
import com.fcn.park.base.contract.RecyclerViewContract;
import com.fcn.park.manager.bean.ManagerEnterpriseCheckBean;

/**
 * 管理中心的企业审核管理功能用Contract.
 */
public interface ManagerEnterpriseCheckListContract {

    interface View extends BaseView, RecyclerViewContract.View<ManagerEnterpriseCheckBean.EnterpriseCheckListBean>, PullToRefeshContract.View {
    }

    interface Presenter extends RecyclerViewContract.Presenter, PullToRefeshContract.Presenter {
    }

}



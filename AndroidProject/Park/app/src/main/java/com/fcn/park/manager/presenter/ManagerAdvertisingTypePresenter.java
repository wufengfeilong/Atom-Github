package com.fcn.park.manager.presenter;

import com.fcn.park.base.BasePresenter;
import com.fcn.park.manager.activity.ManagerAdvertisingApprovalListActivity;
import com.fcn.park.manager.activity.ManagerAdvertisingListActivity;
import com.fcn.park.manager.contract.ManagerAdvertisingTypeContract;

/**
 * 广告位待审核/已审核分类画面用Presenter.
 */
public class ManagerAdvertisingTypePresenter
        extends BasePresenter<ManagerAdvertisingTypeContract.View>
        implements ManagerAdvertisingTypeContract.Presenter {

    @Override
    public void goAdvertisingUnApprovalList() {// 广告位待审核
        getView().actionStartActivity(ManagerAdvertisingApprovalListActivity.class);
    }

    @Override
    public void goAdvertisingApprovaledList() {// 广告位已审核
        getView().actionStartActivity(ManagerAdvertisingListActivity.class);
    }
}

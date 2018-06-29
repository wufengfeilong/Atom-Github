package com.fcn.park.manager.presenter;

import android.util.Log;

import com.fcn.park.base.BasePresenter;
import com.fcn.park.base.utils.OnDataGetCallback;
import com.fcn.park.manager.bean.ManagerAdvertisingApprovalBean;
import com.fcn.park.manager.contract.ManagerAdvertisingContract;
import com.fcn.park.manager.module.ManagerAdvertisingListModule;

/**
 * 审批完了的广告位详情用Presenter.
 */
public class ManagerAdvertisingPresenter
        extends BasePresenter<ManagerAdvertisingContract.View>
        implements ManagerAdvertisingContract.Presenter  {

    private String TAG = "=== ManagerAdvertisingPresenter ===";

    private ManagerAdvertisingListModule mManagerAdvertisingModule;

    @Override
    public void attach(ManagerAdvertisingContract.View view) {
        super.attach(view);
        mManagerAdvertisingModule = new ManagerAdvertisingListModule();
    }

    /**
     * 通过广告Id，获取广告的详情内容
     * @param advertisingId
     */
    @Override
    public void getAdvertisingInfoById(String advertisingId) {
        Log.d(TAG, "==== 通过广告Id，获取广告的详情内容 ====");
        mManagerAdvertisingModule.getAdvertisingInfo(getView().getContext(), advertisingId, new OnDataGetCallback<ManagerAdvertisingApprovalBean>() {
            @Override
            public void onSuccessResult(ManagerAdvertisingApprovalBean data) {
                getView().showDataToView(data);
            }
        });
    }
}
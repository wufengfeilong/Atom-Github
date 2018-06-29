package com.fcn.park.info.presenter;

import com.fcn.park.base.BasePresenter;
import com.fcn.park.base.utils.OnDataGetCallback;
import com.fcn.park.info.bean.DemandDetailInfoBean;
import com.fcn.park.info.contract.ManagerDemandDetailContract;
import com.fcn.park.info.module.ManagerDemandDetailModule;

/**
 * Created by liuyq on 2018/04/25.
 * 企业需求发布详情Presenter
 */

public class ManagerDemandDetailPresenter extends BasePresenter<ManagerDemandDetailContract.View> implements ManagerDemandDetailContract.Presenter {
    private ManagerDemandDetailModule managerDemandDetailMoudle;

    @Override
    public void attach(ManagerDemandDetailContract.View view) {
        super.attach(view);
        managerDemandDetailMoudle = new ManagerDemandDetailModule();
    }

    /**
     * 加载数据使用
     */
    @Override
    public void loadInfo() {
        managerDemandDetailMoudle.requestManagerDemandDetail(getView().getContext(), getView().getDemandDetailId(), new OnDataGetCallback<DemandDetailInfoBean>() {
            @Override
            public void onSuccessResult(DemandDetailInfoBean data) {
                getView().bindData(data);
            }
        });

    }
}

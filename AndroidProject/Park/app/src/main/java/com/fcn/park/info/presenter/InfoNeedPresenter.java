package com.fcn.park.info.presenter;

import com.fcn.park.base.BasePresenter;
import com.fcn.park.base.utils.OnDataGetCallback;
import com.fcn.park.info.bean.NeedInfoBean;
import com.fcn.park.info.contract.InfoDemandDetailContract;
import com.fcn.park.info.module.InfoNeedModule;

/**
 * Created by liuyq on 2018/04/20.
 * 园区动态中企业需求详情页面使用module
 */

public class InfoNeedPresenter extends BasePresenter<InfoDemandDetailContract.View> implements InfoDemandDetailContract.Presenter {

    private InfoNeedModule mModule;

    @Override
    public void attach(InfoDemandDetailContract.View view) {
        super.attach(view);
        mModule = new InfoNeedModule();
    }

    /**
     * 加载数据
     */
    @Override
    public void loadInfo() {
        mModule.requestNeedInfo(getView().getContext(), getView().getNeedId(), new OnDataGetCallback<NeedInfoBean>() {
            @Override
            public void onSuccessResult(NeedInfoBean data) {
                getView().bindData(data);
            }
        });

    }
}

package com.fcn.park.manager.presenter;

import com.fcn.park.base.BasePresenter;
import com.fcn.park.base.utils.OnDataGetCallback;
import com.fcn.park.manager.bean.DetailInfoBean;
import com.fcn.park.manager.contract.ManagerPostNewsDetailContract;
import com.fcn.park.manager.module.ManagerPostNewsDetailInfoModule;

/**
 * 新闻、公告、活动详情用Presenter.
 */
public class ManagerPostNewsDetailPresenter
        extends BasePresenter<ManagerPostNewsDetailContract.View>
        implements ManagerPostNewsDetailContract.Presenter {

    private ManagerPostNewsDetailInfoModule mManagerPostNewsDetailInfoModule;

    @Override
    public void attach(ManagerPostNewsDetailContract.View view) {
        super.attach(view);
        mManagerPostNewsDetailInfoModule = new ManagerPostNewsDetailInfoModule();
    }

    @Override
    public void loadInfo() {
        mManagerPostNewsDetailInfoModule.requestPostNewsDetailInfo(getView().getContext(), getView().getId(), new OnDataGetCallback<DetailInfoBean>() {
            @Override
            public void onSuccessResult(DetailInfoBean data) {
                getView().updateInfo(data);
            }
        });
    }
}

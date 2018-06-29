package com.fcn.park.info.presenter;

import com.fcn.park.base.BasePresenter;
import com.fcn.park.base.utils.OnDataGetCallback;
import com.fcn.park.info.bean.InfoNewsBean;
import com.fcn.park.info.contract.InfoNewsDetailContract;
import com.fcn.park.info.module.InfoNewsDetailModule;

/**
 * Created by liuyq on 2018/04/12.
 * 园区动态中公告/新闻/活动详情使用presenter
 */

public class InfoNewsDetailPresenter extends BasePresenter<InfoNewsDetailContract.View> implements InfoNewsDetailContract.Presenter {

    private InfoNewsDetailModule mModule;

    @Override
    public void attach(InfoNewsDetailContract.View view) {
        super.attach(view);
        mModule = new InfoNewsDetailModule();
    }

    /**
     * 加载数据
     */
    @Override
    public void loadInfo() {
        mModule.requestNewsInfo(getView().getContext(), getView().getId(), new OnDataGetCallback<InfoNewsBean>() {
            @Override
            public void onSuccessResult(InfoNewsBean bean) {
                getView().updateInfo(bean);
            }
        });

    }
}

package com.fcn.park.login.presenter;

import com.fcn.park.base.BasePresenter;
import com.fcn.park.base.interfacee.OnDataCallback;
import com.fcn.park.login.bean.RegisterClauseBean;
import com.fcn.park.login.contract.RegisterClauseContract;
import com.fcn.park.login.module.RegisterClauseModule;

/**
 * Created by 860115001 on 2018/04/10.
 */

public class RegisterClausePresenter  extends BasePresenter<RegisterClauseContract.View> implements RegisterClauseContract.Presenter {

    private RegisterClauseModule mModule;

    @Override
    public void attach(RegisterClauseContract.View view) {
        super.attach(view);
        mModule = new RegisterClauseModule();
    }

    @Override
    public void loadPageData() {
        getView().showLoadingProgress();
        mModule.requestRegisterClause(getView().getContext(), new OnDataCallback<RegisterClauseBean>() {
            @Override
            public void onSuccessResult(RegisterClauseBean data) {
                getView().bindWebData(data);
            }

            @Override
            public void onError(String errMsg) {
                getView().showToast(errMsg);
                getView().hintLoadingProgress();
            }
        });
    }
}

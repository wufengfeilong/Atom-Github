package sdwxwx.com.login.presenter;

import sdwxwx.com.base.BaseCallback;
import sdwxwx.com.base.BasePresenter;
import sdwxwx.com.login.bean.UserAgreementBean;
import sdwxwx.com.login.contract.UserAgreementContract;
import sdwxwx.com.login.model.UserAgreementModel;

/**
 * Created by 丁胜胜 on 2018/05/14.
 */

public class UserAgreementPresenter extends BasePresenter<UserAgreementContract.View>
                implements UserAgreementContract.Presenter{

    UserAgreementModel mModel;

    @Override
    public void loadPageData() {
        getView().showLoadingProgress();
        mModel.requestRegisterClause(new BaseCallback<String>() {
            @Override
            public void onSuccess(String data) {
                UserAgreementBean bean = new UserAgreementBean();
                bean.setContent(data);
                if (getView() == null) {
                    return;
                }
                getView().bindWebData(bean);
            }
            @Override
            public void onFail(String msg) {
                if (getView() == null) {
                    return;
                }
                getView().showToast(msg);
            }
        });
    }

    @Override
    public void onCross() {
        getView().closeActivity();
    }
}

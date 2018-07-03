package sdwxwx.com.me.presenter;


import sdwxwx.com.base.BasePresenter;
import sdwxwx.com.login.activity.UserAgreementActivity;
import sdwxwx.com.me.contract.NameCertifyContract;

/**
 * Created by 860117073 on 2018/5/9.
 */

public class NameCertifyPresenter extends BasePresenter<NameCertifyContract.View> implements NameCertifyContract.Presenter {
    @Override
    public void onClickBack(){
        getView().closeActivity();
    }

    @Override
    public void onClickHandPhoto() {
        getView().selectPhoto(1);
    }

    @Override
    public void onClickCertyPhoto() {
        getView().selectPhoto(2);
    }

    /**
     * 进行实名认证
     */
    @Override
    public void onClickSave() {
        getView().certificate();
    }

    @Override
    public void onClickGoUserAgreement() {//用户协议
        getView().actionStartActivity(UserAgreementActivity.class);
    }
}

package woxingwoxiu.com.me.presenter;


import woxingwoxiu.com.base.BasePresenter;
import woxingwoxiu.com.me.activity.NameCertifyActivity;
import woxingwoxiu.com.me.activity.SettingActivity;
import woxingwoxiu.com.me.contract.CertificationInfoContract;

/**
 * Created by 860117073 on 2018/5/9.
 */

public class CertificationInfoPresenter extends BasePresenter<CertificationInfoContract.View> implements CertificationInfoContract.Presenter {
    @Override
    public void onClickNameCertify(){
        NameCertifyActivity.actionStart(getView().getContext());
    }

    @Override
    public void onClickBack(){
        getView().closeActivity();
    }
}

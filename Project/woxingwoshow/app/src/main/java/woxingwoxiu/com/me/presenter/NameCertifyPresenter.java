package woxingwoxiu.com.me.presenter;


import woxingwoxiu.com.base.BasePresenter;
import woxingwoxiu.com.me.activity.CertificationInfoActivity;
import woxingwoxiu.com.me.contract.NameCertifyContract;

/**
 * Created by 860117073 on 2018/5/9.
 */

public class NameCertifyPresenter extends BasePresenter<NameCertifyContract.View> implements NameCertifyContract.Presenter {
    @Override
    public void onClickBack(){
        getView().closeActivity();
    }
}

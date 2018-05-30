package woxingwoxiu.com.me.presenter;


import woxingwoxiu.com.base.BasePresenter;
import woxingwoxiu.com.me.activity.CertificationInfoActivity;
import woxingwoxiu.com.me.contract.MeEditContract;

/**
 * Created by 860116042 on 2018/5/17.
 */

public class MeEditPresenter extends BasePresenter<MeEditContract.View> implements MeEditContract.Presenter {
    @Override
    public void onClickSave() {

    }

    @Override
    public void onClickBack() {
        getView().closeActivity();
    }

    @Override
    public void onClickImage() {
        getView().showChoosePicDialog();
    }

    // 点击选择性别
    @Override
    public void onClickSelectSex() {
        getView().showSexChooseDialog();
    }

    // 点击选择生日
    @Override
    public void onClickSelectBirth() {
        getView().showDateDialog();
    }


}

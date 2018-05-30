package woxingwoxiu.com.login.presenter;

import woxingwoxiu.com.base.BasePresenter;
import woxingwoxiu.com.home.HomeActivity;
import woxingwoxiu.com.login.contract.DataEditingContract;
import woxingwoxiu.com.login.utils.ActivityCollector;

/**
 * Created by 丁胜胜 on 2018/05/14.
 */

public class DataEditingPresenter extends BasePresenter<DataEditingContract.View> implements DataEditingContract.Presenter {


    @Override
    public void onCross() {
        ActivityCollector.finishAll();
    }

    @Override
    public void onJump() {
        // 保存登录信息
        getView().savaObject();
        // 跳过头像编辑画面，跳转到HOME画面
        getView().actionStartActivity(HomeActivity.class);

    }

    @Override
    public void onClickOk() {
        // 保存登录信息
        getView().savaObject();
        getView().actionStartActivity(HomeActivity.class);
    }

    @Override
    public void onClickImage() {
        getView().showChoosePicDialog();
    }
}

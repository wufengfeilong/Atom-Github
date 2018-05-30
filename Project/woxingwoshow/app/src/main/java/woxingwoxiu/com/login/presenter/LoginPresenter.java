package woxingwoxiu.com.login.presenter;


import woxingwoxiu.com.base.BasePresenter;
import woxingwoxiu.com.login.activity.LoginPhoneActivity;
import woxingwoxiu.com.login.contract.LoginContract;
import woxingwoxiu.com.login.utils.ActivityCollector;

/**
 * Created by 丁胜胜 on 2018/05/09.
 */

public class LoginPresenter extends BasePresenter<LoginContract.View> implements LoginContract.Presenter{

    private String TAG = "== LoginPresenter ==";


    @Override
    public void onCross() {//关闭当前画面
        ActivityCollector.finishAll();
    }

    @Override
    public void onProblem() {

    }


    @Override
    public void onLoginQQ() {//跳转到QQ登录
        getView().showToast("跳转到QQ登录");
        getView().actionStartActivity(LoginPhoneActivity.class);
    }

    @Override
    public void onLoginWeixin() {//跳转到微信登录
        getView().showToast("跳转到微信登录");
        getView().actionStartActivity(LoginPhoneActivity.class);

    }

    @Override
    public void onLoginTel() {//跳转到手机号登录

        getView().showToast("跳转到手机号登录");
        getView().actionStartActivity(LoginPhoneActivity.class);

    }
}

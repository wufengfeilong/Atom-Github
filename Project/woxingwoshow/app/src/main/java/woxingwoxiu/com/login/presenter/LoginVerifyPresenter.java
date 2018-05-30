package woxingwoxiu.com.login.presenter;

import cn.emay.sdk.util.exception.SDKParamsException;
import woxingwoxiu.com.base.BasePresenter;
import woxingwoxiu.com.login.activity.DataEditingActivity;
import woxingwoxiu.com.login.activity.UserAgreementActivity;
import woxingwoxiu.com.login.contract.LoginVerifyContract;
import woxingwoxiu.com.login.utils.ActivityCollector;


/**
 * Created by 丁胜胜 on 2018/05/11.
 */

public class LoginVerifyPresenter extends BasePresenter<LoginVerifyContract.View> implements LoginVerifyContract.Presenter {

    @Override
    public void onCross() {//关闭画面
        ActivityCollector.finishAll();
    }

    @Override
    public void onNext() {//点击下一步

//        String verify = getView().getInputVerify();
//        String verifyCode = getView().getVerify();
//        if (!verify.equals(verifyCode)) {
//            getView().showToast("验证码不正确");
//            return;
//
//        }else {
//            getView().actionStartActivity(DataEditingActivity.class);
//        }

        getView().actionStartActivity(DataEditingActivity.class);
    }

    @Override
    public void onClickGoUserAgreement() {//用户协议
        getView().actionStartActivity(UserAgreementActivity.class);

    }

    @Override
    public void onClickGetVerify(){//获取验证码
        getView().startCountDownTimer();
        try {
           getView().sendSingleSms();
        } catch (SDKParamsException e) {
            e.printStackTrace();
        }

    }
}

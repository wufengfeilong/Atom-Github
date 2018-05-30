package woxingwoxiu.com.login.presenter;

import woxingwoxiu.com.base.BasePresenter;
import woxingwoxiu.com.login.activity.LoginVerifyActivity;
import woxingwoxiu.com.login.bean.LoginVerifyBean;
import woxingwoxiu.com.login.contract.LoginPhoneContract;
import woxingwoxiu.com.login.utils.ActivityCollector;
import woxingwoxiu.com.me.activity.FeedbackActivity;


/**
 * Created by 丁胜胜 on 2018/05/09.
 */

public class LoginPhonePresenter extends BasePresenter<LoginPhoneContract.View> implements LoginPhoneContract.Presenter {


    @Override
    public void onCross() {//关闭当前画面
        ActivityCollector.finishAll();
    }

    @Override
    public void onProblem() {
        getView().actionStartActivity(FeedbackActivity.class);
    }

    @Override
    public void onNext() {

        LoginVerifyBean.VerifyBean bean = new LoginVerifyBean.VerifyBean();

        bean.setPhoneNumSpace(getView().getInputPhoneNumSpace());

        bean.setPhoneNum(getView().getInputPhoneNum());

        LoginVerifyActivity.actionStart(getView().getContext(), bean);

    }


}

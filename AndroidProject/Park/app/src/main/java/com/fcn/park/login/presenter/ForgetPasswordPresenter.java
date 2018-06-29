package com.fcn.park.login.presenter;

import android.text.TextUtils;

import com.fcn.park.R;
import com.fcn.park.base.BasePresenter;
import com.fcn.park.base.http.HttpResult;
import com.fcn.park.base.http.ProgressSubscriber;
import com.fcn.park.base.http.RequestImpl;
import com.fcn.park.base.http.RequestModule;
import com.fcn.park.base.utils.RECheckUtils;
import com.fcn.park.login.bean.CodeBean;
import com.fcn.park.login.contract.ForgetPasswordPhoneContract;
import com.fcn.park.login.module.PhoneGetModule;

/**
 * Created by 860115001 on 2018/04/10.
 */

public class ForgetPasswordPresenter extends BasePresenter<ForgetPasswordPhoneContract.View> implements ForgetPasswordPhoneContract.Presenter {

    private PhoneGetModule mModule;
    private RequestModule mRequestModule;

    @Override
    public void attach(ForgetPasswordPhoneContract.View view) {
        super.attach(view);
        mModule = new PhoneGetModule();
        mRequestModule = new RequestModule();
    }

    @Override
    public void onClickGetVerify() {
        String phoneNum = getView().getInputPhoneNum();
        mModule.setVerifyCode(null);
        getView().startCountDownTimer();
        mRequestModule.startSendCodeByPhone(phoneNum, new ProgressSubscriber<>(getView().getContext(), new RequestImpl<HttpResult<CodeBean>>() {


            @Override
            public void onNext(HttpResult<CodeBean> httpResult) {
                mModule.setVerifyCode(httpResult.getData().getRandom());
            }
        }));
    }

    @Override
    public void onClickInputPhoneNext() {

        String phoneNum = getView().getInputPhoneNum();

        if (TextUtils.isEmpty(phoneNum)) {
            getView().showToast("请填写您的手机号");
            return;
        }

        mModule.setPhoneNum(phoneNum);
        mRequestModule.startSendCodeByPhone(phoneNum, new ProgressSubscriber<>(getView().getContext(), new RequestImpl<HttpResult<CodeBean>>() {
            @Override
            public void onNext(HttpResult<CodeBean> stringHttpResult) {
                mModule.setVerifyCode(stringHttpResult.getData().getRandom());
                getView().inputPhoneOut();
                getView().sendVerifyIn();
                getView().setNumProgress(1);
                getView().startCountDownTimer();
                getView().showSendVerifyHint(
                        mModule.setSendVerifyFormat(
                                String.format(getView().getInputVerifyHint(), mModule.getPhoneNum()),
                                getView().getResColor(R.color.colorBlue),
                                getView().getResColor(R.color.colorTextBlack),
                                20,
                                "验证码",
                                ":"
                        ));
            }
        }));
    }

    @Override
    public void onClickSendVerifyNext() {
        if (getView().checkVerifyInputEmpty()) {
            getView().showToast("请填写您手机收到的验证码");
            return;
        }
        String verify = getView().getInputVerify();
        String verifyCode = mModule.getVerifyCode();
        if (!verify.equals(verifyCode)) {
            getView().showToast("验证码不正确");
            return;
        }
        mModule.setVerifyCode(verify);
        getView().sendVerifyOut();
        getView().saveNewPasswordIn();
        getView().setNumProgress(2);
       /* getView().showSavePasswordHint(
                mModule.setSaveNewPasswordFormat(
                        String.format(getView().getSaveNewPasswordHint(), mModule.getPhoneNum()),
                        "帐号",
                        getView().getResColor(R.color.colorTextBlack),
                        20
                ));*/
    }

    @Override
    public void onClickSaveNewPassword() {
        if (getView().checkNewPasswordInputEmpty()) {
            getView().showToast("密码不可为空");
            return;
        }
        String newPassword = getView().getInputNewPassword();
        String phoneNum = getView().getInputPhoneNum();
        mModule.setNewPassword(newPassword);
        if (RECheckUtils.checkPassword(newPassword) == 1){
            getView().showToast("请输入合法的密码");
            return;
        }else if (RECheckUtils.checkPassword(newPassword) == 2) {
            getView().showToast("密码中不能包含中文字符");
            return;
        }
        mRequestModule.startEditPwdByPhone(phoneNum, newPassword, new ProgressSubscriber<>(getView().getContext(), new RequestImpl<HttpResult<String>>() {
            @Override
            public void onNext(HttpResult<String> stringHttpResult) {
                getView().showToast("您设置的新密码成功");
                getView().closeActivity();
            }
        }));
    }
}

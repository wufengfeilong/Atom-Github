package com.fcn.park.login.presenter;

import com.fcn.park.base.BasePresenter;
import com.fcn.park.base.interfacee.OnDataCallback;
import com.fcn.park.base.utils.RECheckUtils;
import com.fcn.park.login.activity.RegisterClauseActivity;
import com.fcn.park.login.contract.RegisterContract;
import com.fcn.park.login.module.PhoneRegisterModule;

/**
 * Created by 860115001 on 2018/04/10.
 */

public class RegisterPresenter extends BasePresenter<RegisterContract.View> implements RegisterContract.Presenter {
    private PhoneRegisterModule mPhoneRegisterModule;
    private String REG_TYPE_CODE_PHONE = "phone";

    @Override
    public void attach(RegisterContract.View view) {
        super.attach(view);
        mPhoneRegisterModule = new PhoneRegisterModule();
    }

    @Override
    public void onClickGetVerify() {
        String phoneNum = getView().getInputPhoneNum();
        if (getView().checkInputPhoneNumEmpty()) {
            getView().showToast("请输入手机号码");
            return;
        } else if (!RECheckUtils.checkPhoneNum(phoneNum)) {
            getView().showToast("请输入正确的手机号");
        }

        mPhoneRegisterModule.getVerify(getView().getContext(), phoneNum, new OnDataCallback<String>() {
            @Override
            public void onSuccessResult(String s) {
                getView().startCountDownTimer();
                getView().showToast(s);
            }

            @Override
            public void onError(String errMsg) {

            }
        });
    }

    /**
     * 进入注册条款界面
     */
    @Override
    public void onClickGoRegisterClause() {
        getView().actionStartActivity(RegisterClauseActivity.class);
    }

    @Override
    public void onClickSubmit() {
        String phoneNum = getView().getInputPhoneNum();
        String password = getView().getInputPassword();
        String verify = getView().getInputVerify();
        if (getView().checkInputPhoneNumEmpty()) {
            getView().showToast("请输入手机号码");
            return;
        } else if (!RECheckUtils.checkPhoneNum(phoneNum)) {
            getView().showToast("请输入正确的手机号");
            return;
        }

        if (getView().checkInputPasswordEmpty()) {
            getView().showToast("请输入密码");
            return;
        } else if (RECheckUtils.checkPassword(password) == 1) {
            getView().showToast("请输入合法的密码");
            return;
        } else if (RECheckUtils.checkPassword(password) == 2) {
            getView().showToast("密码中不能包含中文字符");
            return;
        }
//        if (TextUtils.isEmpty(getView().getInputRPassword())) {
//            getView().showToast("请输入确认密码");
//            return;
//        } else if (!getView().checkRPassword()) {
//            getView().showToast("两次输入密码不一致");
//            return;
//        }
        if (getView().checkInputVerifyEmpty()) {
            getView().showToast("请输入验证码");
            return;
        }

        mPhoneRegisterModule.userReg(getView().getContext(), password, phoneNum, verify,
                new OnDataCallback<String>() {

                    @Override
                    public void onSuccessResult(String s) {
                        getView().showToast("注册成功");
                        getView().resetRegisterOk();
                        getView().closeActivity();
                    }

                    @Override
                    public void onError(String errMsg) {
                        getView().showToast("注册失败");
                    }
                });
    }
}

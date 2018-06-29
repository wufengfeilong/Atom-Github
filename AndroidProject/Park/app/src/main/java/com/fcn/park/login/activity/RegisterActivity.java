package com.fcn.park.login.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import com.fcn.park.R;
import com.fcn.park.base.BaseActivity;
import com.fcn.park.databinding.ActivityRegisterBinding;
import com.fcn.park.login.RegisterCodeTimer;
import com.fcn.park.login.contract.RegisterContract;
import com.fcn.park.login.presenter.RegisterPresenter;

/**
 * Created by 860115001 on 2018/04/10.
 */

public class RegisterActivity extends BaseActivity<ActivityRegisterBinding, RegisterContract.View,
        RegisterPresenter> implements RegisterContract.View {

    private static final int REQUEST_CODE_REGISTER = 0x00000012;
    private RegisterCodeTimer mRegisterCodeTimer;

    @Override
    protected void setupTitle() {
//        ChangeStatusBarStatus.setStatusBarDarkMode(this, true, Color.WHITE);
        setTitleText("注册");
        openTitleLeftView(true);
    }

    @Override
    protected void initViews() {
        mDataBinding.setPresenter(mPresenter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected RegisterPresenter createPresenter() {
        return new RegisterPresenter();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CODE_REGISTER) {
                finish();
            }
        }
    }

    @Override
    public String getInputPhoneNum() {
        return mDataBinding.inputPhoneNum.getInputText().toString().trim();
    }

    @Override
    public String getInputPassword() {
        return mDataBinding.inputPassword.getInputText().toString().trim();
    }

//    @Override
//    public String getInputRPassword() {
//        return mDataBinding.inputRPassword.getInputText().toString().trim();
//    }

    @Override
    public String getInputVerify() {
        return mDataBinding.inputVerify.getInputText().toString();
    }

    @Override
    public boolean checkInputPhoneNumEmpty() {
        return checkInputEmpty(mDataBinding.inputPhoneNum.getInputEditView());
    }

    @Override
    public boolean checkInputPasswordEmpty() {
        return checkInputEmpty(mDataBinding.inputPassword.getInputEditView());
    }

//    @Override
//    public boolean checkRPassword() {
//        return mDataBinding.inputPassword.getInputText().toString().equals(mDataBinding.inputRPassword.getInputText().toString());
//    }

    @Override
    public boolean checkInputVerifyEmpty() {
        return checkInputEmpty(mDataBinding.inputVerify.getInputEditView());
    }

    @Override
    public void startCountDownTimer() {
        mRegisterCodeTimer.startTiming(R.color.windowBackground, R.color.windowBackground);
    }

    /**
     * 返回注册成功的消息
     */
    @Override
    public void resetRegisterOk() {
        setResult(Activity.RESULT_OK);
    }

    private boolean checkInputEmpty(TextView textView) {
        String text = textView.getText().toString();
        return TextUtils.isEmpty(text);
    }
}

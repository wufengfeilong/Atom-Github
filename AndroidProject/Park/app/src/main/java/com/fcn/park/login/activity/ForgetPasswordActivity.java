package com.fcn.park.login.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.fcn.park.R;
import com.fcn.park.base.BaseActivity;
import com.fcn.park.base.utils.ViewUtils;
import com.fcn.park.base.widget.NumProgressLayout;
import com.fcn.park.databinding.ActivityForgetPasswordBinding;
import com.fcn.park.login.RegisterCodeTimer;
import com.fcn.park.login.contract.ForgetPasswordPhoneContract;
import com.fcn.park.login.presenter.ForgetPasswordPresenter;

/**
 * 忘记密码
 * Created by 860115001 on 2018/04/10.
 */

public class ForgetPasswordActivity extends BaseActivity<ActivityForgetPasswordBinding, ForgetPasswordPhoneContract.View,
        ForgetPasswordPresenter>
        implements ForgetPasswordPhoneContract.View {


    private RegisterCodeTimer mRegisterCodeTimer;

    /**
     * 设置ToolBar标题
     */
    @Override
    protected void setupTitle() {
        setTitleText("忘记密码", DEFAULT_TITLE_TEXT_COLOR);
        openTitleLeftView(true);
    }

    /**
     * 获取LayoutId
     * @return
     */
    @Override
    protected int getLayoutId() {
        return R.layout.activity_forget_password;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * 初始化界面
     */
    @Override
    protected void initViews() {
        mRegisterCodeTimer = new RegisterCodeTimer(mDataBinding.tvGetVerify);
        mDataBinding.setPresenter(mPresenter);
    }

    /**
     * 获取Presenter
     * @return
     */
    @Override
    protected ForgetPasswordPresenter createPresenter() {
        return new ForgetPasswordPresenter();
    }

    @Override
    public void inputPhoneOut() {
        ViewUtils.viewOut(mDataBinding.llInputPhone);
    }

    @Override
    public void sendVerifyIn() {
        ViewUtils.viewIn(mDataBinding.llSendVerify);
    }

    @Override
    public void sendVerifyOut() {
        ViewUtils.viewOut(mDataBinding.llSendVerify);
    }

    @Override
    public void saveNewPasswordIn() {
        ViewUtils.viewIn(mDataBinding.llSavePassword);
    }

    @Override
    public void setNumProgress(int progress) {
        View childView = mDataBinding.llNumProgress.getChildAt(progress);
        if (childView instanceof NumProgressLayout) {
            ((NumProgressLayout) childView).setSelect(true);
        }
    }

    @Override
    public String getInputPhoneNum() {
        return mDataBinding.inputPhone.getInputText().toString();
    }

    @Override
    public String getInputVerify() {
        return mDataBinding.inputVerify.getInputText().toString();
    }

    @Override
    public String getInputNewPassword() {
        return mDataBinding.inputNewPassword.getInputText().toString();
    }

    @Override
    public void showSendVerifyHint(CharSequence hint) {
        mDataBinding.tvSendVerifyHint.setText(hint);
    }

    @Override
    public void showSavePasswordHint(CharSequence hint) {
        mDataBinding.tvSaveNewPasswordHint.setText(hint);
    }

    @Override
    public String getInputVerifyHint() {
        return mDataBinding.tvSendVerifyHint.getText().toString();
    }

    @Override
    public void actionStartActivity(Class cls) {
        super.actionStartActivity(cls);
    }

    @Override
    public String getSaveNewPasswordHint() {
        return mDataBinding.tvSaveNewPasswordHint.getText().toString();
    }

    @Override
    public void startCountDownTimer() {
        mRegisterCodeTimer.startTiming(R.color.windowBackground, R.color.windowBackground);
    }

    @Override
    public boolean checkVerifyInputEmpty() {
        return checkInputEmpty(mDataBinding.inputVerify.getInputEditView());
    }

    @Override
    public boolean checkPhoneInputEmpty() {
        return checkInputEmpty(mDataBinding.inputPhone.getInputEditView());
    }

    @Override
    public boolean checkNewPasswordInputEmpty() {
        return checkInputEmpty(mDataBinding.inputNewPassword.getInputEditView());
    }

    private boolean checkInputEmpty(TextView textView) {
        String text = textView.getText().toString();
        return TextUtils.isEmpty(text);
    }

}

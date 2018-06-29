package com.fcn.park.manager.activity;

import com.fcn.park.R;
import com.fcn.park.base.BaseActivity;
import com.fcn.park.databinding.ManagerResetPasswordBinding;
import com.fcn.park.manager.contract.ManagerResetPasswordContract;
import com.fcn.park.manager.presenter.ManagerResetPasswordPresenter;

/**
 * 管理员用户重置登录密码用Activity.
 *
 */
public class ManagerResetPasswordActivity
        extends BaseActivity<ManagerResetPasswordBinding, ManagerResetPasswordContract.View, ManagerResetPasswordPresenter>
        implements ManagerResetPasswordContract.View {

    /**
     * 重写的方法，用来加载定义画面的Layout
     *
     * @return
     */
    @Override
    protected int getLayoutId() {
        return R.layout.manager_reset_password;
    }

    /**
     * 重写的方法，用来设置标题栏要显示的文字
     */
    @Override
    protected void setupTitle() {
        setTitleText(getString(R.string.manager_reset_password));
        openTitleLeftView(true);
    }

    @Override
    protected void initViews() {
        mDataBinding.setPresenter(mPresenter);
    }

    @Override
    protected ManagerResetPasswordPresenter createPresenter() {
        return new ManagerResetPasswordPresenter();
    }

    @Override
    public String getOldPassword() {
        return mDataBinding.editOldPassword.getText().toString();
    }

    @Override
    public String getNewPassword() {
        return mDataBinding.editNewPassword.getText().toString();
    }

    @Override
    public String getAgainPassword() {
        return mDataBinding.editAgainPassword.getText().toString();
    }
}

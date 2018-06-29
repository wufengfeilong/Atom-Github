package com.fcn.park.manager.presenter;

import android.text.TextUtils;

import com.fcn.park.base.BasePresenter;
import com.fcn.park.base.utils.OnDataGetCallback;
import com.fcn.park.base.utils.RECheckUtils;
import com.fcn.park.login.LoginHelper;
import com.fcn.park.manager.contract.ManagerResetPasswordContract;
import com.fcn.park.manager.module.ManagerResetPasswordModule;

/**
 * 管理员用户重置登录密码用Presenter.
 */

public class ManagerResetPasswordPresenter extends BasePresenter<ManagerResetPasswordContract.View> implements ManagerResetPasswordContract.Presenter {

    private ManagerResetPasswordModule mManagerResetPasswordModule;

    @Override
    public void attach(ManagerResetPasswordContract.View view) {
        super.attach(view);
        mManagerResetPasswordModule = new ManagerResetPasswordModule();
    }

    /**
     * 点击“保存”按钮后的处理
     */
    @Override
    public void onClickSave() {
        String oldPas = getView().getOldPassword();
        String newPas = getView().getNewPassword();
        String againPas = getView().getAgainPassword();
        if (TextUtils.isEmpty(oldPas) ) {
            getView().showToast("请输入当前密码");
            return;
        }

        if (TextUtils.isEmpty(newPas)) {
            getView().showToast("请输入新密码");
            return;
        } else if (RECheckUtils.checkPassword(newPas)==1) {
            getView().showToast("请输入符合规范的密码");
            return;
        }else if (RECheckUtils.checkPassword(newPas) == 2){
            getView().showToast("密码中不能包含中文字符");
            return;
        }
        if (TextUtils.isEmpty(againPas)) {
            getView().showToast("请再次输入密码");
            return;
        }
        if (!newPas.equals(againPas)) {
            getView().showToast("输入的新密码两次不相同");
            return;
        }
        mManagerResetPasswordModule.resetPassword(getView().getContext(), LoginHelper.getInstance().getUserToken(), oldPas, newPas, new OnDataGetCallback<String>() {
            @Override
            public void onSuccessResult(String data) {
                getView().showToast(data);
                getView().closeActivity();
            }
        });
    }
}

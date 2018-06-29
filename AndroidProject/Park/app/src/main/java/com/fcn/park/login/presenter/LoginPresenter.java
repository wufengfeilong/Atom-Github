package com.fcn.park.login.presenter;

import android.text.TextUtils;

import com.fcn.park.MainActivity;
import com.fcn.park.base.BasePresenter;
import com.fcn.park.base.http.HttpResult;
import com.fcn.park.base.http.ProgressSubscriber;
import com.fcn.park.base.http.RequestImpl;
import com.fcn.park.base.http.RequestModule;
import com.fcn.park.login.LoginHelper;
import com.fcn.park.login.activity.ForgetPasswordActivity;
import com.fcn.park.login.activity.RegisterActivity;
import com.fcn.park.login.bean.User;
import com.fcn.park.login.contract.LoginContract;

/**
 * Created by 860115001 on 2018/04/10.
 */

public class LoginPresenter extends BasePresenter<LoginContract.View> implements LoginContract.Presenter {
    private RequestModule mRequestModule;

    @Override
    public void attach(LoginContract.View view) {
        super.attach(view);
        mRequestModule = new RequestModule();
    }

    @Override
    public void onClickLogin() {

        getView().getInputPassword();
        final String userName = getView().getInputUserName();
        String password = getView().getInputPassword();
        if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(password)) {
            getView().showToast("帐号或密码不可为空");
            return;
        }

        mRequestModule.startLogin(userName, password, new ProgressSubscriber<>(getView().getContext(), new RequestImpl<HttpResult<User>>() {
            @Override
            public void onNext(HttpResult<User> userHttpResult) {
                getView().showToast(userHttpResult.getMsg());
                LoginHelper.getInstance().userExit();
                User user = userHttpResult.getData();
//                getView().saveUser(user);
                LoginHelper.getInstance().setOnline(true);
                LoginHelper.getInstance().getUserBean().setUserType(user.getUserType());
                LoginHelper.getInstance().getUserBean().setToken(user.getToken());
                LoginHelper.getInstance().getUserBean().setUserName(user.getUserName());
                LoginHelper.getInstance().getUserBean().setAvatar(user.getAvatar());
//                mRequestModule.saveCID(user.getUserName(), PushManager.getInstance().getClientid(getView().getContext()));
                //如果登录的用户为管理员用户，则通知MainActivity进行关闭，那么就会走他的onActivityResult()方法
//                if (Constant.UserType.MANAGE.getValue().equals(user.getUserType())) {
//                    getView().closeMain();
//                } else {
//                    getView().actionStartActivity(MainActivity.class);
//                }
                getView().actionStartActivity(MainActivity.class);
                getView().closeActivity();
            }
        }));


    }

    @Override
    public void onClickGoRegister() {
        getView().actionStartActivity(RegisterActivity.class);
    }

    @Override
    public void onClickForgetPassword() {
        getView().actionStartActivity(ForgetPasswordActivity.class);
    }

    @Override
    public void onClickClose() {
        getView().closeActivity();
    }
}

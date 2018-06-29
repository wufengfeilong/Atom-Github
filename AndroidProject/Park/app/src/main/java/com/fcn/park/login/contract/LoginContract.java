package com.fcn.park.login.contract;

import com.fcn.park.base.BaseView;
import com.fcn.park.login.bean.User;

/**
 * Created by 860115001 on 2018/04/10.
 */

public interface LoginContract {
    interface View extends BaseView {
        String getInputUserName();

        String getInputPassword();

        void saveUser(User user);

        void closeMain();
    }

    interface Presenter {
        void onClickLogin();

        void onClickGoRegister();

        void onClickForgetPassword();

        void onClickClose();
    }
}

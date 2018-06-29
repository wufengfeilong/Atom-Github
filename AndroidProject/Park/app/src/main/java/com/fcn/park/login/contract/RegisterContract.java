package com.fcn.park.login.contract;

import com.fcn.park.base.BaseView;

/**
 * Created by 860115001 on 2018/04/10.
 */

public interface RegisterContract {
    interface View extends BaseView {
        String getInputPhoneNum();

        String getInputPassword();

//        String getInputRPassword();

        String getInputVerify();

        boolean checkInputPhoneNumEmpty();

        boolean checkInputPasswordEmpty();

//        boolean checkRPassword();

        boolean checkInputVerifyEmpty();

        /**
         * 开始进行倒计时
         */
        void startCountDownTimer();

        /**
         * 返回注册成功的消息
         */
        void resetRegisterOk();
    }

    interface Presenter {
        void onClickGetVerify();

        void onClickSubmit();
        /**
         * 进入注册条款界面
         */
        void onClickGoRegisterClause();
    }
}

package com.fcn.park.login.contract;

import com.fcn.park.base.BaseView;

/**
 * Created by 860115001 on 2018/04/10.
 */

public interface ForgetPasswordPhoneContract {

    interface View extends BaseView {

        void inputPhoneOut();

        void sendVerifyIn();

        void sendVerifyOut();

        void saveNewPasswordIn();

        void setNumProgress(int progress);

        void showSendVerifyHint(CharSequence hint);

        void showSavePasswordHint(CharSequence hint);

        String getInputPhoneNum();

        String getInputVerify();

        String getInputNewPassword();

        String getInputVerifyHint();

        String getSaveNewPasswordHint();

        void startCountDownTimer();


        boolean checkVerifyInputEmpty();

        boolean checkPhoneInputEmpty();

        boolean checkNewPasswordInputEmpty();
    }

    interface Presenter {
        void onClickGetVerify();

        void onClickInputPhoneNext();

        void onClickSendVerifyNext();

        void onClickSaveNewPassword();
    }
}

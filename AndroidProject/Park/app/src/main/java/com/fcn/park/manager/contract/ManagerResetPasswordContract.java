package com.fcn.park.manager.contract;

import com.fcn.park.base.BaseView;

/**
 * 管理员用户重置登录密码用Contract
 */

public interface ManagerResetPasswordContract {
    interface View extends BaseView {
        /**
         * 获取原有密码
         *
         * @return
         */
        String getOldPassword();

        /**
         * 获取新密码
         *
         * @return
         */
        String getNewPassword();

        /**
         * 获取再次输入的密码
         *
         * @return
         */
        String getAgainPassword();
    }

    interface Presenter {
        /**
         * 点击保存
         */
        void onClickSave();
    }
}

package sdwxwx.com.me.contract;

import sdwxwx.com.base.BaseView;
import sdwxwx.com.bean.UserBean;

/**
 * Created by 860116042 on 2018/5/17.
 */

public interface MeEditContract {
    interface View extends BaseView {
        void GetPermission();
        void showChoosePicDialog();
        void showDateDialog();
        void showSexChooseDialog();
        UserBean getUserInfo();
    }

    interface Presenter {
        // 点击保存
        void onClickSave();

        void onClickBack();

        //更换头像
        void onClickImage();

        // 点击性别
        void onClickSelectSex();

        // 点击生日
        void onClickSelectBirth();
    }
}

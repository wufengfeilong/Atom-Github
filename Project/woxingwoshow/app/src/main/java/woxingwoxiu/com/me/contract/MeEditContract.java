package woxingwoxiu.com.me.contract;

import woxingwoxiu.com.base.BaseView;

/**
 * Created by 860116042 on 2018/5/17.
 */

public interface MeEditContract {
    interface View extends BaseView {
        void showChoosePicDialog();
        void savaObject();
        void showDateDialog();
        void showSexChooseDialog();
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

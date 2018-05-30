package woxingwoxiu.com.login.contract;

import woxingwoxiu.com.base.BaseView;

/**
 * Created by 丁胜胜 on 2018/05/14.
 */

public interface DataEditingContract {

    public interface View extends BaseView {

        void showChoosePicDialog();

        void savaObject();
    }

    public interface Presenter{
        //叉号的点击事件
        void onCross();

        //右上角点击事件
        void onJump();

        void onClickOk();

        void onClickImage();
    }
}

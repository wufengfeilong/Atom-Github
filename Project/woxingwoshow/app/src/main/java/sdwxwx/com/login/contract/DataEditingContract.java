package sdwxwx.com.login.contract;

import sdwxwx.com.base.BaseView;
import sdwxwx.com.login.bean.DataEditingBean;

/**
 * Created by 丁胜胜 on 2018/05/14.
 */

public interface DataEditingContract {

    public interface View extends BaseView {

        void GetPermission();

        void savaObject();

        DataEditingBean getBean();

        String getInputUserName();
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

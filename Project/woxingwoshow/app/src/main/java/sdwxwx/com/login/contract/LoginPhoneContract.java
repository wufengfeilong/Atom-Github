package sdwxwx.com.login.contract;


import android.content.Intent;

import sdwxwx.com.base.BaseView;

/**
 * Created by 丁胜胜 on 2018/05/09.
 */

public interface LoginPhoneContract {

    public interface View extends BaseView {
        String getInputPhoneNumSpace();
        String getInputPhoneNum();
        Intent getParamIntent();
    }

    public interface Presenter{
        //叉号的点击事件
        void onCross();

        void onNext();
    }
}

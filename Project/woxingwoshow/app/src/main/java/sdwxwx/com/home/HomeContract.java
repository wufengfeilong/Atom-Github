package sdwxwx.com.home;

import sdwxwx.com.base.BaseView;

/**
 * create by 860115039
 * date      2018/5/8
 * time      17:32
 */
public interface HomeContract {

    interface View extends BaseView {
        /**
         * 设置默认的Fragment
         */
        void setDefaultFragment();
        /**
         * 设置显示的Fragment
         */
        void setShowFragment(int position);
        /**
         * 设置显示的Fragment
         */
        void switchContent(int position);
    }

    interface Presenter{

        //首页
        void toHome();
        //附近
        void toNear();
        //拍摄
        void toCapture();
        //信息
        void toMsg();
        //我的
        void toMe();
    }
}

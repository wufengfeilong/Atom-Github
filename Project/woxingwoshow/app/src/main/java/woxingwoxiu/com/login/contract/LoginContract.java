package woxingwoxiu.com.login.contract;


import woxingwoxiu.com.base.BaseView;

/**
 * Created by 丁胜胜 on 2018/05/09.
 */

public interface LoginContract {

    public interface View extends BaseView {


    }

    public interface Presenter{
        //叉号的点击事件
        void onCross();

        //右上角点击事件

        void onProblem();

        void onLoginQQ();

        void onLoginWeixin();

        void onLoginTel();


    }
}

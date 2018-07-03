package sdwxwx.com.login.contract;

import cn.emay.sdk.util.exception.SDKParamsException;
import sdwxwx.com.base.BaseView;
import sdwxwx.com.login.bean.LoginVerifyBean;

/**
 * Created by 丁胜胜 on 2018/05/11.
 */

public interface LoginVerifyContract {

    public interface View extends BaseView{
        /**
         * 开始进行倒计时
         */
        void startCountDownTimer();

        int getInputVerify();
        String getVerify();

        //手机号输入界面获取的手机号
        LoginVerifyBean.VerifyBean getBean();

        //获取城市Id
        String getOnCity_id();


    }

    public interface Presenter{
        //叉号的点击事件
        void onCross();

        void onNext();

        //用户
        void onClickGoUserAgreement();

        void onClickGetVerify() throws SDKParamsException;

        void verify();

    }
}

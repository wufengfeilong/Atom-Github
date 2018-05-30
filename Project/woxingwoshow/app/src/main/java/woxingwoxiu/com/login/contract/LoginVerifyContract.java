package woxingwoxiu.com.login.contract;

import cn.emay.sdk.util.exception.SDKParamsException;
import woxingwoxiu.com.base.BaseView;

/**
 * Created by 丁胜胜 on 2018/05/11.
 */

public interface LoginVerifyContract {

    public interface View extends BaseView{
        /**
         * 开始进行倒计时
         */
        void startCountDownTimer();

        String getInputVerify();
        String getVerify();

        //发送单行短信
        void sendSingleSms() throws SDKParamsException;


    }

    public interface Presenter{
        //叉号的点击事件
        void onCross();

        void onNext();

        //用户
        void onClickGoUserAgreement();

        void onClickGetVerify() throws SDKParamsException;

    }
}

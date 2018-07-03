package sdwxwx.com.login.presenter;

import android.content.Intent;
import android.os.Bundle;

import sdwxwx.com.base.BasePresenter;
import sdwxwx.com.login.activity.LoginVerifyActivity;
import sdwxwx.com.login.bean.LoginVerifyBean;
import sdwxwx.com.login.contract.LoginPhoneContract;
import sdwxwx.com.login.utils.ActivityCollector;


/**
 * Created by 丁胜胜 on 2018/05/09.
 */

public class LoginPhonePresenter extends BasePresenter<LoginPhoneContract.View> implements LoginPhoneContract.Presenter {


    @Override
    public void onCross() {//关闭当前画面
        ActivityCollector.finishAll();
    }

    /**
     * 点击跳转下一步
     */
    @Override
    public void onNext() {
        // 参数设定用Bean
        LoginVerifyBean.VerifyBean bean = new LoginVerifyBean.VerifyBean();
        // 设定手机号
        bean.setPhoneNumSpace(getView().getInputPhoneNumSpace());
        bean.setPhoneNum(getView().getInputPhoneNum());
        // 取得前一画面参数
        Intent intent = getView().getParamIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            // 如果存在传递的参数，则说明是通过第三方登录跳转到绑定手机号画面
            if (bundle != null) {
                // 设定通过第三方登录获取的内容
                bean.setQqId(bundle.getString("qqId", ""));
                bean.setWechatId(bundle.getString("wechatId", ""));
                bean.setUserName(bundle.getString("userName", ""));
                bean.setUserIcon(bundle.getString("userIcon", ""));
                bean.setUserGender(bundle.getString("userGender", ""));
            }
        }
        // 跳转到验证码画面
        LoginVerifyActivity.actionStart(getView().getContext(), bean);


    }
}

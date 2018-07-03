package sdwxwx.com.login.presenter;

import android.util.Log;
import cn.emay.sdk.util.StringUtil;
import sdwxwx.com.base.BaseCallback;
import sdwxwx.com.base.BasePresenter;
import sdwxwx.com.base.LoginCallback;
import sdwxwx.com.bean.UserBean;
import sdwxwx.com.home.HomeActivity;
import sdwxwx.com.login.activity.DataEditingActivity;
import sdwxwx.com.login.activity.UserAgreementActivity;
import sdwxwx.com.login.bean.LoginVerifyBean;
import sdwxwx.com.login.contract.LoginVerifyContract;
import sdwxwx.com.login.model.DataEditingModel;
import sdwxwx.com.login.model.LoginModel;
import sdwxwx.com.login.model.LoginVerifyModel;
import sdwxwx.com.login.utils.ActivityCollector;
import sdwxwx.com.login.utils.LoginHelper;
import sdwxwx.com.util.LoginUtil;

import java.util.HashMap;


/**
 * Created by 丁胜胜 on 2018/05/11.
 */

public class LoginVerifyPresenter extends BasePresenter<LoginVerifyContract.View> implements LoginVerifyContract.Presenter {

    LoginVerifyModel mModel;

    LoginModel loginModel;

    public String TAG = "LoginVerifyPresenter";

    int verifyCode;
    /**
     * 注册成功标识
     */
    boolean registerFlag = false;
    /**
     * 是否第三方平台标识
     */
    boolean isThirdPlatform = false;

    @Override
    public void onCross() {//关闭画面
        ActivityCollector.finishAll();
    }

    @Override
    public void onNext() {//点击下一步

        // 获取数据
        LoginVerifyBean.VerifyBean bean = getView().getBean();
        // 如果QQID不为空，则是通过第三方平台进行登录
        if (!StringUtil.isEmpty(bean.getQqId())) {
            isThirdPlatform = true;
        }
        int verify = getView().getInputVerify();

        if (verify != verifyCode) {
            getView().showToast("验证码不正确");
            return;
        } else {

            // 如果是第三方登录跳转到本画面，则一定进行注册，让手机号码与第三方信息进行绑定
            if (isThirdPlatform) {
                // 调用注册接口 注：第三方登录的时候需要调用注册接口，让QQ、微信与手机号进行绑定
                DataEditingModel.phoneRegister(bean.getPhoneNum(),String.valueOf(verifyCode),
                        bean.getWechatId(),bean.getQqId(),bean.getUserName(),bean.getUserIcon(),getView().getOnCity_id(),new BaseCallback<UserBean>(){
                            @Override
                            public void onSuccess(UserBean data) {
                                // 注册成功标识设为true
                                registerFlag = true;
                            }
                            @Override
                            public void onFail(String msg) {
                                if (getView() == null) {
                                    return;
                                }
                                getView().showToast(msg);
                            }
                        });
            }
            // 如果是手机号码进行登录或者第三方注册成功都进行登陆操作
            if (!isThirdPlatform || registerFlag) {
                // 定义参数用MAP
                final HashMap<String,Object> hashMap = new HashMap<String,Object>(100);
                // 手机号
                hashMap.put("mobile", bean.getPhoneNum());
                // 验证码
                hashMap.put("code", verifyCode);
                // 微信ID
                hashMap.put("wechat_id", "0");
                // QQID
                hashMap.put("qq_id", "0");

                // 调用手机号码的登录方法
                loginModel.loginUser(hashMap,new LoginCallback<UserBean>(){
                    @Override
                    public void onRegister() {
                        // 如果是手机号码登录，但还未注册，跳转到头像设置画面
                        if (!isThirdPlatform) {
                            //城市Id
                            hashMap.put("city_id",getView().getOnCity_id());
                            // 跳转到头像昵称设置画面
                            DataEditingActivity.actionStart(getView().getContext(), hashMap);
                        }
                    }

                    @Override
                    public void onSuccess(UserBean data) {
                        // 保存用户的登录信息
                        LoginHelper.getInstance().setUserBean(data);
                        if (getView() == null) {
                            return;
                        }
                        LoginUtil.saveLoginInfo(getView().getContext(),data);
                        // 如果成功了，则直接跳转到HOME画面
                        getView().actionStartActivity(HomeActivity.class);

                    }

                    @Override
                    public void onFail(String msg) {
                        if (getView() == null) {
                            return;
                        }
                        getView().showToast(msg);
                    }
                });
            } else {
                getView().showToast("登录失败，请稍后重试！");
            }
        }
    }


    @Override
    public void onClickGetVerify(){//获取验证码
        //验证码倒计时
        getView().startCountDownTimer();

        //获取验证码
        verify();
    }

    //获取验证码
    @Override
    public void verify() {
        //当前手机号
        String mobile = getView().getBean().getPhoneNum();

        //获取验证码
        mModel.getVerify(mobile, new BaseCallback<String>() {

            @Override
            public void onSuccess(String data) {
                Log.i(TAG, "======First->verify=====" + data);
                verifyCode = Integer.parseInt(data);

            }
            @Override
            public void onFail(String msg) {
                if (getView() == null) {
                    return;
                }
                getView().showToast(msg);
            }

        });
    }

    @Override
    public void onClickGoUserAgreement() {//用户协议
        getView().actionStartActivity(UserAgreementActivity.class);
    }

}

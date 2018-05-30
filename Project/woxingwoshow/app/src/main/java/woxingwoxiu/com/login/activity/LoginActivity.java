package woxingwoxiu.com.login.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.mob.tools.utils.ResHelper;
import com.mob.tools.utils.UIHandler;

import java.util.HashMap;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import woxingwoxiu.com.R;
import woxingwoxiu.com.base.BaseActivity;
import woxingwoxiu.com.databinding.ActivityLoginBinding;
import woxingwoxiu.com.home.HomeActivity;
import woxingwoxiu.com.login.contract.LoginContract;
import woxingwoxiu.com.login.presenter.LoginPresenter;
import woxingwoxiu.com.login.utils.ActivityCollector;
import woxingwoxiu.com.me.activity.FeedbackActivity;

import static woxingwoxiu.com.cons.Constant.SP_FILE_NAME;
import static woxingwoxiu.com.cons.Constant.SP_LOGIN_TOKEN;

/**
 * Created by 丁胜胜 on 2018/05/09.
 * 类描述：登陆界面
 */
public class LoginActivity
        extends BaseActivity<ActivityLoginBinding,LoginPresenter>
        implements LoginContract.View,Handler.Callback,View.OnClickListener,PlatformActionListener {

    /**
     * 页面初始化
     */
    @Override
    protected void initViews() {
        mDataBinding.setLoginPresenter(mPresenter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView loginText = (TextView) findViewById(R.id.login_text);

        // 微信登录
        ImageView weixinLogin = (ImageView) findViewById(R.id.login_weixin);
        weixinLogin.setOnClickListener(this);

        // QQ登录
        ImageView qqLogin = (ImageView) findViewById(R.id.login_qq);
        qqLogin.setOnClickListener(this);

        //手机登录
        ImageView telLogin = (ImageView) findViewById(R.id.login_tel);
        telLogin.setOnClickListener(this);

        //cross按钮
        ImageView buttonCross = (ImageView) findViewById(R.id.login_cross_c);
        buttonCross.setOnClickListener(this);

        //cross按钮
        TextView textProblem = (TextView) findViewById(R.id.login_problem_p);
        textProblem.setOnClickListener(this);

        TranslateAnimation translateAnimation = new TranslateAnimation(0, 0, 250, 0);
        translateAnimation.setDuration(1000);
        translateAnimation.setFillAfter(false);
        weixinLogin.setAnimation(translateAnimation);
        weixinLogin.startAnimation(translateAnimation);
        qqLogin.setAnimation(translateAnimation);
        qqLogin.startAnimation(translateAnimation);
        loginText.setAnimation(translateAnimation);
        loginText.startAnimation(translateAnimation);
        telLogin.setAnimation(translateAnimation);
        telLogin.startAnimation(translateAnimation);

        ActivityCollector.addActivity(this);//将活动添加到活动收集器
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // 授权登录按钮:非SSO
            case R.id.login_weixin:
                authorize(ShareSDK.getPlatform(Wechat.NAME), 1);
                break;
            // 授权登录按钮:非SSO
            case R.id.login_qq:
                authorize(ShareSDK.getPlatform(QQ.NAME), 2);
                break;
            case R.id.login_tel:
                Intent intent = new Intent(LoginActivity.this, LoginPhoneActivity.class);
                startActivity(intent);
                break;
            case R.id.login_cross_c:
                finish();
                break;
            case R.id.login_problem_p:
                Intent intentProblem = new Intent(LoginActivity.this, FeedbackActivity.class);
                startActivity(intentProblem);
                break;
            default:
                break;
        }

    }

    /**
     * 授权登录验证
     * @param plat 平台
     * @param type 平台类型
     */
    private void authorize(Platform plat, int type) {
        try {
            if (plat.isClientValid()) {
                showLoading();
                if (plat.isAuthValid()) { //如果授权就删除授权资料
                    plat.removeAccount(true);
                }
                plat.setPlatformActionListener(this);
                plat.SSOSetting(false);
                plat.showUser(null);
            } else {
                // 如果没有QQ客户端，则提示用户安装QQ
                if (type == 1) {
                    toast("ssdk_wechat_client_inavailable");
                } else {
                    toast("ssdk_qq_client_inavailable");
                }
            }
        } catch (Exception e) {
            switch (type) {
                case 1:
                    toast("ssdk_wechat_login_Exception");
                    break;
                case 2:
                    toast("ssdk_qq_login_Exception");
                    break;
            }
        }
    }

    // 回调：授权成功
    public void onComplete(Platform platform, int action, HashMap<String, Object> res) {
        Message msg = new Message();
        msg.what = 0;
        msg.arg1 = 1;
        msg.arg2 = action;
        msg.obj = platform;
        UIHandler.sendMessage(msg, this);
    }


    // 回调：授权失败
    public void onError(Platform platform, int action, Throwable t) {
        Message msg = new Message();
        msg.what = 0;
        msg.arg1 = 2;
        msg.arg2 = action;
        msg.obj = t;
        UIHandler.sendMessage(msg, this);
    }

    // 回调：授权取消
    public void onCancel(Platform platform, int action) {
        Message msg = new Message();
        msg.what = 0;
        msg.arg1 = 3;
        msg.arg2 = action;
        msg.obj = platform;
        UIHandler.sendMessage(msg, this);
    }

    // 统一消息处理
    private static final int MSG_USERID_FOUND 	= 1; // 用户信息已存在
    private static final int MSG_LOGIN 			= 2; // 登录操作
    private static final int MSG_AUTH_CANCEL 	= 3; // 授权取消
    private static final int MSG_AUTH_ERROR 	= 4; // 授权错误
    private static final int MSG_AUTH_COMPLETE 	= 5; // 授权完成

    //登陆发送的handle消息在这里处理
    @Override
    public boolean handleMessage(Message message) {
        hideLoading();
        switch (message.arg1) {
            case 1: { // 成功

                //获取用户资料
                Platform platform = (Platform) message.obj;
                String userId = platform.getDb().getUserId();//获取用户账号
                String userName = platform.getDb().getUserName();//获取用户名字
                String userIcon = platform.getDb().getUserIcon();//获取用户头像
                String userGender = platform.getDb().getUserGender(); //获取用户性别，m = 男, f = 女，如果微信没有设置性别,默认返回null
                Toast.makeText(LoginActivity.this, "用户信息为--用户名：" + userName + "  性别：" + userGender, Toast.LENGTH_SHORT).show();

                //TODO Step1 调用接口 判断该用户是否已经注册过
                // TODO Step2 调用接口 如果还没注册，则调用注册接口 对该用户进行注册
                // TODO STEp3  调用接口 进行会员登录操作

                // 若接口调用正常，则进行更新prefence操作以及进行画面跳转
                SharedPreferences sp = getSharedPreferences(SP_FILE_NAME, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();//获取编辑器
                editor.putString(SP_LOGIN_TOKEN,"01");
                editor.commit();
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intent);

            }
            break;
            case 2:
            case 3:
            break;
        }
        return false;
    }

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }
    /**
     * 提示信息
     * @param resOrName
     */
    private void toast(final String resOrName) {
        int resId = ResHelper.getStringRes(this, resOrName);
        if (resId > 0) {
            Toast.makeText(this, resId, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, resOrName, Toast.LENGTH_SHORT).show();
        }
    }
}
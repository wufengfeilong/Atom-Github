package com.fcn.park.login.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;

import com.fcn.park.R;
import com.fcn.park.base.BaseActivity;
import com.fcn.park.base.utils.InputMethodUtils;
import com.fcn.park.databinding.ActivityLoginBinding;
import com.fcn.park.login.bean.User;
import com.fcn.park.login.contract.LoginContract;
import com.fcn.park.login.presenter.LoginPresenter;
import com.fcn.park.login.util.UserUtils;

/**
 * Created by 860115001 on 2018/04/10.
 */

public class LoginActivity extends BaseActivity<ActivityLoginBinding, LoginContract.View, LoginPresenter> implements LoginContract.View  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initFieldBeforeMethods() {
        isUseDefaultTitle = false;
    }

    @Override
    public void actionStartActivity(Class cls) {
        super.actionStartActivity(cls);
    }

    @Override
    protected void setupTitle() {
        //AndroidBug5497Workaround.assistActivity(this);
        //因为这个页面中不需要封装的标题，所以在这里进行状态栏的处理
//        ChangeStatusBarStatus.setStatusBarDarkMode(this, true, Color.WHITE);
//        if (Build.VERSION.SDK_INT >= 21) {
//            getWindow().setStatusBarColor(Color.WHITE);
//        }
    }

    @Override
    protected void initViews() {
        mDataBinding.setPresenter(mPresenter);
//        setKeyboardStateListener();
    }

    /**
     * 添加软键盘状态监听器321+4
     */
    protected void setKeyboardStateListener() {
        final View rootView = getWindow().getDecorView().findViewById(android.R.id.content);
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                rootView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (InputMethodUtils.isKeyboardShown(rootView)) {
                            mDataBinding.slParent.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mDataBinding.slParent.scrollBy(0, mDataBinding.slParent.getHeight());
                                }
                            }, 100);
                            //Log.d("软键盘", "开启");
                        } else {
                            // Log.d("软键盘", "关闭");
                        }
                    }
                }, 100);
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter();
    }

    @Override
    public String getInputUserName() {
//        return mDataBinding.inputUserName.getInputText().toString().trim();
        return "13256799736";
    }

    @Override
    public String getInputPassword() {
//        return mDataBinding.inputPassword.getInputText().toString().trim();
        return "123456a?";
    }

    @Override
    public void saveUser(User user) {
        UserUtils.saveUser(mContext, user);
    }

    @Override
    public void closeMain() {
        setResult(RESULT_OK);
        finish();
    }
}

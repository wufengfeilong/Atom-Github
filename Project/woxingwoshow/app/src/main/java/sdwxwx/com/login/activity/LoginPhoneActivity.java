package sdwxwx.com.login.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import sdwxwx.com.R;
import sdwxwx.com.base.BaseActivity;
import sdwxwx.com.databinding.ActivityLoginPhoneBinding;
import sdwxwx.com.login.contract.LoginPhoneContract;
import sdwxwx.com.login.presenter.LoginPhonePresenter;
import sdwxwx.com.login.utils.ActivityCollector;
import sdwxwx.com.login.utils.ZpPhoneEditText;

/**
 * Created by 丁胜胜 on 2018/05/09.
 * 类描述：手机输入界面
 */
public class LoginPhoneActivity
        extends BaseActivity<ActivityLoginPhoneBinding,LoginPhonePresenter>
        implements LoginPhoneContract.View{
    private ZpPhoneEditText editText;
    int type;

    /**
     * 画面初期化
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 获取前一画面传递的参数
        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            // 如果存在传递的参数，则说明是通过第三方登录跳转到绑定手机号画面
            if (bundle != null) {
                ((TextView)this.findViewById(R.id.login_text_Tel)).setText("请绑定手机号");
            }
        }
    }
    /**
     * 页面初始化
     */
    @Override
    protected void initViews() {
        mDataBinding.setLoginPhonePresenter(mPresenter);
//        type = Integer.parseInt(getIntent().getStringExtra(Constant.INTENT_PARAM));
        // 获取手机号输入控件
        editText = this.findViewById(R.id.login_phone_num);
        editText.setFocusableInTouchMode(true);
        // 获取焦点
        editText.requestFocus();
        // 弹出软键盘
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                InputMethodManager inputManager =
                        (InputMethodManager)editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.showSoftInput(editText, 0);
            }
        }, 100);
        mDataBinding.loginNext.setEnabled(false);//初始化按钮不能点击

        //动态获取输入手机号焦点
        mDataBinding.loginPhoneNum.addTextChangedListener(new TextWatcher(){

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mDataBinding.loginNext.setEnabled(false);//初始化按钮不能点击
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (mDataBinding.loginPhoneNum.getText().length()==13){
                    mDataBinding.loginNext.setEnabled(true);
                }
            }
        });
        ActivityCollector.addActivity(this);//将活动添加到活动收集器

    }

    @Override
    protected LoginPhonePresenter createPresenter() {
        return new LoginPhonePresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login_phone;

    }

    /**
     * 获取输入的手机号
     * 保留手机号3-4-4格式
     * @return
     */
    @Override
    public String getInputPhoneNumSpace() {
        return mDataBinding.loginPhoneNum.getText().toString().trim();
    }

    /**
     * 获取输入的手机号
     * 清除手机号3-4-4格式
     * @return
     */
    @Override
    public String getInputPhoneNum() {
        return mDataBinding.loginPhoneNum.getText().toString().replaceAll(" ", "");
    }

    /**
     * 获取前一画面传输的参数
     * @return
     */
    @Override
    public Intent getParamIntent() {
        return getIntent();
    }

}




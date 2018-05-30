package woxingwoxiu.com.login.activity;


import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.inputmethod.InputMethodManager;

import java.util.Timer;
import java.util.TimerTask;

import woxingwoxiu.com.R;
import woxingwoxiu.com.base.BaseActivity;
import woxingwoxiu.com.databinding.ActivityLoginPhoneBinding;
import woxingwoxiu.com.login.contract.LoginPhoneContract;
import woxingwoxiu.com.login.presenter.LoginPhonePresenter;
import woxingwoxiu.com.login.utils.ActivityCollector;
import woxingwoxiu.com.login.utils.ZpPhoneEditText;

/**
 * Created by 丁胜胜 on 2018/05/09.
 * 类描述：手机输入界面
 */
public class LoginPhoneActivity
        extends BaseActivity<ActivityLoginPhoneBinding,LoginPhonePresenter>
        implements LoginPhoneContract.View{
    private ZpPhoneEditText editText;

    /**
     * 页面初始化
     */
    @Override
    protected void initViews() {
        mDataBinding.setLoginPhonePresenter(mPresenter);

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


}




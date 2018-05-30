package woxingwoxiu.com.login.activity;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;

import cn.emay.sdk.client.SmsSDKClient;
import cn.emay.sdk.core.dto.sms.common.ResultModel;
import cn.emay.sdk.core.dto.sms.request.SmsSingleRequest;
import cn.emay.sdk.core.dto.sms.response.SmsResponse;
import cn.emay.sdk.util.exception.SDKParamsException;
import woxingwoxiu.com.R;
import woxingwoxiu.com.base.BaseActivity;
import woxingwoxiu.com.databinding.ActivityLoginVerifyBinding;
import woxingwoxiu.com.login.bean.LoginVerifyBean;
import woxingwoxiu.com.login.contract.LoginVerifyContract;
import woxingwoxiu.com.login.presenter.LoginVerifyPresenter;
import woxingwoxiu.com.login.utils.ActivityCollector;
import woxingwoxiu.com.login.utils.RegisterCodeTimer;
import woxingwoxiu.com.login.utils.VeriftyEditText;

/**
 * Created by 丁胜胜 on 2018/05/11.
 * 类描述：手机验证码界面
 */

public class LoginVerifyActivity
        extends BaseActivity<ActivityLoginVerifyBinding,LoginVerifyPresenter>
        implements LoginVerifyContract.View {

    private RegisterCodeTimer mRegisterCodeTimer;
    private LoginVerifyBean.VerifyBean bean;
    private String TAG = "=== LoginVerifyActivity ===";
    private String  random = null;
    // 验证码
    private VeriftyEditText editText;

    @Override
    protected void initViews() {

        mDataBinding.setVerifyPresenter(mPresenter);
        mDataBinding.loginVerifyNext.setEnabled(false);//初始化按钮不能点击
        mDataBinding.loginVerifyNum.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                mDataBinding.loginVerifyAgree.setChecked(true);//初始化复选框
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mDataBinding.loginVerifyNext.setEnabled(false);//初始化按钮不能点击
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (mDataBinding.loginVerifyNum.getText().length() == 4) {
                    mDataBinding.loginVerifyNext.setEnabled(true);
                }

            }
        });
        mDataBinding.loginVerifyAgree.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked && mDataBinding.loginVerifyNum.getText().length() == 4) {
                    mDataBinding.loginVerifyNext.setEnabled(true);
                } else {
                    mDataBinding.loginVerifyNext.setEnabled(false);//初始化按钮不能点击
                }
            }
        });

        CountDownTimer timer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mDataBinding.tvGetVerify.setEnabled(false);
                mDataBinding.tvGetVerify.setText("重新发送(" + millisUntilFinished / 1000 + ")");
                mDataBinding.tvGetVerify.setTextColor(getResources().getColor(R.color.dim_foreground_dark));
            }

            @Override
            public void onFinish() {
                mDataBinding.tvGetVerify.setEnabled(true);
                mDataBinding.tvGetVerify.setText("获取验证码");
                mDataBinding.tvGetVerify.setTextColor(getResources().getColor(R.color.login_red));
            }
        }.start();

        try {
            sendSingleSms();
        } catch (SDKParamsException e) {
            e.printStackTrace();
        }

        initFieldBeforeMethods();

        SpannableString sp = new SpannableString("同意《我行我秀用户协议》");
        mDataBinding.loginVerifyAgreement.setText(sp);

        // 获取验证码控件
        editText = this.findViewById(R.id.login_verify_num);
        editText.setFocusableInTouchMode(true);
        // 获取焦点
        editText.requestFocus();
        // 弹出软键盘
        Timer editTimer = new Timer();
        editTimer.schedule(new TimerTask() {
            public void run() {
                InputMethodManager inputManager =
                        (InputMethodManager)editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.showSoftInput(editText, 0);
            }
        }, 100);

        ActivityCollector.addActivity(this);//将活动添加到活动收集器
    }

    @Override
    protected LoginVerifyPresenter createPresenter() {
        return new LoginVerifyPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login_verify;
    }


    /**
     * @param context
     * @param bean
     */
    public static void actionStart(Context context, LoginVerifyBean.VerifyBean bean) {
        Intent intent = new Intent(context, LoginVerifyActivity.class);
        intent.putExtra("phoneNumSpace", bean.getPhoneNumSpace());
        intent.putExtra("phoneNum", bean.getPhoneNum());
        context.startActivity(intent);
    }

    protected void initFieldBeforeMethods() {
        Intent intent = getIntent();
        LoginVerifyBean.VerifyBean bean = new LoginVerifyBean.VerifyBean();
        bean.setPhoneNumSpace(intent.getStringExtra("phoneNumSpace"));
        bean.setPhoneNum(intent.getStringExtra("phoneNum"));
        mDataBinding.setNumbean(bean);
    }

    @Override
    public String getInputVerify() {
        return mDataBinding.loginVerifyNum.getText().toString();
    }

    @Override
    public String getVerify() {
        return random;
    }
    @Override
    public void startCountDownTimer() {
        CountDownTimer timer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mDataBinding.tvGetVerify.setEnabled(false);
                mDataBinding.tvGetVerify.setText("重新发送(" + millisUntilFinished / 1000 + ")");
                mDataBinding.tvGetVerify.setTextColor(getResources().getColor(R.color.dim_foreground_dark));
            }

            @Override
            public void onFinish() {
                mDataBinding.tvGetVerify.setEnabled(true);
                mDataBinding.tvGetVerify.setText("获取验证码");
                mDataBinding.tvGetVerify.setTextColor(getResources().getColor(R.color.login_red));
            }
        }.start();
    }

    @Override
    public void sendSingleSms() throws SDKParamsException {
        new Thread(new Runnable(){
            @Override
            public void run() {
                SmsSDKClient client = null;
                try {
                    client = new SmsSDKClient("shmtn.b2m.cn", 80, "EUCP-EMY-SMS1-1ESEE", "2636AF0F6024E649");
                } catch (SDKParamsException e) {
                    e.printStackTrace();
                }
                Intent intent = getIntent();
                random = createRandom(true,4);
                String mobile = intent.getStringExtra("phoneNum");
                String content = "【我行我秀】验证码"+random+"，用于手机登录，5分钟内有效。验证码提供给他人可能导致账号被盗，请勿泄露，谨防被骗。";
                String customSmsId = "1";
                String extendedCode = "01";
                Log.d(TAG,"验证码是："+random);
                SmsSingleRequest request = new SmsSingleRequest(mobile, content, customSmsId, extendedCode, "");
//                ResultModel<SmsResponse> result = client.sendSingleSms(request);
//                if (result.getCode().equals("SUCCESS")) {
//                    System.out.println("请求成功");
//                    SmsResponse response = result.getResult();
//                    System.out.println("sendSingleSms:" + response.toString());
//                } else {
//                    System.out.println("请求失败");
//                }
            }
        }).start();
    }

    /**
     * 创建指定数量的随机字符串
     * @param numberFlag 是否是数字
     * @param length
     * @return 验证码
     */
    public static String createRandom(boolean numberFlag, int length) {
        String retStr = "";
        String strTable = numberFlag ? "1234567890" : "1234567890abcdefghijkmnpqrstuvwxyz";
        int len = strTable.length();
        boolean bDone = true;
        do {
            retStr = "";
            int count = 0;
            for (int i = 0; i < length; i++) {
                //double dblR = Math.random() * len;
                double dblR = ThreadLocalRandom.current().nextDouble()* len;
                int intR = (int) Math.floor(dblR);
                char c = strTable.charAt(intR);
                if (('0' <= c) && (c <= '9')) {
                    count++;
                }
                retStr += strTable.charAt(intR);
            }
            if (count >= 2) {
                bDone = false;
            }
        } while (bDone);
        return retStr;
    }

}



package sdwxwx.com.login.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import cn.emay.sdk.util.StringUtil;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import sdwxwx.com.R;
import sdwxwx.com.base.BaseActivity;
import sdwxwx.com.base.BaseCallback;
import sdwxwx.com.databinding.ActivityLoginVerifyBinding;
import sdwxwx.com.home.bean.CityEntity;
import sdwxwx.com.home.model.PickCityModel;
import sdwxwx.com.login.bean.LoginVerifyBean;
import sdwxwx.com.login.contract.LoginVerifyContract;
import sdwxwx.com.login.presenter.LoginVerifyPresenter;
import sdwxwx.com.login.utils.ActivityCollector;
import sdwxwx.com.login.utils.LoginHelper;
import sdwxwx.com.login.utils.RegisterCodeTimer;
import sdwxwx.com.login.utils.VeriftyEditText;

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

    public LocationClient mLocationClient = null;
    private CityLocationListener myListener = new CityLocationListener();
    String city_id = "0";

//    /**
//     * 初期化
//     * @param savedInstanceState
//     */
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        // 获取前一画面传递的参数
//        Intent intent = getIntent();
//        Bundle bundle = intent.getExtras();
//        // 如果存在传递的参数，则说明是通过第三方登录跳转到绑定手机号画面
//        if (!StringUtil.isEmpty(bundle.getString("qqId"))) {
//            // 更改按钮的内容为完成
//            mDataBinding.loginVerifyNext.setBackgroundResource(R.drawable.login_ok);
//        }
//    }

    @Override
    protected void initViews() {

        mDataBinding.setVerifyPresenter(mPresenter);
        cityLocation();
        mDataBinding.loginVerifyNext.setEnabled(false);//初始化按钮不能点击
        // 在验证码控件追加监听
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
                if (mDataBinding.loginVerifyNum.getText().length() == 6) {
                    mDataBinding.loginVerifyNext.setEnabled(true);
                }

            }
        });
        // 在用户协议除追加监听
        mDataBinding.loginVerifyAgree.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked && mDataBinding.loginVerifyNum.getText().length() == 6) {
                    mDataBinding.loginVerifyNext.setEnabled(true);
                } else {
                    mDataBinding.loginVerifyNext.setEnabled(false);//初始化按钮不能点击
                }
            }
        });

        startCountDownTimer();

        initFieldBeforeMethods();

        //首次获取验证码
        mPresenter.verify();

//        SpannableString sp = new SpannableString("同意《我行我秀用户协议》");
//        mDataBinding.loginVerifyAgreement.setText(sp);

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
        intent.putExtra("qqId",bean.getQqId());
        intent.putExtra("wechatId",bean.getWechatId());
        intent.putExtra("userName",bean.getUserName());
        intent.putExtra("userIcon",bean.getUserIcon());
        intent.putExtra("userGender",bean.getUserGender());
        context.startActivity(intent);
    }

    protected void initFieldBeforeMethods() {
        Intent intent = getIntent();
        bean = new LoginVerifyBean.VerifyBean();
        bean.setPhoneNumSpace(intent.getStringExtra("phoneNumSpace"));
        bean.setPhoneNum(intent.getStringExtra("phoneNum"));
        bean.setQqId(intent.getStringExtra("qqId"));
        bean.setWechatId(intent.getStringExtra("wechatId"));
        bean.setUserName(intent.getStringExtra("userName"));
        bean.setUserIcon(intent.getStringExtra("userIcon"));
        bean.setUserGender(intent.getStringExtra("userGender"));
        // 如果存在传递的参数，则说明是通过第三方登录跳转到绑定手机号画面
        if (!StringUtil.isEmpty(intent.getStringExtra("qqId"))) {
            // 更改按钮的内容为完成
            mDataBinding.loginVerifyNext.setBackgroundResource(R.drawable.login_ok);
        }
        mDataBinding.setNumbean(bean);
    }

    //获取当前手机号
    @Override
    public LoginVerifyBean.VerifyBean getBean() {
        return bean;
    }

    //获取输入的验证码
    @Override
    public int getInputVerify() {
        return Integer.valueOf(mDataBinding.loginVerifyNum.getText().toString());
    }


    @Override
    public String getVerify() {
        return random;
    }

    //验证码倒计时
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

    public class CityLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location){
            final String city = location.getCity();    //获取城市

            PickCityModel model = new PickCityModel();
            model.getCities(new BaseCallback<List<CityEntity>>() {
                @Override
                public void onSuccess(List<CityEntity> data) {
                    //把城市列表放到Application里
                    LoginHelper.getInstance().setCityList(data);
                    for(CityEntity cityEntity:data){
                        if (city.equals(cityEntity.getName())) {
                            city_id = cityEntity.getId();
                        }
                    }
                }

                @Override
                public void onFail(String msg) {
                    showToast(msg);
                }
            });

        }
    }

    @Override
    public String getOnCity_id(){
        return city_id;
    }

    private void cityLocation() {
        RxPermissions permissions = new RxPermissions(this);
        permissions.request(Manifest.permission.ACCESS_FINE_LOCATION)
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }
                    @Override
                    public void onNext(Boolean value) {
                        // 如果已经给予定位的使用权限
                        if(value){
                            // 开始定位
                            startLocation();
                        }else {
                            // 定位不可用
                            showToast("权限已禁止！");
                        }
                    }
                    @Override
                    public void onError(Throwable e) {
                    }
                    @Override
                    public void onComplete() {
                    }
                });
    }

    private void startLocation(){
        mLocationClient = new LocationClient(this);
        //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);
        //注册监听函数
        LocationClientOption option = new LocationClientOption();
        option.setIsNeedAddress(true);
        mLocationClient.setLocOption(option);
        mLocationClient.start();
    }
}



package sdwxwx.com.login.presenter;

import android.util.Log;
import sdwxwx.com.base.BaseCallback;
import sdwxwx.com.base.BasePresenter;
import sdwxwx.com.base.LoginCallback;
import sdwxwx.com.bean.UserBean;
import sdwxwx.com.login.bean.DataEditingBean;
import sdwxwx.com.login.contract.DataEditingContract;
import sdwxwx.com.login.model.DataEditingModel;
import sdwxwx.com.login.model.LoginModel;
import sdwxwx.com.login.utils.ActivityCollector;
import sdwxwx.com.login.utils.LoginHelper;
import sdwxwx.com.util.LoginUtil;

import java.util.HashMap;

/**
 * Created by 丁胜胜 on 2018/05/14.
 */

public class DataEditingPresenter extends BasePresenter<DataEditingContract.View> implements DataEditingContract.Presenter {

    private DataEditingBean dataEditingBean;
    DataEditingModel mModel;
    LoginModel loginModel;
    public String TAG = "DataEditingPresenter";

    @Override
    public void onCross() {
        ActivityCollector.finishAll();
    }

    @Override
    public void onJump() {
        // 保存登录信息
        getView().savaObject();
        // 跳过头像编辑画面，跳转到HOME画面

        dataEditingBean = getView().getBean();

        //注册账号，此时头像昵称为空
        mModel.phoneRegister(dataEditingBean.getMobile(), dataEditingBean.getCode(),
                dataEditingBean.getWechatId(), dataEditingBean.getQqId(), dataEditingBean.getUserName(),
                dataEditingBean.getAvatar(),dataEditingBean.getCity_id(), new BaseCallback<UserBean>() {

                    @Override
                    public void onSuccess(UserBean data) {

                        // 定义参数用MAP
                        final HashMap<String,Object> hashMap = new HashMap<String,Object>(100);
                        // 手机号
                        hashMap.put("mobile", data.getMobile());
                        // 验证码
                        hashMap.put("code", dataEditingBean.getCode());
                        // 微信ID
                        hashMap.put("wechat_id", "0");
                        // QQID
                        hashMap.put("qq_id", "0");

                        // 调用手机号码的登录方法
                        loginModel.loginUser(hashMap,new LoginCallback<UserBean>(){
                            @Override
                            public void onRegister() {

                            }
                            @Override
                            public void onSuccess(UserBean data) {
                                //登录返回data存入UserBean
                                LoginHelper.getInstance().setUserBean(data);
                                Log.i(TAG, "" + data.toString());
                                if (getView() == null) {
                                    return;
                                }
                                //登录返回data存入sharedpreferences
                                LoginUtil.saveLoginInfo(getView().getContext(),data);
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
                    public void onFail(String msg) {
                        if (getView() == null) {
                            return;
                        }
                        getView().showToast(msg);
                    }
                });

    }

    @Override
    public void onClickOk() {

        // 保存登录信息
        getView().savaObject();
        String NickName = getView().getInputUserName();
        dataEditingBean = getView().getBean();

        //注册账号，此时头像昵称不为空
            mModel.phoneHasFileRegister(dataEditingBean.getMobile(), dataEditingBean.getCode(),
                    dataEditingBean.getWechatId(), dataEditingBean.getQqId(), NickName,
                    dataEditingBean.getAvatarFile(),dataEditingBean.getCity_id(), new BaseCallback<UserBean>() {
                        @Override
                        public void onSuccess(UserBean data) {
                            Log.i(TAG, "" + data.toString());
                            // 定义参数用MAP
                            final HashMap<String,Object> hashMap = new HashMap<String,Object>(100);
                            // 手机号
                            hashMap.put("mobile", data.getMobile());
                            // 验证码
                            hashMap.put("code", dataEditingBean.getCode());
                            // 微信ID
                            hashMap.put("wechat_id", "0");
                            // QQID
                            hashMap.put("qq_id", "0");

                            // 调用手机号码的登录方法
                            loginModel.loginUser(hashMap,new LoginCallback<UserBean>(){

                                @Override
                                public void onSuccess(UserBean data) {
                                    //登录返回data存入UserBean
                                    LoginHelper.getInstance().setUserBean(data);
                                    Log.i(TAG, "" + data.toString());
                                    if (getView() == null) {
                                        return;
                                    }
                                    //登录返回data存入sharedpreferences
                                    LoginUtil.saveLoginInfo(getView().getContext(),data);
                                }

                                @Override
                                public void onFail(String msg) {
                                    if (getView() == null) {
                                        return;
                                    }
                                    getView().showToast(msg);
                                }

                                @Override
                                public void onRegister() {

                                }
                            });

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
    public void onClickImage() {
        getView().GetPermission();
    }
}

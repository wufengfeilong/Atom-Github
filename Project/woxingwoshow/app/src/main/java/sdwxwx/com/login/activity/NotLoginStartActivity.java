package sdwxwx.com.login.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import sdwxwx.com.base.BaseCallback;
import sdwxwx.com.bean.UserBean;
import sdwxwx.com.cons.Constant;
import sdwxwx.com.home.HomeActivity;
import sdwxwx.com.login.model.LoginModel;
import sdwxwx.com.login.utils.LoginHelper;
import sdwxwx.com.util.LoginUtil;

import static sdwxwx.com.cons.Constant.SP_FILE_NAME;
import static sdwxwx.com.cons.Constant.SP_LOGIN_TOKEN;

public class NotLoginStartActivity extends AppCompatActivity {

    protected SharedPreferences mSharedPreferences;
    boolean isLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkIsFirst();

    }

    /**
     * 判断APP是否已经登录
     */
    public void checkIsFirst(){

        if (isLogin()) {
//            LoginUtil.getLoginInfo(this);
            LoginModel.getMemberInfo(LoginUtil.getStrUserInfo(Constant.SP_MEMBER_ID, this)
                    , LoginUtil.getStrUserInfo(Constant.SP_MEMBER_ID, this)
                    , new BaseCallback<UserBean>() {
                        @Override
                        public void onSuccess(UserBean data) {
                            LoginHelper.getInstance().setUserBean(data);
                        }

                        @Override
                        public void onFail(String msg) {
                            Toast.makeText(NotLoginStartActivity.this, msg, Toast.LENGTH_SHORT).show();
                        }
                    });
            Intent intent = new Intent(NotLoginStartActivity.this, HomeActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(NotLoginStartActivity.this, NotLoginInterfaceActivity.class);
            startActivity(intent);
        }
        finish();
    }

    public boolean isLogin(){
        mSharedPreferences = getSharedPreferences(SP_FILE_NAME, Context.MODE_PRIVATE);
        String loginToken = mSharedPreferences.getString(SP_LOGIN_TOKEN, null);
        if (loginToken != null) {
            isLogin = true;
        } else {
            isLogin = false;
        }
        return isLogin;
    }

}

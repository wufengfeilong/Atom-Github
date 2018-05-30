package woxingwoxiu.com.base;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import com.android.tu.loadingdialog.LoadingDailog;
import woxingwoxiu.com.cons.Constant;
import woxingwoxiu.com.login.activity.LoginActivity;

import static woxingwoxiu.com.cons.Constant.*;

public abstract class BaseActivity<D extends ViewDataBinding, P extends BasePresenter> extends AppCompatActivity implements BaseView {

    private LoadingDailog mProgressDialog;
    protected SharedPreferences mSharedPreferences;
    boolean isLogin;
    private static void actionStartActivity(Context currentActivity, Class activityClass) {
        Intent intent = new Intent(currentActivity, activityClass);
        currentActivity.startActivity(intent);
    }

    /**
     * Activity的DataBinding对象
     */
    protected D mDataBinding;

    protected P mPresenter;

    protected abstract void initViews();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_base);
        mDataBinding = setContentView();
        mSharedPreferences = getSharedPreferences(SP_FILE_NAME, Context.MODE_PRIVATE);
        LoadingDailog.Builder builder = new LoadingDailog.Builder(this)
                .setMessage("加载中...")
                .setCancelable(false)
                .setCancelOutside(false);
        mProgressDialog = builder.create();
        mPresenter = createPresenter();
        mPresenter.attachView(this);
        initViews();

    }

    private boolean intentInterception(String clsName) {
        boolean isLoginClass = false;
        for (int i = 0; i < mInterClsName.length; i++) {
            if (clsName.equals(mInterClsName[i])) {
                isLoginClass = true;
            }
        }
        if (isLoginClass && !isLogin()) {
            return true;
        }
        return false;
    }
    public boolean isLogin(){
        String loginToken = mSharedPreferences.getString(SP_LOGIN_TOKEN, null);
        if (loginToken != null) {
            isLogin = true;
        } else {
            isLogin = false;
        }
        return isLogin;
    }

    /**
     * 页面跳转
     *
     * @param cls
     */
    public void actionStartActivity(Class cls) {
        if (intentInterception(cls.getSimpleName())) {
            actionStartActivity(BaseActivity.this, LoginActivity.class);
        } else {
            actionStartActivity(BaseActivity.this, cls);
        }
    }

    /**
     * 带参数页面跳转
     * @param cls
     */
    public void paramStartActivity(Class cls,String param) {
        if (intentInterception(cls.getSimpleName())) {
            actionStartActivity(this, LoginActivity.class);
        } else {
            Intent i = new Intent(this,cls);
            i.putExtra(Constant.INTENT_PARAM, param);
            startActivity(i);
        }
    }

    /**
     * 画面关闭
     */
    public void closeActivity() {
        finish();
    }

    /**
     * 通过DataBindingUtils填充页面布局并返回数据绑定对象
     *
     * @return 当前页面数据绑定对象
     */
    protected D setContentView() {
        if (mDataBinding == null)
            return DataBindingUtil.setContentView(this, getLayoutId());
        return mDataBinding;
    }

    protected abstract P createPresenter();

    @LayoutRes
    protected abstract int getLayoutId();

    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public Context getContext() {
        return BaseActivity.this;
    }

    @Override
    public void showLoading() {
        if (!mProgressDialog.isShowing()) {
            mProgressDialog.show();
        }
    }

    @Override
    public void hideLoading() {
        if (mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) mPresenter.detachView();
    }
}

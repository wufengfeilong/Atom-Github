package woxingwoxiu.com.base;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.android.tu.loadingdialog.LoadingDailog;
import woxingwoxiu.com.cons.Constant;
import woxingwoxiu.com.login.activity.LoginActivity;

import static woxingwoxiu.com.cons.Constant.*;

/**
 * create by 860115039
 * date      2018/5/7
 * time      16:34
 */
public abstract class BaseFragment<D extends ViewDataBinding, P extends BasePresenter>  extends Fragment implements BaseView {

    private LoadingDailog mProgressDialog;
    /**
     * Activity的DataBinding对象
     */
    protected D mDataBinding;

    protected P mPresenter;

    protected Context mContext;

    protected SharedPreferences mSharedPreferences;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mDataBinding = DataBindingUtil.inflate(inflater, getLayoutRes(), container, false);
        mPresenter = createPresenter();
        mPresenter.attachView(this);

        this.mContext = getActivity();
        LoadingDailog.Builder builder = new LoadingDailog.Builder(mContext)
                .setMessage("加载中...")
                .setCancelable(false)
                .setCancelOutside(false);
        mProgressDialog = builder.create();
        mSharedPreferences =mContext.getSharedPreferences(SP_FILE_NAME,Context.MODE_PRIVATE);
                initViews();
        return mDataBinding.getRoot();

    }

    private boolean intentInterception(String clsName){
        boolean isLogin = false;
        boolean isLoginClass = false;
        for(int i=0;i< mInterClsName.length;i++){
            if (clsName.equals(mInterClsName[i])) {
                isLoginClass = true;
            }
        }
        String loginToken = mSharedPreferences.getString(SP_LOGIN_TOKEN,null);
        if (loginToken != null) {
            isLogin = true;
        }
        if (isLoginClass&&!isLogin) {
            return true;
        }
        return false;
    }


    @LayoutRes
    protected abstract int getLayoutRes();

    protected abstract P createPresenter();

    protected abstract void initViews();

    @Override
    public void showLoading() {
        checkActivityAttached();
        if (!mProgressDialog.isShowing()) {
            mProgressDialog.show();
        }

    }

    @Override
    public void hideLoading() {
        checkActivityAttached();
        if (mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }

    protected boolean isAttachedContext(){
        return mContext != null;
    }

    /**
     * 检查activity连接情况
     */
    public void checkActivityAttached() {
        if (getActivity() == null) {
            throw new ActivityNotAttachedException();
        }
    }

    public static class ActivityNotAttachedException extends RuntimeException {
        public ActivityNotAttachedException() {
            super("Fragment has disconnected from Activity ! - -.");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) mPresenter.detachView();
    }

    /**
     * 页面跳转
     * @param cls
     */
    public void actionStartActivity(Class cls) {
        if (intentInterception(cls.getSimpleName())) {
            actionStartActivity(mContext, LoginActivity.class);
        } else {
            actionStartActivity(mContext, cls);
        }
    }

    /**
     * 带参数页面跳转
     * @param cls
     */
    public void paramStartActivity(Class cls,String param) {
        if (intentInterception(cls.getSimpleName())) {
            actionStartActivity(mContext, LoginActivity.class);
        } else {
            Intent i = new Intent(mContext,cls);
            i.putExtra(Constant.INTENT_PARAM, param);
            mContext.startActivity(i);
        }
    }




    private static void actionStartActivity(Context currentActivity, Class activityClass) {
        Intent intent = new Intent(currentActivity, activityClass);
        currentActivity.startActivity(intent);
    }

    /**
     * 画面关闭
     */
    public void closeActivity() {
        getActivity().finish();
    }
}

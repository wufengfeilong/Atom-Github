package woxingwoxiu.com.me.activity;

import android.content.Context;
import android.content.Intent;

import woxingwoxiu.com.R;
import woxingwoxiu.com.base.BaseActivity;
import woxingwoxiu.com.databinding.ActivityNameCertifyBinding;
import woxingwoxiu.com.me.contract.NameCertifyContract;
import woxingwoxiu.com.me.presenter.NameCertifyPresenter;


public class NameCertifyActivity extends BaseActivity<ActivityNameCertifyBinding, NameCertifyPresenter> implements NameCertifyContract.View {

    /**
     * 启动当前的Activity
     * @param context
     */
    public static void actionStart(Context context) {
        Intent intent = new Intent(context, NameCertifyActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected NameCertifyPresenter createPresenter() {
        return new NameCertifyPresenter();
    }

    @Override
    protected void initViews() {
        mDataBinding.setPresenter(mPresenter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_name_certify;
    }
}

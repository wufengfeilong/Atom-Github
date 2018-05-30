package woxingwoxiu.com.me.activity;

import android.content.Context;
import android.content.Intent;

import woxingwoxiu.com.R;
import woxingwoxiu.com.base.BaseActivity;
import woxingwoxiu.com.databinding.ActivityCertificationInfoBinding;
import woxingwoxiu.com.me.contract.CertificationInfoContract;
import woxingwoxiu.com.me.presenter.CertificationInfoPresenter;

// TODO 废弃必要
public class CertificationInfoActivity extends BaseActivity<ActivityCertificationInfoBinding, CertificationInfoPresenter> implements CertificationInfoContract.View {

    /**
     * 启动当前的Activity
     * @param context
     */
    public static void actionStart(Context context) {
        Intent intent = new Intent(context, CertificationInfoActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected CertificationInfoPresenter createPresenter() {
        return new CertificationInfoPresenter();
    }

    @Override
    protected void initViews() {
        mDataBinding.setPresenter(mPresenter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_certification_info;
    }
}

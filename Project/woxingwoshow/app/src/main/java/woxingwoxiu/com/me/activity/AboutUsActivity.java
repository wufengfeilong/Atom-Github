package woxingwoxiu.com.me.activity;

import woxingwoxiu.com.R;
import woxingwoxiu.com.base.BaseActivity;
import woxingwoxiu.com.databinding.ActivityAboutUsBinding;
import woxingwoxiu.com.me.contract.AboutUsContract;
import woxingwoxiu.com.me.presenter.AboutUsPresenter;


public class AboutUsActivity extends BaseActivity<ActivityAboutUsBinding, AboutUsPresenter> implements AboutUsContract.View {

    @Override
    protected AboutUsPresenter createPresenter() {
        return new AboutUsPresenter();
    }

    @Override
    protected void initViews() {
        mDataBinding.setPresenter(mPresenter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_about_us;
    }
}

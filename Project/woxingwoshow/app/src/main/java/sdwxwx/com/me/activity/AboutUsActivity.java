package sdwxwx.com.me.activity;

import sdwxwx.com.R;
import sdwxwx.com.base.BaseActivity;
import sdwxwx.com.databinding.ActivityAboutUsBinding;
import sdwxwx.com.me.bean.AboutBean;
import sdwxwx.com.me.contract.AboutUsContract;
import sdwxwx.com.me.presenter.AboutUsPresenter;


public class AboutUsActivity extends BaseActivity<ActivityAboutUsBinding, AboutUsPresenter> implements AboutUsContract.View {

    @Override
    protected AboutUsPresenter createPresenter() {
        return new AboutUsPresenter();
    }

    @Override
    protected void initViews() {
        mDataBinding.setPresenter(mPresenter);
        mPresenter.initView();

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_about_us;
    }

    @Override
    public void setAboutUsBean(AboutBean bean){
        mDataBinding.setBean(bean);
    }
}

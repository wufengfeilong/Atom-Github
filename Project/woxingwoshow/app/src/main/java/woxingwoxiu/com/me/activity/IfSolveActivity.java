package woxingwoxiu.com.me.activity;

import woxingwoxiu.com.R;
import woxingwoxiu.com.base.BaseActivity;
import woxingwoxiu.com.databinding.ActivityIfSolveBinding;
import woxingwoxiu.com.me.contract.IfSolveContract;
import woxingwoxiu.com.me.presenter.IfSolvePresenter;

public class IfSolveActivity extends BaseActivity<ActivityIfSolveBinding,IfSolvePresenter> implements IfSolveContract.View {

    @Override
    protected void initViews() {

    }

    @Override
    protected IfSolvePresenter createPresenter() {
        return new IfSolvePresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_if_solve;
    }
}

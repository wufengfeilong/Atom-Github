package woxingwoxiu.com.letter.activity;

import woxingwoxiu.com.R;
import woxingwoxiu.com.base.BaseActivity;
import woxingwoxiu.com.databinding.ActivityLetterSettingBinding;
import woxingwoxiu.com.letter.contract.LetterSettingContract;
import woxingwoxiu.com.letter.presenter.LetterSettingPresenter;

public class LetterSettingActivity extends BaseActivity<ActivityLetterSettingBinding,LetterSettingPresenter> implements LetterSettingContract.View {

    @Override
    protected LetterSettingPresenter createPresenter() {
        return new LetterSettingPresenter();
    }

    @Override
    protected void initViews() {
        mDataBinding.setPresenter(mPresenter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_letter_setting;
    }

    @Override
    public void finishThis() {
        finish();
    }
}

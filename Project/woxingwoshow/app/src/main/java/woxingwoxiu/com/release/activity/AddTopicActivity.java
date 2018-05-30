package woxingwoxiu.com.release.activity;

import woxingwoxiu.com.R;
import woxingwoxiu.com.base.BaseActivity;
import woxingwoxiu.com.databinding.AddTopicActivityBinding;
import woxingwoxiu.com.release.contract.AddTopicContract;
import woxingwoxiu.com.release.presenter.AddTopicPresenter;

public class AddTopicActivity extends BaseActivity<AddTopicActivityBinding, AddTopicPresenter>
        implements AddTopicContract.View {

    @Override
    protected void initViews() {

    }

    @Override
    protected AddTopicPresenter createPresenter() {
        return new AddTopicPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.add_topic_activity;
    }
}

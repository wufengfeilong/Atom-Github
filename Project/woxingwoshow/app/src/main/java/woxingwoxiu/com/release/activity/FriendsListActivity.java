package woxingwoxiu.com.release.activity;

import woxingwoxiu.com.R;
import woxingwoxiu.com.base.BaseActivity;
import woxingwoxiu.com.databinding.FriendsListActivityBinding;
import woxingwoxiu.com.release.contract.FriendsListContract;
import woxingwoxiu.com.release.presenter.FriendsListPresenter;

public class FriendsListActivity extends BaseActivity<FriendsListActivityBinding, FriendsListPresenter>
        implements FriendsListContract.View {

    @Override
    protected void initViews() {
        mDataBinding.setTitle(getString(R.string.release_music_title_tx));
        mDataBinding.setPresenter(mPresenter);

    }

    @Override
    protected FriendsListPresenter createPresenter() {
        return new FriendsListPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.friends_list_activity;
    }
}

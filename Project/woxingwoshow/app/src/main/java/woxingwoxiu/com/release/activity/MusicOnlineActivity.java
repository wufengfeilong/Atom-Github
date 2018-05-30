package woxingwoxiu.com.release.activity;

import android.view.View;

import woxingwoxiu.com.R;
import woxingwoxiu.com.base.BaseActivity;
import woxingwoxiu.com.databinding.MusicOnlineActivityBinding;
import woxingwoxiu.com.release.contract.MusicOnlineContract;
import woxingwoxiu.com.release.presenter.MusicOnlinePresenter;

public class MusicOnlineActivity extends BaseActivity<MusicOnlineActivityBinding, MusicOnlinePresenter>
        implements MusicOnlineContract.View {

    @Override
    protected void initViews() {

        mDataBinding.setTitle(getString(R.string.release_music_title_tx));
        mDataBinding.setPresenter(mPresenter);
    }

    @Override
    protected MusicOnlinePresenter createPresenter() {
        return new MusicOnlinePresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.music_online_activity;
    }

    public void onMenuClick(View view) {
        switch (view.getId()) {
            case R.id.release_back_btn:
                super.onBackPressed();
                break;
            default:
                break;
        }
    }

}

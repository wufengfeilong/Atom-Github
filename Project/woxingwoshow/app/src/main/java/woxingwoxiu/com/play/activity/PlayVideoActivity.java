package woxingwoxiu.com.play.activity;

import android.support.v4.app.Fragment;
import android.view.WindowManager;
import woxingwoxiu.com.R;
import woxingwoxiu.com.base.BaseActivity;
import woxingwoxiu.com.databinding.ActivityPlayVideoBinding;
import woxingwoxiu.com.message.activity.MessageFriendInformationActivity;
import woxingwoxiu.com.play.adapter.PlayVideoFragmentAdapter;
import woxingwoxiu.com.play.contract.PlayVideoContract;
import woxingwoxiu.com.play.fragment.PlayVideoFragment;
import woxingwoxiu.com.play.presenter.PlayVideoPresenter;
import woxingwoxiu.com.release.utils.ViewUtils;
import woxingwoxiu.com.widget.VerticalViewPager;

import java.util.ArrayList;
import java.util.List;

public class PlayVideoActivity extends BaseActivity<ActivityPlayVideoBinding, PlayVideoPresenter>
        implements PlayVideoContract.View,VerticalViewPager.OnSwipeListener {
    List<String> mUrlList = new ArrayList<>();
    List<Fragment> fragments = new ArrayList<>();
    PlayVideoFragmentAdapter mAdapter;
    VerticalViewPager mVvp;


    @Override
    protected void initViews() {
        ViewUtils.setImgTransparent(this);
        int flag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
        //设置当前窗体为全屏显示
        getWindow().setFlags(flag, flag);
        mDataBinding.setPresenter(mPresenter);
//        mUrlList.add("http://ks3-cn-beijing.ksyun.com/woxingwoxiu/%E4%B8%89%E5%8F%A5%E5%8D%8A.mp4");
        mUrlList.add("http://ksy.fffffive.com/mda-hinp1ik37b0rt1mj/mda-hinp1ik37b0rt1mj.mp4");
        mUrlList.add("http://ksy.fffffive.com/mda-himtqzs2un1u8x2v/mda-himtqzs2un1u8x2v.mp4");
        mUrlList.add("http://ksy.fffffive.com/mda-himek207gvvqg3wq/mda-himek207gvvqg3wq.mp4");
        mUrlList.add("http://ksy.fffffive.com/mda-hfrigfn2y9jvzm72/mda-hfrigfn2y9jvzm72.mp4");
        mUrlList.add("http://ksy.fffffive.com/mda-hhzeh4c05ivmtiv7/mda-hhzeh4c05ivmtiv7.mp4");
        for (int i=0;i<mUrlList.size();i++) {
//            fragments.add(PlayVideoFragment.newInstance(i+""));
            fragments.add(PlayVideoFragment.newInstance(mUrlList.get(i)));
        }
        mVvp = mDataBinding.playVideoVvp;
        mAdapter = new PlayVideoFragmentAdapter(getSupportFragmentManager(),fragments);
        mVvp.setAdapter(mAdapter);
//        mVvp.setOffscreenPageLimit(0);
//        mDataBinding.playVideoVvp.setPageTransformer(false, new DefaultTransformer());
//        mDataBinding.playVideoVvp.setOverScrollMode(View.OVER_SCROLL_NEVER);
        mVvp.setOnSwipeListener(this);


    }

    @Override
    protected PlayVideoPresenter createPresenter() {
        return new PlayVideoPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_play_video;
    }

    @Override
    public void onLeftSwipe() {
//        mDataBinding.playVideoVvp.getCurrentItem();
        paramStartActivity(MessageFriendInformationActivity.class,null);
//        paramStartActivity(LoginActivity.class,null);
    }

    @Override
    public void onRightSwipe() {
        closeActivity();
    }

}

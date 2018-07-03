package sdwxwx.com.play.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.WindowManager;
import sdwxwx.com.R;
import sdwxwx.com.base.BaseActivity;
import sdwxwx.com.cons.Constant;
import sdwxwx.com.databinding.ActivityPlayVideoBinding;
import sdwxwx.com.home.bean.PlayVideoBean;
import sdwxwx.com.login.activity.LoginActivity;
import sdwxwx.com.login.utils.LoginHelper;
import sdwxwx.com.message.activity.FansHomeActivity;
import sdwxwx.com.play.adapter.PlayVideoFragmentAdapter;
import sdwxwx.com.play.contract.PlayVideoContract;
import sdwxwx.com.play.fragment.PlayVideoFragment;
import sdwxwx.com.play.presenter.PlayVideoPresenter;
import sdwxwx.com.release.utils.ViewUtils;
import sdwxwx.com.widget.VerticalViewPager;

import java.util.ArrayList;
import java.util.List;

public class PlayVideoActivity extends BaseActivity<ActivityPlayVideoBinding, PlayVideoPresenter>
        implements PlayVideoContract.View,VerticalViewPager.OnSwipeListener {

    List<Fragment> fragments = new ArrayList<>();
    PlayVideoFragmentAdapter mAdapter;
    VerticalViewPager mVvp;
    LoginHelper mHelper;
    int type;
    int loadType;
    boolean misScrolled;
    int size;
    LoadMoreEndReceiver mReceiver;

    @Override
    protected void initViews() {
        ViewUtils.setImgTransparent(this);
        int flag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
        //设置当前窗体为全屏显示
        getWindow().setFlags(flag, flag);
        mDataBinding.setPresenter(mPresenter);
        mHelper = LoginHelper.getInstance();
        int pos = Integer.parseInt(getIntent().getStringExtra(Constant.INTENT_PARAM));
        type = getIntent().getIntExtra(Constant.INTENT_PARAM_ONE,0);
        loadType = getIntent().getIntExtra(Constant.INTENT_PARAM_TWO,0);
        //非登录和登陆首页的视频列表
        if (type==0) {
            size = mHelper.getPlayVideoListSize();
            //附近的视频列表
        } else if (type==1) {
            size = mHelper.getNearVideoListSize();
            //话题的视频列表
        } else if (type==2) {
            size = mHelper.getTopicVideoListSize();
            //个人自己的视频列表
        } else if (type==3) {
            size = mHelper.getOwnerVideoListSize();
            //个人点赞的视频列表
        } else if (type==4) {
            size = mHelper.getLikeVideoListSize();
            //粉丝详情自己的视频列表
        } else if (type==5) {
            size = mHelper.getFansListSize();
        }


        for (int i=0;i<size;i++) {
//            fragments.add(PlayVideoFragment.newInstance(i+""));
            fragments.add(PlayVideoFragment.newInstance(i,type));
        }
        mVvp = mDataBinding.playVideoVvp;
        mAdapter = new PlayVideoFragmentAdapter(getSupportFragmentManager(),fragments);
        mVvp.setAdapter(mAdapter);
//        mVvp.setOffscreenPageLimit(0);
//        mDataBinding.playVideoVvp.setPageTransformer(false, new DefaultTransformer());
//        mDataBinding.playVideoVvp.setOverScrollMode(View.OVER_SCROLL_NEVER);
        mVvp.setOnSwipeListener(this);
        mVvp.setCurrentItem(pos);
        mVvp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                switch (state) {
                    case ViewPager.SCROLL_STATE_DRAGGING:
                        misScrolled = false;
                        break;
                    case ViewPager.SCROLL_STATE_SETTLING:
                        misScrolled = true;
                        break;
                    case ViewPager.SCROLL_STATE_IDLE:
                        if (mVvp.getCurrentItem() == mVvp.getAdapter().getCount() - 1 && !misScrolled) {
                            showLoading();
                            IntentFilter filter = new IntentFilter();
                            filter.addAction("com.sdwxwx.load.video.list.end");
                            //注册我的广播
                            mReceiver = new LoadMoreEndReceiver();
                            registerReceiver(mReceiver, filter);
//                            LocalBroadcastManager.getInstance(getContext()).registerReceiver(mReceiver, filter);
                            //非登录和登陆首页的视频列表
                            Intent intent = new Intent();
                            if (type == 0) {
                                intent.setAction("com.sdwxwx.load.play.video.list");
                                intent.putExtra("load_type",loadType);
                                //附近的视频列表
                            } else if (type == 1) {
                                intent.setAction("com.sdwxwx.load.near.video.list");
                                //话题的视频列表
                            } else if (type == 2) {
                                intent.setAction("com.sdwxwx.load.topic.video.list");
                                //个人自己的视频列表
                            } else if (type == 3) {
                                intent.setAction("com.sdwxwx.load.owner.video.list");
                                intent.putExtra("load_type",0);
                                //个人点赞的视频列表
                            } else if (type == 4) {
                                intent.setAction("com.sdwxwx.load.owner.video.list");
                                intent.putExtra("load_type",1);
                                //粉丝详情自己的视频列表
                            } else if (type == 5) {
                                intent.setAction("com.sdwxwx.load.fans.video.list");
                            }
                            sendBroadcast(intent);
//                            LocalBroadcastManager.getInstance(getContext()).sendBroadcast(intent);
                        }
                        misScrolled = true;
                        break;
                }
            }
        });

    }

    //接收删除回话的广播
    public class LoadMoreEndReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            hideLoading();
            int newSize = size;
            //非登录和登陆首页的视频列表
            if (type==0) {
                newSize = mHelper.getPlayVideoListSize();
                //附近的视频列表
            } else if (type==1) {
                newSize = mHelper.getNearVideoListSize();
                //话题的视频列表
            } else if (type==2) {
                newSize = mHelper.getTopicVideoListSize();
                //个人自己的视频列表
            } else if (type==3) {
                newSize = mHelper.getOwnerVideoListSize();
                //个人点赞的视频列表
            } else if (type == 4) {
                newSize = mHelper.getLikeVideoListSize();
            } else if (type == 5) {
                newSize = mHelper.getFansListSize();
            }
            for (int i=size;i<newSize;i++) {
                fragments.add(PlayVideoFragment.newInstance(i,type));
            }
            mAdapter.notifyDataSetChanged();
            mVvp.setCurrentItem(size);
            size = newSize;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mReceiver!=null) {
            unregisterReceiver(mReceiver);
        }
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
        List<PlayVideoBean> list = new ArrayList<>();
        if (type==0) {
            list = mHelper.getPlayVideoList();
            //附近的视频列表
        } else if (type==1) {
            list = mHelper.getNearList();
            //话题的视频列表
        } else if (type==2) {
            list = mHelper.getTopicList();
            //个人和粉丝详情自己的视频列表
        } else if (type==3) {
            list = mHelper.getOwnerList();
            //个人和粉丝详情点赞的视频列表
        } else if (type==4) {
            list = mHelper.getLikeList();
        } else if (type==5) {
            list = mHelper.getFansList();
        }
//        mDataBinding.playVideoVvp.getCurrentItem();
        if (!LoginHelper.getInstance().isOnline()) {
            actionStartActivity(LoginActivity.class);
            return;
        }
        if (loadType != 10) {
            paramStartActivity(FansHomeActivity.class,list.get(mVvp.getCurrentItem()).getMember_id()+"");
        }
//        paramStartActivity(LoginActivity.class,null);
    }

    @Override
    public void onRightSwipe() {
        closeActivity();
    }

}

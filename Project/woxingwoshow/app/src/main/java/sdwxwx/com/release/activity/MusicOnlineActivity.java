package sdwxwx.com.release.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.tbruyelle.rxpermissions2.RxPermissions;
import io.reactivex.functions.Consumer;
import sdwxwx.com.R;
import sdwxwx.com.adapter.BaseAdapter;
import sdwxwx.com.adapter.BaseHolder;
import sdwxwx.com.base.BaseActivity;
import sdwxwx.com.base.BaseCallback;
import sdwxwx.com.bean.MusicBean;
import sdwxwx.com.bean.MusicTypeBean;
import sdwxwx.com.cons.Constant;
import sdwxwx.com.databinding.MusicOnlineActivityBinding;
import sdwxwx.com.login.utils.LoginHelper;
import sdwxwx.com.release.adapter.ReleaseMusicOnlineAdapter;
import sdwxwx.com.release.contract.MusicOnlineContract;
import sdwxwx.com.release.fragment.MusicOnlineFragment;
import sdwxwx.com.release.model.MusicModel;
import sdwxwx.com.release.presenter.MusicOnlinePresenter;
import sdwxwx.com.util.StringUtil;
import sdwxwx.com.widget.ScrollViewPager;
import sdwxwx.com.widget.TabLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MusicOnlineActivity extends BaseActivity<MusicOnlineActivityBinding, MusicOnlinePresenter>
        implements MusicOnlineContract.View,TabLayout.OnTabSelectedListener,BaseAdapter.OnItemClickListener {
    //定义适配器
    private MusicOnlineAdapter mMusicOnlineAdapter;
    private ReleaseMusicOnlineAdapter mReleaseOnlineAdapter;
    //搜索音乐结果的Adapter
    SearchMusicAdapter mSearchMusicAdapter;

    List<MusicBean> mSearchMusicList = new ArrayList<>();
    private MediaPlayer mMediaPlayer;
    private AlertDialog.Builder mDialog;
    private RxPermissions mPermissions;
    private String[] mRequestPermissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};

    //定义音乐类型的list，type数量<=8时为mHideList
    List<MusicTypeBean> mList =new ArrayList<>();
    List<MusicTypeBean> mHideList =new ArrayList<>();

    private RecyclerView mRecyclerView;
    FragmentManager fm;
    List<Fragment> list;
    private TabLayout tabLayout;
    private ScrollViewPager viewPager;
    private List<String> tabs = new ArrayList<>();
    private List<Fragment> fragments = new ArrayList<>();
    boolean isHide = true;
    private static final int SUCCESSCODE = 100;

    @Override
    protected void initViews() {
        //设定页面title
        mDataBinding.setTitle(getString(R.string.release_music_title_tx));
        mDataBinding.setPresenter(mPresenter);

        //RecyclerView（音乐类型）的设置
        mDataBinding.releaseOnlineMusicRecycler.setLayoutManager(new GridLayoutManager(MusicOnlineActivity.this, 4));
        mPresenter.loadListData();
        mMusicOnlineAdapter = new MusicOnlineAdapter(mHideList);
        mDataBinding.releaseOnlineMusicRecycler.setAdapter(mMusicOnlineAdapter);

        //ScrollViewPager
        tabLayout = mDataBinding.releaseOnlineMusicTl;
        viewPager = mDataBinding.releaseOnlineMusicVp;
        initTabs();
        if(fragments.size()<=0){
            //设置TabLayout点击事件
            for (int i = 0; i < tabs.size(); i++) {
                fragments.add(MusicOnlineFragment.newInstance(i));
            }
        }
        tabLayout.addOnTabSelectedListener(this);
        mReleaseOnlineAdapter = new ReleaseMusicOnlineAdapter(getSupportFragmentManager(), tabs, fragments);
        viewPager.setAdapter(mReleaseOnlineAdapter);
        tabLayout.setupWithViewPager(viewPager);
        /*设置搜索结果adapter*/
        mDataBinding.searchMusicLayoutTwo.setLayoutManager(new LinearLayoutManager(MusicOnlineActivity.this));
        editTextChange();
        //播放音乐
        mPermissions = new RxPermissions(this);
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);// 设置媒体流类型
        mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.start();
            }
        });
        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                for (MusicBean bean : mSearchMusicList) {
                    bean.setPlay(false);
                }
                mMusicOnlineAdapter.notifyDataSetChanged();
            }
        });
        mDataBinding.musicSearchLayoutOne.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY > oldScrollY) {
                    // 向下滑动
                }

                if (scrollY < oldScrollY) {
                    // 向上滑动
                }

                if (scrollY == 0) {
                    // 顶部
                }

                if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
                    // 底部
                    ((MusicOnlineFragment)fragments.get(viewPager.getCurrentItem())).loadMoreData(viewPager.getCurrentItem());
                }
            }
        });
    }
    //搜索音乐的Adapter
    public class SearchMusicAdapter extends BaseAdapter<MusicBean> {
        public SearchMusicAdapter(List<MusicBean> list) {
            super(R.layout.item_music_collection_activity, list);
        }

    @Override
    protected void convert(final BaseHolder holder, final MusicBean item) {
        holder.setText(R.id.music_collection_name, item.getTitle());
        holder.setText(R.id.music_collection_author, item.getArtist());
        holder.setText(R.id.music_collection_time, StringUtil.formatSecond(item.getDuration()));
        if (item.isPlay()) {
            Glide.with(getContext()).load(R.drawable.stop_camera).into((ImageView) holder.getView(R.id.music_collection_play));
        } else {
            Glide.with(getContext()).load(R.drawable.start_camera).into((ImageView) holder.getView(R.id.music_collection_play));
        }
        //音乐封面加载异常时，显示默认图片
        RequestOptions options = new RequestOptions().error(R.drawable.music_cover);
        Glide.with(getContext()).load(item.getCover_url()).apply(options).into((ImageView)holder.getView(R.id.music_collection_cover));
//              Glide.with(mContext).load(item.getCover_url()).into((ImageView)holder.getView(R.id.music_collection_cover));
        ((ImageView) holder.getView(R.id.music_collection_cover)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO 点击播放或者暂停音乐
                if (item.isPlay()) {
                    mMediaPlayer.stop();
                    item.setPlay(false);
                    Glide.with(getContext()).load(R.drawable.start_camera).into((ImageView) holder.getView(R.id.music_collection_play));
                    return;
                }
                notifyDataSetChanged();
                try {
                    if (mMediaPlayer != null) {
                        mMediaPlayer.stop();
                    }
                    mMediaPlayer.reset();
                    mMediaPlayer.setDataSource(item.getMusic_url());
                    mMediaPlayer.prepareAsync();
                    for (MusicBean bean : mSearchMusicList) {
                        bean.setPlay(false);
                    }
                    item.setPlay(true);
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        });
        //收藏音乐
        if ("0".equals(item.getIs_favorited())) {
            holder.setImageResource(R.id.music_collection, R.drawable.unfavorited_collection_music);
        } else if ("1".equals(item.getIs_favorited())) {
            holder.setImageResource(R.id.music_collection, R.drawable.favorited_collection_music);
        }
        ((ImageView) holder.getView(R.id.music_collection)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ("0".equals(item.getIs_favorited())) {
                    MusicModel.favoriteMusic(LoginHelper.getInstance().getUserId(), item.getId(), new BaseCallback<String>() {
                        @Override
                        public void onSuccess(String data) {
                            item.setIs_favorited("1");
                            showToast("音乐收藏成功 到我的收藏查看");
                            notifyDataSetChanged();
                        }

                        @Override
                        public void onFail(String msg) {
                        }
                    });
                } else {
                    holder.setImageResource(R.id.music_collection, R.drawable.unfavorited_collection_music);
                    MusicModel.unfavoriteMusic(LoginHelper.getInstance().getUserId(), item.getId(), new BaseCallback<String>() {
                        @Override
                        public void onSuccess(String data) {
                            item.setIs_favorited("0");
                            showToast("取消收藏");
                            notifyDataSetChanged();
                        }

                        @Override
                        public void onFail(String msg) {
                        }
                    });
                }

            }
        });
    }
}
    public void editTextChange() {

        mDataBinding.searchMusicTitleEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {

                //文字输入后的状态
                String afterText = mDataBinding.searchMusicTitleEt.getText().toString().trim();
                if (afterText.isEmpty()) {
                    //输入框为空的状态
                    NestedScrollView searchLayoutOne = (NestedScrollView) mDataBinding.musicSearchLayoutOne;
                    searchLayoutOne.setVisibility(View.VISIBLE);
                    LinearLayout noAnswer = (LinearLayout) mDataBinding.musicNoAnswer;
                    noAnswer.setVisibility(View.GONE);
                    RecyclerView searchLayoutTwo = (RecyclerView) mDataBinding.searchMusicLayoutTwo;
                    searchLayoutTwo.setVisibility(View.GONE);
                } else {
                    mPresenter.isMusicHave(afterText);
                }
            }
        });
    }
    private void initTabs() {
        tabs.add("热门歌曲");
        tabs.add("我的收藏");
    }

    private void initFragment() {
        fm = getSupportFragmentManager();
        list = new ArrayList<>();
        MusicOnlineFragment mMusicOnlineFragment = MusicOnlineFragment.newInstance(1);
        list.add(mMusicOnlineFragment);
    }
    @Override
    protected MusicOnlinePresenter createPresenter() {
        return new MusicOnlinePresenter();
    }
    //    加载页面布局
    @Override
    protected int getLayoutId() {
        return R.layout.music_online_activity;
    }
    //   页面title的返回按钮
    public void onMenuClick(View view) {
        switch (view.getId()) {
            case R.id.release_back_btn:
                super.onBackPressed();
                break;
            default:
                break;
        }
    }
    public void onShowMore(View v){
        if (isHide) {
            //收起
            mMusicOnlineAdapter = new MusicOnlineAdapter(mList);
            mDataBinding.releaseOnlineMusicRecycler.setAdapter(mMusicOnlineAdapter);
//            mMusicOnlineAdapter.notifyDataSetChanged();
            mDataBinding.releaseOnlineMusicTypeMore.setText("收起");
            mDataBinding.releaseOnlineMusicTypeUrl.setImageResource(R.drawable.more_up);
            isHide =false;
        }else{
            // 更多
            mDataBinding.releaseOnlineMusicTypeMore.setText("更多");
            mDataBinding.releaseOnlineMusicTypeUrl.setImageResource(R.drawable.more_down);
            isHide =true;
            mMusicOnlineAdapter = new MusicOnlineAdapter(mHideList);
            mDataBinding.releaseOnlineMusicRecycler.setAdapter(mMusicOnlineAdapter);
//            mMusicOnlineAdapter.notifyDataSetChanged();
        }
        mMusicOnlineAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                Intent i = new Intent(MusicOnlineActivity.this, MusicOnlineTypeActivity.class);
                i.putExtra(Constant.INTENT_PARAM, mList.get(postion).getId());
                i.putExtra(Constant.INTENT_PARAM_ONE, mList.get(postion).getName());
                startActivity(i);
            }
        });
    }
    @Override
    public void initRecyclerView() {
    }
    @Override
    public void bindListData(List<MusicTypeBean> beanList) {
        //绑定音乐类型数据
//        mList.addAll(beanList);
        mList = beanList;
        int size = beanList.size()<=8?beanList.size():8;
        if(beanList.size()>8){
            mDataBinding.showMoreVisible.setVisibility(View.VISIBLE);
        }
        for (int i=0;i<size;i++) {
            mHideList.add(beanList.get(i));
        }
        mMusicOnlineAdapter = new MusicOnlineAdapter(mHideList);
        mDataBinding.releaseOnlineMusicRecycler.setAdapter(mMusicOnlineAdapter);
        mMusicOnlineAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                Intent i = new Intent(MusicOnlineActivity.this, MusicOnlineTypeActivity.class);
                i.putExtra(Constant.INTENT_PARAM, mList.get(postion).getId());
                i.putExtra(Constant.INTENT_PARAM_ONE, mList.get(postion).getName());
                startActivity(i);
            }
        });
//        mMusicOnlineAdapter.notifyDataSetChanged();
    }
    @Override
    public void onItemClick(View view, int postion) {
        playMusic(mSearchMusicList.get(postion));

    }
    @Override
    public void playMusic(final MusicBean bean) {
        mDialog = new AlertDialog.Builder(getContext());
        mDialog.setMessage("确定下载此音乐？");
        mDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                //
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    mPermissions
                            .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            .subscribe(new Consumer<Boolean>() {
                                @Override
                                public void accept(Boolean isAgree) {
                                    if (isAgree) {
                                        mPresenter.downloadOnlineMusic(bean);
                                        Log.d("permission", "---- 请求通过----");
                                    } else {
                                        showToast("权限被拒绝");
                                        for (String permission : mRequestPermissions) {
                                            if (ContextCompat.checkSelfPermission(getContext(), permission) == PackageManager.PERMISSION_DENIED) {
                                                Log.d("permission", "---- 权限被拒绝----" + permission);
                                            }
                                        }
                                    }
                                }
                            });
                } else {
                    mPresenter.downloadOnlineMusic(bean);
                }

                dialog.dismiss();
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).create().show();
    }


    @Override
    public void ShowSearchData(boolean flg,List<MusicBean> list) {
        mSearchMusicList.clear();
        if (flg) {
            mSearchMusicList.addAll(list);
            //匹配到用户
             mDataBinding.musicSearchLayoutOne.setVisibility(View.GONE);
             mDataBinding.musicNoAnswer.setVisibility(View.GONE);
            RecyclerView searchLayoutTwo = (RecyclerView) mDataBinding.searchMusicLayoutTwo;
            searchLayoutTwo.setVisibility(View.VISIBLE);
            SearchMusicAdapter searchMusicAdapter = new SearchMusicAdapter(list);
            mDataBinding.searchMusicLayoutTwo.setAdapter(searchMusicAdapter);
            searchMusicAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int postion) {
                    playMusic(mSearchMusicList.get(postion));
                }
            });
            if (mDataBinding.searchMusicLayoutTwo != null) {
                Log.d("afterTextChanged", "getListHeight=" + mDataBinding.searchMusicLayoutTwo.getHeight());
            }
        } else {
            //没有匹配到用户
           mDataBinding.musicSearchLayoutOne.setVisibility(View.GONE);
           mDataBinding.musicNoAnswer.setVisibility(View.VISIBLE);
           mDataBinding.searchMusicLayoutTwo.setVisibility(View.GONE);
        }
    }

    @Override
    public RecyclerView getRecyclerView() {
        return mDataBinding.releaseOnlineMusicRecycler;
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        ((MusicOnlineFragment)fragments.get(tab.getPosition())).loadFreshData(tab.getPosition());
    }
    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
    }
    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
    //    加载音乐类型的item布局
    public class MusicOnlineAdapter extends BaseAdapter<MusicTypeBean> {
        public MusicOnlineAdapter(List<MusicTypeBean> list) {
            super(R.layout.music_online_type_item, list);
        }
        //    加载音乐类型的item的数据
        @Override
        protected void convert(BaseHolder holder, MusicTypeBean item) {
            holder.setText(R.id.release_online_music_type,item.getName());
            Glide.with(getContext()).load(item.getIcon_url()).into((ImageView) holder.getView(R.id.release_online_music_typeUrl));
        }
    }

}
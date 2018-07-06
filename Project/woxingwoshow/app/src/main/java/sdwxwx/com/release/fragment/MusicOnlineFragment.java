package sdwxwx.com.release.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.tbruyelle.rxpermissions2.RxPermissions;
import io.reactivex.functions.Consumer;
import sdwxwx.com.R;
import sdwxwx.com.adapter.BaseAdapter;
import sdwxwx.com.adapter.BaseHolder;
import sdwxwx.com.base.BaseCallback;
import sdwxwx.com.base.BaseFragment;
import sdwxwx.com.bean.MusicBean;
import sdwxwx.com.cons.Constant;
import sdwxwx.com.databinding.MusicOnlineFragmentBinding;
import sdwxwx.com.login.utils.LoginHelper;
import sdwxwx.com.release.contract.MusicOnlineFragmentContract;
import sdwxwx.com.release.model.MusicModel;
import sdwxwx.com.release.presenter.MusicOnlineFragmentPresenter;
import sdwxwx.com.util.StringUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 860117066 on 2018/05/17.
 */

public class MusicOnlineFragment extends BaseFragment<MusicOnlineFragmentBinding, MusicOnlineFragmentPresenter>
        implements MusicOnlineFragmentContract.View, BaseAdapter.OnItemClickListener {
    int type;
    private ImageView iv_music_icon;
    MusicOnlineAdapter mMusicOnlineAdapter;
    List<MusicBean> mList = new ArrayList<>();
    private MediaPlayer mMediaPlayer;
    private AlertDialog.Builder mDialog;
    private RxPermissions mPermissions;
    private String[] mRequestPermissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
    int rePage=1;
    int mePage=1;
    public static MusicOnlineFragment newInstance(int type) {
        Bundle args = new Bundle();
        args.putInt("type", type);
        MusicOnlineFragment fragment = new MusicOnlineFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
        super.onActivityCreated(savedInstanceState);
        if (getArguments() != null) {
            type = getArguments().getInt("type");
        }
        if (type == 0) {
            mPresenter.loadPopularMusicData(Constant.REQUEST_PAGE,mDataBinding);
        } else if (type == 1) {
            mPresenter.loadCollectMusicData(Constant.REQUEST_PAGE,mDataBinding);
        }
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.music_online_fragment;
    }

    @Override
    protected MusicOnlineFragmentPresenter createPresenter() {
        return new MusicOnlineFragmentPresenter();
    }

    @Override
    protected void initViews() {
        mDataBinding.setPresenter(mPresenter);

        initRecyclerView();
        mPermissions = new RxPermissions(getActivity());
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
                for (MusicBean bean : mList) {
                    bean.setPlay(false);
                }
                mMusicOnlineAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void initRecyclerView() {
        mDataBinding.musicOnlineSpringView.setListener(this);
        mDataBinding.musicOnlineSpringView.setHeader(new DefaultHeader(mContext));
        mDataBinding.musicOnlineSpringView.setFooter(new DefaultFooter(mContext));
        mDataBinding.musicOnlineRecyclerview.setLayoutManager(new LinearLayoutManager(mContext));
        mMusicOnlineAdapter = new MusicOnlineAdapter(mList);
        mDataBinding.musicOnlineRecyclerview.setAdapter(mMusicOnlineAdapter);
        mMusicOnlineAdapter.setOnItemClickListener(this);


    }

    @Override
    public void bindListData(List<MusicBean> beanList) {
        mList.addAll(beanList);
        if (mList.size()<=0) {
            mDataBinding.musicOnlineSpringView.setVisibility(View.GONE);
        } else {
            mDataBinding.musicOnlineSpringView.setVisibility(View.VISIBLE);
        }
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mMusicOnlineAdapter.notifyDataSetChanged();
            }
        });
        mDataBinding.musicOnlineSpringView.onFinishFreshAndLoad();
    }


    @Override
    public void playMusic(final MusicBean bean) {
        mDialog = new AlertDialog.Builder(mContext);
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
                                            if (ContextCompat.checkSelfPermission(mContext, permission) == PackageManager.PERMISSION_DENIED) {
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
    public void onItemClick(View view, int postion) {
        playMusic(mList.get(postion));
    }
    public class MusicOnlineAdapter extends BaseAdapter<MusicBean> {
        public MusicOnlineAdapter(List<MusicBean> list) {
            super(R.layout.item_music_collection_activity, list);
        }

        @Override
        protected void convert(final BaseHolder holder, final MusicBean item) {
            holder.setText(R.id.music_collection_name, item.getTitle());
            holder.setText(R.id.music_collection_author, item.getArtist());
            holder.setText(R.id.music_collection_time, StringUtil.formatSecond(item.getDuration()));
            if (item.isPlay()) {
                Glide.with(mContext).load(R.drawable.stop_camera).into((ImageView) holder.getView(R.id.music_collection_play));
            } else {
                Glide.with(mContext).load(R.drawable.start_camera).into((ImageView) holder.getView(R.id.music_collection_play));
            }
             //音乐封面加载异常时，显示默认图片
              RequestOptions options = new RequestOptions().error(R.drawable.music_cover);
              Glide.with(mContext).load(item.getCover_url()).apply(options).into((ImageView)holder.getView(R.id.music_collection_cover));
//              Glide.with(mContext).load(item.getCover_url()).into((ImageView)holder.getView(R.id.music_collection_cover));

            ((ImageView) holder.getView(R.id.music_collection_cover)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //TODO 点击播放或者暂停音乐
                    if (item.isPlay()) {
                        mMediaPlayer.stop();
                        item.setPlay(false);
                        Glide.with(mContext).load(R.drawable.start_camera).into((ImageView) holder.getView(R.id.music_collection_play));
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
                        for (MusicBean bean : mList) {
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
//                                showToast("收藏失败");
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
//                                showToast("取消收藏失败");
                            }
                        });
                    }

                }
            });
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
            for (MusicBean bean : mList) {
                bean.setPlay(false);
            }
            mMusicOnlineAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public RecyclerView getRecyclerView() {
        return mDataBinding.musicOnlineRecyclerview;
    }

    @Override
    public void onRefresh() {
        mList.clear();
        if (type == 0) {
            rePage = 1;
            mPresenter.loadPopularMusicData(Constant.REQUEST_PAGE,mDataBinding);
        } else {
            mePage = 1;
            mPresenter.loadCollectMusicData(Constant.REQUEST_PAGE,mDataBinding);
        }

    }

    public void loadFreshData(int pos) {
        mList.clear();
        if (mPresenter != null) {
            if (pos == 0) {
                rePage = 1;
                mPresenter.loadPopularMusicData(Constant.REQUEST_PAGE,mDataBinding);
            } else {
                mePage = 1;
                mPresenter.loadCollectMusicData(Constant.REQUEST_PAGE,mDataBinding);
            }
        }
    }

    public void loadMoreData(int pos) {
        if (mPresenter != null) {
            if (pos == 0) {
                rePage++;
                mPresenter.loadPopularMusicData(rePage+"",mDataBinding);
            } else {
                mePage++;
                mPresenter.loadCollectMusicData(mePage+"",mDataBinding);
            }
        }
    }


    @Override
    public void onLoadmore() {

//        mPresenter.loadPopularMusicData(page+"",mDataBinding);
//        mPresenter.loadCollectMusicData(page+"",mDataBinding);
    }
}

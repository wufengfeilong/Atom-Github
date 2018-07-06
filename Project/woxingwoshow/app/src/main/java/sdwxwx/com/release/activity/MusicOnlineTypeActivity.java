package sdwxwx.com.release.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;
import sdwxwx.com.R;
import sdwxwx.com.adapter.BaseAdapter;
import sdwxwx.com.adapter.BaseHolder;
import sdwxwx.com.base.BaseActivity;
import sdwxwx.com.base.BaseCallback;
import sdwxwx.com.bean.MusicBean;
import sdwxwx.com.cons.Constant;
import sdwxwx.com.databinding.OnlineMusicTypeListBinding;
import sdwxwx.com.login.utils.LoginHelper;
import sdwxwx.com.release.contract.MusicOnlineTypeContract;
import sdwxwx.com.release.model.MusicModel;
import sdwxwx.com.release.presenter.MusicOnlineTypePresenter;
import sdwxwx.com.util.StringUtil;

/**
 * Created by 860117066 on 2018/06/13.
 * 类描述：音乐分类别列表Activity
 */

public class MusicOnlineTypeActivity extends BaseActivity<OnlineMusicTypeListBinding, MusicOnlineTypePresenter>
        implements MusicOnlineTypeContract.View,BaseAdapter.OnItemClickListener{
    /** 定义适配器 */
    MusicAdapter mMusicAdapter = null;
    private String type_id;
    private MediaPlayer mMediaPlayer;
    private AlertDialog.Builder mDialog;
    private RxPermissions mPermissions;
    private String[] mRequestPermissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
    List<MusicBean> mList = new ArrayList<>();

    //页面初始化
    @Override
    protected void initViews() {
        mDataBinding.setPresenter(mPresenter);
        initRecyclerView();
        //设置音乐类型的标题
        String title = getIntent().getStringExtra(Constant.INTENT_PARAM_ONE);
        mDataBinding.musicTypeName.setText(title);
        type_id = getIntent().getStringExtra(Constant.INTENT_PARAM);
        mPresenter.loadListData(type_id,mDataBinding);

        //下载
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
                for (MusicBean bean : mList) {
                    bean.setPlay(false);
                }
                mMusicAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected MusicOnlineTypePresenter createPresenter() {
        return new MusicOnlineTypePresenter() ;
    }
    @Override
    protected int getLayoutId() {
        return R.layout.online_music_type_list;
    }

    @Override
    public void bindListData(List<MusicBean> beanList) {
            mList.addAll(beanList);
            this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mMusicAdapter.notifyDataSetChanged();
                }
            });
        }
    @Override
    public void initRecyclerView() {
        mDataBinding.musicList.setLayoutManager(new LinearLayoutManager(getContext()));
        // 定义适配器并绑定点击事件
        mMusicAdapter = new MusicAdapter(mList);
        mMusicAdapter.setOnItemClickListener(this);

        // 绑定适配器
        mDataBinding.musicList.setAdapter(mMusicAdapter);
    }

    @Override
    public RecyclerView getRecyclerView() {
        return mDataBinding.musicList;
    }

    @Override
    public void onItemClick(View view, int postion) {
        playMusic(mList.get(postion));
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadmore() {

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

    public class MusicAdapter extends BaseAdapter<MusicBean> {
        public MusicAdapter(List<MusicBean> list) {
            super(R.layout.online_music_collection_item, list);
        }
        @Override
        protected void convert(final BaseHolder holder, final MusicBean item) {
            holder.setText(R.id.music_type_name,item.getTitle());
            holder.setText(R.id.music_type_author,item.getArtist());
            holder.setText(R.id.music_type_time, StringUtil.formatSecond(item.getDuration()));
            Glide.with(getContext()).load(item.getCover_url()).into((ImageView) holder.getView(R.id.music_type_cover_url));
            //音乐封面加载异常时，显示默认图片
            RequestOptions options = new RequestOptions().error(R.drawable.music_cover);
            Glide.with(getContext()).load(item.getCover_url()).apply(options).into((ImageView)holder.getView(R.id.music_type_cover_url));
//          Glide.with(getContext()).load(item.getCover_url()).into((ImageView) holder.getView(R.id.music_type_cover_url));
            if (item.isPlay()) {
                Glide.with(getContext()).load(R.drawable.stop_camera).into((ImageView) holder.getView(R.id.music_collection_play_music));
            } else {
                Glide.with(getContext()).load(R.drawable.start_camera).into((ImageView) holder.getView(R.id.music_collection_play_music));
            }
            ((ImageView) holder.getView(R.id.music_type_cover_url)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //TODO 点击播放或者暂停音乐
                    if (item.isPlay()) {
                        mMediaPlayer.stop();
                        item.setPlay(false);
                        Glide.with(getContext()).load(R.drawable.start_camera).into((ImageView) holder.getView(R.id.music_collection_play_music));
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
                holder.setImageResource(R.id.music_collection_type, R.drawable.unfavorited_collection_music);
            } else if ("1".equals(item.getIs_favorited())) {
                holder.setImageResource(R.id.music_collection_type, R.drawable.favorited_collection_music);
            }
            ((ImageView) holder.getView(R.id.music_collection_type)).setOnClickListener(new View.OnClickListener() {
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
            mMusicAdapter.notifyDataSetChanged();
        }
    }
}

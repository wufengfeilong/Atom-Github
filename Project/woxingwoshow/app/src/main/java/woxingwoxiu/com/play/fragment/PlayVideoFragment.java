package woxingwoxiu.com.play.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.SurfaceTexture;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.*;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.ksyun.media.player.IMediaPlayer;
import com.ksyun.media.player.KSYMediaPlayer;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.widget.SpringView;
import woxingwoxiu.com.R;
import woxingwoxiu.com.adapter.BaseAdapter;
import woxingwoxiu.com.adapter.BaseHolder;
import woxingwoxiu.com.base.BaseFragment;
import woxingwoxiu.com.bean.ShareBean;
import woxingwoxiu.com.databinding.FragmentPlayVideoBinding;
import woxingwoxiu.com.play.bean.CommentBean;
import woxingwoxiu.com.play.contract.PlayVideoFragmentContract;
import woxingwoxiu.com.play.presenter.PlayVideoFragmentPresenter;
import woxingwoxiu.com.util.DialogUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * create by 860115039
 * date      2018/5/18
 * time      16:22
 */
public class PlayVideoFragment extends BaseFragment<FragmentPlayVideoBinding,PlayVideoFragmentPresenter>
        implements PlayVideoFragmentContract.View,TextureView.SurfaceTextureListener {
    private static final String TAG = "PlayVideoFragment";
    Dialog mBtmDialog;
    static String mVideoUrl;
    // 播放SDK提供的监听器
    // 播放器的对象
    private KSYMediaPlayer ksyMediaPlayer;
    // TextureView需在Layout中定义
    TextureView mVideoTextureView;
    Surface mSurface;

    private PlayCommentAdapter mCommentAdapter;
    List<CommentBean> mCommentList = new ArrayList<>();

    public static PlayVideoFragment newInstance(String videoUrl) {

        Bundle args = new Bundle();
        args.putString("type", videoUrl);
        PlayVideoFragment fragment = new PlayVideoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getArguments() != null) {
            mVideoUrl = getArguments().getString("type");
            Log.d(TAG, "onCreate: "+mVideoUrl);
            initPlayer();

        }
    }


    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_play_video;
    }

    @Override
    protected PlayVideoFragmentPresenter createPresenter() {
        return new PlayVideoFragmentPresenter();
    }



    @Override
    protected void initViews() {
        mDataBinding.setPresenter(mPresenter);
        loadData();
        mVideoTextureView = mDataBinding.playVideoSfv;
        mVideoTextureView.setSurfaceTextureListener(this);

    }

    private void loadData() {

    }

    private void initPlayer() {
        ksyMediaPlayer = new KSYMediaPlayer.Builder(mContext.getApplicationContext()).build();
        ksyMediaPlayer.setOnCompletionListener(mOnCompletionListener);
        ksyMediaPlayer.setOnPreparedListener(mOnPreparedListener);
        ksyMediaPlayer.setOnInfoListener(mOnInfoListener);
        ksyMediaPlayer.setOnErrorListener(mOnErrorListener);

        ksyMediaPlayer.setLooping(true);
        ksyMediaPlayer.setScreenOnWhilePlaying(true);
        // 按照初始位置播放
        ksyMediaPlayer.seekTo(0);
        try {
            ksyMediaPlayer.setDataSource(mVideoUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public IMediaPlayer.OnInfoListener mOnInfoListener = new IMediaPlayer.OnInfoListener() {
        @Override
        public boolean onInfo(IMediaPlayer iMediaPlayer, int i, int i1) {
            switch (i) {
                case KSYMediaPlayer.MEDIA_INFO_BUFFERING_START:
                    Log.d(TAG, "开始缓冲数据");
                    showLoading();
                    break;
                case KSYMediaPlayer.MEDIA_INFO_BUFFERING_END:
                    Log.d(TAG, "数据缓冲完毕");
                    hideLoading();
                    break;
                case KSYMediaPlayer.MEDIA_INFO_AUDIO_RENDERING_START:
//                    Toast.makeText(mContext, "开始播放音频", Toast.LENGTH_SHORT).show();
                    hideLoading();
                    break;
                case KSYMediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START:
//                    Toast.makeText(mContext, "开始渲染视频", Toast.LENGTH_SHORT).show();
                    break;
                case KSYMediaPlayer.MEDIA_INFO_SUGGEST_RELOAD:
                    // 播放SDK有做快速开播的优化，在流的音视频数据交织并不好时，可能只找到某一个流的信息
                    // 当播放器读到另一个流的数据时会发出此消息通知
                    // 请务必调用reload接口
                    if(ksyMediaPlayer != null)
                        ksyMediaPlayer.reload(mVideoUrl, false);
                    break;
                case KSYMediaPlayer.MEDIA_INFO_RELOADED:
                    Toast.makeText(mContext, "reload成功的消息通知", Toast.LENGTH_SHORT).show();
                    hideLoading();
                    break;
            }
            return false;
        }
    };
    private IMediaPlayer.OnPreparedListener mOnPreparedListener = new IMediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(IMediaPlayer mp) {
            if(ksyMediaPlayer != null) {
                // 设置视频伸缩模式，此模式为裁剪模式
                ksyMediaPlayer.setVideoScalingMode(KSYMediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING);
                // 开始播放视频
                ksyMediaPlayer.start();
            }
        }
    };
    private IMediaPlayer.OnErrorListener mOnErrorListener = new IMediaPlayer.OnErrorListener() {
        @Override
        public boolean onError(IMediaPlayer iMediaPlayer, int i, int i1) {
            showToast("播放器遇到错误，播放已退出，错误码:"+i);
            initPlayer();
            return false;
        }
    };

    private IMediaPlayer.OnCompletionListener mOnCompletionListener = new IMediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(IMediaPlayer mp) {
            // 播放完成，用户可选择释放播放器
            if(ksyMediaPlayer != null) {
                ksyMediaPlayer.stop();
                ksyMediaPlayer.release();
            }
        }
    };

    @Override
    public void showShareDialog() {
        ShareBean shareBean = new ShareBean();
        DialogUtil.showShareDialog(mContext,shareBean);
    }

    @Override
    public void showCommentDialog() {
        mBtmDialog = new Dialog(mContext, R.style.BottomDialog);
        View view = LayoutInflater.from(mContext).inflate(R.layout.play_comment_list, null);
        mBtmDialog.setContentView(view);
        SpringView springView = view.findViewById(R.id.play_video_comment_sv);
        RecyclerView recyclerView = view.findViewById(R.id.play_video_comment_rv);
        springView.setListener(this);
//        springView.setHeader(new DefaultHeader(mContext));
        springView.setFooter(new DefaultFooter(mContext));
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mPresenter.loadCommentData("123456");
        mCommentAdapter = new PlayCommentAdapter(mCommentList);
        recyclerView.setAdapter(mCommentAdapter);
        mCommentAdapter.setOnItemClickListener(this);


        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = mContext.getResources().getDisplayMetrics().widthPixels;
        layoutParams.height = mContext.getResources().getDisplayMetrics().heightPixels*7/10;
        view.setLayoutParams(layoutParams);
        mBtmDialog.getWindow().setGravity(Gravity.BOTTOM);
        mBtmDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        mBtmDialog.setCanceledOnTouchOutside(true);
        mBtmDialog.show();
    }

    @Override
    public void loadCommentData(List<CommentBean> list) {
        mCommentList = list;
        mCommentAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().getWindow().setNavigationBarColor(Color.BLACK);
    }

    @Override
    public void showSaySthDialog() {

        mBtmDialog = new Dialog(mContext, R.style.BottomDialog);
        final View view = LayoutInflater.from(mContext).inflate(R.layout.play_say_sth, null);
        final EditText saySthEt = view.findViewById(R.id.play_video_say_sth);
        ImageView atSbIv = view.findViewById(R.id.play_video_at_sb);
        atSbIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("点击了@");
            }
        });
        Button sendBtn = view.findViewById(R.id.play_video_send);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(saySthEt.getText().toString())) {
                    addComment(saySthEt.getText().toString());
                }
            }
        });
        mBtmDialog.setContentView(view);
        mBtmDialog.getWindow().setGravity(Gravity.BOTTOM);
        mBtmDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        mBtmDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                InputMethodManager inputMethodManager =
                        (InputMethodManager)mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(mDataBinding.playVideoSaySth.getWindowToken(),0);
            }
        });
        mBtmDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            public void onShow(DialogInterface dialog) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(saySthEt, InputMethodManager.SHOW_IMPLICIT);
            }
        });
        mBtmDialog.setCanceledOnTouchOutside(true);
        mBtmDialog.show();
    }

    private void addComment(String content){
        //comment_id:回复的目标评论的编号。没有目标评论时，编号为0。
        mPresenter.addComment("123456","12345678",0,content);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if(ksyMediaPlayer != null)
            ksyMediaPlayer.release();
        ksyMediaPlayer = null;
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadmore() {

    }

    @Override
    public void onItemClick(View view, int postion) {
        showToast(""+postion);
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        //连接对象（MediaPlayer和TextureView）
        mSurface = new Surface(surface);
        ksyMediaPlayer.setSurface(mSurface);
        ksyMediaPlayer.prepareAsync();
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        mVideoTextureView = null;
        mSurface = null;
        // 播放完成，用户可选择释放播放器
        if(ksyMediaPlayer != null) {
            ksyMediaPlayer.stop();
            ksyMediaPlayer.release();
        }
        return true;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

    }

    public class PlayCommentAdapter extends BaseAdapter<CommentBean> {
        public PlayCommentAdapter(List<CommentBean> list) {
            super(R.layout.play_comment_list_item, list);
        }

        @Override
        protected void convert(BaseHolder holder, CommentBean item) {
            holder.setText(R.id.play_comment_name, "哈里路亚");
            holder.setText(R.id.play_comment_date, "2018-05-15");
            holder.setText(R.id.play_comment_thumb_up_count, "123");
            holder.setText(R.id.play_comment_content, "pinglunneitrong");
            holder.setText(R.id.play_comment_reply_count, "5");
            holder.setImageResource(R.id.play_comment_head, R.drawable.temp);
            holder.setImageResource(R.id.play_comment_thumb_up_iv, R.drawable.temp);
        }
    }
}

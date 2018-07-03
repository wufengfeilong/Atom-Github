package sdwxwx.com.play.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.*;
import android.graphics.SurfaceTexture;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.*;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import com.bumptech.glide.Glide;
import com.ksyun.media.player.IMediaPlayer;
import com.ksyun.media.player.KSYMediaPlayer;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.widget.SpringView;
import sdwxwx.com.R;
import sdwxwx.com.adapter.BaseAdapter;
import sdwxwx.com.adapter.BaseHolder;
import sdwxwx.com.base.BaseCallback;
import sdwxwx.com.base.BaseFragment;
import sdwxwx.com.cons.Constant;
import sdwxwx.com.databinding.FragmentPlayVideoBinding;
import sdwxwx.com.home.bean.PlayVideoBean;
import sdwxwx.com.home.bean.SearchUserBean;
import sdwxwx.com.login.activity.LoginActivity;
import sdwxwx.com.login.utils.LoginHelper;
import sdwxwx.com.message.activity.FansHomeActivity;
import sdwxwx.com.play.bean.CommentBean;
import sdwxwx.com.play.contract.PlayVideoFragmentContract;
import sdwxwx.com.play.model.PlayVideoFragmentModel;
import sdwxwx.com.play.presenter.PlayVideoFragmentPresenter;
import sdwxwx.com.release.activity.FriendsListActivity;
import sdwxwx.com.release.model.ReleaseModel;
import sdwxwx.com.util.MeterUtil;
import sdwxwx.com.util.StringUtil;
import sdwxwx.com.widget.OnekeyShare;
import sdwxwx.com.widget.RichEditText;
import sdwxwx.com.widget.ShareFrag;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;


/**
 * create by 860115039
 * date      2018/5/18
 * time      16:22
 */
public class PlayVideoFragment extends BaseFragment<FragmentPlayVideoBinding, PlayVideoFragmentPresenter>
        implements PlayVideoFragmentContract.View, TextureView.SurfaceTextureListener {
    private static final String TAG = "PlayVideoFragment";
    Dialog mBtmDialog;
    Dialog mReplyDialog;
    SpringView mCommentSv;
    AlertDialog.Builder mDownLoadDialog;
    int pos;
    PlayVideoBean mBean;
    LoginHelper mHelper;
    // 播放SDK提供的监听器
    // 播放器的对象
    private KSYMediaPlayer ksyMediaPlayer;
    // TextureView需在Layout中定义
    TextureView mVideoTextureView;
    Surface mSurface;
    int videoMemberId;
    int type;
    boolean isFront;

    String at = "0";
    List<SearchUserBean.HaveUserBean> atList = new ArrayList<>();
    RichEditText mCurEt;
    boolean commentChangeFlg;

    View mCommentView;
    PlayVideoFragmentModel mModel;
    /**
     * 回复的目标评论的编号。
     */
    String commentId = "0";

    int page = 1;

    private PlayCommentAdapter mCommentAdapter;
    private PlayReplyAdapter mReplyAdapter;
    List<CommentBean> mCommentList = new ArrayList<>();
    List<CommentBean> mReplyList = new ArrayList<>();

    DownLoadRecieiver mReceiver;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mDataBinding.playVideoCoverIv.setVisibility(View.GONE);

        }
    };

    public static PlayVideoFragment newInstance(int pos, int type) {

        Bundle args = new Bundle();
        args.putInt("pos", pos);
        args.putInt("type", type);
        PlayVideoFragment fragment = new PlayVideoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mHelper = LoginHelper.getInstance();
        if (getArguments() != null) {
            pos = getArguments().getInt("pos");
            type = getArguments().getInt("type");
            if (type == 0) {
                mBean = mHelper.getPlayVideoList().get(pos);
            } else if (type == 1) {
                mBean = mHelper.getNearList().get(pos);
            } else if (type == 2) {
                mBean = mHelper.getTopicList().get(pos);
            } else if (type == 3) {
                mBean = mHelper.getOwnerList().get(pos);
            } else if (type == 4) {
                mBean = mHelper.getLikeList().get(pos);
            } else if (type == 5) {
                mBean = mHelper.getFansList().get(pos);
            }
            videoMemberId = mBean.getMember_id();
            Glide.with(mContext).load(mBean.getCover_url()).into(mDataBinding.playVideoCoverIv);
            Log.d(TAG, "onCreate: " + pos);
            initPlayer();
            initData();
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
        mVideoTextureView = mDataBinding.playVideoSfv;
        mVideoTextureView.setSurfaceTextureListener(this);

        mModel = new PlayVideoFragmentModel();

        //下载视频的广播
        mReceiver = new DownLoadRecieiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.INTENT_FILTER_DOWNLOAD_VIDEO);
        mContext.registerReceiver(mReceiver, filter);
    }

    private class DownLoadRecieiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            boolean isSuccess = intent.getBooleanExtra("isSuccess", false);
            boolean isOnlyDownload = intent.getBooleanExtra("isOnlyDownload", false);
            final boolean isWeixinShare = intent.getBooleanExtra("isWeixinShare", false);
            int position = intent.getIntExtra("position", 0);
            if (isSuccess) {
                if (isOnlyDownload) {
                    showToast("下载成功！视频已保存到/DownLoad文件夹下！");
                } else {
                    mDownLoadDialog = new AlertDialog.Builder(mContext);
                    mDownLoadDialog.setMessage("腾讯以\"互联网短视频整治\"为理由，屏蔽了您要分享的链接。\n需要您上传视频才能分享。\n视频已保存到/DownLoad文件夹下！");
                    mDownLoadDialog.setPositiveButton("去分享", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //打开微信或QQ
                            try {
                                Intent intent = new Intent();

                                if (isWeixinShare) {
                                    ComponentName cmp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI");
                                    intent.setAction(Intent.ACTION_MAIN);
                                    intent.addCategory(Intent.CATEGORY_LAUNCHER);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    intent.setComponent(cmp);
                                    startActivity(intent);
                                } else {
                                    intent = mContext.getPackageManager().getLaunchIntentForPackage("com.tencent.mobileqq");
                                    startActivity(intent);
//                                    cmp = new ComponentName("com.tencent.mobileqq", "com.tencent.mobileqq.activity.HomeActivity");
                                }
                                dialog.dismiss();
                                PlayVideoFragmentModel.videoShare(LoginHelper.getInstance().getUserId()
                                        , mBean.getId() + ""
                                        , new BaseCallback<String>() {
                                            @Override
                                            public void onSuccess(String data) {
                                                //TODO 分享加1
                                                mDataBinding.playVideoShareCount.setText((mBean.getShare_count() + 1) + "");
                                            }

                                            @Override
                                            public void onFail(String msg) {
                                                showToast(msg);
                                            }
                                        });
                            } catch (Exception e) {
                                //若无法正常跳转，在此进行错误处理
                                if (isWeixinShare) {
                                    showToast("无法跳转到微信，请检查您是否安装了微信！");
                                } else {
                                    showToast("无法跳转到QQ，请检查您是否安装了QQ！");
                                }
                            }
                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).create();
                    if (position == pos) {
                        mDownLoadDialog.show();
                    }
                }
            } else {
                showToast("下载失败！");
            }
        }
    }

    private void initData() {
        //会员不能点赞自己
        if (!mHelper.getUserId().equals(mBean.getMember_id() + "")) {
            if (!"1".equals(mBean.getIs_followed())) {
                mDataBinding.playVideoFollow.setVisibility(View.VISIBLE);
            }
        }
        Glide.with(mContext).load(mBean.getAvatar_url()).into(mDataBinding.playVideoHead);
        mDataBinding.playVideoTitle.setText(mBean.getNickname());
        mDataBinding.playVideoIntroduce.setText(mBean.getDescription());
        mDataBinding.playVideoCommentCount.setText(MeterUtil.numToWan(mBean.getComment_count()));
        mDataBinding.playVideoThumbUpCount.setText(MeterUtil.numToWan(mBean.getLike_count()));
        mDataBinding.playVideoShareCount.setText(MeterUtil.numToWan(mBean.getShare_count()));


        if ("1".equals(mBean.getIs_liked())) {
            mDataBinding.playVideoThumbUpIv.setImageResource(R.drawable.video_liked);
        } else {
            mDataBinding.playVideoThumbUpIv.setImageResource(R.drawable.thumb_up_no_selected);
        }

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
            ksyMediaPlayer.setDataSource(mBean.getUrl());
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
                    if (ksyMediaPlayer != null)
                        ksyMediaPlayer.reload(mBean.getUrl(), false);
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
            if (ksyMediaPlayer != null) {
                // 设置视频伸缩模式，此模式为裁剪模式
                ksyMediaPlayer.setVideoScalingMode(KSYMediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING);
                // 开始播放视频
                if (isFront) {
                    ksyMediaPlayer.start();
                    mHandler.sendEmptyMessageDelayed(0,300);//给主线程发送一个隐藏图片的消息
                } else {
                    ksyMediaPlayer.pause();
                    mDataBinding.playVideoCoverIv.setVisibility(View.VISIBLE);
                }
            }
        }
    };
    private IMediaPlayer.OnErrorListener mOnErrorListener = new IMediaPlayer.OnErrorListener() {
        @Override
        public boolean onError(IMediaPlayer iMediaPlayer, int i, int i1) {
            Log.d(TAG, "播放器遇到错误，播放已退出，错误码:" + i);
            initPlayer();
            return false;
        }
    };

    private IMediaPlayer.OnCompletionListener mOnCompletionListener = new IMediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(IMediaPlayer mp) {
            // 播放完成，用户可选择释放播放器
            if (ksyMediaPlayer != null) {
                ksyMediaPlayer.stop();
                ksyMediaPlayer.release();
            }
        }
    };

    @Override
    public void showShareDialog() {
//        ShareBean shareBean = new ShareBean();
//        DialogUtil.showShareDialog(mContext,shareBean);
        android.app.FragmentManager fm = getActivity().getFragmentManager();
//        android.app.FragmentManager fm =getActivity().getFragmentManager();
        ShareFrag shareFrag = new ShareFrag();

        OnekeyShare webBean = new OnekeyShare();
        webBean.setVideoUrl(mBean.getUrl(), mBean.getId(), type, pos);
        // 设定web网页分享的URL地址
        webBean.setLayoutId(R.layout.play_video_share);
        webBean.setTitle("分享我在我行我秀的视频，快来一起玩吧～");
        // 文字内容
        // 如果还未登陆的场合
        if (mHelper.getUserBean() == null) {
            webBean.setText("#玩视频来我行我秀#"+mBean.getNickname()+"在我行我秀上分享了视频，快来围观！传送门戳我>>" + mBean.getUrl());
        } else {
            webBean.setText(mHelper.getUserBean().getNickname() + "在我行我秀上分享了视频，快来围观！传送门戳我>>" + mBean.getUrl());
        }

        // 分享视频封面
        webBean.setImageUrl(mBean.getCover_url());
        // TODO URL地址
        webBean.setTitleUrl(mBean.getUrl());
        webBean.setUrl(mBean.getUrl());
        //  TODO 一下内容为QQ控件使用
        webBean.setSite("我行我秀");
        webBean.setSiteUrl(mBean.getUrl());
        shareFrag.setShareParamsMap(webBean.getParams());
        shareFrag.show(fm, null);
    }

    @Override
    public void showCommentDialog() {

        mBtmDialog = new Dialog(mContext, R.style.BottomDialog);
        mCommentView = LayoutInflater.from(mContext).inflate(R.layout.play_comment_list, null);
        mBtmDialog.setContentView(mCommentView);
        mCommentSv = mCommentView.findViewById(R.id.play_video_comment_sv);
        RecyclerView recyclerView = mCommentView.findViewById(R.id.play_video_comment_rv);
        mCommentSv.setListener(this);
//        springView.setHeader(new DefaultHeader(mContext));
        mCommentSv.setFooter(new DefaultFooter(mContext));
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mCommentAdapter = new PlayCommentAdapter(mCommentList);
        recyclerView.setAdapter(mCommentAdapter);
        final RichEditText sayEt = mCommentView.findViewById(R.id.play_comment_say_sth);
        ImageView atIv = mCommentView.findViewById(R.id.play_comment_at_sb);
        final Button sendBtn = mCommentView.findViewById(R.id.play_comment_send);
        sayEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (TextUtils.isEmpty(s)) {
                    sendBtn.setEnabled(false);
                } else {
                    sendBtn.setEnabled(true);
                }
            }
        });
        mCommentAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                requestFocus(postion, sayEt);
            }
        });
        atIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!LoginHelper.getInstance().isOnline()) {
                    actionStartActivity(LoginActivity.class);
                    return;
                }
                mCurEt = sayEt;
                startActivityForResult(new Intent(mContext, FriendsListActivity.class), 0x100);
            }
        });
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!LoginHelper.getInstance().isOnline()) {
                    actionStartActivity(LoginActivity.class);
                    return;
                }
                if (sayEt.getText().length() >= 50) {
                    showToast("评论内容不能大于50个字符。");
                    return;
                }
                sendComment(sayEt, commentId);
                mBtmDialog.dismiss();
                mCommentList.clear();
            }
        });

        //获取评论
        mPresenter.loadCommentData(mBean.getId() + "", Constant.REQUEST_PAGE);

        ImageView closeIv = mCommentView.findViewById(R.id.play_video_comment_close);
        closeIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBtmDialog.dismiss();
                mCommentList.clear();
            }
        });
        ViewGroup.LayoutParams layoutParams = mCommentView.getLayoutParams();
        layoutParams.width = mContext.getResources().getDisplayMetrics().widthPixels;
        layoutParams.height = mContext.getResources().getDisplayMetrics().heightPixels * 7 / 10;
        mCommentView.setLayoutParams(layoutParams);
        mBtmDialog.getWindow().setGravity(Gravity.BOTTOM);
        mBtmDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        mBtmDialog.setCanceledOnTouchOutside(true);
        mBtmDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                mCommentList.clear();
            }
        });
        mBtmDialog.show();
    }

    private void sendComment(final RichEditText sayEt, final String commentId) {
        final String sayStr = sayEt.getText().toString();
        if (!TextUtils.isEmpty(sayStr)) {
            ReleaseModel.getSensitiveList(new BaseCallback<List<String>>() {
                @Override
                public void onSuccess(List<String> data) {
                    for (String word : data) {
                        if (sayStr.indexOf(word) != -1) {
                            showToast("评论内容包含敏感词，不允许发送！");
                            return;
                        }
                    }
                    addComment(sayEt, commentId);
                }

                @Override
                public void onFail(String msg) {
                    showToast(msg);
                }
            });
        }
    }

    @Override
    public void loadCommentData(List<CommentBean> list) {
        mCommentList.addAll(list);
        //设置评论数
        TextView count = mCommentView.findViewById(R.id.play_comment_count);
        count.setText(String.format(getString(R.string.all_comments), mCommentList.size()));

        mCommentAdapter.notifyDataSetChanged();
        mCommentSv.onFinishFreshAndLoad();
    }

    @Override
    public void isFollowed() {
        mDataBinding.playVideoFollow.setVisibility(View.GONE);
        mBean.setIs_followed("1");
        showToast("关注成功。");
    }

    @Override
    public int getFollowId() {
        return videoMemberId;
    }

    @Override
    public String getVideoId() {
        return mBean.getId() + "";
    }

    @Override
    public ImageView getThumbIv() {
        return mDataBinding.playVideoThumbUpIv;
    }

    @Override
    public TextView getCommentTv() {
        return mDataBinding.playVideoCommentCount;
    }

    @Override
    public TextView getThumbTv() {
        return mDataBinding.playVideoThumbUpCount;
    }

    @Override
    public boolean getIsLiked() {
        if ("1".equals(mBean.getIs_liked())) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void setIsLiked(String isLike) {
        mBean.setIs_liked(isLike);
    }

    @Override
    public Dialog getReplyDialog() {
        return mReplyDialog;
    }

    @Override
    public int getVideoType() {
        return type;
    }

    @Override
    public void setLikeCount(int add) {
        mBean.setLike_count(mBean.getLike_count()+add);
    }


    @Override
    public void showSaySthDialog() {

        mBtmDialog = new Dialog(mContext, R.style.BottomDialog);
        final View view = LayoutInflater.from(mContext).inflate(R.layout.play_say_sth, null);
        final RichEditText saySthEt = view.findViewById(R.id.play_video_say_sth);
        ImageView atSbIv = view.findViewById(R.id.play_video_at_sb);
        atSbIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurEt = saySthEt;
                startActivityForResult(new Intent(mContext, FriendsListActivity.class), 0x100);
            }
        });
        Button sendBtn = view.findViewById(R.id.play_video_send);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (saySthEt.getText().length() >= 50) {
                    showToast("评论内容不能大于50个字符。");
                    return;
                }
                sendComment(saySthEt, "0");
                mBtmDialog.dismiss();
            }
        });
        mBtmDialog.setContentView(view);
        mBtmDialog.getWindow().setGravity(Gravity.BOTTOM);
        mBtmDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        mBtmDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                InputMethodManager inputMethodManager =
                        (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(mDataBinding.playVideoSaySth.getWindowToken(), 0);
            }
        });
        mBtmDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            public void onShow(DialogInterface dialog) {
                saySthEt.setFocusable(true);
                saySthEt.setFocusableInTouchMode(true);
                saySthEt.requestFocus();
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(saySthEt, InputMethodManager.SHOW_IMPLICIT);
            }
        });
        mBtmDialog.setCanceledOnTouchOutside(true);
        mBtmDialog.show();
    }

    private void addComment(RichEditText et, String commentId) {
        for (RichEditText.XlpsFriend friend : et.getXlpsFriends()) {
            at = at + "," + friend.mUserId;
        }
        if (at.length() > 2) {
            at = at.substring(2);
        }
        //comment_id:回复的目标评论的编号。没有目标评论时，编号为0。
        mPresenter.addComment(mBean.getId() + "", mHelper.getUserId(), commentId, et.getText().toString(), at);

        at = "0";
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (ksyMediaPlayer != null)
            ksyMediaPlayer.stop();
        ksyMediaPlayer.release();
        ksyMediaPlayer = null;
        mVideoTextureView = null;
        mSurface = null;
        mContext.unregisterReceiver(mReceiver);
    }

    @Override
    public void onRefresh() {
        mCommentList.clear();
        page = 1;
        //获取评论
        mPresenter.loadCommentData(mBean.getId() + "", Constant.REQUEST_PAGE);

    }

    @Override
    public void onLoadmore() {
        page = page + 1;
        //获取评论
        mPresenter.loadCommentData(mBean.getId() + "", page + "");
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
        protected void convert(final BaseHolder holder, final CommentBean item) {
            holder.setText(R.id.play_comment_name, item.getNikename());
            holder.setText(R.id.play_comment_date, StringUtil.dataFormat(item.getCreate_time()));
            if (0 == item.getLike_count()) {
                holder.setText(R.id.play_comment_thumb_up_count, getString(R.string.comment_up_tx));
            } else {
                holder.setText(R.id.play_comment_thumb_up_count, String.valueOf(item.getLike_count()));
            }
            holder.setText(R.id.play_comment_content, item.getContent());
            if (item.getReply_count() == 0) {
                ((TextView) holder.getView(R.id.play_comment_reply_count)).setVisibility(View.GONE);
            } else {
                ((TextView) holder.getView(R.id.play_comment_reply_count)).setVisibility(View.VISIBLE);
                ((TextView) holder.getView(R.id.play_comment_reply_count)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showReplyDialog(item);
                    }
                });
                holder.setText(R.id.play_comment_reply_count,
                        String.format(getString(R.string.look_all), item.getReply_count()));
            }
            Glide.with(mContext).load(item.getAvatar_url()).into((ImageView) holder.getView(R.id.play_comment_head));
            ((ImageView) holder.getView(R.id.play_comment_head)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    paramStartActivity(FansHomeActivity.class, item.getMember_id() + "");
                }
            });

            if ("1".equals(item.getIs_liked())) {
                holder.setImageResource(R.id.play_comment_thumb_up_iv, R.drawable.thumb_up_selected);
                ((TextView)holder.getView(R.id.play_comment_thumb_up_count))
                        .setTextColor(getResources().getColor(android.R.color.holo_red_light));
            } else {
                holder.setImageResource(R.id.play_comment_thumb_up_iv, R.drawable.thumb_up_no_select);
                ((TextView)holder.getView(R.id.play_comment_thumb_up_count))
                        .setTextColor(getResources().getColor(android.R.color.darker_gray));
            }
            ((ImageView) holder.getView(R.id.play_comment_thumb_up_iv)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!LoginHelper.getInstance().isOnline()) {
                        actionStartActivity(LoginActivity.class);
                        return;
                    }
                    if ("1".equals(item.getIs_liked())) {
                        mModel.commentUnlike(mHelper.getUserId(), item.getId() + "", new BaseCallback<String>() {
                            @Override
                            public void onSuccess(String data) {
                                showToast("取消点赞评论成功！");
                                holder.setImageResource(R.id.play_comment_thumb_up_iv, R.drawable.thumb_up_no_select);
                                item.setIs_liked("0");
                                item.setLike_count(item.getLike_count() - 1);
                                ((TextView)holder.getView(R.id.play_comment_thumb_up_count))
                                        .setTextColor(getResources().getColor(android.R.color.darker_gray));
                                notifyDataSetChanged();
                            }

                            @Override
                            public void onFail(String msg) {
                                showToast(msg);
                            }
                        });
                    } else {
                        mModel.commentLike(mHelper.getUserId(), item.getId() + "", new BaseCallback<String>() {
                            @Override
                            public void onSuccess(String data) {
                                showToast("点赞评论成功！");
                                holder.setImageResource(R.id.play_comment_thumb_up_iv, R.drawable.thumb_up_selected);
                                item.setIs_liked("1");
                                item.setLike_count(item.getLike_count() + 1);
                                ((TextView)holder.getView(R.id.play_comment_thumb_up_count))
                                        .setTextColor(getResources().getColor(android.R.color.holo_red_light));
                                notifyDataSetChanged();
                            }

                            @Override
                            public void onFail(String msg) {
                                showToast(msg);
                            }
                        });
                    }

                }
            });

        }
    }

    private void showReplyDialog(final CommentBean item) {
        commentChangeFlg = false;
        mReplyDialog = new Dialog(mContext, R.style.BottomFullDialog);
        View view = LayoutInflater.from(mContext).inflate(R.layout.play_reply_list, null);
        mReplyDialog.setContentView(view);
        RecyclerView recyclerView = view.findViewById(R.id.play_video_reply_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mReplyAdapter = new PlayReplyAdapter(mReplyList);
        recyclerView.setAdapter(mReplyAdapter);
        final RichEditText sayEt = view.findViewById(R.id.play_reply_say_sth);
        ImageView atIv = view.findViewById(R.id.play_reply_at_sb);
        final Button sendBtn = view.findViewById(R.id.play_reply_send);
        mReplyAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                requestFocus(postion, sayEt);
            }
        });
        sayEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s)) {
                    sendBtn.setEnabled(false);
                } else {
                    sendBtn.setEnabled(true);
                }
            }
        });
        atIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!LoginHelper.getInstance().isOnline()) {
                    actionStartActivity(LoginActivity.class);
                    return;
                }
                mCurEt = sayEt;
                startActivityForResult(new Intent(mContext, FriendsListActivity.class), 0x100);
            }
        });

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!LoginHelper.getInstance().isOnline()) {
                    actionStartActivity(LoginActivity.class);
                    return;
                }
                if (sayEt.getText().length() >= 50) {
                    showToast("评论内容不能大于50个字符。");
                    return;
                }
                if ("0".equals(commentId)) {
                    commentId = item.getId()+"";
                }
                sendComment(sayEt, commentId);
                mReplyList.clear();
                commentChangeFlg = true;
            }
        });

        ImageView headIv = view.findViewById(R.id.play_reply_comment_head);
        Glide.with(mContext).load(item.getAvatar_url()).into(headIv);

        final ImageView thumbIv = view.findViewById(R.id.play_reply_comment_thumb_up_iv);
        final TextView commentLikeCount = view.findViewById(R.id.play_reply_comment_thumb_up_count);
        thumbIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!LoginHelper.getInstance().isOnline()) {
                    actionStartActivity(LoginActivity.class);
                    return;
                }
                if ("1".equals(item.getIs_liked())) {
                    mModel.commentUnlike(mHelper.getUserId(), item.getId() + "", new BaseCallback<String>() {
                        @Override
                        public void onSuccess(String data) {
                            showToast("取消点赞评论成功！");
                            thumbIv.setImageResource(R.drawable.thumb_up_no_select);
                            commentLikeCount.setTextColor(getResources().getColor(android.R.color.darker_gray));
                            item.setIs_liked("0");
                            item.setLike_count(item.getLike_count() - 1);
                            commentLikeCount.setText(item.getLike_count() + "");
                            mCommentAdapter.notifyDataSetChanged();
                            if (commentChangeFlg) {
                                commentChangeFlg = false;
                            } else {
                                commentChangeFlg = true;
                            }
                        }

                        @Override
                        public void onFail(String msg) {
                            showToast(msg);
                        }
                    });
                } else {
                    mModel.commentLike(mHelper.getUserId(), item.getId() + "", new BaseCallback<String>() {
                        @Override
                        public void onSuccess(String data) {
                            showToast("点赞评论成功！");
                            thumbIv.setImageResource(R.drawable.thumb_up_selected);
                            commentLikeCount.setTextColor(getResources().getColor(android.R.color.holo_red_light));
                            item.setIs_liked("1");
                            item.setLike_count(item.getLike_count() + 1);
                            commentLikeCount.setText(item.getLike_count() + "");
                            mCommentAdapter.notifyDataSetChanged();
                            if (commentChangeFlg) {
                                commentChangeFlg = false;
                            } else {
                                commentChangeFlg = true;
                            }
                        }

                        @Override
                        public void onFail(String msg) {
                            showToast(msg);
                        }
                    });
                }

            }
        });
        if ("0".equals(item.getIs_liked())) {
            thumbIv.setImageResource(R.drawable.thumb_up_no_select);
            commentLikeCount.setTextColor(getResources().getColor(android.R.color.darker_gray));
        } else {
            thumbIv.setImageResource(R.drawable.thumb_up_selected);
            commentLikeCount.setTextColor(getResources().getColor(android.R.color.holo_red_light));
        }

        ((TextView) view.findViewById(R.id.play_reply_comment_name)).setText(item.getNikename());
        ((TextView) view.findViewById(R.id.play_reply_comment_date)).setText(StringUtil.dataFormat(item.getCreate_time()));
        commentLikeCount.setText(String.valueOf(item.getLike_count()));
        ((TextView) view.findViewById(R.id.play_reply_comment_content)).setText(item.getContent());
        final TextView count = view.findViewById(R.id.reply_comment_count);
        count.setText(String.format(getString(R.string.all_reply), 0));

        mModel.replyCollection(LoginHelper.getInstance().getUserId(), item.getId() + "", new BaseCallback<List<CommentBean>>() {
            @Override
            public void onSuccess(List<CommentBean> data) {
//                mReplyList = data;
                mReplyList.addAll(data);
                count.setText(String.format(getString(R.string.all_reply), data.size()));
                mReplyAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFail(String msg) {
                showToast(msg);
            }
        });

        ImageView closeIv = view.findViewById(R.id.play_reply_close_iv);
        closeIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mReplyDialog.dismiss();
                mReplyList.clear();
            }
        });
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = mContext.getResources().getDisplayMetrics().widthPixels;
        int dialogHeight = getScreenHeight(mContext);
        mReplyDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, dialogHeight == 0 ? ViewGroup.LayoutParams.MATCH_PARENT : dialogHeight);
        mReplyDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        view.setLayoutParams(layoutParams);
        mReplyDialog.getWindow().setGravity(Gravity.BOTTOM);
        mReplyDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        mReplyDialog.setCanceledOnTouchOutside(true);
        mReplyDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                mReplyList.clear();
                if (commentChangeFlg) {
                    onRefresh();
                }
            }
        });
        mReplyDialog.show();
    }

    private void requestFocus(int postion, EditText sayEt) {
        sayEt.setFocusable(true);
        sayEt.setFocusableInTouchMode(true);
        sayEt.requestFocus();
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(sayEt, InputMethodManager.SHOW_IMPLICIT);
        commentId = mCommentList.get(postion).getId() + "";
    }

    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int height = wm.getDefaultDisplay().getHeight();
        return height;
    }

    public class PlayReplyAdapter extends BaseAdapter<CommentBean> {
        public PlayReplyAdapter(List<CommentBean> list) {
            super(R.layout.play_reply_list_item, list);
        }

        @Override
        protected void convert(final BaseHolder holder, final CommentBean item) {
            holder.setText(R.id.play_reply_name, item.getNikename());
            holder.setText(R.id.play_reply_date, StringUtil.dataFormat(item.getCreate_time()));
            holder.setText(R.id.play_reply_thumb_up_count, item.getLike_count() + "");
            holder.setText(R.id.play_reply_content, item.getContent());
            Glide.with(mContext).load(item.getAvatar_url()).into((ImageView) holder.getView(R.id.play_reply_head));
            ((ImageView) holder.getView(R.id.play_reply_head)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    paramStartActivity(FansHomeActivity.class, item.getMember_id() + "");
                }
            });
            if ("1".equals(item.getIs_liked())) {
                holder.setImageResource(R.id.play_reply_thumb_up_iv, R.drawable.thumb_up_selected);
                ((TextView)holder.getView(R.id.play_reply_thumb_up_count))
                        .setTextColor(getResources().getColor(android.R.color.holo_red_light));
            } else {
                holder.setImageResource(R.id.play_reply_thumb_up_iv, R.drawable.thumb_up_no_select);
                ((TextView)holder.getView(R.id.play_reply_thumb_up_count))
                        .setTextColor(getResources().getColor(android.R.color.darker_gray));
            }
            ((ImageView) holder.getView(R.id.play_reply_thumb_up_iv)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!LoginHelper.getInstance().isOnline()) {
                        actionStartActivity(LoginActivity.class);
                        return;
                    }
                    if ("1".equals(item.getIs_liked())) {
                        mModel.commentUnlike(mHelper.getUserId(), item.getId() + "", new BaseCallback<String>() {
                            @Override
                            public void onSuccess(String data) {
                                showToast("取消点赞评论成功！");
                                holder.setImageResource(R.id.play_reply_thumb_up_iv, R.drawable.thumb_up_no_select);
                                item.setIs_liked("0");
                                item.setLike_count(item.getLike_count() - 1);
                                ((TextView)holder.getView(R.id.play_reply_thumb_up_count))
                                        .setTextColor(getResources().getColor(android.R.color.darker_gray));
                                notifyDataSetChanged();

                            }

                            @Override
                            public void onFail(String msg) {
                                showToast(msg);
                            }
                        });
                    } else {
                        mModel.commentLike(mHelper.getUserId(), item.getId() + "", new BaseCallback<String>() {
                            @Override
                            public void onSuccess(String data) {
                                showToast("点赞评论成功！");
                                holder.setImageResource(R.id.play_reply_thumb_up_iv, R.drawable.thumb_up_selected);
                                item.setIs_liked("1");
                                item.setLike_count(item.getLike_count() + 1);
                                ((TextView)holder.getView(R.id.play_reply_thumb_up_count))
                                        .setTextColor(getResources().getColor(android.R.color.holo_red_light));
                                notifyDataSetChanged();
                            }

                            @Override
                            public void onFail(String msg) {
                                showToast(msg);
                            }
                        });
                    }
                }
            });
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            // 解析传回的话题并显示在[说点什么…]中，以[@username ]区分
            if (data != null) {

                SearchUserBean.HaveUserBean resultFriend = ((SearchUserBean.HaveUserBean) data.getSerializableExtra(Constant.CHOOSE_FRIEND));
                RichEditText.XlpsFriend friend = new RichEditText.XlpsFriend();
                friend.mUserId = resultFriend.getId();
                friend.mUserName = resultFriend.getNickname();
                mCurEt.addFriend(friend);

                if ("0".equals(at)) {
                    at = resultFriend.getId() + "";
                } else {
                    at = at + "," + resultFriend.getId();
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (ksyMediaPlayer != null) {
            ksyMediaPlayer.pause();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isFront&&ksyMediaPlayer != null) {
            ksyMediaPlayer.seekTo(0);
            ksyMediaPlayer.start();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser){
            isFront = true;
            // load data here：fragment可见时执行加载数据或者进度条等
            if (ksyMediaPlayer != null) {
                ksyMediaPlayer.seekTo(0);
                ksyMediaPlayer.start();
                mHandler.sendEmptyMessageDelayed(0,300);//给主线程发送一个隐藏图片的消息

            }
        }else {
            isFront = false;
            // fragment is no longer visible：不可见时不执行操作
            if (ksyMediaPlayer != null){
                ksyMediaPlayer.pause();
                mDataBinding.playVideoCoverIv.setVisibility(View.VISIBLE);
            }
        }
    }
}

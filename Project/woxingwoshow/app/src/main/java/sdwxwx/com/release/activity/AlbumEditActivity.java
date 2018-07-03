package sdwxwx.com.release.activity;

import android.media.MediaMetadataRetriever;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.ksyun.media.shortvideo.kit.KSYEditKit;
import com.ksyun.media.shortvideo.utils.ShortVideoConstants;
import com.ksyun.media.streamer.kit.StreamerConstants;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.annotations.Nullable;
import sdwxwx.com.R;
import sdwxwx.com.cons.Constant;
import sdwxwx.com.release.videorange.HorizontalListView;
import sdwxwx.com.release.videorange.VideoRangeSeekBar;
import sdwxwx.com.release.videorange.VideoThumbnailAdapter;
import sdwxwx.com.release.videorange.VideoThumbnailInfo;

/**
 * 相册选择视频编辑截取画面
 */

public class AlbumEditActivity extends AppCompatActivity {

    private final String TAG = "AlbumEditActivity";

    // 视频最长截取15秒
    private final int CUT_MAX_DURATION = 15 * 1000;
    // 视频路径
    private String mLocalPath;
    // 截取后视频保存路径
    private String mSaveCutPath;
    // 视频预览时长
    private long mPreviewLength;
    private MediaMetadataRetriever mediaMetadataRetriever;

    // 开始SeekBar位置
    private long mStartSample;
    // 截止SeekBar位置
    private long mEndSample;
    private int mScreenWidth;
    private int mScreenHeight;

    //for video range
    private HorizontalListView mVideoThumbnailList;
    private VideoRangeSeekBar mVideoRangeSeekBar;
//    private VideoRecyclerThumbnailAdapter mVideoThumbnailAdapter;
    private VideoThumbnailAdapter mVideoThumbnailAdapter;
    private float mHLVOffsetX = 0.0f;
    private long mEditPreviewDuration;
    private TextView mVideoRangeText;
    private float mLastX = 0;
    private Handler mMainHandler;
    private KSYEditKit mEditKit;
    private GLSurfaceView mEditPreviewView;
    // 缩略图List
    private List<VideoThumbnailInfo> listData = new ArrayList<>();

    // 旋转角度
    private int mRotateDegrees = 0;

    // 合成Flag
    private boolean mComposeFinished = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_edit);

        //must set
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        mScreenWidth = getResources().getDisplayMetrics().widthPixels;
        mScreenHeight = getResources().getDisplayMetrics().heightPixels;
        mLocalPath = getIntent().getStringExtra(Constant.COMPOSE_PATH);
//        mPreviewLength = getIntent().getLongExtra(Constant.PREVIEW_LEN, 0);
        mediaMetadataRetriever = new MediaMetadataRetriever();
        mediaMetadataRetriever.setDataSource(mLocalPath);
        // 取得视频的长度(单位为毫秒)
        String time = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
        // 单位为微秒
        mPreviewLength = Long.valueOf(time) * 1000;
        mStartSample = 0;
        // 所选视频时长 < 60秒
//        if (mPreviewLength <= CUT_MAX_DURATION) {
//            mEndSample = mPreviewLength;
//        } else {
//            mEndSample = CUT_MAX_DURATION;
//        }
        initViews();

        Log.d(TAG, "onCreate end");
    }

    /**
     * View初始化
     */
    private void initViews() {
        Log.d(TAG, "initViews start");

        // 视频预览区域
        mEditPreviewView = findViewById(R.id.album_edit_preview);

        mMainHandler = new Handler();
        mEditKit = new KSYEditKit(this);
        mEditKit.setDisplayPreview(mEditPreviewView);
        mEditKit.setOnErrorListener(mOnErrorListener);
        mEditKit.setOnInfoListener(mOnInfoListener);
        if (!TextUtils.isEmpty(mLocalPath)) {
            mEditKit.setEditPreviewUrl(mLocalPath);
        }
        mPreviewLength = mEditKit.getEditDuration();

        initVideoRange();
        startEditPreview();
        Log.d(TAG, "initViews end");
    }

    // TODO:裁剪
//    public void resizePreview(int type, int mode) {
//        mScaleType = type;
//        mScaleMode = mode;
//
//        mEditKit.setScaleType(type);
//        mEditKit.setScalingMode(mScaleMode);
//
//        //默认全屏显示预览
//        int previewWidth = mScreenWidth;
//        int previewHeight = mScreenHeight;
//
//        //根据不同比例来更新预览区域大小
//        //to 3:4
//        if (mScaleType == KSYEditKit.SCALE_TYPE_3_4) {
//            previewHeight = previewWidth * 4 / 3;
//        }
//        //to 1:1
//        if (mScaleType == KSYEditKit.SCALE_TYPE_1_1) {
//            previewHeight = previewWidth;
//        }
//
//        RelativeLayout.LayoutParams previewParams = (RelativeLayout.LayoutParams) mPreviewLayout
//                .getLayoutParams();
//        previewParams.height = previewHeight;
//        previewParams.width = previewWidth;
//
//        mPreviewLayout.setLayoutParams(previewParams);
//    }

    /**
     * 各个View的点击事件
     *
     * @param view 操作View
     */
    public void onMenuClick(View view) {
        switch (view.getId()) {
            case R.id.album_edit_back_btn:
                onBackPressed();
                break;
            case R.id.album_edit_next_btn:
                saveToAPPPath();
//                Intent intent = new Intent();
//                intent.putExtra()
//
//                setResult(RESULT_CODE, intent);
                break;
            default:
                break;
        }
    }


    private void startEditPreview() {
        //设置预览的原始音频的音量
        mEditKit.setOriginAudioVolume(0.4f);
        //设置是否循环预览
        mEditKit.setLooping(true);
        //开启预览
        mEditKit.startEditPreview();

    }

    private void saveToAPPPath() {
        Log.d(TAG, "saveToAPPPath start");

        // 截取的视频存放路径
        String cutPath = getFilesDir().getPath() + Constant.RECORD_BOX_NAME;
        File saveCutDirectory = new File(cutPath);
        if (!saveCutDirectory.exists()) {
            saveCutDirectory.mkdirs();
        }
        StringBuilder composeUrl = new StringBuilder(cutPath)
                .append("/")
                .append(System.currentTimeMillis())
                .append(Constant.VIDEO_TYPE_NAME);
        Log.d(TAG, "compose Url:" + composeUrl);
        mSaveCutPath = composeUrl.toString();
        //开始合成
        mComposeFinished = false;
        mEditKit.startCompose(composeUrl.toString());
        Log.d(TAG, "saveToAPPPath end");
    }

    /**
     * init video range ui
     */
    private void initVideoRange() {
        Log.d(TAG, "initVideoRange start");
        //裁剪时间
        mVideoRangeText = findViewById(R.id.album_edit_txt);
        mVideoRangeText.setText(String.valueOf(mPreviewLength/10));

        // 裁剪bar
        mVideoRangeSeekBar = findViewById(R.id.seek_bar);
        mVideoRangeSeekBar.setOnVideoMaskScrollListener(mVideoMaskScrollListener);
        mVideoRangeSeekBar.setOnRangeBarChangeListener(onRangeBarChangeListener);

        // 缩略图显示
        mVideoThumbnailList = (HorizontalListView) findViewById(R.id.edit_img_list);

//        LinearLayoutManager llm = new LinearLayoutManager(
//                this, LinearLayoutManager.HORIZONTAL, false);
//        mVideoThumbnailList.setLayoutManager(llm);
//        // 显示头部
//        mVideoThumbnailList.smoothScrollToPosition(0);
//        mVideoThumbnailList.setOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//                Log.d(TAG, "OnScrollListener    onScrollStateChanged start");
//
//            }
//
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                Log.d(TAG, "OnScrollListener    onScrolled start");
//            }
//        });
        mVideoThumbnailList.setOnScrollListener(mVideoThumbnailScrollListener);

        Log.d(TAG, "initVideoRange end");
    }

    HorizontalListView.OnScrollListener mVideoThumbnailScrollListener = new HorizontalListView.OnScrollListener() {

        @Override
        public void onScroll(final int currentX) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    mMainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mHLVOffsetX = mVideoRangeSeekBar.getRange(currentX);

                            if (mEditPreviewDuration > CUT_MAX_DURATION) {
                                if ((mVideoRangeSeekBar.getRangeEnd() + mHLVOffsetX) * 1000 >= mEditPreviewDuration) {
                                    mHLVOffsetX = (mEditPreviewDuration / 1000 - mVideoRangeSeekBar.getRangeEnd());
                                }
                            }

                            setRangeTextView(mHLVOffsetX);

                            if (mLastX != mVideoRangeSeekBar.getRangeStart() + mHLVOffsetX) {
                                //do not need to effect
                                //rangeLoopPreview();
                                mLastX = mVideoRangeSeekBar.getRangeStart() + mHLVOffsetX;
                            }
                        }
                    });
                }
            }).start();

        }
    };

    /**
     * init video thumbnail
     */
    private void initThumbnailAdapter() {
        Log.d(TAG, "initThumbnailAdapter start");

        float picWidth;  //每个thumbnail显示的宽度
        if (mVideoRangeSeekBar == null) {
            picWidth = 60;
        } else {
            picWidth = mVideoRangeSeekBar.getFrameWidth();
        }

        long durationMS = mEditKit.getEditDuration();
        Log.d(TAG, "initThumbnailAdapter 时长durationMS = " + durationMS);

        //list区域需要显示的item个数
        int totalFrame;
        //比最大裁剪时长大的视频,每长mMaxClipSpanMs长度,则增加5个thumbnail
        //比最大裁剪时长小的视频,最多显示3秒1个thumbnail
        if (durationMS > CUT_MAX_DURATION) {
//            totalFrame = (int) (durationMS * 5) / CUT_MAX_DURATION;
            totalFrame = (int) (durationMS * 8) / CUT_MAX_DURATION;
        } else {
//            totalFrame = (int) durationMS / (3 * 1000);
            totalFrame = 10;
        }

        int mm = totalFrame;

//        VideoThumbnailInfo videoThumbnailInfo;
//        for (int i = 0; i < totalFrame; i++) {
//            videoThumbnailInfo = new VideoThumbnailInfo();
//            if (durationMS > CUT_MAX_DURATION) {
//                videoThumbnailInfo.mCurrentTime = i * ((float) durationMS / 1000) * (1.0f / mm);
//            } else {
//                if (i > 0 && i < 5) {
//                    videoThumbnailInfo.mCurrentTime = (i - 1) * ((float) durationMS / 1000) * (1.0f / 8);
//                }
//            }
//            videoThumbnailInfo.mType = VideoThumbnailInfo.TYPE_NORMAL;
//            videoThumbnailInfo.mWidth = getResources().getDimensionPixelSize(R.dimen.release_cover_img_width);
//            listData.add(videoThumbnailInfo);
//        }
        VideoThumbnailInfo[] listData = new VideoThumbnailInfo[totalFrame];
        for (int i = 0; i < totalFrame; i++) {
            listData[i] = new VideoThumbnailInfo();
            if (durationMS > CUT_MAX_DURATION) {
                listData[i].mCurrentTime = i * ((float) durationMS / 1000) * (1.0f / mm);
            } else {
                if (i > 0 && i < 9) {
                    listData[i].mCurrentTime = (i - 1) * ((float) durationMS / 1000) * (1.0f / 8);
                }
            }

            if (i == 0 && mVideoRangeSeekBar != null) {
                listData[i].mType = VideoThumbnailInfo.TYPE_START;
                listData[i].mWidth = (int) mVideoRangeSeekBar.getMaskWidth();
            } else if (i == totalFrame - 1 && mVideoRangeSeekBar != null) {
                listData[i].mType = VideoThumbnailInfo.TYPE_END;
                listData[i].mWidth = (int) mVideoRangeSeekBar.getMaskWidth();
            } else {
                listData[i].mType = VideoThumbnailInfo.TYPE_NORMAL;
                listData[i].mWidth = (int) picWidth;
            }
        }

//        mVideoThumbnailAdapter = new VideoRecyclerThumbnailAdapter(this, listData, mEditKit);
//        mVideoThumbnailList.setAdapter(mVideoThumbnailAdapter);
//        ((LinearLayoutManager)mVideoThumbnailList.getLayoutManager()).scrollToPositionWithOffset(-1, 0);

        mVideoThumbnailAdapter = new VideoThumbnailAdapter(this, listData, mEditKit);
        mVideoThumbnailList.setAdapter(mVideoThumbnailAdapter);

        Log.d(TAG, "initThumbnailAdapter end");
    }

    private KSYEditKit.OnErrorListener mOnErrorListener = new KSYEditKit.OnErrorListener() {
        @Override
        public void onError(int type, long msg) {
            Log.d(TAG, "mOnErrorListener onError type = " + type);

            switch (type) {
                case ShortVideoConstants.SHORTVIDEO_ERROR_COMPOSE_FAILED_UNKNOWN:
                case ShortVideoConstants.SHORTVIDEO_ERROR_COMPOSE_FILE_CLOSE_FAILED:
                case ShortVideoConstants.SHORTVIDEO_ERROR_COMPOSE_FILE_FORMAT_NOT_SUPPORTED:
                case ShortVideoConstants.SHORTVIDEO_ERROR_COMPOSE_FILE_OPEN_FAILED:
                case ShortVideoConstants.SHORTVIDEO_ERROR_COMPOSE_FILE_WRITE_FAILED:
                    Log.d(TAG, "compose failed:" + type);
                    Toast.makeText(AlbumEditActivity.this,
                            "Compose Failed:" + type, Toast.LENGTH_LONG).show();
//                    if (mComposeDialog != null && mComposeDialog.isShowing()) {
//                        mComposeDialog.closeDialog();
//                        resumeEditPreview();
//                    }
                    break;
                case ShortVideoConstants.SHORTVIDEO_ERROR_SDK_AUTHFAILED:
                    Log.d(TAG, "sdk auth failed:" + type);
                    Toast.makeText(AlbumEditActivity.this,
                            "Auth failed can't start compose:" + type, Toast.LENGTH_LONG).show();
//                    if (mComposeDialog != null) {
//                        mComposeDialog.closeDialog();
//                        resumeEditPreview();
//                    }
                    break;
                case ShortVideoConstants.SHORTVIDEO_EDIT_PREVIEW_PLAYER_ERROR:
                    Log.d(TAG, "KSYEditKit preview player error:" + type + "_" + msg);
                    break;
                case ShortVideoConstants.SHORTVIDEO_ERROR_EDIT_FEATURE_NOT_SUPPORTED:
                    Toast.makeText(AlbumEditActivity.this,
                            "This Feature not support:" + type, Toast.LENGTH_LONG).show();
                    break;
                case StreamerConstants.KSY_STREAMER_VIDEO_ENCODER_ERROR_UNSUPPORTED:
                case StreamerConstants.KSY_STREAMER_VIDEO_ENCODER_ERROR_UNKNOWN:
//                    boolean result = handleEncodeError();
//                    if (result) {
//                        mMainHandler.postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                startCompose();
//                            }
//                        }, 200);
//                    }
                    break;
                default:
                    break;
            }
        }
    };

    private KSYEditKit.OnInfoListener mOnInfoListener = new KSYEditKit.OnInfoListener() {
        @Override
        public void onInfo(int type, String... msgs) {
            Log.d(TAG, "mOnInfoListener onError type = " + type);
            switch (type) {
                case ShortVideoConstants.SHORTVIDEO_EDIT_PREPARED:
                    Log.d(TAG, "preview player prepared");
                    mEditPreviewDuration = mEditKit.getEditDuration();
//                    mPreviewLength = mEditPreviewDuration;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            initSeekBar();
                            initThumbnailAdapter();
//                            // 启动预览后，开始片段编辑UI初始化
//                            mSectionView.init(mEditPreviewDuration, mEditKit);
//                            mEffectsView.initView(mEditPreviewDuration, mEditKit);
//                            startPreviewTimerTask();
                        }
                    });
                    break;
                case ShortVideoConstants.SHORTVIDEO_EDIT_PREVIEW_ONE_LOOP_END:
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            if (mEffectsView.getVisibility() == View.VISIBLE) {
//                                mEffectsView.setProgress(mEditKit.getEditDuration());
//                            }
//                        }
//                    });
                    break;
                case ShortVideoConstants.SHORTVIDEO_COMPOSE_START: {
                    Log.d(TAG, "compose started");
                    //合成开始null，再次预览时重新创建
//                    clearImgFilter();
                    mEditKit.pauseEditPreview();
                    break;
                }
                case ShortVideoConstants.SHORTVIDEO_COMPOSE_FINISHED: {
                    Log.d(TAG, "compose finished");
                    mComposeFinished = true;
                    RecordEditActivity.startActivity(AlbumEditActivity.this, mSaveCutPath,
                            false, Constant.EMPTY_STRING, Constant.ZERO_STRING);
                    break;
                }
                case ShortVideoConstants.SHORTVIDEO_COMPOSE_ABORTED:
                    Log.d(TAG, "compose aborted by user");
                    break;
                case ShortVideoConstants.SHORTVIDEO_EDIT_PREVIEW_PLAYER_INFO:
                    Log.d(TAG, "KSYEditKit preview player info:" + type + "_" + msgs[0]);
                    break;
                case ShortVideoConstants.SHORTVIDEO_COMPOSE_TAIL_STARTED:
                    Log.d(TAG, "tail video compose started");
                    break;
                case ShortVideoConstants.SHORTVIDEO_COMPOSE_TITLE_STARTED:
                    Log.d(TAG, "title video compose started");
                default:
                    break;
            }
            return;
        }
    };


    VideoRangeSeekBar.OnVideoMaskScrollListener mVideoMaskScrollListener = new VideoRangeSeekBar.OnVideoMaskScrollListener() {

        @Override
        public void onVideoMaskScrollListener(VideoRangeSeekBar rangeBar,
                                              MotionEvent event) {
            Log.d(TAG, "mVideoMaskScrollListener onVideoMaskScrollListener event.getAction() = " + event.getAction());
            if (!rangeBar.mLeftThumb.mIsPressed && !rangeBar.mRightThumb.mIsPressed) {
                mVideoThumbnailList.dispatchTouchEvent(event);
            }
        }
    };

    VideoRangeSeekBar.OnRangeBarChangeListener onRangeBarChangeListener = new VideoRangeSeekBar.OnRangeBarChangeListener() {

        @Override
        public void onIndexChangeListener(VideoRangeSeekBar rangeBar,
                                          float rangeStart, float rangeEnd, final int change, boolean toEnd) {

            float toLen = (mVideoRangeSeekBar.getRangeEnd() + mHLVOffsetX) * 1000;
            if (toEnd && toLen >= CUT_MAX_DURATION && CUT_MAX_DURATION > 0 && toLen <= mEditPreviewDuration) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(AlbumEditActivity.this, "视频总长不能超过" + CUT_MAX_DURATION / 1000 + "秒 " +
                                "T_T", Toast.LENGTH_LONG);
                    }
                });
            }
            new Thread(new Runnable() {
                @Override
                public void run() {
                    mMainHandler.post(new Runnable() {
                        @Override
                        public void run() {
//                            if (mHLVOffsetX >= 5.5f && mHLVOffsetX <= 10.5f
//                                    && !mVideoRangeSeekBar.isTouching()) {
//                                mHLVOffsetX = 5.0f;
//                                mVideoRangeSeekBar.setRange(mVideoRangeSeekBar.getRangeStart(),
//                                        mVideoRangeSeekBar.getRangeStart() + mHLVOffsetX);
//                            }
                            setRangeTextView(mHLVOffsetX);
                        }
                    });
                }
            }).start();
        }

        @Override
        public void onActionUp() {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    rangeLoopPreview();
                }
            }).start();

        }

        @Override
        public void onActionMove() {
            if ((int)(mVideoRangeSeekBar.getRangeEnd() - mVideoRangeSeekBar.getRangeStart()) <= 5) {
                if (mVideoRangeSeekBar.mLeftThumb.mIsPressed) {
                    mVideoRangeSeekBar.setRange(mVideoRangeSeekBar.getRangeEnd() - 5.0f, mVideoRangeSeekBar.getRangeEnd());
                } else if (mVideoRangeSeekBar.mRightThumb.mIsPressed) {
                    mVideoRangeSeekBar.setRange(mVideoRangeSeekBar.getRangeStart(), mVideoRangeSeekBar.getRangeStart() + 5.0f);
                }
            }
            setRangeTextView(mHLVOffsetX);
        }

    };

    /**
     * loop preview during range
     */
    private void rangeLoopPreview() {
        Log.d(TAG, "rangeLoopPreview start");
        long startTime = (long) ((mVideoRangeSeekBar.getRangeStart() + mHLVOffsetX) * 1000);
        long endTime = (long) ((mVideoRangeSeekBar.getRangeEnd() + mHLVOffsetX) * 1000);

        //是否对播放区间的设置立即生效，true为立即生效
        mEditKit.setEditPreviewRanges(startTime, endTime, true);

        Log.d(TAG, "rangeLoopPreview end");
    }

    /**
     * init video seek range
     */
    private void initSeekBar() {
        Log.d(TAG, "initSeekBar start");

//        long durationMS = mEditKit.getEditDuration();
//        float durationInSec = durationMS * 1.0f / 1000;
//        if (durationMS < CUT_MAX_DURATION) {
//            mVideoRangeSeekBar.setMaxRange(durationInSec);
//            mVideoRangeSeekBar.setRange(0.0f, durationInSec);
//        } else {
//            mVideoRangeSeekBar.setMaxRange(CUT_MAX_DURATION * 1.0f / 1000);
//            mVideoRangeSeekBar.setRange(0.0f, CUT_MAX_DURATION * 1.0f / 1000);
//        }
//        mVideoRangeSeekBar.setMinRange(1.7f);
        long durationMS = mEditKit.getEditDuration();
        float durationInSec = durationMS * 1.0f / 1000;
        if (durationMS > CUT_MAX_DURATION) {
            mVideoRangeSeekBar.setMaxRange(CUT_MAX_DURATION * 1.0f / 1000);
        } else {
            mVideoRangeSeekBar.setMaxRange(durationInSec);
        }

        mVideoRangeSeekBar.setMinRange(1.0f);

        if (durationInSec > 300.0f) {
            mVideoRangeSeekBar.setRange(0.0f, 300.0f);
        } else {
            mVideoRangeSeekBar.setRange(0.0f, durationInSec);
        }

        Log.d(TAG, "initSeekBar end");
    }

    private void setRangeTextView(float offset) {
        Log.d(TAG, "setRangeTextView start" + offset);
        mVideoRangeText.setText(String.format(getResources().getString(R.string.release_album_edit_tx),
                String.valueOf((int) (mVideoRangeSeekBar.getRangeEnd())
                - (int) (mVideoRangeSeekBar.getRangeStart()))));
//        mVideoRangeText.setText(formatTimeStr2(((int) (10 * mVideoRangeSeekBar.getRangeEnd()))
//                - (int) (10 * mVideoRangeSeekBar.getRangeStart())));
//        mVideoRangeStart.setText(formatTimeStr(mVideoRangeSeekBar.getRangeStart() + offset));
//        mVideoRangeEnd.setText(formatTimeStr(mVideoRangeSeekBar.getRangeEnd() + offset));
//
//        mVideoRange.setText(formatTimeStr2(((int) (10 * mVideoRangeSeekBar.getRangeEnd()))
//                - (int) (10 * mVideoRangeSeekBar.getRangeStart())));
//        mPreviewLength = (long) (mVideoRangeSeekBar.getRangeEnd() -
//                mVideoRangeSeekBar.getRangeStart()) * 1000;
//        if (mAudioSeekLayout != null && mAudioLength != 0 &&
//                mPreviewLength < mAudioLength) {
//            mAudioSeekLayout.updateAudioSeekUI(mAudioLength, mPreviewLength);
//        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume start");

        mEditKit.onResume();

        // 隐藏虚拟键盘
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        Log.d(TAG, "onResume end");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onPause() {
        super.onPause();
        mEditKit.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy start");

        if (mMainHandler != null) {
            mMainHandler.removeCallbacksAndMessages(null);
            mMainHandler = null;
        }
        mEditKit.stopEditPreview();
        mEditKit.release();
        Log.d(TAG, "onDestroy end");
    }
}

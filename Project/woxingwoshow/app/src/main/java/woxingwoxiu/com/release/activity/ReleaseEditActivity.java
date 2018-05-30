package woxingwoxiu.com.release.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.AppCompatSeekBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ksyun.media.player.IMediaPlayer;
import com.ksyun.media.shortvideo.kit.KSYEditKit;
import com.ksyun.media.shortvideo.utils.FileUtils;
import com.ksyun.media.shortvideo.utils.ShortVideoConstants;
import com.ksyun.media.streamer.filter.audio.AudioReverbFilter;
import com.ksyun.media.streamer.filter.audio.KSYAudioEffectFilter;
import com.ksyun.media.streamer.filter.imgtex.ImgBeautySpecialEffectsFilter;
import com.ksyun.media.streamer.filter.imgtex.ImgBeautyStylizeFilter;
import com.ksyun.media.streamer.filter.imgtex.ImgFilterBase;
import com.ksyun.media.streamer.kit.StreamerConstants;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import woxingwoxiu.com.R;

import woxingwoxiu.com.cons.Constant;
import woxingwoxiu.com.cons.EventMessageBean;
import woxingwoxiu.com.home.HomeActivity;
import woxingwoxiu.com.release.adapter.BottomTitleAdapter;
import woxingwoxiu.com.release.adapter.ImageTextAdapter;
import woxingwoxiu.com.release.audiorange.AudioSeekLayout;
import woxingwoxiu.com.release.utils.DataFactory;
import woxingwoxiu.com.release.utils.ShortVideoConfig;
import woxingwoxiu.com.release.utils.SystemStateObtainUtil;

/**
 * 编辑合成示例窗口
 * 水印
 * 美颜
 * 滤镜
 * 变速
 * 视频裁剪
 * 背景音：音量调节、裁剪
 * 原始音频：音量调节、变声、混响
 * 字幕
 * 静态贴纸
 * 合成后文件上传ks3
 */

public class ReleaseEditActivity extends Activity implements
        ActivityCompat.OnRequestPermissionsResultCallback {
    private static String TAG = "ReleaseEditActivity";

    private static final int REQUEST_CODE = 10010;
    private static final int FILTER_DISABLE = 0;

    private RelativeLayout mPreviewLayout;
    private GLSurfaceView mEditPreviewView;
    private RelativeLayout mBarBottomLayout;
    private RelativeLayout mBottomBtn;
    // 下一步
    private ImageView mNextView;
    // 返回按钮
    private ImageView mBackView;
    // 音乐设定按钮
    private ImageView mMusicView;
    // 滤镜设定按钮
    private ImageView mFilterView;
    // 音量设定按钮
    private ImageView mVolumView;

    private AppCompatSeekBar mOriginAudioVolumeSeekBar;
    private AppCompatSeekBar mBgmVolumeSeekBar;
    private View mFilterLayout; //滤镜
    private View mAudioEditLayout;  //bgm音频裁剪

    //滤镜
    private ImageView mFilterOriginImage;
    private ImageView mFilterBorder;
    private TextView mFilterOriginText;
    private RecyclerView mFilterRecyclerView;
    private ImageTextAdapter mFilterAdapter;
    private View[] mBottomViewList;
    private int mFilterTypeIndex = -1;

    private InputMethodManager mInputMethodManager;  //输入
//    private FilterEffectsView mEffectsView;

    //    private SectionSeekLayout mSectionView;  //片段编辑UI
    private Timer mPreviewRefreshTimer;
    private TimerTask mPreviewRefreshTask;  //跟随播放预览的缩略图自动滚动任务

    private ImageView mSpeedDown; //减速
    private ImageView mSpeedUp; //加速
    private TextView mSpeedInfo; //速度信息

    private boolean mFirstPlay = true;
    private AudioSeekLayout.OnAudioSeekChecked mAudioSeekListener;
    private long mAudioLength;  //背景音乐时长
    private long mPreviewLength; //视频裁剪后的时长
    private ComposeDialog mComposeDialog;
    private ShortVideoConfig mComposeConfig; //输出视频参数配置
    private SeekBarChangedObserver mSeekBarChangedObserver;

    // 视频合成后的存储路径
    public static String mLocalPath;
    public final static String SRC_URL = "src_url";
    public final static String MIME_TYPE = "mime_type";
    public final static String PREVIEW_LEN = "preview_length";
    private static final int AUDIO_FILTER_DISABLE = 0;  //不使用音频滤镜的类型标志

    private static final int BOTTOM_VIEW_NUM = 1;

    private KSYEditKit mEditKit; //编辑合成kit类
    private int mEffectFilterIndex = FILTER_DISABLE;  //滤镜filter type
    private int mLastEffectFilterIndex = FILTER_DISABLE;  //滤镜filter type
    private Map<Integer, ImgFilterBase> mEffectFilters;

    private boolean mComposeFinished = false;

    private Handler mMainHandler;
    private boolean mPaused = false;
    private int mBottomViewPreIndex;

    //for video range
    private static final int LONG_VIDEO_MAX_LEN = 300000;
    private float mHLVOffsetX = 0.0f;
    private long mEditPreviewDuration;

    private boolean mHWEncoderUnsupported;  //硬编支持标志位
    private boolean mSWEncoderUnsupported;  //软编支持标志位

    private int mScreenHeight;

    private int mRotateDegrees = 0;

    public static void startActivity(Context context, String srcurl) {
        Intent intent = new Intent(context, ReleaseEditActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        intent.putExtra(SRC_URL, srcurl);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.edit_activity);

        //must set
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        //默认设置为纵屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mScreenHeight = getResources().getDisplayMetrics().heightPixels;
//        mButtonObserver = new ButtonObserver();
        mSeekBarChangedObserver = new ReleaseEditActivity.SeekBarChangedObserver();

        mPreviewLayout = (RelativeLayout) findViewById(R.id.preview_layout);
        mEditPreviewView = (GLSurfaceView) findViewById(R.id.edit_preview);
        mBarBottomLayout = (RelativeLayout) findViewById(R.id.edit_bar_bottom);
        mBottomBtn = findViewById(R.id.edit_bottom_btn);

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mBarBottomLayout.getLayoutParams();
        params.height = mScreenHeight / 3;
        mBarBottomLayout.setLayoutParams(params);

        mBottomViewList = new View[BOTTOM_VIEW_NUM];

//        mPauseView = (ImageView) findViewById(R.id.click_to_pause);
//        mPauseView.setOnClickListener(mButtonObserver);
//        mPauseView.getDrawable().setLevel(2);
//        mRotateView = findViewById(R.id.click_to_rotate);
//        mRotateView.setOnClickListener(mButtonObserver);
//        mBeautyLayout = findViewById(R.id.beauty_choose);
//        mBottomViewList[BEAUTY_LAYOUT_INDEX] = mBeautyLayout;
        mFilterLayout = findViewById(R.id.edit_filter_choose);
        mBottomViewList[0] = mFilterLayout;
//        mSpeedLayout = findViewById(R.id.speed_layout);
//        mBottomViewList[SPEED_LAYOUT_INDEX] = mSpeedLayout;

//        mVideoScaleLayout = findViewById(R.id.video_scale_choose);
//        mBottomViewList[VIDEO_SCALE_INDEX] = mVideoScaleLayout;
//        mVideoRangeLayout = findViewById(R.id.video_range_choose);
//        mVideoScale9_16 = findViewById(R.id.click_to_9_16);
//        mVideoScale9_16.setOnClickListener(mButtonObserver);
//        mVideoScale3_4 = findViewById(R.id.click_to_3_4);
//        mVideoScale3_4.setOnClickListener(mButtonObserver);
//        mVideoScale1_1 = findViewById(R.id.click_to_1_1);
//        mVideoScale1_1.setOnClickListener(mButtonObserver);
//        mVideoScaleFit = findViewById(R.id.video_scale_fit);
//        mVideoScaleFit.setOnClickListener(mButtonObserver);
//        mVideoScaleCrop = findViewById(R.id.video_scale_crop);
//        mVideoScaleCrop.setOnClickListener(mButtonObserver);

//        mBottomViewList[VIDEO_RANGE_INDEX] = mVideoRangeLayout;
        mAudioEditLayout = findViewById(R.id.audio_choose);
//        mBottomViewList[MUSIC_LAYOUT_INDEX] = mAudioEditLayout;
//        mAudioSeekLayout = (AudioSeekLayout) findViewById(R.id.audioSeekLayout);
//        mSoundChangeLayout = findViewById(R.id.edit_sound_change);
//        mBottomViewList[SOUND_CHANGE_INDEX] = mSoundChangeLayout;
//        mReverbLayout = findViewById(R.id.edit_reverb);
//        mBottomViewList[REVERB_LAYOUT_INDEX] = mReverbLayout;
        mOriginAudioVolumeSeekBar = (AppCompatSeekBar) findViewById(R.id.record_mic_audio_volume);
        mOriginAudioVolumeSeekBar.setOnSeekBarChangeListener(mSeekBarChangedObserver);
        mBgmVolumeSeekBar = (AppCompatSeekBar) findViewById(R.id.record_music_audio_volume);
        mBgmVolumeSeekBar.setOnSeekBarChangeListener(mSeekBarChangedObserver);
//        mStickerLayout = findViewById(R.id.sticker_choose);
//        mBottomViewList[STICKER_LAYOUT_INDEX] = mStickerLayout;
//        mAnimatedStickerLayout = findViewById(R.id.animate_sticker_choose);
//        mBottomViewList[ANIMATED_STICKER_LAYOUT_INDEX] = mAnimatedStickerLayout;
//        mSubtitleLayout = findViewById(R.id.subtitle_choose);
//        mBottomViewList[SUBTITLE_LAYOUT_INDEX] = mSubtitleLayout;
//        mPaintMenuLayout = findViewById(R.id.paint_menu_choose);
//        mBottomViewList[PAINT_MENU_LAYOUT_INDEX] = mPaintMenuLayout;

//        mEffectsView = findViewById(R.id.edit_filter_effects);
//        mBottomViewList[0] = mEffectsView;
//
//        mEffectsView.setOnEffectsChangeListener(new FilterEffectsView.OnEffectsChangeListener() {
//            @Override
//            public int onAddFilterStart(int type) {
//                ImgFilterBase filterBase = null;
//                switch (type) {
//                    case 0:
//                        filterBase = new ImgShakeColorFilter(mEditKit.getGLRender());
//                        break;
//                    case 1:
//                        filterBase = new ImgShakeShockWaveFilter(mEditKit.getGLRender());
//                        break;
//                    case 2:
//                        filterBase = new ImgShakeZoomFilter(mEditKit.getGLRender());
//                        break;
//                    case 3:
//                        filterBase = new ImgBeautySpecialEffectsFilter(mEditKit.getGLRender(), ReleaseEditActivity.this,
//                                ImgBeautySpecialEffectsFilter.KSY_SPECIAL_EFFECT_LIGHTING);
//                        break;
//                    case 4:
//                        GPUImageSobelEdgeDetection sobelEdgeDetection = new GPUImageSobelEdgeDetection();
//                        filterBase = new ImgTexGPUImageFilter(mEditKit.getGLRender(), sobelEdgeDetection);
//                        break;
//                    case 5:
//                        filterBase = new ImgShake70sFilter(mEditKit.getGLRender());
//                        break;
//                    case 6:
//                        filterBase = new ImgShakeIllusionFilter(mEditKit.getGLRender());
//                        break;
//                    case 7:
//                        filterBase = new ImgShaderXSingleFilter(mEditKit.getGLRender());
//                        break;
//                    default:
//                        break;
//                }
//                TimerEffectInfo effectInfo = new TimerEffectInfo(mEditKit.getEditPreviewCurrentPosition(),
//                        mEditPreviewDuration, new TimerEffectFilter(filterBase));
//
//                int filterId = mEditKit.addTimerEffectFilter(effectInfo);
//                return filterId;
//            }
//
//            @Override
//            public void onUpdateFilter(int index) {
////                resumePreview();
//            }
//
//            @Override
//            public void onAddFilterEnd(int index, long position) {
//                //pause
////                pausePreview();
//                mEditKit.updateTimerEffectEndTime(index, position);
//            }
//
//            @Override
//            public void onDelete(int index) {
//                TimerEffectInfo info = mEditKit.getTimerEffectInfo(index);
//                if (mEditKit.getReversePlay()) {
//                    //在倒放状态添加时间特效时，由于ui 是seek到正序的时间，因此需要配合seek到结束时间,并且需要把结束时间算折算一下
//                    long endTime = mEditKit.getEditDuration() - info.endTime;
//                    mEditKit.seekTo(endTime);
//                } else {
//                    mEditKit.seekTo(info.startTime);
//                }
//
//                //pause
////                pausePreview();
//                mEditKit.removeTimerEffectFilter(index);
//            }
//
//            @Override
//            public void onProgressChanged(long position) {
//                mEditKit.seekTo(position);
////                mSectionView.scrollAuto(position);
//            }
//        });

//        mPaintView = findViewById(R.id.edit_paint_view);
//        mPaintView.setBgColor(Color.TRANSPARENT);
//        mPaintView.setPaintEnable(false);
//        mIsPainting = false;
//        mPaintMenuView = new PaintMenu(this, mPaintMenuLayout, mPaintView);
//        mPaintMenuView.setOnPaintOpera(new PaintMenu.OnPaintComplete() {
//            @Override
//            public void completePaint() {
//                mBottomViewList[mBottomViewPreIndex].setVisibility(View.GONE);
//                mPaintView.setPaintEnable(false);
//                mTitleAdapter.clear();
//            }
//        });

//        mKSYStickerView = (KSYStickerView) findViewById(R.id.sticker_panel);

//        //初始化动态贴纸UI
//        mAnimatedStickerList = (RecyclerView) findViewById(R.id.animated_stickers_list);
//        LinearLayoutManager listLayoutManager = new LinearLayoutManager(
//                this);
//        listLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
//        mAnimatedStickerList.setLayoutManager(listLayoutManager);
//        mAnimatedStickerAdapter = new StickerAdapter(this);
//        mAnimatedStickerList.setAdapter(mAnimatedStickerAdapter);
//        //Adapter中设置贴纸的路径，默认支持的是assets目录下面的，其它目录需要自行修改Adapter
//        mAnimatedStickerAdapter.addStickerImages(mAnimateStickerPath);
//        //添加Item选择事件用于添加动态贴纸
//        mAnimatedStickerAdapter.setOnStickerItemClick(mOnAnimatedStickerItemClick);
//
//        //初始化图片贴纸UI
//        mStickerList = (RecyclerView) findViewById(R.id.stickers_list);
//        LinearLayoutManager stickerListLayoutManager = new LinearLayoutManager(
//                this);
//        stickerListLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
//        mStickerList.setLayoutManager(stickerListLayoutManager);
//        mStickerAdapter = new StickerAdapter(this);
//        mStickerList.setAdapter(mStickerAdapter);
//        //Adapter中设置贴纸的路径，默认支持的是assets目录下面的，其它目录需要自行修改Adapter
//        mStickerAdapter.addStickerImages(mStickerPath);
//        //添加Item选择事件用于添加图片贴纸
//        mStickerAdapter.setOnStickerItemClick(mOnStickerItemClick);
//
//        //init 字幕贴纸UI
//        mTextInput = (EditText) findViewById(R.id.text_input);
//        mTextInput.addTextChangedListener(mTextInputChangedListener);
//        mTextColorSelect = (ImageView) findViewById(R.id.text_color);
//        initStickerHelpBox();
//        mTextStickerList = (RecyclerView) findViewById(R.id.text_stickers_list);
//        LinearLayoutManager textstickerListLayoutManager = new LinearLayoutManager(
//                this);
//        textstickerListLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
//        mTextStickerList.setLayoutManager(textstickerListLayoutManager);
//        mTextStickerAdapter = new StickerAdapter(this);
//        mTextStickerList.setAdapter(mTextStickerAdapter);
//        //Adapter中设置贴纸的路径，默认支持的是assets目录下面的，其它目录需要自行修改Adapter
//        mTextStickerAdapter.addStickerImages(mTextStickerPath);
//        //添加Item选择事件用于添加字幕贴纸
//        mTextStickerAdapter.setOnStickerItemClick(mOnTextStickerItemClick);
//        //字幕贴纸的颜色选择器
//        mColorPicker = new ColorPicker(this, 255, 255, 255);
//        mTextColorSelect.setOnClickListener(mButtonObserver);
//        //片段编辑
//        mSectionView = (SectionSeekLayout) findViewById(R.id.session_layout);

//        //变速
//        mSpeedDown = (ImageView) findViewById(R.id.speed_down);
//        mSpeedDown.setOnClickListener(mButtonObserver);
//        mSpeedUp = (ImageView) findViewById(R.id.speed_up);
//        mSpeedUp.setOnClickListener(mButtonObserver);
//        mSpeedInfo = (TextView) findViewById(R.id.speed_info);
//
//        mNextView = (ImageView) findViewById(R.id.click_to_next);
//        mNextView.setOnClickListener(mButtonObserver);
        // 下一步
        mNextView = findViewById(R.id.next_img);
        // 返回按钮
        mBackView = findViewById(R.id.back_img);
        // 音乐设定按钮
        mMusicView = findViewById(R.id.music_img);
        // 滤镜设定按钮
        mFilterView = findViewById(R.id.filter_img);
        // 音量设定按钮
        mVolumView = findViewById(R.id.volume_img);

        mMainHandler = new Handler();
        mEditKit = new KSYEditKit(this);
        mEditKit.setDisplayPreview(mEditPreviewView);
        mEditKit.setOnErrorListener(mOnErrorListener);
        mEditKit.setOnInfoListener(mOnInfoListener);
//        //添加贴纸View到SDK
//        mEditKit.addStickerView(mKSYStickerView);
//        mEditKit.setTimerEffectOverlying(false);

//        mSpeedInfo.setText(String.valueOf(mEditKit.getNomalSpeed()));
        Bundle bundle = getIntent().getExtras();
        String url = bundle.getString(SRC_URL);
        if (!TextUtils.isEmpty(url)) {
            mEditKit.setEditPreviewUrl(url);
        }
        mEditKit.setTimerEffectOverlying(false);

//        initTitleRecycleView();
//        initBeautyUI();
        initFilterUI();
//        initVideoRange();
        initBgmView();
//        initSoundEffectView();
//        initSticker();
        startEditPreview();

        mInputMethodManager = (InputMethodManager) this.getSystemService(Context
                .INPUT_METHOD_SERVICE);

        mEditKit.getAudioPlayerCapture().setOnPreparedListener(new IMediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(IMediaPlayer iMediaPlayer) {
                mAudioLength = iMediaPlayer.getDuration();
                mAudioSeekListener = new AudioSeekLayout.OnAudioSeekChecked() {
                    @Override
                    public void onActionUp(long start, long end) {
                        mEditKit.setBGMRanges(start, end, true);
                    }
                };
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        if (mAudioSeekLayout.getVisibility() != View.VISIBLE) {
//                            mAudioSeekLayout.setVisibility(View.VISIBLE);
//                            mAudioSeekLayout.setOnAudioSeekCheckedListener(mAudioSeekListener);
//                        }
//                        if (mFirstPlay) {
//                            mFirstPlay = false;
//                            mAudioSeekLayout.updateAudioSeekUI(mAudioLength, mPreviewLength);
//                        }
                    }
                });
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        mPaused = false;
        mEditKit.onResume();

        // 隐藏虚拟键盘
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        // 操作窗口如果正在显示，则关闭
        // 如果滤镜选择框正在显示，则关闭
        if (mFilterLayout.getVisibility() == View.VISIBLE) {
            mFilterLayout.setVisibility(View.GONE);
        }
        // 如果音量调节框正在显示，则关闭
        if (mAudioEditLayout.getVisibility() == View.VISIBLE) {
            mAudioEditLayout.setVisibility(View.GONE);
        }
        mBottomBtn.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed() {
        if (mFilterLayout.getVisibility() == View.VISIBLE) {
            // 如果滤镜选择框正在显示，则关闭
            mFilterLayout.setVisibility(View.GONE);
            mBottomBtn.setVisibility(View.VISIBLE);
        } else if (mAudioEditLayout.getVisibility() == View.VISIBLE) {
            // 如果音量调节框显示，则关闭
            mAudioEditLayout.setVisibility(View.GONE);
            mBottomBtn.setVisibility(View.VISIBLE);
        } else {
            // 返回前页面
            super.onBackPressed();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mPaused = true;
        mEditKit.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mMainHandler != null) {
            mMainHandler.removeCallbacksAndMessages(null);
            mMainHandler = null;
        }

        if (mComposeDialog != null) {
            mComposeDialog.closeDialog();
            mComposeDialog = null;
        }
        stopPreviewTimerTask();
        mEditKit.stopEditPreview();
        mEditKit.release();
    }

    /**
     * 打开系统文件夹，导入音频文件作为背景音乐
     */
    private void importMusicFile() {
        Intent target = FileUtils.createGetContentIntent();
        Intent intent = Intent.createChooser(target, "ksy_import_music_file");
        try {
            startActivityForResult(intent, REQUEST_CODE);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 选中本地背景音乐后返回结果处理
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE:
                // If the file selection was successful
                if (resultCode == RESULT_OK) {
                    mFirstPlay = true;
                    if (data != null) {
                        // Get the URI of the selected file
                        final Uri uri = data.getData();
                        Log.i(TAG, "Uri = " + uri.toString());
                        try {
                            // Get the file path from the URI
                            final String path = FileUtils.getPath(this, uri);
                            mEditKit.startBgm(path, true);
                            setEnableBgmEdit(true);
                        } catch (Exception e) {
                            Log.e(TAG, "File select error:" + e);
                        }
                    }
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private boolean isTouchPointInView(View view, int x, int y) {
        if (view == null) {
            return false;
        }
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int left = location[0];
        int top = location[1];
        int right = left + view.getMeasuredWidth();
        int bottom = top + view.getMeasuredHeight();
        if (y >= top && y <= bottom && x >= left
                && x <= right) {
            return true;
        }
        return false;
    }

    private void startEditPreview() {
        //设置预览的原始音频的音量
        mEditKit.setOriginAudioVolume(0.4f);
        //设置是否循环预览
        mEditKit.setLooping(true);
        //开启预览
        mEditKit.startEditPreview();

        mOriginAudioVolumeSeekBar.setProgress((int) (mEditKit.getOriginAudioVolume() * 100));
    }

    private void onNextClick() {
//        if (progressDialog == null || progressDialog.isShowing()) {
//            return;
//        }

//        progressDialog = new ProgressDialog(this);
//        progressDialog.setTitle("正在合成");
//        progressDialog.show();

        // 合成
        setOutputConfirm();
        startCompose();
    }

    /**
     * 显示正在合成的Dialog
     */
    private void showComposeDialog() {
        if (mComposeDialog != null && mComposeDialog.isShowing()) {
            mComposeDialog.closeDialog();
        }

        if (mComposeDialog == null) {
            mComposeDialog = new ComposeDialog(this, R.style.dialog);
        }
        mComposeDialog.show();
    }

    /**
     * 设置输出参数
     */
    private void setOutputConfirm() {
        showComposeDialog();
        // 输出参数实例：采用默认值
        mComposeConfig = new ShortVideoConfig();
        //配置合成参数
        if (mComposeConfig != null) {
            //配置合成参数
            mEditKit.setTargetResolution(mComposeConfig.resolution);
            mEditKit.setVideoFps(mComposeConfig.fps);
            mEditKit.setEncodeMethod(mComposeConfig.encodeMethod);
            mEditKit.setVideoCodecId(mComposeConfig.encodeType);
            mEditKit.setVideoEncodeProfile(mComposeConfig.encodeProfile);
            mEditKit.setAudioKBitrate(mComposeConfig.audioBitrate);
            mEditKit.setVideoKBitrate(mComposeConfig.videoBitrate);
            mEditKit.setVideoDecodeMethod(mComposeConfig.decodeMethod);
            mEditKit.setAudioEncodeProfile(mComposeConfig.audioEncodeProfile);
        }
    }

//    private void onOutputConfirmClick() {
//        confirmConfig();
//        if (mConfigDialog.isShowing()) {
//            mConfigDialog.dismiss();
//        }
//        showComposeDialog();
//        //配置合成参数
//        if (mComposeConfig != null) {
//            //配置合成参数
//            mEditKit.setTargetResolution(mComposeConfig.resolution);
//            mEditKit.setVideoFps(mComposeConfig.fps);
//            mEditKit.setEncodeMethod(mComposeConfig.encodeMethod);
//            mEditKit.setVideoCodecId(mComposeConfig.encodeType);
//            mEditKit.setVideoEncodeProfile(mComposeConfig.encodeProfile);
//            mEditKit.setAudioKBitrate(mComposeConfig.audioBitrate);
//            mEditKit.setVideoKBitrate(mComposeConfig.videoBitrate);
//            mEditKit.setVideoDecodeMethod(mComposeConfig.decodeMethod);
//            mEditKit.setTailUrl(mTailVideoPath);
//            mEditKit.addPaintView(mPaintView);
//            mEditKit.setAudioEncodeProfile(mComposeConfig.audioEncodeProfile);
//            startCompose();
//        }
//    }

    private void startCompose() {
        //设置合成路径
        String fileFolder = Environment.getExternalStorageDirectory().
                getAbsolutePath() + "/ksy_sv_compose_wxwx";
        File file = new File(fileFolder);
        if (!file.exists()) {
            file.mkdir();
        }

        StringBuilder composeUrl = new StringBuilder(fileFolder).append("/").append(System
                .currentTimeMillis());
        composeUrl.append(".mp4");
        Log.d(TAG, "compose Url:" + composeUrl);
        mLocalPath = composeUrl.toString();
        mComposeFinished = false;
        // 开始合成
        mEditKit.startCompose(composeUrl.toString());
    }

    /**
     * 不支持硬编的设备，fallback到软编
     */
    private boolean handleEncodeError() {
        int encodeMethod = mEditKit.getVideoEncodeMethod();
        if (encodeMethod == StreamerConstants.ENCODE_METHOD_HARDWARE) {
            mHWEncoderUnsupported = true;
            if (mSWEncoderUnsupported) {
                Log.e(TAG, "Got HW and SW encoder error, compose failed");
                return false;
            } else {
                mEditKit.setEncodeMethod(StreamerConstants.ENCODE_METHOD_SOFTWARE);
                Log.e(TAG, "Got HW encoder error, switch to SOFTWARE mode");
            }
        } else if (encodeMethod == StreamerConstants.ENCODE_METHOD_SOFTWARE) {
            mSWEncoderUnsupported = true;
            if (mHWEncoderUnsupported) {
                Log.e(TAG, "Got SW and HW encoder error, compose failed");
                return false;
            } else {
                mEditKit.setEncodeMethod(StreamerConstants.ENCODE_METHOD_HARDWARE);
                Log.e(TAG, "Got SW encoder error, switch to HARDWARE mode");
            }
        }
        return true;
    }

    private KSYEditKit.OnErrorListener mOnErrorListener = new KSYEditKit.OnErrorListener() {
        @Override
        public void onError(int type, long msg) {
            switch (type) {
                case ShortVideoConstants.SHORTVIDEO_ERROR_COMPOSE_FAILED_UNKNOWN:
                case ShortVideoConstants.SHORTVIDEO_ERROR_COMPOSE_FILE_CLOSE_FAILED:
                case ShortVideoConstants.SHORTVIDEO_ERROR_COMPOSE_FILE_FORMAT_NOT_SUPPORTED:
                case ShortVideoConstants.SHORTVIDEO_ERROR_COMPOSE_FILE_OPEN_FAILED:
                case ShortVideoConstants.SHORTVIDEO_ERROR_COMPOSE_FILE_WRITE_FAILED:
                    Log.d(TAG, "compose failed:" + type);
                    Toast.makeText(ReleaseEditActivity.this,
                            "Compose Failed:" + type, Toast.LENGTH_LONG).show();
                    if (mComposeDialog != null && mComposeDialog.isShowing()) {
                        mComposeDialog.closeDialog();
                        resumeEditPreview();
                    }
                    break;
                case ShortVideoConstants.SHORTVIDEO_ERROR_SDK_AUTHFAILED:
                    Log.d(TAG, "sdk auth failed:" + type);
                    Toast.makeText(ReleaseEditActivity.this,
                            "Auth failed can't start compose:" + type, Toast.LENGTH_LONG).show();
                    if (mComposeDialog != null) {
                        mComposeDialog.closeDialog();
                        resumeEditPreview();
                    }
                    break;
                case ShortVideoConstants.SHORTVIDEO_EDIT_PREVIEW_PLAYER_ERROR:
                    Log.d(TAG, "KSYEditKit preview player error:" + type + "_" + msg);
                    break;
                case ShortVideoConstants.SHORTVIDEO_ERROR_EDIT_FEATURE_NOT_SUPPORTED:
                    Toast.makeText(ReleaseEditActivity.this,
                            "This Feature not support:" + type, Toast.LENGTH_LONG).show();
                    break;
                case StreamerConstants.KSY_STREAMER_VIDEO_ENCODER_ERROR_UNSUPPORTED:
                case StreamerConstants.KSY_STREAMER_VIDEO_ENCODER_ERROR_UNKNOWN:
                    boolean result = handleEncodeError();
                    if (result) {
                        mMainHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                startCompose();
                            }
                        }, 200);
                    }
                    break;
                default:
                    break;
            }
        }
    };

    private KSYEditKit.OnInfoListener mOnInfoListener = new KSYEditKit.OnInfoListener() {
        @Override
        public void onInfo(int type, String... msgs) {
            switch (type) {
                case ShortVideoConstants.SHORTVIDEO_EDIT_PREPARED:
                    Log.d(TAG, "preview player prepared");
                    mEditPreviewDuration = mEditKit.getEditDuration();
                    mPreviewLength = mEditPreviewDuration;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                            initSeekBar();
//                            initThumbnailAdapter();
                            // 启动预览后，开始片段编辑UI初始化
//                            mSectionView.init(mEditPreviewDuration, mEditKit);
//                            mEffectsView.initView(mEditPreviewDuration, mEditKit);
                            startPreviewTimerTask();
                        }
                    });
                    break;
                case ShortVideoConstants.SHORTVIDEO_EDIT_PREVIEW_ONE_LOOP_END:
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                            if (mEffectsView.getVisibility() == View.VISIBLE) {
//                                mEffectsView.setProgress(mEditKit.getEditDuration());
//                            }
                        }
                    });
                    break;
                case ShortVideoConstants.SHORTVIDEO_COMPOSE_START: {
                    Log.d(TAG, "compose started");
                    //合成开始null，再次预览时重新创建
                    clearImgFilter();
                    mEditKit.pauseEditPreview();
                    if (mComposeDialog != null && mComposeDialog.isShowing()) {
                        mComposeDialog.composeStarted();
                    }
                    break;
                }
                case ShortVideoConstants.SHORTVIDEO_COMPOSE_FINISHED: {
                    Log.d(TAG, "compose finished");
                    if (mComposeDialog != null && mComposeDialog.isShowing()) {
                        mComposeDialog.composeFinished(msgs[0]);
                    }
                    mComposeFinished = true;

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

    private void startPreviewTimerTask() {
        mPreviewRefreshTimer = new Timer();
        mPreviewRefreshTask = new TimerTask() {
            @Override
            public void run() {
                refreshUiOnUiThread();
            }
        };
        // 定义顶部滚动view的刷新频率为20fps
        mPreviewRefreshTimer.schedule(mPreviewRefreshTask, 50, 50);
    }

    private void stopPreviewTimerTask() {
        if (mPreviewRefreshTimer != null) {
            mPreviewRefreshTimer.cancel();
            mPreviewRefreshTimer = null;
        }
        if (mPreviewRefreshTask != null) {
            mPreviewRefreshTask.cancel();
            mPreviewRefreshTask = null;
        }
//        mSectionView.stopPreview();
    }

    private void refreshUiOnUiThread() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                long curTime = mEditKit.getEditPreviewCurrentPosition();
            }
        });
    }

    /**
     * @param view 操作对象
     */
    public void onMenuClick(View view) {
        switch (view.getId()) {
            case R.id.back_img:
                onBackPressed();
                break;
            case R.id.filter_img:
                // 如果音量调节框显示，则关闭
                if (mAudioEditLayout.getVisibility() == View.VISIBLE) {
                    mAudioEditLayout.setVisibility(View.GONE);
                }
                // 如果滤镜选择框未显示，则关闭
                if (mFilterLayout.getVisibility() == View.VISIBLE) {
                    mFilterLayout.setVisibility(View.GONE);
                    mBottomBtn.setVisibility(View.VISIBLE);
                } else {
                    mFilterLayout.setVisibility(View.VISIBLE);
                    mBottomBtn.setVisibility(View.GONE);
                }
                break;
            case R.id.music_img:
                // 在线音乐选择画面
                Intent intent = new Intent(this, MusicOnlineActivity.class);
                startActivity(intent);
                break;
            case R.id.volume_img:
                // 如果滤镜选择框显示，则关闭
                if (mFilterLayout.getVisibility() == View.VISIBLE) {
                    mFilterLayout.setVisibility(View.GONE);
                }
                // 如果音量调节框显示，则关闭
                if (mAudioEditLayout.getVisibility() == View.VISIBLE) {
                    mAudioEditLayout.setVisibility(View.GONE);
                    mBottomBtn.setVisibility(View.VISIBLE);
                } else {
                    mAudioEditLayout.setVisibility(View.VISIBLE);
                    mBottomBtn.setVisibility(View.GONE);
                }
                break;
            case R.id.next_img:
                onNextClick();
                break;
            case R.id.save_to_draft:
                // 存储到草稿箱
                saveFileToDraftBox();
                break;
            default:
                break;
        }

    }

    private class SeekBarChangedObserver implements SeekBar.OnSeekBarChangeListener {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (!fromUser) {
                return;
            }
            float val = progress / 100.f;
            switch (seekBar.getId()) {
                case R.id.record_mic_audio_volume:
                    mEditKit.setOriginAudioVolume(val);
                    break;
                case R.id.record_music_audio_volume:
                    mEditKit.setBgmVolume(val);
                    break;
                default:
                    break;
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    }

    private void clearImgFilter() {
        mEffectFilterIndex = FILTER_DISABLE;
        if (mEffectFilters != null) {
            mEffectFilters.clear();
        }

        mFilterAdapter.clear();
        changeOriginalImageState(true);

    }

    private void addEffectFilter() {
        if (mLastEffectFilterIndex == mEffectFilterIndex) {
            return;
        }
        if (mEffectFilters == null) {
            mEffectFilters = new LinkedHashMap<>();
        }

        if (mEffectFilterIndex == FILTER_DISABLE) {
            if (mEffectFilters.containsKey(mLastEffectFilterIndex)) {
                ImgFilterBase lastFilter = mEffectFilters.get(mLastEffectFilterIndex);
                if (mEditKit.getImgTexFilterMgt().getFilter().contains(lastFilter)) {
                    mEditKit.getImgTexFilterMgt().replaceFilter(lastFilter, null, false);
                }
            }
            mLastEffectFilterIndex = mEffectFilterIndex;
            //暂停状态时，使得滤镜在预览区域立即生效
            mEditKit.queueLastFrame();
            return;
        }
        if (mEffectFilters.containsKey(mEffectFilterIndex)) {
            ImgFilterBase filter = mEffectFilters.get(mEffectFilterIndex);
            if (mEffectFilters.containsKey(mLastEffectFilterIndex)) {
                ImgFilterBase lastfilter = mEffectFilters.get(mLastEffectFilterIndex);
                if (mEditKit.getImgTexFilterMgt().getFilter().contains(lastfilter)) {
                    mEditKit.getImgTexFilterMgt().replaceFilter(lastfilter, filter, false);
                }
            } else {
                if (!mEditKit.getImgTexFilterMgt().getFilter().contains(filter)) {
                    mEditKit.getImgTexFilterMgt().addFilter(filter);
                }
            }
            mLastEffectFilterIndex = mEffectFilterIndex;
        } else {
            ImgFilterBase filter;
            if (mFilterTypeIndex < 13) {
                filter = new ImgBeautySpecialEffectsFilter(mEditKit.getGLRender(),
                        getApplicationContext(), mEffectFilterIndex);
            } else {
                filter = new ImgBeautyStylizeFilter(mEditKit
                        .getGLRender(), getApplicationContext(), mEffectFilterIndex);
            }
            mEffectFilters.put(mEffectFilterIndex, filter);
            ImgFilterBase lastFilter = null;
            if (mEffectFilters.containsKey(mLastEffectFilterIndex)) {
                lastFilter = mEffectFilters.get(mLastEffectFilterIndex);
            }
            if (lastFilter != null && mEditKit.getImgTexFilterMgt().getFilter().contains
                    (lastFilter)) {
                mEditKit.getImgTexFilterMgt().replaceFilter(lastFilter, filter, false);
            } else {
                mEditKit.getImgTexFilterMgt().addFilter(filter);
            }
            mLastEffectFilterIndex = mEffectFilterIndex;
        }
        //暂停状态时，使得滤镜在预览区域立即生效
        mEditKit.queueLastFrame();
    }

    private void setEffectFilter(int type) {
        mEffectFilterIndex = type;
        addEffectFilter();
    }

    /**
     * 视频滤镜
     * https://github.com/ksvc/KSYStreamer_Android/wiki/style_filter
     */
    private void initFilterUI() {
        final int[] FILTER_TYPE = {ImgBeautySpecialEffectsFilter.KSY_SPECIAL_EFFECT_FRESHY,
                ImgBeautySpecialEffectsFilter.KSY_SPECIAL_EFFECT_BEAUTY,
                ImgBeautySpecialEffectsFilter.KSY_SPECIAL_EFFECT_SWEETY,
                ImgBeautySpecialEffectsFilter.KSY_SPECIAL_EFFECT_SEPIA,
                ImgBeautySpecialEffectsFilter.KSY_SPECIAL_EFFECT_BLUE,
                ImgBeautySpecialEffectsFilter.KSY_SPECIAL_EFFECT_NOSTALGIA,
                ImgBeautySpecialEffectsFilter.KSY_SPECIAL_EFFECT_SAKURA,
                ImgBeautySpecialEffectsFilter.KSY_SPECIAL_EFFECT_SAKURA_NIGHT,
                ImgBeautySpecialEffectsFilter.KSY_SPECIAL_EFFECT_RUDDY_NIGHT,
                ImgBeautySpecialEffectsFilter.KSY_SPECIAL_EFFECT_SUNSHINE_NIGHT,
                ImgBeautySpecialEffectsFilter.KSY_SPECIAL_EFFECT_RUDDY,
                ImgBeautySpecialEffectsFilter.KSY_SPECIAL_EFFECT_SUSHINE,
                ImgBeautySpecialEffectsFilter.KSY_SPECIAL_EFFECT_NATURE,
                ImgBeautyStylizeFilter.KSY_FILTER_STYLE_AMARO,
                ImgBeautyStylizeFilter.KSY_FILTER_STYLE_BRANNAN,
                ImgBeautyStylizeFilter.KSY_FILTER_STYLE_EARLY_BIRD,
                ImgBeautyStylizeFilter.KSY_FILTER_STYLE_HUDSON,
                ImgBeautyStylizeFilter.KSY_FILTER_STYLE_LOMO,
                ImgBeautyStylizeFilter.KSY_FILTER_STYLE_NASHVILLE,
                ImgBeautyStylizeFilter.KSY_FILTER_STYLE_RISE,
                ImgBeautyStylizeFilter.KSY_FILTER_STYLE_TOASTER,
                ImgBeautyStylizeFilter.KSY_FILTER_STYLE_VALENCIA,
                ImgBeautyStylizeFilter.KSY_FILTER_STYLE_WALDEN,
                ImgBeautyStylizeFilter.KSY_FILTER_STYLE_XPROLL};
        List<ImageTextAdapter.Data> filterData = DataFactory.getImgFilterData(this);
        mFilterOriginImage = (ImageView) findViewById(R.id.iv_filter_origin);
        mFilterBorder = (ImageView) findViewById(R.id.iv_filter_border);
        mFilterOriginText = (TextView) findViewById(R.id.tv_filter_origin);
        changeOriginalImageState(true);
        mFilterRecyclerView = (RecyclerView) findViewById(R.id.filter_recyclerView);
        mFilterAdapter = new ImageTextAdapter(this, filterData);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mFilterRecyclerView.setLayoutManager(layoutManager);
        ImageTextAdapter.OnImageItemClickListener listener = new ImageTextAdapter.OnImageItemClickListener() {
            @Override
            public void onClick(int index) {
                if (mFilterOriginText.isActivated()) {
                    changeOriginalImageState(false);
                }
                mFilterTypeIndex = index;
                setEffectFilter(FILTER_TYPE[index]);
            }
        };
        mFilterOriginImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setEffectFilter(FILTER_DISABLE);
                mFilterAdapter.clear();
                changeOriginalImageState(true);
            }
        });
        mFilterAdapter.setOnImageItemClick(listener);
        mFilterRecyclerView.setAdapter(mFilterAdapter);
    }

    public void changeOriginalImageState(boolean isSelected) {
        if (isSelected) {
            mFilterOriginText.setActivated(true);
            mFilterBorder.setVisibility(View.VISIBLE);
        } else {
            mFilterOriginText.setActivated(false);
            mFilterBorder.setVisibility(View.INVISIBLE);
        }
    }

    private void resumeEditPreview() {
        Log.d(TAG, "resumeEditPreview ");
//        //恢复播放的状态
//        if (mPauseView.getDrawable().getLevel() == 1) {
//            mPauseView.getDrawable().setLevel(2);
//        }

        mEditKit.resumeEditPreview();
        mFilterOriginImage.callOnClick();
        startPreviewTimerTask();
    }

    private void initBgmView() {
//        List<BgmSelectAdapter.BgmData> list = DataFactory.getBgmData(getApplicationContext());
//        mBgmAdapter = new BgmSelectAdapter(this, list);
//        mBgmRecyclerView = (RecyclerView) findViewById(R.id.bgm_recycler);
//        LinearLayoutManager manager = new LinearLayoutManager(this);
//        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
//        mBgmRecyclerView.setLayoutManager(manager);
        setEnableBgmEdit(false);
        mOriginAudioVolumeSeekBar.setProgress((int) mEditKit.getOriginAudioVolume() * 100);
        mBgmVolumeSeekBar.setProgress((int) mEditKit.getBgmVolume() * 100);
    }

    private boolean isComposeWindowShow() {
        if (mComposeDialog != null && mComposeDialog.isShowing()) {
            return true;
        }
        return false;
    }

    /**
     * 根据是否有背景音乐选中来设置相应的编辑控件是否可用
     */
    private void setEnableBgmEdit(boolean enable) {
        if (mBgmVolumeSeekBar != null) {
            mBgmVolumeSeekBar.setEnabled(enable);
        }
    }

    private class ComposeDialog extends Dialog {
        private TextView mStateTextView;
        private TextView mProgressText;
        private View mSystemState;
        private TextView mCpuRate;
        private AlertDialog mConfirmDialog;
        private Timer mTimer;

        protected ComposeDialog(Context context, int themeResId) {
            super(context, themeResId);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            View composeView = LayoutInflater.from(ReleaseEditActivity.this).inflate(R.layout.compose_layout, null);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            setContentView(composeView, params);
            setCanceledOnTouchOutside(false);
            mStateTextView = (TextView) composeView.findViewById(R.id.state_text);
            mProgressText = (TextView) composeView.findViewById(R.id.progress_text);
            mSystemState = composeView.findViewById(R.id.system_state);
            mSystemState.setVisibility(View.VISIBLE);
            mCpuRate = (TextView) composeView.findViewById(R.id.cpu_rate);
        }

        public void composeStarted() {
            mStateTextView.setVisibility(View.VISIBLE);
            mStateTextView.setText(R.string.compose_file);
            mTimer = new Timer();
            mTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    final int progress = mEditKit.getProgress();
                    updateProgress(progress);
                }

            }, 500, 500);
        }

        private void updateProgress(final int progress) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    int rate = (int) SystemStateObtainUtil.getInstance().sampleCPU();
                    mProgressText.setText(String.valueOf(progress) + "%");
                    mCpuRate.setText(rate + "%");
                }
            });
        }

        public void composeFinished(String path) {
            String mime_type = FileUtils.getMimeType(new File(path));
            final Intent intent = new Intent(ReleaseEditActivity.this, PublishEditActivity.class);
            intent.putExtra(Constant.COMPOSE_PATH, path);
            intent.putExtra(PREVIEW_LEN, mPreviewLength);
            if (mTimer != null) {
                mTimer.cancel();
                mTimer = null;
            }
            if (mComposeDialog.isShowing()) {
                mComposeDialog.closeDialog();
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    startActivity(intent);
                }
            });
        }

        @Override
        public boolean onKeyDown(int keyCode, KeyEvent event) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (!mComposeFinished) {
                        mConfirmDialog = new AlertDialog.Builder(ReleaseEditActivity.this).setCancelable
                                (true)
                                .setTitle("中止合成?")
                                .setNegativeButton("取消", new OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface arg0, int arg1) {
                                        mConfirmDialog = null;
                                    }
                                })
                                .setPositiveButton("确定", new OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface arg0, int arg1) {
                                        if (!mComposeFinished) {
                                            mEditKit.stopCompose();
                                            //合成开始后，之前的特效滤镜无效了，不能再次被使用
                                            mEditKit.removeAllTimeEffectFilter();
//                                            mEffectsView.clear();
                                            mComposeFinished = false;
                                            closeDialog();
                                            resumeEditPreview();
                                        }
                                        mConfirmDialog = null;
                                    }
                                }).show();
                    } else {
                        closeDialog();
                        resumeEditPreview();
                    }
                    break;
                default:
                    break;
            }
            return false;
        }

        public void closeDialog() {
            mProgressText.setText(0 + "%");
            if (mTimer != null) {
                mTimer.cancel();
                mTimer = null;
            }

            if (mConfirmDialog != null && mConfirmDialog.isShowing()) {
                mConfirmDialog.dismiss();
                mConfirmDialog = null;
            }

            ComposeDialog.this.dismiss();
        }
    }

    /**
     * 保存视频到草稿箱
     */
    private void saveFileToDraftBox() {
        String srcPath = mEditKit.getEditUrl();
        Log.d(TAG, "saveFileToDCIM from srcPath = " + srcPath);
        String name = srcPath.substring(srcPath.lastIndexOf('/'));

//        String desDir = Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_MOUNTED)
//                ? Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath()
//                + "/Camera/" : Environment.getExternalStorageDirectory().getAbsolutePath() + "/KSYShortVideo";
//        String desPath = desDir + name;
//        Log.d(TAG, "saveFileToDCIM to desPath = " + desPath);
//
//        File desFile = new File(desPath);
        // 获取 /data/data/pkg/file
        File desFile = getApplicationContext().getFilesDir();
        String desPath = desFile.getPath()  + "/draft";
        Log.d(TAG, "saveFileToDCIM to desPath = " + desPath);
        // 文件夹不存在的话，创建文件夹
        File draftDre = new File(desPath);
        if (!draftDre.exists()) {
            draftDre.mkdirs();
        }

        File draftFile = new File(desPath + name);
        try {
            File srcFile = new File(srcPath);
            if (srcFile.exists()) {
                InputStream is = new FileInputStream(srcPath);
                FileOutputStream fos = new FileOutputStream(draftFile);
                byte[] buffer = new byte[1024];
                int length;
                while ((length = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, length);
                }
                Toast.makeText(this, "文件保存草稿箱成功", Toast.LENGTH_SHORT).show();

                // 删除操作的视频
                deleteVideos();
                startMeActivity();
                is.close();
                fos.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 发送系统广播通知有图片更新
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(draftFile);
        intent.setData(uri);
        sendBroadcast(intent);
    }

    /**
     * 删除拍摄的断点视频
     */
    private void deleteVideos() {
        // 向视频录制画面传递消息
        EventBus.getDefault().post(new EventMessageBean(Constant.DELETE_VIDEOS));

        // 删除合成的视频
        FileUtils.deleteFile(mEditKit.getEditUrl());
    }

    /**
     * 启动首页 我的画面
     */
    private void startMeActivity() {
        Intent intent = new Intent(this, HomeActivity.class);
        // 清空在HOME之上的Activity栈
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        // 跳转到HOME，要显示ME
        intent.putExtra(Constant.BACK_HOME_KEY, 3);
        startActivity(intent);
    }

//    ProgressDialog dialog = new ProgressDialog(this);
//        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);// 设置进度条的形式为圆形转动的进度条
//        dialog.setCancelable(true);// 设置是否可以通过点击Back键取消
//        dialog.setCanceledOnTouchOutside(false);// 设置在点击Dialog外是否取消Dialog进度条
//        dialog.setIcon(R.drawable.ic_launcher);//
//    // 设置提示的title的图标，默认是没有的，如果没有设置title的话只设置Icon是不会显示图标的
//        dialog.setTitle("提示");
//    // dismiss监听
//        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
//
//        @Override
//        public void onDismiss(DialogInterface dialog) {
//            // TODO Auto-generated method stub
//
//        }
//    });
//    // 监听Key事件被传递给dialog
//        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
//
//        @Override
//        public boolean onKey(DialogInterface dialog, int keyCode,
//        KeyEvent event) {
//            // TODO Auto-generated method stub
//            return false;
//        }
//    });
//    // 监听cancel事件
//        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
//
//        @Override
//        public void onCancel(DialogInterface dialog) {
//            // TODO Auto-generated method stub
//
//        }
//    });
//    //设置可点击的按钮，最多有三个(默认情况下)
//        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定",
//            new DialogInterface.OnClickListener() {
//
//        @Override
//        public void onClick(DialogInterface dialog, int which) {
//            // TODO Auto-generated method stub
//
//        }
//    });
//        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消",
//            new DialogInterface.OnClickListener() {
//
//        @Override
//        public void onClick(DialogInterface dialog, int which) {
//            // TODO Auto-generated method stub
//
//        }
//    });
//        dialog.setButton(DialogInterface.BUTTON_NEUTRAL, "中立",
//            new DialogInterface.OnClickListener() {
//
//        @Override
//        public void onClick(DialogInterface dialog, int which) {
//            // TODO Auto-generated method stub
//
//        }
//    });
//        dialog.setMessage("这是一个圆形进度条");
//        dialog.show();
//        new Thread(new Runnable() {
//
//        @Override
//        public void run() {
//            // TODO Auto-generated method stub
//            try {
//                Thread.sleep(5000);
//                // cancel和dismiss方法本质都是一样的，都是从屏幕中删除Dialog,唯一的区别是
//                // 调用cancel方法会回调DialogInterface.OnCancelListener如果注册的话,dismiss方法不会回掉
//                dialog.cancel();
//                // dialog.dismiss();
//            } catch (InterruptedException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//
//        }
//    }).start();

    /**
     * 画面跳转至发布编缉画面
     * 需要将合成后的视频路径传给下一个画面
     */
    private void startPublishActivity() {
        Intent intent = new Intent(this, PublishEditActivity.class);
        intent.putExtra(Constant.COMPOSE_PATH, mLocalPath);
        startActivity(intent);
    }
}

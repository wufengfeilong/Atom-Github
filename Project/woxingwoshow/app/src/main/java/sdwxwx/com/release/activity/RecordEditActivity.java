package sdwxwx.com.release.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.AppCompatSeekBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.tu.loadingdialog.LoadingDailog;
import com.ksyun.media.shortvideo.kit.KSYEditKit;
import com.ksyun.media.shortvideo.utils.ShortVideoConstants;
import com.ksyun.media.streamer.filter.imgtex.ImgBeautySpecialEffectsFilter;
import com.ksyun.media.streamer.filter.imgtex.ImgBeautyStylizeFilter;
import com.ksyun.media.streamer.filter.imgtex.ImgFilterBase;
import com.ksyun.media.streamer.kit.StreamerConstants;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;

import sdwxwx.com.R;
import sdwxwx.com.cons.Constant;
import sdwxwx.com.home.HomeActivity;
import sdwxwx.com.login.utils.LoginHelper;
import sdwxwx.com.release.adapter.ImageTextAdapter;
import sdwxwx.com.release.utils.DataFactory;
import sdwxwx.com.release.utils.ShortVideoConfig;

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

public class RecordEditActivity extends Activity implements
        ActivityCompat.OnRequestPermissionsResultCallback {
    private static String TAG = "RecordEditActivity";

    private static final int REQUEST_CODE = 10010;
    private static final int FILTER_DISABLE = 0;

    private GLSurfaceView mEditPreviewView;
    private RelativeLayout mBarBottomLayout;
    private RelativeLayout mBottomBtn;
    // 音乐设定按钮
    private ImageView mMusicView;
    // 音乐已设定按钮
    private FrameLayout mMusicCheckedLayout;
    // 背景音乐操作窗口
    private RelativeLayout mMusicMenu;
    // 音量设定按钮
    private ImageView mVolumeView;

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
    private int mFilterTypeIndex = -1;
    private Timer mPreviewRefreshTimer;
    // 视频时长
    private long mPreviewLength;
    private LoadingDailog mComposeDialog;
    //输出视频参数配置
    private ShortVideoConfig mComposeConfig;
    private SeekBarChangedObserver mSeekBarChangedObserver;

    // 视频合成前的存储路径
    public static String mLocalPath;
    // 视频合成后的存储路径
    public static String mComposeLocalPath;
    public final static String SRC_URL = "src_url";
    public final static String BGM_FLAG = "BGM_FLAG";
    // 录制视频是否有音乐设置
    private boolean mMusicFrontFlag = false;
    // 是否有音乐设置
    private boolean mMusicFlag = false;
    // 音乐文件路径
    private String mMusicPath;
    // 视频所使用的音乐编号
    private String mMusicId;

    private KSYEditKit mEditKit; //编辑合成kit类
    private int mEffectFilterIndex = FILTER_DISABLE;  //滤镜filter type
    private int mLastEffectFilterIndex = FILTER_DISABLE;  //滤镜filter type
    private Map<Integer, ImgFilterBase> mEffectFilters;

    // 合成是否结束
    private boolean mComposeFinished = false;

    // 存储到草稿箱的合成标志
    private boolean mComposeForSave = false;

    private Handler mMainHandler;

    private boolean mHWEncoderUnsupported;  //硬编支持标志位
    private boolean mSWEncoderUnsupported;  //软编支持标志位

    private int mScreenHeight;

    public static void startActivity(Context context, String srcUrl,
                                     boolean hasBgm, String musicPath, String musicId) {
        Intent intent = new Intent(context, RecordEditActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(SRC_URL, srcUrl);
        intent.putExtra(BGM_FLAG, hasBgm);
        intent.putExtra(Constant.MUSIC_ONLINE_PATH, musicPath);
        intent.putExtra(Constant.MUSIC_ONLINE_ID, musicId);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_record_edit);

        //must set
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        //默认设置为纵屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mScreenHeight = getResources().getDisplayMetrics().heightPixels;
//        mButtonObserver = new ButtonObserver();
        mSeekBarChangedObserver = new RecordEditActivity.SeekBarChangedObserver();

        mEditPreviewView = findViewById(R.id.edit_preview);

        // 底部操作窗口正在显示时，点击屏幕消失
        mEditPreviewView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // 如果滤镜操作框正在显示，要关闭
                        if (mFilterLayout.getVisibility() == View.VISIBLE) {
                            mFilterLayout.setVisibility(View.GONE);
                            mBottomBtn.setVisibility(View.VISIBLE);
                        }
                        break;
                    default:
                        break;
                }
                return false;
            }

        });

        mBarBottomLayout = findViewById(R.id.edit_bar_bottom);
        mBottomBtn = findViewById(R.id.edit_bottom_btn);

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mBarBottomLayout.getLayoutParams();
        params.height = mScreenHeight / 3;
        mBarBottomLayout.setLayoutParams(params);

        mFilterLayout = findViewById(R.id.edit_filter_choose);
        mAudioEditLayout = findViewById(R.id.audio_choose);
        mOriginAudioVolumeSeekBar = findViewById(R.id.record_mic_audio_volume);
        mOriginAudioVolumeSeekBar.setOnSeekBarChangeListener(mSeekBarChangedObserver);
        mBgmVolumeSeekBar = findViewById(R.id.record_music_audio_volume);
        mBgmVolumeSeekBar.setOnSeekBarChangeListener(mSeekBarChangedObserver);
        // 音乐设定按钮
        mMusicView = findViewById(R.id.music_img);
        // 音乐已设定按钮
        mMusicCheckedLayout = findViewById(R.id.music_img_checked_fl);
        // 背景音乐操作窗口
        mMusicMenu = findViewById(R.id.record_edit_music_menu);
        // 音量设定按钮
        mVolumeView = findViewById(R.id.volume_img);

        mMainHandler = new Handler();
        mEditKit = new KSYEditKit(this);
        mEditKit.setDisplayPreview(mEditPreviewView);
        mEditKit.setOnErrorListener(mOnErrorListener);
        mEditKit.setOnInfoListener(mOnInfoListener);

        Bundle bundle = getIntent().getExtras();
        mLocalPath = bundle.getString(SRC_URL);
        if (!TextUtils.isEmpty(mLocalPath)) {
            mEditKit.setEditPreviewUrl(mLocalPath);
            mPreviewLength = mEditKit.getEditDuration();
        }
        // 是否配置了背景音乐
        mMusicFrontFlag = bundle.getBoolean(BGM_FLAG, false);
        mMusicPath = bundle.getString(Constant.MUSIC_ONLINE_PATH);
        mMusicId = bundle.getString(Constant.MUSIC_ONLINE_ID);
        mEditKit.setTimerEffectOverlying(false);

        initFilterUI();
        initBgmView();
        startEditPreview();

    }

    @Override
    public void onResume() {
        super.onResume();
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
        } else if (mMusicMenu.getVisibility() == View.VISIBLE) {
            // 如果背景音乐操作框显示，则关闭
            mMusicMenu.setVisibility(View.GONE);
            mBottomBtn.setVisibility(View.VISIBLE);
        } else {
            // 返回前页面
            super.onBackPressed();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
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
            mComposeDialog.dismiss();
            mComposeDialog = null;
        }
        stopPreviewTimerTask();
        mEditKit.stopEditPreview();
        mEditKit.release();
    }

    /**
     * 选中背景音乐后返回结果处理
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE:
                // If the file selection was successful
                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        mMusicPath = data.getExtras().getString(Constant.MUSIC_ONLINE_PATH);
                        if (TextUtils.isEmpty(mMusicPath)) {
                            mMusicFlag = false;
                            setEnableBgmEdit(false);
                            Toast.makeText(RecordEditActivity.this, "获取音乐失败！", Toast.LENGTH_SHORT).show();
                        } else {
                            mMusicFlag = true;
                            mMusicId = data.getStringExtra(Constant.MUSIC_ONLINE_ID);
                            mEditKit.startBgm(mMusicPath, true);
                            mEditKit.setBgmVolume(1);
                            mEditKit.setBGMRanges(0, (long) mPreviewLength, false);
                            setEnableBgmEdit(true);
                            mMusicView.setVisibility(View.GONE);
                            mMusicCheckedLayout.setVisibility(View.VISIBLE);
                        }
                    }
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
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
        // 合成
        setOutputConfirm();
        startCompose();
    }

    /**
     * 显示正在合成的Dialog
     */
    private void showComposeDialog() {
        if (mComposeDialog != null && mComposeDialog.isShowing()) {
            return;
        }

        if (mComposeDialog == null) {
            LoadingDailog.Builder builder = new LoadingDailog.Builder(this)
                    .setMessage(getString(R.string.compose_file))
                    .setCancelable(true)
                    .setCancelOutside(false);
            mComposeDialog = builder.create();
        }
        mComposeDialog.show();
    }

    /**
     * 关闭正在合成的Dialog
     */
    private void closeComposeDialog() {
        if (mComposeDialog != null && mComposeDialog.isShowing()) {
            mComposeDialog.dismiss();
        }
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

    /**
     * 开始合成
     */
    private void startCompose() {
        //设置合成路径
        String fileFolder = getApplicationContext().getFilesDir().getPath() + Constant.COMPOSE_BOX_NAME;
        File file = new File(fileFolder);
        if (!file.exists()) {
            file.mkdir();
        }

        StringBuilder composeUrl = new StringBuilder(fileFolder).append("/").append(System
                .currentTimeMillis());
        composeUrl.append(Constant.VIDEO_TYPE_NAME);
        Log.d(TAG, "compose Url:" + composeUrl);
        mComposeLocalPath = composeUrl.toString();
        mComposeFinished = false;
        // 开始合成
        mEditKit.startCompose(composeUrl.toString());
        // 显示正在合成的Dialog
        showComposeDialog();
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
                    Toast.makeText(RecordEditActivity.this,
                            getString(R.string.compose_file_failed), Toast.LENGTH_LONG).show();
                    if (mComposeDialog != null && mComposeDialog.isShowing()) {
                        mComposeDialog.dismiss();
                        resumeEditPreview();
                    }
                    break;
                case ShortVideoConstants.SHORTVIDEO_ERROR_SDK_AUTHFAILED:
                    Log.d(TAG, "sdk auth failed:" + type);
                    Toast.makeText(RecordEditActivity.this,
                            getString(R.string.release_check_auth_failed), Toast.LENGTH_LONG).show();
                    if (mComposeDialog != null) {
                        mComposeDialog.dismiss();
                        resumeEditPreview();
                    }
                    break;
                case ShortVideoConstants.SHORTVIDEO_EDIT_PREVIEW_PLAYER_ERROR:
                    Log.d(TAG, "KSYEditKit preview player error:" + type + "_" + msg);
                    break;
                case ShortVideoConstants.SHORTVIDEO_ERROR_EDIT_FEATURE_NOT_SUPPORTED:
                    Toast.makeText(RecordEditActivity.this,
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
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            startPreviewTimerTask();
                        }
                    });
                    break;
                case ShortVideoConstants.SHORTVIDEO_EDIT_PREVIEW_ONE_LOOP_END:
                    break;
                case ShortVideoConstants.SHORTVIDEO_COMPOSE_START: {
                    Log.d(TAG, "compose started");
                    //合成开始null，再次预览时重新创建
                    clearImgFilter();
                    mEditKit.pauseEditPreview();
                    if (mComposeDialog == null && !mComposeDialog.isShowing()) {
                        showComposeDialog();
                    }
                    break;
                }
                case ShortVideoConstants.SHORTVIDEO_COMPOSE_FINISHED: {
                    Log.d(TAG, "compose finished");
                    if (mComposeDialog != null && mComposeDialog.isShowing()) {
                        finishCompose();
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

    /**
     * 视频合成结束，画面跳转
     */
    private void finishCompose() {
        // 关闭正在合成的Dialog
        closeComposeDialog();

        if (mComposeForSave) {
            // 如果是存储到草稿箱前执行的合成，接下来就存储到草稿箱
            saveFileToDraftBox();
            return;
        }
        String pathBase = getFilesDir().getPath();

        // 删除录制的所有视频
        File recordFile = new File(pathBase + Constant.RECORD_BOX_NAME);
        deleteAllFiles(recordFile);

        // 删除已下载的所有音乐
        File musicFile = new File(pathBase + Constant.MUSIC_BOX_NAME);
        deleteAllFiles(musicFile);

        Intent intent = new Intent(RecordEditActivity.this, PublishEditActivity.class);
        intent.putExtra(Constant.COMPOSE_PATH, mComposeLocalPath);
        intent.putExtra(Constant.MUSIC_ONLINE_ID, mMusicId);
        startActivity(intent);
    }

    private void startPreviewTimerTask() {
        mPreviewRefreshTimer = new Timer();
    }

    private void stopPreviewTimerTask() {
        if (mPreviewRefreshTimer != null) {
            mPreviewRefreshTimer.cancel();
            mPreviewRefreshTimer = null;
        }
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
                // 如果背景音乐操作框显示，则关闭
                if (mMusicMenu.getVisibility() == View.VISIBLE) {
                    mMusicMenu.setVisibility(View.GONE);
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
                // 启动在线音乐选择画面
                startOnlineMusic();
                break;
            case R.id.music_img_checked_fl:
                showMusicMenu();
                break;
            case R.id.volume_img:
                // 如果滤镜选择框显示，则关闭
                if (mFilterLayout.getVisibility() == View.VISIBLE) {
                    mFilterLayout.setVisibility(View.GONE);
                }
                // 如果背景音乐操作框显示，则关闭
                if (mMusicMenu.getVisibility() == View.VISIBLE) {
                    mMusicMenu.setVisibility(View.GONE);
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
                // 先合成再存储到草稿箱
                mComposeForSave = true;
                onNextClick();
                break;
            case R.id.record_edit_music_exit_menu:
                mMusicMenu.setVisibility(View.GONE);
                mBottomBtn.setVisibility(View.VISIBLE);
                mMusicCheckedLayout.setVisibility(View.GONE);
                mMusicView.setVisibility(View.VISIBLE);
                mMusicFlag = false;

                setEnableBgmEdit(false);
                mEditKit.stopBgm();

                String pathBase = getApplicationContext().getFilesDir().getPath();
                // 删除已下载的所有音乐
                File musicFile = new File(pathBase + Constant.MUSIC_BOX_NAME);
                deleteAllFiles(musicFile);
                break;
            case R.id.record_edit_music_change_menu:
                mMusicMenu.setVisibility(View.GONE);
                // 启动在线音乐选择画面
                startOnlineMusic();
                break;
            case R.id.release_edit_cancel_menu:
                mMusicMenu.setVisibility(View.GONE);
                mBottomBtn.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }

    }

    /**
     * 画面跳转
     * 在线音乐选择画面
     */
    private void startOnlineMusic() {
        // 在线音乐选择画面
        Intent intent = new Intent(this, MusicOnlineActivity.class);
        startActivityForResult(intent, REQUEST_CODE);
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
                ImgFilterBase lastFilter = mEffectFilters.get(mLastEffectFilterIndex);
                if (mEditKit.getImgTexFilterMgt().getFilter().contains(lastFilter)) {
                    mEditKit.getImgTexFilterMgt().replaceFilter(lastFilter, filter, false);
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
        mFilterOriginImage = findViewById(R.id.iv_filter_origin);
        mFilterBorder = findViewById(R.id.iv_filter_border);
        mFilterOriginText = findViewById(R.id.tv_filter_origin);
        changeOriginalImageState(true);
        mFilterRecyclerView = findViewById(R.id.filter_recyclerView);
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

        mEditKit.resumeEditPreview();
        mFilterOriginImage.callOnClick();
        startPreviewTimerTask();
    }

    /**
     * 音乐组件初始化
     */
    private void initBgmView() {
        // 录制视频时已经设置了背景音乐
        if (mMusicFrontFlag) {
            mEditKit.startBgm(mMusicPath, true);
            setEnableBgmEdit(true);
            mEditKit.setBGMRanges(0, mPreviewLength, false);
            mMusicView.setVisibility(View.GONE);
            mMusicCheckedLayout.setVisibility(View.GONE);
            mVolumeView.setVisibility(View.GONE);
        } else {
            setEnableBgmEdit(false);
            mMusicView.setVisibility(View.VISIBLE);
            mVolumeView.setVisibility(View.VISIBLE);
            mMusicCheckedLayout.setVisibility(View.GONE);
        }
        mOriginAudioVolumeSeekBar.setProgress((int) mEditKit.getOriginAudioVolume() * 100);
        mBgmVolumeSeekBar.setProgress((int) mEditKit.getBgmVolume() * 100);
    }

    /**
     * 根据是否有背景音乐选中来设置相应的编辑控件是否可用
     */
    private void setEnableBgmEdit(boolean enable) {
        if (mBgmVolumeSeekBar != null) {
            mBgmVolumeSeekBar.setEnabled(enable);
        }
    }

    /**
     * 保存视频到草稿箱
     */
    private void saveFileToDraftBox() {
        String srcPath = mEditKit.getEditUrl();
        Log.d(TAG, "saveFileToDraftBox from srcPath = " + srcPath);

        // 获取 /data/data/pkg/file
        File desFile = getApplicationContext().getFilesDir();
        String desPath = desFile.getPath() + Constant.DRAFT_BOX_NAME;
        Log.d(TAG, "saveFileToDraftBox to desPath = " + desPath);
        // 文件夹不存在的话，创建文件夹
        File draftDre = new File(desPath);
        if (!draftDre.exists()) {
            draftDre.mkdirs();
        }

        String name = srcPath.substring(srcPath.lastIndexOf('/'));
        String nameWithMusicId = name.replace(
                Constant.VIDEO_TYPE_NAME,
                Constant.DRAFT_BOX_MUSIC_ID + mMusicId + Constant.VIDEO_TYPE_NAME);
        String nameWithUserId = nameWithMusicId.replace(
                "/", "/" + LoginHelper.getInstance().getUserId() + "id");
        File draftFile = new File(desPath + nameWithUserId);
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
                is.close();
                fos.close();

                // 删除操作的视频
                deleteVideos();
                Toast.makeText(this, "文件保存草稿箱成功", Toast.LENGTH_SHORT).show();
                mComposeForSave = false;
                startMeActivity();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除拍摄的断点视频
     */
    private void deleteVideos() {
        String pathBase = getApplicationContext().getFilesDir().getPath();

        // 删除录制的所有视频
        File recordFile = new File(pathBase + Constant.RECORD_BOX_NAME);
        deleteAllFiles(recordFile);

        // 删除合成的所有视频
        File composeFile = new File(pathBase + Constant.COMPOSE_BOX_NAME);
        deleteAllFiles(composeFile);


        // 删除已下载的所有音乐
        File musicFile = new File(pathBase + Constant.MUSIC_BOX_NAME);
        deleteAllFiles(musicFile);
    }

    /**
     * 递归删除所有文件
     *
     * @param root 操作的文件夹
     */
    private void deleteAllFiles(File root) {
        if (root.exists()) {
            File files[] = root.listFiles();
            if (files != null)
                for (File f : files) {
                    if (f.isDirectory()) { // 判断是否为文件夹
                        deleteAllFiles(f);
                        try {
                            f.delete();
                        } catch (Exception e) {
                        }
                    } else {
                        if (f.exists()) { // 判断是否存在
                            deleteAllFiles(f);
                            try {
                                f.delete();
                            } catch (Exception e) {
                            }
                        }
                    }
                }
        }
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

    /**
     * 显示/关闭音乐操作窗口
     */
    private void showMusicMenu() {
        // 如果音量调节框显示，则关闭
        if (mAudioEditLayout.getVisibility() == View.VISIBLE) {
            mAudioEditLayout.setVisibility(View.GONE);
        }
        // 如果滤镜选择框显示，则关闭
        if (mFilterLayout.getVisibility() == View.VISIBLE) {
            mFilterLayout.setVisibility(View.GONE);
        }
        if (mMusicMenu.getVisibility() == View.VISIBLE) {
            mMusicMenu.setVisibility(View.GONE);
            mBottomBtn.setVisibility(View.VISIBLE);
        } else {
            mMusicMenu.setVisibility(View.VISIBLE);
            mBottomBtn.setVisibility(View.GONE);
        }
    }
}

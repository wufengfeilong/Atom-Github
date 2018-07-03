package sdwxwx.com.release.activity;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.tu.loadingdialog.LoadingDailog;
import com.ksyun.media.shortvideo.kit.KSYRecordKit;
import com.ksyun.media.shortvideo.utils.AuthInfoManager;
import com.ksyun.media.streamer.capture.CameraCapture;
import com.ksyun.media.streamer.capture.camera.CameraTouchHelper;
import com.ksyun.media.streamer.filter.imgtex.ImgBeautyProFilter;
import com.ksyun.media.streamer.filter.imgtex.ImgBeautySoftFilter;
import com.ksyun.media.streamer.filter.imgtex.ImgBeautySpecialEffectsFilter;
import com.ksyun.media.streamer.filter.imgtex.ImgBeautyStylizeFilter;
import com.ksyun.media.streamer.filter.imgtex.ImgFilterBase;
import com.ksyun.media.streamer.kit.KSYStreamer;
import com.ksyun.media.streamer.kit.StreamerConstants;
import com.ksyun.media.streamer.logstats.StatsLogReport;

import org.apache.http.util.EncodingUtils;

import java.io.File;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import sdwxwx.com.R;
import sdwxwx.com.base.BaseActivity;
import sdwxwx.com.cons.Constant;
import sdwxwx.com.databinding.AcitvityRecordBinding;
import sdwxwx.com.release.adapter.ImageTextAdapter;
import sdwxwx.com.release.contract.RecordContract;
import sdwxwx.com.release.presenter.RecordPresenter;
import sdwxwx.com.release.utils.DataFactory;
import sdwxwx.com.release.utils.ShortVideoConfig;
import sdwxwx.com.release.view.RecordProgressController;
import sdwxwx.com.release.view.RecordProgressView;

/**
 * 短视频录制示例窗口
 * 水印
 * 变焦
 * 美颜
 * 滤镜
 * 断点拍摄
 * 魔方动态贴纸
 * 麦克风音频音量调节
 * 背景音频添加及音量调节
 * 麦克风音频音效处理：变声&混响
 * 背景音频变调
 */
public class RecordActivity extends BaseActivity<AcitvityRecordBinding, RecordPresenter>
        implements RecordContract.View, ActivityCompat.OnRequestPermissionsResultCallback {

    private static String TAG = "RecordActivity";
    // 摄像头、麦克风请求授权的请求码
    private final static int PERMISSION_REQUEST_CAMERA_AUDIO_REC = 1;
    // 摄像头、麦克风请求授权被拒绝时，手动设置权限的Dialog
    private Dialog permissionDialog = null;
    //    // 保存/舍弃的Dialog
//    private Dialog saveDialog = null;
    // 删除的Dialog
    private Dialog deleteDialog = null;
    // 更多操作窗口
    private LinearLayout mMoreMenuLL = null;
    // 美颜操作窗口
    private RelativeLayout mBeautyLayout = null;
    // 在线音乐操作窗口
    private RelativeLayout mOnlineMusicLayout = null;

    private TextView mBeauty;
    private TextView mFilter;

    // 是否超过最短时间
    private boolean isPassedMinTime = false;

    // 倒计时时间
    private int countDownTime = 0;

    // 是否正在录制
    private boolean mIsFileRecording = false;

    // 从音乐中选择
    private static final int REQUEST_MUSIC_CODE = 10000;
    // 是否有音乐设置
    private boolean mMusicFlag = false;
    // 音乐文件路径
    private String mMusicPath = Constant.EMPTY_STRING;
    // 视频所使用的音乐编号
    private String mMusicId = Constant.ZERO_STRING;
    // 从相册中选择
    private static final int REQUEST_ALBUM_CODE = 10010;

    private static final int INDEX_BEAUTY_TITLE_BASE = 0;  //美颜标题在mRecordTitleArray中的启始位置索引
    private static final int INDEX_BGM_TITLE_BASE = 10;  //音乐标题在mRecordTitleArray中的启始位置索引

    public static final String ZIP_INFO = ".zip";

    private static final int FILTER_DISABLE = 0;

    private ImageView mFilterOriginImage;
    private ImageView mFilterBorder;
    private TextView mFilterOriginText;
    private RecyclerView mFilterRecyclerView;
    private ShortVideoConfig mRecordConfig;

    private AnimatorSet mAnimatorSet;

    //美颜
    private static final int BEAUTY_DISABLE = 100;
    private static final int BEAUTY_NATURE = 101;
    private static final int BEAUTY_PRO = 102;
    private static final int BEAUTY_FLOWER_LIKE = 103;
    private static final int BEAUTY_DELICATE = 104;
    private View mBeautyChooseView;
    private ImageView mBeautyOriginalView;
    private ImageView mBeautyBorder;
    private TextView mBeautyOriginalText;
    private RecyclerView mBeautyRecyclerView;

    private View mFilterChooseView;

    //断点拍摄UI初始化
    private RecordProgressView mRecordProgressView;
    //断点拍摄进度控制
    private RecordProgressController mRecordProgressCtl;
    private Chronometer mChronometer;
    private ButtonObserver mObserverButton;

    private boolean mHasBgm;

    //录制kit
    private KSYRecordKit mKSYRecordKit;
    private int mImgBeautyTypeIndex = BEAUTY_DISABLE;  //美颜type
    private int mEffectFilterIndex = FILTER_DISABLE;  //滤镜filter type
    private int mLastImgBeautyTypeIndex = BEAUTY_DISABLE;  //美颜type
    private int mLastEffectFilterIndex = FILTER_DISABLE;  //滤镜filter type
    private Map<Integer, ImgFilterBase> mBeautyFilters;
    private Map<Integer, ImgFilterBase> mEffectFilters;

    private Handler mMainHandler;

    private String mRecordUrl;
    private boolean mHWEncoderUnsupported;  //硬编支持标志位
    private boolean mSWEncoderUnsupported;  //软编支持标志位

    private int mPreBeautyTitleIndex = 0;  //记录上次选择的美颜标题索引
    private int mPreBgmTitleIndex = 0;  //记录上次选择的背景音乐标题索引

    //美颜、背景音乐标题和布局自定义内容集合
    private SparseArray<BottomTitleViewInfo> mRecordTitleArray = new SparseArray<>();

    private int mFilterTypeIndex = -1;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, RecordActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    protected void initViews() {
        mObserverButton = new ButtonObserver();
//        ViewUtils.setImgTransparent(this);
        // 初始化美颜界面
        initBeautyUI();
        // 初始化滤镜界面
        initFilterUI();
        // 初始化在线音乐菜单界面
        initOnlineMusic();
        // 断点拍摄UI初始化
        mRecordProgressView = (RecordProgressView) findViewById(R.id.record_progress);
        mRecordProgressCtl = new RecordProgressController(mRecordProgressView, mChronometer);
        //拍摄时长变更回调
        mRecordProgressCtl.setRecordingLengthChangedListener(mRecordLengthChangedListener);
        mRecordProgressCtl.start();

        // 美颜操作窗口
        mBeautyLayout = findViewById(R.id.item_beauty_select);
        mBeautyLayout.setVisibility(View.GONE);
        // 初始化更多操作显示框
        initMoreMenu();
        mDataBinding.setPresenter(mPresenter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //录制参数配置
        mRecordConfig = new ShortVideoConfig();

        //init
        mMainHandler = new Handler();
        mKSYRecordKit = new KSYRecordKit(this);

        float frameRate = mRecordConfig.fps;
        if (frameRate > 0) {
            mKSYRecordKit.setPreviewFps(frameRate);
            mKSYRecordKit.setTargetFps(frameRate);
        }

        int videoBitrate = mRecordConfig.videoBitrate;
        if (videoBitrate > 0) {
            mKSYRecordKit.setVideoKBitrate(videoBitrate);
        }

        int audioBitrate = mRecordConfig.audioBitrate;
        if (audioBitrate > 0) {
            mKSYRecordKit.setAudioKBitrate(audioBitrate);
        }

        int videoResolution = mRecordConfig.resolution;
        mKSYRecordKit.setPreviewResolution(videoResolution);
        mKSYRecordKit.setTargetResolution(videoResolution);

        int encode_type = mRecordConfig.encodeType;
        mKSYRecordKit.setVideoCodecId(encode_type);

        int encode_method = mRecordConfig.encodeMethod;
        mKSYRecordKit.setEncodeMethod(encode_method);

        int encodeProfile = mRecordConfig.encodeProfile;
        mKSYRecordKit.setVideoEncodeProfile(encodeProfile);

        if (mRecordConfig.isLandscape) {
            mKSYRecordKit.setRotateDegrees(90);
        } else {
            mKSYRecordKit.setRotateDegrees(0);
        }
        mKSYRecordKit.setDisplayPreview(mDataBinding.cameraPreview);
        mKSYRecordKit.setEnableRepeatLastFrame(false);
        mKSYRecordKit.setCameraFacing(CameraCapture.FACING_FRONT);
        mKSYRecordKit.setFrontCameraMirror(true);
        mKSYRecordKit.setOnInfoListener(mOnInfoListener);
        mKSYRecordKit.setOnErrorListener(mOnErrorListener);
        mKSYRecordKit.setOnLogEventListener(mOnLogEventListener);

        // touch focus and zoom support
        CameraTouchHelper cameraTouchHelper = new CameraTouchHelper();
        cameraTouchHelper.setCameraCapture(mKSYRecordKit.getCameraCapture());
        mDataBinding.cameraPreview.setOnTouchListener(cameraTouchHelper);
        // set CameraHintView to show focus rect and zoom ratio
        cameraTouchHelper.setCameraHintView(mDataBinding.cameraHint);

        mDataBinding.cameraPreview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // 如果更多操作框正在显示，要关闭
                        if (mMoreMenuLL.getVisibility() == View.VISIBLE) {
                            closeMoreMenu();
                        }
                        // 如果美颜操作框正在显示，要关闭
                        if (mBeautyLayout.getVisibility() == View.VISIBLE) {
                            mBeautyLayout.setVisibility(View.GONE);
                            mDataBinding.recordBottomBar.recordBottomRl.setVisibility(View.VISIBLE);
                        }
                        break;
                    default:
                        break;
                }
                return false;
            }
        });

        // 金山云SDK鉴权操作
        checkAuth();
    }

    @Override
    protected RecordPresenter createPresenter() {
        return new RecordPresenter();
    }

    @Override
    protected int getLayoutId() {
        //must set
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        // 隐藏虚拟键盘
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        return R.layout.acitvity_record;
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if (hasFocus) {
            // 隐藏虚拟键盘
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
        super.onWindowFocusChanged(hasFocus);
    }

    @Override
    public void onResume() {
        super.onResume();

        mKSYRecordKit.setDisplayPreview(mDataBinding.cameraPreview);
        mKSYRecordKit.onResume();
        mDataBinding.cameraHint.hideAll();

        // 从其他画面回到拍摄画面时，初始化相册Indicate
        mDataBinding.recordBottomBar.releaseSelectWayAlbum.setVisibility(View.INVISIBLE);
        mDataBinding.recordBottomBar.releaseSelectWayRecord.setVisibility(View.VISIBLE);

        // 请求授权，成功则开启预览
        startCameraPreviewWithPermCheck();
    }

    @Override
    public void onPause() {
        super.onPause();
        mKSYRecordKit.onPause();
        if (!mKSYRecordKit.isRecording() && !mKSYRecordKit.isFileRecording()) {
            mKSYRecordKit.stopCameraPreview();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Activity退出时，删除所有拍摄的视频
        // 删除断点录制的所有视频
        mKSYRecordKit.deleteAllFiles();

        if (mMainHandler != null) {
            mMainHandler.removeCallbacksAndMessages(null);
            mMainHandler = null;
        }

        if (mAnimatorSet != null) {
            mAnimatorSet.cancel();
        }

        mRecordProgressCtl.stop();
        mRecordProgressCtl.setRecordingLengthChangedListener(null);
        mRecordProgressCtl.release();
        mKSYRecordKit.setOnLogEventListener(null);
        mKSYRecordKit.release();
    }

    /**
     * 开始倒计时动画
     * @param count 倒计时秒数
     */
    private void startCountDownAnimation(final int count) {
        Log.d(TAG, "倒计时 count = " + count);
        // 更多操作窗口的倒计时要恢复
        mDataBinding.recordMoreMenuBar.releaseCameraCountDownNo.setSelected(true);
        mDataBinding.recordMoreMenuBar.releaseCameraCountDown3.setSelected(false);
        mDataBinding.recordMoreMenuBar.releaseCameraCountDown10.setSelected(false);

        //沿x轴缩小
        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(mDataBinding.countDownTv, "scaleX", 1f, 0.5f, 0.2f);
        scaleXAnimator.setRepeatCount(count - 1);
        //沿y轴缩小
        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(mDataBinding.countDownTv, "scaleY", 1f, 0.5f, 0.2f);
        scaleYAnimator.setRepeatCount(count - 1);
        //透明度动画
        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(mDataBinding.countDownTv, "alpha", 1f, 0.5f);
        alphaAnimator.setRepeatCount(count - 1);
        if (mAnimatorSet != null) {
            mAnimatorSet.cancel();
        }
        mAnimatorSet = new AnimatorSet();
        mAnimatorSet.play(scaleXAnimator).with(scaleYAnimator).with(alphaAnimator);
        scaleXAnimator.addListener(new Animator.AnimatorListener() {
            int index = 0;

            @Override
            public void onAnimationStart(Animator animation) {
                // 屏蔽暂停按钮
                mDataBinding.recordBottomBar.stopCameraBtn.setEnabled(false);

                if (mDataBinding.countDownTv.getVisibility() != View.VISIBLE) {
                    mDataBinding.countDownTv.setVisibility(View.VISIBLE);
                }
                mDataBinding.countDownTv.setText(String.valueOf(count - index));
                index++;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mDataBinding.countDownTv.setVisibility(View.GONE);
                if (!RecordActivity.this.isFinishing()) {
                    // 重启暂停按钮
                    mDataBinding.recordBottomBar.stopCameraBtn.setEnabled(true);
                    startRecord();
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                if (index >= 0 && index < count) {
                    mDataBinding.countDownTv.setText(String.valueOf(count - index));
                    index++;
                }
            }
        });
        mAnimatorSet.setDuration(1000).start();
    }

    /**
     * 开始拍摄视频存储到本地
     */
    private void startRecord() {
        if (mIsFileRecording) {
            // 上一次录制未停止完，不能开启下一次录制
            return;
        }

        // 有快镜头或者慢镜头设定时，设置进度条速度
        mRecordProgressCtl.setRecordingSpeed(mKSYRecordKit.getRecordSpeed());

        // 设置存储路径
        String fileFolder = getRecordFileFolder();
        mRecordUrl = fileFolder + "/" + System.currentTimeMillis() + Constant.VIDEO_TYPE_NAME;
        Log.d(TAG, "record url:" + mRecordUrl);
        // 设置录制文件的本地存储路径，并开始录制
        if (mKSYRecordKit.startRecord(mRecordUrl)) {
            if (mMusicFlag) {
                mKSYRecordKit.startBgm(mMusicPath, true);
                mKSYRecordKit.setVoiceVolume(1);
            }
            mIsFileRecording = true;
        }
    }

    /**
     * 音乐按钮点击事件
     */
    @Override
    public void onMusicClick() {
        Intent intent = new Intent(this, MusicOnlineActivity.class);
        startActivityForResult(intent, REQUEST_MUSIC_CODE);
    }

    /**
     * 美颜按钮点击事件
     */
    @Override
    public void onFairClick() {
        Log.d(TAG, "onFairClick() 美颜");
        mBeautyLayout.setVisibility(View.VISIBLE);
        // 隐藏底部按钮
        mDataBinding.recordBottomBar.recordBottomRl.setVisibility(View.GONE);
    }

    /**
     * 暂停按钮点击事件
     */
    @Override
    public void onStopRecordClick() {
        setRestartViews();
        stopRecord(false);
    }

    /**
     * 设置再次录制的显示
     */
    private void setRestartViews() {
        // 顶部按钮显示/非显示控制
        mDataBinding.actionbar.closeCamera.setVisibility(View.GONE);
        mDataBinding.actionbar.moreCamera.setVisibility(View.GONE);
        mDataBinding.actionbar.reverseCamera.setVisibility(View.GONE);
        if (mMusicFlag) {
            mDataBinding.actionbar.recordMusicName.setVisibility(View.GONE);
        } else {
            mDataBinding.actionbar.musicCamera.setVisibility(View.GONE);
        }
        mDataBinding.actionbar.progressBar.setVisibility(View.VISIBLE);

        // 底部按钮显示/非显示控制
        mBeautyLayout.setVisibility(View.GONE);
        mDataBinding.recordBottomBar.restartCameraBtn.setVisibility(View.VISIBLE);
        mDataBinding.recordBottomBar.startCameraBtn.setVisibility(View.GONE);
        mDataBinding.recordBottomBar.stopCameraBtn.setVisibility(View.GONE);
        mDataBinding.recordBottomBar.deleteLastVideoBtn.setVisibility(View.VISIBLE);
        if (isPassedMinTime) {
            mDataBinding.recordBottomBar.saveVideoBtn.setVisibility(View.VISIBLE);
        } else {
            mDataBinding.recordBottomBar.saveVideoBtn.setVisibility(View.GONE);
        }
        mDataBinding.recordBottomBar.selectTabLl.setVisibility(View.INVISIBLE);
    }

    /**
     * 保存按钮点击事件
     */
    @Override
    public void onSaveBtnClick() {
        if (mIsFileRecording) {
            setRestartViews();
        }
        stopRecord(true);
    }

    /**
     * 启动从相册中选择
     */
    @Override
    public void onStartAlbum() {
        mDataBinding.recordBottomBar.releaseSelectWayAlbum.setVisibility(View.VISIBLE);
        mDataBinding.recordBottomBar.releaseSelectWayRecord.setVisibility(View.INVISIBLE);
        Intent intent = new Intent(this, AlbumImportActivity.class);
//        startActivityForResult(intent, REQUEST_ALBUM_CODE);
        // 因为画面不要返回此画面，所以直接启动
        startActivity(intent);
    }


    /**
     * 从其他画面返回结果处理
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_MUSIC_CODE:
                if (data != null) {
                    mMusicPath = data.getStringExtra(Constant.MUSIC_ONLINE_PATH);

                    if (TextUtils.isEmpty(mMusicPath)) {
                        showToast("获取音乐失败！");
                        mMusicFlag = false;
                    } else {
                        mMusicFlag = true;
                        mMusicId = data.getStringExtra(Constant.MUSIC_ONLINE_ID);
                        String musicName = mMusicPath.substring(mMusicPath.lastIndexOf("/") + 1, mMusicPath.lastIndexOf("."));
                        mDataBinding.actionbar.recordMusicName.setText(musicName);
                        mDataBinding.actionbar.recordMusicName.setVisibility(View.VISIBLE);
                        mDataBinding.actionbar.musicCamera.setVisibility(View.GONE);
                    }
                }
                Log.d(TAG, "音乐选择画面返回" + REQUEST_MUSIC_CODE);
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 录制再启动按钮点击事件
     */
    @Override
    public void onRecordRestartClick() {
        // 顶部按钮显示/非显示控制
        mDataBinding.actionbar.closeCamera.setVisibility(View.GONE);
        mDataBinding.actionbar.moreCamera.setVisibility(View.GONE);
        mDataBinding.actionbar.reverseCamera.setVisibility(View.GONE);
        if (mMusicFlag) {
            mDataBinding.actionbar.recordMusicName.setVisibility(View.GONE);
        } else {
            mDataBinding.actionbar.musicCamera.setVisibility(View.GONE);
        }
        mDataBinding.actionbar.progressBar.setVisibility(View.VISIBLE);

        // 底部按钮显示/非显示控制
        mBeautyLayout.setVisibility(View.GONE);
        mDataBinding.recordBottomBar.restartCameraBtn.setVisibility(View.GONE);
        mDataBinding.recordBottomBar.stopCameraBtn.setVisibility(View.VISIBLE);
        mDataBinding.recordBottomBar.fairCamera.setVisibility(View.GONE);
        mDataBinding.recordBottomBar.startCameraBtn.setVisibility(View.GONE);
        mDataBinding.recordBottomBar.deleteLastVideoBtn.setVisibility(View.GONE);
        mDataBinding.recordBottomBar.saveVideoBtn.setVisibility(View.GONE);
        mDataBinding.recordBottomBar.selectTabLl.setVisibility(View.INVISIBLE);
        startRecord();
    }

    /**
     * 初始化更多操作显示框
     */
    private void initMoreMenu() {
        mMoreMenuLL = findViewById(R.id.record_more_menu_bar);
        mMoreMenuLL.setVisibility(View.GONE);
        // 镜头速度：正常
        mDataBinding.recordMoreMenuBar.releaseCameraSpeedNormal.setSelected(true);
        mDataBinding.recordMoreMenuBar.releaseCameraSpeedSlow.setSelected(false);
        mDataBinding.recordMoreMenuBar.releaseCameraSpeedFast.setSelected(false);

        // 倒计时：无
        mDataBinding.recordMoreMenuBar.releaseCameraCountDownNo.setSelected(true);
        mDataBinding.recordMoreMenuBar.releaseCameraCountDown3.setSelected(false);
        mDataBinding.recordMoreMenuBar.releaseCameraCountDown10.setSelected(false);
    }

    /**
     * 更多操作显示按钮点击事件
     *
     * @param itemId 区分点击位置
     */
    @Override
    public void onMoreItemClick(int itemId) {
        Log.d(TAG, "更多操作显示按钮点击事件 itemId = " + itemId);
        switch (itemId) {
            // 镜头速度：正常
            case 1:
                mDataBinding.recordMoreMenuBar.releaseCameraSpeedNormal.setSelected(true);
                mDataBinding.recordMoreMenuBar.releaseCameraSpeedSlow.setSelected(false);
                mDataBinding.recordMoreMenuBar.releaseCameraSpeedFast.setSelected(false);
                onSpeedClick(1.0f);
                break;
            // 镜头速度：慢镜头
            case 2:
                mDataBinding.recordMoreMenuBar.releaseCameraSpeedNormal.setSelected(false);
                mDataBinding.recordMoreMenuBar.releaseCameraSpeedSlow.setSelected(true);
                mDataBinding.recordMoreMenuBar.releaseCameraSpeedFast.setSelected(false);
                onSpeedClick(0.5f);
                break;
            // 镜头速度：快镜头
            case 3:
                mDataBinding.recordMoreMenuBar.releaseCameraSpeedNormal.setSelected(false);
                mDataBinding.recordMoreMenuBar.releaseCameraSpeedSlow.setSelected(false);
                mDataBinding.recordMoreMenuBar.releaseCameraSpeedFast.setSelected(true);
                onSpeedClick(2.0f);
                break;
            // 倒计时：无
            case 4:
                mDataBinding.recordMoreMenuBar.releaseCameraCountDownNo.setSelected(true);
                mDataBinding.recordMoreMenuBar.releaseCameraCountDown3.setSelected(false);
                mDataBinding.recordMoreMenuBar.releaseCameraCountDown10.setSelected(false);
                countDownTime = 0;
                break;
            // 倒计时：3秒
            case 5:
                mDataBinding.recordMoreMenuBar.releaseCameraCountDownNo.setSelected(false);
                mDataBinding.recordMoreMenuBar.releaseCameraCountDown3.setSelected(true);
                mDataBinding.recordMoreMenuBar.releaseCameraCountDown10.setSelected(false);
                countDownTime = 3;
                break;
            // 倒计时：10秒
            case 6:
                mDataBinding.recordMoreMenuBar.releaseCameraCountDownNo.setSelected(false);
                mDataBinding.recordMoreMenuBar.releaseCameraCountDown3.setSelected(false);
                mDataBinding.recordMoreMenuBar.releaseCameraCountDown10.setSelected(true);
                countDownTime = 10;
                break;
            // 其他
            default:
                break;
        }
    }

    /**
     * 关闭更多操作显示框
     */
    private void closeMoreMenu() {
        mMoreMenuLL.setVisibility(View.GONE);
    }

    /**
     * 停止拍摄
     *
     * @param finished 代表是否结束断点拍摄
     */
    private void stopRecord(boolean finished) {
        // 停止播放背景音乐
        if (mMusicFlag) {
            mKSYRecordKit.stopBgm();
        }
        //停止录制接口为异步接口，sdk在停止结束后会发送
        // StreamerConstants.KSY_STREAMER_FILE_RECORD_STOPPED消息
        //下一次录制响应最好在接收到消息后再进行
        //录制完成进入编辑
        //若录制文件大于1则需要在录制结束后触发文件合成
        if (finished) {
            String fileFolder = getRecordFileFolder();
            //合成文件路径
            String outFile = fileFolder + System.currentTimeMillis() + Constant.VIDEO_TYPE_NAME;
            Log.d(TAG, "合成文件路径 outFile = " + outFile);
            //合成过程为异步，需要block下一步处理
            LoadingDailog.Builder builder = new LoadingDailog.Builder(this)
                    .setMessage(getString(R.string.compose_file))
                    .setCancelable(false)
                    .setCancelOutside(false);
            final LoadingDailog dialog = builder.create();
            dialog.show();
            mKSYRecordKit.stopRecord(outFile, new KSYRecordKit.MergeFilesFinishedListener() {
                @Override
                public void onFinished(final String filePath) {
                    Log.d(TAG, "合成文件路径 filePath = " + filePath);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            dialog.dismiss();
                            mRecordUrl = filePath;  //合成文件本地路径
                            updateRecordUI();
                            if (filePath == null) {
                                Log.e(TAG, "Merge file failed");
                                Toast.makeText(RecordActivity.this, "Merge file failed!",
                                        Toast.LENGTH_LONG).show();
                                return;
                            }


                            //合成结束启动编辑
                            Log.d(TAG, "合成结束启动编辑 mRecordUrl = " + mRecordUrl);
                            RecordEditActivity.startActivity(RecordActivity.this,
                                    mRecordUrl, mMusicFlag, mMusicPath, mMusicId);
                        }
                    });
                }
            });
        } else {
            //普通录制停止
            mKSYRecordKit.stopRecord();
        }

    }

    // Example to handle camera related operation
    private void setCameraAntiBanding50Hz() {
        Camera.Parameters parameters = mKSYRecordKit.getCameraCapture().getCameraParameters();
        if (parameters != null) {
            parameters.setAntibanding(Camera.Parameters.ANTIBANDING_50HZ);
            mKSYRecordKit.getCameraCapture().setCameraParameters(parameters);
        }
    }

    private KSYStreamer.OnInfoListener mOnInfoListener = new KSYStreamer.OnInfoListener() {
        @Override
        public void onInfo(int what, int msg1, int msg2) {
            switch (what) {
                case StreamerConstants.KSY_STREAMER_CAMERA_INIT_DONE:
                    Log.d(TAG, "KSY_STREAMER_CAMERA_INIT_DONE");
                    setCameraAntiBanding50Hz();
                    break;
                case StreamerConstants.KSY_STREAMER_CAMERA_FACEING_CHANGED:
                    break;
                case StreamerConstants.KSY_STREAMER_OPEN_FILE_SUCCESS:
                    Log.d(TAG, "KSY_STREAMER_OPEN_FILE_SUCCESS");
                    mRecordProgressCtl.startRecording();
                    break;
                case StreamerConstants.KSY_STREAMER_FILE_RECORD_STOPPED:
                    Log.d(TAG, "KSY_STREAMER_FILE_RECORD_STOPPED");
                    //未停止结束，最好不要操作开始录制，否则在某些机型上容易造成录制开始失败的case
                    mIsFileRecording = false;
                    updateRecordUI();
                    break;
                default:
                    Log.d(TAG, "OnInfo: " + what + " msg1: " + msg1 + " msg2: " + msg2);
                    break;
            }
        }
    };

    private void updateRecordUI() {
        mDataBinding.recordBottomBar.startCameraBtn.setEnabled(true);
        //更新进度显示
        mRecordProgressCtl.stopRecording();
        mDataBinding.recordBottomBar.startCameraBtn.getDrawable().setLevel(1);
        updateDeleteView();
    }

    /**
     * 不支持硬编的设备，fallback到软编
     */
    private void handleEncodeError() {
        int encodeMethod = mKSYRecordKit.getVideoEncodeMethod();
        if (encodeMethod == StreamerConstants.ENCODE_METHOD_HARDWARE) {
            mHWEncoderUnsupported = true;
            if (mSWEncoderUnsupported) {
                mKSYRecordKit.setEncodeMethod(
                        StreamerConstants.ENCODE_METHOD_SOFTWARE_COMPAT);
                Log.e(TAG, "Got HW encoder error, switch to SOFTWARE_COMPAT mode");
            } else {
                mKSYRecordKit.setEncodeMethod(StreamerConstants.ENCODE_METHOD_SOFTWARE);
                Log.e(TAG, "Got HW encoder error, switch to SOFTWARE mode");
            }
        } else if (encodeMethod == StreamerConstants.ENCODE_METHOD_SOFTWARE) {
            mSWEncoderUnsupported = true;
            if (mHWEncoderUnsupported) {
                mKSYRecordKit.setEncodeMethod(
                        StreamerConstants.ENCODE_METHOD_SOFTWARE_COMPAT);
                Log.e(TAG, "Got SW encoder error, switch to SOFTWARE_COMPAT mode");
            } else {
                mKSYRecordKit.setEncodeMethod(StreamerConstants.ENCODE_METHOD_HARDWARE);
                Log.e(TAG, "Got SW encoder error, switch to HARDWARE mode");
            }
        }
    }

    private KSYStreamer.OnErrorListener mOnErrorListener = new KSYStreamer.OnErrorListener() {
        @Override
        public void onError(int what, int msg1, int msg2) {
            switch (what) {
                case StreamerConstants.KSY_STREAMER_ERROR_AV_ASYNC:
                    Log.d(TAG, "KSY_STREAMER_ERROR_AV_ASYNC " + msg1 + "ms");
                    break;
                case StreamerConstants.KSY_STREAMER_VIDEO_ENCODER_ERROR_UNSUPPORTED:
                    Log.d(TAG, "KSY_STREAMER_VIDEO_ENCODER_ERROR_UNSUPPORTED");
                    break;
                case StreamerConstants.KSY_STREAMER_VIDEO_ENCODER_ERROR_UNKNOWN:
                    Log.d(TAG, "KSY_STREAMER_VIDEO_ENCODER_ERROR_UNKNOWN");
                    break;
                case StreamerConstants.KSY_STREAMER_AUDIO_ENCODER_ERROR_UNSUPPORTED:
                    Log.d(TAG, "KSY_STREAMER_AUDIO_ENCODER_ERROR_UNSUPPORTED");
                    break;
                case StreamerConstants.KSY_STREAMER_AUDIO_ENCODER_ERROR_UNKNOWN:
                    Log.d(TAG, "KSY_STREAMER_AUDIO_ENCODER_ERROR_UNKNOWN");
                    break;
                case StreamerConstants.KSY_STREAMER_AUDIO_RECORDER_ERROR_START_FAILED:
                    Log.d(TAG, "KSY_STREAMER_AUDIO_RECORDER_ERROR_START_FAILED");
                    break;
                case StreamerConstants.KSY_STREAMER_AUDIO_RECORDER_ERROR_UNKNOWN:
                    Log.d(TAG, "KSY_STREAMER_AUDIO_RECORDER_ERROR_UNKNOWN");
                    break;
                case StreamerConstants.KSY_STREAMER_CAMERA_ERROR_UNKNOWN:
                    Log.d(TAG, "KSY_STREAMER_CAMERA_ERROR_UNKNOWN");
                    break;
                case StreamerConstants.KSY_STREAMER_CAMERA_ERROR_START_FAILED:
                    Log.d(TAG, "KSY_STREAMER_CAMERA_ERROR_START_FAILED");
                    break;
                case StreamerConstants.KSY_STREAMER_CAMERA_ERROR_SERVER_DIED:
                    Log.d(TAG, "KSY_STREAMER_CAMERA_ERROR_SERVER_DIED");
                    break;
                //Camera was disconnected due to use by higher priority user.
                case StreamerConstants.KSY_STREAMER_CAMERA_ERROR_EVICTED:
                    Log.d(TAG, "KSY_STREAMER_CAMERA_ERROR_EVICTED");
                    break;
                default:
                    Log.d(TAG, "what=" + what + " msg1=" + msg1 + " msg2=" + msg2);
                    break;
            }
            switch (what) {
                case StreamerConstants.KSY_STREAMER_AUDIO_RECORDER_ERROR_START_FAILED:
                case StreamerConstants.KSY_STREAMER_AUDIO_RECORDER_ERROR_UNKNOWN:
                    break;
                case StreamerConstants.KSY_STREAMER_CAMERA_ERROR_UNKNOWN:
                case StreamerConstants.KSY_STREAMER_CAMERA_ERROR_START_FAILED:
                case StreamerConstants.KSY_STREAMER_CAMERA_ERROR_EVICTED:
                case StreamerConstants.KSY_STREAMER_CAMERA_ERROR_SERVER_DIED:
                    mKSYRecordKit.stopCameraPreview();
                    break;
                case StreamerConstants.KSY_STREAMER_FILE_PUBLISHER_CLOSE_FAILED:
                case StreamerConstants.KSY_STREAMER_FILE_PUBLISHER_ERROR_UNKNOWN:
                case StreamerConstants.KSY_STREAMER_FILE_PUBLISHER_OPEN_FAILED:
                case StreamerConstants.KSY_STREAMER_FILE_PUBLISHER_FORMAT_NOT_SUPPORTED:
                case StreamerConstants.KSY_STREAMER_FILE_PUBLISHER_WRITE_FAILED:
                    stopRecord(false);
                    rollBackClipForError();
                    break;
                case StreamerConstants.KSY_STREAMER_VIDEO_ENCODER_ERROR_UNSUPPORTED:
                case StreamerConstants.KSY_STREAMER_VIDEO_ENCODER_ERROR_UNKNOWN: {
                    handleEncodeError();
                    stopRecord(false);
                    rollBackClipForError();
                    mMainHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            startRecord();
                        }
                    }, 200);
                }
                break;
                default:
                    break;
            }
        }
    };

    private StatsLogReport.OnLogEventListener mOnLogEventListener =
            new StatsLogReport.OnLogEventListener() {
                @Override
                public void onLogEvent(StringBuilder singleLogContent) {
                    Log.i(TAG, "***onLogEvent : " + singleLogContent.toString());
                }
            };

    /**
     * 前后置摄像头切换
     */
    @Override
    public void onSwitchCamera() {
        mKSYRecordKit.switchCamera();
    }

    /**
     * 返回键点击事件,根据不同状态执行不同内容
     */
    @Override
    public void onBackPressed() {
        mPresenter.onBackOffClick();
    }

    /**
     * back按钮作为返回上一级和删除按钮
     * 当录制文件>=1时 作为删除按钮，否则作为返回上一级按钮
     * 作为删除按钮时，初次点击时先设置为待删除状态，在带删除状态下再执行文件回删
     */
    @Override
    public void backOffClick() {

        if (mDataBinding.recordBottomBar.startCameraBtn.getVisibility() == View.VISIBLE) {
            // 若美颜设置窗口打开，则关闭
            if (mBeautyLayout.getVisibility() == View.VISIBLE) {
                mBeautyLayout.setVisibility(View.GONE);
                // 显示底部按钮
                mDataBinding.recordBottomBar.recordBottomRl.setVisibility(View.VISIBLE);
            } else {
                // 待录制状态下，作为返回上一级按钮
                super.onBackPressed();
            }
        } else if (mDataBinding.recordBottomBar.stopCameraBtn.getVisibility() == View.VISIBLE) {
            // 录制中状态下，BackKey无效
        } else if (mDataBinding.recordBottomBar.restartCameraBtn.getVisibility() == View.VISIBLE) {
            mIsFileRecording = false;
            super.onBackPressed();

//            // 断点录制状态下，显示保存Dialog
//            showSaveDialog();
        }
    }

    /**
     * 删除上一段视频
     */
    @Override
    public void deleteLastVideo() {
        showDeleteDialog();
    }
//
//    /**
//     * 弹出保存/舍弃的Dialog
//     */
//    private void showSaveDialog() {
//        // 如果保存Dialog正在显示，则不再重建
//        if (saveDialog != null && saveDialog.isShowing()) {
//            return;
//        }
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        saveDialog = builder.setTitle(R.string.release_save_dialog_title)
//                .setMessage(R.string.release_save_dialog_msg)
//                .setPositiveButton(getResources().getString(R.string.release_save_btn_tx), new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        dialogInterface.dismiss();
//                        closeActivity();
//                    }
//                }).setNegativeButton(getResources().getString(R.string.release_destroy_btn_tx), new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        dialogInterface.dismiss();
//                    }
//                }).create();
//        saveDialog.show();
//    }

    /**
     * 弹出删除的Dialog
     */
    private void showDeleteDialog() {
        // 如果保存Dialog正在显示，则不再重建
        if (deleteDialog != null && deleteDialog.isShowing()) {
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        deleteDialog = builder.setTitle(R.string.release_delete_dialog_title)
                .setMessage(R.string.release_delete_dialog_msg)
                .setPositiveButton(getResources().getString(R.string.release_confirm_btn_tx), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        if (mKSYRecordKit.getRecordedFilesCount() > 1) {
                            //设置最后一个文件为待删除文件
                            mRecordProgressCtl.setLastClipPending();
                            //删除录制文件
                            mKSYRecordKit.deleteRecordFile(mKSYRecordKit.getLastRecordedFiles());
                            mRecordProgressCtl.rollback();
                            // 录制达到最长时间不可再录制，若删除上一段视频，要恢复再录制按钮的活性
                            mDataBinding.recordBottomBar.restartCameraBtn.setEnabled(true);
                            // 删除最后一段视频后，剩余视频时间不超过最短录制时间的情况下
                            if (!mRecordProgressCtl.isPassMinPoint()) {
                                mDataBinding.recordBottomBar.saveVideoBtn.setVisibility(View.GONE);
                            }
                        } else if (mKSYRecordKit.getRecordedFilesCount() == 1) {
                            // 设置是否超过最短录制时间为false
                            isPassedMinTime = false;

                            //设置最后一个文件为待删除文件
                            mRecordProgressCtl.setLastClipPending();
                            //删除录制文件
                            mKSYRecordKit.deleteRecordFile(mKSYRecordKit.getLastRecordedFiles());
                            mRecordProgressCtl.rollback();
                            // 显示待录制画面
                            // 顶部按钮显示/非显示控制
                            mDataBinding.actionbar.closeCamera.setVisibility(View.VISIBLE);
                            mDataBinding.actionbar.moreCamera.setVisibility(View.VISIBLE);
                            mDataBinding.actionbar.reverseCamera.setVisibility(View.VISIBLE);
                            if (mMusicFlag) {
                                mDataBinding.actionbar.recordMusicName.setVisibility(View.VISIBLE);
                            } else {
                                mDataBinding.actionbar.musicCamera.setVisibility(View.VISIBLE);
                            }
                            mDataBinding.actionbar.progressBar.setVisibility(View.GONE);

                            // 底部按钮显示/非显示控制
                            mBeautyLayout.setVisibility(View.GONE);
                            mDataBinding.recordBottomBar.restartCameraBtn.setVisibility(View.GONE);
                            mDataBinding.recordBottomBar.fairCamera.setVisibility(View.VISIBLE);
                            mDataBinding.recordBottomBar.startCameraBtn.setVisibility(View.VISIBLE);
                            mDataBinding.recordBottomBar.stopCameraBtn.setVisibility(View.GONE);
                            mDataBinding.recordBottomBar.deleteLastVideoBtn.setVisibility(View.GONE);
                            mDataBinding.recordBottomBar.saveVideoBtn.setVisibility(View.GONE);
                            mDataBinding.recordBottomBar.selectTabLl.setVisibility(View.VISIBLE);
                        }
                    }
                }).setNegativeButton(getResources().getString(R.string.release_cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).create();
        deleteDialog.show();
    }

    /**
     * 开始录制
     */
    @Override
    public void onRecordClick() {
        // 关闭更多操作显示框
        closeMoreMenu();
        // 顶部按钮显示/非显示控制
        mDataBinding.actionbar.closeCamera.setVisibility(View.GONE);
        mDataBinding.actionbar.moreCamera.setVisibility(View.GONE);
        mDataBinding.actionbar.reverseCamera.setVisibility(View.GONE);
        mDataBinding.actionbar.recordMusicName.setVisibility(View.GONE);
        mDataBinding.actionbar.musicCamera.setVisibility(View.GONE);
        mDataBinding.actionbar.progressBar.setVisibility(View.VISIBLE);

        // 底部按钮显示/非显示控制
        mBeautyLayout.setVisibility(View.GONE);
        mDataBinding.recordBottomBar.restartCameraBtn.setVisibility(View.GONE);
        mDataBinding.recordBottomBar.fairCamera.setVisibility(View.GONE);
        mDataBinding.recordBottomBar.startCameraBtn.setVisibility(View.GONE);
        mDataBinding.recordBottomBar.stopCameraBtn.setVisibility(View.VISIBLE);
        mDataBinding.recordBottomBar.deleteLastVideoBtn.setVisibility(View.GONE);
        if (isPassedMinTime) {
            mDataBinding.recordBottomBar.saveVideoBtn.setVisibility(View.VISIBLE);
        } else {
            mDataBinding.recordBottomBar.saveVideoBtn.setVisibility(View.GONE);
        }
        mDataBinding.recordBottomBar.selectTabLl.setVisibility(View.INVISIBLE);

        // 设置了倒计时
        if (countDownTime != 0) {
            startCountDownAnimation(countDownTime);
            countDownTime = 0;
        } else {
            startRecord();
        }
    }

    /**
     * 进入编辑页面
     */
    @Override
    public void onNextClick() {
        //进行编辑前需要停止录制，并且结束断点拍摄
        stopRecord(true);
        clearBackoff();
        clearRecordState();
    }

    /**
     * 更多操作按钮点击事件
     */
    @Override
    public void onMoreClick() {
        if (mMoreMenuLL.getVisibility() == View.VISIBLE) {
            mMoreMenuLL.setVisibility(View.GONE);
        } else {
            mMoreMenuLL.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 添加美颜效果
     */
    private void addBeautyFiler() {
        if (mImgBeautyTypeIndex == mLastImgBeautyTypeIndex) {
            return;
        }
        if (mBeautyFilters == null) {
            mBeautyFilters = new LinkedHashMap<>();
        }

        //disable beauty
        if (mImgBeautyTypeIndex == BEAUTY_DISABLE) {
            if (mBeautyFilters.containsKey(mLastImgBeautyTypeIndex)) {
                ImgFilterBase lastFilter = mBeautyFilters.get
                        (mLastImgBeautyTypeIndex);
                if (mKSYRecordKit.getImgTexFilterMgt().getFilter().contains(lastFilter)) {
                    mKSYRecordKit.getImgTexFilterMgt().replaceFilter(lastFilter, null, false);
                }
            }
            mLastImgBeautyTypeIndex = mImgBeautyTypeIndex;
            return;
        }
        //enable filter
        if (mBeautyFilters.containsKey(mImgBeautyTypeIndex)) {
            ImgFilterBase filterBase = mBeautyFilters.get
                    (mImgBeautyTypeIndex);
            if (mBeautyFilters.containsKey(mLastImgBeautyTypeIndex)) {
                ImgFilterBase lastFilter = mBeautyFilters.get(mLastImgBeautyTypeIndex);
                if (mKSYRecordKit.getImgTexFilterMgt().getFilter().contains(lastFilter)) {
                    mKSYRecordKit.getImgTexFilterMgt().replaceFilter(lastFilter, filterBase, false);
                }
            } else {
                if (!mKSYRecordKit.getImgTexFilterMgt().getFilter().contains(filterBase)) {
                    mKSYRecordKit.getImgTexFilterMgt().addFilter(filterBase);
                }
            }
            mLastImgBeautyTypeIndex = mImgBeautyTypeIndex;
            return;
        }
        ImgFilterBase filterBase = null;
        switch (mImgBeautyTypeIndex) {
            case BEAUTY_NATURE:
                ImgBeautySoftFilter softFilter = new ImgBeautySoftFilter(mKSYRecordKit.getGLRender());
                softFilter.setGrindRatio(0.5f);
                filterBase = softFilter;
                break;
            case BEAUTY_PRO:
                ImgBeautyProFilter proFilter = new ImgBeautyProFilter(mKSYRecordKit.getGLRender()
                        , getApplicationContext());
                proFilter.setGrindRatio(0.5f);
                proFilter.setWhitenRatio(0.5f);
                proFilter.setRuddyRatio(0);
                filterBase = proFilter;
                break;
            case BEAUTY_FLOWER_LIKE:
                ImgBeautyProFilter pro1Filter = new ImgBeautyProFilter(mKSYRecordKit.getGLRender()
                        , getApplicationContext(), 3);
                pro1Filter.setGrindRatio(0.5f);
                pro1Filter.setWhitenRatio(0.5f);
                pro1Filter.setRuddyRatio(0.15f);
                mBeautyFilters.put(BEAUTY_FLOWER_LIKE, pro1Filter);
                filterBase = pro1Filter;
                break;
            case BEAUTY_DELICATE:
                ImgBeautyProFilter pro2Filter = new ImgBeautyProFilter(mKSYRecordKit.getGLRender()
                        , getApplicationContext(), 3);
                pro2Filter.setGrindRatio(0.5f);
                pro2Filter.setWhitenRatio(0.5f);
                pro2Filter.setRuddyRatio(0.3f);
                filterBase = pro2Filter;
                break;
            case BEAUTY_DISABLE:
                break;
            default:
                break;
        }

        if (filterBase != null) {
            ImgFilterBase lastFilter = null;
            if (mBeautyFilters.containsKey(mLastImgBeautyTypeIndex)) {
                lastFilter = mBeautyFilters.get(mLastImgBeautyTypeIndex);

            }
            mBeautyFilters.put(mImgBeautyTypeIndex, filterBase);
            if (lastFilter != null && mKSYRecordKit.getImgTexFilterMgt().getFilter().contains
                    (lastFilter)) {
                mKSYRecordKit.getImgTexFilterMgt().replaceFilter(lastFilter, filterBase, false);
            } else {
                mKSYRecordKit.getImgTexFilterMgt().addFilter(filterBase);
            }

        }
        mLastImgBeautyTypeIndex = mImgBeautyTypeIndex;
    }

    /**
     * 添加滤镜效果
     */
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
                if (mKSYRecordKit.getImgTexFilterMgt().getFilter().contains(lastFilter)) {
                    mKSYRecordKit.getImgTexFilterMgt().replaceFilter(lastFilter, null, false);
                }
            }
            mLastEffectFilterIndex = mEffectFilterIndex;
            return;
        }
        if (mEffectFilters.containsKey(mEffectFilterIndex)) {
            ImgFilterBase filter = mEffectFilters.get(mEffectFilterIndex);
            if (mEffectFilters.containsKey(mLastEffectFilterIndex)) {
                ImgFilterBase lastfilter = mEffectFilters.get(mLastEffectFilterIndex);
                if (mKSYRecordKit.getImgTexFilterMgt().getFilter().contains(lastfilter)) {
                    mKSYRecordKit.getImgTexFilterMgt().replaceFilter(lastfilter, filter, false);
                }
            } else {
                if (!mKSYRecordKit.getImgTexFilterMgt().getFilter().contains(filter)) {
                    mKSYRecordKit.getImgTexFilterMgt().addFilter(filter);
                }
            }
            mLastEffectFilterIndex = mEffectFilterIndex;
        } else {
            ImgFilterBase filter;
            if (mFilterTypeIndex < 13) {
                filter = new ImgBeautySpecialEffectsFilter(mKSYRecordKit.getGLRender(),
                        getApplicationContext(), mEffectFilterIndex);
            } else {
                filter = new ImgBeautyStylizeFilter(mKSYRecordKit
                        .getGLRender(), getApplicationContext(), mEffectFilterIndex);
            }
            mEffectFilters.put(mEffectFilterIndex, filter);
            ImgFilterBase lastFilter = null;
            if (mEffectFilters.containsKey(mLastEffectFilterIndex)) {
                lastFilter = mEffectFilters.get(mLastEffectFilterIndex);
            }
            if (lastFilter != null && mKSYRecordKit.getImgTexFilterMgt().getFilter().contains
                    (lastFilter)) {
                mKSYRecordKit.getImgTexFilterMgt().replaceFilter(lastFilter, filter, false);
            } else {
                mKSYRecordKit.getImgTexFilterMgt().addFilter(filter);
            }
            mLastEffectFilterIndex = mEffectFilterIndex;
        }
    }

    /**
     * 设置滤镜
     *
     * @param type 滤镜类型
     */
    private void setEffectFilter(int type) {
        mEffectFilterIndex = type;
        addEffectFilter();
    }

    /**
     * 初始化美颜设置
     */
    private void initBeautyUI() {
        mBeauty = findViewById(R.id.item_beauty);
        mBeautyChooseView = findViewById(R.id.record_beauty_choose);
        BottomTitleViewInfo mBeautyInfo = new BottomTitleViewInfo(mBeauty,
                mBeautyChooseView, mObserverButton);
        mBeautyInfo.setChosenState(true);
        mRecordTitleArray.put(INDEX_BEAUTY_TITLE_BASE, mBeautyInfo);


        final int[] BEAUTY_TYPE = {BEAUTY_NATURE, BEAUTY_PRO, BEAUTY_FLOWER_LIKE, BEAUTY_DELICATE};
        mBeautyOriginalView = findViewById(R.id.iv_beauty_origin);
        mBeautyBorder = findViewById(R.id.iv_beauty_border);
        mBeautyOriginalText = findViewById(R.id.tv_beauty_origin);
        mBeautyRecyclerView = findViewById(R.id.beauty_recyclerView);
        changeOriginalBeautyState(true);
        List<ImageTextAdapter.Data> beautyData = DataFactory.getBeautyTypeDate(this);
        final ImageTextAdapter beautyAdapter = new ImageTextAdapter(this, beautyData);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mBeautyRecyclerView.setLayoutManager(layoutManager);
        ImageTextAdapter.OnImageItemClickListener listener = new ImageTextAdapter.OnImageItemClickListener() {
            @Override
            public void onClick(int index) {
                if (mBeautyOriginalText.isActivated()) {
                    changeOriginalBeautyState(false);
                }
                mImgBeautyTypeIndex = BEAUTY_TYPE[index];
                addBeautyFiler();
            }
        };
        mBeautyOriginalView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                beautyAdapter.clear();
                changeOriginalBeautyState(true);
                mImgBeautyTypeIndex = BEAUTY_DISABLE;
                addBeautyFiler();
            }
        });
        beautyAdapter.setOnImageItemClick(listener);
        mBeautyRecyclerView.setAdapter(beautyAdapter);
    }

    /**
     * 设置选中View显示/非显示状态
     *
     * @param isSelected 选中标识
     */
    private void changeOriginalBeautyState(boolean isSelected) {
        if (isSelected) {
            mBeautyBorder.setVisibility(View.VISIBLE);
            mBeautyOriginalText.setActivated(true);
        } else {
            mBeautyBorder.setVisibility(View.INVISIBLE);
            mBeautyOriginalText.setActivated(false);
        }
    }

    /**
     * 视频滤镜
     */
    private void initFilterUI() {

        mFilter = findViewById(R.id.item_filter);

        mFilterChooseView = findViewById(R.id.record_filter_choose);
        BottomTitleViewInfo mFilterInfo = new BottomTitleViewInfo(mFilter,
                mFilterChooseView, mObserverButton);
        mRecordTitleArray.put(INDEX_BEAUTY_TITLE_BASE + 1, mFilterInfo);

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
        final ImageTextAdapter adapter = new ImageTextAdapter(this, filterData);
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
                adapter.clear();
                changeOriginalImageState(true);
            }
        });
        adapter.setOnImageItemClick(listener);
        mFilterRecyclerView.setAdapter(adapter);
    }

    /**
     * 初始化在线音乐菜单界面
     */
    private void initOnlineMusic() {
        // 在线音乐操作窗口
        mOnlineMusicLayout = findViewById(R.id.item_music_menu);
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

    /**
     * 重置录制状态
     */
    private void clearRecordState() {
        mImgBeautyTypeIndex = BEAUTY_DISABLE;
        mEffectFilterIndex = FILTER_DISABLE;
        mFilterOriginImage.callOnClick();
        addBeautyFiler();
        addEffectFilter();
    }

    /**
     * 设置镜头速度
     *
     * @param speed 镜头速度值
     */
    private void onSpeedClick(float speed) {
        mKSYRecordKit.setRecordSpeed(speed);
    }

    /**
     * 检测APP获取到的权限并开启相机
     */
    private void startCameraPreviewWithPermCheck() {
        int cameraPerm = ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        int audioPerm = ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO);
        int readPerm = ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int writePerm = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (cameraPerm != PackageManager.PERMISSION_GRANTED ||
                audioPerm != PackageManager.PERMISSION_GRANTED ||
                readPerm != PackageManager.PERMISSION_GRANTED ||
                writePerm != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                String[] permissions = {
                        Manifest.permission.CAMERA,
                        Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE};
                ActivityCompat.requestPermissions(this, permissions,
                        PERMISSION_REQUEST_CAMERA_AUDIO_REC);
            }
        } else {
            mKSYRecordKit.startCameraPreview();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CAMERA_AUDIO_REC: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mKSYRecordKit.startCameraPreview();
                } else {
                    for (int grant : grantResults) {
                        if (grant != PackageManager.PERMISSION_GRANTED) {
                            // 若用户不授予权限，提示手动授权弹窗
                            openAppPermissionDetails();
                        }
                    }
                }
                break;
            }
        }
    }

    /**
     * 若用户不授予权限
     * 打开 APP 的详情设置
     */
    private void openAppPermissionDetails() {
        if (null != permissionDialog && permissionDialog.isShowing()) {
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        permissionDialog = builder.setMessage(R.string.release_set_permission_msg)
                .setPositiveButton(getResources().getString(R.string.release_set), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        intent.addCategory(Intent.CATEGORY_DEFAULT);
                        intent.setData(Uri.parse("package:" + getPackageName()));
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                        startActivity(intent);
                    }
                }).setNegativeButton(getResources().getString(R.string.release_cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        showToast(getString(R.string.release_permission_refused));
                        // 画面结束
                        closeActivity();
                    }
                }).create();
        permissionDialog.show();
    }

    /**
     * 开始拍摄更新，删除按钮状态
     */
    private void updateDeleteView() {
    }

    /**
     * 清除back按钮的状态（删除），并设置最后一个录制的文件为普通文件
     *
     * @return
     */
    private boolean clearBackoff() {
        //设置最后一个文件为普通文件
        mRecordProgressCtl.setLastClipNormal();
        return false;
    }

    /**
     * 拍摄错误停止后，删除多余文件的进度
     */
    private void rollBackClipForError() {
        //当拍摄异常停止时，SDk内部会删除异常文件，如果ctl比SDK返回的文件小，则需要更新ctl中的进度信息
        int clipCount = mRecordProgressCtl.getClipListSize();
        int fileCount = mKSYRecordKit.getRecordedFilesCount();
        if (clipCount > fileCount) {
            int diff = clipCount - fileCount;
            for (int i = 0; i < diff; i++) {
                mRecordProgressCtl.rollback();
            }
        }
    }

    /**
     * 录制时间变更的监听
     */
    private RecordProgressController.RecordingLengthChangedListener mRecordLengthChangedListener =
            new RecordProgressController.RecordingLengthChangedListener() {
                @Override
                public void passMinPoint(boolean pass) {
                    // 超过最短时长显示下一步按钮，否则不能进入编辑，最短时长设定为5s
                    isPassedMinTime = pass;
                    if (isPassedMinTime) {
                        mDataBinding.recordBottomBar.saveVideoBtn.setVisibility(View.VISIBLE);
                    } else {
                        mDataBinding.recordBottomBar.saveVideoBtn.setVisibility(View.GONE);
                    }

                }

                @Override
                public void passMaxPoint() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // 开始按钮无效
                            mDataBinding.recordBottomBar.restartCameraBtn.setEnabled(false);
                            // 设置再次录制的显示
                            setRestartViews();
                            // 到达最大拍摄时长时，需要主动停止录制
                            stopRecord(true);
                        }
                    });
                }
            };

    /**
     * 创建视频存储文件夹路径
     *
     * @return fileFolder 视频存储文件夹路径
     */
    private String getRecordFileFolder() {
        String fileFolder = getContext().getFilesDir().getPath() + Constant.RECORD_BOX_NAME;
        File file = new File(fileFolder);
        if (!file.exists()) {
            file.mkdirs();
        }
        return fileFolder;
    }

    /**
     * 封装一个TextView和View，TextView用来显示标题，View代表相应的布局
     * 提供setChosenState()方法用来统一设置选中（非选中）下各组件的状态
     */
    public class BottomTitleViewInfo {
        private TextView titleView;
        private View relativeLayout;

        public BottomTitleViewInfo(TextView tv, View layout,
                                   View.OnClickListener onClickListener) {
            this.titleView = tv;
            this.relativeLayout = layout;
            if (titleView != null) {
                titleView.setOnClickListener(onClickListener);
            }
        }

        public void setChosenState(boolean isChosen) {
            if (isChosen) {
                relativeLayout.setVisibility(View.VISIBLE);
                titleView.setActivated(true);
                titleView.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
            } else {
                relativeLayout.setVisibility(View.GONE);
                titleView.setActivated(false);
                titleView.setTextColor(getResources().getColor(android.R.color.white));
            }
        }
    }

    /**
     * SDK鉴权
     */
    private void checkAuth() {
        String token = null;
        try {
            InputStream in = getResources().getAssets().open("AuthForWoXingWoXiu.pkg");

            int length = in.available();

            byte[] buffer = new byte[length];

            in.read(buffer);

            token = EncodingUtils.getString(buffer, "UTF-8");

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!TextUtils.isEmpty(token)) {

            // 离线鉴权设置鉴权信息接口
            AuthInfoManager.getInstance().setAuthInfo(token);
            // 触发鉴权
            AuthInfoManager.getInstance().checkAuth();

            // 鉴权结果查询
            // 返回鉴权成功还是失败
            if (!AuthInfoManager.getInstance().getAuthState()) {
                showToast(getString(R.string.release_check_auth_failed));
                // 画面结束
                closeActivity();
            }
        }
    }

    /**
     * 美颜标题点击事件处理
     */
    private void onBeautyTitleClick(int index) {
        BottomTitleViewInfo curInfo = mRecordTitleArray.get(INDEX_BEAUTY_TITLE_BASE + index);
        BottomTitleViewInfo preInfo = mRecordTitleArray.get(INDEX_BEAUTY_TITLE_BASE + mPreBeautyTitleIndex);
        if (index != mPreBeautyTitleIndex) {
            curInfo.setChosenState(true);
            preInfo.setChosenState(false);
            mPreBeautyTitleIndex = index;
        }
    }

    /**
     * 美颜，滤镜点击事件
     */
    private class ButtonObserver implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.item_beauty:
                    onBeautyTitleClick(0);
                    break;
                case R.id.item_filter:
                    onBeautyTitleClick(1);
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 退出在线音乐
     */
    @Override
    public void onMusicExit() {
        mMusicFlag = false;
        mMusicPath = "";
        mOnlineMusicLayout.setVisibility(View.GONE);
        // 显示音乐设置按钮
        mDataBinding.actionbar.musicCamera.setVisibility(View.VISIBLE);
        // 隐藏背景音乐名字
        mDataBinding.actionbar.recordMusicName.setVisibility(View.GONE);
        // 显示底部按钮
        mDataBinding.recordBottomBar.recordBottomRl.setVisibility(View.VISIBLE);

        String pathBase = getApplicationContext().getFilesDir().getPath();
        // 删除已下载的所有音乐
        File musicFile = new File(pathBase + Constant.MUSIC_BOX_NAME);
        deleteAllFiles(musicFile);
    }

    /**
     * 递归删除所有音乐文件
     *
     */
    private void deleteAllFiles(File root) {
        String pathBase = getApplicationContext().getFilesDir().getPath();
        // 删除已下载的所有音乐
        File musicFile = new File(pathBase + Constant.MUSIC_BOX_NAME);

        if (root.exists()) {
            File files[] = musicFile.listFiles();
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
     * 已设定的音乐按钮点击事件
     */
    @Override
    public void onMusicOnlineClick() {

        if (mOnlineMusicLayout.getVisibility() == View.VISIBLE) {
            // 在线音乐操作窗口正在显示
            mOnlineMusicLayout.setVisibility(View.GONE);
            // 显示底部按钮
            mDataBinding.recordBottomBar.recordBottomRl.setVisibility(View.VISIBLE);
            mBeautyLayout.setVisibility(View.GONE);
        } else {
            // 在线音乐操作窗口正在显示
            mOnlineMusicLayout.setVisibility(View.VISIBLE);
            // 显示底部按钮
            mDataBinding.recordBottomBar.recordBottomRl.setVisibility(View.GONE);
            mBeautyLayout.setVisibility(View.GONE);
        }
    }

    /**
     * 变更在线音乐
     */
    @Override
    public void onMusicChange() {
        mOnlineMusicLayout.setVisibility(View.GONE);
        // 显示底部按钮
        mDataBinding.recordBottomBar.recordBottomRl.setVisibility(View.VISIBLE);
        // 启动在线音乐选择画面
        Intent intent = new Intent(RecordActivity.this, MusicOnlineActivity.class);
        startActivityForResult(intent, REQUEST_MUSIC_CODE);
    }

    /**
     * 取消变更在线音乐
     */
    @Override
    public void onCancelMusicChange() {
        mOnlineMusicLayout.setVisibility(View.GONE);
        // 显示底部按钮
        mDataBinding.recordBottomBar.recordBottomRl.setVisibility(View.VISIBLE);
    }

}
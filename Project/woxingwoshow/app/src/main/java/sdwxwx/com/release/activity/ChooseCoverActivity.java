package sdwxwx.com.release.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.v7.widget.AppCompatSeekBar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ksyun.media.shortvideo.utils.ProbeMediaInfoTools;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import sdwxwx.com.R;
import sdwxwx.com.base.BaseCallback;
import sdwxwx.com.cons.Constant;
import sdwxwx.com.login.utils.LoginHelper;
import sdwxwx.com.release.model.ReleaseModel;

public class ChooseCoverActivity extends Activity {
    public static final String TAG = "PublishActivity";

    // 选择封面
    private static final int REQUEST_COVER_CODE = 10100;

    private MediaMetadataRetriever mediaMetadataRetriever;
    private ImageView mCoverBack;
    private TextView mCoverComplete;
    private ImageView mCoverImage;
    private AppCompatSeekBar mCoverSeekBar;
    // 编缉封面标题
    private EditText mEditTitle;
    // 用户头像
    private CircleImageView mUserIcon;
    // 用户昵称
    private TextView mUserName;

    private String mLocalPath;  //合成视频的本地存储地址
    private volatile Bitmap mBitmap;  //视频封面
    private long mSeekTime;
    private long mPreviewLength;  //视频预览时长
    private ButtonObserver mButtonObserver;
    private ProbeMediaInfoTools mImageSeekTools; //根据时间获取视频帧的工具类

    private HandlerThread mSeekThumbnailThread;
    private Handler mSeekThumbnailHandler;
    private Runnable mSeekThumbnailRunable;
    private volatile boolean mStopSeekThumbnail = true;
    // 敏感词列表
    private List<String> mListSensitiveWords = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_choose_cover);
        //must set
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mLocalPath = getIntent().getExtras().getString(Constant.COMPOSE_PATH);
        mediaMetadataRetriever = new MediaMetadataRetriever();
        mediaMetadataRetriever.setDataSource(mLocalPath);
        // 取得视频的长度(单位为毫秒)
        String time = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
        // 单位为微秒
        mPreviewLength = Long.valueOf(time);

        initView();
        mButtonObserver = new ButtonObserver();
        startCoverSeek();
    }

    private void initView() {
        // 编缉封面标题
        mEditTitle = findViewById(R.id.edit_title);
        // 用户名
        mUserName = findViewById(R.id.user_name);
        mUserName.setText(LoginHelper.getInstance().getUserBean().getNickname());
        // 用户昵称
        mUserIcon = findViewById(R.id.user_icon);
        // 用户头像
        RequestOptions options = new RequestOptions().error(R.drawable.default_header_image);
        Glide.with(this)
                .load(LoginHelper.getInstance().getUserBean().getAvatar_url())
                .apply(options)
                .into(mUserIcon);
    }

    private void initSeekThread() {
        if (mSeekThumbnailThread == null) {
            mSeekThumbnailThread = new HandlerThread("screen_setup_thread", Thread.NORM_PRIORITY);
            mSeekThumbnailThread.start();
            mSeekThumbnailHandler = new Handler(mSeekThumbnailThread.getLooper()) {
                @Override
                public void handleMessage(Message msg) {
                    return;
                }
            };

            mSeekThumbnailRunable = new Runnable() {
                @Override
                public void run() {
                    if (mSeekTime < 0) {
                        mSeekTime = 0;
                    }

                    if (mSeekTime > mPreviewLength) {
                        mSeekTime = mPreviewLength;
                    }

                    mBitmap = mImageSeekTools.getVideoThumbnailAtTime(mLocalPath, mSeekTime,
                            0, 0, true);
//                    mBitmap = mediaMetadataRetriever.getFrameAtTime(mSeekTime);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (mBitmap != null) {
                                mCoverImage.setImageBitmap(mBitmap);
                            }
                        }
                    });
                    if (!mStopSeekThumbnail) {
                        mSeekThumbnailHandler.postDelayed(mSeekThumbnailRunable, 100);
                    }
                }
            };
        }
    }

    /**
     * 开始选择封面相关准备
     */
    private void startCoverSeek() {
        mCoverBack = (ImageView) findViewById(R.id.cover_back);
        mCoverBack.setOnClickListener(mButtonObserver);
        mCoverComplete = (TextView) findViewById(R.id.cover_complete);
        mCoverComplete.setOnClickListener(mButtonObserver);
        mCoverImage = (ImageView) findViewById(R.id.cover_image);
        mCoverSeekBar = (AppCompatSeekBar) findViewById(R.id.cover_seekBar);
        mImageSeekTools = new ProbeMediaInfoTools();
        mBitmap = mImageSeekTools.getVideoThumbnailAtTime(mLocalPath, mSeekTime, 0, 0, true);

//        mBitmap = mediaMetadataRetriever.getFrameAtTime(mSeekTime);
        mImageSeekTools.probeMediaInfo(mLocalPath, new ProbeMediaInfoTools.ProbeMediaInfoListener() {
            @Override
            public void probeMediaInfoFinished(ProbeMediaInfoTools.MediaInfo info) {
                //使用合成视频时长更新视频的时长
                mPreviewLength = info.duration;
            }
        });

        mCoverImage.setImageBitmap(mBitmap);

        int bitmapHeight = mBitmap.getHeight();
        int bitmapWidth = mBitmap.getWidth();
        int h = mCoverImage.getMaxHeight();
        int w = h * bitmapWidth / bitmapHeight;
        mCoverImage.setAdjustViewBounds(true);
        mCoverImage.setMaxWidth(w);
        // 取出视频中五个Bitmap
        // No.1
        ImageView imageView1 = findViewById(R.id.preview_img_no1);
        long timeL = mPreviewLength / 4;
        Log.d(TAG, " 取出视频中五个Bitmap timeL = " + timeL);
        // 预览画面显示视频第一帧
        imageView1.setImageBitmap(
                mImageSeekTools.getVideoThumbnailAtTime(mLocalPath, 0, 0, 0, true));
        // No.2
        ImageView imageView2 = findViewById(R.id.preview_img_no2);
        // 第一个参数是微秒，不是毫秒
        imageView2.setImageBitmap(
                mImageSeekTools.getVideoThumbnailAtTime(mLocalPath, timeL, 0, 0, true));
//        mediaMetadataRetriever.getFrameAtTime(
//                timeL, MediaMetadataRetriever.OPTION_CLOSEST)
        // No.3
        ImageView imageView3 = findViewById(R.id.preview_img_no3);
        imageView3.setImageBitmap(
                mImageSeekTools.getVideoThumbnailAtTime(mLocalPath, timeL * 2, 0, 0, true));
        // No.4
        ImageView imageView4 = findViewById(R.id.preview_img_no4);
        imageView4.setImageBitmap(
                mImageSeekTools.getVideoThumbnailAtTime(mLocalPath, timeL * 3, 0, 0, true));
        // No.5
        ImageView imageView5 = findViewById(R.id.preview_img_no5);
        imageView5.setImageBitmap(
                mImageSeekTools.getVideoThumbnailAtTime(mLocalPath, timeL * 4, 0, 0, true));

        mCoverSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float rate = progress / 100.f;
                mSeekTime = (long) (mPreviewLength * rate);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                initSeekThread();
                mStopSeekThumbnail = false;
                mSeekThumbnailHandler.postDelayed(mSeekThumbnailRunable, 100);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mStopSeekThumbnail = true;
                if (mSeekThumbnailHandler != null) {
                    mSeekThumbnailHandler.removeCallbacksAndMessages(null);
                }
                mSeekThumbnailHandler.post(mSeekThumbnailRunable);
            }
        });
    }

    private class ButtonObserver implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.cover_complete:
                    // 确认选择封面
                    checkSensitive();
                    break;
                case R.id.cover_back:
                    onBackPressed();
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 设置返回结果，并结束自身Activity
     */
    private void setCoverResult() {
        Intent intent = new Intent();
        intent.putExtra(Constant.CHOOSE_COVER_BITMAP, mSeekTime);
        intent.putExtra(Constant.CHOOSE_COVER_TITLE, mEditTitle.getText());
        setResult(Activity.RESULT_OK, intent);
        finish();
    }


    /**
     * 检测敏感词汇
     */
    private void checkSensitive() {
        Log.d(TAG, "checkSensitive start");
        mListSensitiveWords.clear();

        ReleaseModel.getSensitiveList(new BaseCallback<List<String>>() {
            @Override
            public void onSuccess(List<String> data) {
                Log.d(TAG, "checkSensitive onSuccess");
                mListSensitiveWords.addAll(data);
            }

            @Override
            public void onFail(String msg) {
                Log.d(TAG, "checkSensitive onFail");

                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                return;
            }
        });

        int sensitiveSize = mListSensitiveWords.size();
        // 标题
        if (!TextUtils.isEmpty(mEditTitle.getText())) {
            for (int i = 0; i < sensitiveSize; i ++) {
                if (mEditTitle.getText().toString().contains(mListSensitiveWords.get(i))) {
                    Toast.makeText(this, getString(R.string.sensitive_content_remind), Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        }

        setCoverResult();
        Log.d(TAG, "checkSensitive end");
    }

}

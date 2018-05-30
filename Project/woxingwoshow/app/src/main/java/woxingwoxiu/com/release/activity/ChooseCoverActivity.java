package woxingwoxiu.com.release.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.v7.widget.AppCompatSeekBar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.ksyun.media.shortvideo.utils.ProbeMediaInfoTools;

import woxingwoxiu.com.R;
import woxingwoxiu.com.cons.Constant;

public class ChooseCoverActivity extends Activity {
    public static final String TAG = "PublishActivity";

    // 选择封面
    private static final int REQUEST_COVER_CODE = 10100;

    private MediaMetadataRetriever mediaMetadataRetriever;
    private ImageView mCoverBack;
    private TextView mCoverComplete;
    private ImageView mCoverImage;
    private AppCompatSeekBar mCoverSeekBar;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.choose_cover_activity);
        //must set
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        mLocalPath = getIntent().getExtras().getString(Constant.COMPOSE_PATH);
        mediaMetadataRetriever = new MediaMetadataRetriever();
        mediaMetadataRetriever.setDataSource(mLocalPath);
        mPreviewLength = getIntent().getExtras().getLong(Constant.PREVIEW_LEN);
        mButtonObserver = new ButtonObserver();
        startCoverSeek();
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

//                    mBitmap = mImageSeekTools.getVideoThumbnailAtTime(mLocalPath, mSeekTime,
//                            0, 0, true);
                    mBitmap = mediaMetadataRetriever.getFrameAtTime(mSeekTime);
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
//        mBitmap = mImageSeekTools.getVideoThumbnailAtTime(mLocalPath, mSeekTime, 0, 0, true);

        mBitmap = mediaMetadataRetriever.getFrameAtTime(mSeekTime);
        mImageSeekTools.probeMediaInfo(mLocalPath, new ProbeMediaInfoTools.ProbeMediaInfoListener() {
            @Override
            public void probeMediaInfoFinished(ProbeMediaInfoTools.MediaInfo info) {
                //使用合成视频时长更新视频的时长
                mPreviewLength = info.duration;
            }
        });

        mCoverImage.setImageBitmap(mBitmap);
        // 取出视频中五个Bitmap
        // No.1
        ImageView imageView1 = findViewById(R.id.preview_img_no1);
        long timeL = mPreviewLength / 4;
        // 预览画面显示视频第一帧
        imageView1.setImageBitmap(mediaMetadataRetriever.getFrameAtTime());
        // No.2
        ImageView imageView2 = findViewById(R.id.preview_img_no2);
        imageView2.setImageBitmap(mediaMetadataRetriever.getFrameAtTime(timeL));
        // No.3
        ImageView imageView3 = findViewById(R.id.preview_img_no3);
        imageView3.setImageBitmap(mediaMetadataRetriever.getFrameAtTime(timeL * 2));
        // No.4
        ImageView imageView4 = findViewById(R.id.preview_img_no4);
        imageView4.setImageBitmap(mediaMetadataRetriever.getFrameAtTime(timeL * 3));
        // No.5
        ImageView imageView5 = findViewById(R.id.preview_img_no5);
        imageView5.setImageBitmap(mediaMetadataRetriever.getFrameAtTime(timeL * 4));

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
                    setCoverResult();
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
        setResult(REQUEST_COVER_CODE, intent);
        finish();
    }

}

package fujisoft.videooverlaysample;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.github.hiteshsondhi88.libffmpeg.ExecuteBinaryResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.FFmpeg;
import com.github.hiteshsondhi88.libffmpeg.LoadBinaryResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegCommandAlreadyRunningException;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegNotSupportedException;
import com.nononsenseapps.filepicker.FilePickerActivity;
import com.nononsenseapps.filepicker.Utils;
import fujisoft.videooverlaysample.Utils.UriUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.select_logo_btn)
    Button selectLogoBtn;
    @BindView(R.id.video_select_btn)
    Button videoSelectBtn;
    @BindView(R.id.compose_btn)
    Button composeBtn;
    @BindView(R.id.logo_iv)
    ImageView logoIv;
    @BindView(R.id.left_top)
    Button leftTop;
    @BindView(R.id.center_top)
    Button centerTop;
    @BindView(R.id.right_top)
    Button rightTop;
    @BindView(R.id.left_center)
    Button leftCenter;
    @BindView(R.id.center_center)
    Button centerCenter;
    @BindView(R.id.right_center)
    Button rightCenter;
    @BindView(R.id.left_bottom)
    Button leftBottom;
    @BindView(R.id.center_bottom)
    Button centerBottom;
    @BindView(R.id.right_bottom)
    Button rightBottom;
    @BindView(R.id.transparency_sb)
    SeekBar transparencySb;
    @BindView(R.id.percent_tv)
    TextView percentTv;
    @BindView(R.id.video_vv)
    VideoView videoVv;
    @BindView(R.id.compose_pb)
    ProgressBar composePb;
    @BindView(R.id.image_iv)
    ImageView imageIv;

    private static final String TAG = "MainActivity";

    private static final int CHOOSE_DIRECTORY = 3;
    private static final int CHOOSE_IMAGE_FILE = 5;
    private static final int CHOOSE_VIDEO_FILE = 10;

    private String[] MULTI_PERMISSION = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE};

    private String logoPath;
    private String selectFilePath;
    private String outFilePath;

    String[] cmd = new String[8];
    String cmd5Opacity;
    String cmd5Overlay = "overlay=10:10";

    String selectFileName;
    String fileParentPath;

    boolean isVideoSelected;
    FFmpeg ffmpeg;

    int PERMISSION_REQUEST_CODE = 1;
    int progress;
    long old_duration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        leftTop.setSelected(true);
        // 権限チェック
        if (ActivityCompat.checkSelfPermission(this, MULTI_PERMISSION[0]) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, MULTI_PERMISSION[1]) != PackageManager.PERMISSION_GRANTED) {
            // 権限申請
            ActivityCompat.requestPermissions(this, MULTI_PERMISSION, PERMISSION_REQUEST_CODE);
        }

        transparencySb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float alphaVal = 1 - (float)progress / 100;
                Log.e(TAG, "onProgressChanged: alphaVal="+alphaVal );
                cmd5Opacity = "[1]lut=a=val* "+ alphaVal +"[a];[0][a]";
                Log.e(TAG, "onProgressChanged: cmd5Opacity="+cmd5Opacity );
                percentTv.setText(progress + "%");
                logoIv.setImageAlpha(100 - progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            // 権限を貰いました場合
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this,
                        "外部ストレージからの読み取りが可能です！", Toast.LENGTH_SHORT).show();
            } else {
                ActivityCompat.requestPermissions(this, MULTI_PERMISSION, PERMISSION_REQUEST_CODE);
            }
        }
    }

    private void addWaterMark(String originalPath, String imgPath, String outputPath) {
        //String cmd="-i "+ selectFilePath+" -vf movie="+alphaCacheImgPath+",scale=150:183[wm];[in][wm]overlay="+picX+":"+picY+"[out] -y -b 0.5M "+outFile;
        cmd[0] = "-i";
        cmd[1] = originalPath;
        cmd[2] = "-i";
        cmd[3] = imgPath;
        cmd[4] = "-filter_complex";
        cmd[5] = cmd5Opacity+cmd5Overlay;
        cmd[6] = "-y";
        cmd[7] = outputPath;
    }

    @OnClick({R.id.select_logo_btn, R.id.video_select_btn, R.id.compose_btn, R.id.left_top, R.id.center_top, R.id.right_top, R.id.left_center, R.id.center_center, R.id.right_center, R.id.left_bottom, R.id.center_bottom, R.id.right_bottom})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.select_logo_btn:
                chooseImageFile();
                break;
            case R.id.video_select_btn:
                chooseVideoFile();
                break;
            case R.id.compose_btn:
                chooseFileDirectory();
                break;
            case R.id.left_top:
                cmd5Overlay = "overlay=10:10";
                clearBtnStatus();
                leftTop.setSelected(true);
                break;
            case R.id.center_top:
                cmd5Overlay = "overlay=(main_w-overlay_w)/2:10";
                clearBtnStatus();
                centerTop.setSelected(true);
                break;
            case R.id.right_top:
                cmd5Overlay = "overlay=main_w-overlay_w-10:10";
                clearBtnStatus();
                rightTop.setSelected(true);
                break;
            case R.id.left_center:
                cmd5Overlay = "overlay=10:(main_h-overlay_h)/2";
                clearBtnStatus();
                leftCenter.setSelected(true);
                break;
            case R.id.center_center:
                cmd5Overlay = "overlay=(main_w-overlay_w)/2:(main_h-overlay_h)/2";
                clearBtnStatus();
                centerCenter.setSelected(true);
                break;
            case R.id.right_center:
                cmd5Overlay = "overlay=main_w-overlay_w-10:(main_h-overlay_h)/2";
                clearBtnStatus();
                rightCenter.setSelected(true);
                break;
            case R.id.left_bottom:
                cmd5Overlay = "overlay=10:main_h-overlay_h-10";
                clearBtnStatus();
                leftBottom.setSelected(true);
                break;
            case R.id.center_bottom:
                cmd5Overlay = "overlay=(main_w-overlay_w)/2:main_h-overlay_h-10";
                clearBtnStatus();
                centerBottom.setSelected(true);
                break;
            case R.id.right_bottom:
                cmd5Overlay = "overlay=main_w-overlay_w-10:main_h-overlay_h-10";
                clearBtnStatus();
                rightBottom.setSelected(true);
                break;
        }
    }

    /**
     * 选择Video文件
     */
    private void chooseVideoFile() {
        Intent intent = new Intent();
        intent.setType("video/*;image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, CHOOSE_VIDEO_FILE);
    }

    private void clearBtnStatus() {
        leftTop.setSelected(false);
        centerTop.setSelected(false);
        rightTop.setSelected(false);
        leftCenter.setSelected(false);
        centerCenter.setSelected(false);
        rightCenter.setSelected(false);
        leftBottom.setSelected(false);
        centerBottom.setSelected(false);
        rightBottom.setSelected(false);
    }

    /**
     * 选择Image文件
     */
    private void chooseImageFile() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, CHOOSE_IMAGE_FILE);
    }

    /**
     * 选择文件路径
     */
    private void chooseFileDirectory() {
        // This always works
        Intent i = new Intent(this, FilePickerActivity.class);
        // This works if you defined the intent filter
        // Intent i = new Intent(Intent.ACTION_GET_CONTENT);

        // Set these depending on your use case. These are the defaults.
        i.putExtra(FilePickerActivity.EXTRA_ALLOW_MULTIPLE, false);
        i.putExtra(FilePickerActivity.EXTRA_ALLOW_CREATE_DIR, false);
        i.putExtra(FilePickerActivity.EXTRA_MODE, FilePickerActivity.MODE_DIR);

        // Configure initial directory by specifying a String.
        // You could specify a String like "/storage/emulated/0/", but that can
        // dangerous. Always use Android's API calls to get paths to the SD-card or
        // internal memory.
        i.putExtra(FilePickerActivity.EXTRA_START_PATH, fileParentPath);

        startActivityForResult(i, CHOOSE_DIRECTORY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case CHOOSE_VIDEO_FILE:
                if (resultCode == RESULT_OK) {
                    selectFilePath = UriUtils.getPath(this, data.getData());
                    selectFileName = getFileName(selectFilePath);
                    Log.e(TAG, "onActivityResult: selectFileName=" + selectFileName);
                    Log.e(TAG, "onActivityResult: selectFilePath=" + selectFilePath);
                    String fileExtension = MimeTypeMap.getFileExtensionFromUrl(selectFilePath.toString());
                    String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtension);
                    Log.e(TAG, "onActivityResult: mimeType=" + mimeType);
                    if (mimeType != null && !"".equals(mimeType)) {
                        if (mimeType.contains("video")) {
                            isVideoSelected = true;
                            setVideoVV();
                        } else {
                            isVideoSelected = false;
                            setImgIV();
                        }
                    } else {
                        Toast.makeText(this, "動画・静止画を選択してください。", Toast.LENGTH_SHORT).show();
                    }
                    composeBtn.setEnabled(true);
                    break;
                }
            case CHOOSE_IMAGE_FILE:
                if (resultCode == RESULT_OK) {
                    logoPath = UriUtils.getPath(this, data.getData());
                    Bitmap logoBit = BitmapFactory.decodeFile(logoPath);
                    logoIv.setImageBitmap(logoBit);
                    Log.e(TAG, "onActivityResult: logoPath=" + logoPath);
                    break;
                }
            case CHOOSE_DIRECTORY:
                if (resultCode == RESULT_OK) {
                    List<Uri> files = Utils.getSelectedFilesFromResult(data);
                    for (Uri uri : files) {
                        File file = Utils.getFileForUri(uri);
                        outFilePath = file.getPath();
                        Log.e(TAG, "onActivityResult: outFilePath=" + outFilePath);
                        execVideo();
                    }
                    break;
                }
        }
    }

    /**
     * 设置选择视频
     */
    private void setVideoVV() {
        videoVv.setVisibility(View.VISIBLE);
        imageIv.setVisibility(View.INVISIBLE);
        videoVv.setMediaController(new MediaController(this));
        videoVv.setVideoPath(selectFilePath);
        videoVv.requestFocus();
        videoVv.seekTo(100);
        setVideoDuration();
    }

    /**
     * 设置选择图片
     */
    private void setImgIV() {
        videoVv.setVisibility(View.INVISIBLE);
        imageIv.setVisibility(View.VISIBLE);
        Bitmap bmp = BitmapFactory.decodeFile(selectFilePath);
        imageIv.setImageBitmap(bmp);
    }

    /**
     * 设置视频时长
     */
    private void setVideoDuration() {
        MediaMetadataRetriever retr = new MediaMetadataRetriever();
        retr.setDataSource(selectFilePath);
        old_duration = Long.parseLong(retr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION));
        Log.e(TAG, "onActivityResult: old_duration=" + old_duration);
    }

    /**
     * 获取文件名字
     */
    private String getFileName(String file_path) {
        File file = new File(file_path);
        fileParentPath = file.getParent();
        Log.e(TAG, "getFileName: fileParentPath=" + fileParentPath);
        return file.getName();
    }

    private void loadFFmpeg() {
        ffmpeg = FFmpeg.getInstance(this);
        try {
            ffmpeg.loadBinary(new LoadBinaryResponseHandler() {

                @Override
                public void onStart() {
                }

                @Override
                public void onFailure() {
                }

                @Override
                public void onSuccess() {
                    String outFile = null;
                    if (isVideoSelected) {
                        outFile = outFilePath + File.separator + selectFileName + "_out.mp4";
                    } else {
                        outFile = outFilePath + File.separator + selectFileName + "_out.jpg";
                    }
                    addWaterMark(selectFilePath, logoPath, outFile);
                    execCMD();
                }

                @Override
                public void onFinish() {
                }
            });
        } catch (FFmpegNotSupportedException e) {
            // Handle if FFmpeg is not supported by device
        }
    }

    /**
     * 开始合成
     */
    private void execVideo() {
        if (logoPath == null || "".equals(logoPath)) {
            Toast.makeText(this, "ロゴを選択してください。", Toast.LENGTH_SHORT).show();
            return;
        }
        if (selectFilePath == null || "".equals(selectFilePath)) {
            Toast.makeText(this, "動画・静止画を選択してください。", Toast.LENGTH_SHORT).show();
            return;
        }
//        saveImgToDisk(selectImgName, getTransparentBitmap(logoBit, alphaVal));
        loadFFmpeg();
    }

    private void getExecProgress(String msg) {
        //message=frame=  195 fps=4.2 q=29.0 size=    1380kB time=00:00:07.24 bitrate=1560.1kbits/s dup=16 drop=13 speed=0.156x
        int posTime = msg.indexOf("time=");
        if (posTime > 0) {
            String time = msg.substring(posTime, msg
                    .indexOf("bitrate") - 1);
            time = time.substring(time.indexOf("=") + 1, time.length());// 截取当前所用时间字符串
            Log.e(TAG, "onProgress: time=" + time);
            int h = Integer.parseInt(time.substring(0, 2));// 封装成小时
            int m = Integer.parseInt(time.substring(3, 5));// 封装成分钟
            int s = Integer.parseInt(time.substring(6, 8));// 封装成秒
            int ms = Integer.parseInt(time.substring(9, 11));// 封装成毫秒
            long hasTime = (h * 3600 + m * 60 + s) * 1000 + ms * 10;// 得到总共的时间秒数
            float t = (float) hasTime / (float) old_duration;// 计算所用时间与总共需要时间的比例
            progress = (int) Math.ceil(t * 100);
        }
    }

    private void execCMD() {
        try {
            // to execute "ffmpeg -version" command you just need to pass "-version"
            ffmpeg.execute(cmd, new ExecuteBinaryResponseHandler() {

                @Override
                public void onStart() {
                }

                @Override
                public void onProgress(String message) {
                    getExecProgress(message);
                    composePb.setProgress(progress);
                }

                @Override
                public void onFailure(String message) {
                    Toast.makeText(MainActivity.this, "onFailure:", Toast.LENGTH_SHORT).show();

                    Log.e(TAG, "execVideo: onFailure");
                }

                @Override
                public void onSuccess(String message) {
                    Toast.makeText(MainActivity.this, "onSuccess:", Toast.LENGTH_SHORT).show();
                    composePb.setProgress(100);
                    Log.e(TAG, "execVideo: onSuccess");
                }

                @Override
                public void onFinish() {
                    Toast.makeText(MainActivity.this, "onFinish", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (FFmpegCommandAlreadyRunningException e) {
            // Handle if FFmpeg is already running
            Toast.makeText(MainActivity.this, "FFmpegCommandAlreadyRunningException=" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}

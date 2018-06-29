package fujisoft.videooverlaysample;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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

/**
 * 動画・静止画にロゴを挿入する
 */
public class MainActivity extends AppCompatActivity {
    // 重ねる画像選択ボタン
    @BindView(R.id.select_logo_btn)
    Button selectLogoBtn;
    // 動画選択ボタン
    @BindView(R.id.video_select_btn)
    Button videoSelectBtn;
    // 合成実行ボタン
    @BindView(R.id.compose_btn)
    Button composeBtn;
    // ロゴイメージビュー
    @BindView(R.id.logo_iv)
    ImageView logoIv;
    // 重ねる位置：左上
    @BindView(R.id.left_top)
    Button leftTop;
    // 重ねる位置：中央上
    @BindView(R.id.center_top)
    Button centerTop;
    // 重ねる位置：右上
    @BindView(R.id.right_top)
    Button rightTop;
    // 重ねる位置：左中央
    @BindView(R.id.left_center)
    Button leftCenter;
    // 重ねる位置：上下中央
    @BindView(R.id.center_center)
    Button centerCenter;
    // 重ねる位置：右中央
    @BindView(R.id.right_center)
    Button rightCenter;
    // 重ねる位置：左下
    @BindView(R.id.left_bottom)
    Button leftBottom;
    // 重ねる位置：中央下
    @BindView(R.id.center_bottom)
    Button centerBottom;
    // 重ねる位置：右下
    @BindView(R.id.right_bottom)
    Button rightBottom;
    // 透明度バー
    @BindView(R.id.transparency_sb)
    SeekBar transparencySb;
    // 透明度パーセント
    @BindView(R.id.percent_tv)
    TextView percentTv;
    // 合成実行プログレスバー
    @BindView(R.id.compose_pb)
    ProgressBar composePb;
    // 静止画選択ビュー
    @BindView(R.id.image_iv)
    ImageView imageIv;
    // タグ
    private static final String TAG = "MainActivity";
    // ディレクトリを選択
    private static final int CHOOSE_DIRECTORY = 3;
    // ロゴファイルを選択
    private static final int CHOOSE_LOGO_FILE = 5;
    // 動画・静止画を選択
    private static final int CHOOSE_VIDEO_FILE = 10;
    // 必要な権限
    private String[] MULTI_PERMISSION = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE};
    // ロゴパス
    private String logoPath;
    // 選択したファイルパス
    private String selectFilePath;
    // アウトファイルパス
    private String outFilePath;
    // ffmpegコマンド
    String[] cmd = new String[10];
    // ffmpeg透明コマンド
    String cmd5Opacity = "";
    // ffmpegオーバーレイコマンド
    String cmd5Overlay = "overlay=10:10";
    // 選択したファイル名
    String selectFileName;
    // 選択したファイル拡張子
    String selectSuffixName;
    // 親ファイルのパス
    String fileParentPath;
    // 選択したファイルフラグ　true：動画；false：静止画
    boolean isVideoSelected;
    // ffmpeg
    FFmpeg ffmpeg;
    // 権限要求コード
    int PERMISSION_REQUEST_CODE = 1;
    // 合成実行プログレス
    int progress;
    // 動画持続時間
    long old_duration;

    /**
     * 画面初期化
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // レイアウトを設定
        setContentView(R.layout.activity_main);
        // レイアウトをバインド
        ButterKnife.bind(this);
        // ロゴ初期位置を指定
        leftTop.setSelected(true);
        // 権限チェック
        if (ActivityCompat.checkSelfPermission(this
                , MULTI_PERMISSION[0]) != PackageManager.PERMISSION_GRANTED) {
            // 権限申請
            ActivityCompat.requestPermissions(
                    this
                    , MULTI_PERMISSION
                    , PERMISSION_REQUEST_CODE);
        }
        // 透明度バー変更リスナーを設定
        transparencySb.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
            /**
             * プログレス変更時呼び出す
             * @param seekBar
             * @param progress
             * @param fromUser
             */
            @Override
            public void onProgressChanged(
                    SeekBar seekBar
                    , int progress
                    , boolean fromUser) {
                float alphaVal = 1 - (float) progress / 100;
                cmd5Opacity = "[1]lut=a=val* " + alphaVal + "[a];[0][a]";
                // 透明度パーセントテキストを設定
                percentTv.setText(progress + "%");
                // イメージ透明度を設定
                logoIv.setImageAlpha((int) (255 - progress*2.55));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    /**
     * 権限申請結果の処理
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(
            int requestCode
            , @NonNull String[] permissions
            , @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            // 権限を貰いました場合
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this
                        ,"外部ストレージからの読み取りが可能です！"
                        , Toast.LENGTH_SHORT).show();
            } else {
                ActivityCompat.requestPermissions(
                        this
                        , MULTI_PERMISSION
                        , PERMISSION_REQUEST_CODE);
            }
        }
    }

    /**
     * ロゴを挿入するコマンド
     *
     * @param originalPath
     * @param imgPath
     * @param outputPath
     */
    private void addWaterMark(
            String originalPath
            , String imgPath
            , String outputPath) {
        cmd[0] = "-i";
        // 選択した動画・静止画パス
        cmd[1] = originalPath;
        cmd[2] = "-i";
        // 挿入したいロゴパス
        cmd[3] = imgPath;
        cmd[4] = "-filter_complex";
        cmd[5] = cmd5Opacity + cmd5Overlay;
        cmd[6] = "-y";
        cmd[7] = "-preset";
        cmd[8] = "ultrafast";
        // 挿入完了動画・静止画パス
        cmd[9] = outputPath;
    }

    /**
     * ボタンのクリックイベント
     *
     * @param view
     */
    @OnClick({R.id.select_logo_btn
            , R.id.video_select_btn
            , R.id.compose_btn
            , R.id.left_top
            , R.id.center_top
            , R.id.right_top
            , R.id.left_center
            , R.id.center_center
            , R.id.right_center
            , R.id.left_bottom
            , R.id.center_bottom
            , R.id.right_bottom})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            // 「重ねる画像選択」ボタン
            case R.id.select_logo_btn:
                chooseImageFile();
                break;
            // 「動画選択」ボタン
            case R.id.video_select_btn:
                chooseVideoFile();
                break;
            // 「合成実行」ボタン
            case R.id.compose_btn:
                // 合成を行う
                execVideo();
                break;
            // 左上ボタン
            case R.id.left_top:
                cmd5Overlay = "overlay=10:10";
                clearBtnStatus();
                leftTop.setSelected(true);
                break;
            // 中央上ボタン
            case R.id.center_top:
                cmd5Overlay = "overlay=(main_w-overlay_w)/2:10";
                clearBtnStatus();
                centerTop.setSelected(true);
                break;
            // 右上ボタン
            case R.id.right_top:
                cmd5Overlay = "overlay=main_w-overlay_w-10:10";
                clearBtnStatus();
                rightTop.setSelected(true);
                break;
            // 左中央ボタン
            case R.id.left_center:
                cmd5Overlay = "overlay=10:(main_h-overlay_h)/2";
                clearBtnStatus();
                leftCenter.setSelected(true);
                break;
            // 上下中央ボタン
            case R.id.center_center:
                cmd5Overlay = "overlay=(main_w-overlay_w)/2:(main_h-overlay_h)/2";
                clearBtnStatus();
                centerCenter.setSelected(true);
                break;
            // 右中央上ボタン
            case R.id.right_center:
                cmd5Overlay = "overlay=main_w-overlay_w-10:(main_h-overlay_h)/2";
                clearBtnStatus();
                rightCenter.setSelected(true);
                break;
            // 左下ボタン
            case R.id.left_bottom:
                cmd5Overlay = "overlay=10:main_h-overlay_h-10";
                clearBtnStatus();
                leftBottom.setSelected(true);
                break;
            // 中央下ボタン
            case R.id.center_bottom:
                cmd5Overlay = "overlay=(main_w-overlay_w)/2:main_h-overlay_h-10";
                clearBtnStatus();
                centerBottom.setSelected(true);
                break;
            // 右下ボタン
            case R.id.right_bottom:
                cmd5Overlay = "overlay=main_w-overlay_w-10:main_h-overlay_h-10";
                clearBtnStatus();
                rightBottom.setSelected(true);
                break;
        }
    }

    /**
     * 動画・静止画ファイルの選択
     */
    private void chooseVideoFile() {
        Intent intent = new Intent();
        intent.setType("video/*;image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, CHOOSE_VIDEO_FILE);
    }

    /**
     * ロゴ挿入位置ボタンの選択した状態をクリア
     */
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
     * ロゴファイルの選択
     */
    private void chooseImageFile() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, CHOOSE_LOGO_FILE);
    }

    /**
     * ファイルディレクトリの選択
     */
    private void chooseFileDirectory() {
        Intent i = new Intent(this, FilePickerActivity.class);
        // 選択ファイルタイプを設定
        i.putExtra(FilePickerActivity.EXTRA_MODE, FilePickerActivity.MODE_DIR);
        // 初期選択パスを設定
        i.putExtra(FilePickerActivity.EXTRA_START_PATH, fileParentPath);
        startActivityForResult(i, CHOOSE_DIRECTORY);
    }

    /**
     * 選択したファイルの処理
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CHOOSE_VIDEO_FILE:
                    // 選択したファイルパスを取得
                    selectFilePath = UriUtils
                            .getPath(this, data.getData());
                    // 選択したファイル名を取得
                    selectFileName = getFileName(selectFilePath);
                    // 選択したファイル拡張子を取得
                    selectSuffixName = getSuffixName(selectFileName);
                    String fileExtension = MimeTypeMap
                            .getFileExtensionFromUrl(selectFilePath.toString());
                    // 選択したファイルmimeタイプを取得
                    String mimeType = MimeTypeMap.getSingleton()
                            .getMimeTypeFromExtension(fileExtension);
                    if (mimeType != null && !"".equals(mimeType)) {
                        // 選択したファイル動画の場合
                        if (mimeType.contains("video")) {
                            isVideoSelected = true;
                            setVideoVV();
                            // 選択したファイル静止画の場合
                        } else {
                            isVideoSelected = false;
                            setImgIV();
                        }
                    } else {
                        Toast.makeText(this
                                , "動画・静止画を選択してください。"
                                , Toast.LENGTH_SHORT).show();
                    }
                    // 合成実行ボタン使用可能
                    composeBtn.setEnabled(true);
                    break;

                case CHOOSE_LOGO_FILE:
                    // 選択したロゴパスを取得
                    logoPath = UriUtils.getPath(this, data.getData());
                    // 選択したロゴBitmapを取得
                    Bitmap logoBit = BitmapFactory.decodeFile(logoPath);
                    logoIv.setImageBitmap(logoBit);
                    break;

                case CHOOSE_DIRECTORY:
                    // 選択したファイルUriリストを取得
                    List<Uri> files = Utils.getSelectedFilesFromResult(data);
                    for (Uri uri : files) {
                        File file = Utils.getFileForUri(uri);
                        // 選択したディレクトリパスを取得
                        outFilePath = file.getPath();
                        // FFmpegをロード
                        loadFFmpeg();
                    }
                    break;
            }
        }
    }

    /**
     * ファイルの拡張子名の取得
     * @param name
     * @return
     */
    private String getSuffixName(String name) {
        // "."を含む拡張子名
        String suffixName = name.substring(name.lastIndexOf("."));
        if (".3gp".equals(suffixName)) {
            suffixName = ".mp4";
        }
        return suffixName;
    }

    /**
     * 選択した動画の設定
     */
    private void setVideoVV() {
        // サムネイルを取得
        Bitmap bmp = ThumbnailUtils.createVideoThumbnail(selectFilePath
                , MediaStore.Video.Thumbnails.MINI_KIND);
        imageIv.setImageBitmap(bmp);
        // 動画持続時間を取得
        setVideoDuration();
    }

    /**
     * 選択した静止画の設定
     */
    private void setImgIV() {
        // 静止画Bitmapを取得
        Bitmap bmp = BitmapFactory.decodeFile(selectFilePath);
        imageIv.setImageBitmap(bmp);
    }

    /**
     * 動画持続時間の取得
     */
    private void setVideoDuration() {
        MediaMetadataRetriever retr = new MediaMetadataRetriever();
        retr.setDataSource(selectFilePath);
        old_duration = Long.parseLong(retr
                .extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION));
    }

    /**
     * ファイル名の取得
     * @param file_path
     * @return
     */
    private String getFileName(String file_path) {
        File file = new File(file_path);
        // 親ファイルパスを取得
        fileParentPath = file.getParent();
        return file.getName();
    }

    /**
     * FFmpegのロード
     */
    private void loadFFmpeg() {
        // FFmpegインスタンスを取得
        ffmpeg = FFmpeg.getInstance(this);
        try {
            // 2進法の形式でロードする
            ffmpeg.loadBinary(new LoadBinaryResponseHandler() {

                @Override
                public void onStart() {
                }

                @Override
                public void onFailure() {
                }

                /**
                 * ロード成功時呼び出す
                 */
                @Override
                public void onSuccess() {
                    // 出力ファイルパスの設定
                    String outFile = outFilePath
                            + File.separator
                            + selectFileName
                            + "_out"
                            + selectSuffixName;
                    // ロゴを挿入するコマンド
                    addWaterMark(selectFilePath, logoPath, outFile);
                    // コマンドを実行
                    execCMD();
                }

                @Override
                public void onFinish() {
                }
            });
        } catch (FFmpegNotSupportedException e) {
            // この設備はFFmpeg支持しない
            Toast.makeText(MainActivity.this
                    , "この設備はFFmpegをサポートしていません。"
                    , Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * ロゴ挿入を実行
     */
    private void execVideo() {
        if (logoPath == null || "".equals(logoPath)) {
            Toast.makeText(this
                    , "ロゴを選択してください。"
                    , Toast.LENGTH_SHORT).show();
            return;
        }
        if (selectFilePath == null || "".equals(selectFilePath)) {
            Toast.makeText(this
                    , "動画・静止画を選択してください。"
                    , Toast.LENGTH_SHORT).show();
            return;
        }
        chooseFileDirectory();
    }

    /**
     * 実行プログレスの取得
     * @param msg
     */
    private void getExecProgress(String msg) {
        int posTime = msg.indexOf("time=");
        if (posTime > 0) {
            String time = msg.substring(posTime, msg
                    .indexOf("bitrate") - 1);
            // 現在の使用時間の文字列を切り取る
            time = time.substring(time.indexOf("=") + 1, time.length());
            // 時間を設定する
            int h = Integer.parseInt(time.substring(0, 2));
            // 分を設定する
            int m = Integer.parseInt(time.substring(3, 5));
            // 秒を設定する
            int s = Integer.parseInt(time.substring(6, 8));
            // ミリ秒を設定する
            int ms = Integer.parseInt(time.substring(9, 11));
            // 合計時間のミリ秒数を得る
            long hasTime = (h * 3600 + m * 60 + s) * 1000 + ms * 10;
            // 所用時間と全部時間を必要とする割合を計算する
            float t = (float) hasTime / (float) old_duration;
            progress = (int) Math.ceil(t * 100);
        }
    }

    /**
     * FFmpegコマンドの実行
     */
    private void execCMD() {
        try {
            ffmpeg.execute(cmd, new ExecuteBinaryResponseHandler() {

                @Override
                public void onStart() {
                    changeStatus(false);
                }

                /**
                 * 実行中呼び出す
                 * @param message
                 */
                @Override
                public void onProgress(String message) {
                    getExecProgress(message);
                    composePb.setProgress(progress);
                    Log.e(TAG, "onProgress: message=" + message);
                }
                /**
                 * 実行失敗時呼び出す
                 * @param message
                 */
                @Override
                public void onFailure(String message) {
                    Toast.makeText(MainActivity.this
                            , "動画合成に失敗しました。"
                            , Toast.LENGTH_SHORT).show();
                }
                /**
                 * 実行成功時呼び出す
                 * @param message
                 */
                @Override
                public void onSuccess(String message) {
                    Toast.makeText(MainActivity.this
                            , "動画合成に成功しました。"
                            , Toast.LENGTH_SHORT).show();
                    composePb.setProgress(100);
                }

                @Override
                public void onFinish() {
                    changeStatus(true);
                    composePb.setProgress(0);
                }
            });
        } catch (FFmpegCommandAlreadyRunningException e) {
            // FFmpegCommand実行中
            Toast.makeText(MainActivity.this
                    , "FFmpegCommandが実行中のため、再度実行できません。"
                    , Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * コントロールの状態の変更
     * @param flg 合成実行中：false;合成実行しない：true
     */
    private void changeStatus(boolean flg){
        selectLogoBtn.setEnabled(flg);
        videoSelectBtn.setEnabled(flg);
        transparencySb.setEnabled(flg);
        leftTop.setEnabled(flg);
        leftCenter.setEnabled(flg);
        rightTop.setEnabled(flg);
        centerTop.setEnabled(flg);
        centerCenter.setEnabled(flg);
        rightCenter.setEnabled(flg);
        leftBottom.setEnabled(flg);
        centerBottom.setEnabled(flg);
        rightBottom.setEnabled(flg);
        composeBtn.setEnabled(flg);
    }
}

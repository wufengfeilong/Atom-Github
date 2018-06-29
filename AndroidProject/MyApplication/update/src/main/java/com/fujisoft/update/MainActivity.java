package com.fujisoft.update;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * メイン画面
 *
 * @author 860115025
 * 2017/09/20
 */
public class MainActivity extends BaseActivity {
    // GPS必要な権限配列
    private final static String[] MULTI_PERMISSION = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};
    // アップロード先URL
    private TextView sendUrl;
    // アップロードリストビュー
    private ListView listView;
    // アドレススピナー
    private Spinner ipSpinner;
    // アップロード先フォルダスピナー
    private Spinner folderSpinner;
    // アップロード中ダイアログ
    private ProgressDialog dialog;

    // 選択したファイル
    List<File> files;
    // 選択したファイルアダプタ
    MyAdapter adapter;
    // 選択したファイル情報
    Map<String, Object> map;
    // デフォルトフォルダ
    String folder = "folder1";
    // 選択したファイル情報リスト
    private List<Map<String, Object>> fileInfos;

    /**
     * 画面初期表示
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 権限チェック
        if (ActivityCompat.checkSelfPermission(this, MULTI_PERMISSION[0]) != PackageManager.PERMISSION_GRANTED) {
            // 権限申請
            ActivityCompat.requestPermissions(this, MULTI_PERMISSION, Constant.PERMISSION_REQUEST_CODE);
        }
    }

    /**
     * ビュー設定
     */
    @Override
    public void setView() {
        setContentView(R.layout.activity_main);
    }

    /**
     * モジュール取得
     */
    @Override
    public void findViews() {
        ipSpinner = findViewById(R.id.ipSpinner);
        folderSpinner = findViewById(R.id.folderSpinner);
        sendUrl = findViewById(R.id.sendUrl);
        listView = findViewById(R.id.file_view);
    }

    /**
     * モジュール初期化
     */
    @Override
    public void initViews() {
        files = new ArrayList<>();
        fileInfos = new ArrayList<>();
        // 新規設定アダプタ
        adapter = new MyAdapter(this, fileInfos);
        listView.setAdapter(adapter);
    }

    /**
     * リスナーを追加
     */
    @Override
    public void addListeners() {
        // アドレススピナーリスナー
        ipSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
                String[] ips = getResources().getStringArray(R.array.ip);
                sendUrl.setText("https://" + ips[pos] + ":8080/upload/");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        // アップロード先フォルダスピナーリスナー
        folderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
                String[] folders = getResources().getStringArray(R.array.directory);
                folder = folders[pos];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        // アダプタリスナー
        adapter.setOnItemDeleteClickListener(new MyAdapter.onItemDeleteListener() {
            @Override
            public void onDeleteClick(int i) {
                fileInfos.remove(i);
                files.remove(i);
                adapter.notifyDataSetChanged();
            }
        });
    }

    /**
     * 動画を選択
     * @param v
     */
    public void selectVideos(View v) {
        // 動画呼び出し
        Intent intent = new Intent();
        // タイプ：動画
        intent.setType("video/*");
        // 複数選択可
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        // Intent.ACTION_GET_CONTENTを使用
        intent.setAction(Intent.ACTION_GET_CONTENT);
        // 動画選択完了本画面に戻る
        startActivityForResult(Intent.createChooser(intent, "動画選択"), Constant.VIDEO_REQUEST_CODE);
    }

    /**
     * 動画をアップロード
     * @param v
     */
    public void fileUpload(View v) {
        // アップロードする動画ない場合
        if (files.size() == 0) {
            Toast.makeText(this, "アップロードしたい動画を選択してください。", Toast.LENGTH_SHORT).show();
            return;
        }
        // アップロードを行う
        OkGo.post(sendUrl.getText().toString())
                .tag(this)
                // アップロード先フォルダ
                .params("directory", folder)
                // 選択した動画ファイル
                .addFileParams("file", files)
                .execute(new UploadCallBack() {
                    /**
                     * アップロード開始
                     * @param request
                     */
                    @Override
                    public void onStart(Request request) {
                        // ダイアログ表示
                        initProgressDialog();
                    }

                    @Override
                    public void onError(Response response) {
                        // ダイアログ閉じる
                        dialog.dismiss();
                        // 成功トースト
                        Toast.makeText(getApplicationContext(), "リモートサーバーが開いていない。", Toast.LENGTH_SHORT).show();
                    }

                    /**
                     * アップロード成功
                     * @param response
                     */
                    @Override
                    public void onSuccess(Response response) {
                        // ダイアログ閉じる
                        dialog.dismiss();
                        // 成功トースト
                        Toast.makeText(getApplicationContext(), "アップロードに成功しました。", Toast.LENGTH_SHORT).show();
                        files.clear();
                        fileInfos.clear();
                        adapter.notifyDataSetChanged();
                    }

                    /**
                     * アップロード進度
                     * @param progress
                     */
                    @Override
                    public void uploadProgress(Progress progress) {
                        // アップロード進度設定
                        dialog.setProgress((int) (progress.currentSize * 100 / progress.totalSize));
                    }
                });
    }

    /**
     * アップロードキャンセル
     * @param v
     */
    public void uploadCancel(View v) {
        files.clear();
        fileInfos.clear();
        adapter.notifyDataSetChanged();
    }

    /**
     * 選択した動画の処理
     * @param requestCode
     * @param resultCode
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.VIDEO_REQUEST_CODE && resultCode == RESULT_OK && null != data) {

            // 単一選択
            if (data.getData() != null) {
                Uri uri = data.getData();
                setData(uri);
            } else {
                // 複数選択
                int count = data.getClipData().getItemCount();
                for (int i = 0; i < count; i++) {
                    Uri uri = data.getClipData().getItemAt(i).getUri();
                    setData(uri);
                }
            }
            adapter.notifyDataSetChanged();
        }
    }

    /**
     * 使用権限を申請する
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == Constant.PERMISSION_REQUEST_CODE) {
            // 権限を貰いました場合
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this,
                        "外部ストレージからの読み取りが可能です！", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /**
     * 画面を廃棄する
     */
    @Override
    protected void onDestroy() {
        OkGo.getInstance().cancelTag(this);
        super.onDestroy();
    }

    /**
     * ダイアログ初期化
     */
    private void initProgressDialog() {
        // ダイアログ新規する
        dialog = new ProgressDialog(this);
        // 水平タイプダイアログ
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        // 戻るボタンタップ時、ダイアログキャンセル
        dialog.setCancelable(true);
        // ダイアログ外タップ時、ダイアログキャンセルしない
        dialog.setCanceledOnTouchOutside(false);
        // イアログのタイトル
        dialog.setTitle("ヒント");
        // 進度MAX値
        dialog.setMax(100);
        dialog.setMessage("動画アップロード中...");
        dialog.setProgressNumberFormat("");
        // ダイアログ表示する
        dialog.show();
    }

    /**
     * データ設定
     * @param uri
     */
    private void setData(Uri uri) {
        // uri --> path
        String path = UriUtils.getPath(this, uri);
        // ファイルを取得する
        File file = new File(path);
        files.add(file);
        map = new HashMap<>();
        // ファイル名
        map.put("name", file.getName());
        // ファイルサイズ（MB）
        map.put("size", (float) ((int) file.length() * 100 / 1024 / 1024) / 100 + "MB");
        fileInfos.add(map);
    }
}

package com.fujisoft.storage.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import com.fujisoft.storage.R;

/**
 * メイン画面
 */
public class MainActivity extends AppCompatActivity {

    // 必要な権限配列
    private final static String[] MULTI_PERMISSION =
            new String[]{Manifest.permission.CAMERA};

    /**
     * 画面初期表示
     * @param savedInstanceState 初期化引数
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 権限チェック
        if (ActivityCompat.checkSelfPermission(this,
                MULTI_PERMISSION[0]) != PackageManager.PERMISSION_GRANTED) {
            // 権限申請
            ActivityCompat.requestPermissions(this, MULTI_PERMISSION,
                    0x02);
        } else {
            toBtmNav();
        }
    }

    /**
     * 入庫画面に遷移する
     */
    public void toBtmNav() {
        startActivity(new Intent(this, BtmNavActivity.class));
        // 本画面が終わる
        finish();
    }

    /**
     * 使用権限を申請する
     * @param requestCode コード
     * @param permissions 権限
     * @param grantResults 権限受与
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == 0x02) {
            // 権限を貰いました場合
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this,
                        "カメラ機能が使える！",
                        Toast.LENGTH_SHORT).show();
                toBtmNav();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions,
                grantResults);
    }
}

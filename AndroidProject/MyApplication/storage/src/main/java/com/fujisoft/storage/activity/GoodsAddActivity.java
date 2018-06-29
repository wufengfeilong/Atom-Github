package com.fujisoft.storage.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.fujisoft.storage.Constant;
import com.fujisoft.storage.R;
import com.fujisoft.storage.Utils;
import com.google.zxing.activity.CaptureActivity;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

/**
 * 物品追加画面
 */
public class GoodsAddActivity extends AppCompatActivity {
    // デフォルトの請求URL
    private String URL = Constant.BASE_URL + "saveGoods";
    // コードテキスト
    private EditText code;
    // 品名テキスト
    private EditText name;
    // 説明テキスト
    private EditText desc;
    // シェアードプリファレンス
    SharedPreferences sp;

    /**
     * 画面初期表示
     * @param savedInstanceState 初期化引数
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // レイアウト設定
        setContentView(R.layout.activity_goods_add);
        // シェアードプリファレンス取得
        sp = getSharedPreferences("storage", 0);
        // モジュール取得
        code = (EditText) findViewById(R.id.add_code);
        name = (EditText) findViewById(R.id.add_name);
        desc = (EditText) findViewById(R.id.add_desc);
    }

    /**
     * 物品スキャン画面に遷移する
     * @param v ビュー
     */
    public void toScan(View v) {
        Intent intent = new Intent(this, CaptureActivity.class);
        startActivityForResult(intent, 0x01);
    }

    /**
     * 物品保存
     * @param v ビュー
     */
    public void addGoods(View v) {
        // コード空判断処理
        if (TextUtils.isEmpty(code.getText())) {
            Toast.makeText(this,
                    "コードは必ず入力してください。",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        // 品名空判断処理
        if (TextUtils.isEmpty(name.getText())) {
            Toast.makeText(this,
                    "品名は必ず入力してください。",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        // URL設定
        String ip = sp.getString("ip", null);
        String request_url = null;
        if (ip == null) {
            request_url = URL;
        } else {
            request_url = "https://" + ip + ":5003/Storage/saveGoods";
        }
        // 物品情報サーバに送信
        OkGo.<String>post(request_url)
                .tag(this)
                .params("code", code.getText().toString())
                .params("name", name.getText().toString())
                .params("desc", desc.getText().toString())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Toast.makeText(GoodsAddActivity.this,
                                response.body(), Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
    }

    /**
     * 物品スキャンした後処理
     * @param requestCode 請求コード
     * @param resultCode 結果コード
     * @param data データ
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // スキャン結果コールバック
        if (resultCode == Constant.RESULT_OK) {
            Bundle bundle = data.getExtras();
            String scanResult = bundle.getString("qr_scan_result");
            // スキャン結果の処理
            if (!scanResult.equals("") && scanResult != null) {
                if (Utils.regValid(scanResult)) {
                    code.setText(scanResult);
                } else {
                    Toast.makeText(this,
                            "バーコードをスキャンできない。",
                            Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "再度スキャンしてください。",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 画面を廃棄する
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
    }
}
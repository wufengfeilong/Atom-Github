package com.fujisoft.storage.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.fujisoft.storage.Constant;
import com.fujisoft.storage.R;
import com.fujisoft.storage.Utils;
import com.fujisoft.storage.bean.Goods;
import com.fujisoft.storage.fragment.OutHouseFragment;
import com.fujisoft.storage.fragment.SettingFragment;
import com.fujisoft.storage.fragment.InHouseFragment;
import com.fujisoft.storage.fragment.SearchFragment;
import com.google.zxing.activity.CaptureActivity;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.model.Response;
import org.json.JSONObject;

/**
 * 共通フッタ部
 */
public class BtmNavActivity extends AppCompatActivity {
    // デフォルトの請求URL
    private String URL = Constant.BASE_URL + "getGoodesByCode";
    // シェアードプリファレンス
    private SharedPreferences sp;
    // コード
    private String code;
    // ナビゲーションバー
    private BottomNavigationBar navigation;
    // 入庫フラグメント
    private InHouseFragment inHouseFragment;
    // 出庫フラグメント
    private OutHouseFragment outHouseFragment;
    // 検索フラグメント
    private SearchFragment searchFragment;
    // 設定フラグメント
    private SettingFragment settingFragment;
    // ナビゲーションバーリスナー
    private BottomNavigationBar.OnTabSelectedListener mOnNavigationTabSelected
            = new BottomNavigationBar.OnTabSelectedListener() {
        /**
         * フラグメントロード
         * @param i 画面インデックス
         */
        @Override
        public void onTabSelected(int i) {
            FragmentManager fm = BtmNavActivity.this.getFragmentManager();
            // 事務を開く
            FragmentTransaction transaction = fm.beginTransaction();
            hideAllFragment(transaction);
            switch (i) {
                case 0:
                    // 入庫フラグメント表示
                    if (inHouseFragment == null) {
                        inHouseFragment = InHouseFragment.newInstance();
                        transaction.add(R.id.content, inHouseFragment);
                    } else {
                        transaction.show(inHouseFragment);
                    }
                    break;
                case 1:
                    // 出庫フラグメント表示
                    if (outHouseFragment == null) {
                        outHouseFragment = OutHouseFragment.newInstance();
                        transaction.add(R.id.content, outHouseFragment);
                    } else {
                        transaction.show(outHouseFragment);
                    }
                    break;
                case 2:
                    // 検索フラグメント表示
                    if (searchFragment == null) {
                        searchFragment = SearchFragment.newInstance();
                        transaction.add(R.id.content, searchFragment);
                    } else {
                        transaction.show(searchFragment);
                    }
                    break;
                case 3:
                    // 設定フラグメント表示
                    if (settingFragment == null) {
                        settingFragment = SettingFragment.newInstance();
                        transaction.add(R.id.content, settingFragment);
                    } else {
                        transaction.show(settingFragment);
                    }
                    break;
                default:
                    break;
            }
            // 事務提出
            transaction.commit();
        }

        @Override
        public void onTabUnselected(int i) {
        }
        @Override
        public void onTabReselected(int i) {
        }
    };

    /**
     * 全部フラグメント非表示
     * @param transaction トランザクション
     */
    private void hideAllFragment(FragmentTransaction transaction) {
        // 入庫
        if (inHouseFragment != null) {
            transaction.hide(inHouseFragment);
        }
        // 出庫
        if (outHouseFragment != null) {
            transaction.hide(outHouseFragment);
        }
        // 検索
        if (searchFragment != null) {
            transaction.hide(searchFragment);
        }
        // 設定
        if (settingFragment != null) {
            transaction.hide(settingFragment);
        }
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
     * 物品追加画面に遷移する
     * @param v ビュー
     */
    public void toAddGoods(View v) {
        Intent intent = new Intent(this, GoodsAddActivity.class);
        startActivity(intent);
    }

    /**
     * 画面初期表示
     * @param savedInstanceState インスタンス
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // レイアウト設定
        setContentView(R.layout.activity_btm_nav);
        // シェアードプリファレンス取得
        sp = getSharedPreferences("storage", 0);
        // モジュール取得
        navigation = (BottomNavigationBar) findViewById(R.id.navigation);
        navigation.setMode(BottomNavigationBar.MODE_FIXED);
        navigation.setBackgroundStyle(
                BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        // モジュール取得
        navigation
                .addItem(new BottomNavigationItem(R.drawable.nav_in,
                        "入庫").setActiveColor(Color.CYAN))
                .addItem(new BottomNavigationItem(R.drawable.nav_out,
                        "出庫").setActiveColor(Color.CYAN))
                .addItem(new BottomNavigationItem(R.drawable.nav_search,
                        "検索").setActiveColor(Color.CYAN))
                .addItem(new BottomNavigationItem(R.drawable.nav_setting,
                        "設定").setActiveColor(Color.CYAN))
                .initialise();
        navigation.setTabSelectedListener(mOnNavigationTabSelected);
        setDefaultFragment();
    }

    /**
     * デフォルトフラグメントを設定する
     */
    private void setDefaultFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        inHouseFragment = InHouseFragment.newInstance();
        transaction.add(R.id.content, inHouseFragment);
        transaction.commit();
    }

    /**
     * 結果のコールバック
     * @param requestCode 請求コード
     * @param resultCode 結果コード
     * @param data データ
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 結果のコールバック
        if (resultCode == Constant.RESULT_OK) {
            Bundle bundle = data.getExtras();
            // スキャンしたコード
            code = bundle.getString("qr_scan_result");
            if (Utils.regValid(code)) {
                getGoodsInfo(code);
            } else {
                Toast.makeText(this,
                        "バーコードをスキャンできない。",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * Goods情報検索
     */
    private void getGoodsInfo(String code) {
        String ip = sp.getString("ip", null);
        // URL設定
        String request_url = null;
        if (ip == null) {
            request_url = URL;
        } else {
            request_url = "https://" + ip + ":5003/Storage/getGoodesByCode";
        }
        // Goods情報取得
        OkGo.<Goods>post(request_url)
                .tag(this)
                .params("code", code)
                .execute(new AbsCallback<Goods>() {
                    /**
                     * 結果に転化Goods情報
                     */
                    @Override
                    public Goods convertResponse(okhttp3.Response response)
                            throws Throwable {
                        String jsonStr = response.body().string();
                        if (jsonStr.isEmpty()) {
                            return null;
                        }
                        JSONObject jsonObject = new JSONObject(jsonStr);
                        Goods goods = new Goods(jsonObject.getString(
                                "goods_name")
                                , jsonObject.getString("barcode")
                                , jsonObject.getString("description"));
                        return goods;
                    }

                    /**
                     * 成功を請求のコールバック
                     */
                    @Override
                    public void onSuccess(Response<Goods> response) {
                        if (response.body() != null) {
                            int pos = navigation.getCurrentSelectedPosition();
                            // 入庫フラグメントGoods情報を設定
                            if (pos == 0) {
                                inHouseFragment.clearViews();
                                inHouseFragment.setInfoValue(response.body()
                                                .getBarcode()
                                        , response.body().getGoodsName(),
                                        response.body().getDescription());
                                // 出庫フラグメントGoods情報を設定
                            } else if (pos == 1) {
                                outHouseFragment.clearViews();
                                outHouseFragment.setInfoValue(response.body()
                                                .getBarcode()
                                        , response.body().getGoodsName(),
                                        response.body().getDescription());
                            }
                        } else {
                            // 請求結果nullの処理
                            startActivity(new Intent(
                                    BtmNavActivity.this,
                                    GoodsAddActivity.class));
                            Toast.makeText(BtmNavActivity.this,
                                    "条件に該当する物品はない！",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    /**
     * 画面を廃棄する
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
        OkGo.getInstance().cancelAll();
    }
}
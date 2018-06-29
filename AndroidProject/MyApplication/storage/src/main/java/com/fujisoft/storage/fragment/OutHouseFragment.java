package com.fujisoft.storage.fragment;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.fujisoft.storage.Constant;
import com.fujisoft.storage.R;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.util.Calendar;

/**
 * 出庫画面
 */
public class OutHouseFragment extends Fragment {
    // デフォルトの請求URL
    private String URL = Constant.BASE_URL + "saveStorage";
    // デフォルトの在庫数請求URL
    private String COUNT_URL = Constant.BASE_URL + "getAtLibNumByCode";
    // コード
    private String code;
    // 出庫ボタン
    private Button outHouseBtn;
    // 品名テキスト
    private TextView nameTv;
    // 説明テキスト
    private EditText descEt;
    // 出庫日
    private EditText outHouseDate;
    // 価格
    private EditText outPrice;
    // 数
    private EditText outCount;
    // シェアードプリファレンス
    SharedPreferences sp;

    /**
     * 新しいインスタンス
     */
    public static OutHouseFragment newInstance() {
        OutHouseFragment fragment = new OutHouseFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * 画面初期表示
     * @param inflater
     * @param container コンテナ
     * @param savedInstanceState インスタンス
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // シェアードプリファレンス取得
        sp = getActivity().getSharedPreferences("storage", 0);
        // レイアウト設定
        View view = inflater.inflate(R.layout.out_house_fragment, container,
                false);
        // モジュール取得
        nameTv = view.findViewById(R.id.out_goods_name);
        descEt = view.findViewById(R.id.out_goods_desc);
        outHouseDate = view.findViewById(R.id.product_date);
        outPrice = view.findViewById(R.id.out_price);
        outCount = view.findViewById(R.id.out_house_count);
        outHouseBtn = view.findViewById(R.id.out_house_btn);
        // 出庫ボタンをクリックリスナー
        outHouseBtn.setOnClickListener(new View.OnClickListener() {
            /**
             * 出庫ボタンをクリックする
             */
            @Override
            public void onClick(View view) {
                // 品名空判断処理
                if (TextUtils.isEmpty(nameTv.getText())) {
                    Toast.makeText(getActivity(),
                            "まずは物品のバーコードをスキャンしてください。",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                // 出庫価格必須チェック
                if (TextUtils.isEmpty(outPrice.getText())) {
                    Toast.makeText(getActivity(),
                            "出庫価格は必ず入力してください。",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                // 出庫数必須チェック
                if (TextUtils.isEmpty(outCount.getText())) {
                    Toast.makeText(getActivity(),
                            "出庫数は必ず入力してください。",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                // 出庫数0以上チェック
                if (("0").equals(outCount.getText().toString())) {
                    Toast.makeText(getActivity(),
                            "出庫数に1以上の数字を入力してください。",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                // アドレスを取得
                String ip = sp.getString("ip", null);
                // URL設定
                String request_count_url = null;
                String request_url = null;
                if (ip == null) {
                    request_count_url = COUNT_URL;
                    request_url = URL;
                } else {
                    request_count_url = "https://"
                            + ip + ":5003/Storage/getAtLibNumByCode";
                    request_url = "https://" + ip + ":5003/Storage/saveStorage";
                }
                final String finalRequest_url = request_url;
                // 在庫数情報サーバに取得
                OkGo.<String>post(request_count_url)
                        .tag(this)
                        .params("code", code)
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(Response<String> response) {
                                int atLibNum = Integer.parseInt(response.body());
                                int outNum = Integer.parseInt(
                                        outCount.getText().toString());
                                if (outNum <= atLibNum) {
                                    // 出庫情報サーバに送信
                                    OkGo.<String>post(finalRequest_url)
                                            .tag(this)
                                            .params("barcode", code)
                                            .params("amount",
                                                    outCount.getText().toString())
                                            .params("flg", 1)
                                            .params("price",
                                                    outPrice.getText().toString())
                                            .execute(new StringCallback() {
                                                @Override
                                                public void onSuccess(
                                                        Response<String> response) {
                                                    Toast.makeText(
                                                            getActivity(),
                                                            response.body(),
                                                            Toast.LENGTH_SHORT)
                                                            .show();
                                                    clearViews();
                                                }
                                            });
                                } else {
                                    Toast.makeText(getActivity(),
                                            "出庫数が在庫数を超えている。（今在庫数：" + atLibNum + "個）",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });
        return view;
    }

    /**
     * 物品情報を設定する
     * @param code コード
     * @param name 名称
     * @param desc 詳細
     */
    public void setInfoValue(String code, String name, String desc) {
        this.code = code;
        nameTv.setText(name);
        descEt.setText(desc);
        Calendar c = Calendar.getInstance();
        outHouseDate.setText(c.get(Calendar.YEAR) + "-" +
                (c.get(Calendar.MONTH) + 1) + "-" +
                c.get(Calendar.DAY_OF_MONTH));
    }

    /**
     * 物品情報をクリアする
     */
    public void clearViews() {
        nameTv.setText("");
        descEt.setText("");
        outHouseDate.setText("");
        outPrice.setText("");
        outCount.setText("");
    }
}

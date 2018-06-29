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
 * 入庫画面
 */
public class InHouseFragment extends Fragment {
    // デフォルトの請求URL
    private String URL = Constant.BASE_URL + "saveStorage";
    // コード
    private String code;
    // 入庫ボタン
    private Button inHouseBtn;
    // 品名テキスト
    private TextView nameTv;
    // 説明テキスト
    private EditText descEt;
    // 入庫日
    private EditText inHouseDate;
    // 価格
    private EditText inPrice;
    // 数
    private EditText inCount;
    // シェアードプリファレンス
    SharedPreferences sp;

    /**
     * 新しいインスタンス
     */
    public static InHouseFragment newInstance() {
        InHouseFragment fragment = new InHouseFragment();
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
        View view = inflater.inflate(R.layout.in_house_fragment, container,
                false);
        // モジュール取得
        nameTv = view.findViewById(R.id.in_goods_name);
        descEt = view.findViewById(R.id.in_goods_desc);
        inHouseDate = view.findViewById(R.id.product_date);
        inPrice = view.findViewById(R.id.in_price);
        inCount = view.findViewById(R.id.in_house_count);
        inHouseBtn = view.findViewById(R.id.in_house_btn);
        // 入庫ボタンをクリックリスナー
        inHouseBtn.setOnClickListener(new View.OnClickListener() {
            /**
             * 入庫ボタンをクリックする
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
                // 入庫価格必須チェック
                if (TextUtils.isEmpty(inPrice.getText())) {
                    Toast.makeText(getActivity(),
                            "入庫価格は必ず入力してください。",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                // 入庫数必須チェック
                if (TextUtils.isEmpty(inCount.getText())) {
                    Toast.makeText(getActivity(),
                            "入庫数は必ず入力してください。",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                // 入庫数は0の判断処理
                if (("0").equals(inCount.getText().toString())) {
                    Toast.makeText(getActivity(),
                            "入庫数に1以上の数字を入力してください。",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                // アドレスを取得
                String ip = sp.getString("ip", null);
                // URL設定
                String request_url = null;
                if (ip == null) {
                    request_url = URL;
                } else {
                    request_url = "https://" + ip + ":5003/Storage/saveStorage";
                }
                // 入庫情報サーバに送信
                OkGo.<String>post(request_url)
                        .tag(this)
                        .params("barcode", code)
                        .params("amount", inCount.getText().toString())
                        .params("flg", 0)
                        .params("price", inPrice.getText().toString())
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(Response<String> response) {
                                Toast.makeText(getActivity(), response.body(),
                                        Toast.LENGTH_SHORT).show();
                                clearViews();
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
        inHouseDate.setText(c.get(Calendar.YEAR) + "-" +
                (c.get(Calendar.MONTH) + 1) + "-" +
                c.get(Calendar.DAY_OF_MONTH));
    }

    /**
     * 物品情報をクリアする
     */
    public void clearViews() {
        nameTv.setText("");
        descEt.setText("");
        inHouseDate.setText("");
        inPrice.setText("");
        inCount.setText("");
    }
}
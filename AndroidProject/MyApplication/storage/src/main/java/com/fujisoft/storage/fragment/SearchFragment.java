package com.fujisoft.storage.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import com.fujisoft.storage.Constant;
import com.fujisoft.storage.R;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 検索画面
 */
public class SearchFragment extends Fragment {
    // デフォルトの在庫数請求URL
    private String URL0 = Constant.BASE_URL + "getStorageByGoods";
    // デフォルトの入庫情報請求URL
    private String URL1 = Constant.BASE_URL + "getStorageByDate";
    // 検索タイプ
    private int typeVal = 0;
    // 検索内容
    private EditText goodsNameEt;
    // 検索ボタン
    private Button searchBtn;
    // 結果リスト
    private ListView goodsLv;
    // 検索タイプスピナー
    private Spinner typeSpinner;
    // アダプタ
    private MyAdapter myAdapter;
    // シェアードプリファレンス
    SharedPreferences sp;
    // リストデータ
    private List<String> searchList = new ArrayList<>();

    /**
     * 新しいインスタンス
     */
    public static SearchFragment newInstance() {
        SearchFragment fragment = new SearchFragment();
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
        View view = inflater.inflate(R.layout.search_fragment, container, false);
        // モジュール取得
        typeSpinner = view.findViewById(R.id.search_type);
        goodsNameEt = view.findViewById(R.id.search_goods_et);
        searchBtn = view.findViewById(R.id.search_btn);
        goodsLv = view.findViewById(R.id.search_lv);
        myAdapter = new MyAdapter();
        // アダプタを設定
        goodsLv.setAdapter(myAdapter);
        // 検索ボタンをクリックリスナー
        searchBtn.setOnClickListener(new View.OnClickListener() {
            /**
             * 検索ボタンをクリックする
             */
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager) getActivity()
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                // 軟らかい強制隠しキーボード
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                // 検索結果リストクリア
                searchList.clear();
                myAdapter.notifyDataSetChanged();
                // アドレスを取得
                String ip = sp.getString("ip", null);
                // URL設定
                String request_url0 = null;
                String request_url1 = null;
                if (ip == null) {
                    request_url0 = URL0;
                    request_url1 = URL1;
                } else {
                    request_url0 = "https://"
                            + ip + ":5003/Storage/getStorageByGoods";
                    request_url1 = "https://"
                            + ip + ":5003/Storage/getStorageByDate";
                }
                // 在庫数検索
                if (typeVal == 0) {
                    OkGo.<String>post(request_url0)
                            .tag(this)
                            .params("name",
                                    goodsNameEt.getText().toString())
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(Response<String> response) {
                                    String resultStr = response.body()
                                            .replace("[", "")
                                            .replace("]", "");
                                    if (resultStr != "" && resultStr != null) {
                                        for (String str : resultStr.split(";")) {
                                            searchList.add(str);
                                        }
                                    } else {
                                        Toast.makeText(getActivity(),
                                                "条件に該当する物品はない!",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                    myAdapter.notifyDataSetChanged();
                                }
                            });
                    // 出/入庫数情報検索
                } else if (typeVal == 1) {
                    OkGo.<String>post(request_url1)
                            .tag(this)
                            .params("date", goodsNameEt.getText().toString())
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(Response<String> response) {
                                    String resultStr = response.body();
                                    if (resultStr != "" && resultStr != null) {
                                        for (String str : resultStr.split(";")) {
                                            searchList.add(str);
                                        }
                                    } else {
                                        Toast.makeText(getActivity(),
                                                "条件に該当する物品はない!",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                    myAdapter.notifyDataSetChanged();
                                }
                            });
                }
            }
        });
        // 検索タイプスピナーリスナーを設定
        typeSpinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
            /**
             * アイテム選択する
             */
            @Override
            public void onItemSelected(
                    AdapterView<?> adapterView, View view, int i, long l) {
                goodsNameEt.setText("");
                searchList.clear();
                myAdapter.notifyDataSetChanged();
                if (i == 0) {
                    typeVal = 0;
                    goodsNameEt.setInputType(InputType.TYPE_CLASS_TEXT);
                } else if (i == 1) {
                    typeVal = 1;
                    goodsNameEt.setInputType(InputType.TYPE_CLASS_DATETIME);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        return view;
    }

    // カスタマイズアダプタ定義
    public class MyAdapter extends BaseAdapter {
        LayoutInflater inflater = null;

        /**
         * コンストラクタ
         */
        public MyAdapter() {
            inflater = LayoutInflater.from(getActivity());
        }

        /**
         * 数を取得する
         */
        @Override
        public int getCount() {
            return searchList.size();
        }

        /**
         * アイテムを取得する
         */
        @Override
        public Object getItem(int i) {
            return searchList.get(i);
        }

        /**
         * アイテムIdを取得する
         */
        @Override
        public long getItemId(int i) {
            return i;
        }

        /**
         * アイテムビューを取得する
         */
        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder1 vh1 = null;
            ViewHolder2 vh2 = null;
            // 検索タイプ：在庫数
            if (typeVal == 0) {
                if (view == null || view.getTag() instanceof ViewHolder2) {
                    vh1 = new ViewHolder1();
                    // レイアウト設定
                    view = inflater.inflate(R.layout.search_lv_item_1, null);
                    // モジュール取得
                    vh1.goodsNameTv = view.findViewById(R.id.goods_name);
                    vh1.codeTv = view.findViewById(R.id.barcode);
//                    vh1.codeTv.setEllipsize(TextUtils.TruncateAt.MARQUEE);
//                    vh1.codeTv.setSingleLine(true);
//                    vh1.codeTv.setSelected(true);
//                    vh1.codeTv.setFocusable(true);
//                    vh1.codeTv.setFocusableInTouchMode(true);
                    vh1.inTotalTv = view.findViewById(R.id.price);
                    view.setTag(vh1);
                } else {
                    vh1 = (ViewHolder1) view.getTag();
                }
                // 検索タイプ：出/入庫情報
            } else if (typeVal == 1) {
                if (view == null || view.getTag() instanceof ViewHolder1) {
                    vh2 = new ViewHolder2();
                    // レイアウト設定
                    view = inflater.inflate(R.layout.search_lv_item_2, null);
                    // モジュール取得
                    vh2.goodsNameTv = view.findViewById(R.id.goods_name);
                    vh2.priceTv = view.findViewById(R.id.price);
                    vh2.houseDateTv = view.findViewById(R.id.house_date);
                    vh2.amountTv = view.findViewById(R.id.house_count);
                    vh2.houseTv = view.findViewById(R.id.house_tv);
                    view.setTag(vh2);
                } else {
                    vh2 = (ViewHolder2) view.getTag();
                }
            }
            // リスト情報を取得
            List<String> list = Arrays.asList(searchList.get(i).split(","));
            // 在庫数情報を設定
            if (typeVal == 0) {
                vh1.goodsNameTv.setText(list.get(0));
                vh1.codeTv.setText(list.get(1));
                vh1.inTotalTv.setText(list.get(2));
                // 出/入庫情報を設定
            } else if (typeVal == 1) {
                vh2.goodsNameTv.setText(list.get(0));
                vh2.priceTv.setText(list.get(2));
                vh2.houseDateTv.setText(list.get(3));
                vh2.amountTv.setText(list.get(4));
                if (("0").equals(list.get(5))) {
                    vh2.amountTv.setTextColor(Color.BLUE);
                    vh2.houseTv.setText("入庫日付：");
                } else {
                    vh2.amountTv.setTextColor(Color.RED);
                    vh2.houseTv.setText("出庫日付：");
                }
            }

            return view;
        }

        /**
         * 在庫数ビューホルダー
         */
        class ViewHolder1 {
            TextView goodsNameTv, codeTv, inTotalTv;
        }

        /**
         * 出入庫情報ビューホルダー
         */
        class ViewHolder2 {
            TextView goodsNameTv, priceTv, houseDateTv, amountTv, houseTv;
        }
    }
}
package com.fujisoft.storage.fragment;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import com.fujisoft.storage.R;

/**
 * 設定画面
 */
public class SettingFragment extends Fragment {
    // アドレススピナー
    private Spinner spinner;
    // シェアードプリファレンス
    private SharedPreferences sp;
    // シェアードプリファレンスのエディタ
    SharedPreferences.Editor editor;

    /**
     * 新しいインスタンス
     */
    public static SettingFragment newInstance() {
        SettingFragment fragment = new SettingFragment();
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
        // レイアウト設定
        View view = inflater.inflate(R.layout.setting_fragment, container,
                false);
        // シェアードプリファレンス取得
        sp = getActivity().getSharedPreferences("storage", 0);
        editor = sp.edit();
        // モジュール取得
        spinner = view.findViewById(R.id.spinner);
        // アドレススピナーリスナーを設定
        spinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    
            /**
             * アイテム選択する
             */
            @Override
            public void onItemSelected(
                    AdapterView<?> adapterView, View view, int i, long l) {
                editor.putString("ip",
                        spinner.getItemAtPosition(i).toString());
                editor.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        return view;
    }
}
package com.fujisoft.update;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
/**
 * カスタマイズアダプタ
 * @author 860115025
 * 2017/09/20
 */
public class MyAdapter extends BaseAdapter {
    private Context mContext;
    private List<Map<String,Object>> mList = new ArrayList<>();
    /**
     * 構造法
     */
    public MyAdapter(Context context, List<Map<String,Object>> list) {
        mContext = context;
        mList = list;
    }
    /**
     * リストビューのサイズ
     */
    @Override
    public int getCount() {
        return mList.size();
    }
    /**
     * リストビューのアイテム
     */
    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }
    /**
     * リストビューのアイテムID
     */
    @Override
    public long getItemId(int i) {
        return i;
    }
    /**
     * リストビューを取得
     */
    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {
            viewHolder = new ViewHolder();
            //　レイアウトを設定
            view = LayoutInflater.from(mContext).inflate(R.layout.list_cell, null);
            // モジュール取得
            viewHolder.mName =  view.findViewById(R.id.name);
            viewHolder.mSize = view.findViewById(R.id.size);
            viewHolder.mDelBtn = view.findViewById(R.id.del_btn);
            // タグを設定
            view.setTag(viewHolder);
        } else {
            // タグを取得
            viewHolder = (ViewHolder) view.getTag();
        }
        // ファイル名を設定
        viewHolder.mName.setText(mList.get(i).get("name").toString());
        // ファイルサイズを設定
        viewHolder.mSize.setText(mList.get(i).get("size").toString());
        // 削除ボタンリスナーを設定
        viewHolder.mDelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemDeleteListener.onDeleteClick(i);
            }
        });
        return view;
    }

    /**
     * 削除ボタンのリスナインターフェース
     */
    public interface onItemDeleteListener {
        void onDeleteClick(int i);
    }
    // 削除ボタンのリスナ
    private onItemDeleteListener mOnItemDeleteListener;
    /**
     * 削除ボタンのリスナを設定
     */
    public void setOnItemDeleteClickListener(onItemDeleteListener mOnItemDeleteListener) {
        this.mOnItemDeleteListener = mOnItemDeleteListener;
    }
    /**
     * ビューホルダー
     */
    class ViewHolder {
        // ファイル名
        TextView mName;
        // ファイルサイズ
        TextView mSize;
        // 削除ボタン
        ImageButton mDelBtn;
    }

}

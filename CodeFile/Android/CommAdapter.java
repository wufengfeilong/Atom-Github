package com.fushi.test.common;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by 860115039 on 2015/12/28.
 */
public abstract class CommAdapter<T> extends BaseAdapter{
    private List<T> mList;
    private LayoutInflater mInflater;
    //加载的ListView中item的布局资源id
    private int mLayoutId;


    public CommAdapter(Context context,List<T> list,int layoutId) {
        mList = list;
        mLayoutId = layoutId;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public T getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder holder;
        if(convertView == null){
            view = mInflater.inflate(mLayoutId, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }else{
            view = convertView;
            holder = (ViewHolder)view.getTag();
        }
        setHolder(holder, getItem(position));
        return view;
    }

    public abstract void setHolder(ViewHolder holder, T list_item);

    public class ViewHolder{
        private View view;
        public ViewHolder(View view) {
            this.view = view;
        }
        public ImageView getIV(int ivId){
            return (ImageView)view.findViewById(ivId);
        }
        //子类调用holder.getTV()获取item的TextView
        public TextView getTV(int tvId){
            return (TextView)view.findViewById(tvId);
        }
    }
}

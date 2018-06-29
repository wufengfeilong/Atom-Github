package com.fcn.park.base.recycler_listener;

import android.support.v7.widget.RecyclerView;

/**
 * Created by liuyq on 2017/04/10.
 * 类描述：
 */
public abstract class OnRecyclerItemClickListener extends OnRecyclerItemListener {

    public OnRecyclerItemClickListener(RecyclerView recyclerView) {
        super(recyclerView);
    }
    //实现长按事件的处理，暴露出点击事件的处理方式
    @Override
    public boolean onItemLongClick(RecyclerView.ViewHolder vh) {
        return false;
    }
}

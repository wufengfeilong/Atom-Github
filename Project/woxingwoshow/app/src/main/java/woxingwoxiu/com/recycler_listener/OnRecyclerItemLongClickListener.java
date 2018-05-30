package woxingwoxiu.com.recycler_listener;

import android.support.v7.widget.RecyclerView;

/**
 * create by 860115039
 * date      2018/5/8
 * time      9:00
 */
public abstract class OnRecyclerItemLongClickListener extends OnRecyclerItemListener {

    public OnRecyclerItemLongClickListener(RecyclerView recyclerView) {
        super(recyclerView);
    }
    //实现点击事件的处理，暴露出长按事件的处理方式
    @Override
    public void onItemClick(RecyclerView.ViewHolder vh) {

    }

}

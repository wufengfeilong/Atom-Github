package sdwxwx.com.recycler_listener;

import android.support.v7.widget.RecyclerView;

/**
 * create by 860115039
 * date      2018/5/8
 * time      9:00
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

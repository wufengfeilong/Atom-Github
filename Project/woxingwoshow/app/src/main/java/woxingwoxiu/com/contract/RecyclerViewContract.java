package woxingwoxiu.com.contract;

import android.support.v7.widget.RecyclerView;

import java.util.List;

/**
 * create by 860115039
 * date      2018/5/8
 * time      9:00
 */

public interface RecyclerViewContract {

    interface View<T> {
        void initRecyclerView();

        void bindListData(List<T> beanList);

        RecyclerView getRecyclerView();
    }

    interface Presenter {
        void loadListData();
    }
}

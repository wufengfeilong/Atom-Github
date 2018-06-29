package com.fcn.park.base.contract;

import android.support.v7.widget.RecyclerView;

import java.util.List;

/**
 * Created by 860115001 on 2018/04/03.
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

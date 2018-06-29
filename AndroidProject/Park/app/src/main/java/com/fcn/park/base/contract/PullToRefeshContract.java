package com.fcn.park.base.contract;

/**
 * Created by liuyq on 2017/04/10.
 * 类描述：
 */

public interface PullToRefeshContract {

    interface View {
        void updateIsEnd(boolean isEnd);
    }

    interface Presenter {
        /**
         * 加载更多的数据
         * 只需要根据相应的页码加载相应的数据，无需关心刷新和加载更多
         *
         * @param page
         */
        void onLoadMore(int page);

    }

}

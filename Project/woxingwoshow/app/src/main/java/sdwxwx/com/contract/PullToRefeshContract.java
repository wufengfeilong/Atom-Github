package sdwxwx.com.contract;

/**
 * create by 860115039
 * date      2018/5/8
 * time      9:00
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

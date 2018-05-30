package woxingwoxiu.com.home.contract;

import woxingwoxiu.com.base.BaseView;
import woxingwoxiu.com.widget.TabLayout;

/**
 * create by 860115039
 * date      2018/5/9
 * time      9:47
 */
public interface HomeFragmentContract {

    interface View extends BaseView, TabLayout.OnTabSelectedListener {
        void showCategoryVideo();
        void hideCategoryVideo(boolean flg);
        void setSelectedTab(int pos);
    }

    interface Presenter {

        void loadListData();

        void backClick();

        void tabMoreClick();

        void cityClick();

        void searchClick();

        void letterClick();
    }
}

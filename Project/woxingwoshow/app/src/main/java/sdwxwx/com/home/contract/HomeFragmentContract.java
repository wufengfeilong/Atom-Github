package sdwxwx.com.home.contract;

import sdwxwx.com.base.BaseView;
import sdwxwx.com.bean.CategoryBean;
import sdwxwx.com.widget.TabLayout;

import java.util.List;

/**
 * create by 860115039
 * date      2018/5/9
 * time      9:47
 */
public interface HomeFragmentContract {

    interface View extends BaseView, TabLayout.OnTabSelectedListener {
        void loadTabs(List<CategoryBean> list);
        void showCategoryVideo();
        void hideCategoryVideo(boolean flg);
        void setSelectedTab(int pos);
    }

    interface Presenter {
        void loadTabData();

        void loadListData();

        void backClick();

        void tabMoreClick();

        void cityClick();

        void searchClick();

        void letterClick();
    }
}

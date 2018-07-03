package sdwxwx.com.home.contract;

import com.liaoinstan.springview.widget.SpringView;
import sdwxwx.com.base.BaseView;
import sdwxwx.com.bean.CategoryBean;
import sdwxwx.com.contract.RecyclerViewContract;
import sdwxwx.com.home.bean.BannerBean;
import sdwxwx.com.home.bean.PlayVideoBean;

import java.util.List;

/**
 * create by 860115039
 * date      2018/5/9
 * time      9:47
 */
public interface HomeCategoryVideoContract {

    interface View extends BaseView,RecyclerViewContract.View<PlayVideoBean>,SpringView.OnFreshListener {
        void bindCategoryData(List<CategoryBean> list);
        void showVideoCategory();
        void hideVideoCategory();
        void bindBannerData(List<BannerBean> list);
        SpringView getSpringView();
    }

    interface Presenter extends RecyclerViewContract.Presenter{

       void loadListData(String category_id,String page);

       void loadBannerData();

       void loadCategoryData();

    }
}

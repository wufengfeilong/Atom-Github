package woxingwoxiu.com.home.contract;

import com.liaoinstan.springview.widget.SpringView;
import woxingwoxiu.com.base.BaseView;
import woxingwoxiu.com.contract.RecyclerViewContract;
import woxingwoxiu.com.home.bean.BannerBean;
import woxingwoxiu.com.home.bean.PlayVideoBean;

import java.util.List;

/**
 * create by 860115039
 * date      2018/5/9
 * time      9:47
 */
public interface HomeCategoryVideoContract {

    interface View extends BaseView,RecyclerViewContract.View<PlayVideoBean>,SpringView.OnFreshListener {
        void showVideoCategory();
        void hideVideoCategory();
        void bindBannerData(List<BannerBean> list);
    }

    interface Presenter extends RecyclerViewContract.Presenter{

       void loadListData(String category_id,String city_id,String page);

       void loadBannerData();

    }
}

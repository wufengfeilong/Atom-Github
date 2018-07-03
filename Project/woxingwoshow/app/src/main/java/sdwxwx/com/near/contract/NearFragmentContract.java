package sdwxwx.com.near.contract;

import com.liaoinstan.springview.widget.SpringView;
import sdwxwx.com.base.BaseView;
import sdwxwx.com.contract.RecyclerViewContract;
import sdwxwx.com.home.bean.PlayVideoBean;

/**
 * create by 860115039
 * date      2018/5/9
 * time      9:47
 */
public interface NearFragmentContract {
    interface View extends BaseView,RecyclerViewContract.View<PlayVideoBean>,SpringView.OnFreshListener {
        SpringView getSpringView();
    }

    interface Presenter{

        void loadListData(double longitude,double latitude,String page);

    }
}

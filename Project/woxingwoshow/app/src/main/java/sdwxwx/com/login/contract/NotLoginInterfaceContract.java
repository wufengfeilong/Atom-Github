package sdwxwx.com.login.contract;

import com.liaoinstan.springview.widget.SpringView;

import sdwxwx.com.base.BaseView;
import sdwxwx.com.contract.RecyclerViewContract;
import sdwxwx.com.home.bean.PlayVideoBean;

/**
 * Created by 丁胜胜 on 2018/05/23.
 */

public interface NotLoginInterfaceContract {

    interface View extends BaseView,RecyclerViewContract.View<PlayVideoBean>,SpringView.OnFreshListener {
        SpringView getSpringView();
    }

    interface Presenter extends RecyclerViewContract.Presenter{

        void loadListData(String page);

        void onLogin();

        void onSearch();
    }
}


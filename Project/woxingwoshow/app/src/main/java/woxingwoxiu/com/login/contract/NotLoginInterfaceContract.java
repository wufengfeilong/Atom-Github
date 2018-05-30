package woxingwoxiu.com.login.contract;

import com.liaoinstan.springview.widget.SpringView;

import woxingwoxiu.com.base.BaseView;
import woxingwoxiu.com.contract.RecyclerViewContract;
import woxingwoxiu.com.home.bean.CategoryVideoListBean;
import woxingwoxiu.com.login.bean.NotLoginInterfaceBean;

/**
 * Created by 丁胜胜 on 2018/05/23.
 */

public interface NotLoginInterfaceContract {

    interface View extends BaseView,RecyclerViewContract.View<NotLoginInterfaceBean>,SpringView.OnFreshListener {

    }

    interface Presenter extends RecyclerViewContract.Presenter{

        void loadListData();

        void onLogin();

        void onSearch();
    }
}


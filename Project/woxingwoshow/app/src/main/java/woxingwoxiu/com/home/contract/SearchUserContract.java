package woxingwoxiu.com.home.contract;

import com.liaoinstan.springview.widget.SpringView;
import woxingwoxiu.com.base.BaseView;
import woxingwoxiu.com.contract.RecyclerViewContract;
import woxingwoxiu.com.home.bean.SearchUserBean;

/**
 * create by 860115039
 * date      2018/5/14
 * time      15:22
 */
public interface SearchUserContract {

    interface View extends BaseView,RecyclerViewContract.View<SearchUserBean>,SpringView.OnFreshListener{
    }

    interface Presenter extends RecyclerViewContract.Presenter {

        void cancelClick();

        void myQrCodeClick();

        void scanClick();

        void loadTopicData();

        void loadUserData();

    }
}

package sdwxwx.com.home.contract;

import com.liaoinstan.springview.widget.SpringView;

import java.util.List;

import sdwxwx.com.base.BaseView;
import sdwxwx.com.contract.RecyclerViewContract;
import sdwxwx.com.home.bean.SearchUserBean;

/**
 * create by 860115039
 * date      2018/5/14
 * time      15:22
 */
public interface SearchUserContract {

    interface View extends BaseView,RecyclerViewContract.View<SearchUserBean>,SpringView.OnFreshListener{
        void haveUser(List<SearchUserBean.HaveUserBean> list);
        void haveNoUser();
    }

    interface Presenter extends RecyclerViewContract.Presenter {

        void cancelClick();

        void myQrCodeClick();

        void scanClick();

        void loadTopicData();

        void loadUserData();

    }
}

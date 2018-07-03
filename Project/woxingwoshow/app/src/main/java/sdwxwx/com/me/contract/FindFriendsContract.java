package sdwxwx.com.me.contract;

import sdwxwx.com.base.BaseView;
import sdwxwx.com.contract.RecyclerViewContract;
import sdwxwx.com.home.bean.RecommendUserBean;
import sdwxwx.com.me.bean.FindFriendsBean;

/**
 * Created by 860117073 on 2018/5/22.
 */

public interface FindFriendsContract {
    interface View extends BaseView,RecyclerViewContract.View<RecommendUserBean> {
    }

    interface Presenter extends RecyclerViewContract.Presenter{
        void myQrCodeClick();
    }
}

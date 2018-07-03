package sdwxwx.com.letter.contract;

import sdwxwx.com.base.BaseView;
import sdwxwx.com.bean.UserBean;
import sdwxwx.com.contract.RecyclerViewContract;

/**
 * Created by 860117073 on 2018/5/28.
 */

public interface LetterListContract {
    interface View extends BaseView,RecyclerViewContract.View<UserBean> {
        void finishThis();
    }
    interface Presenter extends RecyclerViewContract.Presenter {
    }
}

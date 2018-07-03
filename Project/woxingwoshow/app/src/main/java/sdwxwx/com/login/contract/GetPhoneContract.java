package sdwxwx.com.login.contract;

import sdwxwx.com.base.BaseView;
import sdwxwx.com.contract.RecyclerViewContract;
import sdwxwx.com.login.bean.GetPhoneBean;

/**
 * Created by 丁胜胜 on 2018/05/29.
 */

public interface GetPhoneContract {

    public interface View extends BaseView,RecyclerViewContract.View<GetPhoneBean> {

    }

    public interface Presenter extends RecyclerViewContract.Presenter{
            void childEntityData();

            void onClickBack();
    }

}

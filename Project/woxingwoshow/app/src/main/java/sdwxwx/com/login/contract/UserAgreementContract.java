package sdwxwx.com.login.contract;

import sdwxwx.com.base.BaseView;
import sdwxwx.com.login.bean.UserAgreementBean;

/**
 * Created by 丁胜胜 on 2018/05/14.
 */

public interface UserAgreementContract {

    interface View extends BaseView{

        void bindWebData(UserAgreementBean bean);

        void showLoadingProgress();

        void hintLoadingProgress();

        void updateLoadingProgress(int progress);

    }

    interface Presenter{

        void loadPageData();

        void onCross();

    }

}

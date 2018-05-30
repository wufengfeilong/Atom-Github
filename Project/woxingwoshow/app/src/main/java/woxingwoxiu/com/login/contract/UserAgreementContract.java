package woxingwoxiu.com.login.contract;

import woxingwoxiu.com.base.BaseView;
import woxingwoxiu.com.login.bean.UserAgreementBean;

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

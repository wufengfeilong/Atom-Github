package sdwxwx.com.release.contract;

import java.util.List;

import sdwxwx.com.base.BaseView;
import sdwxwx.com.home.bean.SearchUserBean;

/**
 * 添加话题画面的Contract
 */

public class FriendsListContract {
    public interface View extends BaseView {

        void refreshListData(List<SearchUserBean.HaveUserBean> list);
    }

    public interface Presenter {

        /**
         * @param keyWords
         * @param page
         */
        void getFriendList(String keyWords, String page);

        /**
         * 点击取消
         */
        void onCancelClick();
    }
}

package sdwxwx.com.release.contract;

import com.liaoinstan.springview.widget.SpringView;

import java.util.List;

import sdwxwx.com.base.BaseView;
import sdwxwx.com.contract.RecyclerViewContract;
import sdwxwx.com.home.bean.SearchUserBean;

/**
 * 添加话题画面的Contract
 */

public class FriendsListContract {
    public interface View extends BaseView, RecyclerViewContract.View<SearchUserBean.HaveUserBean>, SpringView.OnFreshListener {
        void bindListData(List<SearchUserBean.HaveUserBean> list);

        void loadMoreData(List<SearchUserBean.HaveUserBean> data);
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

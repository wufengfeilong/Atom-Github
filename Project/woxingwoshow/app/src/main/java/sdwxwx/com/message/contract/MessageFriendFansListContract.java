package sdwxwx.com.message.contract;

import com.liaoinstan.springview.widget.SpringView;

import java.util.List;

import sdwxwx.com.base.BaseView;
import sdwxwx.com.contract.RecyclerViewContract;
import sdwxwx.com.home.bean.RecommendUserBean;

/**
 *
 * 类描述：好友粉丝列表
 */

public interface MessageFriendFansListContract {
    interface View extends BaseView,RecyclerViewContract.View<RecommendUserBean>,SpringView.OnFreshListener {
        void bindListData(List<RecommendUserBean> bean);
    }
    interface Presenter extends RecyclerViewContract.Presenter{
        void onClickBack();
        void loadListData(String type, String member_id,String page);
    }
}

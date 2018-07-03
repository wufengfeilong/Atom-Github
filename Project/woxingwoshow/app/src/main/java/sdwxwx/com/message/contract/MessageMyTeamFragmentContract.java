package sdwxwx.com.message.contract;

import com.liaoinstan.springview.widget.SpringView;

import sdwxwx.com.base.BaseView;
import sdwxwx.com.contract.RecyclerViewContract;
import sdwxwx.com.databinding.MessageMyTeamGradeFragmentBinding;
import sdwxwx.com.home.bean.RecommendUserBean;

/**
 *
 * 类描述：我的团队一级二级列表
 */

public interface MessageMyTeamFragmentContract {
    interface View extends BaseView,RecyclerViewContract.View<RecommendUserBean> {
//        void bindTabFragment(List<Fragment> fragmentList, List<String> titleList);
       String getMemberId();
    }
    interface Presenter {
//        void initFragments();
        void loadListData(int type, MessageMyTeamGradeFragmentBinding mDataBinding);
//          List setTeamOneList();
//          List setTeamTwoList();
    }
}


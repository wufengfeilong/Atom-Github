package woxingwoxiu.com.message.contract;

import com.liaoinstan.springview.widget.SpringView;

import woxingwoxiu.com.base.BaseView;
import woxingwoxiu.com.contract.RecyclerViewContract;
import woxingwoxiu.com.message.bean.MessageMyTeamBean;

/**
 *
 * 类描述：我的团队一级二级列表
 */

public interface MessageMyTeamFragmentContract {
    interface View extends BaseView,RecyclerViewContract.View<MessageMyTeamBean>,SpringView.OnFreshListener {
//        void bindTabFragment(List<Fragment> fragmentList, List<String> titleList);
    }
    interface Presenter {
//        void initFragments();
        void loadListData();
    }
}


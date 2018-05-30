package woxingwoxiu.com.message.contract;

import com.liaoinstan.springview.widget.SpringView;

import woxingwoxiu.com.base.BaseView;
import woxingwoxiu.com.contract.RecyclerViewContract;
import woxingwoxiu.com.message.bean.MessageWealthBean;

/**
 * Created by 860117066 on 2018/05/17.
 */

public interface MessageWealthFragmentContract {
    interface View extends BaseView,RecyclerViewContract.View<MessageWealthBean>,SpringView.OnFreshListener {
//        void bindTabFragment(List<Fragment> fragmentList, List<String> titleList);
    }
    interface Presenter {
        //        void initFragments();
        void loadListData();
    }
}

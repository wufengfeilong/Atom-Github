package woxingwoxiu.com.me.contract;

import com.liaoinstan.springview.widget.SpringView;

import woxingwoxiu.com.base.BaseView;
import woxingwoxiu.com.contract.RecyclerViewContract;
import woxingwoxiu.com.me.bean.MeHomeBean;

/**
 * Created by 860117073 on 2018/5/11.
 */

public interface MeHomeFragmentContract {
    interface View extends BaseView,RecyclerViewContract.View<MeHomeBean>,SpringView.OnFreshListener {
//        void bindTabFragment(List<Fragment> fragmentList, List<String> titleList);
    }

    interface Presenter {

        void loadListData();
    }
}

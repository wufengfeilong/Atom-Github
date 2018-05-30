package woxingwoxiu.com.message.contract;

import woxingwoxiu.com.base.BaseView;

/**
 *
 * 类描述：我的团队一级二级列表
 */

public interface MessageMyTeamContract {
    interface View extends BaseView {
//        void bindTabFragment(List<Fragment> fragmentList, List<String> titleList);
    }
    interface Presenter {
//        void initFragments();
//        void loadListData();
          void onClickBack();
    }
}


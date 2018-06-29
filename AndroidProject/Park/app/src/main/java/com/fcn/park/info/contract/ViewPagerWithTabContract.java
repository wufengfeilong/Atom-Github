package com.fcn.park.info.contract;

import android.support.v4.app.Fragment;

import java.util.List;

/**
 * Created by liuyq 2018/04/09
 * 类描述：ViewPage和TabLayout的联用
 */

public interface ViewPagerWithTabContract {
    interface View {
        /**
         * 绑定TabFragment的数据
         * @param fragmentList
         * @param titleList
         */
        void bindTabFragment(List<Fragment> fragmentList, List<String> titleList);
    }

    interface Presenter {
        /**
         * 初始化fragment
         */
        void initFragments();
    }

    interface Module {
        /**
         * 取得fragment的列表
         * @return
         */
        List<Fragment> getFragmentList();

        /**
         * 取得title列表
         * @return
         */
        List<String> getTitleList();
    }
}

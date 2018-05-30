package woxingwoxiu.com.me.contract;

import woxingwoxiu.com.base.BaseView;

/**
 * Created by 860117073 on 2018/5/11.
 */

public interface MeHomeContract {
    interface View extends BaseView {

    }

    interface Presenter {
        // 点击设置图片
        void onClickSetting();

        // 点击编辑按钮
        void onClickEdit();

        // 点击粉丝按钮
        void onClickFans();

        // 点击关注按钮
        void onClickAttention();

        // 点击我的团队按钮
        void onClickMyTeam();

        // 点击我的财富按钮
        void onClickMyWealth();

        void onClickFindFriends();

        void loadListData();
    }
}

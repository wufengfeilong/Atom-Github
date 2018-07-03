package sdwxwx.com.me.contract;

import sdwxwx.com.base.BaseView;

/**
 * Created by 860117073 on 2018/5/11.
 */

public interface MeHomeContract {
    interface View extends BaseView {
        void onShare();
        void clickHederImg();
    }

    interface Presenter {
        // 分享
        void onClickShare();
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

        // 点击头像
        void onClickHeaderImg();

        void onClickFindFriends();

        void loadListData();
    }
}

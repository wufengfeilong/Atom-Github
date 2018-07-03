package sdwxwx.com.me.presenter;


import android.content.Intent;

import sdwxwx.com.base.BasePresenter;
import sdwxwx.com.cons.Constant;
import sdwxwx.com.login.utils.LoginHelper;
import sdwxwx.com.me.activity.FindFriendsActivity;
import sdwxwx.com.me.activity.MeEditActivity;
import sdwxwx.com.me.activity.SettingActivity;
import sdwxwx.com.me.contract.MeHomeContract;
import sdwxwx.com.message.activity.MessageFriendFansListActivity;
import sdwxwx.com.message.activity.MessageMyTeamActivity;
import sdwxwx.com.message.activity.MessageWealthActivity;

/**
 * Created by 860116042 on 2018/5/16.
 */

public class MeHomePresenter extends BasePresenter<MeHomeContract.View> implements MeHomeContract.Presenter {

    // 点击进行分享
    @Override
    public void onClickShare(){
        getView().onShare();
    }

    // 点击设置图片
    @Override
    public void onClickSetting(){
        // 跳转到设置画面
        SettingActivity.actionStart(getView().getContext());
    }

    @Override
    public void onClickEdit() {
        // 跳转到编辑画面
        MeEditActivity.actionStart(getView().getContext());
    }

    /**
     * 粉丝列表
     */
    @Override
    public void onClickFans() {
        Intent intent = new Intent();
        // 跳转到好友资料画面
        intent.setClass(getView().getContext(), MessageFriendFansListActivity.class);
        // 传递会员ID
        intent.putExtra("type", "1");
        getView().getContext().startActivity(intent);
    }

    /**
     * 关注列表
     */
    @Override
    public void onClickAttention() {
        Intent intent = new Intent();
        // 跳转到好友资料画面
        intent.setClass(getView().getContext(), MessageFriendFansListActivity.class);
        // 传递会员ID
        intent.putExtra("type", "2");
        getView().getContext().startActivity(intent);
    }

    @Override
    public void onClickMyTeam() {
        // 跳转到我的团队画面
        getView().paramStartActivity(MessageMyTeamActivity.class, LoginHelper.getInstance().getUserId());
    }

    @Override
    public void onClickMyWealth() {
        // 跳转到我的财富画面
        Intent i = new Intent(getView().getContext(), MessageWealthActivity.class);
        i.putExtra(Constant.INTENT_PARAM, LoginHelper.getInstance().getUserId());
        i.putExtra(Constant.INTENT_PARAM_ONE, LoginHelper.getInstance().getUserBean().getAvatar_url());
        getView().getContext().startActivity(i);
    }

    @Override
    public void onClickHeaderImg() {
        getView().clickHederImg();
    }

    @Override
    public void onClickFindFriends() {
        //跳转到发现好友页面
        getView().actionStartActivity(FindFriendsActivity.class);
    }


    @Override
    public void loadListData() {

    }

}

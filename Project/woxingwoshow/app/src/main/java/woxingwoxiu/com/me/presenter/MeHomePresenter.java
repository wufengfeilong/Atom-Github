package woxingwoxiu.com.me.presenter;


import woxingwoxiu.com.base.BasePresenter;
import woxingwoxiu.com.me.activity.FindFriendsActivity;
import woxingwoxiu.com.me.activity.MeEditActivity;
import woxingwoxiu.com.me.activity.SettingActivity;
import woxingwoxiu.com.me.contract.MeHomeContract;
import woxingwoxiu.com.message.activity.MessageFriendAttentionListActivity;
import woxingwoxiu.com.message.activity.MessageFriendFansListActivity;
import woxingwoxiu.com.message.activity.MessageMyTeamActivity;
import woxingwoxiu.com.message.activity.MessageWealthActivity;

/**
 * Created by 860116042 on 2018/5/16.
 */

public class MeHomePresenter extends BasePresenter<MeHomeContract.View> implements MeHomeContract.Presenter {
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
    @Override
    public void onClickFans() {
        // 跳转到粉丝画面
        getView().actionStartActivity(MessageFriendFansListActivity.class);
    }

    @Override
    public void onClickAttention() {
        // 跳转到关注画面
        getView().actionStartActivity(MessageFriendAttentionListActivity.class);
    }

    @Override
    public void onClickMyTeam() {
        // 跳转到我的团队画面
        getView().actionStartActivity(MessageMyTeamActivity.class);
    }

    @Override
    public void onClickMyWealth() {
        // 跳转到我的财富画面
        getView().actionStartActivity(MessageWealthActivity.class);
    }

    @Override
    public void onClickFindFriends() {
        //跳转到发现好友页面
        getView().actionStartActivity(FindFriendsActivity.class);
    }

//    List<MeHomeBean> mList=new ArrayList<>();
//    @Override
//    public void loadListData() {
//        mList.clear();
//        MeHomeBean bean1=new MeHomeBean();
//        bean1.setCommentNumber("1321");
//        mList.add(bean1);
//        MeHomeBean bean2=new MeHomeBean();
//        bean2.setCommentNumber("22312");
//        mList.add(bean2);
//        MeHomeBean bean3=new MeHomeBean();
//        bean3.setCommentNumber("3212");
//        mList.add(bean3);
//        MeHomeBean bean4=new MeHomeBean();
//        bean4.setCommentNumber("1321");
//        mList.add(bean4);
//        MeHomeBean bean5=new MeHomeBean();
//        bean5.setCommentNumber("22312");
//        mList.add(bean5);
//        MeHomeBean bean6=new MeHomeBean();
//        bean6.setCommentNumber("3212");
//        mList.add(bean6);
//        getView().bindListData(mList);
//    }

    @Override
    public void loadListData() {

    }
}

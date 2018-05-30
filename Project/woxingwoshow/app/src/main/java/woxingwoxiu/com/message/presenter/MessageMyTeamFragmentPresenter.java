package woxingwoxiu.com.message.presenter;

import java.util.ArrayList;
import java.util.List;

import woxingwoxiu.com.base.BasePresenter;
import woxingwoxiu.com.message.bean.MessageMyTeamBean;
import woxingwoxiu.com.message.contract.MessageMyTeamFragmentContract;

/**
 * 类描述：我的团队一级二级列表
 */

public class MessageMyTeamFragmentPresenter extends BasePresenter<MessageMyTeamFragmentContract.View> implements MessageMyTeamFragmentContract.Presenter{
    @Override
    public void loadListData() {
        List<MessageMyTeamBean> list = new ArrayList<>();
        MessageMyTeamBean bean = new MessageMyTeamBean();
        bean.setFriendTeamGrade(1);
        bean.setFriendName("老大");
        bean.setFriendRegTime("2018-01-02");
        bean.setFriendPostTimes("12");
        bean.setFriendActivities("234234");
        list.add(bean);
        MessageMyTeamBean bean1 = new MessageMyTeamBean();
        bean1.setFriendTeamGrade(1);
        bean1.setFriendName("老二");
        bean1.setFriendRegTime("2018-01-02");
        bean1.setFriendPostTimes("12");
        bean1.setFriendActivities("234234");
        list.add(bean1);
        MessageMyTeamBean bean2 = new MessageMyTeamBean();
        bean2.setFriendTeamGrade(1);
        bean2.setFriendName("老三");
        bean2.setFriendRegTime("2018-01-02");
        bean2.setFriendPostTimes("12");
        bean2.setFriendActivities("234234");
        list.add(bean2);
        MessageMyTeamBean bean3 = new MessageMyTeamBean();
        bean3.setFriendTeamGrade(1);
        bean3.setFriendName("老四");
        bean3.setFriendRegTime("2018-01-02");
        bean3.setFriendPostTimes("12");
        bean3.setFriendActivities("234234");
        list.add(bean3);
        getView().bindListData(list);
    }


}

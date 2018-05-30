package woxingwoxiu.com.message.presenter;

import java.util.ArrayList;
import java.util.List;

import woxingwoxiu.com.base.BasePresenter;
import woxingwoxiu.com.message.bean.MessageWealthBean;
import woxingwoxiu.com.message.contract.MessageWealthFragmentContract;

/**
 * Created by 860117066 on 2018/05/17.
 */

public class MessageWealthFragmentPresenter
        extends BasePresenter<MessageWealthFragmentContract.View> implements MessageWealthFragmentContract.Presenter {
    @Override
    public void loadListData() {
        List<MessageWealthBean> list = new ArrayList<>();
        MessageWealthBean bean = new MessageWealthBean();
        bean.setWealthTitle("发布小视频");
        bean.setWealthTime("2018-01-02");
        bean.setWealthCount("223");
        list.add(bean);
        MessageWealthBean bean1 = new MessageWealthBean();
        bean1.setWealthTitle("发布小视频1");
        bean1.setWealthTime("2018-01-02");
        bean1.setWealthCount("223");
        list.add(bean1);
        MessageWealthBean bean2 = new MessageWealthBean();
        bean2.setWealthTitle("发布小视频2");
        bean2.setWealthTime("2018-01-02");
        bean2.setWealthCount("223");
        list.add(bean2);
        MessageWealthBean bean3 = new MessageWealthBean();
        bean3.setWealthTitle("发布小视频3");
        bean3.setWealthTime("2018-01-02");
        bean3.setWealthCount("223");
        list.add(bean3);
        getView().bindListData(list);
    }


}

package woxingwoxiu.com.home.presenter;

import java.util.ArrayList;
import java.util.List;

import woxingwoxiu.com.base.BasePresenter;
import woxingwoxiu.com.home.bean.TopicVideoBean;
import woxingwoxiu.com.home.contract.TopicVideoListContract;

/**
 * Created by 860117073 on 2018/5/29.
 */

public class TopicVideoListPresenter extends BasePresenter<TopicVideoListContract.View> implements TopicVideoListContract.Presenter{
    List<TopicVideoBean> mList = new ArrayList<>();
    public void back(){
        //返回键操作
        getView().finishThis();
    }

    @Override
    public void loadListData() {
        mList.clear();
        TopicVideoBean bean1=new TopicVideoBean();
        bean1.setNickname("佩奇");
        bean1.setTitle("一只小猪佩奇,一只小猪佩奇,一只小猪佩奇,一只小猪佩奇");
        mList.add(bean1);
        TopicVideoBean bean2=new TopicVideoBean();
        bean2.setNickname("佩奇");
        bean2.setTitle("一只小猪佩奇");
        mList.add(bean2);
        TopicVideoBean bean3=new TopicVideoBean();
        bean3.setNickname("佩奇");
        bean3.setTitle("一只小猪佩奇");
        mList.add(bean3);
        TopicVideoBean bean4=new TopicVideoBean();
        bean4.setNickname("佩奇");
        bean4.setTitle("一只小猪佩奇");
        mList.add(bean4);
        TopicVideoBean bean5=new TopicVideoBean();
        bean5.setNickname("佩奇");
        bean5.setTitle("一只小猪佩奇");
        mList.add(bean5);
        TopicVideoBean bean6=new TopicVideoBean();
        bean6.setNickname("佩奇");
        bean6.setTitle("一只小猪佩奇");
        mList.add(bean6);
        TopicVideoBean bean7=new TopicVideoBean();
        bean7.setNickname("佩奇");
        bean7.setTitle("一只小猪佩奇");
        mList.add(bean7);
        TopicVideoBean bean8=new TopicVideoBean();
        bean8.setNickname("佩奇");
        bean8.setTitle("一只小猪佩奇");
        mList.add(bean8);
        TopicVideoBean bean9=new TopicVideoBean();
        bean9.setNickname("佩奇");
        bean9.setTitle("一只小猪佩奇");
        mList.add(bean9);
        getView().bindListData(mList);
    }
}

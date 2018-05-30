package woxingwoxiu.com.me.presenter;


import java.util.ArrayList;
import java.util.List;

import woxingwoxiu.com.base.BasePresenter;
import woxingwoxiu.com.me.bean.MeHomeBean;
import woxingwoxiu.com.me.contract.MeHomeFragmentContract;

/**
 * Created by 860116042 on 2018/5/16.
 */

public class MeHomeFragmentPresenter extends BasePresenter<MeHomeFragmentContract.View> implements MeHomeFragmentContract.Presenter {


    List<MeHomeBean> mList=new ArrayList<>();
    @Override
    public void loadListData() {
        mList.clear();
        MeHomeBean bean1=new MeHomeBean();
        bean1.setCommentNumber("1321");
        mList.add(bean1);
        MeHomeBean bean2=new MeHomeBean();
        bean2.setCommentNumber("22312");
        mList.add(bean2);
        MeHomeBean bean3=new MeHomeBean();
        bean3.setCommentNumber("3212");
        mList.add(bean3);
        MeHomeBean bean4=new MeHomeBean();
        bean4.setCommentNumber("1321");
        mList.add(bean4);
        MeHomeBean bean5=new MeHomeBean();
        bean5.setCommentNumber("22312");
        mList.add(bean5);
        MeHomeBean bean6=new MeHomeBean();
        bean6.setCommentNumber("3212");
        mList.add(bean6);
        getView().bindListData(mList);
    }

}

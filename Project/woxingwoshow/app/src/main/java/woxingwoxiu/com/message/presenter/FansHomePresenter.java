package woxingwoxiu.com.message.presenter;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import woxingwoxiu.com.base.BasePresenter;
import woxingwoxiu.com.message.activity.MessageFriendAttentionListActivity;
import woxingwoxiu.com.message.activity.MessageFriendFansListActivity;
import woxingwoxiu.com.message.activity.MessageMyTeamActivity;
import woxingwoxiu.com.message.activity.MessageWealthActivity;
import woxingwoxiu.com.message.bean.FansBean;
import woxingwoxiu.com.message.contract.FansHomeContract;

/**
 * Created by 860117073 on 2018/5/16.
 */

public class FansHomePresenter extends BasePresenter<FansHomeContract.View> implements FansHomeContract.Presenter {
    List<FansBean> mList = new ArrayList<>();

    @Override
    public void loadListData() {
        mList.clear();
        FansBean bean1=new FansBean();
        bean1.setCommentNumber("1321");
        mList.add(bean1);
        FansBean bean2=new FansBean();
        bean2.setCommentNumber("22312");
        mList.add(bean2);
        FansBean bean3=new FansBean();
        bean3.setCommentNumber("3212");
        mList.add(bean3);
        FansBean bean4=new FansBean();
        bean4.setCommentNumber("1321");
        mList.add(bean4);
        FansBean bean5=new FansBean();
        bean5.setCommentNumber("22312");
        mList.add(bean5);
        FansBean bean6=new FansBean();
        bean6.setCommentNumber("3212");
        mList.add(bean6);
        getView().bindListData(mList);
    }

    @Override
    public void getAttention() {
        ImageView getAttention =getView().getAttention();
        LinearLayout cancelAttention=getView().cancelAttention();
        FansBean bean=new FansBean();
        bean.setIsfollowed(true);
        if (bean.isIsfollowed()){
            getAttention.setVisibility(View.GONE);
            cancelAttention.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void cancelAttention() {
        ImageView getAttention =getView().getAttention();
        LinearLayout cancelAttention=getView().cancelAttention();
        FansBean bean=new FansBean();
        bean.setIsfollowed(false);
        if (!bean.isIsfollowed()){
            getAttention.setVisibility(View.VISIBLE);
            cancelAttention.setVisibility(View.GONE);
        }

    }
    public void onBack() {
        // 关闭当前的Activity，返回上一个画面
        getView().closeActivity();
    }

    @Override
    public void toFansAttention() {
        getView().actionStartActivity(MessageFriendAttentionListActivity.class);
    }

    @Override
    public void toFansList() {
        getView().actionStartActivity(MessageFriendFansListActivity.class);
    }

    @Override
    public void toMoney() {
        getView().actionStartActivity(MessageWealthActivity.class);
    }

    @Override
    public void toTeam() {
        getView().actionStartActivity(MessageMyTeamActivity.class);

    }
}

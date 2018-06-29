package com.fcn.park.me.presenter;

import android.support.v7.widget.RecyclerView;
import com.fcn.park.base.BasePresenter;
import com.fcn.park.base.utils.OnDataGetCallback;
import com.fcn.park.login.LoginHelper;
import com.fcn.park.me.activity.MeMyMsgDetailActivity;
import com.fcn.park.me.bean.MeMessageRecordBean;
import com.fcn.park.me.contract.MeMyMessageContract;
import com.fcn.park.me.module.MeMyMessageModule;

import java.util.List;

/**
 * create by 860115039
 * date      2018/04/23
 * time      13:07
 * 个人中心-我的消息列表presenter
 */
public class MeMyMessagePresenter extends BasePresenter<MeMyMessageContract.View> implements MeMyMessageContract.Presenter{

    private MeMyMessageModule mMyMessageModule;

    @Override
    public void attach(MeMyMessageContract.View view) {
        super.attach(view);
        mMyMessageModule = new MeMyMessageModule();
        createItemClickListener(getView().getRecyclerView());
    }
    /**
     * item被点击，跳转到消息详情
     */
    @Override
    public void onItemClick(RecyclerView.ViewHolder vh, int position) {
        String id = mMyMessageModule.getListData().get(position).getId();
        getView().jumpActivity(MeMyMsgDetailActivity.class,id);
    }
    /**
     * 加载更多
     */
    @Override
    public void onLoadMore(int page) {
        loadListData(page);
    }
    /**
     * 加载第一页数据
     */
    @Override
    public void loadListData() {
        getView().initRecyclerView();
        loadListData(1);
    }
    /**
     * 加载其他页数据
     */
    public void loadListData(int page) {
        mMyMessageModule.getList(getView().getContext(),LoginHelper.getInstance().getUserBean().getToken(),page,new OnDataGetCallback<List<MeMessageRecordBean.ListMessageBean>>() {
            @Override
            public void onSuccessResult(List<MeMessageRecordBean.ListMessageBean> listMessageBean) {
                getView().updateIsEnd(mMyMessageModule.isEnd());
                getView().bindListData(listMessageBean);
            }
        });
    }
}

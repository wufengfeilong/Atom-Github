package com.fcn.park.manager.presenter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.fcn.park.base.BasePresenter;
import com.fcn.park.base.utils.OnDataGetCallback;
import com.fcn.park.manager.activity.ManagerMessageReplyDetailActivity;
import com.fcn.park.manager.bean.ManagerMessageReplyListBean;
import com.fcn.park.manager.contract.ManagerMessageReplyListContract;
import com.fcn.park.manager.contract.ManagerMessageReplyListContract.Presenter;
import com.fcn.park.manager.module.ManagerMessageReplyListModule;

import java.util.List;

/**
 * Created by 丁胜胜 on 2018/04/25.
 * 类描述：管理中心的留言回复列表用Presenter
 */

public class ManagerMessageReplyListPresenter extends BasePresenter<ManagerMessageReplyListContract.View>
                implements Presenter{
    private String TAG = "=== ManagerMessageReplyListPresenter ===";
    private ManagerMessageReplyListModule mManagerMessageReplyListModule;

    @Override
    public void attach(ManagerMessageReplyListContract.View view){
        super.attach(view);
        mManagerMessageReplyListModule = new ManagerMessageReplyListModule();
        getView().initRecyclerView();
    }

    @Override
    public void loadListData() {
        loadListData(1);
    }

    /**
     *加载更多的数据
     * 只需要根据相应的页面加载相应的数据，不需要关心刷新和加载更多
     * @param page
     */
    @Override
    public void onLoadMore(int page) {
        loadListData(page);
    }

    /**
     * 加载列表数据
     * 调用Module中的requestMessageReplyList()
     */
    public void loadListData(int page) {
        mManagerMessageReplyListModule.requestMessageReplyList(getView().getContext(), page, new OnDataGetCallback<List<ManagerMessageReplyListBean.ManagerReplyBean>>() {
            @Override
            public void onSuccessResult(List<ManagerMessageReplyListBean.ManagerReplyBean> data) {

                getView().updateIsEnd(mManagerMessageReplyListModule.isEnd());
                getView().bindListData(data);
            }
        });
    }

    //item点击跳转到需求详情页面
    @Override
    public void onItemClick(RecyclerView.ViewHolder vh, int position) {
        String suggestionId = mManagerMessageReplyListModule.getListData().get(position).getSuggestionId();
        Log.d(TAG, "====== onItemClick() repairId = " + suggestionId);
        ManagerMessageReplyDetailActivity.actionStart(getView().getContext(), suggestionId);
    }
}

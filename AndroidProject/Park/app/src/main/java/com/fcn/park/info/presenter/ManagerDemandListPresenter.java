package com.fcn.park.info.presenter;

import android.support.v7.widget.RecyclerView;

import com.fcn.park.base.BasePresenter;
import com.fcn.park.base.utils.OnDataGetCallback;
import com.fcn.park.info.bean.ManagerDemandListBean;
import com.fcn.park.info.contract.ManagerDemandListContract;
import com.fcn.park.info.module.ManagerDemandListModule;
import com.fcn.park.info.view.ManagerDemandDetailActivity;

import java.util.List;

/**
 * Created by liuYq on 2018/04/23.
 * 企业需求列表页面用Presenter
 */

public class ManagerDemandListPresenter extends BasePresenter<ManagerDemandListContract.View> implements ManagerDemandListContract.Presenter {

    private ManagerDemandListModule mModule;

    @Override
    public void attach(ManagerDemandListContract.View view) {
        super.attach(view);
        mModule = new ManagerDemandListModule();
        createItemClickListener(getView().getRecyclerView());
    }

    /**
     * 加载更多，刷新时使用
     * @param page
     */
    @Override
    public void onLoadMore(int page) {
        loadListData(page);
    }

    @Override
    public void loadListData() {
        getView().initRecyclerView();
        loadListData(1);
    }

    public void loadListData(int page) {
        mModule.requestDemandList(page, getView().getContext(), new OnDataGetCallback<List<ManagerDemandListBean.DemandListBean>>() {
            @Override
            public void onSuccessResult(List<ManagerDemandListBean.DemandListBean> data) {
                getView().updateIsEnd(mModule.isEnd());
                getView().bindListData(data);
            }
        });
    }

    /**
     * 列表条目点击事件
     * @param vh
     * @param position
     */
    @Override
    public void onItemClick(RecyclerView.ViewHolder vh, int position) {
        //super.onItemClick(vh, position);
        ManagerDemandDetailActivity.actionStart(getView().getContext(), mModule.getDemandlist().get(position).getDemandId());

    }

    /**
     * 长按点击事件
     */
    @Override
    public void onItemLongClick() {
        final int position = getView().getClickPosition();
        if (position > mModule.getDemandlist().size()|| position < 0) return;
        final ManagerDemandListBean.DemandListBean listDemandBean = mModule.getDemandlist().get(position);
        mModule.deleteDemandListItem(getView().getContext(), listDemandBean.getDemandId(), new OnDataGetCallback<String>() {
            @Override
            public void onSuccessResult(String data) {
                getView().showToast(data);
                mModule.getDemandlist().remove(listDemandBean);
                getView().deleteListItem(position);
            }
        });
    }
}

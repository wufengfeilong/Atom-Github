package com.fcn.park.info.presenter;

import android.support.v7.widget.RecyclerView;

import com.fcn.park.base.BasePresenter;
import com.fcn.park.base.utils.OnDataGetCallback;
import com.fcn.park.info.bean.DemandListBean;
import com.fcn.park.info.contract.InfoDemandListContract;
import com.fcn.park.info.module.InfoDemandListModule;
import com.fcn.park.info.view.InfoDemandDetailActivity;

import java.util.List;

/**
 * Created by liuyq on 2018/04/19.
 * 企业需求列表presenter
 */

public class InfoDemandListPresenter extends BasePresenter<InfoDemandListContract.View> implements InfoDemandListContract.Presenter {

    private InfoDemandListModule mModule;

    @Override
    public void attach(InfoDemandListContract.View view) {
        super.attach(view);
        mModule = new InfoDemandListModule();
        getView().initRecyclerView();
    }

    /**
     * 加载更多
     *
     * @param page
     */
    @Override
    public void onLoadMore(int page) {

        loadListData(page);
    }

    /**
     * 加载数据
     */
    @Override
    public void loadListData() {

        loadListData(1);
    }

    public void loadListData(int page) {
        mModule.requestDemandList(page, getView().getContext(), new OnDataGetCallback<List<DemandListBean.DemandsListBean>>() {
            @Override
            public void onSuccessResult(List<DemandListBean.DemandsListBean> data) {
                getView().updateIsEnd(mModule.isEnd());
                getView().bindListData(data);
            }
        });
    }

    /**
     * item点击跳转到需求详情页面
     *
     * @param vh
     * @param position
     */
    @Override
    public void onItemClick(RecyclerView.ViewHolder vh, int position) {
        //super.onItemClick(vh, position);
        InfoDemandDetailActivity.actionStart(getView().getContext(), mModule.getDemandlist().get(position).getDemandId());
    }
}

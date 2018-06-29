package com.fcn.park.info.presenter;

import android.support.v7.widget.RecyclerView;

import com.fcn.park.base.BasePresenter;
import com.fcn.park.base.utils.OnDataGetCallback;
import com.fcn.park.info.bean.EnterpriseListBean;
import com.fcn.park.info.contract.InfoEnterpriseListContract;
import com.fcn.park.info.module.InfoEnterpriseListModule;
import com.fcn.park.info.view.InfoEnterpriseDetailActivity;

import java.util.List;


/**
 * Created by liuyq on 2017/04/16.
 * 类描述：企业列表使用presenter
 */

public class InfoEnterpriseListPresenter extends BasePresenter<InfoEnterpriseListContract.View> implements InfoEnterpriseListContract.Presenter {

    private InfoEnterpriseListModule mModule;

    @Override
    public void attach(InfoEnterpriseListContract.View view) {
        super.attach(view);
        mModule = new InfoEnterpriseListModule();
        //  createItemClickListener(getView().getRecyclerView());
        getView().initRecyclerView();
    }

    @Override
    public void loadListData() {
        // getView().initRecyclerView();
        loadListData(1);
    }

    /**
     * 加载更多的数据
     * 只需要根据相应的页码加载相应的数据，无需关心刷新和加载更多
     *
     * @param page
     */
    @Override
    public void onLoadMore(int page) {
        loadListData(page);
    }

    public void loadListData(int page) {
        mModule.requestEnterpriseList(page, getView().getContext(), new OnDataGetCallback<List<EnterpriseListBean.BusinesslistBean>>() {
            @Override
            public void onSuccessResult(List<EnterpriseListBean.BusinesslistBean> data) {
                getView().updateIsEnd(mModule.isEnd());
                getView().bindListData(data);
            }
        });
    }

    /**
     * 条目点击使用
     *
     * @param vh
     * @param position
     */
    @Override
    public void onItemClick(RecyclerView.ViewHolder vh, int position) {
        InfoEnterpriseDetailActivity.actionStart(getView().getContext(), mModule.getEnterpriseList().get(position).getCompanyId());
    }
}

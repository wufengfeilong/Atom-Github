package com.fcn.park.home.advertisement.presenter;

import android.support.v7.widget.RecyclerView;

import com.fcn.park.base.BasePresenter;
import com.fcn.park.base.utils.OnDataGetCallback;
import com.fcn.park.home.advertisement.contract.AdvertisementListContract;
import com.fcn.park.manager.bean.ManagerAdvertisingApprovalListBean;
import com.fcn.park.manager.module.ManagerAdvertisingListModule;

import java.util.List;

/**
 * Created by 860115001 on 2018/04/27.
 */

public class AdvertisementListPresenter extends BasePresenter<AdvertisementListContract.View> implements AdvertisementListContract.Presenter {

    private ManagerAdvertisingListModule mManagerAdvertisingListModule;

    @Override
    public void attach(AdvertisementListContract.View view) {
        super.attach(view);
        mManagerAdvertisingListModule = new ManagerAdvertisingListModule();
        getView().initRecyclerView();
    }

    /**
     * 加载页面数据
     */
    @Override
    public void loadListData() {
        loadListData(1);
    }

    public void loadListData(int page) {
        //获取广告列表
        mManagerAdvertisingListModule.getAdvertisingList(getView().getContext(), page, new OnDataGetCallback<List<ManagerAdvertisingApprovalListBean.ListAdvertisingApprovalBean>>() {
            @Override
            public void onSuccessResult(List<ManagerAdvertisingApprovalListBean.ListAdvertisingApprovalBean> listAdvertisingBean) {
                getView().updateIsEnd(mManagerAdvertisingListModule.isEnd());
                getView().bindListData(listAdvertisingBean);
            }
        });
    }

    /**
     * Item点击事件
     * @param vh
     * @param position
     */
    @Override
    public void onItemClick(RecyclerView.ViewHolder vh, int position) {
//        String advertisingId = mManagerAdvertisingListModule.getAdvertisingList().get(position).getAdvertisingId();
//        ManagerAdvertisingApprovalDetailActivity.actionStart(getView().getContext(), advertisingId);
    }
}

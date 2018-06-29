package com.fcn.park.me.presenter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.fcn.park.base.BasePresenter;
import com.fcn.park.base.utils.OnDataGetCallback;
import com.fcn.park.login.LoginHelper;
import com.fcn.park.me.activity.MeRepairRecordDetailActivity;
import com.fcn.park.me.activity.MeRepairRecordListActivity;
import com.fcn.park.me.bean.RepairRecordBean;
import com.fcn.park.me.contract.MeRepairRecordContract;
import com.fcn.park.me.contract.MeRepairRecordDetailContract;
import com.fcn.park.me.module.MeRepairRecordModule;

import java.util.List;

/**
 * 类描述：报修列表用Presenter.
 */

public class MeRepairRecordPresenter extends BasePresenter<MeRepairRecordContract.View> implements MeRepairRecordContract.Presenter{
    private MeRepairRecordModule mMeRepairRecordModule;


    private String TAG = "=== MeRepairRecordPresenter ===";

    @Override
    public void attach(MeRepairRecordContract.View view) {
        super.attach(view);
        mMeRepairRecordModule = new MeRepairRecordModule();
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
    public void loadListData(int page ) {
        mMeRepairRecordModule.getList(getView().getContext(),page, LoginHelper.getInstance().getUserBean().getToken(),new OnDataGetCallback<List<RepairRecordBean.ListRecordBean>>() {
            @Override
            public void onSuccessResult(List<RepairRecordBean.ListRecordBean> listRecordBean) {
                getView().updateIsEnd(mMeRepairRecordModule.isEnd());
                getView().bindListData(listRecordBean);
            }
        });
    }
    @Override
    public void onItemClick(RecyclerView.ViewHolder vh, int position) {
        String repairId = mMeRepairRecordModule.getListData().get(position).getRepairId();
        MeRepairRecordListActivity.actionStart(getView().getContext(), repairId);

    }

    @Override
    public void onItemLongClick() {
    }
}


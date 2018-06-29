package com.fcn.park.manager.presenter;

import android.support.v7.widget.RecyclerView;
import com.fcn.park.base.BasePresenter;
import com.fcn.park.manager.contract.ManagerEnterpriseCheckListContract;
import com.fcn.park.manager.module.ManagerEnterpriseCheckListModule;

/**
 * 管理中心的企业审核管理功能用Presenter
 */
public class ManagerEnterpriseCheckListPresenter extends BasePresenter<ManagerEnterpriseCheckListContract.View> implements ManagerEnterpriseCheckListContract.Presenter {

    private ManagerEnterpriseCheckListModule managerEnterpriseCheckListModule;
    @Override
    public void attach(ManagerEnterpriseCheckListContract.View view) {
        super.attach(view);
        managerEnterpriseCheckListModule = new ManagerEnterpriseCheckListModule();
    }

    /**
     * 加载数据列表
     */
    @Override
    public void loadListData() {
    }

    /**
     * 加载更多
     * @param page
     */
    @Override
    public void onLoadMore(int page) {
        loadListData(page);
    }

    /**
     * 加载数据列表
     * @param page
     */
    private void loadListData(int page) {
    }

    @Override
    public void onItemClick(RecyclerView.ViewHolder vh, int position) {

    }

}
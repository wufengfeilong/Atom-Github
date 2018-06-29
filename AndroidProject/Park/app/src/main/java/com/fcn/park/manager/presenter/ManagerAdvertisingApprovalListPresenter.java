package com.fcn.park.manager.presenter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.fcn.park.base.BasePresenter;
import com.fcn.park.base.utils.OnDataGetCallback;
import com.fcn.park.manager.activity.ManagerAdvertisingApprovalDetailActivity;
import com.fcn.park.manager.bean.ManagerAdvertisingApprovalListBean;
import com.fcn.park.manager.contract.ManagerAdvertisingApprovalListContract;
import com.fcn.park.manager.module.ManagerAdvertisingApprovalListModule;

import java.util.List;

/**
 * 广告位待审核列表用Presenter.
 */
public class ManagerAdvertisingApprovalListPresenter
        extends BasePresenter<ManagerAdvertisingApprovalListContract.View>
        implements ManagerAdvertisingApprovalListContract.Presenter {

    private String TAG = "=== ManagerAdvertisingApprovalListPresenter ===";
    private ManagerAdvertisingApprovalListModule mManagerAdvertisingApprovalModule;

    @Override
    public void attach(ManagerAdvertisingApprovalListContract.View view) {
        super.attach(view);
        mManagerAdvertisingApprovalModule = new ManagerAdvertisingApprovalListModule();
        createItemClickListener(getView().getRecyclerView());
        getView().initRecyclerView();
    }

    @Override
    public void loadListData() {
        loadListData( 1);
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

    /**
     * 加载广告位待审核的列表数据
     * 调用Module中的getAdvertisingApprovalList()
     */
    public void loadListData(int page) {
        Log.d(TAG, "====== loadListData() Start =====");
        mManagerAdvertisingApprovalModule.getAdvertisingApprovalList(getView().getContext(), page, new OnDataGetCallback<List<ManagerAdvertisingApprovalListBean.ListAdvertisingApprovalBean>>() {
            @Override
            public void onSuccessResult(List<ManagerAdvertisingApprovalListBean.ListAdvertisingApprovalBean> listAdvertisingApprovalBean) {
                getView().bindListData(listAdvertisingApprovalBean);
            }
        });
        Log.d(TAG, "====== loadListData() End =====");
    }

    /**
     * 点击列表画面上的条目，进入详情画面
     * @param vh
     * @param position
     */
    @Override
    public void onItemClick(RecyclerView.ViewHolder vh, int position) {
        Log.d(TAG, "====== onItemClick() position = " + position);
        String advertisingId = mManagerAdvertisingApprovalModule.getAdvertisingApprovalList().get(position).getAdvertisingId();
        Log.d(TAG, "====== onItemClick() advertisingId = " + advertisingId);
        // 通过广告Id，启动待审核广告详情画面
        ManagerAdvertisingApprovalDetailActivity.actionStart(getView().getContext(), advertisingId);
    }
}
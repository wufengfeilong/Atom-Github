package com.fcn.park.manager.presenter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.fcn.park.base.BasePresenter;
import com.fcn.park.base.utils.OnDataGetCallback;
import com.fcn.park.manager.activity.ManagerAdvertisingDetailActivity;
import com.fcn.park.manager.bean.ManagerAdvertisingApprovalListBean;
import com.fcn.park.manager.contract.ManagerAdvertisingListContract;
import com.fcn.park.manager.module.ManagerAdvertisingListModule;

import java.util.List;

/**
 * 广告位已审核列表用Presenter.
 */
public class ManagerAdvertisingListPresenter
        extends BasePresenter<ManagerAdvertisingListContract.View>
        implements ManagerAdvertisingListContract.Presenter {

    private String TAG = "=== ManagerAdvertisingApprovalListPresenter ===";
    private ManagerAdvertisingListModule mManagerAdvertisingListModule;

    @Override
    public void attach(ManagerAdvertisingListContract.View view) {
        super.attach(view);
        mManagerAdvertisingListModule = new ManagerAdvertisingListModule();
        createItemClickListener(getView().getRecyclerView());
        getView().initRecyclerView();
    }

    @Override
    public void loadListData() {
        loadListData(1);
    }

    public void loadListData(int page) {
        Log.d(TAG, "====== loadListData() Start ======");
        mManagerAdvertisingListModule.getAdvertisingList(getView().getContext(), page, new OnDataGetCallback<List<ManagerAdvertisingApprovalListBean.ListAdvertisingApprovalBean>>() {
            @Override
            public void onSuccessResult(List<ManagerAdvertisingApprovalListBean.ListAdvertisingApprovalBean> listAdvertisingBean) {
                Log.d(TAG, "====== loadListData() listAdvertisingBean = " + listAdvertisingBean);
                getView().bindListData(listAdvertisingBean);
            }
        });
        Log.d(TAG, "====== loadListData() End ======");
    }

    @Override
    public void onItemClick(RecyclerView.ViewHolder vh, int position) {
        Log.d(TAG, "====== onItemClick() position = " + position);
        String advertisingId = mManagerAdvertisingListModule.getAdvertisingList().get(position).getAdvertisingId();
        Log.d(TAG, "====== onItemClick() advertisingId = " + advertisingId);
        ManagerAdvertisingDetailActivity.actionStart(getView().getContext(), advertisingId);
    }
}

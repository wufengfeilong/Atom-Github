package com.fcn.park.manager.presenter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.fcn.park.base.BasePresenter;
import com.fcn.park.base.utils.OnDataGetCallback;
import com.fcn.park.manager.activity.ManagerRepairsDetailActivity;
import com.fcn.park.manager.bean.ManagerRepairsListBean;
import com.fcn.park.manager.contract.ManagerRepairsDetailContract;
import com.fcn.park.manager.contract.ManagerRepairsListContract;
import com.fcn.park.manager.module.ManagerRepairsListModule;

import java.util.List;

/**
 * Created by 丁胜胜 on 2018/04/12.
 * 类描述：管理中心报修一览Presenter
 */

public class ManagerRepairsListPresenter extends BasePresenter<ManagerRepairsListContract.View> implements ManagerRepairsListContract.Presenter{

    private String TAG = "=== ManagerRepairsListPresenter ===";
    private ManagerRepairsListModule mManagerRepairsListModule;


    @Override
    public void attach(ManagerRepairsListContract.View view){
        super.attach(view);
        mManagerRepairsListModule = new ManagerRepairsListModule();
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

    public void loadListData(int page) {
        //绑定数据
        mManagerRepairsListModule.requestRepairsList(getView().getContext(), page, new OnDataGetCallback<List<ManagerRepairsListBean.RepairsListBean>>() {
            @Override
            public void onSuccessResult(List<ManagerRepairsListBean.RepairsListBean> data) {
                Log.d(TAG,"绑定数据 " + data);
                getView().updateIsEnd(mManagerRepairsListModule.isEnd());
                getView().bindListData(data);
            }
        });
    }

    //item点击跳转到需求详情页面
    @Override
    public void onItemClick(RecyclerView.ViewHolder vh, int position) {
        String repairId = mManagerRepairsListModule.getListData().get(position).getRepairId();
        String repairPhone = mManagerRepairsListModule.getListData().get(position).getRepairPhone();
        Log.d(TAG, "====== onItemClick() repairId  onItemClick() repairPhone = " + repairId +repairPhone);
        ManagerRepairsDetailActivity.actionStart(getView().getContext(),repairId,repairPhone);
    }

}

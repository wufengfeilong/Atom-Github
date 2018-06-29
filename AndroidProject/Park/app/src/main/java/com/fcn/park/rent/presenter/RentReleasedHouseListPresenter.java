package com.fcn.park.rent.presenter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import com.fcn.park.base.BasePresenter;
import com.fcn.park.base.utils.OnDataGetCallback;
import com.fcn.park.rent.activity.RentHouseDetailActivity;
import com.fcn.park.rent.bean.RentReleasedHouseListBean;
import com.fcn.park.rent.contract.RentReleasedHouseListContract;
import com.fcn.park.rent.module.RentReleasedHouseModule;

import java.util.List;

/**
 * Created by 860117066 on 2018/04/25.
 */

public class RentReleasedHouseListPresenter extends BasePresenter<RentReleasedHouseListContract.View> implements RentReleasedHouseListContract.Presenter{

    private String TAG = "=== RentReleasedHouseListPresenter ===";

    private RentReleasedHouseModule mRentReleasedHouseModule;
    @Override
    public void attach(RentReleasedHouseListContract.View view) {
        super.attach(view);
        mRentReleasedHouseModule = new RentReleasedHouseModule();
        createItemClickListener(getView().getRecyclerView());
        getView().initRecyclerView();
    }

    @Override
    public void loadListData() {
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

    /**
     * 最新租赁Item点击事件
     * @param vh
     * @param position
     */
    @Override
    public void onItemClick(RecyclerView.ViewHolder vh, int position) {
        Log.d(TAG, "====== onItemClick() position = " + position);
        int HouseId = mRentReleasedHouseModule.getHouseReleasedList().get(position).getId();

        RentHouseDetailActivity.actionStart(getView().getContext(), String.valueOf(HouseId));
    }

    /**
     * 加载数据
     * @param page
     */
    public void loadListData(int page) {
        mRentReleasedHouseModule.requestRentReleasedHouseList(getView().getContext(), page, new OnDataGetCallback<List<RentReleasedHouseListBean.ListReleasedHouseBean>>() {
            @Override
            public void onSuccessResult(List<RentReleasedHouseListBean.ListReleasedHouseBean> listReleasedHouseBean) {

                getView().updateIsEnd(mRentReleasedHouseModule.isEnd());
                getView().bindListData(listReleasedHouseBean);
            }
        });
    }
}

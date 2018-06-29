package com.fcn.park.rent.presenter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import com.fcn.park.base.BasePresenter;
import com.fcn.park.base.utils.OnDataGetCallback;
import com.fcn.park.rent.activity.RentAddActivity;
import com.fcn.park.rent.activity.RentHouseDetailActivity;
import com.fcn.park.rent.bean.RentHouseListBean;
import com.fcn.park.rent.contract.RentHouseListContract;
import com.fcn.park.rent.module.RentModule;
import java.util.List;

/**
 * 房屋列表用Presenter.
 */

public class RentHousePresenter extends BasePresenter<RentHouseListContract.View> implements RentHouseListContract.Presenter {

    private String TAG = "=== RentHousePresenter ===";
    private RentModule rentModule;

    @Override
    public void attach(RentHouseListContract.View view) {
        super.attach(view);
        rentModule = new RentModule();
        createItemClickListener(getView().getRecyclerView());
    }

    /**
     * 加载列表信息
     */
    @Override
    public void loadListData() {
        getView().initRecyclerView();
        loadListData(getView().getFromId(), 1);
    }


    /**
     * 加载更多的数据
     * 只需要根据相应的页码加载相应的数据，无需关心刷新和加载更多
     *
     * @param page
     */
    public void onLoadMore(int page) {
        loadListData(getView().getFromId(), page);
    }

    private void loadListData(String goRent, int page) {

        Log.d(TAG, "====== loadListData() page = " + page);
        rentModule.requestRentHouseList(getView().getContext(), goRent, page, new OnDataGetCallback<List<RentHouseListBean.ListHouseBean>>() {
            @Override
            public void onSuccessResult(List<RentHouseListBean.ListHouseBean> listHouseBean) {
                getView().updateIsEnd(rentModule.isEnd());
                getView().bindListData(listHouseBean);
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
        Log.d(TAG, "====== onItemClick() position = " + position);
        int HouseId = rentModule.getHouseList().get(position).getId();
        RentHouseDetailActivity.actionStart(getView().getContext(), String.valueOf(HouseId));
    }

    /**
     * 房屋添加按钮点击事件
     */
    @Override
    public void houseAdd() {
        Intent intent = new Intent(getView().getContext(), RentAddActivity.class);
        getView().getContext().startActivity(intent);

    }
}

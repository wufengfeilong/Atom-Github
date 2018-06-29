package com.fcn.park.manager.presenter;

import com.fcn.park.base.BasePresenter;
import com.fcn.park.base.http.HttpResult;
import com.fcn.park.base.http.ProgressSubscriber;
import com.fcn.park.base.http.RequestImpl;
import com.fcn.park.base.utils.OnDataGetCallback;
import com.fcn.park.manager.activity.WaterFeeDetailActivity;
import com.fcn.park.manager.bean.WaterFeeListBean;
import com.fcn.park.manager.contract.WaterFeeListContract;
import com.fcn.park.manager.module.WaterFeeListModule;

import java.util.List;

/**
 * 水费列表用Presenter.
 */

public class WaterFeeListPresenter extends BasePresenter<WaterFeeListContract.View> implements WaterFeeListContract.Presenter {

    private WaterFeeListModule mWaterFeeListModule;

    @Override
    public void attach(WaterFeeListContract.View view) {
        super.attach(view);
        mWaterFeeListModule = new WaterFeeListModule();
        getView().initRecyclerView();
    }

    @Override
    public void loadListData() {
        onLoadMore(1);
    }

    /**
     * 加载更多的数据
     * 只需要根据相应的页码加载相应的数据，无需关心刷新和加载更多
     *
     * @param page
     */
    @Override
    public void onLoadMore(int page) {
        mWaterFeeListModule.requestWaterFeeList(getView().getContext(), page, new OnDataGetCallback<List<WaterFeeListBean.ListWaterBean>>() {
            @Override
            public void onSuccessResult(List<WaterFeeListBean.ListWaterBean> data) {
                getView().updateIsEnd(mWaterFeeListModule.isEnd());
                getView().bindListData(data);
            }
        });
    }

    /**
     * 水费条目点击事件
     */
    @Override
    public void onItemClick() {
        int position = getView().getClickPosition();
        WaterFeeListBean.ListWaterBean bean = mWaterFeeListModule.getListData().get(position);
        WaterFeeDetailActivity.actionStart(getView().getContext(), bean);
    }

    /**
     * 催缴费按钮点击事件
     */
    @Override
    public void onMailSendClick() {
        int position = getView().getClickPosition();
        WaterFeeListBean.ListWaterBean bean = mWaterFeeListModule.getListData().get(position);
        String recordDate = bean.getRecordDate();
        String[] recordDates = recordDate.split("-");
        recordDate = recordDates[0] + "年" + recordDates[1] + "月" + recordDates[2] + "日";

        mWaterFeeListModule.sendWaterFeeMail(bean.getCompanyMail(), recordDate, bean.getFee(), new ProgressSubscriber<HttpResult<String>>(getView().getContext(),
                new RequestImpl<HttpResult<String>>() {
                    @Override
                    public void onNext(HttpResult<String> result) {
                        if (result.isResult()) {
                            getView().showToast("发送成功");
                        }
                    }
                }));
    }
}
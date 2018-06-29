package com.fcn.park.manager.presenter;

import com.fcn.park.base.BasePresenter;
import com.fcn.park.base.http.HttpResult;
import com.fcn.park.base.http.ProgressSubscriber;
import com.fcn.park.base.http.RequestImpl;
import com.fcn.park.base.utils.OnDataGetCallback;
import com.fcn.park.manager.activity.ElectricFeeDetailActivity;
import com.fcn.park.manager.bean.ElectricFeeListBean;
import com.fcn.park.manager.contract.ElectricFeeListContract;
import com.fcn.park.manager.module.ElectricFeeListModule;

import java.util.List;

/**
 * 电费列表用Presenter.
 */

public class ElectricFeeListPresenter extends BasePresenter<ElectricFeeListContract.View> implements ElectricFeeListContract.Presenter {

    private ElectricFeeListModule mElectricFeeListModule;

    @Override
    public void attach(ElectricFeeListContract.View view) {
        super.attach(view);
        mElectricFeeListModule = new ElectricFeeListModule();
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
        mElectricFeeListModule.requestElectricFeeList(getView().getContext(), page, new OnDataGetCallback<List<ElectricFeeListBean.ListElectricBean>>() {
            @Override
            public void onSuccessResult(List<ElectricFeeListBean.ListElectricBean> data) {
                getView().updateIsEnd(mElectricFeeListModule.isEnd());
                getView().bindListData(data);
            }
        });
    }

    /**
     * 电费条目点击事件
     */
    @Override
    public void onItemClick() {
        int position = getView().getClickPosition();
        ElectricFeeListBean.ListElectricBean bean = mElectricFeeListModule.getListData().get(position);
        ElectricFeeDetailActivity.actionStart(getView().getContext(), bean);
    }

    /**
     * 催缴费按钮点击事件
     */
    @Override
    public void onMailSendClick() {
        int position = getView().getClickPosition();
        ElectricFeeListBean.ListElectricBean bean = mElectricFeeListModule.getListData().get(position);
        String recordDate = bean.getRecordDate();
        String[] recordDates = recordDate.split("-");
        recordDate = recordDates[0] + "年" + recordDates[1] + "月" + recordDates[2] + "日";

        mElectricFeeListModule.sendElectricFeeMail(bean.getCompanyMail(), recordDate, bean.getFee(), new ProgressSubscriber<HttpResult<String >>(getView().getContext(),
                new RequestImpl<HttpResult<String>>() {
                    @Override
                    public void onNext(HttpResult<String > result) {
                        if (result.isResult()) {
                            getView().showToast("发送成功");
                        }
                    }
                }));
    }
}

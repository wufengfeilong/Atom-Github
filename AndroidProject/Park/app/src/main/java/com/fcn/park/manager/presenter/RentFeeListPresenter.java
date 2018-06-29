package com.fcn.park.manager.presenter;

import com.fcn.park.base.BasePresenter;
import com.fcn.park.base.http.HttpResult;
import com.fcn.park.base.http.ProgressSubscriber;
import com.fcn.park.base.http.RequestImpl;
import com.fcn.park.base.interfacee.OnDataCallback;
import com.fcn.park.base.utils.OnDataGetCallback;
import com.fcn.park.manager.activity.RentFeeDetailActivity;
import com.fcn.park.manager.bean.RentFeeListBean;
import com.fcn.park.manager.contract.RentFeeListContract;
import com.fcn.park.manager.module.RentFeeListModule;

import java.util.List;

/**
 * 租赁费列表用Presenter.
 */
public class RentFeeListPresenter
        extends BasePresenter<RentFeeListContract.View>
        implements RentFeeListContract.Presenter {

    private RentFeeListModule mRentFeeListModule;

    @Override
    public void attach(RentFeeListContract.View view) {
        super.attach(view);
        mRentFeeListModule = new RentFeeListModule();
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
        mRentFeeListModule.requestRentFeeList(getView().getContext(), page, new OnDataGetCallback<List<RentFeeListBean.ListRentBean>>() {
            @Override
            public void onSuccessResult(List<RentFeeListBean.ListRentBean> data) {
                getView().updateIsEnd(mRentFeeListModule.isEnd());
                getView().bindListData(data);
            }
        });
    }

    /**
     * 物业费条目点击事件
     */
    @Override
    public void onItemClick() {
        int position = getView().getClickPosition();
        RentFeeListBean.ListRentBean bean = mRentFeeListModule.getListData().get(position);
//        // 物业费ID
//        String propertyFeeId = bean.getPropertyFeeId();
//        // 建筑面积
//        String companyName = bean.getCompanyName();
//        // 物业费单价
//        String unitPrice = bean.getUnitPrice();
//        // 物业费折扣
//        String discount = bean.getDiscount();
//        // 物业费
//        String fee = bean.getFee();
//        // 备注
//        String comment = bean.getComment();
        RentFeeDetailActivity.actionStart(getView().getContext(), bean);
    }

    /**
     * 催缴费按钮点击事件
     */
    @Override
    public void onMailSendClick() {
        int position = getView().getClickPosition();
        RentFeeListBean.ListRentBean bean = mRentFeeListModule.getListData().get(position);
        String startDate = bean.getStartDate();
        int endIndex = startDate.lastIndexOf("-");
        startDate = startDate.substring(0, endIndex).replace("-", "年");
        startDate = startDate + "月";

        String endDate = bean.getEndDate();
        endDate = endDate.substring(0, endIndex).replace("-", "年");
        endDate = endDate + "月";

        mRentFeeListModule.sendRentFeeMail(bean.getCompanyMail(), startDate, endDate, bean.getFee(), new ProgressSubscriber<HttpResult<String>>(getView().getContext(),
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
package com.fcn.park.manager.presenter;

import com.fcn.park.base.BasePresenter;
import com.fcn.park.base.http.HttpResult;
import com.fcn.park.base.utils.OnDataGetCallback;
import com.fcn.park.manager.activity.PropertyFeeDetailActivity;
import com.fcn.park.manager.bean.PropertyFeeListBean;
import com.fcn.park.manager.contract.PropertyFeeListContract;
import com.fcn.park.manager.module.PropertyFeeListModule;
import com.fcn.park.base.http.RequestImpl;
import com.fcn.park.base.http.ProgressSubscriber;

import java.util.List;

/**
 * 物业费列表用Presenter.
 */

public class PropertyFeeListPresenter
        extends BasePresenter<PropertyFeeListContract.View>
        implements PropertyFeeListContract.Presenter {

    private PropertyFeeListModule mPropertyFeeListModule;

    @Override
    public void attach(PropertyFeeListContract.View view) {
        super.attach(view);
        mPropertyFeeListModule = new PropertyFeeListModule();
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
        mPropertyFeeListModule.requestPropertyFeeList(getView().getContext(), page, new OnDataGetCallback<List<PropertyFeeListBean.ListPropertyBean>>() {
            @Override
            public void onSuccessResult(List<PropertyFeeListBean.ListPropertyBean> data) {
                getView().updateIsEnd(mPropertyFeeListModule.isEnd());
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
        PropertyFeeListBean.ListPropertyBean bean = mPropertyFeeListModule.getListData().get(position);
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
        PropertyFeeDetailActivity.actionStart(getView().getContext(), bean);
    }

    /**
     * 催缴费按钮点击事件
     */
    @Override
    public void onMailSendClick() {
        int position = getView().getClickPosition();
        PropertyFeeListBean.ListPropertyBean bean = mPropertyFeeListModule.getListData().get(position);
        String startDate = bean.getStartDate();
        int endIndex = startDate.lastIndexOf("-");
        startDate = startDate.substring(0, endIndex).replace("-", "年");
        startDate = startDate + "月";

        String endDate = bean.getEndDate();
        endDate = endDate.substring(0, endIndex).replace("-", "年");
        endDate = endDate + "月";

        mPropertyFeeListModule.sendPropertyFeeMail(bean.getCompanyMail(), startDate, endDate, bean.getFee(), new ProgressSubscriber<>(getView().getContext(), new RequestImpl<HttpResult<String>>() {
            @Override
            public void onNext(HttpResult<String> result) {
                if (result.isResult()) {
                    getView().showToast("发送成功");
                }
            }
        }));
    }
}
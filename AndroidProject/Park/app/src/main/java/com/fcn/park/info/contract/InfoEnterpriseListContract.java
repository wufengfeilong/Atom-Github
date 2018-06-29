package com.fcn.park.info.contract;


import com.fcn.park.base.BaseView;
import com.fcn.park.base.contract.PullToRefeshContract;
import com.fcn.park.base.contract.RecyclerViewContract;
import com.fcn.park.info.bean.EnterpriseListBean;

/**
 * Created by liuyq on 2018/04/16.
 * 类描述：企业列表使用contract
 */

public interface InfoEnterpriseListContract {

    interface View extends BaseView, RecyclerViewContract.View<EnterpriseListBean.BusinesslistBean>, PullToRefeshContract.View {
    }

    interface Presenter extends RecyclerViewContract.Presenter, PullToRefeshContract.Presenter {
    }

}

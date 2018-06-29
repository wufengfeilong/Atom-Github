package com.fcn.park.manager.contract;

import com.fcn.park.base.BaseView;
import com.fcn.park.manager.bean.ManagerAdvertisingApprovalBean;

/**
 * 广告位详情审批用Contract.
 */
public interface ManagerAdvertisingApprovalContract {

    interface View extends BaseView {

        // 接口：将后台返回的数据data，显示到画面上
        void showDataToView(ManagerAdvertisingApprovalBean bean);

        // 接口：获取输入的拒绝的理由
        String getInputRefuseData();

        // 接口：获取申请广告位的用户的Id
        String getUserId();

        // 接口：获取申请广告位的用户需要缴纳广告费的标题
        String getMsgTitle();

        // 接口：获取申请广告位的用户需要缴纳广告费的说明内容
        String getMsgContent(boolean isOK);
    }

    interface Presenter {

        // 接口：通过广告Id获取广告的详情内容
        void getAdvertisingInfoById(String advertisingId);

        void passOnClick();
        void refuseOnClick();
    }
}

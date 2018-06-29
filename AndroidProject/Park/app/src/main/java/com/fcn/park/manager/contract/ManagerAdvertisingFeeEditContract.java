package com.fcn.park.manager.contract;

import com.fcn.park.base.BaseView;
import com.fcn.park.manager.bean.ManagerAdvertisingFeeEditBean;

import java.util.List;

/**
 * 广告费用编辑用Contract.
 */
public interface ManagerAdvertisingFeeEditContract {

    interface View extends BaseView {

        // 获取套餐一（一个月）的费用
        String getInputOffer1Fee();
        // 获取套餐二（三个月）的费用
        String getInputOffer2Fee();
        // 获取套餐三（一年）的费用
        String getInputOffer3Fee();

        // 将从服务器
        void showDataToView(List<ManagerAdvertisingFeeEditBean.AdvertisingFeeList> bean);
    }

    interface Presenter {
        // “确定”按钮，提交设置的广告费用内容
        void advertisingFeeSubmitButton();

        // 向服务器请求数据
        void loadAdvertisingFeeInfo();
    }
}

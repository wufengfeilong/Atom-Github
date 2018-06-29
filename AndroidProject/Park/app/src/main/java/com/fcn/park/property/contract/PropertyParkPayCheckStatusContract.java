package com.fcn.park.property.contract;

import com.fcn.park.base.BaseView;
import com.fcn.park.property.bean.PropertyParkPayBean;
import com.fcn.park.property.bean.PropertyParkPayTypeBean;

import java.util.List;
import java.util.Map;

/**
 * Created by 860115032 on 2018/04/08.
 */

public interface PropertyParkPayCheckStatusContract {
    interface View extends BaseView {

        /**
         * 页面初始化数据
         * @param PPBean
         */
        void setInitData(PropertyParkPayBean PPBean);

    }

    interface Presenter {

        /**
         * 获取页面初始化数据
         * @param parkPayId
         */
        void getInitData(int parkPayId);

        /**
         * 重新申请按钮
         */
        void onClickApplyCommit();

    }
}

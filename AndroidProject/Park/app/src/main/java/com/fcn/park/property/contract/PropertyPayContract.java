package com.fcn.park.property.contract;

import com.fcn.park.base.BaseView;
import com.fcn.park.property.bean.PropertyMainBean;

/**
 * 绿色物管的缴费画面用Contract
 */

public interface PropertyPayContract {
    interface View extends BaseView {

        /**
         * @param data
         *         从服务器获取到的数据
         */
        void setLayoutContent(PropertyMainBean data);

        /**
         * 获取车牌号
         * 临时停车缴费时,需要根据车牌号码查询数据
         * @return 车牌号
         */
        String getPlateNum();

        /**
         * 获取月租车辆申请的ID
         * 月租车辆申请停车缴费时,需要根据ID查询数据
         * @return ID
         */
        int getParkPayID();
    }

    interface Presenter {

        /**
         * [缴费记录]的点击事件
         * @param payType
         * 用payType来区分支付类型
         *     1:水费缴费
         *     2:电费缴费
         *     3:物业费缴费
         *     4:租赁费缴费
         *     5:月租停车费
         *     6:临时停车缴费
         */
        void onPaymentListClick(int payType);

        /**
         * 获取画面上要显示的数据
         * @param payType
         *     1:水费缴费
         *     2:电费缴费
         *     3:物业费缴费
         *     4:租赁费缴费
         *     5:月租停车费
         *     6:临时停车缴费
         */
        void onDataInit(int payType);

    }
}

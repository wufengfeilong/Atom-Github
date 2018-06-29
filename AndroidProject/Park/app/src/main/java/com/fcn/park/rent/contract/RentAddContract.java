package com.fcn.park.rent.contract;

import com.fcn.park.base.BaseView;
import com.fcn.park.rent.bean.ImageInfo;
import com.fcn.park.rent.bean.RentAddBean;
import com.fcn.park.rent.bean.RentHouseEditBean;
import com.fcn.park.rent.bean.RentInitBean;
import java.util.List;

/**
 * 房屋编辑Contract
 */
public interface RentAddContract {
    interface View extends BaseView {

        /**
         * 获取房屋Id
         * @return
         */
        String getHouseId();

        /**
         * 获取页面输入的房屋信息
         * @return
         */
        RentAddBean getBean();

        /**
         * 获取页面上传的图片
         * @return
         */
        List<ImageInfo> getImageMap();

        /**
         * 获取选择的房屋状态
         * @return
         */
        String getHouseStatus();

        /**
         * 获取选择的房屋类型
         * @return
         */
        String getHouseType();

        /**
         * 添加房屋的场合的初始化数据绑定
         * @param rentInitBean
         */
        void setAddInitData(RentInitBean rentInitBean);

        /**
         * 编辑房屋的场合的初始化数据绑定
         * @param rentHouseEditBean
         */
        void setEditInitData(RentHouseEditBean rentHouseEditBean);

        /**
         * 第1个图片选择
         */
        void imageSelect1();

        /**
         * 第2个图片选择
         */
        void imageSelect2();

        /**
         * 第3个图片选择
         */
        void imageSelect3();

        /**
         * 第4个图片选择
         */
        void imageSelect4();

    }

    interface Presenter {

        void aaa(int asd);

        /**
         * 获取添加场合初始化数据
         */
        void getInitData();

        /**
         * 添加房屋信息
         */
        void addRentInfo(int appFlg);

        /**
         * 获取编辑场合初始化数据
         */
        void loadEditInitData();

        /**
         * 第1个图片选择
         */
        void imageSelect1();

        /**
         * 第2个图片选择
         */
        void imageSelect2();

        /**
         * 第3个图片选择
         */
        void imageSelect3();

        /**
         * 第4个图片选择
         */
        void imageSelect4();

    }
}

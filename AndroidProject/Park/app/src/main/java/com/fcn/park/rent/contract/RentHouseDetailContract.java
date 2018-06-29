package com.fcn.park.rent.contract;

import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.fcn.park.base.BaseView;
import com.fcn.park.info.utils.LocalImageHolderView;
import com.fcn.park.rent.bean.RentHouseDetailBean;

import java.util.List;

/**
 * 房屋详情Contract.
 */
public interface RentHouseDetailContract {

    interface View extends BaseView{

        /**
         * 获取房屋ID
         * @return
         */
        String getHouseId();

        /**
         * 初始化数据
         * @param infoBean
         */
        void bindData(RentHouseDetailBean infoBean);

        /**
         * 房屋详情里房屋图片的轮播
         * @param holderCreator
         * @param images
         */
        void setConvenientBannerHolder(CBViewHolderCreator<LocalImageHolderView> holderCreator, List<String> images);
    }

    interface Presenter{

        /**
         * 加载房屋详情信息
         */
        void loadInfo();

        /**
         * 绑定轮播图的数据
         */
        void bindBanner(List<String> mBannerImages);

        /**
         * 编辑房屋按钮事件
         */
        void editHouseInfo();
    }
}

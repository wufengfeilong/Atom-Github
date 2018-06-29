package com.fcn.park.home.advertisement.contract;

import com.fcn.park.base.BaseView;

/**
 * Created by 860115001 on 2018/04/24.
 */

public interface AdvertisementContract {

    interface View extends BaseView {
        /**
         * 打开选择图片的PopWindow
         */
        void openSelectPhotoPop();

        /**
         * 获取上传图片路径
         * @return
         */
        String getPictureUrl();

        /**
         * 获取广告内容
         */
        String getContent();

        /**
         * 获取套餐类型
         * @return
         */
        int getAdvertisementPayType();
    }

    interface Presenter {
        /**
         * 添加广告图片
         */
        void addAdvertisement();

        /**
         * 提交广告
         */
        void onSubmit();
    }
}

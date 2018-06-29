package com.fcn.park.info.contract;

import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.fcn.park.base.BaseView;
import com.fcn.park.info.bean.EnterpriseInfoBean;
import com.fcn.park.info.utils.LocalImageHolderView;

import java.util.List;

/**
 * Created by liuyq on 2018/04/17.
 * 企业详情用contract
 */

public interface InfoEnterpriseDetailContract {
    interface View extends BaseView {
        //绑定数据
        void bindData(EnterpriseInfoBean infoBean);

        String getEnterpriseId();

        //企业详情里机构环境的轮播
        void setConvenientBannerHolder(CBViewHolderCreator<LocalImageHolderView> holderCreator, List<String> images);

    }

    interface Presenter {
        void loadInfo();

        //绑定轮播图的数据
        void bindBanner(List<String> mBannerImages);
    }
}

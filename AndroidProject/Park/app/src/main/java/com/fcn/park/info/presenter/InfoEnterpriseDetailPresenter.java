package com.fcn.park.info.presenter;

import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.fcn.park.base.BasePresenter;
import com.fcn.park.base.utils.OnDataGetCallback;
import com.fcn.park.info.bean.EnterpriseInfoBean;
import com.fcn.park.info.contract.InfoEnterpriseDetailContract;
import com.fcn.park.info.module.InfoEnterpriseDetailModule;
import com.fcn.park.info.utils.LocalImageHolderView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 860116014 on 2018/04/17.
 * 企业详情页面使用Presenter
 */

public class InfoEnterpriseDetailPresenter extends BasePresenter<InfoEnterpriseDetailContract.View> implements InfoEnterpriseDetailContract.Presenter {

    private InfoEnterpriseDetailModule mModule;
    private List<EnterpriseInfoBean.ComPicUpLoadList> mPicBean;
    private List<String> mBannerImages;

    @Override
    public void attach(InfoEnterpriseDetailContract.View view) {
        super.attach(view);
        mModule = new InfoEnterpriseDetailModule();
    }

    /**
     * 加载数据
     */
    @Override
    public void loadInfo() {
        mModule.requestEnterpriseInfo(getView().getContext(), getView().getEnterpriseId(), new OnDataGetCallback<EnterpriseInfoBean>() {
            @Override
            public void onSuccessResult(EnterpriseInfoBean data) {
                getView().bindData(data);
                //取得轮播图片
                if (data.getComPictUploadList() != null && !data.getComPictUploadList().isEmpty()) {
                    mPicBean = data.getComPictUploadList();
                    bindBanner(getBannerImages());
                } else {
                    bindBanner(null);
                }
            }
        });

    }

    /**
     * 绑定轮播图的数据
     */
    @Override
    public void bindBanner(List<String> mBannerImages) {
        getView().setConvenientBannerHolder(new CBViewHolderCreator<LocalImageHolderView>() {
            @Override
            public LocalImageHolderView createHolder() {
                return new LocalImageHolderView();
            }
        }, mBannerImages);
    }

    public List<String> getBannerImages() {
        mBannerImages = new ArrayList<>();
        if (mPicBean != null && mPicBean.size() > 0) {
            for (int i = 0; i < mPicBean.size(); i++) {
                mBannerImages.add(i, mPicBean.get(i).getEnvironmentPicture());
            }
        }
        return mBannerImages;
    }

}

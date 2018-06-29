package com.fcn.park.info.view;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.fcn.park.R;
import com.fcn.park.base.BaseActivity;
import com.fcn.park.databinding.InfoEnterpriseDetailBinding;
import com.fcn.park.info.bean.EnterpriseInfoBean;
import com.fcn.park.info.contract.InfoEnterpriseDetailContract;
import com.fcn.park.info.presenter.InfoEnterpriseDetailPresenter;
import com.fcn.park.info.utils.LocalImageHolderView;

import java.util.List;

/**
 * Created by liuyq on 2018/04/17.
 * 企业详情页面
 */

public class InfoEnterpriseDetailActivity extends BaseActivity<InfoEnterpriseDetailBinding,InfoEnterpriseDetailContract.View, InfoEnterpriseDetailPresenter>
        implements InfoEnterpriseDetailContract.View{

    private static final String ID_TAG = "id";

    public static void actionStart(Context context, String id) {
        Intent intent = new Intent(context, InfoEnterpriseDetailActivity.class);
        intent.putExtra(ID_TAG, id);
        context.startActivity(intent);
    }

    @Override
    protected void setupTitle() {
        setTitleText("企业详情");
        openTitleLeftView(true);
    }

    @Override
    protected void initViews() {
        mPresenter.loadInfo();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.info_enterprise_detail;
    }

    @Override
    protected InfoEnterpriseDetailPresenter createPresenter() {
        return new InfoEnterpriseDetailPresenter();
    }


    @Override
    public void bindData(EnterpriseInfoBean infoBean) {
        mDataBinding.setEnterpriseInfo(infoBean);
    }

    @Override
    public String getEnterpriseId() {
        return getIntent().getStringExtra(ID_TAG);
    }

    /**
     * 轮播
     * @param holderCreator
     * @param images
     */
    @Override
    public void setConvenientBannerHolder(CBViewHolderCreator<LocalImageHolderView> holderCreator, List<String> images) {
        if (images == null) {
            mDataBinding.convenientBanner.setVisibility(View.GONE);
        }else if (images.size() > 1) {
            mDataBinding.convenientBanner
                    .setPages(holderCreator, images)
                    .setPageIndicator(new int[]{R.drawable.info_ic_banner_page_indicator, R.drawable.info_ic_banner_page_indicator_focused})
                    .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                    .startTurning(3000);
        }else {
            mDataBinding.convenientBanner
                    .setPages(holderCreator, images)
                    .setManualPageable(false);
        }
    }
}

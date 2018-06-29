package com.fcn.park.rent.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.fcn.park.R;
import com.fcn.park.base.BaseActivity;
import com.fcn.park.databinding.RentHouseDetailBinding;
import com.fcn.park.info.utils.LocalImageHolderView;
import com.fcn.park.login.LoginHelper;
import com.fcn.park.rent.bean.RentHouseDetailBean;
import com.fcn.park.rent.contract.RentHouseDetailContract;
import com.fcn.park.rent.presenter.RentHouseDetailPresenter;

import java.util.List;

/**
 * 房屋租赁详情页面
 */
public class RentHouseDetailActivity extends BaseActivity<RentHouseDetailBinding,
        RentHouseDetailContract.View, RentHouseDetailPresenter> implements RentHouseDetailContract.View,
        View.OnClickListener{

    /**
     * 页面跳转接口
     * @param context
     * @param houseId
     */
    public static void actionStart(Context context, String houseId) {
        Intent intent = new Intent(context, RentHouseDetailActivity.class);
        intent.putExtra("houseId", houseId);
        context.startActivity(intent);
    }

    /**
     * 页面初始化
     */
    @Override
    protected void initViews() {
        mPresenter.loadInfo();
        mDataBinding.setPresenter(mPresenter);
        //登录类型为管理员，显示编辑按钮
        if("0".equals(LoginHelper.getInstance().getUserBean().getUserType())){
            setRightMenuText("编辑");
            mTvTitleRight.setOnClickListener(this);
        }
    }

    /**
     * 获取详情页面传过来的houseId
     * @return
     */
    @Override
    public String getHouseId() {
        return getIntent().getStringExtra("houseId");
    }

    /**
     * 房屋详细信息设定
     * @param infoBean
     */
    @Override
    public void bindData(RentHouseDetailBean infoBean) {
        mDataBinding.setRentHouseDetailBean(infoBean);
    }

    /**
     * 房屋图片轮播
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

    @Override
    protected RentHouseDetailPresenter createPresenter() {
        return new RentHouseDetailPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.rent_house_detail;
    }

    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
        setTitleText("房屋信息详情");
    }

    /**
     * 编辑按钮点击事件
     * @param view
     */
    @Override
    public void onClick(View view) {
        mPresenter.editHouseInfo();
    }
}

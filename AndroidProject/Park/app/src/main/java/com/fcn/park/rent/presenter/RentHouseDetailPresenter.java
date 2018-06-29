package com.fcn.park.rent.presenter;

import android.content.Intent;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.fcn.park.base.BasePresenter;
import com.fcn.park.base.utils.OnDataGetCallback;
import com.fcn.park.info.utils.LocalImageHolderView;
import com.fcn.park.rent.activity.RentAddActivity;
import com.fcn.park.rent.bean.RentHouseDetailBean;
import com.fcn.park.rent.contract.RentHouseDetailContract;
import com.fcn.park.rent.module.RentModule;
import java.util.List;

/**
 * 房屋详细情报用RentHouseDetailPresenter
 */
public class RentHouseDetailPresenter extends BasePresenter<RentHouseDetailContract.View> implements RentHouseDetailContract.Presenter {

    private RentModule rentModule;

    @Override
    public void attach(RentHouseDetailContract.View view) {

        super.attach(view);
        rentModule = new RentModule();
    }

    /**
     * 房屋详情信息加载
     */
    @Override
    public void loadInfo() {
        rentModule.rentHouseDetail(getView().getContext(), getView().getHouseId(), new OnDataGetCallback<RentHouseDetailBean>() {
            @Override
            public void onSuccessResult(RentHouseDetailBean data) {
                getView().bindData(data);
                if (data.getImageList() != null && !data.getImageList().isEmpty()) {
                    bindBanner(data.getImageList());
                }else {
                    bindBanner(null);
                }
            }
        });
    }

    /**
     * 轮播图片设置
     * @param mBannerImages
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

    /**
     * 房屋编辑画面跳转
     */
    @Override
    public void editHouseInfo() {
        String houseId = getView().getHouseId();
        Intent intent = new Intent(getView().getContext(), RentAddActivity.class);
        intent.putExtra("houseId",houseId);
        getView().getContext().startActivity(intent);
    }
}






package com.fcn.park.home;

import android.support.v7.widget.RecyclerView;

import com.fcn.park.base.BaseView;
import com.fcn.park.base.contract.RecyclerViewContract;
import com.fcn.park.manager.bean.ManagerAdvertisingApprovalListBean;
import com.fcn.park.rent.bean.RentReleasedHouseListBean;

import java.util.List;

/**
 * Created by 860115001 on 2018/04/03.
 */

public interface HomeContract {

    interface View extends BaseView, RecyclerViewContract.View<RentReleasedHouseListBean.ListReleasedHouseBean> {

        /**
         * 绑定公告公示轮播
         * @param views
         */
        void bindAfficheViews(android.view.View... views);

        /**\
         * 获取公告公示View
         * @param affiche 公告公示
         * @param afficheId 公告公示Id
         * @return
         */
        android.view.View getAfficheView(String affiche,String afficheId);

        /**
         * 获取停车缴费View
         * @return
         */
        android.view.View getParkingFeeView();

        /**
         * 获取服务列表View
         * @return
         */
        android.view.View getServiceView();

        /**
         * 展示显示在首页的广告
         */
        void showTopAdvertisement(List<ManagerAdvertisingApprovalListBean.ListAdvertisingApprovalBean> list);
    }

    interface Presenter extends RecyclerViewContract.Presenter {

        /**
         * 绑定并创建公告的数据和View
         */
        void bindAfficheViews();

        /**
         * HOME画面上各个模块按钮的点击事件
         * @param viewFlag
         *         按钮的区分标识
         */
        void onItemClick(int viewFlag);

        /**
         * 强力推荐的更多点击事件
         */
        void onAdvertisementMore();

        /**
         * 最新租赁点击事件
         */
        void onRentItemClick(RecyclerView.ViewHolder vh, int position);

        /**
         * 最新租赁更多点击事件
         */
        void onRentInfoMore();
    }
}

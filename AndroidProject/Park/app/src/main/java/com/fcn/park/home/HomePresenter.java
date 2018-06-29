package com.fcn.park.home;

import android.content.Intent;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.fcn.park.R;
import com.fcn.park.base.BasePresenter;
import com.fcn.park.base.constant.Constant;
import com.fcn.park.base.utils.OnDataGetCallback;
import com.fcn.park.home.advertisement.activity.AdvertisementActivity;
import com.fcn.park.home.advertisement.activity.AdvertisementListActivity;
import com.fcn.park.info.bean.NewsListBean;
import com.fcn.park.login.LoginHelper;
import com.fcn.park.manager.bean.ManagerAdvertisingApprovalListBean;
import com.fcn.park.manager.module.ManagerAdvertisingListModule;
import com.fcn.park.property.activity.PropertyPayActivity;
import com.fcn.park.property.activity.PropertyPlateNumEditActivity;
import com.fcn.park.property.activity.PropertyRentParkListActivity;
import com.fcn.park.property.activity.PropertyRepairActivity;
import com.fcn.park.property.activity.PropertyServiceActivity;
import com.fcn.park.property.activity.PropertySuggestionActivity;
import com.fcn.park.rent.activity.RentHouseDetailActivity;
import com.fcn.park.rent.activity.RentReleasedHouseListActivity;
import com.fcn.park.rent.bean.RentReleasedHouseListBean;
import com.fcn.park.rent.module.RentReleasedHouseModule;

import java.util.List;

/**
 * Created by 860115001 on 2018/04/03.
 */

public class HomePresenter extends BasePresenter<HomeContract.View> implements HomeContract.Presenter, View.OnClickListener {

    private HomeModule mHomeModule;
    private ManagerAdvertisingListModule mManagerAdvertisingListModule;
    private RentReleasedHouseModule mRentReleasedHouseModule;

    @Override
    public void attach(HomeContract.View view) {
        super.attach(view);
        mHomeModule = new HomeModule();
        mManagerAdvertisingListModule = new ManagerAdvertisingListModule();
        mRentReleasedHouseModule = new RentReleasedHouseModule();
        getView().initRecyclerView();
    }
    @Override
    public void onClick(View view) {

    }

    @Override
    public void onItemClick(int viewFlag) {
        Intent intent = null;
        switch (viewFlag) {
            // 水费缴费
            case 1:
                // 只有企业用户可以操作
                if (LoginHelper.getInstance().isOnline() && "2".equals(LoginHelper.getInstance().getUserBean().getUserType())) {
                    Log.d("onItemClick", "水费缴费");
                    intent = new Intent(getView().getContext(), PropertyPayActivity.class);
                    intent.putExtra(Constant.PROPERTY_PAY_TYPE, 1);
                } else {
                    getView().showToast(getView().getContext().getString(R.string.company_check_toast));
                }
                break;
            // 电费缴费
            case 2:
                // 只有企业用户可以操作
                if (LoginHelper.getInstance().isOnline() && "2".equals(LoginHelper.getInstance().getUserBean().getUserType())) {
                    Log.d("onItemClick", "电费缴费");
                    intent = new Intent(getView().getContext(), PropertyPayActivity.class);
                    intent.putExtra(Constant.PROPERTY_PAY_TYPE, 2);
                } else {
                    getView().showToast(getView().getContext().getString(R.string.company_check_toast));
                }
                break;
            // 停车缴费
            case 3:
                showSelectParkPopMenu(getView().getParkingFeeView());
                break;
            // 物业费缴费
            case 4:
                // 只有企业用户可以操作
                if (LoginHelper.getInstance().isOnline() && "2".equals(LoginHelper.getInstance().getUserBean().getUserType())) {
                    Log.d("onItemClick", "物业费缴费");
                    intent = new Intent(getView().getContext(), PropertyPayActivity.class);
                    intent.putExtra(Constant.PROPERTY_PAY_TYPE, 3);
                } else {
                    getView().showToast(getView().getContext().getString(R.string.company_check_toast));
                }
                break;
            // 租赁费缴费
            case 5:
                // 只有企业用户可以操作
                if (LoginHelper.getInstance().isOnline() && "2".equals(LoginHelper.getInstance().getUserBean().getUserType())) {
                    Log.d("onItemClick", "租赁费缴费");
                    intent = new Intent(getView().getContext(), PropertyPayActivity.class);
                    intent.putExtra(Constant.PROPERTY_PAY_TYPE, 4);
                } else {
                    getView().showToast(getView().getContext().getString(R.string.company_check_toast));
                }
                break;
            //房屋租赁
            case 6:
                    Log.d("onItemClick", "房屋租赁");
                    intent = new Intent(getView().getContext(), RentReleasedHouseListActivity.class);
                    intent.putExtra(Constant.RENT_FROM, Constant.RENT_FROM);

                break;
            //报修追加
            case 7:
                // 只有企业用户可以操作
                if (LoginHelper.getInstance().isOnline() && "2".equals(LoginHelper.getInstance().getUserBean().getUserType())) {
                    intent = new Intent(getView().getContext(), PropertyRepairActivity.class);
                } else {
                    getView().showToast(getView().getContext().getString(R.string.company_check_toast));
                }
                break;
            //商家服务列表
            case 8:
                intent = new Intent(getView().getContext(), PropertyServiceActivity.class);
                break;
            // 月租车辆申请
            case 9:
                // 个人登录check,只有个人用户可以操作
                if (LoginHelper.getInstance().isOnline() && "1".equals(LoginHelper.getInstance().getUserBean().getUserType())) {
                    intent = new Intent(getView().getContext(), PropertyRentParkListActivity.class);
                    intent.putExtra(Constant.PROPERTY_MOVE_TYPE, Constant.PROPERTY_MOVE_TYPE_APPLY);
                } else if (LoginHelper.getInstance().isOnline() && "2".equals(LoginHelper.getInstance().getUserBean().getUserType())) {
                    getView().showToast(getView().getContext().getString(R.string.company_check_to_personal_toast));
                } else {
                    getView().showToast(getView().getContext().getString(R.string.login_check_toast));
                }
                break;
            //租广告位
            case 10:
                if (LoginHelper.getInstance().isOnline()) {
                    if ("2".equals(LoginHelper.getInstance().getUserBean().getUserType())) {
                        intent = new Intent(getView().getContext(), AdvertisementActivity.class);
                    } else {
                        getView().showToast(getView().getContext().getString(R.string.company_check_toast));
                    }
                } else {
                    getView().showToast(getView().getContext().getString(R.string.login_check_toast));
                }
                break;
            //投诉建议页面
            case 11:
                if (LoginHelper.getInstance().isOnline()) {
                    intent = new Intent(getView().getContext(), PropertySuggestionActivity.class);
                } else {
                    getView().showToast(getView().getContext().getString(R.string.login_check_toast));
                }
                break;

            // 以上以外
            default:
                break;
        }
        if (intent != null) {
            getView().getContext().startActivity(intent);
        }
    }

    /**
     * 强力推荐的更多点击事件
     */
    @Override
    public void onAdvertisementMore() {
        Intent intent = new Intent(getView().getContext(), AdvertisementListActivity.class);
        getView().getContext().startActivity(intent);
    }

    /**
     * 最新租赁点击事件
     * @param vh
     * @param position
     */
    @Override
    public void onRentItemClick(RecyclerView.ViewHolder vh, int position) {
        int HouseId = mRentReleasedHouseModule.getHouseReleasedList().get(position).getId();
        RentHouseDetailActivity.actionStart(getView().getContext(), String.valueOf(HouseId));
    }

    /**
     * 最新租赁更多点击事件
     */
    @Override
    public void onRentInfoMore() {
        Intent intent = new Intent(getView().getContext(), RentReleasedHouseListActivity.class);
        getView().getContext().startActivity(intent);
    }

    /**
     * 点击［停车缴费］按钮，弹出PopMenu
     *
     * @param view 停车缴费按钮的View
     */
    public void showSelectParkPopMenu(View view) {
        PopupMenu menu = new PopupMenu(getView().getContext(), view);
        menu.getMenuInflater().inflate(R.menu.select_park_type_menu, menu.getMenu());
        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    // 选择临时停车
                    case R.id.property_select_park_type_temporary:
                        getView().actionStartActivity(PropertyPlateNumEditActivity.class);
                        break;
                    // 选择月租停车
                    case R.id.property_select_park_type_rent:
                        // 个人登录check,只有个人用户可以操作
                        if (LoginHelper.getInstance().isOnline() && "1".equals(LoginHelper.getInstance().getUserBean().getUserType())) {
                            Intent intent = new Intent(getView().getContext(), PropertyRentParkListActivity.class);
                            intent.putExtra(Constant.PROPERTY_MOVE_TYPE, Constant.PROPERTY_MOVE_TYPE_PAY);
                            // 月租车辆申请列表
                            getView().getContext().startActivity(intent);
                        } else if (LoginHelper.getInstance().isOnline() && "2".equals(LoginHelper.getInstance().getUserBean().getUserType())) {
                            getView().showToast(getView().getContext().getString(R.string.company_check_to_personal_toast));
                        } else {
                            getView().showToast(getView().getContext().getString(R.string.login_check_toast));
                        }
                        break;

                }
                return true;
            }
        });
        menu.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu menu) {
            }
        });
        menu.show();
    }

    /**
     * 加载首页数据
     */
    @Override
    public void loadListData() {
        mHomeModule.getNewsTitle(new OnDataGetCallback<List<NewsListBean.GetlistNewsBean>>() {
            @Override
            public void onSuccessResult(List<NewsListBean.GetlistNewsBean> getlistNewsBeen) {
                bindAfficheViews();
            }
        });

        //获取广告列表
        mManagerAdvertisingListModule.getAdvertisingList(getView().getContext(), 1, new OnDataGetCallback<List<ManagerAdvertisingApprovalListBean.ListAdvertisingApprovalBean>>() {
            @Override
            public void onSuccessResult(List<ManagerAdvertisingApprovalListBean.ListAdvertisingApprovalBean> listAdvertisingBean) {
                getView().showTopAdvertisement(listAdvertisingBean);
            }
        });

        //获取最新租赁列表
        mRentReleasedHouseModule.requestRentReleasedHouseList(getView().getContext(), 1, new OnDataGetCallback<List<RentReleasedHouseListBean.ListReleasedHouseBean>>() {
            @Override
            public void onSuccessResult(List<RentReleasedHouseListBean.ListReleasedHouseBean> listReleasedHouseBean) {
                getView().bindListData(listReleasedHouseBean);
            }
        });
    }

    /**
     * 绑定并创建公告的数据和View
     */
    @Override
    public void bindAfficheViews() {
        if (mHomeModule.getAfficheList() != null && mHomeModule.getAfficheList().size() > 0) {
            int afficheListSize = mHomeModule.getAfficheList().size();
            android.view.View[] afficheViews = new View[afficheListSize];
            for (int i = 0; i < afficheListSize; i++) {
                NewsListBean.GetlistNewsBean noticeBean = mHomeModule.getAfficheList().get(i);
                String affiche = noticeBean.getTitle();
                String afficheId = noticeBean.getNewsId();
                afficheViews[i] = getView().getAfficheView(affiche, afficheId);
            }
            getView().bindAfficheViews(afficheViews);
        }
    }

}

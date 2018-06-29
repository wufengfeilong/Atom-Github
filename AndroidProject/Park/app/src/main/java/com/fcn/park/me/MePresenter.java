package com.fcn.park.me;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import com.fcn.park.MainActivity;
import com.fcn.park.R;
import com.fcn.park.base.BasePresenter;
import com.fcn.park.base.constant.Constant;
import com.fcn.park.base.interfacee.OnDataCallback;
import com.fcn.park.base.utils.OnDataGetCallback;
import com.fcn.park.info.view.ManagerDemandListActivity;
import com.fcn.park.login.LoginHelper;
import com.fcn.park.manager.activity.*;
import com.fcn.park.manager.activity.car.CarWaitCheckListActivity;
import com.fcn.park.manager.activity.car.ManagerParkFeeListActivity;
import com.fcn.park.manager.bean.MenuBean;
import com.fcn.park.manager.contract.MenuLoadContract;
import com.fcn.park.manager.utils.DialogUtils;
import com.fcn.park.me.activity.*;
import com.fcn.park.rent.activity.RentHouseListActivity;

/**
 * 人个中心Tab用Presenter.
 */

public class MePresenter extends BasePresenter<MeContract.View> implements MeContract.Presenter{

    private String TAG = "=== MePresenter ===";
    private MenuLoadContract.Module<MenuBean> mMenuModule;
    private MeModule meMeModule;
    @Override
    public void attach(MeContract.View view) {
        super.attach(view);
        mMenuModule = new MeMenuModule();
        mMenuModule.attachMenuList(getView().getMenuList());
        meMeModule = new MeModule();
        getView().initRecyclerView();
        createItemClickListener(getView().getRecyclerView());
    }

    @Override
    public void loadListData() {

    }
    /**
     * 初始化menu
     */
    @Override
    public void initMenu() {
        getView().bindListData(mMenuModule.getMenuBeanList());
    }
    /**
     * 我的Item点击跳转
     */
    @Override
    public void onItemClick(RecyclerView.ViewHolder vh, int position) {
        MenuBean bean = mMenuModule.getMenuBeanList().get(position);
        Log.d(TAG, "====== userType = " + LoginHelper.getInstance().getUserBean().getUserType());
        if (Constant.UserType.PERSON.getValue().equals(LoginHelper.getInstance().getUserBean().getUserType())) {
            switch (bean.getId()) {
                case R.id.me_company_auth:

                    meMeModule.selectAuthResult(getView().getContext(), LoginHelper.getInstance().getUserBean().getToken(), new OnDataCallback<String>() {
                        @Override
                        public void onSuccessResult(String data) {
                            if ("0".equals(data)) {
                                getView().showToast("认证中,不能重复申请认证");
                            } else {
                                getView().jumpActivity(MeCompanyAuthActivity.class,data);
                            }
                        }
                        @Override
                        public void onError(String errMsg) {
                            getView().showToast(errMsg);
                        }
                    });

                    break;
                case R.id.me_person_my_msg: // 我的消息
                    getView().actionStartActivity(MeMyMessageActivity.class);
                    break;
                case R.id.me_person_my_car: // 我的车辆
                    getView().actionStartActivity(MeCarInfoActivity.class);
                    break;
                case R.id.me_person_my_ver: // 版本信息
                    getView().actionStartActivity(MeVersionInfoActivity.class);
                    break;
                case R.id.me_person_update_pwd: // 密码重置
                    getView().actionStartActivity(ManagerResetPasswordActivity.class);
                    break;
                case R.id.me_person_quit_login: // 退出登录
                    exitLogin();
                    break;

            }
        }
        if (Constant.UserType.MANAGE.getValue().equals(LoginHelper.getInstance().getUserBean().getUserType())) {
            switch (bean.getId()) {
                case R.id.manager_title_enterprise_check:// 企业审核管理
                      getView().actionStartActivity(ManagerEnterpriseCheckListActivity.class);
                    break;
                case R.id.manager_title_property_management_fee:// 物业费收支
                    getView().actionStartActivity(PropertyFeeListActivity.class);
                    break;
                case R.id.manager_title_rent:// 租赁费收支
                    getView().actionStartActivity(RentFeeListActivity.class);
                    break;
                case R.id.manager_title_water_electricity_fee:// 水电费收支
                    getView().actionStartActivity(WaterAndElectricFeeTypeActivity.class);
                    break;
                case R.id.manager_title_publish_repairs:// 公共报修
                    getView().actionStartActivity(ManagerRepairsListActivity.class);
                    break;
                case R.id.manager_title_reply_message:// 留言回复
                    getView().actionStartActivity(ManagerMessageReplyListActivity.class);
                    break;
                case R.id.manager_title_monthly_vehicle_check:// 月租车辆审批
                    getView().actionStartActivity(CarWaitCheckListActivity.class);
                    break;
                case R.id.manager_title_parking_fee: // 停车付费
                    getView().actionStartActivity(ManagerParkFeeListActivity.class);
                    break;
                case R.id.manager_title_make_announcement: // 公告发布
                    Intent intent0 = new Intent();
                    intent0.putExtra(Constant.NEWS_TYPE, Constant.NEWS_TYPE_0);
                    intent0.setClass(getView().getContext(), ManagerPostNewsListActivity.class);
                    getView().getContext().startActivity(intent0);
                    break;
                case R.id.manager_title_post_news:// 新闻发布
                    Intent intent1 = new Intent();
                    intent1.putExtra(Constant.NEWS_TYPE, Constant.NEWS_TYPE_1);
                    intent1.setClass(getView().getContext(), ManagerPostNewsListActivity.class);
                    getView().getContext().startActivity(intent1);
                    break;
                case R.id.manager_title_publish_activities:// 活动发布
                    Intent intent2 = new Intent();
                    intent2.putExtra(Constant.NEWS_TYPE, Constant.NEWS_TYPE_2);
                    intent2.setClass(getView().getContext(), ManagerPostNewsListActivity.class);
                    getView().getContext().startActivity(intent2);
                    break;
                case R.id.manager_title_park_introduction:// 园区简介
                    getView().actionStartActivity(ManagerParkIntroductionActivity.class);
                    break;
                case R.id.manager_title_user_management:// 用户管理
//                    getView().actionStartActivity(TestRecordActivity.class);
                    break;
                case R.id.manager_rent_house_management: // 房屋租赁管理
                    getView().actionStartActivity(RentHouseListActivity.class);
                    break;
                case R.id.manager_title_advertising_position_management:// 广告位管理
                    getView().actionStartActivity(ManagerAdvertisingTypeActivity.class);
                    break;
                case R.id.manager_title_reset_password:// 重置密码
                    getView().actionStartActivity(ManagerResetPasswordActivity.class);
                    break;
                case R.id.manager_title_quit: // 退出
                    exitLogin();
                    return;
            }

        } else {
            switch (bean.getId()) {
                case R.id.me_company_demand:
                    getView().actionStartActivity(ManagerDemandListActivity.class);
                    break;
                case R.id.me_company_my_msg: // 我的消息
                    getView().actionStartActivity(MeMyMessageActivity.class);
                    break;
                case R.id.me_company_my_car: // 我的车辆
                   getView().actionStartActivity(MeCarInfoActivity.class);
                    break;
                case R.id.me_company_my_repair:// 我的报修
                    getView().actionStartActivity(MeRepairRecordListActivity.class);
                    break;
                case R.id.me_company_my_ver: // 版本信息
                    getView().actionStartActivity(MeVersionInfoActivity.class);
                    break;
                case R.id.me_company_update_pwd: // 密码重置
                    getView().actionStartActivity(ManagerResetPasswordActivity.class);
                    break;
                case R.id.me_company_quit_login: // 退出登录
                    exitLogin();
                    break;
            }
        }
    }

    /**
     * 点击“退出”按钮后的处理
     */
    private void exitLogin() {
        DialogUtils
                .buildAlertDialogWithCancel(getView().getContext(), "温馨提示", "您是否要退出登录")
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        LoginHelper.getInstance().userExit();
                        getView().closeActivity();
                        getView().actionStartActivity(MainActivity.class);
                        getView().showToast("退出成功");
                    }
                }).show();
    }
    /**
     * 点击进入个人详情
     */
    @Override
    public void onClickGoProjectInfo() {
        if (Constant.UserType.MANAGE.getValue().equals(LoginHelper.getInstance().getUserBean().getUserType())) {

        } else if (Constant.UserType.PERSON.getValue().equals(LoginHelper.getInstance().getUserBean().getUserType())) {
            getView().actionStartActivity(MePersonInfoActivity.class);
        } else if (Constant.UserType.ENTERPRISE.getValue().equals(LoginHelper.getInstance().getUserBean().getUserType())){
            getView().actionStartActivity(MeCompanyInfoActivity.class);
        }
    }

    /**
     * 判断是否有未读消息
     */
    public void hasMsg() {
        meMeModule.selectHasMsg(getView().getContext(), LoginHelper.getInstance().getUserBean().getToken(), new OnDataGetCallback<String>() {
            @Override
            public void onSuccessResult(String data) {
                if (Integer.valueOf(data)>0) {
                    getView().isMsg(true);
                } else {
                    getView().isMsg(false);
                }
            }
        });
    }
}

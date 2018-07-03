package sdwxwx.com.me.presenter;

import sdwxwx.com.base.BaseCallback;
import sdwxwx.com.base.BasePresenter;
import sdwxwx.com.cons.Constant;
import sdwxwx.com.home.activity.SearchUserActivity;
import sdwxwx.com.home.bean.RecommendUserBean;
import sdwxwx.com.home.model.SearchUserModel;
import sdwxwx.com.login.activity.GetPhoneActivity;
import sdwxwx.com.me.activity.QRCodeGenerateActivity;
import sdwxwx.com.me.activity.QRCodeScanActivity;
import sdwxwx.com.me.contract.FindFriendsContract;
import sdwxwx.com.util.LoginUtil;

import java.util.List;

/**
 * Created by 860117073 on 2018/5/22.
 */

public class FindFriendsPresenter extends BasePresenter<FindFriendsContract.View> implements FindFriendsContract.Presenter {

    @Override
    public void loadListData() {
        SearchUserModel.recommendUsers(LoginUtil.getStrUserInfo(Constant.SP_MEMBER_ID, getView().getContext()),new BaseCallback<List<RecommendUserBean>>() {

            @Override
            public void onSuccess(List<RecommendUserBean> data) {
                if (getView() == null) {
                    return;
                }
                getView().bindListData(data);
            }
            @Override
            public void onFail(String msg) {
            }
        });
    }
    @Override
    public void myQrCodeClick() {
        //我的二维码
        getView().actionStartActivity(QRCodeGenerateActivity.class);
    }

    public void toScan(){
        //扫码功能
        getView().actionStartActivity(QRCodeScanActivity.class);


    }
    public void toSearchUser(){
        //跳转到搜索界面
        getView().actionStartActivity(SearchUserActivity.class);

    }

    public void back(){
        //返回键操作
        getView().closeActivity();
    }
    public void getPhone(){
        getView().actionStartActivity(GetPhoneActivity.class);
    }
}

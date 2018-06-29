package com.fcn.park.manager.presenter;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import com.fcn.park.base.BasePresenter;
import com.fcn.park.base.constant.Constant;
import com.fcn.park.base.utils.OnDataGetCallback;
import com.fcn.park.manager.activity.ManagerRepairsDetailActivity;
import com.fcn.park.manager.bean.ManagerRepairsDetailInfoBean;
import com.fcn.park.manager.contract.ManagerRepairsDetailContract;
import com.fcn.park.manager.module.ManagerRepairsDetailInfoModule;
import com.fcn.park.property.utils.customViews.PropertyServiceDialog;

/**
 * Created by 丁胜胜 on 2018/04/24.
 * 类描述：管理中心报修详情用Pressenter
 */

public class ManagerRepairsDetailPresenter extends BasePresenter<ManagerRepairsDetailContract.View> implements ManagerRepairsDetailContract.Presenter {

    private ManagerRepairsDetailInfoModule mManagerRepairsDetailInfoModule;

    private PropertyServiceDialog propertyServiceDialog;
    public String str;
    private TextView textView;

    @Override
    public void attach(ManagerRepairsDetailContract.View view) {
        super.attach(view);
        mManagerRepairsDetailInfoModule = new ManagerRepairsDetailInfoModule();
    }

    @Override
    public void loadInfo() {
        mManagerRepairsDetailInfoModule.requestRepairsDetailInfo(getView().getContext(), getView().getRepairId(),  getView().getRepairPhone(),new OnDataGetCallback<ManagerRepairsDetailInfoBean>() {
            @Override
            public void onSuccessResult(ManagerRepairsDetailInfoBean data) {
                getView().updateInfo(data);
            }
        });
    }

    /**
     * 点击电话条目的处理
     */
    public void telCallOnClick() {
        //获取到电话号码存入str变量；
        str = getView().getRepairPhone();

        //创建一个自定义dialog
        propertyServiceDialog = new PropertyServiceDialog(getView().getContext());
        //设置提示信息
        propertyServiceDialog.setMessage("要给" + str + "打电话吗？");
        //为自定义dialog设置确定的监听事件
        propertyServiceDialog.setYesOnclickListener("确定", new PropertyServiceDialog.onYesOnclickListener() {
            @Override
            public void onYesClick() {
                callPhone(str);
                propertyServiceDialog.dismiss();
            }

            public void callPhone(String str) {
                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (ContextCompat.checkSelfPermission(getView().getContext(), Manifest.permission.CALL_PHONE)
                                != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions((ManagerRepairsDetailActivity) getView().getContext(),
                                    new String[]{Manifest.permission.CALL_PHONE}, 10);
                        }
                    }
                    Intent phoneIntent = new Intent(Constant.PROPERTY_CALL_ACTION, Uri.parse("tel:" + str));
                    //启动
                    getView().getContext().startActivity(phoneIntent);
                } catch (Exception e) {
                    e.printStackTrace();
                }}});
        //为自定义dialog设置取消的监听事件
        propertyServiceDialog.setNoOnclickListener("取消", new PropertyServiceDialog.onNoOnclickListener() {
            @Override
            public void onNoClick() {
                propertyServiceDialog.dismiss();
            }
        });
        propertyServiceDialog.show();
    }
}

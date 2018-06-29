package com.fcn.park.me.presenter;

import android.app.ProgressDialog;
import com.fcn.park.base.BasePresenter;
import com.fcn.park.base.utils.OnDataGetCallback;
import com.fcn.park.me.bean.VersionInfoBean;
import com.fcn.park.me.contract.MeVersionInfoContract;
import com.fcn.park.me.module.MeVersionInfoModule;
import com.fcn.park.me.service.MeUpdateService;

/**
 * create by 860115039
 * date      2018/04/23
 * time      13:07
 * 个人中心-版本更新presenter
 */
public class MeVersionInfoPresenter extends BasePresenter<MeVersionInfoContract.View> implements MeVersionInfoContract.Presenter {

    private MeVersionInfoModule meVersionInfoModule;
    ProgressDialog mDialog;

    @Override
    public void attach(MeVersionInfoContract.View view) {
        super.attach(view);
        meVersionInfoModule = new MeVersionInfoModule();
    }
    /**
     * 检测新版本
     */
    @Override
    public void versionDetect() {
        meVersionInfoModule.getVersionInfo(getView().getContext(), new OnDataGetCallback<VersionInfoBean>() {
            @Override
            public void onSuccessResult(VersionInfoBean data) {
                if (getView().getVersionCode() < Integer.valueOf(data.getServerFlag())) {
                    //需要升级
                    if (Integer.valueOf(data.getLastForce()) == 1) {
                        //强制升级
                        getView().showNeedUpdateDialog(data);
                    } else {
                        //自主选择是否升级
                        getView().showSelectUpdateDialog(data);
                    }
                } else {
                    // 已经是最新版本，无需更新
                    getView().showNoNeedUpdateDialog();
                }
            }
        });
    }

    /**
     * APK下载
     */
    @Override
    public void versionUpdate(VersionInfoBean bean) {
        createPD();
        // 启动Service 开始下载
        MeUpdateService.startUpdate(getView().getContext(), bean.getUpdateurl(), bean.getAppname(), new MeUpdateService.OnProgressListener() {
            @Override
            public void onProgress(int progress) {
                //更新对话框进度条
                mDialog.setProgress(progress);
            }

            @Override
            public void onSuccess(boolean isSuccess) {
                mDialog.dismiss();
                //失败提示
                if (!isSuccess) {
                    getView().showToast("更新不成功");
                }
            }
        });
    }
    /**
     * 下载进度条
     */
    private void createPD(){
        mDialog = new ProgressDialog(getView().getContext());
        mDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mDialog.setTitle("正在下载最新版本");
        mDialog.setCancelable(false);// 设置是否可以通过点击Back键取消
        mDialog.setCanceledOnTouchOutside(false);// 设置在点击Dialog外是否取消Dialog进度条
        mDialog.setMax(100);
        mDialog.show();
    }
}

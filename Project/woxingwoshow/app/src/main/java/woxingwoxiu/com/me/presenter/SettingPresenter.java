package woxingwoxiu.com.me.presenter;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.widget.Toast;
import woxingwoxiu.com.base.BaseCallback;
import woxingwoxiu.com.base.BasePresenter;
import woxingwoxiu.com.home.HomeActivity;
import woxingwoxiu.com.login.activity.NotLoginInterfaceActivity;
import woxingwoxiu.com.me.activity.AboutUsActivity;
import woxingwoxiu.com.me.activity.FeedbackActivity;
import woxingwoxiu.com.me.activity.NameCertifyActivity;
import woxingwoxiu.com.me.activity.QRCodeGenerateActivity;
import woxingwoxiu.com.me.bean.UpdateInfoBean;
import woxingwoxiu.com.me.contract.SettingContract;
import woxingwoxiu.com.me.model.SettingModel;
import woxingwoxiu.com.me.service.MeUpdateService;
import woxingwoxiu.com.util.DataCleanUtils;

import static woxingwoxiu.com.cons.Constant.SP_FILE_NAME;

/**
 * Created by 860117073 on 2018/5/9.
 */

public class SettingPresenter extends BasePresenter<SettingContract.View> implements SettingContract.Presenter {
    SettingModel mModel;

    /** 进度条 */
    private ProgressDialog progressDialog;


    @Override
    public void attachView(SettingContract.View mvpView) {
        super.attachView(mvpView);
        mModel = new SettingModel();
    }

    @Override
    public void onClickBack(){
        getView().closeActivity();
    }

    /**
     * 点击我的二维码
     */
    @Override
    public void onClickQrCode(){
        // 跳转到我的二维码画面
        getView().actionStartActivity(QRCodeGenerateActivity.class);
    }

    /**
     * 点击认证信息
     */
    @Override
    public void onClickCertify(){
        // 跳转到实名认证画面
        NameCertifyActivity.actionStart(getView().getContext());
    }

    /**
     * 点击清除缓存
     */
    @Override
    public void onClickClearCaches() {

        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(getView().getContext());
        normalDialog.setMessage("确定清除缓存吗?");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        showProgressDialog("缓存清理中");
                        // 进行清空缓存操作
                        DataCleanUtils.clearAllCache(getView().getContext());
                        hideProgressDialog();
                        // 画面刷新
                        getView().refresh(1);
                        Toast.makeText(getView().getContext(), "清除缓存完毕！", Toast.LENGTH_SHORT).show();
                    }
                });
        normalDialog.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 不做任何处理
                    }
                });
        // 显示
        normalDialog.show();
    }

    /**
     * 点击用户反馈
     */
    @Override
    public void onClickFeedback() {
        // 跳转到反馈画面
        getView().actionStartActivity(FeedbackActivity.class);
    }

    /**
     * 关于我们
     */
    @Override
    public void onClickAboutUs() {
        // 跳转到关于我们画面
        getView().actionStartActivity(AboutUsActivity.class);
    }

    /**
     * 点击退出登录
     */
    @Override
    public void onClicklogOut(){
                /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(getView().getContext());
        normalDialog.setMessage("确定退出吗?");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 把Preferences中的登录信息清空
                        SharedPreferences sp = getView().getContext().getSharedPreferences(SP_FILE_NAME, Context.MODE_PRIVATE);
                        //获取编辑器
                        SharedPreferences.Editor editor = sp.edit();
                        // 把编辑器清空
                        editor.clear();
                        // 提交修改
                        editor.commit();
                        // 跳转到HOME画面
                        getView().actionStartActivity(NotLoginInterfaceActivity.class);
                    }
                });
        normalDialog.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 不做任何处理
                    }
                });
        // 显示
        normalDialog.show();
    }
    /**
     * 点击检查更新
     */
    @Override
    public void onClickCheckUpdate() {
        mModel.checkUpdate(getVersionName(), new BaseCallback<UpdateInfoBean>() {
            @Override
            public void onSuccess(UpdateInfoBean data) {
                if (data==null) {
                    getView().showNoNeedUpdateDialog();
                } else {
                    getView().showSelectUpdateDialog(data);
                }
            }

            @Override
            public void onFail(String msg) {
                getView().showToast(msg);
            }
        });

    }
    /**
     * 获取版本信息
     */
    private String getVersionName()
    {
        // 获取packagemanager的实例
        PackageManager packageManager = getView().getContext().getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = null;
        try {
            packInfo = packageManager.getPackageInfo(getView().getContext().getPackageName(),0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return packInfo.versionName;
    }

    /**
     * APK下载
     */
    @Override
    public void versionUpdate(UpdateInfoBean bean) {
        createPD();
        // 启动Service 开始下载
        MeUpdateService.startUpdate(getView().getContext(), bean.getUrl(), "woxingwoxiu_"+bean.getVersion(), new MeUpdateService.OnProgressListener() {
            @Override
            public void onProgress(int progress) {
                //更新对话框进度条
                progressDialog.setProgress(progress);
            }

            @Override
            public void onSuccess(boolean isSuccess) {
                progressDialog.dismiss();
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
        progressDialog = new ProgressDialog(getView().getContext());
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("正在下载最新版本");
        progressDialog.setCancelable(false);// 设置是否可以通过点击Back键取消
        progressDialog.setCanceledOnTouchOutside(false);// 设置在点击Dialog外是否取消Dialog进度条
        progressDialog.setMax(100);
        progressDialog.show();
    }


    /**
     * 显示进度框
     * @param message
     */
    private void showProgressDialog(String message) {
        progressDialog = new ProgressDialog(getView().getContext());
        progressDialog.setMessage(message);
        progressDialog.setCancelable(true);
        progressDialog.show();
    }

    /**
     * 隐藏进度框
     */
    private void hideProgressDialog() {
        if (progressDialog != null)
            progressDialog.dismiss();
    }
}

package com.fcn.park.me.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;
import com.fcn.park.R;
import com.fcn.park.base.BaseActivity;
import com.fcn.park.databinding.MeVersionInfoActivityBinding;
import com.fcn.park.me.bean.VersionInfoBean;
import com.fcn.park.me.contract.MeVersionInfoContract;
import com.fcn.park.me.presenter.MeVersionInfoPresenter;
import com.tbruyelle.rxpermissions.RxPermissions;
import rx.functions.Action1;

/**
 * 个人中心-版本信息及更新
 */
public class MeVersionInfoActivity extends
        BaseActivity<MeVersionInfoActivityBinding, MeVersionInfoContract.View, MeVersionInfoPresenter> implements
        MeVersionInfoContract.View {

    private int versionCode;
    private String versionName;
    private AlertDialog.Builder mDialog;
    private RxPermissions mPermissions;
    private String[] mRequestPermissions = { Manifest.permission.WRITE_EXTERNAL_STORAGE};
    /**
     * 设置标题以及返回按钮显示
     */
    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
        setTitleText("版本信息");
    }
    /**
     * 初期化视图
     */
    @Override
    protected void initViews() {
        getVersion();
        setVersionInfo(versionName);
        mDataBinding.setPresenter(mPresenter);
        mPermissions = new RxPermissions(this);
    }
    /**
     * 设置视图布局
     */
    @Override
    protected int getLayoutId() {
        return R.layout.me_version_info_activity;
    }
    /**
     * 实例化presenter
     */
    @Override
    protected MeVersionInfoPresenter createPresenter() {
        return new MeVersionInfoPresenter();
    }
    /**
     * 获取版本code
     */
    @Override
    public int getVersionCode() {
        return versionCode;
    }
    /**
     * 获取版本名
     */
    @Override
    public String getVersionName() {
        return versionName;
    }
    /**
     * 已是最新版本，无需升级
     */
    @Override
    public void showNoNeedUpdateDialog() {
        mDialog = new AlertDialog.Builder(this);
        mDialog.setTitle("版本更新");
        mDialog.setMessage("当前为最新版本");
        mDialog.setNegativeButton("确定", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).create().show();
    }
    /**
     * 可以选择升级
     */
    @Override
    public void showSelectUpdateDialog(final VersionInfoBean bean) {
        mDialog = new AlertDialog.Builder(this);
        mDialog.setMessage(bean.getUpgradeinfo());
        mDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                //
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    mPermissions
                            .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            .subscribe(new Action1<Boolean>() {
                                @Override
                                public void call(Boolean isAgree) {
                                    if (isAgree) {
                                        mPresenter.versionUpdate(bean);
                                        Log.d("permission", "---- 请求通过----");
                                    } else {
                                        Toast.makeText(mActivity, "权限被拒绝", Toast.LENGTH_SHORT).show();
                                        for (String permission : mRequestPermissions) {
                                            if (ContextCompat.checkSelfPermission(mActivity, permission) == PackageManager.PERMISSION_DENIED) {
                                                Log.d("permission", "---- 权限被拒绝----" + permission);
                                            }
                                        }
                                    }
                                }
                            });
                } else {
                    mPresenter.versionUpdate(bean);
                }

                dialog.dismiss();
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).create().show();
    }
    /**
     * 必须升级
     */
    @Override
    public void showNeedUpdateDialog(final VersionInfoBean bean) {
        mDialog = new AlertDialog.Builder(this);
        mDialog.setMessage(bean.getUpgradeinfo());
        mDialog.setNegativeButton("立即更新", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    mPermissions
                            .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            .subscribe(new Action1<Boolean>() {
                                @Override
                                public void call(Boolean isAgree) {
                                    if (isAgree) {
                                        mPresenter.versionUpdate(bean);
                                        Log.d("permission", "---- 请求通过----");
                                    } else {
                                        Toast.makeText(mActivity, "权限被拒绝", Toast.LENGTH_SHORT).show();
                                        for (String permission : mRequestPermissions) {
                                            if (ContextCompat.checkSelfPermission(mActivity, permission) == PackageManager.PERMISSION_DENIED) {
                                                Log.d("permission", "---- 权限被拒绝----" + permission);
                                            }
                                        }
                                    }
                                }
                            });
                } else {
                    mPresenter.versionUpdate(bean);
                }
                dialog.dismiss();
            }
        }).setCancelable(false).create().show();
    }
    /**
     * 设置版本信息
     */
    @Override
    public void setVersionInfo(String code) {
        mDataBinding.versionCode.setText(code);
    }

    /**
     * 获取版本信息
     */
    private void getVersion()
    {
        // 获取packagemanager的实例
        PackageManager packageManager = getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = null;
        try {
            packInfo = packageManager.getPackageInfo(getPackageName(),0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        versionName = packInfo.versionName;
        versionCode = packInfo.versionCode;
    }

}

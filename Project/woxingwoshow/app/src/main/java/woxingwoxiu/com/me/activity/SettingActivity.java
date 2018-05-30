package woxingwoxiu.com.me.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.TextView;
import com.tbruyelle.rxpermissions2.RxPermissions;
import io.reactivex.functions.Consumer;
import woxingwoxiu.com.R;
import woxingwoxiu.com.base.BaseActivity;
import woxingwoxiu.com.databinding.ActivityMeSettingBinding;
import woxingwoxiu.com.me.bean.UpdateInfoBean;
import woxingwoxiu.com.me.contract.SettingContract;
import woxingwoxiu.com.me.presenter.SettingPresenter;
import woxingwoxiu.com.util.DataCleanUtils;


public class SettingActivity extends BaseActivity<ActivityMeSettingBinding, SettingPresenter> implements SettingContract.View {

    /** 缓存大小 */
    private TextView tvCachesSize;

    private AlertDialog.Builder mDialog;
    private RxPermissions mPermissions;
    private String[] mRequestPermissions = { Manifest.permission.WRITE_EXTERNAL_STORAGE};

    /**
     * 启动当前的Activity
     * @param context
     */
    public static void actionStart(Context context) {
        Intent intent = new Intent(context, SettingActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected SettingPresenter createPresenter() {
        return new SettingPresenter();
    }

    @Override
    protected void initViews() {
        mDataBinding.setPresenter(mPresenter);
        // 获取缓存控件
        tvCachesSize = (TextView) findViewById(R.id.cachesSize);
        // 计算缓存
        String cachesSize = DataCleanUtils.getTotalCacheSize(this);
        // 把缓存容量设定到画面
        tvCachesSize.setText(cachesSize);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_me_setting;
    }

    /**
     * 刷新画面
     */
    @Override
    public void refresh(int type) {
        // 如果点击的是清空缓存
        if (type == 1) {
            // 把缓存容量设定到画面
            tvCachesSize.setText("0.00MB");
        }
    }

    @Override
    public void showSelectUpdateDialog(final UpdateInfoBean bean) {
        mDialog = new AlertDialog.Builder(this);
        mDialog.setMessage(bean.getDescription());
        mDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                //
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    mPermissions
                            .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            .subscribe(new Consumer<Boolean>() {
                                @Override
                                public void accept(Boolean isAgree) {
                                    if (isAgree) {
                                        mPresenter.versionUpdate(bean);
                                        Log.d("permission", "---- 请求通过----");
                                    } else {
                                        showToast("权限被拒绝");
                                        for (String permission : mRequestPermissions) {
                                            if (ContextCompat.checkSelfPermission(SettingActivity.this, permission) == PackageManager.PERMISSION_DENIED) {
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
}

package com.fcn.park.base.widget;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.fcn.park.R;
import com.fcn.park.base.SinglePictureSelectHelper;
import com.fcn.park.databinding.SelectPhotoPopBinding;
import com.tbruyelle.rxpermissions.RxPermissions;

import rx.functions.Action1;


/**
 * 作者：吕振鹏
 * 创建时间：10月19日
 * 时间：14:16
 * 版本：v1.0.0
 * 类描述：一个用于单选图片的PopWindow
 * 使用方法1.创建一个该对象
 * 2.调用该对象的crateHelper();方法
 * 3.在Activity中的onActivityResult();中调用{@link SinglePictureSelectHelper#onActivityResult(int, int, Intent, ImageView, boolean)}方法
 * 具体的{@link SinglePictureSelectHelper}对象可以通过该对象中的{@link #getSelectHelper()}方法获取
 * 修改时间：
 */

public class SinglePictureSelectPopWindow extends AddPopWindow {

    private Activity mActivity;
    private SinglePictureSelectHelper mSinglePictureSelectHelper;
    private RxPermissions mPermissions;
    private String[] mRequestPermissions = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    /**
     * 初始化一个PopupWindow
     *
     * @param activity 上下文对象
     */
    public SinglePictureSelectPopWindow(Activity activity) {
        super(activity, R.layout.layout_pop_select_photo);

        mActivity = activity;
        mPermissions = new RxPermissions(activity);

        setAnimationStyle(R.style.PopWindow_y_anim_style);

        SelectPhotoPopBinding selectPhotoPopBinding = SelectPhotoPopBinding.bind(getWindowRootView());

        PopClickListener listener = new PopClickListener();
        selectPhotoPopBinding.btnCancel.setOnClickListener(listener);
        selectPhotoPopBinding.tvOpenCamera.setOnClickListener(listener);
        selectPhotoPopBinding.tvOpenAlbum.setOnClickListener(listener);
    }

    public void createHelper() {
        if (mSinglePictureSelectHelper == null)
            mSinglePictureSelectHelper = new SinglePictureSelectHelper(mActivity);

    }

    public SinglePictureSelectHelper getSelectHelper() {
        createHelper();
        return mSinglePictureSelectHelper;
    }

    /**
     * PopWindow按钮的点击事件
     */
    private class PopClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_cancel:
                    break;
                case R.id.tv_open_camera:
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        mPermissions
                                .request(mRequestPermissions)
                                .subscribe(new Action1<Boolean>() {
                                    @Override
                                    public void call(Boolean isAgree) {
                                        if (isAgree) {
                                            if (mSinglePictureSelectHelper != null) {
                                                mSinglePictureSelectHelper.openCameraForFile();
                                            }
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
                        if (mSinglePictureSelectHelper != null) {
                            mSinglePictureSelectHelper.openCameraForFile();
                        }
                    }

                    break;
                case R.id.tv_open_album:
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        mPermissions
                                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                .subscribe(new Action1<Boolean>() {
                                    @Override
                                    public void call(Boolean isAgree) {
                                        if (isAgree) {
                                            if (mSinglePictureSelectHelper != null)
                                                mSinglePictureSelectHelper.openAlbum();
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
                        if (mSinglePictureSelectHelper != null)
                            mSinglePictureSelectHelper.openAlbum();
                    }
                    break;
            }
            closePopupWindow();
        }
    }

}

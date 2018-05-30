package woxingwoxiu.com.me.presenter;

import android.content.Intent;

import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerActivity;
import woxingwoxiu.com.base.BasePresenter;
import woxingwoxiu.com.me.activity.QRCodeGenerateActivity;
import woxingwoxiu.com.me.activity.QRCodeScanActivity;
import woxingwoxiu.com.me.contract.QRCodeScanContract;

import static android.support.v4.app.ActivityCompat.startActivityForResult;

/**
 * Created by 860115025 on 2018/05/15.
 */

public class QRCodeScanPresenter extends BasePresenter<QRCodeScanContract.View> implements QRCodeScanContract.Presenter  {
    /**
     * 返回前一画面
     */
    @Override
    public void onBack() {
        getView().showToast("返回前一画面");
        // 关闭当前的Activity，返回上一个画面
        getView().closeActivity();
    }

    /**
     * 查看我的二维码
     */
    @Override
    public void onShowQrCode() {
        // 添加表示二维码必要的参数
        getView().actionStartActivity(QRCodeGenerateActivity.class);
    }
}

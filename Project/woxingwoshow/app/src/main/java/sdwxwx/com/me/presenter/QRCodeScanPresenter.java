package sdwxwx.com.me.presenter;

import android.content.Intent;

import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerActivity;
import sdwxwx.com.base.BasePresenter;
import sdwxwx.com.me.activity.QRCodeGenerateActivity;
import sdwxwx.com.me.activity.QRCodeScanActivity;
import sdwxwx.com.me.contract.QRCodeScanContract;

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

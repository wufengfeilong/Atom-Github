package woxingwoxiu.com.me.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Vibrator;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.tbruyelle.rxpermissions2.RxPermissions;
import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.QRCodeDecoder;
import cn.bingoogolapple.qrcode.zxing.ZXingView;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import woxingwoxiu.com.R;
import woxingwoxiu.com.base.BaseActivity;
import woxingwoxiu.com.databinding.ActivityQrcodeScanBinding;
import woxingwoxiu.com.me.contract.QRCodeScanContract;
import woxingwoxiu.com.me.presenter.QRCodeScanPresenter;
import woxingwoxiu.com.util.PhotoUtil;

/**
 * 二维码扫描画面
 * Created by 860115025 on 2018/05/15.
 */
public class QRCodeScanActivity extends BaseActivity<ActivityQrcodeScanBinding,QRCodeScanPresenter> implements QRCodeScanContract.View,QRCodeView.Delegate {
    /** class名称 */
    private static final String TAG = QRCodeScanActivity.class.getSimpleName();
    /** 访问相册的请求码 */
    private static final int REQUEST_CODE_CHOOSE_QRCODE_FROM_GALLERY = 666;
    /** 二维码扫描区域 */
    private QRCodeView mQRCodeView;
    /** 选择照片的URL */
    private Uri uri;

    @Override
    protected QRCodeScanPresenter createPresenter() {
        return new QRCodeScanPresenter();
    }

    /**
     * 画面初期化
     */
    @Override
    protected void initViews() {
        // presenter定义
        mDataBinding.setPresenter(mPresenter);
        // 获取二维码扫描区域
        mQRCodeView = (ZXingView) findViewById(R.id.zxingview);
        mQRCodeView.setDelegate(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // 画面启动后即进入扫描状态
        // RxPermissions询问相机的使用权限
        RxPermissions rxPermissions = new RxPermissions(QRCodeScanActivity.this);
        rxPermissions.request(Manifest.permission.CAMERA)
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }
                    @Override
                    public void onNext(Boolean value) {
                        // 如果已经给予相机的使用权限
                        if(value){
                            // 打开照相机
                            mQRCodeView.startCamera();
                            mQRCodeView.showScanRect();
                            // 开始扫描二维码
                            mQRCodeView.startSpot();
                        }else {
                            showNormalDialog("照相机");
                        }
                    }
                    @Override
                    public void onError(Throwable e) {
                    }
                    @Override
                    public void onComplete() {
                    }
                });
    }
    @Override
    protected void onStop() {
        mQRCodeView.stopCamera();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mQRCodeView.onDestroy();
        super.onDestroy();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_qrcode_scan;
    }

    /**
     * 二维码扫描成功后的回调方法
     * @param result
     */
    @Override
    public void onScanQRCodeSuccess(String result) {
        // 停止扫描
        mQRCodeView.stopSpot();
        // 停止相机
        mQRCodeView.stopCamera();
        Intent intent = new Intent();
        // TODO 他人主页完成后，修改为直接跳转到该画面即可。
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse(result);
        intent.setData(content_url);
        startActivity(intent);
    }
    @Override
    public void onScanQRCodeOpenCameraError() {
        Log.e(TAG, "打开相机出错");
    }
    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }
    /**
     *从相册选择图片进行扫描
     */
    public void chooseQrcdeFromGallery(View v) {
        // RxPermissions询问相机的使用权限
        RxPermissions rxPermissions = new RxPermissions(QRCodeScanActivity.this);
        rxPermissions.request(Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }
                    @Override
                    public void onNext(Boolean value) {
                        // 如果已经给予读取内存的使用权限
                        if(value){
                            Intent innerIntent = new Intent(Intent.ACTION_GET_CONTENT);
                            innerIntent.setType("image/*");
                            startActivityForResult(innerIntent, REQUEST_CODE_CHOOSE_QRCODE_FROM_GALLERY);
                        }else {
                            showNormalDialog("照片读取");
                        }
                    }
                    @Override
                    public void onError(Throwable e) {
                    }
                    @Override
                    public void onComplete() {
                    }
                });
    }

    /**
     * 通过相片识别二维码
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mQRCodeView.showScanRect();
        // 如果识别陈宫
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_CHOOSE_QRCODE_FROM_GALLERY) {
            if (data == null) {
                return;
            }
            uri = data.getData();
            int sdkVersion = Integer.valueOf(Build.VERSION.SDK);
            String picturePath = "";
            final String photoPath;
            if (sdkVersion >= 19) {
                picturePath = PhotoUtil.getPath_above19(this, this.uri);
            } else {
                picturePath = PhotoUtil.getFilePath_below19(this, this.uri);
            }
            photoPath = picturePath;

            /**
             * 启动线程，进行二维码解析
             */
            new AsyncTask<Void, Void, String>() {
                @Override
                protected String doInBackground(Void... params) {
                    return QRCodeDecoder.syncDecodeQRCode(photoPath);
                }
                @Override
                protected void onPostExecute(String result) {
                    if (TextUtils.isEmpty(result)) {
                        Toast.makeText(QRCodeScanActivity.this, "未发现二维码", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent intent = new Intent();
                        // TODO 他人主页完成后，修改为直接跳转到该画面即可。
                        intent.setAction("android.intent.action.VIEW");
                        Uri content_url = Uri.parse(result.toString());
                        intent.setData(content_url);
                        startActivity(intent);
                    }
                }
            }.execute();
        }
    }

    /**
     * 对话框
     */
    private void showNormalDialog(String message){
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(QRCodeScanActivity.this);
        normalDialog.setTitle("权限申请");
        normalDialog.setMessage("未获得" + message +"权限，是否去设置中授予我行我秀该权限？");
        normalDialog.setPositiveButton("知道了",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mQRCodeView.hiddenScanRect();
                    }
                });
        normalDialog.setNegativeButton("去设置",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Uri packageURI = Uri.parse("package:woxingwoxiu.com");
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
                        startActivity(intent);
                    }
                });
        // 显示
        normalDialog.show();
    }
}

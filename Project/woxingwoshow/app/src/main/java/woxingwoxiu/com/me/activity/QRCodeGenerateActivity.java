package woxingwoxiu.com.me.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.Settings;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.tbruyelle.rxpermissions2.RxPermissions;

import cn.bingoogolapple.qrcode.core.BGAQRCodeUtil;
import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import woxingwoxiu.com.R;
import woxingwoxiu.com.base.BaseActivity;
import woxingwoxiu.com.databinding.ActivityQrcodeGenerateBinding;
import woxingwoxiu.com.me.contract.QRCodeGenerateContract;
import woxingwoxiu.com.me.presenter.QRCodeGeneratePresenter;
import woxingwoxiu.com.widget.OnekeyShare;
import woxingwoxiu.com.widget.ShareFrag;

import woxingwoxiu.com.util.ImageUtil;

/**
 * Created by 860115025 on 2018/05/10.
 */
public class QRCodeGenerateActivity extends BaseActivity<ActivityQrcodeGenerateBinding,QRCodeGeneratePresenter> implements QRCodeGenerateContract.View {

    private ImageView imgQrcode;


    @Override
    protected QRCodeGeneratePresenter createPresenter() {
        return new QRCodeGeneratePresenter();
    }

    @Override
    protected void initViews() {
        mDataBinding.setPresenter(mPresenter);
        imgQrcode = (ImageView) findViewById(R.id.qrcode);
        showLoading();
        new AsyncTask<Void, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground(Void... params) {
                // TODO 暂定URL，后期根据接口传递相关参数
                String url = "https://reflow.huoshan.com/share/user/98167759466/?qrscheme=sslocal%3A%2F%2Fprofile%3Fid%3D98167759466&utm_campaign=client_share&share_ht_uid=98167759466&did=46545705378&app=live_stream&iid=31989426298&utm_medium=huoshan_ios";
                return QRCodeEncoder.syncEncodeQRCode(url, BGAQRCodeUtil.dp2px(QRCodeGenerateActivity.this, 300));
            }

            /**
             * 二维码生成之后，用来设定二维码
             * @param bitmap
             */
            @Override
            protected void onPostExecute(Bitmap bitmap) {
                if (bitmap != null) {
                    imgQrcode.setImageBitmap(bitmap);
                } else {
                    Toast.makeText(QRCodeGenerateActivity.this, "生成二维码失败", Toast.LENGTH_SHORT).show();
                }
                hideLoading();
            }
        }.execute();
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_qrcode_generate;
    }

    /**
     * 二维码分享
     * @param v
     */
    public void onShare(View v) {
        // 画面启动后即进入扫描状态
        // RxPermissions询问相机的使用权限
        RxPermissions rxPermissions = new RxPermissions(QRCodeGenerateActivity.this);
        rxPermissions.request(Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }
                    @Override
                    public void onNext(Boolean value) {
                        // 如果已经给予相机的使用权限
                        if(value){
                            // 先生成图片
                            Bitmap map = ImageUtil.onCut(QRCodeGenerateActivity.this, 52f, 104f);
                            // 把图片保存到路径下
                            String imagePath =  ImageUtil.saveBitmap(QRCodeGenerateActivity.this.getContext(), map);
                            // 调用共通分享dialog
                            FragmentManager fm = getFragmentManager();
                            ShareFrag shareFrag = new ShareFrag();
                            OnekeyShare webBean = new OnekeyShare();
                            // 分享到QQ必须要设定的字段
                            webBean.setImagePath(imagePath);
                            // 设定不需要表示的按钮
                            webBean.setLayoutId(R.layout.qr_code_share);
                            shareFrag.setSilent(false);
                            shareFrag.setShareParamsMap(webBean.getParams());
                            shareFrag.show(fm, null);
                        }else {
                            showNormalDialog("读写存储空间");
                        }
                    }
                    @Override
                    public void onError(Throwable e) {
                    }
                    @Override
                    public void onComplete() {
                    }
                });
        // 动态申请文件读写权限
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
                new AlertDialog.Builder(QRCodeGenerateActivity.this);
        normalDialog.setTitle("权限申请");
        normalDialog.setMessage("未获得" + message +"权限，是否去设置中授予我行我秀该权限？");
        normalDialog.setPositiveButton("知道了",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
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

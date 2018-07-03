package sdwxwx.com.me.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.Settings;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.HashMap;

import cn.bingoogolapple.qrcode.core.BGAQRCodeUtil;
import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder;
import cn.emay.sdk.util.StringUtil;
import cn.sharesdk.framework.Platform;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import sdwxwx.com.R;
import sdwxwx.com.base.BaseActivity;
import sdwxwx.com.base.BaseCallback;
import sdwxwx.com.cons.Constant;
import sdwxwx.com.databinding.ActivityQrcodeGenerateBinding;
import sdwxwx.com.http.APIClient;
import sdwxwx.com.login.utils.LoginHelper;
import sdwxwx.com.me.contract.QRCodeGenerateContract;
import sdwxwx.com.me.model.QRCodeGenerateModel;
import sdwxwx.com.me.presenter.QRCodeGeneratePresenter;
import sdwxwx.com.util.LoginUtil;
import sdwxwx.com.widget.OnekeyShare;
import sdwxwx.com.widget.ShareFrag;
import sdwxwx.com.util.ImageUtil;

/**
 * Created by 860115025 on 2018/05/10.
 */
public class QRCodeGenerateActivity extends BaseActivity<ActivityQrcodeGenerateBinding,QRCodeGeneratePresenter> implements QRCodeGenerateContract.View {

    /** 二维码生成Model */
    private QRCodeGenerateModel mModel;
    /** 二维码的URL */
    private String url;

    @Override
    protected QRCodeGeneratePresenter createPresenter() {
        return new QRCodeGeneratePresenter();
    }

    /**
     * 初始化
     */
    @Override
    protected void initViews() {
        // 绑定Presenter
        mDataBinding.setPresenter(mPresenter);
        // 获取二维码生成Model
        mModel = new QRCodeGenerateModel();

        // 显示正在生成dialog
        showLoading();
        new AsyncTask<Void, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground(Void... params) {
                // 定义MAP
                HashMap<String,Object> mHashMap = new HashMap<>();
                // 获取时间戳并放置到MAP中
                long times = APIClient.getTimes();
                mHashMap.put("timestamp", times);
                // 把UserID放入
                mHashMap.put("recommender", LoginHelper.getInstance().getUserId());
                // 获取签名
                String signature = APIClient.getSign(mHashMap);
                url = Constant.HTTP_BASE_HOST + "recommend/register?recommender=" + LoginHelper.getInstance().getUserId() + "&timestamp=" + times +"&signature=" + signature;
                return QRCodeEncoder.syncEncodeQRCode(url, BGAQRCodeUtil.dp2px(QRCodeGenerateActivity.this, 300));
            }

            /**
             * 二维码生成之后，用来设定二维码
             * @param bitmap
             */
            @Override
            protected void onPostExecute(Bitmap bitmap) {
                // 如果文件不为空，则对画面表示内容进行设定
                if (mSharedPreferences != null) {
                    // 头像地址
                    String avatarUrl = mSharedPreferences.getString(Constant.SP_AVATAR_URL, null);
                    // 昵称
                    String nickname = mSharedPreferences.getString(Constant.SP_NICK_NAME, null);
                    // 会员编号
                    String memberId = mSharedPreferences.getString(Constant.SP_MEMBER_ID, null);
                    // 利用Glide设定头像
                    RequestOptions options = new RequestOptions().error(R.drawable.default_header_image);
                    Glide.with(getContext()).load(avatarUrl).apply(options).into(mDataBinding.avatar);
                    // 设定昵称
                    mDataBinding.nickName.setText(nickname);
                    // 设定会员编号
                    mDataBinding.memberId.setText("账号：" + memberId);
                }
                // 如果生成的二维码不为空，则设置到二维码画面
                if (bitmap != null) {
                    mDataBinding.qrcode.setImageBitmap(bitmap);
                } else {
                    showToast("生成二维码失败");
                }
                mDataBinding.helpText.setText("使用「扫一扫」扫描上方二维码");
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
     */
    @Override
    public void shareQrCode() {
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
                            webBean.setShareType(Platform.SHARE_IMAGE);
                            // 设定要分享的图片路径
                            webBean.setImagePath(imagePath);
                            webBean.setTitle("我行我秀");
                            webBean.setTitleUrl(url);
                            webBean.setText("我行我秀");
                            webBean.setSite(url);
                            webBean.setSiteUrl(url);
                            // 设定不需要表示的按钮
                            webBean.setLayoutId(R.layout.qr_code_share);
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
                        Uri packageURI = Uri.parse("package:sdwxwx.com");
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
                        startActivity(intent);
                    }
                });
        // 显示
        normalDialog.show();
    }
}
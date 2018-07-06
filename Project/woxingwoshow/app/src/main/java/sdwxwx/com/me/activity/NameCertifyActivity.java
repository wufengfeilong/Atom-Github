package sdwxwx.com.me.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.view.inputmethod.InputMethodManager;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import sdwxwx.com.R;
import sdwxwx.com.base.BaseActivity;
import sdwxwx.com.base.BaseCallback;
import sdwxwx.com.cons.Constant;
import sdwxwx.com.databinding.ActivityNameCertifyBinding;
import sdwxwx.com.login.utils.ImageUtils;
import sdwxwx.com.login.utils.RECheckUtils;
import sdwxwx.com.me.contract.NameCertifyContract;
import sdwxwx.com.me.model.SettingModel;
import sdwxwx.com.me.presenter.NameCertifyPresenter;
import sdwxwx.com.util.LoginUtil;
import sdwxwx.com.util.StringUtil;
import sdwxwx.com.widget.ToastUtil;

/**
 * 实名认证类
 */
public class NameCertifyActivity extends BaseActivity<ActivityNameCertifyBinding, NameCertifyPresenter> implements NameCertifyContract.View {

    /** 手持身份证照片 */
    private String imgPhotoOne;
    /** 独立身份证照片 */
    private String imgPhotoTwo;

    /**
     * 启动当前的Activity
     * @param context
     */
    public static void actionStart(Context context) {
        Intent intent = new Intent(context, NameCertifyActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected NameCertifyPresenter createPresenter() {
        return new NameCertifyPresenter();
    }

    @Override
    protected void initViews() {
        mDataBinding.setPresenter(mPresenter);
        // 获取焦点
        mDataBinding.realName.requestFocus();
        // 弹出软键盘
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                InputMethodManager inputManager =
                        (InputMethodManager)mDataBinding.realName.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.showSoftInput(mDataBinding.realName, 0);
            }
        }, 100);
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_name_certify;
    }

    /**
     * 弹出选择对话框
     */
    public void selectPhoto(int type) {
        Intent openAlbumIntent = new Intent(Intent.ACTION_GET_CONTENT);
        openAlbumIntent.setType("image/*");
        startActivityForResult(openAlbumIntent, type);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) { // 如果返回码是可以用的
            try {
                //将Uri图片转换为Bitmap
                Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(data.getData()));
                // 根据点击按钮的不同设定相应控件
                if (requestCode == 1) {
                    mDataBinding.certifyPhoneTou.setImageBitmap(bitmap);
                    mDataBinding.certifyPhoneTvOne.setText("上传成功");
                    imgPhotoOne = ImageUtils.savePhoto(bitmap, Environment
                            .getExternalStorageDirectory().getAbsolutePath(), String
                            .valueOf(System.currentTimeMillis()));
                } else {
                    mDataBinding.certifyPhoneTwo.setImageBitmap(bitmap);
                    mDataBinding.certifyPhoneTvTwo.setText("上传成功");
                    imgPhotoTwo = ImageUtils.savePhoto(bitmap, Environment
                            .getExternalStorageDirectory().getAbsolutePath(), String
                            .valueOf(System.currentTimeMillis()));
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * 进行实名认证
     */
    public void certificate() {
        // 如果真实姓名为空
        if (StringUtil.isEmpty(mDataBinding.realName.getText().toString())) {
            showCustomToast("请填写姓名");
            return;
        }
        // 如果身份证号为空
        if (StringUtil.isEmpty(mDataBinding.digitNumber.getText().toString())) {
            showCustomToast("请填写身份证号");
            return;
        }
        // 如果手持身份证照片未上传
        if (!"上传成功".equals(mDataBinding.certifyPhoneTvOne.getText().toString())) {
            showCustomToast("请上传手持身份证照片");
            return;
        }
        // 如果独立身份证照片未上传
        if (!"上传成功".equals(mDataBinding.certifyPhoneTvTwo.getText().toString())) {
            showCustomToast("请上传独立身份证照片");
            return;
        }
        // 判断身份证格式
        if (!RECheckUtils.checkIdCard(mDataBinding.digitNumber.getText().toString())) {
            showCustomToast("对不起，身份证格式不合法！");
            return;
        }
        showLoading();
        // 调用接口，进行数据更新操作
        SettingModel.certificate(LoginUtil.getStrUserInfo(Constant.SP_MEMBER_ID,this),
                mDataBinding.realName.getText().toString(),
                mDataBinding.digitNumber.getText().toString(),
                imgPhotoOne, imgPhotoTwo, new BaseCallback<List<String>>() {
                    @Override
                    public void onSuccess(List<String> data) {
                        showCustomToast("保存成功");
                        hideLoading();
                        // 跳转到我的HOME
                        TimerTask task = new TimerTask() {
                            @Override
                            public void run() {
                                finish();
                            }
                        };
                        // 延迟二秒再关闭画面
                        Timer timer = new Timer();
                        timer.schedule(task, 1000);
                    }

                    @Override
                    public void onFail(String msg) {
                        hideLoading();
                        showToast(msg);
                    }
                });
    }
    @Override
    protected void onPause() {
        super.onPause();
        ToastUtil.cancelToast();
    }
}

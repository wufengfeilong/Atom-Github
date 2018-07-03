package sdwxwx.com.login.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.content.FileProvider;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.ImageView;

import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import sdwxwx.com.R;
import sdwxwx.com.base.BaseActivity;
import sdwxwx.com.databinding.ActivityDataEditingBinding;
import sdwxwx.com.login.bean.DataEditingBean;
import sdwxwx.com.login.contract.DataEditingContract;
import sdwxwx.com.login.presenter.DataEditingPresenter;
import sdwxwx.com.login.utils.ActivityCollector;
import sdwxwx.com.login.utils.ImageUtils;
import static sdwxwx.com.cons.Constant.SP_FILE_NAME;
import static sdwxwx.com.cons.Constant.SP_LOGIN_TOKEN;

/**
 * Created by 丁胜胜 on 2018/05/14.
 * 类描述：注册时候的资料编辑界面
 */
public class DataEditingActivity
        extends BaseActivity<ActivityDataEditingBinding,DataEditingPresenter>
        implements DataEditingContract.View {

    protected static final int CHOOSE_PICTURE = 0;
    protected static final int TAKE_PICTURE = 1;
    private static final int CROP_SMALL_PICTURE = 2;
    protected static Uri tempUri;
    private ImageView iv_personal_icon;
    private String mPublicPhotoPath;
    protected static Uri mUritempFile;
    private DataEditingBean dataEditingBean;

    @Override
    protected void initViews() {
        mDataBinding.setDataEditingPresenter(mPresenter);
        iv_personal_icon = findViewById(R.id.iv_personal_icon);
        initFieldBeforeMethods();

        ActivityCollector.addActivity(this);//将活动添加到活动收集器

        mDataBinding.loginNextOk.setEnabled(false);//初始化完成按钮不能点击
        // 对昵称进行监听
        mDataBinding.loginName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mDataBinding.loginNextOk.setEnabled(false);//初始化按钮不能点击
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s) && dataEditingBean.getAvatarFile() != null ) {
                    mDataBinding.loginNextOk.setEnabled(true);
                }else{
                    mDataBinding.loginNextOk.setEnabled(false);
                }
            }
        });


    }

    @Override
    protected DataEditingPresenter createPresenter() {
        return new DataEditingPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_data_editing;
    }

    public void showChoosePicDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("设置头像");
        String[] items = {"选择本地照片", "拍照"};
        builder.setNegativeButton("取消", null);
        builder.setItems(items, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case CHOOSE_PICTURE: // 选择本地照片
                        Intent openAlbumIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                        openAlbumIntent.setType("image/*");
                        startActivityForResult(openAlbumIntent, CHOOSE_PICTURE);
                        break;
                    case TAKE_PICTURE: // 拍照
                        takePicture();
                        break;
                }
            }
        });
        builder.create().show();
    }

    //动态申请权限
    @Override
    public void GetPermission () {
        // RxPermissions询问访问摄像头以及读写存储的权限
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }
                    @Override
                    public void onNext(Boolean value) {
                        // 如果已经给予相机的使用权限
                        if(value){
                            //弹出Dialog
                            showChoosePicDialog();
                        }else {
                            showNormalDialog("照相机以及读写存储");
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
                new AlertDialog.Builder(this);
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

    private void takePicture() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //判断是否有相机应用
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            //创建临时图片文件
            File photoFile = null;
            try {
                photoFile = ImageUtils.createPublicImageFile();
                mPublicPhotoPath = photoFile.getAbsolutePath();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //设置Action为拍照
            if (photoFile != null) {
                takePictureIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                //这里加入flag
                takePictureIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                tempUri = FileProvider.getUriForFile(this, "sdwxwx.com.fileprovider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
                startActivityForResult(takePictureIntent, TAKE_PICTURE);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) { // 如果返回码是可以用的
            switch (requestCode) {
                case TAKE_PICTURE: //拍照
                    startPhotoZoomTwo(tempUri); // 开始对图片进行裁剪处理
                    break;
                case CHOOSE_PICTURE: //选择相册
                    startPhotoZoom(data.getData()); // 开始对图片进行裁剪处理
                    break;
                case CROP_SMALL_PICTURE:
                    if (data != null) {

                        try {
                            //将Uri图片转换为Bitmap
                            Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(mUritempFile));
                            iv_personal_icon.setImageBitmap(bitmap);
                            if (!TextUtils.isEmpty(mDataBinding.loginName.getText())) {
                                mDataBinding.loginNextOk.setEnabled(true);
                            }
                            uploadPic(bitmap);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
//                        setImageToView(data);
                    }
                    break;
            }
        }
    }

    /**
     * 裁剪图片方法实现：本地
     *
     * @param uri
     */
    protected void startPhotoZoom(Uri uri) {
        if (uri == null) {
            Log.i("tag", "The uri is not exist.");
        }
        tempUri = uri;
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

        // crop是设置在开启的intent中设置显示的view可以剪裁
        intent.putExtra("crop", "true");

        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);

        /**
         .        * 此方法返回的图片只能是小图片（sumsang测试为高宽160px的图片）
         * 故将图片保存在Uri中，调用时将Uri转换为Bitmap，此方法还可解决miui系统不能return data的问题
         */
//        intent.putExtra("return-data", true);  //原本的裁剪方式

        //uritempFile为Uri类变量，实例化uritempFile，转化为uri方式解决问题
        mUritempFile = Uri.parse("file://" + "/" + Environment.getExternalStorageDirectory().getPath() + "/" + "small.jpg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mUritempFile);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());


        startActivityForResult(intent, CROP_SMALL_PICTURE);
    }

    /**
     * 裁剪图片方法实现Two ：拍照
     *
     * @param uri
     */
    protected void startPhotoZoomTwo(Uri uri) {
        if (uri == null) {
            Log.i("tag", "The uri is not exist.");
        }
        tempUri = uri;
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
//        intent.putExtra("return-data", true);

        mUritempFile = Uri.parse("file://" + "/" + Environment.getExternalStorageDirectory().getPath() + "/" + "small.jpg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mUritempFile);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());

        startActivityForResult(intent, CROP_SMALL_PICTURE);
    }

    /**
     * 保存裁剪之后的图片数据
     *
     * @param
     *
     */
    protected void setImageToView(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {

            Bitmap photo = extras.getParcelable("data");
            photo = ImageUtils.toRoundBitmap(photo); // 这个时候的图片已经被处理成圆形的了
            iv_personal_icon.setImageBitmap(photo);
//            uploadPic(photo);
        }
    }

    private void uploadPic(Bitmap bitmap) {
        // 上传至服务器
        // ... 可以在这里把Bitmap转换成file，然后得到file的url，做文件上传操作
        // 注意这里得到的图片已经是圆形图片了
        // bitmap是没有做个圆形处理的，但已经被裁剪了

        String imagePath = ImageUtils.savePhoto(bitmap, Environment
                .getExternalStorageDirectory().getAbsolutePath(), String
                .valueOf(System.currentTimeMillis()));
        Log.e("imagePath", imagePath+"");
        if(imagePath != null){
            File file = new File(imagePath);
            dataEditingBean.setAvatarFile(file);
        }
    }

    @Override
    public void savaObject() {
        SharedPreferences sp = getSharedPreferences(SP_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();//获取编辑器
        editor.putString(SP_LOGIN_TOKEN,"01");
        editor.commit();
    }

    @Override
    public DataEditingBean getBean() {
        return dataEditingBean;
    }

    /**
     * @param context
     * @param hashMap
     */
    public static void  actionStart(Context context, HashMap<String,Object> hashMap) {
        Intent intent = new Intent(context, DataEditingActivity.class);
        intent.putExtra("mobile", hashMap.get("mobile").toString());
        intent.putExtra("code", hashMap.get("code").toString());
        intent.putExtra("wechat_id", hashMap.get("wechat_id").toString());
        intent.putExtra("qq_id", hashMap.get("qq_id").toString());
        intent.putExtra("city_id",hashMap.get("city_id").toString());
        context.startActivity(intent);
    }

    protected void initFieldBeforeMethods() {
        Intent intent = getIntent();
        dataEditingBean = new DataEditingBean();
        dataEditingBean.setMobile(intent.getStringExtra("mobile"));
        dataEditingBean.setCode(intent.getStringExtra("code"));
        dataEditingBean.setWechatId(intent.getStringExtra("wechat_id"));
        dataEditingBean.setQqId(intent.getStringExtra("qq_id"));
        dataEditingBean.setCity_id(intent.getStringExtra("city_id"));
    }

    @Override
    public String getInputUserName(){
        return mDataBinding.loginName.getText().toString();
    }
}

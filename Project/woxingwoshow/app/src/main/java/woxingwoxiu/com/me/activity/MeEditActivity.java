package woxingwoxiu.com.me.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.common.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;
import woxingwoxiu.com.R;
import woxingwoxiu.com.base.BaseActivity;
import woxingwoxiu.com.databinding.ActivityMeEditBinding;
import woxingwoxiu.com.login.activity.DataEditingActivity;
import woxingwoxiu.com.login.utils.ImageUtils;
import woxingwoxiu.com.me.contract.MeEditContract;
import woxingwoxiu.com.me.presenter.MeEditPresenter;

import static woxingwoxiu.com.cons.Constant.SP_FILE_NAME;
import static woxingwoxiu.com.cons.Constant.SP_LOGIN_TOKEN;


public class MeEditActivity extends BaseActivity<ActivityMeEditBinding, MeEditPresenter> implements MeEditContract.View {

    /**
     * 启动当前的Activity
     * @param context
     */
    public static void actionStart(Context context) {
        Intent intent = new Intent(context, MeEditActivity.class);
        context.startActivity(intent);
    }

    protected static final int CHOOSE_PICTURE = 0;
    protected static final int TAKE_PICTURE = 1;
    private static final int CROP_SMALL_PICTURE = 2;
    protected static Uri tempUri;
    private Intent intent  = null;
    private String TAG = "=== DataEditingActivity ===";
    private ImageView iv_personal_icon;
    private static final int SUCCESSCODE = 100;
    private String mPublicPhotoPath;

    @Override
    protected MeEditPresenter createPresenter() {
        return new MeEditPresenter();
    }

    @Override
    protected void initViews() {
        mDataBinding.setPresenter(mPresenter);
        iv_personal_icon = (ImageView) findViewById(R.id.me_edit_iv);
    }

    @Override
    protected int getLayoutId() {

        return R.layout.activity_me_edit;
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
                        Intent openAlbumIntent = new Intent(
                                Intent.ACTION_GET_CONTENT);
                        openAlbumIntent.setType("image/*");
                        startActivityForResult(openAlbumIntent, CHOOSE_PICTURE);
                        break;
                    case TAKE_PICTURE: // 拍照
                        showTakePicture();
                        break;
                }
            }
        });
        builder.create().show();
    }

    //拍照的功能
    private void showTakePicture() {
        PermissionGen.with(MeEditActivity.this)
                .addRequestCode(SUCCESSCODE)
                .permissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                )
                .request();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    //权限申请成功
    @PermissionSuccess(requestCode = SUCCESSCODE)
    //权限申请成功
    public void doSomething() {
        //申请成功
        takePicture();
    }
    @PermissionFail(requestCode = SUCCESSCODE)
    public void doFailSomething() {
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
                tempUri = FileProvider.getUriForFile(this, "woxingwoxiu.com.fileprovider", photoFile);
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
                case TAKE_PICTURE:
                    startPhotoZoomTwo(tempUri); // 开始对图片进行裁剪处理
                    break;
                case CHOOSE_PICTURE:
                    startPhotoZoom(data.getData()); // 开始对图片进行裁剪处理
                    break;
                case CROP_SMALL_PICTURE:
                    if (data != null) {
                        setImageToView(data); // 让刚才选择裁剪得到的图片显示在界面上
                    }
                    break;
            }
        }
    }

    /**
     * 裁剪图片方法实现
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
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, CROP_SMALL_PICTURE);
    }

    /**
     * 裁剪图片方法实现Two
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
        intent.putExtra("return-data", true);
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
            uploadPic(photo);
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
            // 拿着imagePath上传了
            // ...
        }
    }

    @Override
    public void savaObject() {
        SharedPreferences sp = getSharedPreferences(SP_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();//获取编辑器
        editor.putString(SP_LOGIN_TOKEN,"01");
        editor.commit();
    }

    /**
     * 创建日期选择对话框
     */
    public void showDateDialog() {
        // 获取当前系统日期
        Calendar nowdate = Calendar.getInstance();
        int mYear = nowdate.get(Calendar.YEAR);
        int mMonth = nowdate.get(Calendar.MONTH);
        int mDay = nowdate.get(Calendar.DAY_OF_MONTH);
        // 调用原生日期选择框
        new DatePickerDialog(this, onDateSetListener, mYear, mMonth, mDay).show();
    }

    /**
     * 监听
     */
    private DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int day) {
            // 获取日期控件
            TextView date_textview = (TextView) findViewById(R.id.birth_date);
            // 格式化日期
            String days = new StringBuffer().append(year).append("-").append(String.format("%02d",month+1)).append("-").append(String.format("%02d",day)).toString();
            date_textview.setText(days);
        }
    };

    /**
     * 性别选择器
     */
    public void showSexChooseDialog() {
        final String[] sexArry = new String[]{ "男", "女","取消"};// 性别选择
        AlertDialog.Builder builder = new AlertDialog.Builder(this);// 自定义对话框
        builder.setTitle("请选择性别");
        builder.setItems(sexArry, new DialogInterface.OnClickListener() {// 2默认的选中

            @Override
            public void onClick(DialogInterface dialog, int which) {// which是被选中的位置
                // 如果不是点击的取消
                if (which !=2 ) {
                    TextView changesex_textview = (TextView) findViewById(R.id.sex);
                    changesex_textview.setText(sexArry[which]);
                }
                dialog.dismiss();// 随便点击一个item消失对话框，不用点击确认取消
            }
        });
        builder.show();// 让弹出框显示
    }
}

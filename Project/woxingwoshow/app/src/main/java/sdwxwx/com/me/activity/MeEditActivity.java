package sdwxwx.com.me.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.content.FileProvider;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import sdwxwx.com.R;
import sdwxwx.com.base.BaseActivity;
import sdwxwx.com.bean.UserBean;
import sdwxwx.com.databinding.ActivityMeEditBinding;
import sdwxwx.com.login.utils.ImageUtils;
import sdwxwx.com.me.contract.MeEditContract;
import sdwxwx.com.me.presenter.MeEditPresenter;
import sdwxwx.com.util.LoginUtil;
import sdwxwx.com.widget.ToastUtil;


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
    private String TAG = "=== DataEditingActivity ===";
    private ImageView iv_personal_icon;
    private static final int SUCCESSCODE = 100;
    private String mPublicPhotoPath;
    protected static Uri mUritempFile;
    /** 图片的真实路径 */
    private String imagePath;
    @Override
    protected MeEditPresenter createPresenter() {
        return new MeEditPresenter();
    }

    @Override
    protected void initViews() {
        mDataBinding.setPresenter(mPresenter);
        iv_personal_icon = (ImageView) findViewById(R.id.me_edit_iv);
        // 设置画面的初期表示内容
        UserBean bean = LoginUtil.getLoginInfo(this);
        // 头像
        // 如果加载异常，则显示默认图片
        RequestOptions options = new RequestOptions().error(R.drawable.login_header);
        Glide.with(this).load(bean.getAvatar_url()).apply(options).into(mDataBinding.meEditIv);
        // 注册时间
        mDataBinding.createTime.setText(bean.getCreate_time());
        // 推荐人
        mDataBinding.recommenderId.setText(bean.getRecommender_id());
        // 昵称
        mDataBinding.nickName.setText(bean.getNickname());
        //设置EditText的光标在最后
        EditText et = (EditText)findViewById(R.id.nick_name);
        et.setSelection(et.getText().length());
        // 初始显示昵称长度
        mDataBinding.nickNameCount.setText(et.getText().length() + "/10");
        // 设置清空按钮点击事件
        mDataBinding.nickName.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // et.getCompoundDrawables()得到一个长度为4的数组，分别表示左右上下四张图片
                Drawable drawable = mDataBinding.nickName.getCompoundDrawables()[2];
                //如果右边没有图片，不再处理
                if (drawable == null)
                    return false;
                //如果不是按下事件，不再处理
                if (event.getAction() != MotionEvent.ACTION_UP)
                    return false;
                if (event.getX() > mDataBinding.nickName.getWidth()
                        - mDataBinding.nickName.getPaddingRight()
                        - drawable.getIntrinsicWidth()) {
                    Log.d(TAG, "Edit onTouch 点击位置 右侧按钮");
                    // 清空输入内容
                    mDataBinding.nickName.setText("");
                }
                return false;

            }
        });
        // 设置输入内容的监听事件
        mDataBinding.nickName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.d(TAG, "Edit afterTextChanged 输入内容变化");
                mDataBinding.nickNameCount.setText(editable.length() + "/10");
            }
        });
        // 性别
        mDataBinding.sex.setText(bean.getGender());
        // 生日
        mDataBinding.birthDate.setText(bean.getBirthday());
        // 个性签名
        mDataBinding.editText.setText(bean.getSignature());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_me_edit;
    }

    /**
     * 弹出拍照还是选择照片的对话框
     */
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
                            imagePath = ImageUtils.savePhoto(bitmap, Environment
                                    .getExternalStorageDirectory().getAbsolutePath(), String
                                    .valueOf(System.currentTimeMillis()));
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
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

    /**
     * 获取编辑的用户情报
     * @return
     */
    public UserBean getUserInfo() {
        UserBean bean = new UserBean();
        // 头像
        if (imagePath != null) {
            bean.setAvatar_url(imagePath.toString());
        }
        // 昵称
        bean.setNickname(((EditText)this.findViewById(R.id.nick_name)).getText().toString());
        // 性别
        bean.setGender(((TextView)this.findViewById(R.id.sex)).getText().toString());
        // 生日
        bean.setBirthday(((TextView)this.findViewById(R.id.birth_date)).getText().toString());
        // 个性签名
        bean.setSignature(((EditText)this.findViewById(R.id.editText)).getText().toString());
        return bean;
    }

    /**
     * 退出画面的时候让toast消失
     */
    @Override
    protected void onPause() {
        super.onPause();
        ToastUtil.cancelToast();
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
                new AlertDialog.Builder(MeEditActivity.this);
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

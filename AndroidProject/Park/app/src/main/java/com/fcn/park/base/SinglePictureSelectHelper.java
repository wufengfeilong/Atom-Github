package com.fcn.park.base;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.concurrent.ExecutionException;

import static android.app.Activity.RESULT_OK;

/**
 * 作者：吕振鹏
 * 创建时间：10月11日
 * 时间：14:48
 * 版本：v1.0.0
 * 类描述：
 * 修改时间：
 */

public class SinglePictureSelectHelper {


    private static final int CAMERA_REQUEST_CODE = 0x0000707;//开启相机的请求码

    private static final int ALBUM_REQUEST_CODE = 0x0000708;//开启相册的请求码

    //定义一个成员变量，用于保存相机拍照的图片
    private String capturePath = null;

    private Activity mActivity;


    private boolean isThumbnail = false;

    public SinglePictureSelectHelper(Activity a) {
        mActivity = a;
    }

    /**
     * 开启本地相机
     * 这种方法获取到的图片是被压缩处理过的，所以会非常的不清晰，建议使用
     */
    @Deprecated
    public void openCamera() {
        isThumbnail = true;
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        mActivity.startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
    }

    /**
     * 开启相机
     */
    public void openCameraForFile() {

        isThumbnail = false;

        final Intent getImageByCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //获取保存的默认相机路经
        File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);

        final String out_file_path = dir.getAbsolutePath() + "/lvzp";

        openCamera(out_file_path, getImageByCamera);

    }

    private void openCamera(String out_file_path, Intent getImageByCamera) {
        try {
            if (createFile(out_file_path))
                //给成员变量赋值
                capturePath = out_file_path + File.separator + System.currentTimeMillis() + ".jpg";
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        Log.d("pricture", "创建临时存储图片的位置是" + capturePath);
        if (TextUtils.isEmpty(capturePath)) {
            Toast.makeText(mActivity, "文件存储位置获取失败", Toast.LENGTH_SHORT).show();
            return;
        }
        getImageByCamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(capturePath)));
        getImageByCamera.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        mActivity.startActivityForResult(getImageByCamera, CAMERA_REQUEST_CODE);
    }

    /**
     * 开启本地相册
     */
    public void openAlbum() {
        if (mActivity == null) return;
        Intent albumIntent = new Intent(Intent.ACTION_PICK, null);
        albumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        mActivity.startActivityForResult(albumIntent, ALBUM_REQUEST_CODE);

    }

    /**
     *
     * @param requestCode
     * @param resultCode
     * @param data
     * @param showView
     * @param isCircleImage
     * @return 选择图片的地址
     */
    public String onActivityResult(int requestCode, int resultCode, Intent data, ImageView showView, boolean isCircleImage) {

        if (mActivity == null ) return "";
        String imageUrl = "";
        switch (requestCode) {
            case CAMERA_REQUEST_CODE://相机的回调
                if (resultCode == RESULT_OK && !TextUtils.isEmpty(capturePath)) {
                    if (isThumbnail) {
                        if (data != null && showView != null)
                            showView.setImageBitmap((Bitmap) data.getExtras().get("data"));
                        // Glide.with(this).load().into(mIvResultPhoto);
                    } else {

                        imageUrl = capturePath;
                        if (!TextUtils.isEmpty(capturePath)) {
                            if (showView != null) {
                                if (isCircleImage)
                                    Glide.with(mActivity).load(new File(capturePath)).into(showView);
                                else
                                    Glide.with(mActivity).load(new File(capturePath)).into(showView);
                            }
                        } else
                            Toast.makeText(mActivity, "图片获取失败，请重试", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case ALBUM_REQUEST_CODE://相册的回掉
                if (resultCode == RESULT_OK) {
                    imageUrl = getRealFilePath(mActivity, data.getData());
                    if (showView != null) {
                        if (isCircleImage)
                            Glide.with(mActivity).load(imageUrl).into(showView);
                        else
                            Glide.with(mActivity).load(imageUrl).into(showView);
                    }
                }
                break;
        }
        return imageUrl;
    }

    private boolean createFile(String path) throws ExecutionException, InterruptedException {
        if (TextUtils.isEmpty(path)) {
            return false;
        }
        final File file = new File(path);
        return file.exists() || file.mkdirs();
    }

    /**
     * 从Uri中获取绝对路径
     *
     * @param context
     * @param uri
     * @return the file path or null
     */
    private String getRealFilePath(final Context context, final Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

}

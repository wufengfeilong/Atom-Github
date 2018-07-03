package sdwxwx.com.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Display;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import sdwxwx.com.cons.Constant;

/**
 * 图形操作类
 * Created by 860115025 on 2018/05/23.
 */

public class ImageUtil {

    /**
     * 当前画面截图
     * @param activity
     * @param cutbottom
     * @param  cutHeight
     * @return
     */
    public static Bitmap onCut(Activity activity, float cutbottom, float cutHeight){
        //获取window最底层的view
        View view=activity.getWindow().getDecorView();
        view.buildDrawingCache();

        //状态栏高度
        Rect rect=new Rect();
        view.getWindowVisibleDisplayFrame(rect);
        int stateBarHeight=rect.top;
        Display display=activity.getWindowManager().getDefaultDisplay();

        //获取屏幕宽高
        int widths=display.getWidth();
        int height=display.getHeight();

        //设置允许当前窗口保存缓存信息
        view.setDrawingCacheEnabled(true);

        //去掉状态栏高度
        Bitmap bitmap=Bitmap.createBitmap(view.getDrawingCache(),0,stateBarHeight + dp2px(activity, cutbottom), widths, height - stateBarHeight - dp2px(activity, cutHeight));

        view.destroyDrawingCache();
        return bitmap;
    }
    /**
     * 保存bitmap到本地
     *
     * @param context
     * @param mBitmap
     * @return
     */
    public static String saveBitmap(Context context, Bitmap mBitmap) {
        String savePath;
        File filePic;
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            savePath = Constant.SD_PATH;
        } else {
            savePath = context.getApplicationContext().getFilesDir()
                    .getAbsolutePath()
                    + Constant.IN_PATH;
        }
        try {
            filePic = new File(savePath + generateFileName() + ".jpg");
            if (!filePic.exists()) {
                filePic.getParentFile().mkdirs();
                filePic.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(filePic);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return filePic.getAbsolutePath();
    }

    /**
     * 随机生产文件名
     *
     * @return
     */
    private static String generateFileName() {
        return UUID.randomUUID().toString();
    }

    /**
     * dp转换成px
     */
    private static int dp2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * @param bmp 获取的bitmap数据
     */

    public static void saveBmp2Gallery(Context context, Bitmap bmp) {
        String fileName = null;
        //系统相册目录
        String galleryPath;
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            galleryPath = Constant.SD_PATH;
        } else {
            galleryPath = context.getApplicationContext().getFilesDir()
                    .getAbsolutePath()
                    + Constant.IN_PATH;
        }
        // 声明文件对象
        File file = null;
        // 声明输出流
        FileOutputStream outStream = null;

        try {
            // 如果有目标文件，直接获得文件对象，否则创建一个以filename为名称的文件
            file = new File(galleryPath, generateFileName() + ".jpg");

            // 获得文件相对路径
            fileName = file.toString();
            // 获得输出流，如果文件中有内容，追加内容
            outStream = new FileOutputStream(fileName);
            if (null != outStream) {
                bmp.compress(Bitmap.CompressFormat.JPEG, 90, outStream);
                outStream.flush();
            }

        } catch (Exception e) {
            e.getStackTrace();
        }finally {
            try {
                if (outStream != null) {
                    outStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //通知相册更新
        MediaStore.Images.Media.insertImage(context.getContentResolver(),
                bmp, fileName, null);
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(file);
        intent.setData(uri);
        context.sendBroadcast(intent);
    }

    /**
     * 删除文件，可以是文件或文件夹
     *
     * @param fileName
     *            要删除的文件名
     * @return 删除成功返回true，否则返回false
     */
    public static void deleteFile(String fileName) {
        File file = new File(fileName);
        // 如果文件存在，就删除文件
        if (file.exists()) {
            file.delete();
        }
    }
}
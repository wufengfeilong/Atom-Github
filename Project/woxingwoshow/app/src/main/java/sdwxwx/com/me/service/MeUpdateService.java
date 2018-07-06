package sdwxwx.com.me.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.widget.RemoteViews;
import sdwxwx.com.R;
import sdwxwx.com.cons.Constant;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * APK下载服务
 */
public class MeUpdateService extends IntentService {
    private static final String TAG = "MeUpdateService";
    private static final String ACTION_UPDATE = "sdwxwx.com.me.service.action.update";
    private static final String EXTRA_URL = "sdwxwx.com.me.service.extra.url";
    // 下载类型：1，下载安装文件,APP；2，只下载，无需安装,视频文件;3,只下载，无需安装,音乐文件
    private static final String EXTRA_DOWNLOAD_TYPE = "sdwxwx.com.me.service.extra.down.type";
    private static final String EXTRA_FILE_NAME = "sdwxwx.com.me.service.extra.file.name";
    private boolean isRunning = false;
    private NotificationManager updateNotificationManager;
    private Notification updateNotification;
    private PendingIntent updatePendingIntent;
    private static OnProgressListener mProgressListener;
    private String basePath = Environment.getExternalStorageDirectory() + File.separator + "Download" + File.separator;
    private static Context mContext;

    public interface OnProgressListener {
        void onProgress(int progress);

        void onSuccess(boolean isSuccess);
    }

    public MeUpdateService() {
        super("MeUpdateService");
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_UPDATE.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_URL);
                final String param2 = intent.getStringExtra(EXTRA_FILE_NAME);
                final int param3 = intent.getIntExtra(EXTRA_DOWNLOAD_TYPE, 0);
                startDownloade(param1, param2, param3);
            }
        }
    }

    @Override
    public void onDestroy() {
        mProgressListener = null;
        super.onDestroy();
    }

    /**
     * 开始更新
     *
     * @see IntentService
     */
    public static void startUpdate(Context context, String param1, String param2, int param3, OnProgressListener pregressListener) {
        mProgressListener = pregressListener;
        Intent intent = new Intent(context, MeUpdateService.class);
        intent.setAction(ACTION_UPDATE);
        intent.putExtra(EXTRA_URL, param1);
        intent.putExtra(EXTRA_FILE_NAME, param2);
        intent.putExtra(EXTRA_DOWNLOAD_TYPE, param3);
        mContext = context;
        context.startService(intent);
    }

    /**
     * 开始升级
     */
    private void startDownloade(String url, String fileName, int type) {
        Log.d(TAG, "开始升级----" + url + "---" + fileName);
        if (isRunning) {
            return;
        }
        isRunning = true;
        if (type == 1) {
            if (Build.VERSION.SDK_INT<26) {
                initRemoteView();
            }
        }

        try {
            boolean isSuccess = downloadUpdateFile(url, fileName, type);
            if (mProgressListener != null) {
                mProgressListener.onSuccess(isSuccess);
            }
            if (type == 1) {
                if (isSuccess) {
                    Intent installIntent = new Intent(Intent.ACTION_VIEW);
                    Uri uri;
                    // 判断版本大于等于7.0
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        // "sdwxwx.com.me.fileprovider"即是在清单文件中配置的authorities
                        uri = FileProvider.getUriForFile(mContext, "sdwxwx.com.me.fileprovider", new File(basePath + fileName));
                        // 给目标应用一个临时授权
                        installIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    } else {
                        uri = Uri.fromFile(new File(basePath + fileName));
                    }
                    installIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    installIntent.setDataAndType(uri, "application/vnd.android.package-archive");
                    startActivity(installIntent);
                    try {
                        if (Build.VERSION.SDK_INT<26) {
                            updateNotificationManager.cancel(0);
                        }
                    } catch (Exception ex) {
                        Log.e(TAG, "startDownloade: " + ex.getMessage());
                    }
                } else {
                    if (Build.VERSION.SDK_INT<26) {
                        Notification notification = new Notification.Builder(MeUpdateService.this)
                                .setContentTitle(getString(R.string.app_name))
                                .setContentText("下载失败")
                                .setSmallIcon(R.mipmap.ic_launcher)
                                .build();
                        updateNotificationManager.notify(0, notification);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 初始化状态栏进度条
     */
    private void initRemoteView() {
        try {
            updateNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            //状态栏提醒内容
            updateNotification = new Notification.Builder(this)
                    .setTicker("版本更新下载")
                    .setContentTitle("版本更新下载")
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.mipmap.ic_launcher).build();
            updatePendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, RemoteViews.class), 0);
            updateNotification.contentIntent = updatePendingIntent;
            //状态栏提醒内容
            updateNotification.contentView = new RemoteViews(getApplication().getPackageName(), R.layout.me_update_progress);
            updateNotification.contentView.setProgressBar(R.id.progressBar1, 100, 0, false);
            updateNotification.contentView.setTextViewText(R.id.textView1, "0%");
            // 发出通知
            updateNotificationManager.notify(0, updateNotification);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 下载文件
     *
     * @param downloadUrl
     * @param filename
     * @return
     * @throws Exception
     */
    private boolean downloadUpdateFile(String downloadUrl, String filename, int type) {
        try {
            int downloadCount = 0;
            int currentSize = 0;
            long totalSize = 0;
            int updateTotalSize = 0;
            boolean result = false;
            HttpURLConnection httpConnection = null;
            InputStream is = null;
            FileOutputStream fos = null;
            String filepath = basePath + filename;
            if (type==3) {
                String musicPath = mContext.getFilesDir().getPath() + Constant.MUSIC_BOX_NAME;
                File file = new File(musicPath);
                if (!file.exists()) {
                    file.mkdirs();
                }
                filepath = musicPath+File.separator+filename;
            }
            File temp = new File(filepath + ".tmp");
            if (temp.getParentFile().isDirectory()) {
                temp.getParentFile().mkdirs();
            }
            try {
                URL url = new URL(downloadUrl);
                httpConnection = (HttpURLConnection) url.openConnection();
                httpConnection.setRequestProperty("User-Agent", "PacificHttpClient");
                if (currentSize > 0) {
                    httpConnection.setRequestProperty("RANGE", "bytes=" + currentSize + "-");
                }
                httpConnection.setConnectTimeout(20000);
                httpConnection.setReadTimeout(120000);
                updateTotalSize = httpConnection.getContentLength();
                if (httpConnection.getResponseCode() == 404) {
                    throw new Exception("fail!");
                }
                is = httpConnection.getInputStream();
                fos = new FileOutputStream(temp, false);
                byte buffer[] = new byte[4096];
                int readsize = 0;
                while ((readsize = is.read(buffer)) > 0) {
                    fos.write(buffer, 0, readsize);
                    totalSize += readsize; // 为了防止频繁的通知导致应用吃紧，百分比增加10才通知一次
                    if ((downloadCount == 0) || (int) (totalSize * 100 / updateTotalSize) - 1 > downloadCount) {
                        downloadCount += 1;
                        try {
                            if (type == 1) {
                                if (Build.VERSION.SDK_INT<26) {
                                    updateNotification.contentView.setProgressBar(R.id.progressBar1, 100, (int) totalSize * 100 / updateTotalSize,
                                            false);
                                    updateNotification.contentView.setTextViewText(R.id.textView1, (int) totalSize * 100 / updateTotalSize + "%");
                                    updateNotification.contentIntent = updatePendingIntent;
                                    updateNotificationManager.notify(0, updateNotification);
                                }
                            }
                            if (mProgressListener != null) {
                                mProgressListener.onProgress((int) (totalSize * 100 / updateTotalSize));
                            }
                        } catch (Exception ex) {
                            Log.e(TAG, ex.getMessage(), ex);
                        }
                    }
                }
                temp.renameTo(new File(filepath));
                temp.delete();
            } finally {
                if (httpConnection != null) {
                    httpConnection.disconnect();
                }
                if (is != null) {
                    is.close();
                }
                if (fos != null) {
                    fos.close();
                }
                result = updateTotalSize > 0 && updateTotalSize == totalSize;
                if (!result) { //下载失败或者为下载完成
                    new File(filepath).delete();
                }
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

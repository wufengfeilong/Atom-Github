package woxingwoxiu.com.release.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.File;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import woxingwoxiu.com.R;
import woxingwoxiu.com.adapter.BaseAdapter;
import woxingwoxiu.com.adapter.BaseHolder;
import woxingwoxiu.com.base.BaseActivity;
import woxingwoxiu.com.databinding.DraftListActivityBinding;
import woxingwoxiu.com.release.bean.VideoBean;
import woxingwoxiu.com.release.contract.DraftListContract;
import woxingwoxiu.com.release.media.MediaStorage;
import woxingwoxiu.com.release.presenter.DraftListPresenter;

/**
 * 待发布视频列表画面
 */
public class DraftListActivity extends BaseActivity<DraftListActivityBinding, DraftListPresenter>
        implements DraftListContract.View, ActivityCompat.OnRequestPermissionsResultCallback {

    private final String TAG = "DraftListActivity";
    private final int PERMISSION_REQUEST_STORAGE = 1000;

    // 视频存放路径
    private String mVideoPath;
    // 获取视频封面工具类
    private MediaMetadataRetriever retriever;
    // 检索固定路径的视频文件
    private MediaStorage mMediaStorage;
    private DraftAdapter draftAdapter;
    private List<VideoBean> listVideo;

    // 删除的Dialog
    private Dialog deleteDialog = null;
    // 内部存储请求授权被拒绝时，手动设置权限的Dialog
    private Dialog permissionDialog = null;

    @Override
    protected void initViews() {
        Log.d(TAG, "initViews() start");

        getStoragePermission();
        mDataBinding.setTitle(getString(R.string.release_draft_list_title_tx));
        mDataBinding.setPresenter(mPresenter);

        mDataBinding.draftList.setLayoutManager(
                new LinearLayoutManager(DraftListActivity.this,
                        LinearLayoutManager.VERTICAL, false));
        getListData();
        setEmpty();
        draftAdapter = new DraftAdapter(R.layout.draft_item_layout, listVideo);

        mDataBinding.draftList.setAdapter(draftAdapter);

        Log.d(TAG, "initViews() start");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mMediaStorage.cancelTask();
    }

    /**
     * 获取草稿箱的视频列表
     *
     * @return 视频列表
     */
    private void getListData() {
        Log.d(TAG, "getListData() start");
        listVideo = new ArrayList<>();
        // 草稿箱位置
//        mVideoPath = Environment.getExternalStorageDirectory().getPath() + Constant.DRAFT_BOX_NAME;
        // 获取 /data/data/pkg/file/draft
        mVideoPath = getApplicationContext().getFilesDir().getPath() + "/draft";
        retriever = new MediaMetadataRetriever();
        // 文件名
        String filename;
        // 文件后缀
        String suf;
        // 文件夹dir
        File dir = new File(mVideoPath);
        if (!dir.exists()) {
            return;
        }

        // 文件夹下的所有文件
        File[] files = dir.listFiles();
        if (files == null) {
            return;
        }

        VideoBean bean = null;
        for (int i = 0; i < files.length; i++) {
            bean = new VideoBean();
            filename = files[i].getName();
            // 得到文件后缀
            suf = filename.substring(filename.lastIndexOf(".") + 1);
            // 判断是不是mp4或者gif后缀的文件
            if (suf.equalsIgnoreCase("mp4")
//                    || suf.equalsIgnoreCase("gif")
                    ) {
                String strFileName = files[i].getAbsolutePath();
                bean.setVideoName(strFileName);

                String fileDate = "未获取时间";
                try {
                    fileDate = longToDate(files[i].lastModified());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                bean.setVideoDate(fileDate);
                retriever.setDataSource(strFileName);
                bean.setVideoCover(retriever.getFrameAtTime());
                listVideo.add(bean);
            }
        }
//        mMediaStorage = new MediaStorage(this, mVideoPath);
//        mMediaStorage.setOnMediaDataUpdateListener(new MediaStorage.OnMediaDataUpdateListener() {
//            @Override
//            public void onDateUpdate(List<MediaInfo> data) {
//                Log.d(TAG, "getListData() onDateUpdate data.size() = " + data.size());
//
////                int count = draftAdapter.getItemCount();
//                int size = data.size();
////                int insert = count - size;
//                listVideo.addAll(updateData(data));
//                if (size == 0) {
//                    mDataBinding.draftList.setVisibility(View.GONE);
//                    mDataBinding.emptyTx.setVisibility(View.VISIBLE);
//                } else {
//
//                    mDataBinding.draftList.setVisibility(View.VISIBLE);
//                    mDataBinding.emptyTx.setVisibility(View.GONE);
//                }
//                draftAdapter.notifyDataSetChanged();
//            }
//        });
//
//        mMediaStorage.startCaptureMedias(MediaStorage.MEDIA_TYPE_VIDEO);
//        Log.d(TAG, "getListData() mMediaStorage.getMediaList().size() = " + mMediaStorage.getMediaList().size());
//        listVideo = updateData(mMediaStorage.getMediaList());

        Log.d(TAG, "getListData() end");
    }

//    private List<VideoBean> updateData(List<MediaInfo> data) {
//        List<VideoBean> list = new ArrayList<>();
//        for (MediaInfo mediaInfo : data) {
//
//            VideoBean bean = new VideoBean();
//            try {
//                // action:这里取出来的是秒，不是毫秒
//                bean.setVideoDate(longToDate(Long.valueOf(mediaInfo.date) * 1000));
//            } catch (ParseException e) {
//                e.printStackTrace();
//                Log.e(TAG, "getListData() ParseException " + e.getMessage());
//            }
//            bean.setVideoName(mediaInfo.filePath);
//            MediaMetadataRetriever retriever = new MediaMetadataRetriever();
//            retriever.setDataSource(mediaInfo.filePath);
//            bean.setVideoCover(retriever.getFrameAtTime());
//            Log.d(TAG, "getListData() bean.getVideoName() = " + bean.getVideoName());
//            list.add(bean);
//        }
//        return list;
//    }

    @Override
    protected DraftListPresenter createPresenter() {
        return new DraftListPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.draft_list_activity;
    }

    public void onMenuClick(View view) {
        switch (view.getId()) {
            case R.id.release_back_btn:
                super.onBackPressed();
                break;
            default:
                break;
        }
    }

    /**
     * 列表为空时，显示内容更新
     */
    private void setEmpty() {
        // 草稿箱为空时
        if (listVideo.size() == 0) {
            mDataBinding.releaseDraftRemindText.setVisibility(View.GONE);
            mDataBinding.draftList.setVisibility(View.GONE);
            mDataBinding.emptyTx.setVisibility(View.VISIBLE);
        } else {
            mDataBinding.releaseDraftRemindText.setVisibility(View.VISIBLE);
            mDataBinding.draftList.setVisibility(View.VISIBLE);
            mDataBinding.emptyTx.setVisibility(View.GONE);
        }
    }

    /**
     * String 转成日期格式
     *
     * @param longTime
     * @return
     * @throws ParseException
     */
    public String longToDate(long longTime) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年M月d日");
        Date date = new Date(longTime);
        String strDate = formatter.format(date);
        return strDate;
    }

    /**
     * 弹出删除的Dialog
     */
    private void showDeleteDialog(final String strPath, final int position) {
        // 如果保存Dialog正在显示，则不再重建
        if (deleteDialog != null && deleteDialog.isShowing()) {
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        deleteDialog = builder.setTitle(R.string.release_delete_dialog_title)
                .setMessage(R.string.release_delete_dialog_msg)
                .setPositiveButton(getResources().getString(R.string.release_confirm_btn_tx), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        if (deleteFile(strPath)) {
                            // 删除成功
                            draftAdapter.notifyDataSetChanged();
                            listVideo.remove(position);
                            setEmpty();
                        }

                    }
                }).setNegativeButton(getResources().getString(R.string.release_cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).create();
        deleteDialog.show();
    }

    /**
     * 删除单个文件
     *
     * @param fileName 要删除的文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public boolean deleteFile(String fileName) {
        File file = new File(fileName);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * 获取存储权限
     */
    private void getStoragePermission() {
        int readPerm = ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int writePerm = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (readPerm != PackageManager.PERMISSION_GRANTED ||
                writePerm != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                String[] permissions = {
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE};
                ActivityCompat.requestPermissions(this, permissions,
                        PERMISSION_REQUEST_STORAGE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mMediaStorage.startCaptureMedias(MediaStorage.MEDIA_TYPE_VIDEO);
                    draftAdapter.notifyDataSetChanged();
                } else {
                    for (int grant : grantResults) {
                        if (grant != PackageManager.PERMISSION_GRANTED) {
                            // 若用户不授予权限，提示手动授权弹窗
                            openAppPermissionDetails();
                        }
                    }
                }
                break;
            }
        }
    }

    /**
     * 若用户不授予权限
     * 打开 APP 的详情设置
     */
    private void openAppPermissionDetails() {
        if (null != permissionDialog && permissionDialog.isShowing()) {
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        permissionDialog = builder.setMessage(R.string.release_set_storage_permission_msg)
                .setPositiveButton(getResources().getString(R.string.release_set), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        intent.addCategory(Intent.CATEGORY_DEFAULT);
                        intent.setData(Uri.parse("package:" + getPackageName()));
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                        startActivity(intent);
                    }
                }).setNegativeButton(getResources().getString(R.string.release_cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        showToast(getString(R.string.release_storage_permission_refused));
                        // 画面结束
                        closeActivity();
                    }
                }).create();
        permissionDialog.show();
    }

    private class DraftAdapter extends BaseAdapter<VideoBean> {

        public DraftAdapter(int layoutId, List<VideoBean> list) {
            super(layoutId, list);
            Log.d(TAG, "DraftAdapter list.size() = " + list.size());
        }

        @Override
        protected void convert(final BaseHolder holder, final VideoBean mediaInfo) {
            Log.d(TAG, "convert mediaInfo.getVideoName() = " + mediaInfo.getVideoName());

            holder.setText(R.id.draft_item_date, mediaInfo.getVideoDate());
            if (mediaInfo.getVideoCover() != null) {
                holder.setImageBitmap(R.id.draft_item_icon, mediaInfo.getVideoCover());
            }

            // 发布按钮
            Button publishBtn = holder.getView(R.id.draft_publish_btn);
            publishBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ReleaseEditActivity.startActivity(
                            DraftListActivity.this,
                            mediaInfo.getVideoName());
                }
            });

            // 删除按钮
            TextView deleteBtn = holder.getView(R.id.draft_item_delete);
            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDeleteDialog(mediaInfo.getVideoName(), holder.getAdapterPosition());
                }
            });
        }
    }
}

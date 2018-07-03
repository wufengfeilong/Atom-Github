package sdwxwx.com.release.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.File;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import sdwxwx.com.R;
import sdwxwx.com.adapter.BaseAdapter;
import sdwxwx.com.adapter.BaseHolder;
import sdwxwx.com.base.BaseActivity;
import sdwxwx.com.cons.Constant;
import sdwxwx.com.databinding.ActivityDraftListBinding;
import sdwxwx.com.login.utils.LoginHelper;
import sdwxwx.com.release.bean.DraftVideoBean;
import sdwxwx.com.release.contract.DraftListContract;
import sdwxwx.com.release.presenter.DraftListPresenter;

/**
 * 待发布视频列表画面
 */
public class DraftListActivity extends BaseActivity<ActivityDraftListBinding, DraftListPresenter>
        implements DraftListContract.View, ActivityCompat.OnRequestPermissionsResultCallback {

    private final String TAG = "DraftListActivity";

    // 视频存放路径
    private String mVideoPath;
    // 获取视频封面工具类
    private MediaMetadataRetriever retriever;
    // 检索固定路径的视频文件
    private List<DraftVideoBean> listVideo = new ArrayList<>();
    private DraftAdapter draftAdapter;

    // 删除的Dialog
    private Dialog deleteDialog = null;

    @Override
    protected void initViews() {
        Log.d(TAG, "initViews() start");

        mDataBinding.setTitle(getString(R.string.release_draft_list_title_tx));
        mDataBinding.setPresenter(mPresenter);

        mDataBinding.draftList.setLayoutManager(
                new LinearLayoutManager(DraftListActivity.this,
                        LinearLayoutManager.VERTICAL, false));
        draftAdapter = new DraftAdapter(R.layout.draft_item, listVideo);

        mDataBinding.draftList.setAdapter(draftAdapter);

        Log.d(TAG, "initViews() start");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() start");
        // 从其他画面回来的情况下，要重新获取
        getListData();
        setEmpty();
        Log.d(TAG, "onResume() end");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 获取草稿箱的视频列表
     */
    private void getListData() {
        Log.d(TAG, "getListData() start");
        listVideo.clear();
        // 草稿箱位置
        mVideoPath = getContext().getFilesDir().getPath() + Constant.DRAFT_BOX_NAME;
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

        DraftVideoBean bean;
        for (int i = 0; i < files.length; i++) {
            bean = new DraftVideoBean();
            filename = files[i].getName();
            // 得到文件后缀
            suf = filename.substring(filename.lastIndexOf(".") + 1);
            int userEndIndex = filename.indexOf("id");
            // 不包含UserId
            if (userEndIndex == -1) {
                continue;
            }
            String userId = filename.substring(0, userEndIndex);
            if (TextUtils.isEmpty(userId)) {
                continue;
            }
            // 判断是不是mp4后缀的文件
            if (userId.equals(LoginHelper.getInstance().getUserId())
                    && suf.equalsIgnoreCase("mp4")
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

        draftAdapter.notifyDataSetChanged();
        Log.d(TAG, "getListData() end");
    }


    @Override
    protected DraftListPresenter createPresenter() {
        return new DraftListPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_draft_list;
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
        Log.d(TAG, "setEmpty() start");
        // 草稿箱为空时
        if (listVideo.size() == 0) {
            Log.d(TAG, "setEmpty() 草稿箱为空时");
            mDataBinding.releaseDraftRemindText.setVisibility(View.GONE);
            mDataBinding.draftList.setVisibility(View.GONE);
            mDataBinding.emptyTx.setVisibility(View.VISIBLE);
        } else {
            mDataBinding.releaseDraftRemindText.setVisibility(View.VISIBLE);
            mDataBinding.draftList.setVisibility(View.VISIBLE);
            mDataBinding.emptyTx.setVisibility(View.GONE);
        }
        Log.d(TAG, "setEmpty() end");
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
                            listVideo.remove(position);
                            Log.d(TAG, "setEmpty() position = " + position + "  listVideo.size() = " + listVideo.size());
                            draftAdapter.notifyDataSetChanged();
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
     * Adapter
     */
    private class DraftAdapter extends BaseAdapter<DraftVideoBean> {

        public DraftAdapter(int layoutId, List<DraftVideoBean> list) {
            super(layoutId, list);
            setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    // 不做处理
                }
            });
            Log.d(TAG, "DraftAdapter list.size() = " + list.size());
        }

        @Override
        protected void convert(final BaseHolder holder, final DraftVideoBean mediaInfo) {
            Log.d(TAG, "convert mediaInfo.getVideoName() = " + mediaInfo.getVideoName());

            holder.setText(R.id.draft_item_date, mediaInfo.getVideoDate());
            if (mediaInfo.getVideoCover() != null) {
                holder.setImageBitmap(R.id.draft_item_icon, mediaInfo.getVideoCover());
            }

            // 发布按钮
            TextView publishBtn = holder.getView(R.id.draft_publish_btn);
            publishBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(DraftListActivity.this, PublishEditActivity.class);
                    String path = mediaInfo.getVideoName();
                    String musicId = path.substring(
                            path.indexOf(Constant.DRAFT_BOX_MUSIC_ID) + Constant.DRAFT_BOX_MUSIC_ID.length(),
                            path.indexOf(Constant.VIDEO_TYPE_NAME));
                    intent.putExtra(Constant.COMPOSE_PATH, path);
                    // 设置未设置音乐
                    intent.putExtra(Constant.MUSIC_ONLINE_ID, musicId);
                    startActivity(intent);
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

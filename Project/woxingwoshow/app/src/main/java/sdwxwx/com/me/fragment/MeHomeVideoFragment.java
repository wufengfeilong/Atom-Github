package sdwxwx.com.me.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;

import java.io.File;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import sdwxwx.com.R;
import sdwxwx.com.adapter.BaseAdapter;
import sdwxwx.com.adapter.BaseHolder;
import sdwxwx.com.base.BaseFragment;
import sdwxwx.com.cons.Constant;
import sdwxwx.com.databinding.MeHomeVideoBinding;
import sdwxwx.com.home.bean.PlayVideoBean;
import sdwxwx.com.login.utils.LoginHelper;
import sdwxwx.com.me.contract.MeHomeFragmentContract;
import sdwxwx.com.me.presenter.MeHomeFragmentPresenter;
import sdwxwx.com.play.activity.PlayVideoActivity;
import sdwxwx.com.release.activity.DraftListActivity;
import sdwxwx.com.release.bean.DraftVideoBean;
import sdwxwx.com.util.StringUtil;

/**
 * Created by 860117066 on 2018/05/29.
 * 类描述：主页视频Fragment
 */

public class MeHomeVideoFragment extends BaseFragment<MeHomeVideoBinding, MeHomeFragmentPresenter>
        implements MeHomeFragmentContract.View, BaseAdapter.OnItemClickListener {
    private final String TAG = "MeHomeVideoFragment";
    private final int PERMISSION_REQUEST_STORAGE = 1000;
    LoginHelper mHelper;
    int mePage = 1;
    int upPage = 1;
    // 草稿箱内文件集合
    private List<DraftVideoBean> mDraftListData = new ArrayList<>();
    private boolean hasDraft = false;

    // 内部存储请求授权被拒绝时，手动设置权限的Dialog
    private Dialog permissionDialog = null;
    //定义视频类型
    int type;

    //视频Adapter
    HomeVideoAdapter mHomeVideoAdapter;
    List<PlayVideoBean> mList = new ArrayList<>();
    LoadMoreMeListReceiver mReceiver;

    boolean isFresh;

    public static MeHomeVideoFragment newInstance(int type) {
        Bundle args = new Bundle();
        args.putInt("type", type);
        MeHomeVideoFragment fragment = new MeHomeVideoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getArguments() != null) {
            type = getArguments().getInt("type");
        }
        if (type == 0) {
            mPresenter.loadMeVideoData(Constant.REQUEST_PAGE, mDataBinding);
        } else if (type == 1) {
            mPresenter.loadUpVideoData(Constant.REQUEST_PAGE, mDataBinding);
        }
    }

    @Override
    public void onRefresh() {
        mList.clear();
        if (type == 0) {
            mePage = 1;
            mPresenter.loadMeVideoData(Constant.REQUEST_PAGE, mDataBinding);
        } else {
            upPage = 1;
            mPresenter.loadUpVideoData(Constant.REQUEST_PAGE, mDataBinding);
        }
    }

    @Override
    public void onLoadmore() {
        if (type == 0) {
            mePage = mePage + 1;
            mPresenter.loadMeVideoMoreData(String.valueOf(mePage));
        } else {
            upPage = upPage + 1;
            mPresenter.loadUpVideoMoreData(String.valueOf(upPage));
        }
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.me_home_video;
    }

    @Override
    protected MeHomeFragmentPresenter createPresenter() {
        return new MeHomeFragmentPresenter();
    }

    @Override
    protected void initViews() {
        mDataBinding.setPresenter(mPresenter);
        mHelper = LoginHelper.getInstance();
        initRecyclerView();
        // 请求存储权限
        getStoragePermission();
        //动态接受网络变化的广播接收器
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.sdwxwx.load.owner.video.list");
        intentFilter.addAction("com.sdwxwx.delete.video");
        intentFilter.addAction("com.sdwxwx.thumb.like");
        intentFilter.addAction("com.sdwxwx.thumb.owner");
        //注册我的广播
        mReceiver = new LoadMoreMeListReceiver();
        getContext().registerReceiver(mReceiver, intentFilter);
    }

    public class LoadMoreMeListReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("com.sdwxwx.load.owner.video.list")) {
                onLoadMore(intent.getIntExtra("load_type",-1));
            } else if (intent.getAction().equals("com.sdwxwx.delete.video")){
                onRefresh();
            } else {
                isFresh = true;
            }
        }
    }

    @Override
    public void bindListDataMore(List<PlayVideoBean> data) {
        mList.addAll(data);
        mHomeVideoAdapter.notifyDataSetChanged();
    }

    public void onLoadMore(int loadType) {
        if (loadType == 0 && loadType == type) {
            mePage = mePage + 1;
            mPresenter.loadMeVideoMoreData(String.valueOf(mePage));
        } else if (loadType == 1 && loadType == type) {
            upPage = upPage + 1;
            mPresenter.loadUpVideoMoreData(String.valueOf(upPage));
        }
    }

    public void onLoadCurPageData() {
        if (type == 0 ) {
            mPresenter.loadMeVideoData(mePage + "", mDataBinding);
        } else if (type == 1) {
            mPresenter.loadUpVideoData(upPage + "", mDataBinding);
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        getContext().unregisterReceiver(mReceiver);
    }

    @Override
    public void initRecyclerView() {

        mDataBinding.homeVideoSpringView.setListener(this);
        mDataBinding.homeVideoSpringView.setHeader(new DefaultHeader(getContext()));
        mDataBinding.homeVideoSpringView.setFooter(new DefaultFooter(getContext()));
        mDataBinding.homeVideoRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 3));
        mHomeVideoAdapter = new HomeVideoAdapter(mList);
        mDataBinding.homeVideoRecyclerView.setAdapter(mHomeVideoAdapter);
        mHomeVideoAdapter.setOnItemClickListener(this);
    }

    @Override
    public void bindListData(List<PlayVideoBean> beanList) {
        mList.clear();
        mList.addAll(beanList);

        if (type == 0) {
            LoginHelper.getInstance().setOwnerList(mList);
        } else {
            LoginHelper.getInstance().setLikeList(mList);
        }
        // 获取草稿箱的视频列表
        getListData();
        // 有待发布视频时
        if (hasDraft && type == 0) {
            PlayVideoBean bean = new PlayVideoBean();
            mList.add(0, bean);
        }

        if (mList.size() <= 0) {
            mDataBinding.homeVideoSpringView.setVisibility(View.GONE);
            mDataBinding.emptyText.setVisibility(View.VISIBLE);
        } else {
            mDataBinding.emptyText.setVisibility(View.GONE);
            mDataBinding.homeVideoSpringView.setVisibility(View.VISIBLE);
        }
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mHomeVideoAdapter.notifyDataSetChanged();

            }
        });
        mDataBinding.homeVideoSpringView.onFinishFreshAndLoad();
        Intent intent = new Intent("com.sdwxwx.load.video.list.end");
        getContext().sendBroadcast(intent);
    }

    @Override
    public void onItemClick(View view, int position) {
        // 有待发布视频时
        if (type == 0) {

            if (mDraftListData != null && mDraftListData.size() > 0) {
                if (position == 0) {
                    actionStartActivity(DraftListActivity.class);
                } else {
//                    param2StartActivity(PlayVideoActivity.class, String.valueOf(position - 1), 3);
                    Intent i = new Intent(mContext, PlayVideoActivity.class);
                    i.putExtra(Constant.INTENT_PARAM, String.valueOf(position - 1));
                    i.putExtra(Constant.INTENT_PARAM_ONE, 3);
                    i.putExtra(Constant.INTENT_PARAM_TWO,10);
                    mContext.startActivity(i);
                }
            } else {
//                param2StartActivity(PlayVideoActivity.class, String.valueOf(position), 3);
                Intent i = new Intent(mContext, PlayVideoActivity.class);
                i.putExtra(Constant.INTENT_PARAM, String.valueOf(position));
                i.putExtra(Constant.INTENT_PARAM_ONE, 3);
                i.putExtra(Constant.INTENT_PARAM_TWO,10);
                mContext.startActivity(i);
            }
        } else {
//            param2StartActivity(PlayVideoActivity.class, String.valueOf(position), 4);
            Intent i = new Intent(mContext, PlayVideoActivity.class);
            i.putExtra(Constant.INTENT_PARAM, String.valueOf(position));
            i.putExtra(Constant.INTENT_PARAM_ONE, 4);
            i.putExtra(Constant.INTENT_PARAM_TWO,10);
            mContext.startActivity(i);
        }
    }

    @Override
    public SpringView getSpringView() {
        return mDataBinding.homeVideoSpringView;
    }

    public class HomeVideoAdapter extends BaseAdapter<PlayVideoBean> {

        public HomeVideoAdapter(List<PlayVideoBean> list) {
            super(R.layout.me_home_video_item, list);

        }

        @Override
        public int getItemCount() {
            return super.getItemCount();
        }
        @Override
        public int getItemViewType(int position) {
            Log.d(TAG, "getItemViewType() hasDraft = " + hasDraft + "  position = " + position);
            if (type == 0 && hasDraft && position == 0) {
                // 有待发布视频时
                return 1;
            } else {
                return super.getItemViewType(position);
            }
        }

        @Override
        public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Log.d(TAG, "onCreateViewHolder() viewType = " + viewType);
            if (viewType == 1) {
                // 待发布视频
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.me_draft_video_item, parent, false);
                BaseHolder holder = new BaseHolder(view, new OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(getContext(), DraftListActivity.class);
                        startActivity(intent);
                    }
                });
                return holder;
            } else {
                return super.onCreateViewHolder(parent, viewType);
            }
        }

        @Override
        public void onBindViewHolder(BaseHolder holder, int position) {
            if (holder.getItemViewType() == 1) {
                holder.setImageBitmap(R.id.draft_cover, mDraftListData.get(0).getVideoCover());
            } else {
                super.onBindViewHolder(holder, position);
            }
        }

        @Override
        protected void convert(BaseHolder holder, PlayVideoBean item) {
            holder.setText(R.id.me_home_video_release_time, StringUtil.dataFormat(item.getCreate_time()));
            holder.setText(R.id.me_home_video_good_count, item.getLike_count() + "");
            Glide.with(mContext).load(item.getCover_url()).into((ImageView) holder.getView(R.id.me_home_video_item_thumbnail));
            if ("0".equals(item.getIs_liked())) {
                holder.setImageResource(R.id.me_home_video_item_thumb, R.drawable.thumb_up_no_selected);
            } else {
                holder.setImageResource(R.id.me_home_video_item_thumb, R.drawable.video_liked);
            }
        }
    }

    @Override
    public RecyclerView getRecyclerView() {
        return mDataBinding.homeVideoRecyclerView;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() start");
        if (isFresh) {
            mHomeVideoAdapter.notifyDataSetChanged();
            isFresh = false;
        }
        if (mList.size() <= 0) {
            mDataBinding.homeVideoSpringView.setVisibility(View.GONE);
            mDataBinding.emptyText.setVisibility(View.VISIBLE);
        } else {
            mDataBinding.emptyText.setVisibility(View.GONE);
            mDataBinding.homeVideoSpringView.setVisibility(View.VISIBLE);
        }
        mHomeVideoAdapter.notifyDataSetChanged();

        Log.d(TAG, "onResume() end");
    }

    /**
     * 获取草稿箱的视频列表
     */
    private void getListData() {
        Log.d(TAG, "getListData() start");
        mDraftListData.clear();
        // 草稿箱位置
        String mVideoPath = getContext().getFilesDir().getPath() + Constant.DRAFT_BOX_NAME;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        // 文件名
        String filename;
        // 文件后缀
        String suf;
        // 文件信息
        DraftVideoBean bean;
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
                    && suf.equalsIgnoreCase("mp4")) {
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
                mDraftListData.add(bean);
            }
        }
        // 有待发布视频时
        if (mDraftListData != null && mDraftListData.size() > 0) {
            hasDraft = true;
        } else {
            hasDraft = false;
        }
        Log.d(TAG, "getListData() end");
    }

    /**
     * String 转成日期格式
     *
     * @param longTime
     * @return
     * @throws ParseException
     */
    public String longToDate(long longTime) throws ParseException {
        Log.d(TAG, "longToDate() start");

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年M月d日");
        Date date = new Date(longTime);
        String strDate = formatter.format(date);

        Log.d(TAG, "longToDate() end");
        return strDate;
    }


    /**
     * 获取存储权限
     */
    private void getStoragePermission() {
        int readPerm = ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE);
        int writePerm = ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (readPerm != PackageManager.PERMISSION_GRANTED ||
                writePerm != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                String[] permissions = {
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE};
                ActivityCompat.requestPermissions(getActivity(), permissions,
                        PERMISSION_REQUEST_STORAGE);
            }
        } else {
            getListData();
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
                    getListData();
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
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        permissionDialog = builder.setMessage(R.string.release_set_storage_permission_msg)
                .setPositiveButton(getResources().getString(R.string.release_set), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        intent.addCategory(Intent.CATEGORY_DEFAULT);
                        intent.setData(Uri.parse("package:" + getContext().getPackageName()));
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

}


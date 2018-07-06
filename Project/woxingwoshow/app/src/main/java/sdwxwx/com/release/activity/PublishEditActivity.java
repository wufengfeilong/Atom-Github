package sdwxwx.com.release.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.tu.loadingdialog.LoadingDailog;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ksyun.ks3.exception.Ks3Error;
import com.ksyun.ks3.services.handler.PutObjectResponseHandler;
import com.ksyun.media.shortvideo.utils.FileUtils;
import com.ksyun.media.shortvideo.utils.ProbeMediaInfoTools;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.apache.http.Header;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import sdwxwx.com.R;
import sdwxwx.com.adapter.BaseAdapter;
import sdwxwx.com.adapter.BaseHolder;
import sdwxwx.com.base.BaseCallback;
import sdwxwx.com.bean.CategoryBean;
import sdwxwx.com.bean.TopicInfoBean;
import sdwxwx.com.cons.Constant;
import sdwxwx.com.home.HomeActivity;
import sdwxwx.com.home.bean.SearchUserBean;
import sdwxwx.com.home.model.HomeFragmentModel;
import sdwxwx.com.login.utils.LoginHelper;
import sdwxwx.com.release.model.ReleaseModel;
import sdwxwx.com.release.utils.KS3ClientWrap;
import sdwxwx.com.release.utils.KS3TokenTask;
import sdwxwx.com.release.utils.LimitInputTextWatcher;
import sdwxwx.com.widget.RichEditText;

public class PublishEditActivity extends AppCompatActivity {

    private final String TAG = "PublishEditActivity";

    // 选择封面
    private static final int REQUEST_COVER_CODE = 10100;
    // 选择话题
    private static final int REQUEST_TOPIC_CODE = 10110;
    // 选择@好友
    private static final int REQUEST_FRIEND_CODE = 10111;
    // 说明内容最大字数
    private final int INPUT_CONTENT_MAX_LENGHT = 50;

    private static final int RESULT_OK = -1;
    // 标记上传状态
    private static final int UPLOAD_STATE_NONE = 0;
    private static final int UPLOAD_STATE_STARTING = 1;
    private static final int UPLOAD_STATE_STARTED = 2;

    // 定位请求授权被拒绝时，手动设置权限的Dialog
    private Dialog permissionDialog = null;
    // 保存/舍弃的Dialog
    private Dialog saveDialog = null;

    private KS3TokenTask mTokenTask;
    // 连接KS3
    private KS3ClientWrap mKS3Wrap;
    // 上传ks3状态
    private AtomicInteger mUploadState;
    // 显示上传进度
    private TextView mProgressText;
    private TextView mStateTextView;
    private View mParentView;

    // 上传状态的显示窗口
    private PopupWindow mUploadWindow;

    // 栏目选择显示窗口
    private Dialog mBtmDialog;
    // 栏目选择List
    private RecyclerView mCategoryList;
    // 栏目选择List数据源
    private List<CategoryBean> mCategoryListData = new ArrayList<>();
    // 栏目选择List的Adapter
    private CategoryAdapter mCategoryAdapter;

    // 上传文件名
    private String mCurObjectKey;
    // 前画面传过来的视频路径
    private String mLocalPath;
    // 媒体操作工具
    private ProbeMediaInfoTools mProbeMediaInfoTools;


    // 屏幕高度
    private int mScreenHeight;
    // 屏幕宽度
    private int mScreenWidth;
    // 视频预览时长
    private long mPreviewLength;

    // 编缉封面标题
    private EditText mEditTitle;
    // 用户头像
    private CircleImageView mUserIcon;
    // 用户昵称
    private TextView mUserName;
    // 说点什么
    private RichEditText mEditContent;
    // 说点什么字数
    private TextView mContentLength;
    // 选择的话题
    private ImageView mTopicImg;
    private TextView mTopicText;
    // 预览封面
    private ImageView mPreviewImg;
    // 预览封面FrameLayout
    private FrameLayout mPreviewFL;

    // 地理位置
    private LocationClient mLocationClient;
    private LocationListener mLocationListener = new LocationListener();

    // 城市名称
    private String mCityName;
    // 加载中……
    private LoadingDailog mProgressDialog;
    // 视频上传后台传递数据
    // 选择的栏目ID列表
    private List<String> mSelectedCategoryIds = new ArrayList<>();
    // 是否设置了话题
    private boolean hasTopic = false;
    // 设置的话题详细
    private TopicInfoBean mTopicInfo = new TopicInfoBean();
    // 敏感词列表
    private List<String> mListSensitiveWords = new ArrayList<>();
    // 经度
    private String mLongitude = Constant.EMPTY_STRING;
    // 纬度
    private String mLatitude = Constant.EMPTY_STRING;
    // 视频所在的城市编号
    private String mCityId = Constant.EMPTY_STRING;
    // 视频所使用的音乐编号
    private String mMusicId = Constant.EMPTY_STRING;
    // 标题
    private String mTitle = Constant.EMPTY_STRING;
    // 描述
    private String mDescription = Constant.EMPTY_STRING;
    // 视频时长（总秒数）
    private String mDuration = Constant.EMPTY_STRING;
    // 视频封面截取时间
    private String mCoverTime = Constant.EMPTY_STRING;
    // 选择的@好友ID列表
    private List<String> mSelectedFriendIds = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_publish_edit);
        mParentView = LayoutInflater.from(this).inflate(R.layout.activity_publish_edit, null);
        //must set
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        initViews();

        // 获取经纬度
        positionLocation();
        // 获取城市Id
        getCurrentCityId();

        // 获取敏感词汇
        getSensitiveWords();
        // 获取所有栏目
        getCategoryList();
    }

    /**
     * 开始显示加载中
     */
    private void startProgress() {
        if (mProgressDialog.isShowing()) {
            return;
        }
        mProgressDialog.show();
    }

    /**
     * 开始显示加载中
     */
    private void stopProgress() {
        if (mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    /**
     * 组件初始化
     */
    private void initViews() {
        Log.d(TAG, "initViews() start");

        LoadingDailog.Builder builder = new LoadingDailog.Builder(this)
                .setMessage("加载中...")
                .setCancelable(true)
                .setCancelOutside(false);
        mProgressDialog = builder.create();

        mScreenHeight = getResources().getDisplayMetrics().heightPixels;
        mScreenWidth = getResources().getDisplayMetrics().widthPixels;

        mLocalPath = getIntent().getStringExtra(Constant.COMPOSE_PATH);
        mMusicId = getIntent().getStringExtra(Constant.MUSIC_ONLINE_ID);
        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        mediaMetadataRetriever.setDataSource(mLocalPath);
        // 取得视频的长度(单位为毫秒 -> 秒)
        mPreviewLength = Long.parseLong(mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION));
        mDuration = String.valueOf(mPreviewLength / 1000);

        mProbeMediaInfoTools = new ProbeMediaInfoTools();
        // 编缉封面标题
        mEditTitle = findViewById(R.id.edit_title);
        // EditText实现不可编辑
        mEditTitle.setKeyListener(null);
//        // 限制最多输入40字符，一个汉字算2个字符
//        InputFilter filter = new NameLengthFilter(40);
        mEditTitle.addTextChangedListener(new LimitInputTextWatcher(mEditTitle));
//        mEditTitle.setFilters(new InputFilter[]{filter});

        // 编缉内容
        mEditContent = findViewById(R.id.content_title);
//        InputFilter filterContent = new NameLengthFilter(50);
        mEditContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Editable editable = mEditContent.getText();
                int len = editable.length();
                if (len > INPUT_CONTENT_MAX_LENGHT) {
                    Toast.makeText(PublishEditActivity.this, R.string.publish_edit_check_max, Toast.LENGTH_SHORT).show();
                    int selEndIndex = Selection.getSelectionEnd(editable);
                    String str = editable.toString();
                    //截取新字符串
                    String newStr = str.substring(0, INPUT_CONTENT_MAX_LENGHT);
                    mEditContent.setText(newStr);
                    editable = mEditContent.getText();

                    //新字符串的长度
                    int newLen = editable.length();
                    //旧光标位置超过字符串长度
                    if (selEndIndex > newLen) {
                        selEndIndex = editable.length();
                    }
                    //设置新光标所在的位置
                    Selection.setSelection(editable, selEndIndex);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d(TAG, "afterTextChanged  s = " + s.toString());
                Log.d(TAG, "afterTextChanged  s.length() = " + s.length());
                // 限制最多输入50字
                mContentLength.setText(
                        String.format(
                                getString(
                                        R.string.publish_edit_content_length),
                                String.valueOf(s.length())));
            }
        });
        // 编辑内容字数显示
        mContentLength = findViewById(R.id.publish_edit_content_length);
        mContentLength.setText(String.format(getString(R.string.publish_edit_content_length), "0"));
        // 选择的话题
        mTopicImg = findViewById(R.id.publish_edit_add_topic);
        mTopicText = findViewById(R.id.publish_edit_topic_title);
        // 预览画面显示视频第一帧
        Bitmap bitmap = mProbeMediaInfoTools.getVideoThumbnailAtTime(
                mLocalPath, 0, 0, 0, true);

        mPreviewImg = findViewById(R.id.preview_img);
        mPreviewImg.setImageBitmap(bitmap);
        int bitmapHeight = bitmap.getHeight();
        int bitmapWidth = bitmap.getWidth();
        int h = mPreviewImg.getMaxHeight();
        int w = h * bitmapWidth / bitmapHeight;
        mPreviewImg.setAdjustViewBounds(true);
        mPreviewImg.setMaxWidth(w);

        // 用户名
        mUserName = findViewById(R.id.user_name);
        mUserName.setText(LoginHelper.getInstance().getUserBean().getNickname());
        // 用户昵称
        mUserIcon = findViewById(R.id.user_icon);
        // 用户头像
        RequestOptions options = new RequestOptions().error(R.drawable.default_header_image);
        Glide.with(this)
                .load(LoginHelper.getInstance().getUserBean().getAvatar_url())
                .apply(options)
                .into(mUserIcon);

        // 实例上传状态
        mUploadState = new AtomicInteger(UPLOAD_STATE_NONE);
        Log.d(TAG, "initViews() end");
    }

    @Override
    public void onBackPressed() {
        // 如果正在上传，做取消上传的处理
        if (mUploadState.get() >= UPLOAD_STATE_STARTING && mUploadState.get() <=
                UPLOAD_STATE_STARTED) {
            //取消上传，直接预览播放本地视频
            mKS3Wrap.cancel();
            onUploadFinished(false);
            return;
        } else {
            showSaveDialog();
        }
    }

    /**
     * 弹出保存/舍弃的Dialog
     */
    private void showSaveDialog() {
        // 如果保存Dialog正在显示，则不再重建
        if (saveDialog != null && saveDialog.isShowing()) {
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.Theme_AppCompat_Dialog);
        saveDialog = builder.setMessage(R.string.release_save_dialog_title)
                .setPositiveButton(getResources().getString(R.string.release_save_btn_tx), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        saveFileToDraftBox();

                        // 启动Home画面的MeFragment
                        startHomeActivity(3);
                    }
                }).setNegativeButton(getResources().getString(R.string.release_destroy_btn_tx), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        deleteVideos();

                        // 启动Home画面的MeFragment
                        startHomeActivity(0);
                    }
                }).create();
        saveDialog.show();
    }


    /**
     * 启动首页
     */
    private void startHomeActivity(int fragment) {
        Intent intent = new Intent(PublishEditActivity.this, HomeActivity.class);
        // 清空在HOME之上的Activity栈
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(Constant.BACK_HOME_KEY, fragment);
        startActivity(intent);
    }
    /**
     * 保存视频到草稿箱
     */
    private void saveFileToDraftBox() {

        // 获取 /data/data/pkg/file
        File desFile = getApplicationContext().getFilesDir();
        String desPath = desFile.getPath() + Constant.DRAFT_BOX_NAME;
        Log.d(TAG, "saveFileToDraftBox to desPath = " + desPath);

        // 如果来自草稿箱，则直接跳转
        if (mLocalPath.startsWith(desPath)) {
            startHomeActivity(3);
            return;
        }

        // 文件夹不存在的话，创建文件夹
        File draftDre = new File(desPath);
        if (!draftDre.exists()) {
            draftDre.mkdirs();
        }

        // 文件名
        String name = mLocalPath.substring(mLocalPath.lastIndexOf('/'));
        String nameWithMusicId = name.replace(
                Constant.VIDEO_TYPE_NAME,
                Constant.DRAFT_BOX_MUSIC_ID + mMusicId + Constant.VIDEO_TYPE_NAME);
        String nameWithUserId = nameWithMusicId.replace(
                "/", "/" + LoginHelper.getInstance().getUserId() + "id");
        File draftFile = new File(desPath + nameWithUserId);
        try {
            File srcFile = new File(mLocalPath);
            if (srcFile.exists()) {
                InputStream is = new FileInputStream(mLocalPath);
                FileOutputStream fos = new FileOutputStream(draftFile);
                byte[] buffer = new byte[1024];
                int length;
                while ((length = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, length);
                }
                is.close();
                fos.close();

                // 删除操作的视频
                deleteVideos();
                Toast.makeText(this, "文件保存草稿箱成功", Toast.LENGTH_SHORT).show();
                startHomeActivity(3);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除合成的视频
     */
    private void deleteVideos() {
        String pathBase = getApplicationContext().getFilesDir().getPath();

        // 删除合成的所有视频
        File composeFile = new File(pathBase + Constant.COMPOSE_BOX_NAME);
        if (composeFile.exists()) {
            Log.d(TAG, "saveFileToDraftBox deleteVideos 删除合成的所有视频");
            deleteAllFiles(composeFile);
        }

    }

    /**
     * 递归删除所有文件
     * @param root 操作的文件夹
     */
    private void deleteAllFiles(File root) {
        File files[] = root.listFiles();
        if (files != null)
            for (File f : files) {
                if (f.isDirectory()) { // 判断是否为文件夹
                    deleteAllFiles(f);
                    try {
                        f.delete();
                    } catch (Exception e) {
                    }
                } else {
                    if (f.exists()) { // 判断是否存在
                        deleteAllFiles(f);
                        try {
                            f.delete();
                        } catch (Exception e) {
                        }
                    }
                }
            }
    }

    /**
     * 集中处理点击事件
     *
     * @param view 操作VIew
     */
    public void onMenuClick(View view) {

        // 画面跳转Intent
        Intent intent = null;
        int requestCode = -1;
        switch (view.getId()) {
            case R.id.back_img:
                onBackPressed();
                break;
            case R.id.edit_title:
                // 选择封面
                intent = new Intent(this, ChooseCoverActivity.class);
                // 将合成后的视频存放路径传给封面选择画面
                intent.putExtra(Constant.COMPOSE_PATH, mLocalPath);
                requestCode = REQUEST_COVER_CODE;
                break;
            case R.id.publish_edit_add_topic:
            case R.id.publish_edit_topic_title:
                // 添加话题
                intent = new Intent(this, AddTopicActivity.class);
                requestCode = REQUEST_TOPIC_CODE;
                break;
            case R.id.publish_edit_friends:
                // 选择@好友
                intent = new Intent(this, FriendsListActivity.class);
                requestCode = REQUEST_FRIEND_CODE;
                break;
            case R.id.publish_btn:
                checkSensitive();
                break;
            default:
                break;
        }
        if (intent != null) {
            if (requestCode != -1) {
                startActivityForResult(intent, requestCode);
            }
        }

    }

    /**
     * 获取敏感词汇
     */
    private void getSensitiveWords() {
        Log.d(TAG, "getSensitiveWords start");

        mListSensitiveWords.clear();

        ReleaseModel.getSensitiveList(new BaseCallback<List<String>>() {
            @Override
            public void onSuccess(List<String> data) {
                Log.d(TAG, "checkSensitive onSuccess");
                mListSensitiveWords.addAll(data);
            }

            @Override
            public void onFail(String msg) {
                Log.d(TAG, "checkSensitive onFail");

                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                return;
            }
        });
        Log.d(TAG, "getSensitiveWords start");
    }

    /**
     * 检测敏感词汇
     */
    private void checkSensitive() {
        Log.d(TAG, "checkSensitive start");

        int sensitiveSize = mListSensitiveWords.size();
        // 标题
        if (!TextUtils.isEmpty(mEditTitle.getText())) {
            for (int i = 0; i < sensitiveSize; i ++) {
                if (mEditTitle.getText().toString().contains(mListSensitiveWords.get(i))) {
                    Toast.makeText(this, getString(R.string.sensitive_content_remind), Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        }

        // 内容
        if (!TextUtils.isEmpty(mEditContent.getText())) {
            for (int i = 0; i < sensitiveSize; i ++) {
                if (mEditContent.getText().toString().contains(mListSensitiveWords.get(i))) {
                    Toast.makeText(this, getString(R.string.sensitive_content_remind), Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        }

        if (mSelectedCategoryIds.size() > 0) {
            // 已经选择了栏目，直接上传
            startUpload();
        } else {
            if (mCategoryListData.size() == 0) {
                // 开始显示加载中
                startProgress();
                getCategoryList();
            }
            // 未选择栏目，去选择
            showCategoryDialog();
        }
        Log.d(TAG, "checkSensitive end");
    }

    /**
     * 弹出栏目选择Dialog
     */
    private void showCategoryDialog() {
        Log.d(TAG, "showCategoryDialog start");

        if (mBtmDialog != null && mBtmDialog.isShowing()) {
            return;
        }

        // 初始化所选栏目
        mSelectedCategoryIds = new ArrayList<>();

        // 栏目选择Dialog
        mBtmDialog = new Dialog(this, R.style.CategoryBottomDialog);
        final View view = LayoutInflater.from(this).inflate(R.layout.category_choose_dialog, null);
        mBtmDialog.setContentView(view);

        // 关闭按钮
        final ImageView closeBtn = view.findViewById(R.id.close_category_icon);
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBtmDialog.dismiss();
            }
        });

        // 确定按钮
        final TextView confirmBtn = view.findViewById(R.id.confirm_category_icon);
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 判断是否选择了栏目
                if (mSelectedCategoryIds.size() == 0) {
                    Toast.makeText(PublishEditActivity.this, getString(R.string.choose_category_empty), Toast.LENGTH_SHORT).show();
                } else {
                    // TODO:开始上传
                    startUpload();
                    mBtmDialog.dismiss();
                }
            }
        });

        // 栏目List
        mCategoryList = view.findViewById(R.id.category_list);
        GridLayoutManager rVLM = new GridLayoutManager(PublishEditActivity.this, 3);
        mCategoryList.setLayoutManager(rVLM);
        mCategoryAdapter = new CategoryAdapter(R.layout.category_item, mCategoryListData);
        mCategoryAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                // nothing
            }
        });
        mCategoryList.setAdapter(mCategoryAdapter);

        // 设置点击Dialog外领域，Dialog不消失
        mBtmDialog.setCanceledOnTouchOutside(false);
        mBtmDialog.show();

        // 设置半屏显示
        WindowManager.LayoutParams lp = mBtmDialog.getWindow().getAttributes();
        //设置宽度
        lp.width = mScreenWidth;
        //设置高度
        lp.height = mScreenHeight / 2;

        lp.gravity = Gravity.BOTTOM;
        mBtmDialog.getWindow().setAttributes(lp);
    }

    class CategoryAdapter extends BaseAdapter<CategoryBean> {

        public CategoryAdapter(int layoutId, List list) {
            super(layoutId, list);
        }

        @Override
        protected void convert(final BaseHolder holder, final CategoryBean bean) {
            // 显示栏目名称
            holder.setText(R.id.category_title_tx, bean.getName());
            TextView textView = holder.getView(R.id.category_title_tx);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (v.isSelected()) {
                        v.setSelected(false);
                        int listSize = mSelectedCategoryIds.size();
                        for (int i = 0; i < listSize; i ++) {
                            if (bean.getId() == mCategoryListData.get(holder.getPosition()).getId()) {
                                mSelectedCategoryIds.remove(bean);
                                break;
                            }
                        }
                    } else {
                        v.setSelected(true);
                        mSelectedCategoryIds.add(mCategoryListData.get(holder.getPosition()).getId());
                    }
                }
            });
        }
    }

    /**
     * 上传视频
     */
    private void startUpload() {
        mKS3Wrap = KS3ClientWrap.getInstance(getApplicationContext());
        if (!TextUtils.isEmpty(mLocalPath)) {
            mUploadState.set(UPLOAD_STATE_STARTING);

            // 设置上传文件的Key:UserID + timeTemp
            StringBuilder objectKey = new StringBuilder(
                    LoginHelper.getInstance().getUserId() + System.currentTimeMillis());
            // objectKeyMD5化
            mCurObjectKey = stringToMD5(objectKey.toString()) + Constant.VIDEO_TYPE_NAME;

            //上传必要信息：bucket, objectkey，及PutObjectResponseHandler上传过程回调
            KS3ClientWrap.KS3UploadInfo bucketInfo = new KS3ClientWrap.KS3UploadInfo
                    ("woxingwoxiu", mCurObjectKey, mPutObjectResponseHandler);

            //调用SDK内部接口触发上传
            mKS3Wrap.putObject(bucketInfo, mLocalPath,
                    mPutObjectResponseHandler, new KS3ClientWrap.OnGetAuthInfoListener() {
                        @Override
                        public KS3ClientWrap.KS3AuthInfo onGetAuthInfo(String s, String s1, String s2, String s3, String s4, String s5) {

                            if (mTokenTask == null) {
                                mTokenTask = new KS3TokenTask(getApplicationContext());
                            }

                            KS3ClientWrap.KS3AuthInfo authInfo = mTokenTask.requestTokenToAppServer(s, s1,
                                    s2, s3, s4, s5);
                            Log.d(TAG, "startUpload    KS3AuthInfo  authInfo.token = " + authInfo.token);
                            Log.d(TAG, "startUpload    KS3AuthInfo  authInfo.data = " + authInfo.date);

                            return authInfo;
                        }
                    });

        }
    }

    /**
     * 将字符串转成MD5值
     *
     * @param stringFrom
     * @return
     */
    public static String stringToMD5(String stringFrom) {
        byte[] hash;

        try {
            hash = MessageDigest.getInstance("MD5").digest(stringFrom.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }

        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10)
                hex.append("0");
            hex.append(Integer.toHexString(b & 0xFF));
        }

        return hex.toString();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult    返回数据" + requestCode);

        switch (requestCode) {
            // 选择封面
            case REQUEST_COVER_CODE:
                // If the file selection was successful
                if (resultCode == RESULT_OK) {
                    // 解析传回的封面Bitmap的时间并显示
                    if (data != null) {
                        // 获取调整的封面在视频中的时间(毫秒)
                        long seekTime = data.getExtras().getLong(Constant.CHOOSE_COVER_BITMAP, 0);
                        mCoverTime = String.valueOf(seekTime);
                        Bitmap coverBitmap = mProbeMediaInfoTools.getVideoThumbnailAtTime(mLocalPath, seekTime, 0, 0, true);
                        if (coverBitmap != null) {
                            mPreviewImg.setImageBitmap(coverBitmap);
                        }
                        // title
                        mTitle = data.getExtras().get(Constant.CHOOSE_COVER_TITLE).toString();
                        mEditTitle.setText(mTitle);
                    }
                }
                break;
            // 添加话题
            case REQUEST_TOPIC_CODE:
                // If the file selection was successful
                if (resultCode == RESULT_OK) {
                    // 解析传回的话题并显示在[说点什么…]的最后，以[# 话题名称 ]区分
                    if (data != null) {
                        // 是否设置了话题
                        hasTopic = data.getBooleanExtra(Constant.CHOOSE_TOPIC, false);
                        if (hasTopic) {
                            mTopicInfo = (TopicInfoBean) data.getSerializableExtra(Constant.RELEASE_ADD_TOPIC_CONTENT);
                            mTopicText.setText(getString(R.string.publish_edit_topic_unit) + mTopicInfo.getTitle());
                            mTopicImg.setVisibility(View.GONE);
                            mTopicText.setVisibility(View.VISIBLE);
                        } else {
                            mTopicText.setText(Constant.EMPTY_STRING);
                            mTopicImg.setVisibility(View.VISIBLE);
                            mTopicText.setVisibility(View.GONE);
                        }
                    }
                }
                break;
            // 选择@好友
            case REQUEST_FRIEND_CODE:
                // If the file selection was successful
                if (resultCode == RESULT_OK) {
                    // 解析传回的话题并显示在[说点什么…]中，以[@ username ]区分
                    if (data != null) {
                        // 是否已经@过此好友的标识
                        boolean alreadyAdded = false;

                        SearchUserBean.HaveUserBean resultFriend =
                                (SearchUserBean.HaveUserBean) data.getSerializableExtra(Constant.CHOOSE_FRIEND);

                        // 遍历数据比较，判断是否重复添加
                        for (RichEditText.XlpsFriend user : mEditContent.getXlpsFriends()) {
                            if (user.mUserId.equals(resultFriend.getId())) {
                                // 不可重复@同一个好友
                                Toast.makeText(
                                        PublishEditActivity.this,
                                        getString(R.string.publish_edit_add_friend_error),
                                        Toast.LENGTH_SHORT).show();
                                alreadyAdded = true;
                                break;
                            }
                        }
                        // 不重复才添加
                        if (!alreadyAdded) {
                            RichEditText.XlpsFriend friend = new RichEditText.XlpsFriend();
                            friend.mUserId = resultFriend.getId();
                            friend.mUserName = resultFriend.getNickname();
                            mEditContent.addFriend(friend);
                        }
                    }
                }
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    /**
     * 上传完成时
     *
     * @param success 是否上传成功
     */
    private void onUploadFinished(final boolean success) {
        Log.d(TAG, "onUploadFinished success = " + success);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (success) {
                    // 上传成功
                    Log.d(TAG, "onUploadFinished    上传成功");
                    updateToServer();
                } else {
                    // 上传失败
                    Log.d(TAG, "onUploadFinished    上传失败");
                    showToast(getString(R.string.upload_file_fail));
                }
            }
        });

        mUploadState.set(UPLOAD_STATE_NONE);
        if (mUploadWindow != null && mUploadWindow.isShowing()) {
            mUploadWindow.dismiss();
        }
    }

    private void updateToServer() {
        Log.d(TAG, "updateToServer start");

        // 传递参数
        // 不使用话题
        if (!hasTopic || TextUtils.isEmpty(mTopicInfo.getId())) {
            mTopicInfo.setId(Constant.ZERO_STRING);
        }
        // 获取说明内容
        if (TextUtils.isEmpty(mEditContent.getText())) {
            mDescription = Constant.EMPTY_STRING;
        } else {
            mDescription = mEditContent.getText().toString();
        }
        for (RichEditText.XlpsFriend friend : mEditContent.getXlpsFriends()) {
            mSelectedFriendIds.add(friend.mUserId);
        }
        ReleaseModel.addVideoToServer(mCurObjectKey,
                getSelectedIDs(mSelectedCategoryIds), mTopicInfo.getId(), mTopicInfo.getTitle(), mCityId, mMusicId, mTitle,
                mDescription, mLongitude, mLatitude, mDuration, mCoverTime, getSelectedIDs(mSelectedFriendIds), new BaseCallback<List<String>>() {
            @Override
            public void onSuccess(List<String> data) {
                // 删除本地视频
                FileUtils.deleteFile(mLocalPath);
                moveToHome();
            }

            @Override
            public void onFail(String msg) {
                showToast(msg);
            }
        });

        Log.d(TAG, "updateToServer end");
    }

    /**
     * @return 拼接栏目ID
     */
    private String getSelectedIDs(List<String> selectedList) {
        Log.d(TAG, "getSelectedIDs start");
        String resultStr = Constant.EMPTY_STRING;
        int size = selectedList.size();
        if (size > 0) {
            resultStr = selectedList.get(0);
            if (size > 1) {
                for (int i = 1; i < size; i ++) {
                    resultStr = resultStr + getString(R.string.choose_category_symbol) + selectedList.get(i);
                }
            }
        }
        Log.d(TAG, "getSelectedIDs end");
        return resultStr;
    }


    /**
     * 画面跳转至Home
     */
    private void moveToHome() {
        // 发布完毕跳转到首页
        Intent intent = new Intent(this, HomeActivity.class);
        // 清空在HOME之上的Activity栈
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        // 跳转到HOME，要显示首页
        intent.putExtra(Constant.BACK_HOME_KEY, 0);
        startActivity(intent);
    }

    /**
     * 开始上传
     */
    private void onUploadStart() {
        mUploadState.set(UPLOAD_STATE_STARTED);
        showUploadProgressDialog();
    }

    /**
     * 显示上传进度Dialog
     */
    private void showUploadProgressDialog() {
        if (mUploadWindow == null) {
            View contentView = LayoutInflater.from(this).inflate(R.layout.compose_layout, null);
            mStateTextView = (TextView) contentView.findViewById(R.id.state_text);
            mProgressText = (TextView) contentView.findViewById(R.id.progress_text);
            mStateTextView.setText(R.string.upload_file);
            mUploadWindow = new PopupWindow(contentView, WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT);
        }

        if (!mUploadWindow.isShowing()) {
            mUploadWindow.showAtLocation(mParentView, Gravity.CENTER, 0, 0);
        }
    }

    /**
     * @param progress 上传进度
     */
    private void onUploadProgress(final double progress) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mProgressText.getVisibility() == View.VISIBLE) {
                    mProgressText.setText(String.valueOf((int) progress) + "%");
                }
            }
        });
    }


    /**
     * 获取地理位置
     */
    private void positionLocation() {
        Log.d(TAG, "positionLocation start");

        RxPermissions permissions = new RxPermissions(this);
        permissions.request(Manifest.permission.ACCESS_FINE_LOCATION)
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }
                    @Override
                    public void onNext(Boolean value) {
                        // 如果已经给予定位的使用权限
                        if(value){
                            // 开始定位
                            startLocation();
                        }else {
                            openAppPermissionDetails();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                    }
                    @Override
                    public void onComplete() {
                    }
                });

        Log.d(TAG, "positionLocation end");
    }

    /**
     * 获取所有栏目
     */
    private void getCategoryList() {
        Log.d(TAG, "getCategoryList start");

        startProgress();

        HomeFragmentModel.getCategories(new BaseCallback<List<CategoryBean>>() {
            @Override
            public void onSuccess(List<CategoryBean> data) {
                stopProgress();
                Log.d(TAG, "showCategoryDialog onSuccess");
                mCategoryListData.clear();
                mCategoryListData.addAll(data);
                if (mCategoryAdapter != null) {
                    mCategoryAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onFail(String msg) {
                stopProgress();
                // 错误提示
                Toast.makeText(PublishEditActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });

        Log.d(TAG, "getCategoryList end");
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
        permissionDialog = builder.setMessage(R.string.location_permission_open_msg)
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
                        showToast(getString(R.string.release_permission_refused));
                        // 画面结束
                        finish();
                    }
                }).create();
        permissionDialog.show();
    }

    private void showToast(String toastStr) {
        Toast.makeText(this, toastStr, Toast.LENGTH_SHORT).show();
    }

    /**
     * 获取城市ID
     */
    private void getCurrentCityId() {
        mCityId = LoginHelper.getInstance().getCityId();
    }

    private void startLocation() {
        Log.d(TAG, "startLocation start");

        mLocationClient = new LocationClient(getApplicationContext());
        //声明LocationClient类
        mLocationClient.registerLocationListener(mLocationListener);
        //注册监听函数
        LocationClientOption option = new LocationClientOption();

        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setCoorType("bd09ll");
        option.setOpenGps(true);
        // 为了获取城市
        option.setIsNeedAddress(true);
        option.setLocationNotify(true);
        option.setIgnoreKillProcess(false);
        option.SetIgnoreCacheException(false);
        option.setEnableSimulateGps(false);
        mLocationClient.setLocOption(option);
        mLocationClient.start();

        Log.d(TAG, "startLocation end");
    }


    public class LocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location){
            // 获取纬度信息
            mLatitude = String.valueOf(location.getLatitude());
            //获取经度信息
            mLongitude = String.valueOf(location.getLongitude());
            mCityName = location.getCity();
            mLocationClient.stop();
        }
    }

    private PutObjectResponseHandler mPutObjectResponseHandler = new PutObjectResponseHandler() {
        @Override
        public void onTaskFailure(int statesCode, Ks3Error error, Header[] responseHeaders, String response, Throwable paramThrowable) {
            Log.e(TAG, "onTaskFailure:" + statesCode);
            onUploadFinished(false);
        }

        @Override
        public void onTaskSuccess(int statesCode, Header[] responseHeaders) {
            Log.d(TAG, "onTaskSuccess:" + statesCode);
            onUploadFinished(true);
        }

        @Override
        public void onTaskStart() {
            Log.d(TAG, "onTaskStart");
            onUploadStart();
        }

        @Override
        public void onTaskFinish() {
            Log.d(TAG, "onTaskFinish");
            mUploadState.set(UPLOAD_STATE_NONE);
        }

        @Override
        public void onTaskCancel() {
            Log.d(TAG, "onTaskCancel");
            mUploadState.set(UPLOAD_STATE_NONE);
        }

        @Override
        public void onTaskProgress(double progress) {
            onUploadProgress(progress);
        }
    };
}

package woxingwoxiu.com.release.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
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

import com.ksyun.ks3.exception.Ks3Error;
import com.ksyun.ks3.services.handler.PutObjectResponseHandler;
import com.ksyun.media.shortvideo.utils.FileUtils;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import woxingwoxiu.com.R;
import woxingwoxiu.com.bean.UserBean;
import woxingwoxiu.com.cons.Constant;
import woxingwoxiu.com.home.HomeActivity;
import woxingwoxiu.com.release.utils.HttpRequestTask;
import woxingwoxiu.com.release.utils.KS3ClientWrap;
import woxingwoxiu.com.release.utils.KS3TokenTask;
import woxingwoxiu.com.release.utils.LimitInputTextWatcher;

public class PublishEditActivity extends AppCompatActivity {

    private final String TAG = "PublishEditActivity";

    // 选择封面
    private static final int REQUEST_COVER_CODE = 10100;
    // 选择话题
    private static final int REQUEST_TOPIC_CODE = 10110;
    // 选择@好友
    private static final int REQUEST_FRIEND_CODE = 10111;

    // 标记上传状态
    private static final int UPLOAD_STATE_NONE = 0;
    private static final int UPLOAD_STATE_STARTING = 1;
    private static final int UPLOAD_STATE_STARTED = 2;

    private KS3TokenTask mTokenTask;
    // 连接KS3
    private KS3ClientWrap mKS3Wrap;
    // 上传ks3状态
    private AtomicInteger mUploadState;
    // 显示上传进度
    private TextView mProgressText;
    private TextView mStateTextView;
    // 上传状态的显示窗口
    private PopupWindow mUploadWindow;
    // 上传文件名
    private String mCurObjectKey;
    // 前画面传过来的视频路径
    private String mLocalPath;
    // 视频预览时长
    private long mPreviewLength;
    // 选择的@好友列表
    private List<UserBean> mListFriend;

    // 编缉封面标题
    private EditText mEditTitle;
    // 说点什么
    private EditText mEditContent;
    // 说点什么字数
    private TextView mContentLength;
    // 预览封面
    private ImageView mPreviewImg;
    // 预览封面FrameLayout
    private FrameLayout mPreviewFL;

    private MediaMetadataRetriever mMedia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.publish_edit_activity);
        //must set
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        initViews();
    }


    private void initViews() {
        Log.d(TAG, "initViews() start");

        mLocalPath = getIntent().getStringExtra(Constant.COMPOSE_PATH);
        mPreviewLength = getIntent().getExtras().getLong(Constant.PREVIEW_LEN);
        // 编缉封面标题
        mEditTitle = findViewById(R.id.edit_title);
        // EditText实现不可编辑
        mEditTitle.setKeyListener(null);
        // 限制最多输入40字符，一个汉字算2个字符
        InputFilter filter = new NameLengthFilter(40);
        mEditTitle.addTextChangedListener(new LimitInputTextWatcher(mEditTitle));
        mEditTitle.setFilters(new InputFilter[]{filter});

        // 编缉内容
        mEditContent = findViewById(R.id.content_title);
        // 限制最多输入50字，两个半角英数算一个字，一个绘文字算一个字
        InputFilter filterContent = new NameLengthFilter(100);
        mEditContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.d(TAG, "beforeTextChanged  s = " + s.toString());
                Log.d(TAG, "beforeTextChanged  start = " + start);
                Log.d(TAG, "beforeTextChanged  count = " + count);
                Log.d(TAG, "beforeTextChanged  after = " + after);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d(TAG, "onTextChanged  s = " + s.toString());
                Log.d(TAG, "onTextChanged  start = " + start);
                Log.d(TAG, "onTextChanged  before = " + before);
                Log.d(TAG, "onTextChanged  count = " + count);

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d(TAG, "afterTextChanged  s = " + s.toString());
            }
        });
        mEditContent.setFilters(new InputFilter[]{filterContent});

        // 编辑内容字数显示
        mContentLength = findViewById(R.id.publish_edit_content_length);
        mContentLength.setText(String.format(getString(R.string.publish_edit_content_length), "0"));

        // 预览画面显示视频第一帧
        mMedia = new MediaMetadataRetriever();
        mMedia.setDataSource(getIntent().getStringExtra(Constant.COMPOSE_PATH));
        Bitmap bitmap = mMedia.getFrameAtTime();

        mPreviewImg = findViewById(R.id.preview_img);
        mPreviewImg.setImageBitmap(bitmap);
        int bitmapHeight = bitmap.getHeight();
        int bitmapWidth = bitmap.getWidth();
        int h = mPreviewImg.getMaxHeight();
        int w = h * bitmapWidth / bitmapHeight;
        mPreviewImg.setAdjustViewBounds(true);
        mPreviewImg.setMaxWidth(w);

//        mPreviewFL = findViewById(R.id.preview_fl);
//        LinearLayout.LayoutParams fl = new LinearLayout.LayoutParams(w, h);
//        mPreviewFL.setLayoutParams(fl);

        // @好友的列表
        mListFriend = new ArrayList<>();

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
            super.onBackPressed();
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
                intent.putExtra(Constant.PREVIEW_LEN, mPreviewLength);
                requestCode = REQUEST_COVER_CODE;
                break;
            case R.id.publish_edit_add_topic:
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
                startUpload();
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
     * 上传视频
     */
    private void startUpload() {
        mKS3Wrap = KS3ClientWrap.getInstance(getApplicationContext());
        if (!TextUtils.isEmpty(mLocalPath)) {
            mUploadState.set(UPLOAD_STATE_STARTING);
            String mineType = FileUtils.getMimeType(new File(mLocalPath));
            StringBuilder objectKey = new StringBuilder(getPackageName() +
                    "/" + System.currentTimeMillis() + ".mp4");
            mCurObjectKey = objectKey.toString();
            //上传必要信息：bucket,objectkey，及PutObjectResponseHandler上传过程回调
            KS3ClientWrap.KS3UploadInfo bucketInfo = new KS3ClientWrap.KS3UploadInfo
                    ("ksvsdemo", mCurObjectKey, mPutObjectResponseHandler);
            //调用SDK内部接口触发上传
            mKS3Wrap.putObject(bucketInfo, mLocalPath,
                    mPutObjectResponseHandler, new KS3ClientWrap.OnGetAuthInfoListener() {
                        @Override
                        public KS3ClientWrap.KS3AuthInfo onGetAuthInfo(String s, String s1, String s2, String s3, String s4, String s5) {

                            if (mTokenTask == null) {
                                mTokenTask = new KS3TokenTask(getApplicationContext());
                            }

                            KS3ClientWrap.KS3AuthInfo authInfo = mTokenTask.requsetTokenToAppServer(s, s1,
                                    s2, s3, s4, s5);
                            Log.d(TAG, "startUpload    KS3AuthInfo" + authInfo);

                            return authInfo;
                        }
                    });

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult    返回数据" + requestCode);

        switch (requestCode) {
            // 选择封面
            case REQUEST_COVER_CODE:
                // If the file selection was successful
                if (resultCode == RESULT_OK) {
                    // 解析传回的封面Bitmap并显示
                    if (data != null) {
                        // 获取调整的封面在视频中的时间
                        long seekTime = data.getExtras().getLong(Constant.CHOOSE_COVER_BITMAP);

                        Bitmap coverBitmap = mMedia.getFrameAtTime(seekTime);
                        mPreviewImg.setImageBitmap(coverBitmap);
                    }
                }
                break;
            // 添加话题
            case REQUEST_TOPIC_CODE:
                // If the file selection was successful
                if (resultCode == RESULT_OK) {
                    // 解析传回的话题并显示在[说点什么…]的最后，以[# 话题名称 ]区分
                    if (data != null) {
                        String resultTopic = data.getStringExtra(Constant.CHOOSE_TOPIC);
                        mEditContent.getText().insert(mEditContent.getSelectionStart(),
                                getString(R.string.publish_edit_topic_unit) + resultTopic);
                    }
                }
                break;
            // 选择@好友
            case REQUEST_FRIEND_CODE:
                // If the file selection was successful
                if (resultCode == RESULT_OK) {
                    // 解析传回的话题并显示在[说点什么…]中，以[@username ]区分
                    if (data != null) {
                        // 是否已经@过此好友的标识
                        boolean alreadyAdded = false;

                        UserBean resultFriend = (UserBean) data.getSerializableExtra(Constant.CHOOSE_FRIEND);

                        // 遍历数据比较，判断是否重复添加
                        for (UserBean user : mListFriend) {
                            if (user.getAvatar_url() == resultFriend.getAvatar_url()) {
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
                            mEditContent.getText().insert(mEditContent.getSelectionStart(),
                                    getString(R.string.publish_edit_topic_unit) + resultFriend.getNickname());
                            mListFriend.add(resultFriend);
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
        mUploadState.set(UPLOAD_STATE_NONE);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (success) {
                    // 上传成功
                    Log.d(TAG, "onUploadFinished    上传成功");
                    mProgressText.setVisibility(View.INVISIBLE);
                    mStateTextView.setVisibility(View.VISIBLE);
                    mStateTextView.setText(R.string.upload_file_success);
                    updateToServer();
                    moveToHome();
                } else {
                    // 上传失败
                    Log.d(TAG, "onUploadFinished    上传失败");
                    if (mStateTextView != null) {
                        mStateTextView.setVisibility(View.VISIBLE);
                        mStateTextView.setText(R.string.upload_file_fail);
                    }
                }
            }
        });
    }

    private void updateToServer() {
        Log.d(TAG, "将上传的视频信息更新至服务器");

        // TODO:将上传的视频信息更新至服务器
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

    private PutObjectResponseHandler mPutObjectResponseHandler = new PutObjectResponseHandler() {
        @Override
        public void onTaskFailure(int statesCode, Ks3Error error, Header[] responceHeaders, String response, Throwable paramThrowable) {
            Log.e(TAG, "onTaskFailure:" + statesCode);
            onUploadFinished(false);
        }

        @Override
        public void onTaskSuccess(int statesCode, Header[] responceHeaders) {
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

    /**
     * 控制EditText最多能输入40字符
     */
    class NameLengthFilter implements InputFilter {
        // 最大英文/数字长度 一个汉字算两个字母
        int MAX_EN;
        // unicode编码，判断是否为汉字
        String regEx = "[\\u4e00-\\u9fa5]";

        /**
         * @param mAX_EN 最多限制数
         */
        public NameLengthFilter(int mAX_EN) {
            super();
            MAX_EN = mAX_EN;
        }

        @Override
        public CharSequence filter(CharSequence source, int start, int end,
                                   Spanned dest, int dstart, int dend) {
            int destCount = dest.toString().length()
                    + getChineseCount(dest.toString());
            int sourceCount = source.toString().length()
                    + getChineseCount(source.toString());
            if (destCount > MAX_EN) {
                int count = 0;
                String subStr = "";
                Toast.makeText(PublishEditActivity.this, R.string.publish_edit_check_max, Toast.LENGTH_SHORT).show();
                for (int i = 0; i < source.length() && count < MAX_EN; i++) {
                    String b = Character.toString(source.charAt(i));
                    if (b.matches(regEx)) {
                        count += 2;
                    } else count++;
                    subStr += b;
                }


                return subStr;

            } else {
                return source;
            }
        }

        /**
         * @param str 输入的字符串
         * @return count 汉字字数
         */
        private int getChineseCount(String str) {
            int count = 0;
            Pattern p = Pattern.compile(regEx);
            Matcher m = p.matcher(str);
            while (m.find()) {
                for (int i = 0; i <= m.groupCount(); i++) {
                    count = count + 1;
                }
            }
            return count;
        }
    }
}

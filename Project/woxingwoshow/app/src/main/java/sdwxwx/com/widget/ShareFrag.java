package sdwxwx.com.widget;

import android.Manifest;
import android.app.*;
import android.content.*;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.*;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import com.mob.MobSDK;
import com.mob.tools.utils.ResHelper;
import com.mob.tools.utils.UIHandler;
import com.tbruyelle.rxpermissions2.RxPermissions;
import io.reactivex.functions.Consumer;
import sdwxwx.com.R;
import sdwxwx.com.base.BaseCallback;
import sdwxwx.com.cons.Constant;
import sdwxwx.com.http.APIClient;
import sdwxwx.com.login.activity.LoginActivity;
import sdwxwx.com.login.utils.LoginHelper;
import sdwxwx.com.me.service.MeUpdateService;
import sdwxwx.com.play.activity.PlayVideoActivity;
import sdwxwx.com.play.model.PlayVideoFragmentModel;
import sdwxwx.com.util.ImageUtil;

import java.util.HashMap;

/**
 * 分享窗口
 * Created by 860115025 on 2018/05/23.
 */
public class ShareFrag extends DialogFragment implements Handler.Callback,View.OnClickListener, PlatformActionListener {

    private static final String TAG = "ShareFrag";

    private View rootView;
    protected HashMap<String, Object> shareParamsMap;
    private String url;
    /** 上下文环境 */
    protected Context context = getActivity();
    /** 举报 */
    private ImageView jubao;
    /** 复制链接 */
    private ImageView copyLink;
    /** b保存 */
    private ImageView shareSave;
    /** layoutId */
    private int layoutId;
    /** 执行操作时的ParamMap */
    private HashMap<String, Object> paramsMap;
    /** 进度条 */
    private ProgressDialog progressDialog;
    /** 只是下载 */
    private boolean isOnlyDownload = false;
    /** 微信分享 */
    private boolean isWeixinShare = false;
//    public interface OnLoadCallback{
//        void onSuccess(boolean isOnlyDownload,boolean isWeixinShare);
//        void onFail();
//    }
//
//    public OnLoadCallback mOnLoadCallback;
//
//    public void setOnLoadCallback(OnLoadCallback callback){
//        mOnLoadCallback = callback;
//    }

    /**
     * 构造函数
     */
    public ShareFrag() {
    }

    public final void setShareParamsMap(HashMap<String, Object> shareParamsMap) {
        this.shareParamsMap = shareParamsMap;
    }

    /**
     * 表示画面
     */
    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        Window window = dialog.getWindow();
        dialog.setCanceledOnTouchOutside(true);
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        window.setLayout(dm.widthPixels, ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.BOTTOM);
        context = getActivity();
        //设置背景透明，不设置dialog可能会出现黑边
//        window.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.color.transparent));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ((ViewGroup) (rootView.getParent())).removeView(rootView);
    }

    /**
     * 绑定事件
     * @param activity
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (rootView == null) {
            LayoutInflater inflater = LayoutInflater.from(activity);
            // 获取要表示的layout
            layoutId = Integer.parseInt(shareParamsMap.get("layoutId").toString());
            rootView = inflater.inflate(layoutId, null, false);

            // 获取画面的各控件
            // 微信好友分享
            ImageView shareWechat = (ImageView) rootView.findViewById(R.id.play_share_send_weixin_iv);
            shareWechat.setOnClickListener(this);
            // 微信朋友圈分享
            ImageView shareMoments = (ImageView) rootView.findViewById(R.id.play_share_friend_circle_iv);
            shareMoments.setOnClickListener(this);
            // QQ好友分享
            ImageView shareQq = (ImageView) rootView.findViewById(R.id.play_share_send_qq_iv);
            shareQq.setOnClickListener(this);
            // 微博分享
            ImageView shareWeibo = (ImageView) rootView.findViewById(R.id.play_share_weibo_iv);
            shareWeibo.setOnClickListener(this);
            // QQ朋友圈分享
            ImageView shareZone = (ImageView) rootView.findViewById(R.id.play_share_qzone_iv);
            shareZone.setOnClickListener(this);
            // 取消
            TextView shareCancel = (TextView) rootView.findViewById(R.id.play_share_cancel);
            shareCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
            // 不是分享二维码的时候再表示下面两个按钮
            if (layoutId == R.layout.qr_code_share) {
                // 保存
                ImageView shareSave = (ImageView) rootView.findViewById(R.id.play_share_save_iv);
                shareSave.setOnClickListener(this);
            } else if (layoutId == R.layout.web_page_share) {
                // 复制链接
                ImageView copyLink = (ImageView) rootView.findViewById(R.id.play_share_copy_link_iv);
                copyLink.setOnClickListener(this);
            } else {
                // 复制链接
                ImageView copyLink = (ImageView) rootView.findViewById(R.id.play_share_copy_link_iv);
                copyLink.setOnClickListener(this);
                // 举报
                ImageView jubao = (ImageView) rootView.findViewById(R.id.play_share_tip_off_iv);
                jubao.setOnClickListener(this);
                // 保存
                ImageView shareSave = (ImageView) rootView.findViewById(R.id.play_video_share_save_iv);
                shareSave.setOnClickListener(this);
                // 删除
                if (Integer.valueOf(shareParamsMap.get("type").toString())==3) {
                    ImageView deleteVideo = rootView.findViewById(R.id.play_share_delete_iv);
                    deleteVideo.setOnClickListener(this);
                    deleteVideo.setVisibility(View.VISIBLE);
                    // 举报
                    jubao.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(context, "自己不能举报自己。", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    jubao.setOnClickListener(this);
                }
            }
        }
        // 初始化ShareSDK
        MobSDK.init(activity);
    }

    /**
     * 点击时的处理
     * @param v
     */
    @Override
    public void onClick(View v) {
        // 分享对话框消失
        dismiss();
        Platform platform;
        switch (v.getId()) {
            case R.id.play_share_send_weixin_iv:
                if (Integer.valueOf(shareParamsMap.get("shareType").toString()) == Platform.SHARE_VIDEO){
                    isWeixinShare = true;
                    requestPermissionsDownload();
                    return;
                }
                platform = ShareSDK.getPlatform("Wechat");
                if (platform.isClientValid()) {
                        shareSilently(platform);
                } else {
                    toast("ssdk_wechat_client_inavailable");
                }
                break;
            case R.id.play_share_friend_circle_iv:
                if (Integer.valueOf(shareParamsMap.get("shareType").toString()) == Platform.SHARE_VIDEO){
                    isWeixinShare = true;
                    requestPermissionsDownload();
                    return;
                }
                platform = ShareSDK.getPlatform("WechatMoments");
                if (platform.isClientValid()) {
                        shareSilently(platform);
                } else {
                    toast("ssdk_wechat_client_inavailable");
                }
                break;
            case R.id.play_share_weibo_iv:
                platform = ShareSDK.getPlatform("SinaWeibo");
                if (platform.isClientValid()) {
                    shareSilently(platform);
                }else {
                    toast("ssdk_weibo_client_inavailable");
                }
                break;
            // 如果是QQ好友分享
            case R.id.play_share_send_qq_iv:
                if (Integer.valueOf(shareParamsMap.get("shareType").toString()) == Platform.SHARE_VIDEO){
                    requestPermissionsDownload();
                    return;
                }
                platform = ShareSDK.getPlatform("QQ");
                if (platform.isClientValid()) {
                        shareSilently(platform);
                } else {
                    toast("ssdk_qq_client_inavailable");
                }
                break;
            // 如果是QQ空间分享
            case R.id.play_share_qzone_iv:
                if (Integer.valueOf(shareParamsMap.get("shareType").toString()) == Platform.SHARE_VIDEO){
                    requestPermissionsDownload();
                    return;
                }
                    platform = ShareSDK.getPlatform("QZone");
                if (platform.isClientValid()) {
                        shareSilently(platform);
                } else {
                    toast("ssdk_qq_client_inavailable");
                }
                break;
            // 复制到剪贴板
            case R.id.play_share_copy_link_iv: {
                ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData mClipData = ClipData.newPlainText("分享链接", shareParamsMap.get("url").toString());
                cm.setPrimaryClip(mClipData);
                toast("clip_success");
                break;
            }
            case R.id.play_share_save_iv: {
                // 先生成图片
                Bitmap bitmap = ImageUtil.onCut(getActivity(), 52f, 104f);
                // 保存到相册
                ImageUtil.saveBmp2Gallery(context,bitmap);
                toast("ssdk_photo_save");
                break;
            }
            case R.id.play_share_tip_off_iv: {
                final Dialog dialog = new Dialog(context, R.style.BottomDialog);
                View view = LayoutInflater.from(context).inflate(R.layout.message_report_dialog, null);
                TextView nameTv = view.findViewById(R.id.msg_report_nick_name);
                if (LoginHelper.getInstance().isOnline()) {
                    nameTv.setText(LoginHelper.getInstance().getUserBean().getNickname());
                } else {
                    context.startActivity(new Intent(context,LoginActivity.class));
                    return;
                }
                final EditText contentEt = view.findViewById(R.id.feedback_content);
                TextView okBtn = view.findViewById(R.id.btn_ok);
                okBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PlayVideoFragmentModel.videoComplain(LoginHelper.getInstance().getUserId()
                                ,shareParamsMap.get("videoId")+""
                                ,contentEt.getText().toString()
                                , new BaseCallback<String>() {
                                    @Override
                                    public void onSuccess(String data) {
                                        dialog.dismiss();
                                        Toast.makeText(context, "举报成功！", Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onFail(String msg) {
                                        dialog.dismiss();
                                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                });
                TextView cancelBtn = view.findViewById(R.id.btn_cancel);
                cancelBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
//                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
//                layoutParams.width = getActivity().getResources().getDisplayMetrics().widthPixels* 8 / 10;
//                view.setLayoutParams(layoutParams);
                dialog.setContentView(view);
                dialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
                dialog.setCanceledOnTouchOutside(true);
                dialog.show();
                break;
            }
            case R.id.play_video_share_save_iv: {
                isOnlyDownload = true;
                requestPermissionsDownload();
                break;
            }
            case R.id.play_share_delete_iv:{
                    AlertDialog.Builder mDialog = new AlertDialog.Builder(context);
//                    mDialog.setTitle("版本更新");
                    mDialog.setMessage("确定删除吗？");
                    mDialog.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            PlayVideoFragmentModel.videoRemove(LoginHelper.getInstance().getUserId()
                                    , shareParamsMap.get("videoId") + ""
                                    , new BaseCallback<String>() {
                                        @Override
                                        public void onSuccess(String data) {
                                            Log.d(TAG, "onSuccess: 删除成功!");
                                            context.sendBroadcast(new Intent("com.sdwxwx.delete.video"));
                                            Toast.makeText(context, "删除成功!", Toast.LENGTH_SHORT).show();
                                            ((PlayVideoActivity)context).finish();
                                        }

                                        @Override
                                        public void onFail(String msg) {
                                            Log.d(TAG, "onSuccess: 删除失败!");
                                            Toast.makeText(context, "删除失败!", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                            dialog.dismiss();
                        }
                    }).setPositiveButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).create().show();
                break;
            }
        }
    }

    private void requestPermissionsDownload() {
        RxPermissions mPermissions = new RxPermissions(getActivity());
        final String[] mRequestPermissions = { Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mPermissions
                    .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .subscribe(new Consumer<Boolean>() {
                        @Override
                        public void accept(Boolean isAgree) {
                            if (isAgree) {
                                // 下载视频
                                startDownload();
                                Log.d("permission", "---- 请求通过----");
                            } else {
                                Toast.makeText(context, "权限被拒绝", Toast.LENGTH_SHORT).show();
                                for (String permission : mRequestPermissions) {
                                    if (ContextCompat.checkSelfPermission(getActivity(), permission) == PackageManager.PERMISSION_DENIED) {
                                        Log.d("permission", "---- 权限被拒绝----" + permission);
                                    }
                                }
                            }
                        }
                    });
        } else {
            startDownload();
        }
    }

    /**
     * 分享
     * @param platform
     */
    final void shareSilently(Platform platform) {
        setParamByPlatform(platform);
        Platform.ShareParams sp = new Platform.ShareParams(paramsMap);
        if (sp != null) {
            toast("ssdk_oks_sharing");
            platform.setPlatformActionListener(this);
            platform.share(sp);
        }

    }

    /**
     * 分享成功
     * @param platform
     * @param action
     * @param hashMap
     */
    @Override
    public void onComplete(Platform platform, int action, HashMap<String, Object> hashMap) {
        Message msg = new Message();
        msg.arg1 = 1;
        msg.arg2 = action;
        msg.obj = platform;
        UIHandler.sendMessage(msg, this);
        if (Integer.valueOf(shareParamsMap.get("shareType").toString()) == Platform.SHARE_VIDEO) {
            PlayVideoFragmentModel.videoShare(LoginHelper.getInstance().getUserId()
                    , shareParamsMap.get("videoId") + ""
                    , new BaseCallback<String>() {
                        @Override
                        public void onSuccess(String data) {
                            Toast.makeText(context, "分享成功!", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFail(String msg) {
                            Toast.makeText(context, "分享失败!", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    /**
     * 分享失败
     * @param platform
     * @param action
     * @param throwable
     */
    @Override
    public void onError(Platform platform, int action, Throwable throwable) {
        throwable.printStackTrace();
        Message msg = new Message();
        msg.arg1 = 2;
        msg.arg2 = action;
        msg.obj = throwable;
        UIHandler.sendMessage(msg, this);
        // 分享失败的统计
        ShareSDK.logDemoEvent(4, platform);
    }

    /**
     * 分享取消
     * @param platform
     * @param action
     */
    @Override
    public void onCancel(Platform platform, int action) {
        Message msg = new Message();
        msg.arg1 = 3;
        msg.arg2 = action;
        msg.obj = platform;
        UIHandler.sendMessage(msg, this);
        // 分享失败的统计
        ShareSDK.logDemoEvent(5, platform);
    }

    /**
     * 分享结束时的处理
     * @param msg
     * @return
     */
    public final boolean handleMessage(Message msg) {
        // 如果是分享二维码
        if (layoutId == R.layout.qr_code_share) {
            // 删除生成的二维码图片
            String imagePath = String.valueOf(shareParamsMap.get("imagePath"));
            ImageUtil.deleteFile(imagePath);
        }
        switch (msg.arg1) {
            case 1:  break;
            case 2: {
                // 失败
                String expName = msg.obj.getClass().getSimpleName();
                if ("WechatClientNotExistException".equals(expName)
                        || "WechatTimelineNotSupportedException".equals(expName)
                        || "WechatFavoriteNotSupportedException".equals(expName)) {
                    toast("ssdk_wechat_client_inavailable");
                } else if ("QQClientNotExistException".equals(expName)) {
                    toast("ssdk_qq_client_inavailable");
                } else {
                    toast("ssdk_oks_share_failed");
                }
            } break;
            case 3: {
                // 取消
                toast("ssdk_oks_share_canceled");
            } break;
        }
        return false;
    }

    /**
     * 提示信息
     * @param resOrName
     */
    private void toast(final String resOrName) {
        int resId = ResHelper.getStringRes(context, resOrName);
        if (resId > 0) {
            Toast.makeText(context, resId, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, resOrName, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 根据分享平台以及分享类型设定相应的参数
     * @param platform
     */
    private void setParamByPlatform(Platform platform) {
        paramsMap = new HashMap<String, Object>(100);
        // 获取分享类型
        int shareType = (int)shareParamsMap.get("shareType");
        paramsMap.put("shareType", shareType);
        if (shareParamsMap.get("imagePath") != null) {
            paramsMap.put("imagePath", shareParamsMap.get("imagePath"));
        }
        // 如果是分享图片
        if (Platform.SHARE_IMAGE == shareType) {
            // 如果是新浪微博，则必须设定text字段
            if ("SinaWeibo".equals(platform.getDb().getPlatformNname())) {
                paramsMap.put("text",shareParamsMap.get("text"));
            } else if("QZone".equals(platform.getDb().getPlatformNname())) {
                // 如果是QQ空间，则需要设定以下字段
                paramsMap.put("site",shareParamsMap.get("site"));
                paramsMap.put("siteUrl",shareParamsMap.get("siteUrl"));
//                paramsMap.put("text",shareParamsMap.get("text"));
            }  else if("Wechat".equals(platform.getDb().getPlatformNname())
                    || "WechatMoments".equals(platform.getDb().getPlatformNname())) {
                // 如果是微信好友或者是朋友圈，则设定以下字段
                paramsMap.put("title",shareParamsMap.get("title"));
            }
            // 如果是分享视频或者分享个人主页
        } else if (Platform.SHARE_WEBPAGE == shareType || Platform.SHARE_VIDEO == shareType) {
            // 如果是新浪微博，则必须设定text字段
            if ("SinaWeibo".equals(platform.getDb().getPlatformNname())) {
                paramsMap.put("text",shareParamsMap.get("text"));
                paramsMap.put("imageUrl",shareParamsMap.get("imageUrl"));
            } else if("QZone".equals(platform.getDb().getPlatformNname())) {
                // 如果是QQ空间，则需要设定以下字段
                paramsMap.put("title",shareParamsMap.get("title"));
                paramsMap.put("titleUrl",shareParamsMap.get("titleUrl"));
                paramsMap.put("site",shareParamsMap.get("site"));
                paramsMap.put("siteUrl",shareParamsMap.get("siteUrl"));
                paramsMap.put("text",shareParamsMap.get("text"));
            }  else if("Wechat".equals(platform.getDb().getPlatformNname())
                    || "WechatMoments".equals(platform.getDb().getPlatformNname())) {
                // 如果是微信好友或者是朋友圈，则设定以下字段
                paramsMap.put("title",shareParamsMap.get("title"));
                paramsMap.put("url",shareParamsMap.get("url"));

            } else if ("QQ".equals(platform.getDb().getPlatformNname())) {
                paramsMap.put("title",shareParamsMap.get("title"));
                paramsMap.put("titleUrl",shareParamsMap.get("titleUrl"));
                paramsMap.put("text",shareParamsMap.get("text"));
                paramsMap.put("imageUrl",shareParamsMap.get("imageUrl"));
            }
        }
    }

    /**
     * 下载进度条
     */
    private void createPD(){
        progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("正在下载本视频");
        progressDialog.setCancelable(false);// 设置是否可以通过点击Back键取消
        progressDialog.setCanceledOnTouchOutside(false);// 设置在点击Dialog外是否取消Dialog进度条
        progressDialog.setMax(100);
        progressDialog.show();
    }
    /**
     * 启动Service 开始下载
     */
    private void startDownload(){
        createPD();
        // 启动Service 开始下载
        MeUpdateService.startUpdate(
                context, shareParamsMap.get("videoUrl").toString(),
                "woxingwoxiu_" + APIClient.getTimes() + Constant.VIDEO_TYPE_NAME,
                2, new MeUpdateService.OnProgressListener() {
            @Override
            public void onProgress(int progress) {
                //更新对话框进度条
                progressDialog.setProgress(progress);
            }

            @Override
            public void onSuccess(boolean isSuccess) {
                progressDialog.dismiss();
                Intent intent = new Intent();
                intent.setAction(Constant.INTENT_FILTER_DOWNLOAD_VIDEO);
                intent.putExtra("isSuccess",isSuccess);
                intent.putExtra("isOnlyDownload",isOnlyDownload);
                intent.putExtra("isWeixinShare",isWeixinShare);
                intent.putExtra("position",Integer.valueOf(shareParamsMap.get("pos").toString()));
                context.sendBroadcast(intent);
//                if (mOnLoadCallback != null) {
//                    if (isSuccess) {
//                        mOnLoadCallback.onSuccess(isOnlyDownload,isWeixinShare);
//                    } else {
//                        mOnLoadCallback.onFail();
//                    }
//                }
            }
        });
    }

}

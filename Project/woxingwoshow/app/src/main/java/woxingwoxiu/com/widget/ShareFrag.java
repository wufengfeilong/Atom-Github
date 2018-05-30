package woxingwoxiu.com.widget;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.mob.MobSDK;
import com.mob.tools.utils.ResHelper;
import com.mob.tools.utils.UIHandler;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;

import cn.emay.sdk.util.StringUtil;
import cn.sharesdk.framework.CustomPlatform;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import woxingwoxiu.com.R;
import woxingwoxiu.com.me.activity.QRCodeGenerateActivity;
import woxingwoxiu.com.util.ImageUtil;

/**
 * 分享窗口
 * Created by 860115025 on 2018/05/23.
 */
public class ShareFrag extends DialogFragment implements Handler.Callback,View.OnClickListener, PlatformActionListener {
    private View rootView;
    protected HashMap<String, Object> shareParamsMap;
    private String url;
    protected boolean silent;
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

    /**
     * 构造函数
     */
    public ShareFrag() {
    }

    public final void setSilent(boolean silent) {
        this.silent = silent;
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
            if (layoutId != R.layout.qr_code_share) {
                // 举报
                ImageView jubao = (ImageView) rootView.findViewById(R.id.play_share_tip_off_iv);
                jubao.setOnClickListener(this);
                // 复制链接
                ImageView copyLink = (ImageView) rootView.findViewById(R.id.play_share_copy_link_iv);
                copyLink.setOnClickListener(this);
            }
            // 保存
            ImageView shareSave = (ImageView) rootView.findViewById(R.id.play_share_save_iv);
            shareSave.setOnClickListener(this);
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
        boolean isCustomPlatform=false;
        boolean isUseClientToShare=false;
        switch (v.getId()) {
            case R.id.play_share_send_weixin_iv:
                platform = ShareSDK.getPlatform("Wechat");
                if (platform.isClientValid()) {
                    isCustomPlatform = platform instanceof CustomPlatform;
                    isUseClientToShare = isUseClientToShare(platform);
                    if (silent || isCustomPlatform || isUseClientToShare) {
                        shareSilently(platform);
                    }
                } else {
                    toast("ssdk_wechat_client_inavailable");
                }
                break;
            case R.id.play_share_friend_circle_iv:
                platform = ShareSDK.getPlatform("WechatMoments");
                if (platform.isClientValid()) {
                    isCustomPlatform = platform instanceof CustomPlatform;
                    isUseClientToShare = isUseClientToShare(platform);
                    if (silent || isCustomPlatform || isUseClientToShare) {
                        shareSilently(platform);
                    }
                } else {
                    toast("ssdk_wechat_client_inavailable");
                }
                break;
            case R.id.play_share_weibo_iv:
                platform = ShareSDK.getPlatform("SinaWeibo");
                if (platform.isClientValid()) {
                    isCustomPlatform = platform instanceof CustomPlatform;
                    isUseClientToShare = isUseClientToShare(platform);
                    if (silent || isCustomPlatform || isUseClientToShare) {
                        shareSilently(platform);
                    }
                }else {
                    toast("ssdk_weibo_client_inavailable");
                }
                break;
            // 如果是QQ好友分享
            case R.id.play_share_send_qq_iv:
                platform = ShareSDK.getPlatform("QQ");
                if (platform.isClientValid()) {
                    isCustomPlatform = platform instanceof CustomPlatform;
                    isUseClientToShare = isUseClientToShare(platform);
                    if (silent || isCustomPlatform || isUseClientToShare) {
                        shareSilently(platform);
                    }
                } else {
                    toast("ssdk_qq_client_inavailable");
                }
                break;
            // 如果是QQ空间分享
            case R.id.play_share_qzone_iv:
                    platform = ShareSDK.getPlatform("QZone");
                if (platform.isClientValid()) {
                    isCustomPlatform = platform instanceof CustomPlatform;
                    isUseClientToShare = isUseClientToShare(platform);
                    if (silent || isCustomPlatform || isUseClientToShare) {
                        shareSilently(platform);
                    }
                } else {
                    toast("ssdk_qq_client_inavailable");
                }
                break;
            case R.id.play_share_tip_off_iv: {
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
        }
    }

    /**
     * 分享
     * @param platform
     */
    final void shareSilently(Platform platform) {
        // 格式化数据
        formateShareData(platform);
        Platform.ShareParams sp = shareDataToShareParams(platform);
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
     * 判断指定平台是否只能使用客户端分享
     * @param platform
     * @return
     */
    final boolean isUseClientToShare(Platform platform) {
        String name = platform.getName();
        if ("Wechat".equals(name) || "WechatMoments".equals(name)
                || "WechatFavorite".equals(name)
                || "QQ".equals(name) ||  "QZone".equals(name)
                ) {
            return true;
        } else if ("SinaWeibo".equals(name)) {
            if ("true".equals(platform.getDevinfo("ShareByAppClient"))) {

                Intent test = new Intent(Intent.ACTION_SEND);
                test.setPackage("com.sina.weibo");
                test.setType("image/*");
                ResolveInfo ri = getActivity().getPackageManager().resolveActivity(test, 0);
                if(ri == null) {
                    test = new Intent(Intent.ACTION_SEND);
                    test.setPackage("com.sina.weibog3");
                    test.setType("image/*");
                    ri = getActivity().getPackageManager().resolveActivity(test, 0);
                }
                return (ri != null);
            }
        }

        return false;
    }

    /**
     * 格式化分享数据
     * @param plat
     * @return
     */
    final void formateShareData(Platform plat) {
        String name = plat.getName();
        boolean isWechat = "WechatFavorite".equals(name) || "Wechat".equals(name) || "WechatMoments".equals(name);
        if (!shareParamsMap.containsKey("shareType")) {
            int shareType = Platform.SHARE_TEXT;
            String imagePath = String.valueOf(shareParamsMap.get("imagePath"));
            if (imagePath != null && (new File(imagePath)).exists()) {
                shareType = Platform.SHARE_IMAGE;
                if (imagePath.endsWith(".gif") && isWechat) {
                    shareType = Platform.SHARE_EMOJI;
                } else if (shareParamsMap.containsKey("url") && !TextUtils.isEmpty(shareParamsMap.get("url").toString())) {
                    shareType = Platform.SHARE_WEBPAGE;
                    if (shareParamsMap.containsKey("musicUrl") && !TextUtils.isEmpty(shareParamsMap.get("musicUrl").toString()) && isWechat) {
                        shareType = Platform.SHARE_MUSIC;
                    }
                }
            } else {
                Bitmap viewToShare = ResHelper.forceCast(shareParamsMap.get("viewToShare"));
                if (viewToShare != null && !viewToShare.isRecycled()) {
                    shareType = Platform.SHARE_IMAGE;
                    if (shareParamsMap.containsKey("url") && !TextUtils.isEmpty(shareParamsMap.get("url").toString())) {
                        shareType = Platform.SHARE_WEBPAGE;
                        if (shareParamsMap.containsKey("musicUrl") && !TextUtils.isEmpty(shareParamsMap.get("musicUrl").toString()) && isWechat) {
                            shareType = Platform.SHARE_MUSIC;
                        }
                    }
                } else {
                    Object imageUrl = shareParamsMap.get("imageUrl");
                    if (imageUrl != null && !TextUtils.isEmpty(String.valueOf(imageUrl))) {
                        shareType = Platform.SHARE_IMAGE;
                        if (String.valueOf(imageUrl).endsWith(".gif") && isWechat) {
                            shareType = Platform.SHARE_EMOJI;
                        } else if (shareParamsMap.containsKey("url") && !TextUtils.isEmpty(shareParamsMap.get("url").toString())) {
                            shareType = Platform.SHARE_WEBPAGE;
                            if (shareParamsMap.containsKey("musicUrl") && !TextUtils.isEmpty(shareParamsMap.get("musicUrl").toString()) && isWechat) {
                                shareType = Platform.SHARE_MUSIC;
                            }
                        }
                    }
                }
            }
            shareParamsMap.put("shareType", shareType);
        }
    }

    /**
     * 参数设定
     * @param plat
     * @return
     */
    final Platform.ShareParams shareDataToShareParams(Platform plat) {
        if (plat == null || shareParamsMap == null) {
            toast("ssdk_oks_share_failed");
            return null;
        }

        try {
            String imagePath = ResHelper.forceCast(shareParamsMap.get("imagePath"));
            Bitmap viewToShare = ResHelper.forceCast(shareParamsMap.get("viewToShare"));
            if (TextUtils.isEmpty(imagePath) && viewToShare != null && !viewToShare.isRecycled()) {
                String path = ResHelper.getCachePath(getActivity(), "screenshot");
                File ss = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
                FileOutputStream fos = new FileOutputStream(ss);
                viewToShare.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                fos.flush();
                fos.close();
                shareParamsMap.put("imagePath", ss.getAbsolutePath());
            }
        } catch (Throwable t) {
            t.printStackTrace();
            toast("ssdk_oks_share_failed");
            return null;
        }

        return new Platform.ShareParams(shareParamsMap);
    }
}

package sdwxwx.com.widget;

import android.text.TextUtils;
import cn.sharesdk.framework.Platform;
import com.mob.tools.utils.ResHelper;

import java.util.HashMap;

/**
 * Created by 860115025 on 2018/05/23.
 */

public class OnekeyShare {
    private HashMap<String, Object> params;

    public OnekeyShare() {
        params = new HashMap<String, Object>();
        params.put("hiddenPlatforms", new HashMap<String, String>());
    }
    /**
     * title标题，在印象笔记、邮箱、信息、微信（包括好友、朋友圈和收藏）、
     * 易信（包括好友、朋友圈）、人人网和QQ空间使用，否则可以不提供
     */
    public void setTitle(String title) {
        params.put("title", title);
    }

    /** titleUrl是标题的网络链接，仅在人人网和QQ空间使用，否则可以不提供 */
    public void setTitleUrl(String titleUrl) {
        params.put("titleUrl", titleUrl);
    }

    /** text是分享文本，所有平台都需要这个字段 */
    public void setText(String text) {
        params.put("text", text);
    }

    /** 获取text字段的值 */
    public String getText() {
        return params.containsKey("text") ? String.valueOf(params.get("text")) : null;
    }

    /** imagePath是本地的图片路径，除Linked-In外的所有平台都支持这个字段 */
    public void setImagePath(String imagePath) {
        if(!TextUtils.isEmpty(imagePath)) {
            params.put("imagePath", imagePath);
        }
    }

    /** imageUrl是图片的网络路径，新浪微博、人人网、QQ空间和Linked-In支持此字段 */
    public void setImageUrl(String imageUrl) {
        if (!TextUtils.isEmpty(imageUrl)) {
            params.put("imageUrl", imageUrl);
        }
    }

    /** url在微信（包括好友、朋友圈收藏）和易信（包括好友和朋友圈）中使用，否则可以不提供 */
    public void setUrl(String url) {
        params.put("url", url);
    }

    /** site是分享此内容的网站名称，仅在QQ空间使用，否则可以不提供 */
    public void setSite(String site) {
        params.put("site", site);
    }

    /** siteUrl是分享此内容的网站地址，仅在QQ空间使用，否则可以不提供 */
    public void setSiteUrl(String siteUrl) {
        params.put("siteUrl", siteUrl);
    }

    /** 分享地纬度，新浪微博、腾讯微博和foursquare支持此字段 */
    public void setLatitude(float latitude) {
        params.put("latitude", latitude);
    }

    /** 分享地经度，新浪微博、腾讯微博和foursquare支持此字段 */
    public void setLongitude(float longitude) {
        params.put("longitude", longitude);
    }

    /** 设置编辑页的初始化选中平台 */
    public void setPlatform(String platform) {
        params.put("platform", platform);
    }

    /** 设置微信分享的音乐的地址 */
    public void setMusicUrl(String musicUrl) {
        params.put("musicUrl", musicUrl);
    }


    /** 设置一个总开关，用于在分享前若需要授权，则禁用sso功能 */
    public void disableSSOWhenAuthorize() {
        params.put("disableSSO", true);
    }

    /** 调用layout模板 */
    public void setLayoutId(int layoutId) {
        params.put("layoutId", layoutId);
    }

    /** 设置视频网络地址 */
    public void setVideoUrl(String url,int videoId,int type,int pos) {
        params.put("videoUrl", url);
        params.put("videoId", videoId);
        params.put("type", type);
        params.put("pos", pos);
        params.put("shareType", Platform.SHARE_VIDEO);
    }

    /** 设置编辑页面的显示模式为Dialog模式 */
    @Deprecated
    public void setDialogMode() {
        params.put("dialogMode", true);
    }

    /** 添加一个隐藏的platform */
    public void addHiddenPlatform(String platform) {
        HashMap<String, String> hiddenPlatforms = ResHelper.forceCast(params.get("hiddenPlatforms"));
        hiddenPlatforms.put(platform, platform);
    }

    /**
     * 设置分享类型 分享图片 分享视频 分享网页 分享应用等
     * @param shareType
     */
    public void setShareType(int shareType) {
        params.put("shareType", shareType);
    }

    public HashMap<String, Object> getParams() {
        return params;
    }
}

package com.fcn.park.base.component;

import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.fcn.park.R;
import com.fcn.park.base.http.ApiService;
import com.fcn.park.base.utils.DateUtils;
import com.fcn.park.base.utils.GlideCircleTransform;
import com.fcn.park.base.utils.GlideRoundTransform;

/**
 * 类描述：
 */

public class BindingComponent {

    /**
     * 显示图片
     *
     * @param image
     * @param url
     */
    @BindingAdapter({"imageUrl"})
    public static void setLoadImage(ImageView image, String url) {
        if (image == null) return;
        if (url != null && !url.startsWith("/"))
            url = ApiService.IMAGE_BASE + url;
        Log.d("image", "图片地址 ---- >>> " + url);
        Glide.with(image.getContext())
                .load(url)
                .error(R.mipmap.ic_launcher)
                .into(image);

    }

    /**
     * 显示图片
     *
     * @param image
     * @param url
     */
    @BindingAdapter({"imageTestUrl"})
    public static void setLoadTestImage(ImageView image, String url) {
        if (image == null) return;
        if (url != null && !url.startsWith("/"))
            url = ApiService.BASE_URL + url;
        Log.d("image", "图片地址 ---- >>> " + url);
        Glide.with(image.getContext())
                .load(url)
                .error(R.mipmap.ic_launcher)
                .into(image);

    }

    /**
     * 显示图片
     *
     * @param image
     * @param url
     */
    @BindingAdapter({"imageVideoUrl"})
    public static void setLoadVideoImage(ImageView image, String url) {
        if (image == null) return;
        Glide.with(image.getContext())
                .load(url)
                .error(R.mipmap.ic_launcher)
                .into(image);

    }

    /**
     * 文本拼接
     *
     * @param textView
     * @param appendText
     * @param text
     */
    @BindingAdapter({"appendText", "text"})
    public static void setAppendText(TextView textView, String appendText, String text) {
        if (textView == null) return;
        if (TextUtils.isEmpty(text)) {
            text = "";
        }
        String newText = appendText + text;
        textView.setText(newText);
    }

    /**
     * 设置圆角的图片
     *
     * @param image
     * @param url
     * @param radius
     */
    @BindingAdapter({"imageRoundUrl", "imageRadius"})
    public static void setLoadRoundImage(ImageView image, String url, int radius) {
        if (image == null) return;
        if (url != null && !url.startsWith("/"))
            url = ApiService.IMAGE_BASE + url;
        Log.d("image", "图片地址 ---- >>> " + url);
        Glide.with(image.getContext())
                .load(url)
                .error(R.mipmap.ic_launcher)
                .bitmapTransform(new GlideRoundTransform(image.getContext(), radius))
                .into(image);
    }

    /**
     * 设置圆形的图片
     *
     * @param image
     * @param url
     */
    @BindingAdapter({"imageCircleUrl", "errorImage"})
    public static void setLoadCircleImage(ImageView image, String url, Drawable err) {
        if (image == null) return;
        if (url != null && !url.startsWith("/"))
            url = ApiService.IMAGE_BASE + url;
        Log.d("image", "图片地址 ---- >>> " + url);
        Glide.with(image.getContext())
                .load(url)
                .error(err)
                .bitmapTransform(new GlideCircleTransform(image.getContext()))
                .into(image);
    }

    /**
     * 设置圆形的图片
     *
     * @param image
     * @param url
     */
    @BindingAdapter({"imageCircleUrl"})
    public static void setLoadCircleImage(ImageView image, String url) {
        setLoadCircleImage(image, url, ContextCompat.getDrawable(image.getContext(), R.mipmap.ic_launcher));
    }

    /**
     * 设置显示的时间
     *
     * @param tv
     * @param time
     */
    @BindingAdapter({"setTime"})
    public static void setTime(TextView tv, long time) {
        String timeStr = DateUtils.formatDate("yyyy-MM-dd", time);
        tv.setText(timeStr);
    }

    /**
     * 为文本加入下划线
     *
     * @param tv
     * @param text
     */
    @BindingAdapter({"underlineText"})
    public static void setUnderlineText(TextView tv, String text) {
        tv.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
        tv.setText(text);
    }

    /**
     * 为文本加入下划线
     *
     * @param tv
     * @param text
     */
    @BindingAdapter({"strikeThroughText"})
    public static void setStrikeThroughText(TextView tv, String text) {
        tv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
        tv.setText(text);
    }

    @BindingAdapter(value = "text")
    public static void setBindingText(TextView tv, String text) {
        if (tv == null || TextUtils.isEmpty(text)) return;
        text = text.replace("null", "");
        String tvText = tv.getText().toString();
        text = tvText + text;
        tv.setText(text);
    }

    @BindingAdapter({"toWebUrl"})
    public static void setTextToWebUrl(TextView tv, String url) {
        if (tv == null || TextUtils.isEmpty(url)) return;
        String tvText = tv.getText().toString();
        tvText = tvText + url;
        tv.setText(tvText);
        tv.setAutoLinkMask(Linkify.ALL);
        tv.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @BindingAdapter({"draBottomUrl"})
    public static void setButtonDraBottom(final RadioButton button, String url) {
        if (button == null || TextUtils.isEmpty(url)) return;
        if (!url.startsWith("/"))
            url = ApiService.IMAGE_BASE + url;
        Log.d("image", "图片地址 ---- >>> " + url);
        Glide.with(button.getContext()).load(url).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                button.setCompoundDrawables(null, null, null, new BitmapDrawable(button.getContext().getResources(), resource));
            }
        });
    }

    @BindingAdapter("bindImageSrc")
    public static void setImageSrc(ImageView iv, Drawable drawable) {
        iv.setImageDrawable(drawable);
    }

}

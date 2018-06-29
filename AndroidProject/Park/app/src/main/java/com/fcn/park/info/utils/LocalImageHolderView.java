package com.fcn.park.info.utils;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.bumptech.glide.Glide;
import com.fcn.park.R;
import com.fcn.park.base.http.ApiService;


/**
 * Created by liuyq on 2017/04/17.
 * 类描述：首页轮播图的图片加载帮助类
 */

public class LocalImageHolderView implements Holder<String> {

    private ImageView imageView;

    @Override
    public View createView(Context context) {
        imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        return imageView;
    }

    @Override
    public void UpdateUI(Context context, final int position, String data) {
        data = ApiService.IMAGE_BASE + data;
        Glide.with(context).load(data).error(R.mipmap.ic_launcher).into(imageView);
    }
}
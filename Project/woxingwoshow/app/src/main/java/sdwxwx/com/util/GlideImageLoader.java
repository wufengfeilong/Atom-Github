package sdwxwx.com.util;

import android.content.Context;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.youth.banner.loader.ImageLoader;
import sdwxwx.com.home.bean.BannerBean;

public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        BannerBean bean = (BannerBean) path;
        //Glide 加载图片简单用法
        Glide.with(context).load(bean.getCover_url()).into(imageView);
    }
}

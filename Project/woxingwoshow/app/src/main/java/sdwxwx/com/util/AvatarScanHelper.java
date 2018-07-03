package sdwxwx.com.util;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import sdwxwx.com.R;
import sdwxwx.com.widget.squareImageView;

/**
 * Created by 860115025 on 2018/06/19.
 */
public class AvatarScanHelper extends Dialog {
    /** 头像地址 */
    private String avatarUrl;
    /** context */
    private Context mContext;
    /** 头像控件 */
    private squareImageView header_img;

    /**
     * 构造函数
     * @param context
     * @param avatarUrl
     */
    public AvatarScanHelper(Context context, String avatarUrl) {
        super(context, R.style.CustomDialog_fill);
        this.mContext = context;
        this.avatarUrl = avatarUrl;
        initImageView(avatarUrl);
        this.show();
    }

    /**
     * 直接使用imageview展示头像图片
     * @param avatarUrl
     */
    private void initImageView(String avatarUrl) {
        //重点在于用setContentView()加载自定义布局
        setContentView(R.layout.image_full_screen);
        header_img = findViewById(R.id.big_head_img);
        // 加载图片
        RequestOptions options = new RequestOptions().error(R.drawable.default_header_image);
        Glide.with(mContext).load(avatarUrl).apply(options).into(header_img);
        // 点击任意位置，头像消失
        findViewById(R.id.header_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        setParams();
    }

    /**
     * 设置对话框的宽高适应全屏
     */
    private void setParams() {
        ViewGroup.LayoutParams lay = this.getWindow().getAttributes();
        DisplayMetrics dm = new DisplayMetrics();
        this.getWindow().getWindowManager().getDefaultDisplay().getMetrics(dm);
        Rect rect = new Rect();
        View view = getWindow().getDecorView();
        view.getWindowVisibleDisplayFrame(rect);
        lay.height = dm.heightPixels - rect.top;
        lay.width = dm.widthPixels;
    }
}

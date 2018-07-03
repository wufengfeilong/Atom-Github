package sdwxwx.com.util;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import sdwxwx.com.R;
import sdwxwx.com.bean.ShareBean;

/**
 * create by 860115039
 * date      2018/5/21
 * time      10:44
 */
public class DialogUtil {
    static Dialog mBtmDialog;
//    ShareBean mShareBean;
    public static void showShareDialog(final Context context, ShareBean shareBean) {
        mBtmDialog = new Dialog(context, R.style.BottomDialog);
        View view = LayoutInflater.from(context).inflate(R.layout.play_video_share, null);
        ImageView weixinShare = view.findViewById(R.id.play_share_send_weixin_iv);
        weixinShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "发送给微信朋友", Toast.LENGTH_SHORT).show();
            }
        });

        ImageView friendCircleShare = view.findViewById(R.id.play_share_friend_circle_iv);
        friendCircleShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "分享到朋友圈", Toast.LENGTH_SHORT).show();
            }
        });

        ImageView qqShare = view.findViewById(R.id.play_share_send_qq_iv);
        qqShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "发送给QQ好友", Toast.LENGTH_SHORT).show();
            }
        });

        ImageView weiboShare = view.findViewById(R.id.play_share_weibo_iv);
        weiboShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "分享给微博", Toast.LENGTH_SHORT).show();
            }
        });

        ImageView qZoneShare = view.findViewById(R.id.play_share_qzone_iv);
        qZoneShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "分享给QQ空间", Toast.LENGTH_SHORT).show();
            }
        });

        ImageView tipOffShare = view.findViewById(R.id.play_share_tip_off_iv);
        tipOffShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "举报", Toast.LENGTH_SHORT).show();
                Dialog mBtmDialog = new Dialog(context, R.style.BottomDialog);
                View view = LayoutInflater.from(context).inflate(R.layout.message_report_dialog, null);
                mBtmDialog.setContentView(view);
//              SpringView springView = view.findViewById(R.id.play_video_comment_sv);
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                layoutParams.width = context.getResources().getDisplayMetrics().widthPixels*4/5;
//              layoutParams.height = context.getResources().getDisplayMetrics().heightPixels*7/10;
                view.setLayoutParams(layoutParams);
                mBtmDialog.getWindow().setGravity(Gravity.CENTER);
                mBtmDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
                mBtmDialog.setCanceledOnTouchOutside(true);
                mBtmDialog.show();
            }
        });

        ImageView copyLinkShare = view.findViewById(R.id.play_share_copy_link_iv);
        copyLinkShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "复制链接", Toast.LENGTH_SHORT).show();
            }
        });

        ImageView saveShare = view.findViewById(R.id.play_share_save_iv);
        saveShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "保存", Toast.LENGTH_SHORT).show();
            }
        });

        TextView cancelTv = view.findViewById(R.id.play_share_cancel);
        cancelTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBtmDialog.dismiss();
            }
        });
        mBtmDialog.setContentView(view);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = context.getResources().getDisplayMetrics().widthPixels;
        view.setLayoutParams(layoutParams);
        mBtmDialog.getWindow().setGravity(Gravity.BOTTOM);
        mBtmDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        mBtmDialog.setCanceledOnTouchOutside(true);
        mBtmDialog.show();
    }

}

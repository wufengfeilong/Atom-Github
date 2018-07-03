package sdwxwx.com.me.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import sdwxwx.com.R;
import sdwxwx.com.adapter.BaseAdapter;
import sdwxwx.com.adapter.BaseHolder;
import sdwxwx.com.base.BaseCallback;
import sdwxwx.com.cons.Constant;
import sdwxwx.com.home.bean.RecommendUserBean;
import sdwxwx.com.login.activity.LoginActivity;
import sdwxwx.com.login.utils.LoginHelper;
import sdwxwx.com.message.model.FansHomeModel;
import sdwxwx.com.play.model.PlayVideoFragmentModel;
import sdwxwx.com.util.LoginUtil;

import java.util.List;

import static sdwxwx.com.cons.Constant.SP_FILE_NAME;
import static sdwxwx.com.cons.Constant.SP_LOGIN_TOKEN;

/**
 * Created by 860115025 on 2018/06/01.
 */

public class FindFriendsAdapter extends BaseAdapter<RecommendUserBean> {

    private Context context;
    private FindFriendsAdapter adapter;
    public FindFriendsAdapter(List<RecommendUserBean> list, Context context) {
        super(R.layout.item_message_friend_fans, list);
        this.context = context;
    }
    public void setAdapter(FindFriendsAdapter adapter) {
        this.adapter = adapter;
    }
    @Override
    protected void convert(final BaseHolder holder, final RecommendUserBean item) {
        // 头像
        RequestOptions options = new RequestOptions().error(R.drawable.default_header_image);
        Glide.with(context).load(item.getAvatar_url()).apply(options).into((ImageView) holder.getView(R.id.fans_avatar_url));
        // 判断显示加V图片
        if ("1".equals(item.getIs_certified())) {
            ((ImageView)holder.getView(R.id.fans_avatar_vip_logo)).setVisibility(View.VISIBLE);
        } else {
            ((ImageView)holder.getView(R.id.fans_avatar_vip_logo)).setVisibility(View.GONE);
        }
        // 设置昵称
        holder.setText(R.id.nickname, item.getNickname());
        if (TextUtils.isEmpty(item.getSignature())) {
            ((TextView)holder.getView(R.id.signature)).setVisibility(View.GONE);
        } else {
            // 设置签名
            holder.setText(R.id.signature, item.getSignature());
            ((TextView)holder.getView(R.id.signature)).setVisibility(View.VISIBLE);
        }
        // 如果是本人则不设置任何图片
        if (!LoginHelper.getInstance().getUserId().equals(String.valueOf(item.getId()))) {
            // 显示关注图片
            if ("1".equals(item.getIs_followed())) {
                holder.setImageResource(R.id.is_followed, R.drawable.already_follow);
            } else {
                holder.setImageResource(R.id.is_followed, R.drawable.message_attention);
            }
        } else {
            ((ImageView)holder.getView(R.id.is_followed)).setVisibility(View.GONE);
        }


        ((ImageView)holder.getView(R.id.is_followed)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences mSharedPreferences = mSharedPreferences = context.getSharedPreferences(SP_FILE_NAME, Context.MODE_PRIVATE);
                String loginToken = mSharedPreferences.getString(SP_LOGIN_TOKEN, null);
                if (loginToken != null) {
                    // 更新画面的关注状态
                    if ("1".equals(item.getIs_followed())) {
                        final AlertDialog.Builder normalDialog =
                                new AlertDialog.Builder(context);
                        normalDialog.setMessage("确定不再关注此人?");
                        normalDialog.setPositiveButton("确定",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // 调用清除好友接口
                                        FansHomeModel.cancelFollow(context, LoginUtil.getStrUserInfo(Constant.SP_MEMBER_ID, context), String.valueOf(item.getId()), new BaseCallback<String>() {
                                            @Override
                                            public void onSuccess(String data) {
                                                //取消关注成功的操作
                                                item.setIs_followed("0");
                                                adapter.notifyDataSetChanged();
                                            }

                                            @Override
                                            public void onFail(String msg) {
                                            }
                                        });
                                    }
                                });
                        normalDialog.setNegativeButton("取消",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // 不做任何处理
                                    }
                                });
                        // 显示
                        normalDialog.show();
                    } else {
                        PlayVideoFragmentModel.followUser(context, LoginUtil.getStrUserInfo(Constant.SP_MEMBER_ID, context), String.valueOf(item.getId()), new BaseCallback<String>() {
                            @Override
                            public void onSuccess(String data) {
                                //关注成功后的操作
                                item.setIs_followed("1");
                                adapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onFail(String msg) {
                            }
                        });
                    }
                } else {
                    Intent intent = new Intent();
                    intent.setClass(context, LoginActivity.class);
                    context.startActivity(intent);
                }
            }
        });
    }
}
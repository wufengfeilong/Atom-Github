package sdwxwx.com.message.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.TextAppearanceSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import sdwxwx.com.R;
import sdwxwx.com.base.BaseCallback;
import sdwxwx.com.cons.Constant;
import sdwxwx.com.home.bean.PlayVideoBean;
import sdwxwx.com.home.model.HomeCategoryVideoModel;
import sdwxwx.com.login.utils.LoginHelper;
import sdwxwx.com.message.activity.FansHomeActivity;
import sdwxwx.com.message.bean.MessageListBean;
import sdwxwx.com.message.fragment.MessageListFragment;
import sdwxwx.com.message.model.FansHomeModel;
import sdwxwx.com.play.activity.PlayVideoActivity;
import sdwxwx.com.play.model.PlayVideoFragmentModel;


/**
 * Created by 860117073 on 2018/5/11.
 * 我的列表用多布局adapter
 */

public class MessageListAdapter extends BaseAdapter {
    private Context context;
    private MessageListFragment fragment;

    private List<MessageListBean.Result> dataList;

    private LayoutInflater inflater;

    //布局数量
    private final int TYPE_COUNT = 4;

    //喜欢了你的作品
    private final int TYPE_ONE = 1;

    //关注了你
    private final int TYPE_TWO = 2;

    //我行我秀小助手
    private final int TYPE_ZERO = 0;
    // @通知
    private final int TYPE_THREE = 3;

    //加载布局类型
    private int currentType;

    public MessageListAdapter(Context context, MessageListFragment fragment, List<MessageListBean.Result> dataList) {
        this.context = context;
        this.fragment = fragment;
        this.dataList = dataList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int i) {
        return dataList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        final MessageListBean.Result result = dataList.get(position);
        //根据currentType来加载不同的布局,并复用convertview
        currentType = getItemViewType(position);

        //加载喜欢了你的作品布局
        if (currentType == TYPE_ONE) {
            ViewHolderOne viewHolderOne;
            //首先判断convertview==null
            if (convertView == null) {
                viewHolderOne = new ViewHolderOne();
                convertView = inflater.inflate(R.layout.message_like_item, null);
                viewHolderOne.headPic = (ImageView) convertView.findViewById(R.id.like_head);
                viewHolderOne.message_content = (TextView) convertView.findViewById(R.id.like_content);
                viewHolderOne.passTime = (TextView) convertView.findViewById(R.id.like_time);
                viewHolderOne.actionPic = (ImageView) convertView.findViewById(R.id.like_action);
                convertView.setTag(viewHolderOne);
            } else {
                viewHolderOne = (ViewHolderOne) convertView.getTag();
            }
            // 用户头像
            RequestOptions options = new RequestOptions().error(R.drawable.default_header_image);
            Glide.with(context).load(result.getAvatar_url()).apply(options).into(viewHolderOne.headPic);

            // 消息内容格式拼接处理
            SpannableString styledText =
                    new SpannableString(result.getNickname() + " 喜欢了你的作品");
            styledText.setSpan(new TextAppearanceSpan(context, R.style.userStyle),
                    0,
                    result.getNickname().length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            styledText.setSpan(new TextAppearanceSpan(context, R.style.typeStyle),
                    result.getNickname().length(),
                    styledText.length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            viewHolderOne.message_content.setText(styledText, TextView.BufferType.SPANNABLE);

            // 通知时间
            viewHolderOne.passTime.setText(result.getCreate_time());
            // 点击头像跳转到相关的好友画面
            viewHolderOne.headPic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 跳转到好友画面
                    fragment.paramStartActivity(FansHomeActivity.class, result.getRelated_member_id());
                }
            });
            viewHolderOne.message_content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 跳转到好友画面
                    fragment.paramStartActivity(FansHomeActivity.class, result.getRelated_member_id());
                }
            });
            // 视频封面
            Glide.with(context).load(result.getCover_url()).into(viewHolderOne.actionPic);
            // 点击视频封面，则跳转到视频播放画面
            viewHolderOne.actionPic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 跳转到播放画面
                    final LoginHelper helper = LoginHelper.getInstance();
                    HomeCategoryVideoModel.getVideoList(
                            helper.getUserBean().getId()
                            , "0", "0", "0", helper.getUserBean().getId(), "0"
                            , new BaseCallback<List<PlayVideoBean>>() {
                                @Override
                                public void onSuccess(List<PlayVideoBean> data) {
                                    helper.setOwnerList(data);
                                    int pos = 0;
                                    for (int i = 0; i < data.size(); i++) {
                                        if (Integer.valueOf(data.get(i).getKsyun_id()) == result.getKsyun_id()) {
                                            pos = i;
                                        }
                                    }
                                    Intent i = new Intent(context, PlayVideoActivity.class);
                                    i.putExtra(Constant.INTENT_PARAM, pos+"");
                                    i.putExtra(Constant.INTENT_PARAM_ONE, 3);
                                    context.startActivity(i);
                                }

                                @Override
                                public void onFail(String msg) {

                                }
                            });
                }
            });
            //加载关注了你或@你的布局
        } else if (currentType == TYPE_TWO || currentType == TYPE_THREE ) {
            ViewHolderTwo viewHolderTwo;
            if (convertView == null) {
                viewHolderTwo = new ViewHolderTwo();
                convertView = inflater.inflate(R.layout.message_attention_item, null);
                viewHolderTwo.headPic = (ImageView) convertView.findViewById(R.id.attention_head);
                viewHolderTwo.passTime = (TextView) convertView.findViewById(R.id.attention_time);
                viewHolderTwo.is_follwed = (ImageView) convertView.findViewById(R.id.is_followed);
                viewHolderTwo.message_content = (TextView) convertView.findViewById(R.id.message_content);
                convertView.setTag(viewHolderTwo);
            } else {
                viewHolderTwo = (ViewHolderTwo) convertView.getTag();
            }
            viewHolderTwo.passTime.setText(result.getCreate_time());

            String content = "";
            if ("2".equals(result.getType())) {
                content = "关注了你";
            } else {
                content = "@了你";
            }
            // 消息内容格式拼接处理
            SpannableString styledText =
                    new SpannableString(result.getNickname() + " " +  content);
            styledText.setSpan(new TextAppearanceSpan(context, R.style.userStyle),
                    0,
                    result.getNickname().length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            styledText.setSpan(new TextAppearanceSpan(context, R.style.typeStyle),
                    result.getNickname().length(),
                    styledText.length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            viewHolderTwo.message_content.setText(styledText, TextView.BufferType.SPANNABLE);

            // 显示头像
            RequestOptions options = new RequestOptions().error(R.drawable.default_header_image);
            Glide.with(context).load(result.getAvatar_url()).apply(options).into(viewHolderTwo.headPic);
            // 给头像添加点击事件
            viewHolderTwo.headPic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 跳转到好友画面
                    fragment.paramStartActivity(FansHomeActivity.class, result.getRelated_member_id());
                }
            });
            viewHolderTwo.message_content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 跳转到好有画面
                    fragment.paramStartActivity(FansHomeActivity.class, result.getRelated_member_id());
                }
            });
            // 设置关注显示的图片
            if (result.is_followed()) {
                viewHolderTwo.is_follwed.setImageResource(R.drawable.mutual_follow);
            } else {
                viewHolderTwo.is_follwed.setImageResource(R.drawable.message_attention);
            }
            viewHolderTwo.is_follwed.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (result.is_followed()) {
                        final AlertDialog.Builder normalDialog =
                                new AlertDialog.Builder(context);
                        normalDialog.setMessage("确定不再关注此人?");
                        normalDialog.setPositiveButton("确定",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // 调用接口 更新关注状态
                                        FansHomeModel.cancelFollow(context,result.getMember_id(),result.getRelated_member_id(), new BaseCallback<String>() {
                                            @Override
                                            public void onSuccess(String data) {
                                                result.setIs_followed(false);
                                                notifyDataSetChanged();
                                            }
                                            @Override
                                            public void onFail(String msg) {
                                                fragment.showToast("取消关注失败");
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
                        PlayVideoFragmentModel.followUser(context,result.getMember_id(),result.getRelated_member_id(), new BaseCallback<String>() {
                            @Override
                            public void onSuccess(String data) {
                                result.setIs_followed(true);
                                notifyDataSetChanged();
                            }
                            @Override
                            public void onFail(String msg) {
                                fragment.showToast("关注失败");
                            }
                        });
                    }
                    notifyDataSetChanged();
                }
            });
        } else if (currentType == TYPE_ZERO) {
            //加载小助手布局
            ViewHolderZero viewHolderZero;
            if (convertView == null) {
                viewHolderZero = new ViewHolderZero();
                convertView = inflater.inflate(R.layout.message_assistant_item, null);
                viewHolderZero.passTime = (TextView) convertView.findViewById(R.id.assistant_time);
                viewHolderZero.assistantContent = (TextView) convertView.findViewById(R.id.assistant_content);
                viewHolderZero.headPic = (ImageView) convertView.findViewById(R.id.assistant_head);
                viewHolderZero.userName = (TextView) convertView.findViewById(R.id.assistant_name);
                convertView.setTag(viewHolderZero);
            } else {
                viewHolderZero = (ViewHolderZero) convertView.getTag();
            }
            viewHolderZero.passTime.setText(result.getCreate_time());
            viewHolderZero.assistantContent.setText(result.getContent());
            viewHolderZero.userName.setText(result.getTitle());
//            viewHolderZero.headPic.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(context, FansHomeActivity.class);
//                    context.startActivity(intent);
//                }
//            });
//            viewHolderZero.userName.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(context, FansHomeActivity.class);
//                    context.startActivity(intent);
//                }
//            });
        }
        return convertView;
    }
    class ViewHolderZero {
        public ImageView headPic;
        public TextView userName;
        public TextView passTime;
        public TextView assistantContent;
    }

    /**
     * 点赞视频通知
     */
    class ViewHolderOne {
        /** 头像 */
        public ImageView headPic;
        /** 用户名称 + 消息内容 */
        public TextView message_content;
        /** 发布时间 */
        public TextView passTime;
        /** 喜欢的视频封面 */
        public ImageView actionPic;
    }

    /**
     * 表示关注通知
     */
    class ViewHolderTwo {
        /** 头像 */
        public ImageView headPic;
        /** 发布时间 */
        public TextView passTime;
        /** 相互关系 */
        public ImageView is_follwed;
        /** 用户名称 + 消息内容 */
        public TextView message_content;
    }

    @Override
    public int getViewTypeCount() {
        return TYPE_COUNT;
    }

    @Override
    public int getItemViewType(int position) {
        MessageListBean.Result result = dataList.get(position);
        String type = result.getType();
        int layoutType = Integer.parseInt(type);
        switch (layoutType) {
            case TYPE_ONE:
                return TYPE_ONE;

            case TYPE_TWO:
                return TYPE_TWO;
            case TYPE_THREE:
                return TYPE_THREE;

            case TYPE_ZERO:
                return TYPE_ZERO;
            default:
                return -1;
        }
    }
}

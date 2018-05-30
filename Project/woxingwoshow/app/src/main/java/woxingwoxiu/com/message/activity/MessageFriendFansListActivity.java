package woxingwoxiu.com.message.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import woxingwoxiu.com.BR;
import woxingwoxiu.com.R;
import woxingwoxiu.com.adapter.BaseAdapter;
import woxingwoxiu.com.adapter.BaseHolder;
import woxingwoxiu.com.adapter.SimpleBindingAdapter;
import woxingwoxiu.com.base.BaseActivity;
import woxingwoxiu.com.databinding.MessageFriendFansListActivityBinding;
import woxingwoxiu.com.message.bean.MessageFriendFansListBean;
import woxingwoxiu.com.message.bean.MessageMyTeamBean;
import woxingwoxiu.com.message.contract.MessageFriendFansListContract;
import woxingwoxiu.com.message.presenter.MessageFriendFansListPresenter;
import woxingwoxiu.com.util.DataCleanUtils;
import woxingwoxiu.com.util.ImageUtil;

/**
 *
 * 类描述：好友粉丝列表
 */
public class MessageFriendFansListActivity
        extends BaseActivity<MessageFriendFansListActivityBinding,MessageFriendFansListPresenter>
        implements MessageFriendFansListContract.View, BaseAdapter.OnItemClickListener{
    /** 定义适配器 */
    FansAdapter fansAdapter = null;
    /** 粉丝列表 */
    List<MessageFriendFansListBean> fansList = new ArrayList<MessageFriendFansListBean>();
    @Override
    protected MessageFriendFansListPresenter createPresenter() {
        return new MessageFriendFansListPresenter();
    }

    @Override
    protected void initViews() {
        // 绑定Presenter
        mDataBinding.setPresenter(mPresenter);
        initRecyclerView();
        // 加载数据
        mPresenter.loadListData();
    }
    @Override
    public void bindListData(List<MessageFriendFansListBean> beanList) {
        fansList.addAll(beanList);
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                fansAdapter.notifyDataSetChanged();
            }
        });
    }
    @Override
    public void initRecyclerView() {
        mDataBinding.fansList.setLayoutManager(new LinearLayoutManager(getContext()));
        // 定义适配器并绑定点击事件
        fansAdapter = new FansAdapter(fansList);
        fansAdapter.setOnItemClickListener(this);
        // 绑定适配器
        mDataBinding.fansList.setAdapter(fansAdapter);
    }
    @Override
    public RecyclerView getRecyclerView() {
        return mDataBinding.fansList;
    }
    @Override
    protected int getLayoutId() {
        return R.layout.message_friend_fans_list_activity;
    }
    // 定义元素的点击事件
    @Override
    public void onItemClick(View view, int postion) {
        Intent intent = new Intent();
        // 跳转到好友资料画面
        intent.setClass(getContext(), MessageFriendInformationActivity.class);
        // 传递会员ID
        intent.putExtra("member_id", fansList.get(postion).getId());
        startActivity(intent);
    }

    public class FansAdapter extends BaseAdapter<MessageFriendFansListBean> {
        public FansAdapter(List<MessageFriendFansListBean> list) {
            super(R.layout.item_message_friend_fans_list_activity, list);
        }
        @Override
        protected void convert(final BaseHolder holder, final MessageFriendFansListBean item) {
            // 头像
            Glide.with(getContext()).load(item.getAvatar_url()).into((ImageView) holder.getView(R.id.avatar_url));
            // 设置昵称
            holder.setText(R.id.nickname, item.getNickname());
            // 设置签名
            holder.setText(R.id.signature, item.getSignature());
            // 如果双方已经互相关注  根据关注状态显示相关图片
            if (item.isIs_followed()) {
                holder.setImageResource(R.id.is_followed, R.drawable.already_follow);
            } else {
                holder.setImageResource(R.id.is_followed, R.drawable.message_attention);
            }
            ((ImageView)holder.getView(R.id.is_followed)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 更新画面的关注状态
                    if (item.isIs_followed()) {
                        final AlertDialog.Builder normalDialog =
                                new AlertDialog.Builder(getContext());
                        normalDialog.setMessage("确定不再关注此人?");
                        normalDialog.setPositiveButton("确定",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // TODO 调用接口 更新关注状态
                                        item.setIs_followed(false);
                                        fansAdapter.notifyDataSetChanged();
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
                        // TODO 调用接口 更新关注状态
                        item.setIs_followed(true);
                    }
                    fansAdapter.notifyDataSetChanged();
                }
            });
        }
    }
}

package sdwxwx.com.message.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import sdwxwx.com.R;
import sdwxwx.com.adapter.BaseAdapter;
import sdwxwx.com.adapter.BaseHolder;
import sdwxwx.com.base.BaseActivity;
import sdwxwx.com.base.BaseCallback;
import sdwxwx.com.cons.Constant;
import sdwxwx.com.databinding.MessageFriendFansListActivityBinding;
import sdwxwx.com.home.bean.RecommendUserBean;
import sdwxwx.com.login.utils.LoginHelper;
import sdwxwx.com.message.contract.MessageFriendFansListContract;
import sdwxwx.com.message.model.FansHomeModel;
import sdwxwx.com.message.presenter.MessageFriendFansListPresenter;
import sdwxwx.com.play.model.PlayVideoFragmentModel;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * 类描述：好友粉丝、关注列表
 */
public class MessageFriendFansListActivity
        extends BaseActivity<MessageFriendFansListActivityBinding,MessageFriendFansListPresenter>
        implements MessageFriendFansListContract.View, BaseAdapter.OnItemClickListener{
    /** 定义适配器 */
    FansAdapter fansAdapter = null;
    /** 粉丝列表 */
    List<RecommendUserBean> mList = new ArrayList<RecommendUserBean>();
    // 是粉丝还是关注得人
    private String type = "1";
    // 传递的会员ID
    private String member_id;
    private String FansHeadUrl;
    RecyclerView recyclerView;
    int page = 1;
    @Override
    protected MessageFriendFansListPresenter createPresenter() {
        return new MessageFriendFansListPresenter();
    }

    @Override
    protected void initViews() {
        // 绑定Presenter
        mDataBinding.setPresenter(mPresenter);
        initRecyclerView();
        // 获取点击的是粉丝还是关注
        type = getIntent().getStringExtra("type");
        // 会员ID
        member_id = getIntent().getStringExtra("member_id");
        // 如果没有传递会员ID，则说明是本人
        if (TextUtils.isEmpty(member_id)) {
            member_id = LoginHelper.getInstance().getUserId();
        }
        TextView textView = this.findViewById(R.id.fans_or_attention);
        if ("2".equals(type)) {
            textView.setText("关注的人");
        } else {
            textView.setText("粉丝");
        }
        // 加载数据
        mPresenter.loadListData(type, member_id,Constant.REQUEST_PAGE);

    }

    @Override
    public void bindListData(List<RecommendUserBean> beanList) {
        // 如果记载更多没有数据
        if (page > 1 && (beanList == null || beanList.size() == 0)) {
            showToast(Constant.NO_MORE_MSG);
        }
        mList.addAll(beanList);
        // 如果还没有粉丝或者关注者
        if (mList == null || mList.size() == 0) {
            mDataBinding.fansListSpringView.setVisibility(View.GONE);
            mDataBinding.noUserMessage.setVisibility(View.VISIBLE);
            // 如果点击的是关注
            if ("2".equals(type)) {
                mDataBinding.noUserMessage.setText("TA还没有关注的人");
            } else {
                mDataBinding.noUserMessage.setText("TA还没有粉丝");
            }
        } else {
            mDataBinding.fansListSpringView.setVisibility(View.VISIBLE);
            mDataBinding.noUserMessage.setVisibility(View.GONE);
        }
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                fansAdapter.notifyDataSetChanged();
                mDataBinding.fansListSpringView.onFinishFreshAndLoad();
            }
        });

    }
    @Override
    public void initRecyclerView() {
        mDataBinding.fansListSpringView.setListener(this);
        mDataBinding.fansListSpringView.setHeader(new DefaultHeader(getContext()));
        mDataBinding.fansListSpringView.setFooter(new DefaultFooter(getContext()));

        mDataBinding.fansList.setLayoutManager(new LinearLayoutManager(getContext()));
        // 定义适配器并绑定点击事件
        fansAdapter = new FansAdapter(mList);
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
        paramStartActivity(FansHomeActivity.class,String.valueOf(mList.get(postion).getId()));
    }

    @Override
    public void onRefresh() {
        mList.clear();
        page = 1;
        mPresenter.loadListData(type,member_id,Constant.REQUEST_PAGE);
    }

    @Override
    public void onLoadmore() {
        page = page + 1;
        mPresenter.loadListData(type,member_id,String.valueOf(page));
    }


    public class FansAdapter extends BaseAdapter<RecommendUserBean> {
        public FansAdapter(List<RecommendUserBean> list) {
            super(R.layout.item_message_friend_fans, list);
        }
        @Override
        protected void convert(final BaseHolder holder, final RecommendUserBean item) {
            // 头像
            RequestOptions options = new RequestOptions().error(R.drawable.default_header_image);
            Glide.with(getContext()).load(item.getAvatar_url()).apply(options).into((ImageView) holder.getView(R.id.fans_avatar_url));
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
            // 如果双方已经互相关注  根据关注状态显示相关图片  注：如果是本人，则不显示图片
            if (!LoginHelper.getInstance().getUserId().equals(String.valueOf(item.getId()))) {
                // 如果双方互相关注了，显示互关注，否则显示关注
                if ("1".equals(item.getIs_followed())) {
                    holder.setImageResource(R.id.is_followed, R.drawable.mutual_follow);
                } else {
                    // 如果是粉丝列表中的数据
                    if ("1".equals(type) || ("2".equals(type) && ("0".equals(item.getStatus()) || "1".equals(item.getStatus())))) {
                        holder.setImageResource(R.id.is_followed, R.drawable.message_attention);
                    } else {
                        holder.setImageResource(R.id.is_followed, R.drawable.already_follow);
                    }
                }
                ((ImageView)holder.getView(R.id.is_followed)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 更新画面的关注状态
                        if (("1".equals(type) && "1".equals(item.getIs_followed())) || ("2".equals(type) && !"0".equals(item.getStatus())&& !"1".equals(item.getStatus()))) {
                            final AlertDialog.Builder normalDialog =
                                    new AlertDialog.Builder(getContext());
                            normalDialog.setMessage("确定不再关注此人?");
                            normalDialog.setPositiveButton("确定",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            // TODO 调用接口 更新关注状态
                                            FansHomeModel.cancelFollow(getContext(),LoginHelper.getInstance().getUserId(),String.valueOf(item.getId()), new BaseCallback<String>() {
                                                @Override
                                                public void onSuccess(String data) {
                                                    if ("1".equals(item.getIs_followed())) {
                                                        item.setStatus("1");
                                                    } else {
                                                        item.setStatus("0");
                                                    }
                                                    item.setIs_followed("0");
                                                    fansAdapter.notifyDataSetChanged();
                                                }
                                                @Override
                                                public void onFail(String msg) {
                                                    showToast("取消关注失败");
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
                            PlayVideoFragmentModel.followUser(getContext(),LoginHelper.getInstance().getUserId(),String.valueOf(item.getId()), new BaseCallback<String>() {
                                @Override
                                public void onSuccess(String data) {
                                    if ("1".equals(type) || "2".equals(type) && "1".equals(item.getStatus())) {
                                        item.setIs_followed("1");
                                    }
                                    item.setStatus("2");
                                    fansAdapter.notifyDataSetChanged();
                                }
                                @Override
                                public void onFail(String msg) {
                                    showToast("关注失败");
                                }
                            });
                        }
                        fansAdapter.notifyDataSetChanged();
                    }
                });
            } else {
                ((ImageView)holder.getView(R.id.is_followed)).setVisibility(View.GONE);
            }
        }
    }
}

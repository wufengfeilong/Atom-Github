package sdwxwx.com.message.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import sdwxwx.com.R;
import sdwxwx.com.adapter.BaseAdapter;
import sdwxwx.com.adapter.BaseHolder;
import sdwxwx.com.base.BaseFragment;
import sdwxwx.com.databinding.MessageMyTeamGradeFragmentBinding;
import sdwxwx.com.home.bean.RecommendUserBean;
import sdwxwx.com.message.activity.FansHomeActivity;
import sdwxwx.com.message.contract.MessageMyTeamFragmentContract;
import sdwxwx.com.message.presenter.MessageMyTeamFragmentPresenter;
import sdwxwx.com.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 类描述：我的团队一级二级列表
 */
public class MessageMyTeamFragment
        extends BaseFragment<MessageMyTeamGradeFragmentBinding, MessageMyTeamFragmentPresenter>
        implements MessageMyTeamFragmentContract.View, BaseAdapter.OnItemClickListener {
    /** 前一画面传递的会员ID */
    private String memberId;
    int type;
    TeamGrade mTeamGrade;
    List<RecommendUserBean> mList = new ArrayList<>();
    public static MessageMyTeamFragment newInstance(int type,String member_id) {

        Bundle args = new Bundle();
        args.putInt("type", type);
        args.putString("member_id", member_id);
        MessageMyTeamFragment fragment = new MessageMyTeamFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            type = getArguments().getInt("type");
        }
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.message_my_team_grade_fragment;
    }

    @Override
    protected MessageMyTeamFragmentPresenter createPresenter() {
        return new MessageMyTeamFragmentPresenter();
    }

    @Override
    protected void initViews() {
        memberId = getArguments().getString("member_id");
        mDataBinding.setPresenter(mPresenter);
        initRecyclerView();
        mPresenter.loadListData(type, mDataBinding);
    }
    @Override
    public void initRecyclerView() {
        mDataBinding.teamGradeTg.setLayoutManager(new LinearLayoutManager(mContext));
        mTeamGrade = new TeamGrade(mList);
        mDataBinding.teamGradeTg.setAdapter(mTeamGrade);
        mTeamGrade.setOnItemClickListener(this);


    }
    @Override
    public String getMemberId(){
        return memberId;
    }
    @Override
    public void bindListData(List<RecommendUserBean> beanList) {
        mList.addAll(beanList);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTeamGrade.notifyDataSetChanged();
            }
        });
    }

    /**
     * 点击列表中的数据则跳转到粉丝资料料画面
     * @param view
     * @param postion
     */
    @Override
    public void onItemClick(View view, int postion) {
        paramStartActivity(FansHomeActivity.class, String.valueOf(mList.get(postion).getId()));
    }

    public class TeamGrade extends BaseAdapter<RecommendUserBean> {
        public TeamGrade(List<RecommendUserBean> list) {
            super(R.layout.item_team_activity, list);
        }

        @Override
        protected void convert(BaseHolder holder, RecommendUserBean item) {
            // 头像
            RequestOptions options = new RequestOptions().error(R.drawable.default_header_image);
            Glide.with(mContext).load(item.getAvatar_url()).apply(options).into((ImageView) holder.getView(R.id.avatar_url));
            // 判断显示加V图片
            if ("1".equals(item.getIs_certified())) {
                ((ImageView)holder.getView(R.id.avatar_url_vip)).setVisibility(View.VISIBLE);
            } else {
                ((ImageView)holder.getView(R.id.avatar_url_vip)).setVisibility(View.GONE);
            }
            // 昵称
            holder.setText(R.id.team_grade_name, item.getNickname());
            // 注册时间
            if (StringUtil.isNotEmpty(item.getCreate_time()) && item.getCreate_time().length() >= 10) {
                holder.setText(R.id.team_reg_time, item.getCreate_time().substring(0, 10));
            } else {
                holder.setText(R.id.team_reg_time, item.getCreate_time());
            }
            // 推荐财富
            holder.setText(R.id.wealth_recommend, String.valueOf(item.getRecommend_wealth()));
            // 视频财富
            holder.setText(R.id.wealth_movie, String.valueOf(item.getVideo_wealth()));
        }
    }

    @Override
    public RecyclerView getRecyclerView() {
        return mDataBinding.teamGradeTg;
    }
}

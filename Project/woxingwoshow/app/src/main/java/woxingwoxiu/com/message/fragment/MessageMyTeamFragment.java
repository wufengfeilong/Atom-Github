package woxingwoxiu.com.message.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;

import java.util.ArrayList;
import java.util.List;

import woxingwoxiu.com.R;
import woxingwoxiu.com.adapter.BaseAdapter;
import woxingwoxiu.com.adapter.BaseHolder;
import woxingwoxiu.com.base.BaseFragment;
import woxingwoxiu.com.databinding.MessageMyTeamGradeFragmentBinding;
import woxingwoxiu.com.message.bean.MessageMyTeamBean;
import woxingwoxiu.com.message.contract.MessageMyTeamFragmentContract;
import woxingwoxiu.com.message.presenter.MessageMyTeamFragmentPresenter;

/**
 * 类描述：我的团队一级二级列表
 */
public class MessageMyTeamFragment
        extends BaseFragment<MessageMyTeamGradeFragmentBinding, MessageMyTeamFragmentPresenter>
        implements MessageMyTeamFragmentContract.View, BaseAdapter.OnItemClickListener {
    int type;
    TeamGrade mTeamGrade;
    List<MessageMyTeamBean> mList = new ArrayList<>();

    public static MessageMyTeamFragment newInstance(int type) {

        Bundle args = new Bundle();
        args.putInt("type", type);
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
        mDataBinding.setPresenter(mPresenter);
        initRecyclerView();
        mPresenter.loadListData();
    }
    @Override
    public void initRecyclerView() {
        mDataBinding.teamGradeSpringView.setListener(this);
        mDataBinding.teamGradeSpringView.setHeader(new DefaultHeader(mContext));
        mDataBinding.teamGradeSpringView.setFooter(new DefaultFooter(mContext));
        mDataBinding.teamGradeTg.setLayoutManager(new LinearLayoutManager(mContext));
        mTeamGrade = new TeamGrade(mList);
        mDataBinding.teamGradeTg.setAdapter(mTeamGrade);
        mTeamGrade.setOnItemClickListener(this);


    }

    @Override
    public void bindListData(List<MessageMyTeamBean> beanList) {
        mList.addAll(beanList);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTeamGrade.notifyDataSetChanged();
            }
        });
        mDataBinding.teamGradeSpringView.onFinishFreshAndLoad();
    }

    @Override
    public void onItemClick(View view, int postion) {
        showToast("" + postion);
    }

    public class TeamGrade extends BaseAdapter<MessageMyTeamBean> {
        public TeamGrade(List<MessageMyTeamBean> list) {
            super(R.layout.item_team_activity, list);
        }

        @Override
        protected void convert(BaseHolder holder, MessageMyTeamBean item) {
            holder.setText(R.id.team_grade_name, item.getFriendName());
            holder.setText(R.id.team_reg_time, item.getFriendRegTime());
            holder.setText(R.id.wealth_recommend, item.getFriendPostTimes());
            holder.setText(R.id.wealth_movie, item.getFriendActivities());
        }
    }

    @Override
    public RecyclerView getRecyclerView() {
        return mDataBinding.teamGradeTg;
    }

    @Override
    public void onRefresh() {
        mList.clear();
        mPresenter.loadListData();
    }
    @Override
    public void onLoadmore() {
        mPresenter.loadListData();
    }

//    @Override
//    public void bindTabFragment(List<Fragment> fragmentList, List<String> titleList) {
//
//    }
}

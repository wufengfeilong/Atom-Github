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
import woxingwoxiu.com.databinding.MessageFragmentWealthBinding;
import woxingwoxiu.com.message.bean.MessageWealthBean;
import woxingwoxiu.com.message.contract.MessageWealthFragmentContract;
import woxingwoxiu.com.message.presenter.MessageWealthFragmentPresenter;

/**
 * Created by 860117066 on 2018/05/17.
 */

public class MessageWealthFragment extends BaseFragment
        <MessageFragmentWealthBinding, MessageWealthFragmentPresenter>
        implements MessageWealthFragmentContract.View, BaseAdapter.OnItemClickListener {
    int type;
    TeamGrade mTeamGrade;
    List<MessageWealthBean> mList = new ArrayList<>();

    public static MessageWealthFragment newInstance(int type) {
        Bundle args = new Bundle();
        args.putInt("type", type);
        MessageWealthFragment fragment = new MessageWealthFragment();
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
        return R.layout.message_fragment_wealth;
    }


    @Override
    protected void initViews() {
        mDataBinding.setPresenter(mPresenter);
        initRecyclerView();
        mPresenter.loadListData();
    }

    @Override
    public void initRecyclerView() {
        mDataBinding.wealthGradeSpringView.setListener(this);
        mDataBinding.wealthGradeSpringView.setHeader(new DefaultHeader(mContext));
        mDataBinding.wealthGradeSpringView.setFooter(new DefaultFooter(mContext));
        mDataBinding.wealthGradeTg.setLayoutManager(new LinearLayoutManager(mContext));
        mTeamGrade = new TeamGrade(mList);
        mDataBinding.wealthGradeTg.setAdapter(mTeamGrade);
        mTeamGrade.setOnItemClickListener(this);


    }

    @Override
    protected MessageWealthFragmentPresenter createPresenter() {
        return new MessageWealthFragmentPresenter();
    }

    @Override
    public void bindListData(List<MessageWealthBean> beanList) {
        mList.addAll(beanList);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTeamGrade.notifyDataSetChanged();
            }
        });
        mDataBinding.wealthGradeSpringView.onFinishFreshAndLoad();
    }

    @Override
    public void onItemClick(View view, int postion) {
        showToast("" + postion);
    }

    public class TeamGrade extends BaseAdapter<MessageWealthBean> {
        public TeamGrade(List<MessageWealthBean> list) {
            super(R.layout.item_wealth_count,list);
        }

        @Override
        protected void convert(BaseHolder holder, MessageWealthBean item) {
            holder.setText(R.id.wealth_title, item.getWealthTitle());
            holder.setText(R.id.wealth_time, item.getWealthTime());
            holder.setText(R.id.wealth_count, item.getWealthCount());
        }
    }

    @Override
    public RecyclerView getRecyclerView() {
        return mDataBinding.wealthGradeTg;
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

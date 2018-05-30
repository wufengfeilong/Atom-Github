package woxingwoxiu.com.me.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.liaoinstan.springview.container.DefaultFooter;

import java.util.ArrayList;
import java.util.List;

import woxingwoxiu.com.R;
import woxingwoxiu.com.adapter.BaseAdapter;
import woxingwoxiu.com.adapter.BaseHolder;
import woxingwoxiu.com.base.BaseFragment;
import woxingwoxiu.com.databinding.MeHomeVideoBinding;
import woxingwoxiu.com.me.bean.MeHomeBean;
import woxingwoxiu.com.me.contract.MeHomeFragmentContract;
import woxingwoxiu.com.me.presenter.MeHomeFragmentPresenter;
import woxingwoxiu.com.release.activity.DraftListActivity;

/**
 * Created by 860117066 on 2018/05/29.
 */

public class MeHomeVideoFragment extends BaseFragment<MeHomeVideoBinding,MeHomeFragmentPresenter>
        implements MeHomeFragmentContract.View,BaseAdapter.OnItemClickListener{
     int type;
     HomeGrade mHomeGrade;
     List<MeHomeBean> mList = new ArrayList<>();

    public static MeHomeVideoFragment newInstance(int type) {

        Bundle args = new Bundle();
        args.putInt("type", type);
        MeHomeVideoFragment fragment = new MeHomeVideoFragment();
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
        return R.layout.me_home_video;
    }

    @Override
    protected MeHomeFragmentPresenter createPresenter() {
        return new MeHomeFragmentPresenter();
    }

    @Override
    protected void initViews() {
        mDataBinding.setPresenter(mPresenter);
        // Test 用
        mDataBinding.draftListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DraftListActivity.class);
                startActivity(intent);
            }
        });
        initRecyclerView();
        mPresenter.loadListData();
    }
    @Override
    public void initRecyclerView() {
        mDataBinding.homeGradeSpringView.setListener(this);
//        mDataBinding.homeGradeSpringView.setHeader(new DefaultHeader(mContext));
        mDataBinding.homeGradeSpringView.setFooter(new DefaultFooter(mContext));
        mDataBinding.homeGradeTg.setLayoutManager(new GridLayoutManager(mContext, 3));
        mHomeGrade = new  HomeGrade(mList);
        mDataBinding.homeGradeTg.setAdapter(mHomeGrade);
        mHomeGrade.setOnItemClickListener(this);
    }
    @Override
    public void bindListData(List<MeHomeBean> beanList) {
        mList.addAll(beanList);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mHomeGrade.notifyDataSetChanged();
            }
        });
        mDataBinding.homeGradeSpringView.onFinishFreshAndLoad();
    }
    @Override
    public void onItemClick(View view, int postion) {
        showToast("" + postion);
    }

    public class HomeGrade extends BaseAdapter<MeHomeBean> {
        public HomeGrade(List<MeHomeBean> list) {
            super(R.layout.me_home_video_item, list);
        }

        @Override
        protected void convert(BaseHolder holder, MeHomeBean item) {
//            holder.setText(R.id.coverUrl, item.getCoverUrl());
            holder.setText(R.id.commentNumber, item.getCommentNumber());
            holder.setImageResource(R.id.coverUrl, R.drawable.temp);
        }
    }
    @Override
    public RecyclerView getRecyclerView() {
        return mDataBinding.homeGradeTg;
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

}


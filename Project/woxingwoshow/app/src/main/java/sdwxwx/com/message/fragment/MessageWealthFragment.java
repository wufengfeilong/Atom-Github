package sdwxwx.com.message.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import sdwxwx.com.R;
import sdwxwx.com.adapter.BaseAdapter;
import sdwxwx.com.adapter.BaseHolder;
import sdwxwx.com.base.BaseFragment;
import sdwxwx.com.databinding.MessageFragmentWealthBinding;
import sdwxwx.com.me.bean.VideoWealthBean;
import sdwxwx.com.message.contract.MessageWealthFragmentContract;
import sdwxwx.com.message.presenter.MessageWealthFragmentPresenter;

/**
 * Created by 860117066 on 2018/05/17.
 * 类描述：视频财富Fragment
 */

public class MessageWealthFragment extends BaseFragment
        <MessageFragmentWealthBinding, MessageWealthFragmentPresenter>
        implements MessageWealthFragmentContract.View, BaseAdapter.OnItemClickListener {

    /** 前一画面传递的会员ID */
    private String memberId;
    private String avatar_url;
    //定义类型
    int type;
    //视频财富Adapter
    VideoWealthAdapter mVideoWealthAdapter;
    List<VideoWealthBean.WealthHistoryBean> mList = new ArrayList<>();
    public static MessageWealthFragment newInstance(int type,String member_id,String avatar_url) {
        Bundle args = new Bundle();
        args.putInt("type", type);
        args.putString("member_id", member_id);
        args.putString("mFansHeadUrl", avatar_url);
        MessageWealthFragment fragment = new MessageWealthFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.message_fragment_wealth;
    }


    @Override
    protected void initViews() {
        memberId = getArguments().getString("member_id");
        avatar_url = getArguments().getString("mFansHeadUrl");
        mDataBinding.setPresenter(mPresenter);
        initRecyclerView();
        mPresenter.loadListData(mDataBinding);
    }

    @Override
    public String getMemberId(){
        return memberId;
    }
    @Override
    public String getFansHeadUrl(){
        return avatar_url;
    }

    @Override
    public void initRecyclerView() {
        mDataBinding.wealthGradeTg.setLayoutManager(new LinearLayoutManager(mContext));
        mVideoWealthAdapter = new VideoWealthAdapter(mList);
        mDataBinding.wealthGradeTg.setAdapter(mVideoWealthAdapter);
        mVideoWealthAdapter.setOnItemClickListener(this);
    }

    @Override
    public void bindListData(List<VideoWealthBean> beanList) {

    }

    @Override
    protected MessageWealthFragmentPresenter createPresenter() {
        return new MessageWealthFragmentPresenter();
    }

    @Override
    public void bindListData(VideoWealthBean bean) {
        mDataBinding.totalWealth.setText(bean.getTotal_wealth());
        mDataBinding.todayWealth.setText(bean.getToday_wealth());
        mList.addAll(bean.getHistory());
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mVideoWealthAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onItemClick(View view, int postion) {
    }

    public class VideoWealthAdapter extends BaseAdapter<VideoWealthBean.WealthHistoryBean> {
        public VideoWealthAdapter(List<VideoWealthBean.WealthHistoryBean> list) {
            super(R.layout.item_wealth_count,list);
        }

        @Override
        protected void convert(BaseHolder holder, VideoWealthBean.WealthHistoryBean item) {
            // 行为描述
            holder.setText(R.id.wealth_title, item.getAction());
            // 创建时间
            holder.setText(R.id.wealth_time, item.getCreate_time());
            // 财富值
            holder.setText(R.id.wealth_count, item.getWealth());
        }
    }

    @Override
    public RecyclerView getRecyclerView() {
        return mDataBinding.wealthGradeTg;
    }
}

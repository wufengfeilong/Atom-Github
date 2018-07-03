package sdwxwx.com.message.fragment;

import android.os.Bundle;

import sdwxwx.com.R;
import sdwxwx.com.base.BaseCallback;
import sdwxwx.com.base.BaseFragment;
import sdwxwx.com.databinding.MessageRecommendWealthBinding;
import sdwxwx.com.me.bean.RecommendWealthBean;
import sdwxwx.com.message.contract.MessageRecommendWealthFragmentContract;
import sdwxwx.com.message.model.MessageTeamAndWealthModel;
import sdwxwx.com.message.presenter.MessageRecommendWealthFragmentPresenter;

/**
 * Created by 860117066 on 2018/05/17.
 */

public class MessageRecommendWealthFragment extends BaseFragment
        <MessageRecommendWealthBinding, MessageRecommendWealthFragmentPresenter>
        implements MessageRecommendWealthFragmentContract.View {

    /** 前一画面传递的会员ID */
    private String memberId;
    private String FansHeadUrl;

    public static MessageRecommendWealthFragment newInstance(int type, String member_id,String fansheadurl) {
        Bundle args = new Bundle();
        args.putInt("type", type);
        args.putString("member_id", member_id);
        MessageRecommendWealthFragment fragment = new MessageRecommendWealthFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.message_recommend_wealth;
    }

    @Override
    protected void initViews() {
        mDataBinding.setPresenter(mPresenter);

        memberId = getArguments().getString("member_id");

        MessageTeamAndWealthModel.getWealthRecommend(memberId, new BaseCallback<RecommendWealthBean>() {
            @Override
            public void onSuccess(RecommendWealthBean data) {
                // 总财富值
                mDataBinding.totalWealth.setText(data.getTotal_wealth());
                // 今日收益
                mDataBinding.todayWealth.setText(data.getToday_wealth());
                // 原始财富值
                mDataBinding.wealthCount.setText(data.getInitial_wealth());
                // 一级财富值
                mDataBinding.wealthOne.setText(data.getRecommend1_wealth());
                // 二级财富值
                mDataBinding.wealthTwo.setText(data.getRecommend2_wealth());
            }
            @Override
            public void onFail(String msg) {
            }
        });
    }

    @Override
    protected MessageRecommendWealthFragmentPresenter createPresenter() {
        return new MessageRecommendWealthFragmentPresenter();
    }
}

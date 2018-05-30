package woxingwoxiu.com.me.activity;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import woxingwoxiu.com.BR;
import woxingwoxiu.com.R;
import woxingwoxiu.com.adapter.SimpleBindingAdapter;
import woxingwoxiu.com.base.BaseActivity;
import woxingwoxiu.com.databinding.ActivityFindFriendsBinding;
import woxingwoxiu.com.me.bean.FindFriendsBean;
import woxingwoxiu.com.me.contract.FindFriendsContract;
import woxingwoxiu.com.me.presenter.FindFriendsPresenter;

public class FindFriendsActivity extends BaseActivity<ActivityFindFriendsBinding,FindFriendsPresenter> implements FindFriendsContract.View {

    SimpleBindingAdapter<FindFriendsBean> mSimpleBindingAdapter;
    @Override
    protected FindFriendsPresenter createPresenter() {
        return new FindFriendsPresenter();
    }

    @Override
    protected void initViews() {
        mDataBinding.setPresenter(mPresenter);
        mSimpleBindingAdapter = new SimpleBindingAdapter<>(R.layout.find_friends_item, BR.findFriendsBean);
        mPresenter.loadListData();
        mDataBinding.recommendList.setLayoutManager(new LinearLayoutManager(FindFriendsActivity.this));
        mDataBinding.recommendList.addItemDecoration(new DividerItemDecoration(FindFriendsActivity.this, DividerItemDecoration.VERTICAL));
        mDataBinding.recommendList.setAdapter(mSimpleBindingAdapter);

    }

    @Override
    public void bindListData(List<FindFriendsBean> beanList) {
        mSimpleBindingAdapter.setupData(beanList);
    }

    @Override
    public void initRecyclerView() {

    }

    @Override
    public RecyclerView getRecyclerView() {
        return mDataBinding.recommendList;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_find_friends;
    }

}

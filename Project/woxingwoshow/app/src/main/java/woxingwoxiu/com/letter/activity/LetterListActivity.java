package woxingwoxiu.com.letter.activity;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import woxingwoxiu.com.BR;
import woxingwoxiu.com.R;
import woxingwoxiu.com.adapter.SimpleBindingAdapter;
import woxingwoxiu.com.base.BaseActivity;
import woxingwoxiu.com.databinding.ActivityLetterListBinding;
import woxingwoxiu.com.letter.bean.PrivateLetterBean;
import woxingwoxiu.com.letter.contract.LetterListContract;
import woxingwoxiu.com.letter.presenter.LetterListPresenter;


public class LetterListActivity extends BaseActivity<ActivityLetterListBinding, LetterListPresenter> implements LetterListContract.View {
    SimpleBindingAdapter<PrivateLetterBean.LetterBean> mSimpleBindingAdapter;

    @Override
    protected LetterListPresenter createPresenter() {
        return new LetterListPresenter();
    }

    @Override
    protected void initViews() {
        mDataBinding.setPresenter(mPresenter);
        mSimpleBindingAdapter = new SimpleBindingAdapter<>(R.layout.letter_list_item, BR.letter);

        mDataBinding.letterList.setLayoutManager(new LinearLayoutManager(LetterListActivity.this));
        mDataBinding.letterList.addItemDecoration(new DividerItemDecoration(LetterListActivity.this, DividerItemDecoration.VERTICAL));
        mDataBinding.letterList.setAdapter(mSimpleBindingAdapter);
    }

    @Override
    public void bindListData(List<PrivateLetterBean.LetterBean> beanList) {
        mSimpleBindingAdapter.setupData(beanList);
    }

    @Override
    public void finishThis() {
        finish();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_letter_list;
    }

    @Override
    public RecyclerView getRecyclerView() {
        return mDataBinding.letterList;
    }

    @Override
    public void initRecyclerView() {
    }
}

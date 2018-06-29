package com.fcn.park.manager.activity;

import android.support.design.widget.TabLayout;
import android.support.v7.widget.RecyclerView;

import com.fcn.park.R;
import com.fcn.park.base.BaseActivity;
import com.fcn.park.base.adapter.SimpleBindingAdapter;
import com.fcn.park.base.recycler_listener.OnRecyclerItemClickListener;
import com.fcn.park.databinding.ManagerEnterpriseCheckListBinding;
import com.fcn.park.manager.bean.ManagerEnterpriseCheckBean;
import com.fcn.park.manager.contract.ManagerEnterpriseCheckListContract;
import com.fcn.park.manager.presenter.ManagerEnterpriseCheckListPresenter;
import com.liaoinstan.springview.widget.SpringView;
import java.util.List;

/**
 *管理中心的企业审核管理功能用Activity.
 */
public class ManagerEnterpriseCheckListActivity extends BaseActivity<ManagerEnterpriseCheckListBinding, ManagerEnterpriseCheckListContract.View, ManagerEnterpriseCheckListPresenter>
        implements ManagerEnterpriseCheckListContract.View, SpringView.OnFreshListener {

    private SimpleBindingAdapter<ManagerEnterpriseCheckBean.EnterpriseCheckListBean> mAdapter;
    private boolean isEnd;
    private boolean Flg=false;

    @Override
    public RecyclerView getRecyclerView() {
        return null;
    }

    /**
     * 重写的方法，用来加载定义画面的Layout
     * @return
     */
    @Override
    protected int getLayoutId() {
        return R.layout.manager_enterprise_check_list;
    }

    /**
     * 重写的方法，用来设置标题栏要显示的文字
     */
    @Override
    protected void setupTitle() {
        setTitleText(getString(R.string.manager_enterprise_check_list));
        openTitleLeftView(true);
    }

    /**
     * 画面初始化操作：获取当前画面要显示的数据并显示到画面上
     */
    @Override
    protected void initViews() {
        TabLayout tabLayout = (TabLayout) findViewById(R.id.enterprise_tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.manager_enterprise__wait_check)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.manager_enterprise__turn_check)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.manager_enterprise__pass_check)));
       /* mPresenter.loadListData();
        mDataBinding.springViewManagerCarwaitchecklist.setListener(this);
        mDataBinding.springViewManagerCarwaitchecklist.setHeader(new DefaultHeader(this));
        mDataBinding.springViewManagerCarwaitchecklist.setFooter(new DefaultFooter(this));

        mDataBinding.recyclerViewManagerCarwaitchecklist.addOnItemTouchListener(new OnItemClickListener(getRecyclerView()));
        mDataBinding.recyclerViewManagerCarwaitchecklist.setLayoutManager(new LinearLayoutManager(mContext));
        mDataBinding.recyclerViewManagerCarwaitchecklist.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        mDataBinding.recyclerViewManagerCarwaitchecklist.setRefrshView(mDataBinding.springViewManagerCarwaitchecklist);
        mDataBinding.recyclerViewManagerCarwaitchecklist.setEmptyView(findViewById(R.id.car_wait_check_list_empty_view));
        mAdapter  = new SimpleBindingAdapter<>(R.layout.item_car_wait_check_list, BR.carWaitCheckListBean);
        mDataBinding.recyclerViewManagerCarwaitchecklist.setAdapter(mAdapter);*/
    }

    /**
     * 下拉刷新，回调接口
     */
    @Override
    public void onRefresh() {
        onRefresh(isEnd);
    }

    /**
     * 上拉加载，回调接口
     */
    @Override
    public void onLoadmore() {
        onLoadMore(isEnd);
    }

    /**
     * 绑定列表数据
     */
    @Override
    public void bindListData(List<ManagerEnterpriseCheckBean.EnterpriseCheckListBean> beanList) {

    }

    @Override
    protected ManagerEnterpriseCheckListPresenter createPresenter() {
        return  new ManagerEnterpriseCheckListPresenter();
    }

    @Override
    public void initRecyclerView() {

    }

    @Override
    public void updateIsEnd(boolean isEnd) {
        this.isEnd = isEnd;
    }

    private class OnItemClickListener extends OnRecyclerItemClickListener {
        @Override
        public void onItemClick(RecyclerView.ViewHolder vh) {
            mPresenter.onItemClick(vh, vh.getLayoutPosition());
        }

        public OnItemClickListener(RecyclerView recyclerView) {
            super(recyclerView);
        }

    }

}

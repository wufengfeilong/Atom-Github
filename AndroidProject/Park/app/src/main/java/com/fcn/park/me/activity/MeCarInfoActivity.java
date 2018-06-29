package com.fcn.park.me.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.android.databinding.library.baseAdapters.BR;
import com.fcn.park.R;
import com.fcn.park.base.BaseActivity;
import com.fcn.park.base.adapter.SimpleBindingAdapter;
import com.fcn.park.base.recycler_listener.OnRecyclerItemClickListener;
import com.fcn.park.databinding.MeCarInfoActivityBinding;
import com.fcn.park.manager.utils.DialogUtils;
import com.fcn.park.me.bean.MeCarInfoBean;
import com.fcn.park.me.contract.MeCarInfoContract;
import com.fcn.park.me.presenter.MeCarInfoPresenter;
import java.util.List;

/**
 * 车辆列表信息
 */
public class MeCarInfoActivity extends BaseActivity<MeCarInfoActivityBinding,MeCarInfoContract.View,MeCarInfoPresenter>
        implements MeCarInfoContract.View{

    private SimpleBindingAdapter<MeCarInfoBean.CarInfoBean> mSimpleBindingAdapter ;
    private boolean isEnd;
    private int mClickPosition;

    @Override
    protected MeCarInfoPresenter createPresenter() {
        return new MeCarInfoPresenter();
    }
    @Override
    protected void setupTitle() {
        String titleStr = getString(R.string.me_car_info);
        setTitleText(titleStr);
        openTitleLeftView(true);
        setRightMenuText("　+　");
        //为标题栏右侧按钮设置点击事件
        mTvTitleRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext,MeAddCarActivity.class);
                startActivity(intent);
            }
        });

    }
    @Override
    protected void initViews() {
        mPresenter.loadListData();
        //绑定adapter
        mDataBinding.meCarInfo.setLayoutManager(new LinearLayoutManager(mContext));
        mDataBinding.meCarInfo.addOnItemTouchListener(new MeCarInfoActivity.OnItemClickListener(mDataBinding.meCarInfo));
        mDataBinding.meCarInfo.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        mDataBinding.meCarInfo.setEmptyView(mDataBinding.getRoot().findViewById(R.id.empty_car_info_list));
        mSimpleBindingAdapter = new SimpleBindingAdapter<>(R.layout.item_me_car_info_activity, BR.carInfo);
        mDataBinding.meCarInfo.setAdapter(mSimpleBindingAdapter);
    }
    @Override
    protected int getLayoutId() {
        return R.layout.me_car_info_activity;
    }
    //设置bean数据源
    @Override
    public void bindListData(List<MeCarInfoBean.CarInfoBean> beanList) {
        setListData(beanList);
        mSimpleBindingAdapter.setupData(beanList);
    }
    @Override
    public RecyclerView getRecyclerView() {
        return mDataBinding.meCarInfo;
    }

    @Override
    public void updateIsEnd(boolean isEnd) {
        this.isEnd = isEnd;
    }


    @Override
    public void initRecyclerView() {

    }
    @Override
    public int getClickPosition() {
        return mClickPosition;
    }
    //ListView的点击事件
    private class OnItemClickListener extends OnRecyclerItemClickListener {

        public OnItemClickListener(RecyclerView recyclerView) {
            super(recyclerView);
        }
        @Override
        public void onItemClick(RecyclerView.ViewHolder vh) {
            mPresenter.onItemClick(vh, vh.getLayoutPosition());
        }
        //列表长按事件监听
        @Override
        public boolean onItemLongClick(final RecyclerView.ViewHolder vh) {
            DialogUtils.buildAlertDialogWithCancel(mContext, "温馨提示", "是否要删除该条目")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mClickPosition = vh.getAdapterPosition();
                            mPresenter.onItemLongClick();
                        }
                    }).show();
            return true;
        }
    }
    //删除按下的item
    @Override
    public void deleteListItem(int position) {
        mSimpleBindingAdapter.notifyItemRemoved(position);
    }
    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.loadListData();

    }

}

package com.fcn.park.property.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.android.databinding.library.baseAdapters.BR;
import com.fcn.park.R;
import com.fcn.park.base.BaseActivity;
import com.fcn.park.base.constant.Constant;
import com.fcn.park.databinding.PropertyRentParkListBinding;
import com.fcn.park.property.bean.PropertyMainBean;
import com.fcn.park.property.contract.PropertyRentParkListContract;
import com.fcn.park.property.presenter.PropertyRentParkListPresenter;
import com.fcn.park.property.utils.adapter.PropertyListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 月租车辆申请列表画面
 */

public class PropertyRentParkListActivity extends BaseActivity<PropertyRentParkListBinding, PropertyRentParkListContract.View, PropertyRentParkListPresenter>
        implements PropertyRentParkListContract.View {

    private String TAG = "PropertyRentParkListActivity";

    private List<PropertyMainBean> listData = new ArrayList<>();
    private PropertyListAdapter<PropertyMainBean> adapter = null;

    /**
     * 区分画面跳转来源
     * 0：月租车辆申请 1：月租车辆缴费
     */
    private int moveType = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter.dataInit(moveType);
    }

    @Override
    protected void setupTitle() {
        setTitleText(getString(R.string.property_rent_park_list_title));
        openTitleLeftView(true);
        setRightMenuText(getString(R.string.property_rent_park_list_add_btn));
        mTvTitleRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.onAddMenuClick();
            }
        });
    }

    @Override
    protected void initViews() {
        mDataBinding.propertyRentParkListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "initViews() onItemClick position = " + position);
                Intent intent = null;
                String checkStatus = listData.get(position).getCheckStatus();
                if (getString(R.string.property_rent_park_check_ok).equals(checkStatus)) {
                    // 审核通过的情况下，跳转到缴费画面
                    intent = new Intent(PropertyRentParkListActivity.this, PropertyPayActivity.class);
                    intent.putExtra(Constant.PROPERTY_PAY_ID, listData.get(position).getParkPayId());
                    intent.putExtra(Constant.PROPERTY_PAY_TYPE, 5);
                } else if (getString(R.string.property_rent_park_checking).equals(checkStatus)) {
                    // 审核中的情况下，跳转到月租车辆申请状态展示画面
                    intent = new Intent(PropertyRentParkListActivity.this, PropertyParkPayCheckStatusActivity.class);
                    intent.putExtra(Constant.PROPERTY_PAY_ID, listData.get(position).getParkPayId());
                    intent.putExtra(Constant.PROPERTY_PARK_PAY_CHECK, 1);
                } else {
                    // 审核未通过的情况下，跳转到月租车辆申请状态展示画面
                    intent = new Intent(PropertyRentParkListActivity.this, PropertyParkPayCheckStatusActivity.class);
                    intent.putExtra(Constant.PROPERTY_PAY_ID, listData.get(position).getParkPayId());
                    intent.putExtra(Constant.PROPERTY_PARK_PAY_CHECK, 0);
                }
                if (null != intent) {
                    startActivity(intent);
                }
            }
        });
        mDataBinding.propertyRentParkListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {
                //定义AlertDialog.Builder对象，当长按列表项的时候弹出确认删除对话框
                AlertDialog.Builder builder = new AlertDialog.Builder(PropertyRentParkListActivity.this);
                builder.setMessage("确定删除?");
                builder.setTitle("提示");

                //添加AlertDialog.Builder对象的setPositiveButton()方法
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mPresenter.deleteItemFromServer(listData.get(i).getParkPayId(), i);
                        Log.d("删除确定", "删除列表项");
                        showToast("删除列表项");
                    }
                });

                //添加AlertDialog.Builder对象的setNegativeButton()方法
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("删除取消", "取消");
                    }
                });

                builder.create().show();
                return true;
            }
        });
    }

    @Override
    protected int getLayoutId() {
        if (Constant.PROPERTY_MOVE_TYPE_PAY.equals(getIntent().getStringExtra(Constant.PROPERTY_MOVE_TYPE))) {
            moveType = 1;
        }
        return R.layout.property_rent_park_list;
    }

    @Override
    protected PropertyRentParkListPresenter createPresenter() {
        return new PropertyRentParkListPresenter();
    }

    @Override
    public void setData(List<PropertyMainBean> list) {
        listData = list;
        Log.d("setData", "size = " + list.size());
        if (listData.size() > 0) {
            // 绑定Adapter
            adapter = new PropertyListAdapter<>(this, listData, R.layout.property_rent_park_item, BR.bean);
            mDataBinding.setAdapter(adapter);
        } else {
            mDataBinding.propertyRentParkListView.setVisibility(View.GONE);
            mDataBinding.empty.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void deleteItemFromView(int position) {
        // 更新画面显示内容
        listData.remove(position);
        adapter.notifyDataSetChanged();
        if (listData.size() == 0) {
            mDataBinding.propertyRentParkListView.setVisibility(View.GONE);
            mDataBinding.empty.setVisibility(View.VISIBLE);
        }
    }
}
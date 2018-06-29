package com.fcn.park.property.activity;

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
import com.fcn.park.databinding.PropertyPaymentListBinding;
import com.fcn.park.property.bean.PropertyMainBean;
import com.fcn.park.property.contract.PropertyPaymentListContract;
import com.fcn.park.property.presenter.PropertyPaymentListPresenter;
import com.fcn.park.property.utils.adapter.PropertyListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 缴费列表画面
 */

public class PropertyPaymentListActivity extends BaseActivity<PropertyPaymentListBinding,
        PropertyPaymentListContract.View, PropertyPaymentListPresenter> implements PropertyPaymentListContract.View {

    private String TAG = "PropertyPaymentListActivity";

    // 1:水费缴费 2:电费缴费 3:物业费缴费
    // 4:租赁费缴费 5:月租停车费 6:临时停车缴费
    private int mPayType;

    private List<PropertyMainBean> listData = new ArrayList<>();
    private PropertyListAdapter<PropertyMainBean> adapter = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate  mPayType = " + mPayType);
        mPresenter.dataInit(mPayType);
    }

    @Override
    protected void setupTitle() {
        String titleStr = "";
        switch (mPayType) {
            case 1:
                titleStr = getString(R.string.property_water_payment_list_title);
                break;
            case 2:
                titleStr = getString(R.string.property_electric_payment_list_title);
                break;
            case 3:
                titleStr = getString(R.string.property_management_payment_list_title);
                break;
            case 4:
                titleStr = getString(R.string.property_rent_payment_list_title);
                break;
            // 月租车辆与临时停车共用
            case 5:
            case 6:
                titleStr = getString(R.string.property_parking_payment_list_title);
                break;
            default:
                titleStr = getString(R.string.property_water_payment_list_title);
                break;
        }
        setTitleText(titleStr);
        openTitleLeftView(true);
    }

    @Override
    protected void initViews() {
        mDataBinding.propertyPaymentListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                showToast("You clicked item :" + i);
                Intent intent = new Intent(PropertyPaymentListActivity.this, PropertyPaymentDetailActivity.class);
                intent.putExtra("bean", listData.get(i));
                Log.d(TAG, "initViews  listData.get(i).getPayType() = " + listData.get(i).getPayType());
                startActivity(intent);
            }
        });

    }

    @Override
    protected int getLayoutId() {
        mPayType = getIntent().getIntExtra(Constant.PROPERTY_PAY_TYPE, 1);
        Log.d(TAG, "getLayoutId  mPayType = " + mPayType);
        return R.layout.property_payment_list;
    }

    @Override
    protected PropertyPaymentListPresenter createPresenter() {
        return new PropertyPaymentListPresenter();
    }

    @Override
    public void setData(List<PropertyMainBean> list) {
        listData = list;
        Log.d("setData", "size = " + list.size());
        if (listData.size() > 0) {
            // 绑定Adapter
            switch (mPayType) {
                case 1:
                    adapter = new PropertyListAdapter<>(this, listData, R.layout.property_water_fee_payment_item, BR.bean);
                    break;
                case 2:
                    adapter = new PropertyListAdapter<>(this, listData, R.layout.property_electric_fee_payment_item, BR.bean);
                    break;
                case 3:
                    adapter = new PropertyListAdapter<>(this, listData, R.layout.property_fee_payment_item, BR.bean);
                    break;
                case 4:
                    adapter = new PropertyListAdapter<>(this, listData, R.layout.property_rent_fee_payment_item, BR.bean);
                    break;
                case 5:
                case 6:
                    adapter = new PropertyListAdapter<>(this, listData, R.layout.property_rent_park_payment_item, BR.bean);
                    break;
                default:
                    adapter = new PropertyListAdapter<>(this, listData, R.layout.property_water_fee_payment_item, BR.bean);
                    break;
            }
            mDataBinding.setAdapter(adapter);
        } else {
            mDataBinding.propertyPaymentListView.setVisibility(View.GONE);
            mDataBinding.empty.setVisibility(View.VISIBLE);
        }
    }
}

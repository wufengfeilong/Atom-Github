package com.fcn.park.property.activity;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.fcn.park.R;
import com.fcn.park.base.BaseActivity;
import com.fcn.park.databinding.PropertyServiceLayoutBinding;
import com.fcn.park.property.contract.PropertyServiceContract;
import com.fcn.park.property.presenter.PropertyServicePresenter;

import java.util.List;


public class PropertyServiceActivity extends BaseActivity<PropertyServiceLayoutBinding, PropertyServiceContract.View, PropertyServicePresenter> implements PropertyServiceContract.View {
    private ListView listView;
    private SimpleAdapter adapter;

    @Override
    protected PropertyServicePresenter createPresenter() {
        return new PropertyServicePresenter();
    }

    @Override
    protected void setupTitle() {
        String titleStr = getString(R.string.property_service);
        setTitleText(titleStr);
        openTitleLeftView(true);
    }

    @Override
    protected void initViews() {
        listView = mDataBinding.propertyService;
        //取得数据源并设置adapter
        setAdapter();
        //列表item点击事件
        setOnItemClick();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.property_service_layout;
    }

    public void setAdapter() {
        //获得数据源
        List list = mPresenter.getData();
        // 数据源与适配器的绑定
        adapter = new SimpleAdapter(
                this,//第一个参数上下文 当前的Activity
                list, //第二个参数是一个集合类型的数据源
                R.layout.property_service_item, //第三个参数是一个用于展示效果的Layout就是我们设定的布局文件
                new String[]{"name"}, //第四个参数通过源码可以看出需要的是一个K值的字符串数组
                new int[]{R.id.service_name}//第五个参数通过源码看出是一个与K值匹配的的控件对象
        );
        listView.setAdapter(adapter);
    }

    public void setOnItemClick() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        Intent intent = new Intent(mContext, PropertyDeliverWaterActivity.class);
                        mContext.startActivity(intent);
                        break;
                    case 1:
                        Intent intent1 = new Intent(mContext, PropertyMoveHouseActivity.class);
                        mContext.startActivity(intent1);
                        break;
                    case 2:
                        Intent intent2 = new Intent(mContext, PropertyCleanActivity.class);
                        mContext.startActivity(intent2);
                        break;
                    case 3:
                        Intent intent3 = new Intent(mContext, PropertyPlantLeaseActivity.class);
                        mContext.startActivity(intent3);
                        break;
                    default:
                        break;
                }
            }
        });
    }
}

package com.fcn.park.property.presenter;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.fcn.park.R;
import com.fcn.park.base.BasePresenter;
import com.fcn.park.base.constant.Constant;
import com.fcn.park.base.utils.OnDataGetCallback;
import com.fcn.park.property.activity.PropertyMoveHouseActivity;
import com.fcn.park.property.bean.PropertyMoveHouseBean;
import com.fcn.park.property.contract.PropertyMoveHouseContract;
import com.fcn.park.property.module.PropertyMoveHouseModule;
import com.fcn.park.property.utils.customViews.PropertyServiceDialog;

import java.util.List;

/**
 * Created by 860117073 on 2018/4/18.
 */

/**
 * 搬家服务的presenter
 */
public class PropertyMoveHousePresenter extends BasePresenter<PropertyMoveHouseContract.View> implements PropertyMoveHouseContract.Presenter {

    private PropertyMoveHouseModule mPropertyMoveHouseModule;
    private PropertyServiceDialog propertyServiceDialog;
    public String str;

    @Override
    public void attach(PropertyMoveHouseContract.View view) {
        super.attach(view);
        mPropertyMoveHouseModule = new PropertyMoveHouseModule();
        createItemClickListener(getView().getRecyclerView());
        getView().initRecyclerView();
    }

    @Override
    public void loadListData() {
        loadListData(1);
    }

    /**
     * 加载更多的数据
     * 只需要根据相应的页码加载相应的数据，无需关心刷新和加载更多
     *
     * @param page
     */
    @Override
    public void onLoadMore(int page) {
        loadListData(page);
    }

    public void loadListData(int page) {
        //绑定数据
        mPropertyMoveHouseModule.requestMoveHouseList(getView().getContext(), page, new OnDataGetCallback<List<PropertyMoveHouseBean.MoveHouseBean>>() {
            @Override
            public void onSuccessResult(List<PropertyMoveHouseBean.MoveHouseBean> listNewsBeen) {
                getView().updateIsEnd(mPropertyMoveHouseModule.isEnd());
                getView().bindListData(listNewsBeen);
            }
        });
    }
    //列表的点击事件
    @Override
    public void onItemClick(RecyclerView.ViewHolder vh, int position) {
        View view;
        view = vh.itemView;
        //获取电话图标
        ImageView iv = (ImageView) view.findViewById(R.id.move_house_phone_img);
        //获取到电话号码存入str变量；
        TextView tv = (TextView) view.findViewById(R.id.move_house_phone);
        str = tv.getText().toString();
        //创建一个自定义dialog
        propertyServiceDialog = new PropertyServiceDialog(getView().getContext());
        //设置提示信息
        propertyServiceDialog.setMessage("要给"+str+"打电话吗？");
        //为自定义dialog设置确定的监听事件
        propertyServiceDialog.setYesOnclickListener("确定", new PropertyServiceDialog.onYesOnclickListener() {
            @Override
            public void onYesClick() {
                callPhone(str);
                propertyServiceDialog.dismiss();
            }
            public void callPhone(String str) {
                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (ContextCompat.checkSelfPermission(getView().getContext(), Manifest.permission.CALL_PHONE)
                                != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions((PropertyMoveHouseActivity)getView().getContext(),
                                    new String[]{Manifest.permission.CALL_PHONE}, 10);
                        }
                    }
                    Intent phoneIntent = new Intent(Constant.PROPERTY_CALL_ACTION, Uri.parse("tel:" + str));
                    //启动
                    getView().getContext().startActivity(phoneIntent);
                } catch (Exception e) {
                    e.printStackTrace();
                }}});
        //为自定义dialog设置取消的监听事件
        propertyServiceDialog.setNoOnclickListener("取消", new PropertyServiceDialog.onNoOnclickListener(){
            @Override
            public void onNoClick(){
                propertyServiceDialog.dismiss();
            }
        });
        propertyServiceDialog.show();

    }
}

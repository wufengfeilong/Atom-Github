package com.fcn.park.property.presenter;

import android.app.ProgressDialog;
import android.util.Log;
import android.widget.Toast;

import com.fcn.park.base.BasePresenter;
import com.fcn.park.base.interfacee.OnDataCallback;
import com.fcn.park.login.LoginHelper;
import com.fcn.park.property.activity.PropertyParkPayActivity;
import com.fcn.park.property.bean.PropertyMainBean;
import com.fcn.park.property.contract.PropertyRentParkListContract;
import com.fcn.park.property.module.PropertyRentParkListModule;

import java.util.ArrayList;
import java.util.List;

/**
 * 绿色物管的月租车辆审核列表画面用Presenter
 */

public class PropertyRentParkListPresenter extends BasePresenter<PropertyRentParkListContract.View> implements PropertyRentParkListContract.Presenter {
    private PropertyRentParkListModule mPropertyRentParkListModule = null;
    private ProgressDialog mDialog = null;

    @Override
    public void attach(PropertyRentParkListContract.View view) {
        super.attach(view);
        mPropertyRentParkListModule = new PropertyRentParkListModule();
    }

    @Override
    public void dataInit(int moveType) {
        // 网络状况等会引起数据加载缓慢，所以加载过程中显示Dialog
        mDialog = new ProgressDialog(getView().getContext());
        mDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        //取消点击屏幕会使Dialog消失
        mDialog.setCanceledOnTouchOutside(false);
        //设置Dialog为不可撤销的，只能够等结束后返回
        mDialog.setCancelable(false);
        mDialog.setMessage("数据加载中...");
        mDialog.show();

        if (moveType == 1) {
            // 画面跳转来源是“月租停车缴费”时

            mPropertyRentParkListModule.requestPassedRentParkList(getView().getContext(), LoginHelper.getInstance().getUserToken(), new OnDataCallback<List<PropertyMainBean>>() {
                @Override
                public void onSuccessResult(List<PropertyMainBean> data) {
                    if (null != mDialog) {
                        mDialog.dismiss();
                        mDialog = null;
                    }
                    getView().setData(data);

                    Log.d("PropertyRentParkList", "dataInit() onSuccessResult" + data.size());
                    getView().showToast("获取成功");

                }

                @Override
                public void onError(String errMsg) {
                    if (null != mDialog) {
                        mDialog.dismiss();
                        mDialog = null;
                    }
                    getView().setData(new ArrayList<PropertyMainBean>());
                    Log.d("PropertyRentParkList", "dataInit() onError" + errMsg);
                    getView().showToast(errMsg);
                }
            });
        } else {
            // 画面跳转来源是“月租停车申请”时

            mPropertyRentParkListModule.requestRentParkList(getView().getContext(), LoginHelper.getInstance().getUserToken(), new OnDataCallback<List<PropertyMainBean>>() {
                @Override
                public void onSuccessResult(List<PropertyMainBean> data) {
                    if (null != mDialog) {
                        mDialog.dismiss();
                        mDialog = null;
                    }
                    getView().setData(data);

                    Log.d("PropertyRentParkList", "dataInit() onSuccessResult" + data.size());
                    getView().showToast("获取成功");

                }

                @Override
                public void onError(String errMsg) {
                    if (null != mDialog) {
                        mDialog.dismiss();
                        mDialog = null;
                    }
                    getView().setData(new ArrayList<PropertyMainBean>());
                    Log.d("PropertyRentParkList", "dataInit() onError" + errMsg);
                    getView().showToast(errMsg);
                }
            });
        }
    }

    @Override
    public void deleteItemFromServer( int parkPayId, final int position) {
        mPropertyRentParkListModule.deleteRentParkItem(getView().getContext(), parkPayId, new OnDataCallback<String>() {
            @Override
            public void onSuccessResult(String data) {
                getView().deleteItemFromView(position);
                Log.d("PropertyRentParkList", "deleteItemFromServer() onSuccessResult" + data);
                getView().showToast("删除成功");
            }

            @Override
            public void onError(String errMsg) {
                Log.d("PropertyRentParkList", "deleteItemFromServer() onError" + errMsg);
                getView().showToast("删除失败");
            }
        });
    }

    /**
     * 列表画面右上角的追加按钮的点击事件
     */
    @Override
    public void onAddMenuClick() {
        getView().actionStartActivity(PropertyParkPayActivity.class);
    }
}

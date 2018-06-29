package com.fcn.park.property.presenter;

import android.app.ProgressDialog;
import android.util.Log;
import android.widget.Toast;

import com.fcn.park.base.BasePresenter;
import com.fcn.park.base.interfacee.OnDataCallback;
import com.fcn.park.login.LoginHelper;
import com.fcn.park.property.bean.PropertyMainBean;
import com.fcn.park.property.contract.PropertyPaymentListContract;
import com.fcn.park.property.module.PropertyPaymentListModule;

import java.util.ArrayList;
import java.util.List;

/**
 * 绿色物管的缴费列表画面用Presenter
 */

public class PropertyPaymentListPresenter extends BasePresenter<PropertyPaymentListContract.View> implements PropertyPaymentListContract.Presenter {

    private PropertyPaymentListModule mPropertyPaymentListModule = null;
    private ProgressDialog mDialog = null;

    private final String TAG = "PropertyPaymentList";

    @Override
    public void attach(PropertyPaymentListContract.View view) {
        super.attach(view);
        mPropertyPaymentListModule = new PropertyPaymentListModule();
    }

    @Override
    public void dataInit(int payType) {

        // 网络状况等会引起数据加载缓慢，所以加载过程中显示Dialog
        mDialog = new ProgressDialog(getView().getContext());
        mDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        //取消点击屏幕会使Dialog消失
        mDialog.setCanceledOnTouchOutside(false);
        //设置Dialog为不可撤销的，只能够等结束后返回
        mDialog.setCancelable(false);
        mDialog.setMessage("数据加载中...");
        mDialog.show();
        Log.d(TAG, "" + payType);

        switch (payType) {
            // 1:水费缴费
            case 1:
                mPropertyPaymentListModule.requestWaterFeePaymentList(getView().getContext(),
                        LoginHelper.getInstance().getUserToken(),
                        new OnDataCallback<List<PropertyMainBean>>() {
                            @Override
                            public void onSuccessResult(List<PropertyMainBean> data) {

                                if (null != mDialog) {
                                    mDialog.dismiss();
                                    mDialog = null;
                                }

                                getView().setData(data);

                                Log.d(TAG, "dataInit() onSuccessResult" + data.size());
                                getView().showToast("获取成功");
                            }

                            @Override
                            public void onError(String errMsg) {

                                if (null != mDialog) {
                                    mDialog.dismiss();
                                    mDialog = null;
                                }
                                Log.d(TAG, "dataInit() onError" + errMsg);
                                getView().showToast(errMsg);

                            }
                        });
                break;
            // 2:电费缴费
            case 2:
                mPropertyPaymentListModule.requestElectricFeePaymentList(getView().getContext(),
                        LoginHelper.getInstance().getUserToken(),
                        new OnDataCallback<List<PropertyMainBean>>() {
                            @Override
                            public void onSuccessResult(List<PropertyMainBean> data) {

                                if (null != mDialog) {
                                    mDialog.dismiss();
                                    mDialog = null;
                                }

                                getView().setData(data);

                                Log.d(TAG, "dataInit() onSuccessResult" + data.size());
                                getView().showToast("获取成功");
                            }

                            @Override
                            public void onError(String errMsg) {

                                if (null != mDialog) {
                                    mDialog.dismiss();
                                    mDialog = null;
                                }
                                Log.d(TAG, "dataInit() onError" + errMsg);
                                getView().showToast(errMsg);
                            }
                        });
                break;
            // 3:物业费缴费
            case 3:
                mPropertyPaymentListModule.requestPropertyFeePaymentList(getView().getContext(),
                        LoginHelper.getInstance().getUserToken(),
                        new OnDataCallback<List<PropertyMainBean>>() {
                            @Override
                            public void onSuccessResult(List<PropertyMainBean> data) {

                                if (null != mDialog) {
                                    mDialog.dismiss();
                                    mDialog = null;
                                }

                                getView().setData(data);

                                Log.d(TAG, "dataInit() onSuccessResult" + data.size());
                                getView().showToast("获取成功");
                            }

                            @Override
                            public void onError(String errMsg) {

                                if (null != mDialog) {
                                    mDialog.dismiss();
                                    mDialog = null;
                                }
                                Log.d(TAG, "dataInit() onError" + errMsg);
                                getView().showToast(errMsg);
                            }
                        });
                break;
            // 4:租赁费缴费
            case 4:
                mPropertyPaymentListModule.requestRentFeePaymentList(getView().getContext(),
                        LoginHelper.getInstance().getUserToken(),
                        new OnDataCallback<List<PropertyMainBean>>() {
                            @Override
                            public void onSuccessResult(List<PropertyMainBean> data) {

                                if (null != mDialog) {
                                    mDialog.dismiss();
                                    mDialog = null;
                                }

                                getView().setData(data);

                                Log.d(TAG, "dataInit() onSuccessResult" + data.size());
                                getView().showToast("获取成功");
                            }

                            @Override
                            public void onError(String errMsg) {

                                if (null != mDialog) {
                                    mDialog.dismiss();
                                    mDialog = null;
                                }
                                Log.d(TAG, "dataInit() onError" + errMsg);
                                getView().showToast(errMsg);
                            }
                        });
                break;
            // 5:月租停车费 6:临时停车缴费 同样
            case 5:
            case 6:
                mPropertyPaymentListModule.requestParkingPaymentList(getView().getContext(),
                        LoginHelper.getInstance().getUserToken(),
                        new OnDataCallback<List<PropertyMainBean>>() {
                            @Override
                            public void onSuccessResult(List<PropertyMainBean> data) {
                                if (null != mDialog) {
                                    mDialog.dismiss();
                                    mDialog = null;
                                }
                                getView().setData(data);

                                Log.d(TAG, "dataInit() onSuccessResult" + data.size());
                                getView().showToast("获取成功");
                            }

                            @Override
                            public void onError(String errMsg) {
                                if (null != mDialog) {
                                    mDialog.dismiss();
                                    mDialog = null;
                                }
                                getView().setData(new ArrayList<PropertyMainBean>());
                                Log.d(TAG, "dataInit() onError" + errMsg);
                                getView().showToast(errMsg);
                            }
                        });
                break;
            default:
                break;
        }
    }
}

package com.fcn.park.property.presenter;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.fcn.park.R;
import com.fcn.park.base.BasePresenter;
import com.fcn.park.base.constant.Constant;
import com.fcn.park.base.interfacee.OnDataCallback;
import com.fcn.park.login.LoginHelper;
import com.fcn.park.property.activity.PropertyPaymentListActivity;
import com.fcn.park.property.bean.PropertyMainBean;
import com.fcn.park.property.contract.PropertyPayContract;
import com.fcn.park.property.module.PropertyPayModule;

/**
 * 绿色物管的缴费画面用Presenter
 * 用payType来区分支付类型
 *     1:水费缴费
 *     2:电费缴费
 *     3:物业费缴费
 *     4:租赁费缴费
 *     5:月租停车费
 *     6:临时停车缴费
 */

public class PropertyPayPresenter extends BasePresenter<PropertyPayContract.View> implements PropertyPayContract.Presenter {

    private PropertyPayModule mPropertyPayModule = null;
    private ProgressDialog mDialog = null;

    @Override
    public void attach(PropertyPayContract.View view) {
        super.attach(view);
        mPropertyPayModule = new PropertyPayModule();
    }

    @Override
    public void onPaymentListClick(int payType) {
        Log.d("PropertyPayPresenter", "onPaymentListClick 点击缴费记录");
        Intent intent = new Intent(getView().getContext(), PropertyPaymentListActivity.class);
        intent.putExtra(Constant.PROPERTY_PAY_TYPE, payType);
        getView().getContext().startActivity(intent);

    }

    @Override
    public void onDataInit(int payType) {
        // 网络状况等会引起数据加载缓慢，所以加载过程中显示Dialog
        mDialog = new ProgressDialog(getView().getContext());
        mDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        //取消点击屏幕会使Dialog消失
        mDialog.setCanceledOnTouchOutside(false);
        //设置Dialog为不可撤销的，只能够等结束后返回
        mDialog.setCancelable(false);
        mDialog.setMessage("数据加载中...");
        mDialog.show();

        switch (payType) {
            // 1:水费缴费
            case 1:
                mPropertyPayModule.requestPropertyWaterPayInfo(getView().getContext(), LoginHelper.getInstance().getUserToken(), 0, new OnDataCallback<PropertyMainBean>() {
                    @Override
                    public void onSuccessResult(PropertyMainBean data) {
                        if (null != mDialog) {
                            mDialog.dismiss();
                            mDialog = null;
                        }
                        if (data != null) {
                            getView().setLayoutContent(data);
                            Log.d("### success ###", "data.getCompanyStr() = " + data.getCompanyStr());
                        }
                        Log.d("### success ###", "data = null");
                    }

                    @Override
                    public void onError(String errMsg) {
                        if (null != mDialog) {
                            mDialog.dismiss();
                            mDialog = null;
                        }
                        Log.d("### failed ###", "errMsg = " + errMsg);
                        getView().showToast(errMsg);
                    }
                });
                break;
            // 2:电费缴费
            case 2:
                mPropertyPayModule.requestPropertyElectricPayInfo(getView().getContext(), LoginHelper.getInstance().getUserToken(), 0, new OnDataCallback<PropertyMainBean>() {
                    @Override
                    public void onSuccessResult(PropertyMainBean data) {
                        if (null != mDialog) {
                            mDialog.dismiss();
                            mDialog = null;
                        }
                        if (data != null) {
                            getView().setLayoutContent(data);
                            Log.d("### success ###", "data.getCompanyStr() = " + data.getCompanyStr());
                        }
                        Log.d("### success ###", "data = null");
                    }

                    @Override
                    public void onError(String errMsg) {
                        if (null != mDialog) {
                            mDialog.dismiss();
                            mDialog = null;
                        }
                        Log.d("### failed ###", "errMsg = " + errMsg);
                        getView().showToast(errMsg);
                    }
                });
                break;
            // 3:物业费缴费
            case 3:
                mPropertyPayModule.requestPropertyFeePayInfo(getView().getContext(), LoginHelper.getInstance().getUserToken(), 0, new OnDataCallback<PropertyMainBean>() {

                    @Override
                    public void onSuccessResult(PropertyMainBean data) {
                        if (null != mDialog) {
                            mDialog.dismiss();
                            mDialog = null;
                        }
                        if (data != null) {
                            getView().setLayoutContent(data);
                            Log.d("### success ###", "data.getCompanyStr() = " + data.getCompanyStr());
                        }
                        Log.d("### success ###", "data = null");
                    }

                    @Override
                    public void onError(String errMsg) {
                        if (null != mDialog) {
                            mDialog.dismiss();
                            mDialog = null;
                        }
                        Log.d("### failed ###", "errMsg = " + errMsg);
                        getView().showToast(errMsg);
                    }
                });
                break;
            // 4:租赁费缴费
            case 4:
                mPropertyPayModule.requestRentFeePayInfo(getView().getContext(), LoginHelper.getInstance().getUserToken(), 0, new OnDataCallback<PropertyMainBean>() {

                    @Override
                    public void onSuccessResult(PropertyMainBean data) {
                        if (null != mDialog) {
                            mDialog.dismiss();
                            mDialog = null;
                        }
                        if (data != null) {
                            getView().setLayoutContent(data);
                            Log.d("### success ###", "data.getCompanyStr() = " + data.getCompanyStr());
                        }
                        Log.d("### success ###", "data = null");
                    }

                    @Override
                    public void onError(String errMsg) {
                        if (null != mDialog) {
                            mDialog.dismiss();
                            mDialog = null;
                        }
                        Log.d("### failed ###", "errMsg = " + errMsg);
                        getView().showToast(errMsg);
                    }
                });
                break;
            // 5:月租停车费
            case 5:
                mPropertyPayModule.requestRentParkInfo(getView().getContext(), LoginHelper.getInstance().getUserToken(), getView().getParkPayID(), 3, new OnDataCallback<PropertyMainBean>() {

                    @Override
                    public void onSuccessResult(PropertyMainBean data) {
                        if (null != mDialog) {
                            mDialog.dismiss();
                            mDialog = null;
                        }
                        if (data != null) {
                            getView().setLayoutContent(data);
                            Log.d("### success ###", "data.getCompanyStr() = " + data.getCompanyStr());
                        }
                        Log.d("### success ###", "data = null");
                    }

                    @Override
                    public void onError(String errMsg) {
                        if (null != mDialog) {
                            mDialog.dismiss();
                            mDialog = null;
                        }
                        Log.d("### failed ###", "errMsg = " + errMsg);
                        getView().showToast(errMsg);
                    }
                });
                break;
            // 6:临时停车缴费
            case 6:
                String plateNum = getView().getPlateNum();
                mPropertyPayModule.requestTemporaryParkInfo(getView().getContext(), plateNum, 7, new OnDataCallback<PropertyMainBean>() {

                    @Override
                    public void onSuccessResult(PropertyMainBean data) {
                        if (null != mDialog) {
                            mDialog.dismiss();
                            mDialog = null;
                        }
                        if (data != null) {
                            getView().setLayoutContent(data);
                            Log.d("### success ###", "data.getCompanyStr() = " + data.getCompanyStr());
                        }
                        Log.d("### success ###", "data = null");
                    }

                    @Override
                    public void onError(String errMsg) {
                        if (null != mDialog) {
                            mDialog.dismiss();
                            mDialog = null;
                        }
                        Log.d("### failed ###", "errMsg = " + errMsg);
                        showErrorDialog(errMsg);
                        getView().showToast(errMsg);
                    }
                });
                break;
            default:
                break;
        }
    }

    /**
     * 显示错误信息
     *
     * @param errMsg 错误信息
     */
    private void showErrorDialog(String errMsg) {
        AlertDialog.Builder errorDialog = new AlertDialog.Builder(getView().getContext());
        errorDialog.setTitle(getView().getContext().getString(R.string.property_data_apply_error_title));
        errorDialog.setMessage(errMsg);
        errorDialog.setCancelable(false);
        errorDialog.setPositiveButton(R.string.property_pay_status_confirm, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                getView().closeActivity();
            }
        });
        errorDialog.show();
    }
}

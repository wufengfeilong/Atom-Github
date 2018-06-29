package com.fcn.park.property.utils.alipay;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.fcn.park.base.http.ApiClient;
import com.fcn.park.base.http.HttpResult;
import com.fcn.park.base.http.ProgressSubscriber;
import com.fcn.park.base.http.RequestImpl;
import com.fcn.park.base.http.RetrofitManager;
import com.fcn.park.property.bean.AlipayBean;
import com.fcn.park.property.utils.OnPayResultListener;

import java.util.Map;

/**
 * 作者：吕振鹏
 * 创建时间：12月15日
 * 时间：12:31
 * 版本：v1.0.0
 * 类描述：
 * 修改时间：
 */

public class AliPayUtils {


    private static final int SDK_PAY_FLAG = 1;

    private Context mContext;
    private OnPayResultListener mOnPayResultListener;

    public AliPayUtils(Context mContext) {
        this.mContext = mContext;
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /*
                     * 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(mContext, "支付成功", Toast.LENGTH_SHORT).show();
                        if (mOnPayResultListener != null) {
                            mOnPayResultListener.onSuccess();
                        }
                    } else {
                        if (mOnPayResultListener != null) {
                            mOnPayResultListener.onFailure();
                        }
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(mContext, "支付失败：" + payResult.getMemo(), Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                default:
                    break;
            }
        }
    };

    public void setOnPayResultListener(OnPayResultListener listener) {
        mOnPayResultListener = listener;
    }

    public void openPay(String user_id, String course_name, String commodity_id, String body, String course_id, String subject_id, String teaching_style, String subject, String total_amount, String order_number) {
        RetrofitManager.toSubscribe(
                ApiClient.getApiService().getAlipayOrderInfo(user_id, course_name, commodity_id, body, course_id, subject_id, teaching_style, subject, total_amount, order_number)
                , new ProgressSubscriber<HttpResult<AlipayBean>>(mContext, new RequestImpl<HttpResult<AlipayBean>>() {
                    @Override
                    public void onNext(HttpResult<AlipayBean> result) {
                        payV2(result.getData().getOrderString());
                    }
                })
        );
    }


    /**
     * 支付宝支付业务
     */
    private void payV2(final String orderInfo) {

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask((Activity) mContext);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Log.i("msp", result.toString());

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }
}

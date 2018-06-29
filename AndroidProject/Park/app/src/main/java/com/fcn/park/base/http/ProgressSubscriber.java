package com.fcn.park.base.http;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.fcn.park.base.utils.NetworkUtils;

import rx.Subscriber;

/**
 * Created by 860115001 on 2018/04/10.
 * 用于在Http请求开始时，自动显示一个ProgressDialog
 * 在Http请求结束是，关闭ProgressDialog
 * 调用者自己对请求数据进行处理
 */

public class ProgressSubscriber<T extends HttpResult> extends Subscriber<T> {
    private RequestImpl<T> mRequestCallback;
    private ProgressDialog mDialog;

    private Context context;

    public ProgressSubscriber(Context context, RequestImpl<T> requestCallback) {
        this.mRequestCallback = requestCallback;
        this.context = context;
        mDialog = new ProgressDialog(context);
        mDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mDialog.setCanceledOnTouchOutside(false);//取消点击屏幕会使Dialog消失
        mDialog.setCancelable(false);//设置Dialog为不可撤销的，只能够等结束后返回
        mDialog.setMessage("数据加载中...");
    }

    private void showProgressDialog() {
        mDialog.show();
    }

    private void dismissProgressDialog() {
        mDialog.dismiss();
        onCancelProgress();
    }

    /**
     * 订阅开始时调用
     * 显示ProgressDialog
     */
    @Override
    public void onStart() {
        showProgressDialog();
    }

    /**
     * 完成，隐藏ProgressDialog
     */
    @Override
    public void onCompleted() {
        dismissProgressDialog();
    }

    /**
     * 对错误进行统一处理
     * 隐藏ProgressDialog
     *
     * @param e
     */
    @Override
    public void onError(Throwable e) {

        Log.e("err", "----错误信息 = " + e.getClass().getSimpleName() + "：" + e.getMessage());

        Toast.makeText(context, NetworkUtils.getErrorMsg(e), Toast.LENGTH_SHORT).show();

        dismissProgressDialog();
    }

    /**
     * 将onNext方法中的返回结果交给Activity或Fragment自己处理
     *
     * @param t 创建Subscriber时的泛型类型
     */
    @Override
    public void onNext(T t) {
        dismissProgressDialog();
        if (mRequestCallback != null) {
            if (t.isResult() || t.getFlag() == 0)
                try {
                    mRequestCallback.onNext(t);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            else {
                Toast.makeText(context, t.getMsg(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 取消ProgressDialog的时候，取消对observable的订阅，同时也取消了http请求
     */
    public void onCancelProgress() {
        if (!this.isUnsubscribed()) {
            this.unsubscribe();
        }
    }
}

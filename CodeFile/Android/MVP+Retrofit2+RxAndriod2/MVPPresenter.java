package com.fujisoft.test.presenter;

import android.text.Editable;
import com.fujisoft.test.bean.Name;
import com.fujisoft.test.bean.User;
import com.fujisoft.test.iview.IMVPView;
import com.fujisoft.test.util.RetrofitAPIManager;
import io.reactivex.*;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import java.util.List;

public class MVPPresenter {
    IMVPView imvpView;

    public MVPPresenter(IMVPView imvpView) {
        this.imvpView = imvpView;
    }

    public void sendNameDate(int id) {
        imvpView.showProgressbar();
//        RetrofitAPIManager
//                .getInstance()
//                .getRetrofitAPIService()
//                .getUsers()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<List<User>>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                        if (d.isDisposed()) {
//                            imvpView.onSendFail(-1, "订阅取消");
//                            imvpView.hideProgressbar();
//                        }
//                    }
//
//                    @Override
//                    public void onNext(List<User> list) {
//
//                        imvpView.onSendSuccess(list.get(0));
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        imvpView.onSendFail(e.hashCode(), e.getMessage());
//                        imvpView.hideProgressbar();
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        imvpView.hideProgressbar();
//                    }
//                });

        RetrofitAPIManager
                .getInstance()
                .getRetrofitAPIService()
                .getById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<User>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        if (d.isDisposed()) {
                            imvpView.onSendFail(-1, "订阅取消");
                            imvpView.hideProgressbar();
                        }
                    }

                    @Override
                    public void onNext(User list) {

                        imvpView.onSendSuccess(list);
                    }

                    @Override
                    public void onError(Throwable e) {
                        imvpView.onSendFail(e.hashCode(), e.getMessage());
                        imvpView.hideProgressbar();
                    }

                    @Override
                    public void onComplete() {
                        imvpView.hideProgressbar();
                    }
                });
    }
}

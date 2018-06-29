package com.fcn.park.base.http;

import com.fcn.park.login.bean.CodeBean;
import com.fcn.park.login.bean.User;

import rx.Subscriber;

/**
 * Created by 860115001 on 2018/04/10.
 * 类描述：专门用于请求数据的Module类
 */

public class RequestModule {

    public void startLogin(String account, String password, Subscriber<HttpResult<User>> subscriber) {
        RetrofitManager.toSubscribe(
                ApiClient.getApiService().login(account, password)
                , subscriber
        );
    }

    /**
     * 根据手机号码重置密码-获取短信验证码
     *
     * @param phoneNum      手机号码
     * @param subscriber
     */
    public void startSendCodeByPhone(String phoneNum, Subscriber<HttpResult<CodeBean>> subscriber){
        RetrofitManager.toSubscribe(
                ApiClient.getApiService().sendCodeByPhone(phoneNum)
                , subscriber
        );
    }

    /**
     * 根据手机号码重置密码-修改密码
     * @param phoneNum      手机号码
     * @param pwd           新密码
     * @param subscriber
     */
    public void startEditPwdByPhone(String phoneNum, String pwd, Subscriber<HttpResult<String>> subscriber){
        RetrofitManager.toSubscribe(
                ApiClient.getApiService().editPwdByPhone(phoneNum,pwd)
                , subscriber
        );
    }

//    public void saveCID(String username,String cid){
//        RetrofitManager.toSubscribe(
//                ApiClient.getApiService().saveCID(username, cid)
//                , new Subscriber<HttpResult<String>>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable throwable) {
//
//                    }
//
//                    @Override
//                    public void onNext(HttpResult<String> stringHttpResult) {
//
//                    }
//                }
//        );
//    }
}

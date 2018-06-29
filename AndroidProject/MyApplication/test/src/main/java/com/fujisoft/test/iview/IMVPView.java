package com.fujisoft.test.iview;

import com.fujisoft.test.bean.User;

public interface IMVPView {
    void showProgressbar();
    void hideProgressbar();
    void onSendSuccess(User name);
    void onSendFail(int errCode,String errMsg);
}

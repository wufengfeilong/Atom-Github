package com.fujisoft.update;

import com.lzy.okgo.callback.AbsCallback;
import okhttp3.Response;

public abstract class UploadCallBack extends AbsCallback {
    /**
     * 戻る値の処理
     * @param response
     * @return
     * @throws Throwable
     */
    @Override
    public Object convertResponse(Response response) throws Throwable {
        return null;
    }
}

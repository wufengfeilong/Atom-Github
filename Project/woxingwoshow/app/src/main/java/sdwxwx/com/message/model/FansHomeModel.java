package sdwxwx.com.message.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import sdwxwx.com.base.BaseCallback;
import sdwxwx.com.http.APIClient;
import sdwxwx.com.http.HttpResult;
import sdwxwx.com.util.MsgDbHelper;
import sdwxwx.com.util.NetworkUtils;

import java.util.HashMap;
import java.util.List;

/**
 * Created by 860117073 on 2018/6/8.
 */

public class FansHomeModel {
    //
    public static void cancelFollow(final Context context, final String member_id, final String followed_member_id, final BaseCallback<String> callback) {
        HashMap<String, Object> mHashMap = new HashMap<>();
        long times = APIClient.getTimes();
        mHashMap.put("member_id", member_id);
        mHashMap.put("followed_member_id", followed_member_id);
        mHashMap.put("timestamp", times);
        String signature = APIClient.getSign(mHashMap);
        APIClient.getAPIService().removeUser(member_id,followed_member_id, times, signature).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<HttpResult<List<String>>>() {
            @Override
            public void accept(HttpResult<List<String>> result) throws Exception {
                if (result.getCode() == 0) {
                    MsgDbHelper dbHelper = new MsgDbHelper(context);
                    //得到一个可写的数据库
                    SQLiteDatabase db =dbHelper.getWritableDatabase();
                    // 更新数据库中的关系
                    dbHelper.updateMsgById(db, member_id, followed_member_id, false);
                    callback.onSuccess("");
                } else {
                    callback.onFail(result.getMessage());
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                callback.onFail(NetworkUtils.getErrorMsg(throwable));
            }
        });
    }
}

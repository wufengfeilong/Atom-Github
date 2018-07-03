package sdwxwx.com.message.presenter;

import android.database.sqlite.SQLiteDatabase;
import sdwxwx.com.base.BaseCallback;
import sdwxwx.com.base.BasePresenter;
import sdwxwx.com.login.utils.LoginHelper;
import sdwxwx.com.message.activity.FansHomeActivity;
import sdwxwx.com.message.bean.MessageListBean;
import sdwxwx.com.message.contract.MessageListContract;
import sdwxwx.com.message.model.MessageListModel;
import sdwxwx.com.util.MsgDbHelper;

import java.util.List;

/**
 * Created by 860117073 on 2018/5/11.
 */

public class MessageListFragmentPresenter extends BasePresenter<MessageListContract.View> implements MessageListContract.Presenter {

    /**
     * 跳转到好友主页
     */
    public void toFansHome(){
            getView().actionStartActivity(FansHomeActivity.class);
    }

    /**
     * 加载数据
     */
    public void loadData(){
        // 显示加载中对话框
        getView().showLoading();
            // 调用通知接口，获取最新通知数据
            MessageListModel.getMessageList(LoginHelper.getInstance().getUserId(), new BaseCallback<List<MessageListBean.Result>>() {
                    @Override
                    public void onSuccess(List<MessageListBean.Result> data) {
                        if (getView() == null) {
                            return;
                        }
                        MsgDbHelper dbHelper = new MsgDbHelper(getView().getContext());
                        //得到一个可读的数据库
                        SQLiteDatabase db =dbHelper.getWritableDatabase();
                        dbHelper.insertMsg(db, data);
                        // 若获取成功，则把最新数据存放到文件中并进行显示
                        getView().messageSaveIntoFile();
                    }

                    @Override
                    public void onFail(String msg) {
                        if (getView() == null) {
                            return;
                        }
                            // 若不成功，则直接显示原来存储数据
                            getView().messageSaveIntoFile();
                    }
                });
        }
}

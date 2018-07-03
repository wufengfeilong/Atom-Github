package sdwxwx.com.me.presenter;

import com.android.tu.loadingdialog.LoadingDailog;
import sdwxwx.com.base.BaseCallback;
import sdwxwx.com.base.BasePresenter;
import sdwxwx.com.login.utils.LoginHelper;
import sdwxwx.com.me.contract.FeedbackContract;
import sdwxwx.com.me.model.FeedBackModel;
import sdwxwx.com.util.StringUtil;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by 860117073 on 2018/5/9.
 */

public class FeedbackPresenter extends BasePresenter<FeedbackContract.View> implements FeedbackContract.Presenter {

    /** 反馈 */
    private FeedBackModel mModel;
    private LoadingDailog mProgressDialog;

    @Override
    public void attachView(FeedbackContract.View mvpView) {
        super.attachView(mvpView);
        mModel = new FeedBackModel();
    }
    /**
     * 提交反馈数据
     */
    public void getData() {
        // 正在保存中的dialog
        LoadingDailog.Builder builder = new LoadingDailog.Builder(getView().getContext())
                .setMessage("保存中...")
                .setCancelable(true)
                .setCancelOutside(false);
        mProgressDialog = builder.create();
        mProgressDialog.show();
        // 获取画面输入内容
        String content= getView().getContent();
        // 如果为空，则弹出提示消息
        if (StringUtil.isEmpty(content)) {
            mProgressDialog.hide();
            getView().showCustomToast("请您输入反馈内容");
            return;
        }
        // 调用网络接口进行更新数据
        mModel.addFeedback(String.valueOf(LoginHelper.getInstance().getUserId()),
                content, new BaseCallback<List<String>>() {
            @Override
            public void onSuccess(List<String> data) {
                mProgressDialog.hide();
                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                        // 关闭当前画面
                        getView().closeActivity();
                    }
                };
                // 延迟二秒再关闭画面
                Timer timer = new Timer();
                timer.schedule(task, 1000);
                if (getView() == null) {
                    return;
                }
                getView().showCustomToast("反馈成功");

            }
            @Override
            public void onFail(String msg) {
                mProgressDialog.hide();
                if (getView() == null) {
                    return;
                }
                getView().showCustomToast("反馈失败");
            }
        });
    }

    /**
     * 返回前一画面
     */
    public void onBack() {
        // 关闭当前的Activity，返回上一个画面
        getView().closeActivity();
    }
}

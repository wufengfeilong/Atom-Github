package sdwxwx.com.me.presenter;

import com.android.tu.loadingdialog.LoadingDailog;
import sdwxwx.com.base.BaseCallback;
import sdwxwx.com.base.BasePresenter;
import sdwxwx.com.bean.UserBean;
import sdwxwx.com.login.utils.LoginHelper;
import sdwxwx.com.me.contract.MeEditContract;
import sdwxwx.com.me.model.MeEditModel;
import sdwxwx.com.util.LoginUtil;
import sdwxwx.com.util.StringUtil;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by 860116042 on 2018/5/17.
 */

public class MeEditPresenter extends BasePresenter<MeEditContract.View> implements MeEditContract.Presenter {
    private UserBean bean;
    private LoadingDailog mProgressDialog;
    @Override
    public void onClickSave() {
        // 正在保存中的dialog
        LoadingDailog.Builder builder = new LoadingDailog.Builder(getView().getContext())
                .setMessage("保存中...")
                .setCancelable(true)
                .setCancelOutside(false);
        mProgressDialog = builder.create();
        mProgressDialog.show();
        // 获取画面信息
        final UserBean targetBean = getView().getUserInfo();
        // 获取
        bean = LoginUtil.getLoginInfo(getView().getContext());
        // 如果画面的头像为NULl，则没有变化
        if (StringUtil.isEmpty(targetBean.getAvatar_url())) {
            targetBean.setAvatar_url(bean.getAvatar_url());
        }
        // 画面信息与原来情报进行比较
        // 如果头像地址，昵称，性别，生日，个性签名与原来一致，则无需更新数据库
        if (targetBean.getAvatar_url().equals(bean.getAvatar_url())
                && targetBean.getNickname().equals(bean.getNickname())
                && targetBean.getGender().equals(bean.getGender())
                && targetBean.getBirthday().equals(bean.getBirthday())
                && targetBean.getSignature().equals(bean.getSignature())) {
            mProgressDialog.hide();
            getView().showCustomToast("您还没有做任何修改");
        } else {
            // 调用接口，进行数据更新操作
            MeEditModel.editMemberInfo(bean.getId(), targetBean.getNickname(), targetBean.getSignature(),
                    targetBean.getGender(), targetBean.getBirthday(), targetBean.getAvatar_url(), new BaseCallback<List<String>>() {
                        @Override
                        public void onSuccess(List<String> data) {
                            // 更新bean中情报
                            bean.setAvatar_url(targetBean.getAvatar_url());
                            bean.setNickname(targetBean.getNickname());
                            bean.setGender(targetBean.getGender());
                            bean.setBirthday(targetBean.getBirthday());
                            bean.setSignature(targetBean.getSignature());
                            // 跳转到我的HOME
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
                            // 更新登录信息
                            LoginUtil.setUserBean(getView().getContext(), bean);
                            LoginHelper.getInstance().setUserBean(bean);
                            mProgressDialog.hide();

                            getView().showCustomToast("保存成功！");

                        }

                        @Override
                        public void onFail(String msg) {
                            mProgressDialog.hide();
                            if (getView() == null) {
                                return;
                            }
                            getView().showCustomToast("保存失败");
                        }
                    });
        }
    }

    @Override
    public void onClickBack() {
        getView().closeActivity();
    }

    @Override
    public void onClickImage() {
        getView().GetPermission();
    }

    // 点击选择性别
    @Override
    public void onClickSelectSex() {
        getView().showSexChooseDialog();
    }

    // 点击选择生日
    @Override
    public void onClickSelectBirth() {
        getView().showDateDialog();
    }


}

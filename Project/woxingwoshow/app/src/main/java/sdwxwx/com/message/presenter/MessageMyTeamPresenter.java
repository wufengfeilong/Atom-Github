package sdwxwx.com.message.presenter;

import sdwxwx.com.base.BasePresenter;
import sdwxwx.com.message.contract.MessageMyTeamContract;

/**
 * 类描述：我的团队一级二级列表
 */

public class MessageMyTeamPresenter extends BasePresenter<MessageMyTeamContract.View> implements MessageMyTeamContract.Presenter{
    @Override
    public void onClickBack(){
        getView().closeActivity();
    }

}

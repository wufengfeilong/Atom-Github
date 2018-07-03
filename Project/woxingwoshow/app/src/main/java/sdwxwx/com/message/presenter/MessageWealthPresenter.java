package sdwxwx.com.message.presenter;

import sdwxwx.com.base.BasePresenter;
import sdwxwx.com.message.contract.MessageWealthContract;

/**
 * Created by 860117066 on 2018/05/17.
 */

public class MessageWealthPresenter extends BasePresenter<MessageWealthContract.View> implements MessageWealthContract.Presenter{


    @Override
    public void onClickBack(){
        getView().closeActivity();
    }

}

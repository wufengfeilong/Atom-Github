package woxingwoxiu.com.letter.presenter;

import woxingwoxiu.com.base.BasePresenter;
import woxingwoxiu.com.letter.contract.LetterChatContract;

/**
 * Created by 860117073 on 2018/5/28.
 */

public class LetterChatPresenter extends BasePresenter<LetterChatContract.View> implements LetterChatContract.Presenter {
    public void back(){
        getView().closeActivity();
    }
}

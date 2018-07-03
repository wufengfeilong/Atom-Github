package sdwxwx.com.letter.presenter;

import sdwxwx.com.base.BasePresenter;
import sdwxwx.com.letter.contract.LetterListContract;

/**
 * Created by 860117073 on 2018/5/28.
 */

public class LetterListPresenter extends BasePresenter<LetterListContract.View> implements LetterListContract.Presenter {
    public void back(){
        //返回键操作
        getView().finishThis();
    }

    @Override
    public void loadListData() {
    }

}

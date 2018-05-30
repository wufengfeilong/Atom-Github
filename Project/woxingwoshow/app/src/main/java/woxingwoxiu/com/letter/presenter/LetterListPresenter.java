package woxingwoxiu.com.letter.presenter;

import java.util.ArrayList;
import java.util.List;

import woxingwoxiu.com.base.BasePresenter;
import woxingwoxiu.com.letter.bean.PrivateLetterBean;
import woxingwoxiu.com.letter.contract.LetterListContract;

/**
 * Created by 860117073 on 2018/5/28.
 */

public class LetterListPresenter extends BasePresenter<LetterListContract.View> implements LetterListContract.Presenter {
    List<PrivateLetterBean.LetterBean> mList = new ArrayList<>();
    public void back(){
        //返回键操作
        getView().finishThis();
    }

    @Override
    public void loadListData() {
        mList.clear();


    }
}

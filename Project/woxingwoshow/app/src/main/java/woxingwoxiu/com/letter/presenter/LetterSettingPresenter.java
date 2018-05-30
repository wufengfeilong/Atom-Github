package woxingwoxiu.com.letter.presenter;

import woxingwoxiu.com.base.BasePresenter;
import woxingwoxiu.com.letter.contract.LetterSettingContract;
import woxingwoxiu.com.message.activity.MessageReportReasonActivity;

/**
 * Created by 860117073 on 2018/5/24.
 */

public class LetterSettingPresenter extends BasePresenter<LetterSettingContract.View> implements LetterSettingContract.Presenter {

   public void back(){
       getView().finishThis();
   }

   public void toReport(){
       getView().actionStartActivity(MessageReportReasonActivity.class);
   }
}

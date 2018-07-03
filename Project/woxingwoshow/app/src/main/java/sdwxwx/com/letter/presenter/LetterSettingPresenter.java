package sdwxwx.com.letter.presenter;

import sdwxwx.com.base.BasePresenter;
import sdwxwx.com.letter.contract.LetterSettingContract;
import sdwxwx.com.message.activity.FansHomeActivity;

/**
 * Created by 860117073 on 2018/5/24.
 */

public class LetterSettingPresenter extends BasePresenter<LetterSettingContract.View> implements LetterSettingContract.Presenter {

   public void back(){
       getView().finishThis();
   }

    /**
     * 跳转到粉丝主页
     */
   public void userInfor(){
       // 跳转到好友资料画面
       getView().paramStartActivity(FansHomeActivity.class, getView().getMemberId());
   }
}

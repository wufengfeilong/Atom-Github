package sdwxwx.com.letter.presenter;

import android.content.Intent;
import sdwxwx.com.base.BasePresenter;
import sdwxwx.com.letter.activity.LetterSettingActivity;
import sdwxwx.com.letter.contract.LetterChatContract;

/**
 * Created by 860117073 on 2018/5/28.
 */

public class LetterChatPresenter extends BasePresenter<LetterChatContract.View> implements LetterChatContract.Presenter {


    public void back(){
        getView().closeActivity();
    }

    /**
     * 跳转到设定画面
     */
    public void letterSet(){
        Intent intent = new Intent();
        // 跳转到好友详情画面
        intent.setClass(getView().getContext(), LetterSettingActivity.class);
        // 传递对方的会员ID
        intent.putExtra("target_member_id", getView().getTargetMemberId());
        // 传递对方的环信ID
        intent.putExtra("target_em_id", getView().getTargetEmId());

        getView().getContext().startActivity(intent);
    };

}

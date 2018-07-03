package sdwxwx.com.letter.contract;

import sdwxwx.com.base.BaseView;

/**
 * Created by 860117073 on 2018/5/28.
 */

public interface LetterChatContract {
    interface View extends BaseView {
        String getTargetMemberId();
        String getTargetEmId();
    }

    interface Presenter {
    }
}

package sdwxwx.com.letter.contract;

import sdwxwx.com.base.BaseView;

/**
 * Created by 860117073 on 2018/5/24.
 */

public interface LetterSettingContract {
    interface View extends BaseView {
       void finishThis();
        String getMemberId();
    }
    interface Presenter {
    }
}

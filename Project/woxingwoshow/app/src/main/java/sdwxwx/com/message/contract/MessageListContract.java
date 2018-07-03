package sdwxwx.com.message.contract;

import com.liaoinstan.springview.widget.SpringView;

import java.util.List;

import sdwxwx.com.base.BaseView;
import sdwxwx.com.message.bean.MessageListBean;

/**
 * Created by 860117073 on 2018/5/11.
 */

public interface MessageListContract {
     interface View extends BaseView,SpringView.OnFreshListener {
         void messageSaveIntoFile();
    }
     interface Presenter {

    }
}

package sdwxwx.com.release.contract;

import sdwxwx.com.base.BaseView;
import sdwxwx.com.bean.TopicInfoBean;

import java.util.List;

/**
 * 添加话题画面的Contract
 */

public class AddTopicContract {
    public interface View extends BaseView {

        // 更新数据列表
        void refreshListData(List<TopicInfoBean> refreshData);

        // 设置不使用话题
        void setNoTopic();
    }

    public interface Presenter {

        // 点击取消
        void onCancelClick();

        // 获取话题列表信息
        void getAddTopicList(String keyWords, String page);

        // 选择不使用话题
        void selectNoTopic();
    }
}

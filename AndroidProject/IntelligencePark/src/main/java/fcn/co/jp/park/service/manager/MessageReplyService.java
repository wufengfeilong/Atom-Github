package fcn.co.jp.park.service.manager;

import fcn.co.jp.park.util.PageData;

import java.util.List;
import java.util.Map;

/**
 * Created by 丁胜胜 on 2018/04/26.
 * 描述：管理中心的留言列表用Service接口
 */
public interface MessageReplyService {
    /**
     * 获取留言列表信息
     * @return
     */
    List<PageData> getMessageReplyList(PageData pd);

    /**
     * 根据留言Id获取留言详情内容
     * @return
     */
    Map<String, Object> getMessageReplyInfoById(PageData pd);

    /**
     * 点击“回复”按钮后，更新信息
     * @param pd
     * @return
     */
    int updateMessageReplyInfoByAnswer(PageData pd);

    /**
     * 获取留言列表总件数
     * @return
     */
    Integer getMessageReplyListCount();


}

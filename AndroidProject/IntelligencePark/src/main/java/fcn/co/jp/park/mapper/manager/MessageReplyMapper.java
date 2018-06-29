package fcn.co.jp.park.mapper.manager;

import fcn.co.jp.park.util.PageData;

import java.util.List;

/**
 * Created by 丁胜胜 on 2018/04/16.
 * 描述：管理中心留言列表用Mapper
 */
public interface MessageReplyMapper {
    /**
     *根据留言的id，删除
     * @param pd
     * @return
     */
    int deleteMessageReplyById(PageData pd);

    /**
     * 查询留言列表内容
     * @return
     */
    List<PageData> getMessageReplyList(PageData pd);

    /**
     * 根据留言的id，获取留言详情
     * @param pd
     * @return
     */
    PageData getMessageReplyInfoById(PageData pd);

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

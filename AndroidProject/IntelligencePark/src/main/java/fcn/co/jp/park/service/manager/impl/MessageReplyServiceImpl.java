package fcn.co.jp.park.service.manager.impl;

import fcn.co.jp.park.mapper.manager.MessageReplyMapper;
import fcn.co.jp.park.service.manager.MessageReplyService;
import fcn.co.jp.park.util.PageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(value = "messageReplyService")
public class MessageReplyServiceImpl implements MessageReplyService{

    @Autowired
    private MessageReplyMapper mMessageReplyMapper;

    /**
     * 描述：获取留言详情里所有的List内容
     * @return
     */
    @Override
    public List<PageData> getMessageReplyList(PageData pd) {
        return mMessageReplyMapper.getMessageReplyList(pd);
    }

    /**
     * 根据留言的id，获取留言详情
     * @param pd：留言ID
     * @return
     */
    @Override
    public Map<String, Object> getMessageReplyInfoById(PageData pd) {
        Map<String, Object> returnMap = new HashMap<>();
        Map<String, Object> data = new HashMap<>();
        PageData param = new PageData();
        String suggestionId = pd.get("suggestionId").toString();
        param.put("suggestionId", suggestionId);
        PageData result = mMessageReplyMapper.getMessageReplyInfoById(param);
        if (result != null) {
            data.put("suggestionId", result.get("suggestionId"));
            data.put("userId", result.get("userId"));
            data.put("userName", result.get("userName"));
            data.put("describeContent", result.get("describeContent"));
            data.put("answerContent", result.get("answerContent"));
            data.put("updateTimestamp", result.get("updateTimestamp"));

            returnMap.put("result", true);
            returnMap.put("msg", "查询留言详情成功");
            returnMap.put("data", data);
            return returnMap;
        } else {
            returnMap.put("result", false);
            returnMap.put("msg", "查询留言详情失败");
            returnMap.put("data", data);
            return returnMap;
        }
    }
    /**
     * 点击“回复”按钮后，更新信息
     * @return
     */
    @Override
    public int updateMessageReplyInfoByAnswer(PageData pd) {
        return mMessageReplyMapper.updateMessageReplyInfoByAnswer(pd);
    }

    @Override
    public Integer getMessageReplyListCount() {
        Integer result = mMessageReplyMapper.getMessageReplyListCount();
        return result;
    }

}
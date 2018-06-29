package fcn.co.jp.park.service.me.impl;

import fcn.co.jp.park.mapper.me.MeMyMsgMapper;
import fcn.co.jp.park.service.me.MeMyMsgService;
import fcn.co.jp.park.util.PageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 860117066 on 2018/04/17.
 * 查看我的消息列表
 */
@Service(value = "meMyMsgService")
public class MeMyMsgServiceImpl implements MeMyMsgService {
    @Autowired
    private MeMyMsgMapper meMyMsgMapper ;
    @Override
    public Map<String, Object> selectMyMsgList(PageData pd) {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        List<PageData> result = meMyMsgMapper.selectMyMsgList(pd);
        if (result != null) {
            returnMap.put("listMessageBean", result);
        } else {
            returnMap.put("result", false);
            returnMap.put("msg", "查询失败");
        }
        return returnMap;

    }
    @Override
    public Map<String, Object> getMsgDetailById(String id) {
        Map<String, Object> returnMap = new HashMap<>();
        PageData result = meMyMsgMapper.getMsgDetailById(id);
        if (result != null) {
            returnMap.put("data", result);
        } else {
            returnMap.put("result", false);
            returnMap.put("msg", "查询失败");

        }
        return returnMap;
    }

    @Override
    public int selectHasMyMsg(String userId) {
        return meMyMsgMapper.selectHasMyMsg(userId);
    }

    @Override
    public int updateMsgStatusById(String id) {
        return meMyMsgMapper.updateMsgStatusById(id);
    }
}

package fcn.co.jp.park.service.me;

import fcn.co.jp.park.util.PageData;

import java.util.Map;

/**
 * Created by 860117066 on 2018/04/17.
 * 查看我的消息列表
 */
public interface MeMyMsgService {
    Map<String,Object> selectMyMsgList(PageData pd);

    Map<String,Object> getMsgDetailById(String id);

    int selectHasMyMsg(String userId);

    int updateMsgStatusById(String id);
}

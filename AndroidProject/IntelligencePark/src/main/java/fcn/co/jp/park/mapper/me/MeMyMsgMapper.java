package fcn.co.jp.park.mapper.me;

import fcn.co.jp.park.util.PageData;

import java.util.List;

/**
 * Created by 860117066 on 2018/04/17.
 * 查看我的消息列表
 */
public interface MeMyMsgMapper {
    List<PageData> selectMyMsgList(PageData pd);

    PageData  getMsgDetailById(String id);

    int selectHasMyMsg(String userId);
    int  updateMsgStatusById(String id);
}

package fcn.co.jp.park.controller.me;

import fcn.co.jp.park.controller.BaseController;
import fcn.co.jp.park.service.me.MeMyMsgService;
import fcn.co.jp.park.util.AppUtil;
import fcn.co.jp.park.util.Const;
import fcn.co.jp.park.util.PageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * create by 860115039
 * date      2018/04/24
 * time      13:18
 * 个人中心-我的消息列表，消息详情
 */
@Controller
@RequestMapping(value = "/myMessage")
public class MeMyMsgController extends BaseController {

    @Autowired
    private MeMyMsgService meMyMsgService;

    /**
     * 我的消息列表
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/messageRecordList", produces = {"application/json;charset=UTF-8"})
    public Object messageRecordList() {

        PageData pd = this.getPageData();
        Map<String, Object> returnMap = new HashMap<>();
        Map<String, Object> meMyMsgListMap = null;
        Map<String, Object> data = new HashMap<String, Object>();
        try {
            pd.put("userId", pd.get("userId"));
            int pageNum = Integer.valueOf(pd.getString("pageNum"));
            pd.put("numS", (pageNum - 1) * Const.SHOWCOUNT);
            pd.put("numE", Const.SHOWCOUNT);
            meMyMsgListMap = meMyMsgService.selectMyMsgList(pd);
            int count = meMyMsgService.selectHasMyMsg((String) pd.get("userId"));
            int totalPage = AppUtil.getTotalPage(count);
            data.put("isNext", true);
            if (pageNum >= totalPage) {
                data.put("isNext", false);
            }
            // 设定新闻列表的查询结果
            data.put("listMessageBean", meMyMsgListMap.get("listMessageBean"));
//            // 设定总页数
            data.put("totalPage", String.valueOf(totalPage));
            returnMap.put("data", data);
            returnMap.put("result", true);
            returnMap.put("msg", "查询成功");
        } catch (Exception e) {
            returnMap.put("result", false);
            returnMap.put("msg", "系统异常,请稍后再试");
        }


        return returnMap;
    }

    /**
     * 根据id获取报修详情内容
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/messageRecordDetail", produces = {"application/json;charset=UTF-8"})
    public Object messageRecordDetail(@RequestParam("id") String id) {

        Map<String, Object> returnMap = new HashMap<>();
        try {
            returnMap = meMyMsgService.getMsgDetailById(id);
            meMyMsgService.updateMsgStatusById(id);
            returnMap.put("result", true);
        } catch (Exception e) {
            returnMap.put("result", false);
            returnMap.put("msg", "系统异常,请稍后再试");
        }
        return returnMap;
    }

    /**
     * 查询有无未读消息
     *
     * @return
     */
    @RequestMapping(value = "/selectHasMsg")
    @ResponseBody
    public Object selectHasMsg(@RequestParam("userId") String userId) {
        int flg = meMyMsgService.selectHasMyMsg(userId);
        Map<String, Object> returnMap = new HashMap();
        returnMap.put("result", true);
        returnMap.put("msg", flg);
        return returnMap;

    }

}

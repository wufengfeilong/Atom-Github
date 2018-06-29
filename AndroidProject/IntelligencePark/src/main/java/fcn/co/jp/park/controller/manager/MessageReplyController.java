package fcn.co.jp.park.controller.manager;


import fcn.co.jp.park.controller.BaseController;
import fcn.co.jp.park.service.manager.MessageReplyService;
import fcn.co.jp.park.util.AppUtil;
import fcn.co.jp.park.util.Const;
import fcn.co.jp.park.util.PageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 丁胜胜 on 2018/04/26.
 * 描述：管理中心留言列表用Controller
 */
@Controller
@RequestMapping(value = "/messageReplyInfo")
public class MessageReplyController extends BaseController {

    @Autowired
    private MessageReplyService messageReplyService;

    /**
     * 获取留言列表信息
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/messageReplyList", produces = {"application/json;charset=UTF-8"})
    public Object getMessageReplyList() {
        PageData pd = this.getPageData();
        Map<String, Object> data = new HashMap<>();
        List<PageData> getReplyList = null;
        Map<String, Object> returnMap = new HashMap<>();
        try {
            int pageNum = Integer.valueOf(pd.getString("pageNum"));
            pd.put("numS", (pageNum - 1) * Const.SHOWCOUNT);
            pd.put("numE", Const.SHOWCOUNT);
            getReplyList = messageReplyService.getMessageReplyList(pd);

            int count = messageReplyService.getMessageReplyListCount();
            int totalPage = AppUtil.getTotalPage(count);

            // 设定isNext
            data.put("isNext", true);
            if(pageNum >= totalPage){
                data.put("isNext", false);
            }
            // 设定列表的查询结果
            data.put("getListMessageReply", getReplyList);
            // 设定总页数
            data.put("totalPage", String.valueOf(totalPage));

            // 设定返回值
            returnMap.put("data", data);
            returnMap.put("result", true);
            returnMap.put("msg", "查询成功");
        } catch (Exception e) {
            returnMap.put("result", false);
            returnMap.put("msg", "系统异常,请稍后再试 e = " + e);
            returnMap.put("data", getReplyList);
        }
        return returnMap;
    }
    /**
     * 获取留言详情信息
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/messageReplyDetail", produces = {"application/json;charset=UTF-8"})
    public Object messageReplyDetail() {
        PageData pd = this.getPageData();
        Map<String, Object> returnMap = new HashMap<>();
        try {
            returnMap = messageReplyService.getMessageReplyInfoById(pd);
            returnMap.put("result", true);
        } catch (Exception e) {
            returnMap.put("result", false);
            returnMap.put("msg", "系统异常,请稍后再试 e = " + e);
        }
        return returnMap;
    }

    /**
     * 点击“回复”按钮后，更新信息
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateMessageReplyInfoByAnswer", produces = {"application/json;charset=UTF-8"})
    public Object updateMessageReplyInfoByAnswer() {
        PageData pd = this.getPageData();
        Map<String, Object> returnMap = new HashMap<>();
        int updateResult = -1;
        try {
            updateResult = messageReplyService.updateMessageReplyInfoByAnswer(pd);
            returnMap.put("data", updateResult);
            returnMap.put("msg", "回复");
            returnMap.put("result", true);
        } catch (Exception e) {
            returnMap.put("data", updateResult);
            returnMap.put("msg", "系统异常,请稍后再试");
            returnMap.put("result", false);
        }
        return returnMap;
    }
}

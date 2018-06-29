package fcn.co.jp.park.controller.manager;

import fcn.co.jp.park.controller.BaseController;
import fcn.co.jp.park.service.manager.RepairsService;
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
 * Created by 丁胜胜 on 2018/04/17.
 * 描述：管理中心报修一览用Controller
 */
@Controller
@RequestMapping(value = "/repairsInfo")
public class RepairsController extends BaseController {

    @Autowired
    private RepairsService repairsService;

    /**
     * 获取报修一览列表信息
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/repairsList", produces = {"application/json;charset=UTF-8"})
    public Object getRepairsListMessage() {
        PageData pd = this.getPageData();
        Map<String, Object> data = new HashMap<>();
        List<PageData> getRepairsList = null;
        Map<String, Object> returnMap = new HashMap<>();
        try {
            int pageNum = Integer.valueOf(pd.getString("pageNum"));
            pd.put("numS", (pageNum - 1) * Const.SHOWCOUNT);
            pd.put("numE", Const.SHOWCOUNT);
            getRepairsList = repairsService.getRepairsList(pd);

            int count = repairsService.getRepairsListCount();
            int totalPage = AppUtil.getTotalPage(count);

            // 设定isNext
            data.put("isNext", true);
            if(pageNum >= totalPage){
                data.put("isNext", false);
            }
            // 设定列表的查询结果
            data.put("getlistRepairs", getRepairsList);
            // 设定总页数
            data.put("totalPage", String.valueOf(totalPage));

            // 设定返回值
            returnMap.put("data", data);
            returnMap.put("result", true);
            returnMap.put("msg", "查询成功");
        } catch (Exception e) {
            returnMap.put("result", false);
            returnMap.put("msg", "系统异常,请稍后再试 e = " + e);
            returnMap.put("data", getRepairsList);
        }
        return returnMap;
    }
    /**
     * 获取报修的详情
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/repairsDetail", produces = {"application/json;charset=UTF-8"})
    public Object getRepairsDetailById() {
        PageData pd = this.getPageData();
        Map<String, Object> returnMap = new HashMap<>();
        try {
            returnMap = repairsService.getRepairsInfoById(pd);
            returnMap.put("result", true);
        } catch (Exception e) {
            returnMap.put("result", false);
            returnMap.put("msg", "系统异常,请稍后再试 e = " + e);
        }
        return returnMap;
    }

}

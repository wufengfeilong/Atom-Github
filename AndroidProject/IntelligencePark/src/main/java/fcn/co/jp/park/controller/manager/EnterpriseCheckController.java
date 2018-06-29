package fcn.co.jp.park.controller.manager;
import fcn.co.jp.park.controller.BaseController;
import fcn.co.jp.park.service.manager.EnterpriseCheckService;
import fcn.co.jp.park.util.PageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/enterpriseCheck")
public class EnterpriseCheckController extends BaseController {

    @Autowired
    private EnterpriseCheckService enterpriseCheckService;

    /**
     * 管理中心的企业审核列表
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getEnterpriseCheckList", produces = {"application/json;charset=UTF-8"})
    public Object selectCarWaitCheckList() {
        PageData pd = this.getPageData();
        Map<String, Object> returnMap = new HashMap<>();
        Map<String, Object> enterpriseCheckListMap = null;
        try {
            enterpriseCheckListMap =enterpriseCheckService.selectEnterpriseCheckList();
            returnMap.put("data", enterpriseCheckListMap);
            returnMap.put("result", true);
            returnMap.put("msg", "查询成功");
            returnMap.put("enterPriseCheckList", "returnMap");

        } catch (Exception e) {
            returnMap.put("result", false);
            returnMap.put("msg", "系统异常,请稍后再试");
        }
        return returnMap;
    }

    /**
     * 根据企业的id，获取企业审核详情内容
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getEnterpriseCheckDetailById", produces = {"application/json;charset=UTF-8"})
    public Object getEnterpriseCheckDetailById() {
        PageData pd = this.getPageData();
        Map<String, Object> returnMap = new HashMap<>();
        try {
            returnMap = enterpriseCheckService.getEnterpriseCheckDetailById(pd);
            returnMap.put("result", true);
        } catch (Exception e) {
            returnMap.put("result", false);
            returnMap.put("msg", "系统异常,请稍后再试 e = " + e);
        }
        return returnMap;
    }

    /**
     * 企业审核通过
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateEnterpriseCheckParkStatus")
    public Object updateEnterpriseCheckParkStatus() {
        PageData pd = this.getPageData();
        Map<String, Object> returnMap = new HashMap<>();
        int updateResult = -1;
        try {
            updateResult = enterpriseCheckService.updateEnterpriseCheckParkStatus(pd);
            returnMap.put("data", updateResult);
            returnMap.put("result", true);
            returnMap.put("msg", "企业审核通过！");
        } catch (Exception e) {
            returnMap.put("result", false);
            returnMap.put("data", updateResult);
            returnMap.put("msg", "系统异常,请稍后再试");
        }
        return returnMap;
    }

    /**
     * 企业审核驳回
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/enterpriseCheckTurnClick")
    public Object enterpriseCheckTurnClick() {
        PageData pd = this.getPageData();
        Map<String, Object> returnMap = new HashMap<>();
        int turnResult = -1;
        try {
            turnResult = enterpriseCheckService.enterpriseCheckTurnClick(pd);
            returnMap.put("data", turnResult);
            returnMap.put("result", true);
            returnMap.put("msg", "驳回成功！");
        } catch (Exception e) {
            returnMap.put("result", false);
            returnMap.put("data", turnResult);
            returnMap.put("msg", "系统异常,请稍后再试");
        }
        return returnMap;
    }

}


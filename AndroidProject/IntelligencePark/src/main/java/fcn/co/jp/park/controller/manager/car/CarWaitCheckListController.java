package fcn.co.jp.park.controller.manager.car;
import fcn.co.jp.park.controller.BaseController;
import fcn.co.jp.park.service.manager.car.CarWaitCheckListService;
import fcn.co.jp.park.util.PageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/carWaitCheck")
public class CarWaitCheckListController extends BaseController {

    @Autowired
    private CarWaitCheckListService carWaitCheckListService;

    /**
     * 管理中心的月租车辆待审批列表
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getCarWaitCheckList", produces = {"application/json;charset=UTF-8"})
    public Object selectCarWaitCheckList() {
        PageData pd = this.getPageData();
        Map<String, Object> returnMap = new HashMap<>();
        Map<String, Object> carWaitCheckListMap = null;
        try {
            carWaitCheckListMap =carWaitCheckListService.selectCarWaitCheckList();
            returnMap.put("data", carWaitCheckListMap);
            returnMap.put("result", true);
            returnMap.put("msg", "查询成功");
            returnMap.put("carWaitCheckList", "returnMap");

        } catch (Exception e) {
            returnMap.put("result", false);
            returnMap.put("msg", "系统异常,请稍后再试");
        }
        return returnMap;
    }

    /**
     * 根据车牌号的id，获取待审批的月租车辆详情内容
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/carWaitCheckDetail", produces = {"application/json;charset=UTF-8"})
    public Object getCarWaitCheckDetailById() {
        PageData pd = this.getPageData();
        Map<String, Object> returnMap = new HashMap<>();
        try {
            returnMap = carWaitCheckListService.getCarWaitCheckDetailById(pd);
            returnMap.put("result", true);
        } catch (Exception e) {
            returnMap.put("result", false);
            returnMap.put("msg", "系统异常,请稍后再试 e = " + e);
        }
        return returnMap;
    }

    /**
     * 月租车辆审核通过
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateCarWaitCheckPassStatus")
    public Object updateCarWaitCheckPassStatus() {
        PageData pd = this.getPageData();
        Map<String, Object> returnMap = new HashMap<>();
        int updateResult = -1;
        try {
            updateResult = carWaitCheckListService.updateCarWaitCheckPassStatus(pd);
            returnMap.put("data", updateResult);
            returnMap.put("result", true);
            returnMap.put("msg", "审核通过！");
        } catch (Exception e) {
            returnMap.put("result", false);
            returnMap.put("data", updateResult);
            returnMap.put("msg", "系统异常,请稍后再试");
        }
        return returnMap;
    }

    /**
     * 月租车辆审核驳回
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/onTurnClick")
    public Object onTurnClick() {
        PageData pd = this.getPageData();
        Map<String, Object> returnMap = new HashMap<>();
        int turnResult = -1;
        try {
            turnResult = carWaitCheckListService.onTurnClick(pd);
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


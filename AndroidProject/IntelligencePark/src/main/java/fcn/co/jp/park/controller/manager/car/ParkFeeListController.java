package fcn.co.jp.park.controller.manager.car;
import fcn.co.jp.park.controller.BaseController;
import fcn.co.jp.park.service.manager.car.ParkFeeListService;
import fcn.co.jp.park.util.PageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/parkFee")
public class ParkFeeListController extends BaseController {

    @Autowired
    private ParkFeeListService parkFeeListService;

    /**
     * 停车付费列表
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/parkFeeList", produces = {"application/json;charset=UTF-8"})
    public Object managerNewsList() {
        Map<String, Object> parkFeeList = new HashMap<>();
        Map<String, Object> returnMap = new HashMap<>();
        try {
            parkFeeList = parkFeeListService.selectParkFeeList();
            returnMap.put("result", true);
            returnMap.put("msg", "查询成功");
            returnMap.put("data", parkFeeList);
        } catch (Exception e) {
            returnMap.put("result", false);
            returnMap.put("msg", "系统异常,请稍后再试 e = " + e);
            returnMap.put("data", parkFeeList);
        }
        return returnMap;
    }

    /**
     * 根据车辆id，获取停车付费详情内容
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getParkFeeListDetail", produces = {"application/json;charset=UTF-8"})
    public Object getParkFeeListDetailById() {
        PageData pd = this.getPageData();
        Map<String, Object> returnMap = new HashMap<>();
        try {
            returnMap = parkFeeListService.getParkFeeListDetailById(pd);
            returnMap.put("result", true);
        } catch (Exception e) {
            returnMap.put("result", false);
            returnMap.put("msg", "系统异常,请稍后再试");
        }
        return returnMap;
    }
}
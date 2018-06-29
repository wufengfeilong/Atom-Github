package fcn.co.jp.park.controller.property;

import fcn.co.jp.park.controller.BaseController;
import fcn.co.jp.park.service.property.PropertyPayService;
import fcn.co.jp.park.util.PageData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/PropertyPay")
public class PropertyPayController extends BaseController {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PropertyPayService propertyPayService;

    /**
     * 水费缴费信息
     *
     * @return
     */
    @RequestMapping(value = "/waterPayInfo")
    @ResponseBody
    public Object getPropertyWaterPayInfo() {
        PageData pd = this.getPageData();
        Map<String, Object> resultMap = new HashMap<>();
        try {
            resultMap = propertyPayService.getWaterPayInfo(pd);
            return resultMap;
        } catch (Exception e) {
            resultMap.put("msg", "数据访问异常！");
            resultMap.put("result", false);
            return resultMap;
        }
    }

    /**
     * 电费缴费信息
     *
     * @return
     */
    @RequestMapping(value = "/electricPayInfo")
    @ResponseBody
    public Object getPropertyElectricPayInfo() {
        PageData pd = this.getPageData();
        Map<String, Object> resultMap = new HashMap<>();
        try {
            resultMap = propertyPayService.getElectricPayInfo(pd);
            return resultMap;
        } catch (Exception e) {
            resultMap.put("msg", "数据访问异常！");
            resultMap.put("result", false);
            return resultMap;
        }
    }

    /**
     * 物业费缴费信息
     *
     * @return
     */
    @RequestMapping(value = "/propertyPayInfo")
    @ResponseBody
    public Object getPropertyFeePayInfo() {
        PageData pd = this.getPageData();
        Map<String, Object> resultMap = new HashMap<>();
        try {
            resultMap = propertyPayService.getPropertyPayInfo(pd);
            return resultMap;
        } catch (Exception e) {
            resultMap.put("msg", "数据访问异常！");
            resultMap.put("result", false);
            return resultMap;
        }
    }

    /**
     * 租赁费缴费信息
     *
     * @return
     */
    @RequestMapping(value = "/rentPayInfo")
    @ResponseBody
    public Object getRentFeePayInfo() {
        PageData pd = this.getPageData();
        Map<String, Object> resultMap = new HashMap<>();
        try {
            resultMap = propertyPayService.getRentPayInfo(pd);
            return resultMap;
        } catch (Exception e) {
            resultMap.put("msg", "数据访问异常！");
            resultMap.put("result", false);
            return resultMap;
        }
    }

    /**
     * 临时停车费缴费信息
     *
     * @return
     */
    @RequestMapping(value = "/temporaryParkInfo", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Object getTemporaryParkInfo() {
        PageData pd = this.getPageData();
        Map<String, Object> resultMap = new HashMap<>();
        try {
            resultMap = propertyPayService.getTemporaryParkInfo(pd);
            return resultMap;
        } catch (Exception e) {
            resultMap.put("msg", "数据访问异常！");
            resultMap.put("result", false);
            return resultMap;
        }
    }

    /**
     * 月租停车费缴费信息
     *
     * @return
     */
    @RequestMapping(value = "/rentParkInfo", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Object getRentParkInfo() {
        PageData pd = this.getPageData();
        Map<String, Object> resultMap = new HashMap<>();
        try {
            resultMap = propertyPayService.getRentParkInfo(pd);
            return resultMap;
        } catch (Exception e) {
            resultMap.put("msg", "数据访问异常！");
            resultMap.put("result", false);
            return resultMap;
        }
    }

    /**
     * 获取绿色物管水费缴费列表
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getWaterFeePaymentList", produces = {"application/json;charset=UTF-8"})
    public Object getWaterFeePaymentList() {
        PageData pd = this.getPageData();
        Map<String, Object> resultMap = new HashMap<>();
        try {
            resultMap = propertyPayService.getWaterFeePaymentList(pd);
            return resultMap;
        } catch (Exception e) {
            resultMap.put("msg", "数据访问异常！");
            resultMap.put("result", false);
            return resultMap;
        }
    }


    /**
     * 获取绿色物管电费缴费列表
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getElectricFeePaymentList", produces = {"application/json;charset=UTF-8"})
    public Object getElectricFeePaymentList() {
        PageData pd = this.getPageData();
        Map<String, Object> resultMap = new HashMap<>();
        try {
            resultMap = propertyPayService.getElectricFeePaymentList(pd);
            return resultMap;
        } catch (Exception e) {
            resultMap.put("msg", "数据访问异常！");
            resultMap.put("result", false);
            return resultMap;
        }
    }


    /**
     * 获取绿色物管物业费缴费列表
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getPropertyFeePaymentList", produces = {"application/json;charset=UTF-8"})
    public Object getPropertyFeePaymentList() {
        PageData pd = this.getPageData();
        Map<String, Object> resultMap = new HashMap<>();
        try {
            resultMap = propertyPayService.getPropertyFeePaymentList(pd);
            return resultMap;
        } catch (Exception e) {
            resultMap.put("msg", "数据访问异常！");
            resultMap.put("result", false);
            return resultMap;
        }
    }


    @ResponseBody
    @RequestMapping(value = "/getRentFeePaymentList", produces = {"application/json;charset=UTF-8"})
    public Object getRentFeePaymentList() {
        PageData pd = this.getPageData();
        Map<String, Object> resultMap = new HashMap<>();
        try {
            resultMap = propertyPayService.getRentFeePaymentList(pd);
            return resultMap;
        } catch (Exception e) {
            resultMap.put("msg", "数据访问异常！");
            resultMap.put("result", false);
            return resultMap;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/getParkingPaymentList", produces = {"application/json;charset=UTF-8"})
    public Object getParkingPaymentList() {
        PageData pd = this.getPageData();
        Map<String, Object> resultMap = new HashMap<>();
        try {
            resultMap = propertyPayService.getParkingPaymentList(pd);
            return resultMap;
        } catch (Exception e) {
            resultMap.put("msg", "数据访问异常！");
            resultMap.put("result", false);
            return resultMap;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/getRentParkList", produces = {"application/json;charset=UTF-8"})
    public Object getRentParkList() {
        PageData pd = this.getPageData();
        Map<String, Object> resultMap = new HashMap<>();
        try {
            resultMap = propertyPayService.getRentParkList(pd);
            return resultMap;
        } catch (Exception e) {
            resultMap.put("msg", "数据访问异常！");
            resultMap.put("result", false);
            return resultMap;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/getPassedRentParkList", produces = {"application/json;charset=UTF-8"})
    public Object getPassedRentParkList() {
        PageData pd = this.getPageData();
        Map<String, Object> resultMap = new HashMap<>();
        try {
            resultMap = propertyPayService.getPassedRentParkList(pd);
            return resultMap;
        } catch (Exception e) {
            resultMap.put("msg", "数据访问异常！");
            resultMap.put("result", false);
            return resultMap;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/deleteRentParkItem", produces = {"application/json;charset=UTF-8"})
    public Object deleteRentParkItem() {
        PageData pd = this.getPageData();
        Map<String, Object> resultMap = new HashMap<>();
        try {
            resultMap = propertyPayService.deleteRentParkItem(pd);
            return resultMap;
        } catch (Exception e) {
            resultMap.put("msg", "数据访问异常！");
            resultMap.put("result", false);
            return resultMap;
        }
    }

}

package fcn.co.jp.park.controller.property;

import fcn.co.jp.park.controller.BaseController;
import fcn.co.jp.park.service.property.BusinessServices;
import fcn.co.jp.park.util.PageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * 商家服务用Controller
 */
@Controller
@RequestMapping(value = "/BusinessServices")
public class BusinessServicesController extends BaseController {

    @Autowired
    private BusinessServices businessServices;

    /**
     * 获取送水商家的List内容
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/DeliverWaterList", produces = {"application/json;charset=UTF-8"})
    public Object deliverWaterList() {
        PageData pd = this.getPageData();
        Map<String, Object> data = new HashMap<>();
        Map<String, Object> returnMap = new HashMap<>();
        try {
            int pageNum = Integer.valueOf(pd.getString("page"));
            pd.put("numS", (pageNum - 1) * 10);
            pd.put("numE", 10);
            data = businessServices.getDeliverWaterList(pd);
            int count = businessServices.getDeliverWaterListCount();
            int totalPage = getTotalPage(count);
            // 设定isNext
            data.put("isNext", true);
            if (pageNum >= totalPage) {
                data.put("isNext", false);
            }
            returnMap.put("result", true);
            returnMap.put("msg", "查询成功");
            returnMap.put("data", data);
        } catch (Exception e) {
            returnMap.put("result", false);
            returnMap.put("msg", "系统异常,请稍后再试 e = " + e);
            returnMap.put("data", data);
        }
        return returnMap;
    }

    /**
     * 获取搬家商家的List内容
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/MoveHouseList", produces = {"application/json;charset=UTF-8"})
    public Object moveHouseList() {
        PageData pd = this.getPageData();
        Map<String, Object> data = new HashMap<>();
        Map<String, Object> returnMap = new HashMap<>();
        try {
            int pageNum = Integer.valueOf(pd.getString("page"));
            pd.put("numS", (pageNum - 1) * 10);
            pd.put("numE", 10);
            data = businessServices.getMoveHouseList(pd);
            int count = businessServices.getMoveHouseListCount();
            int totalPage = getTotalPage(count);
            // 设定isNext
            data.put("isNext", true);
            if (pageNum >= totalPage) {
                data.put("isNext", false);
            }
            returnMap.put("result", true);
            returnMap.put("msg", "查询成功");
            returnMap.put("data", data);
        } catch (Exception e) {
            returnMap.put("result", false);
            returnMap.put("msg", "系统异常,请稍后再试 e = " + e);
            returnMap.put("data", data);
        }
        return returnMap;
    }

    /**
     * 获取清洁商家的List内容
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/CleanList", produces = {"application/json;charset=UTF-8"})
    public Object CleanList() {
        PageData pd = this.getPageData();
        Map<String, Object> data = new HashMap<>();
        Map<String, Object> returnMap = new HashMap<>();
        try {
            int pageNum = Integer.valueOf(pd.getString("page"));
            pd.put("numS", (pageNum - 1) * 10);
            pd.put("numE", 10);
            data = businessServices.getCleanList(pd);
            int count = businessServices.getCleanListCount();
            int totalPage = getTotalPage(count);
            // 设定isNext
            data.put("isNext", true);
            if (pageNum >= totalPage) {
                data.put("isNext", false);
            }
            returnMap.put("result", true);
            returnMap.put("msg", "查询成功");
            returnMap.put("data", data);
        } catch (Exception e) {
            returnMap.put("result", false);
            returnMap.put("msg", "系统异常,请稍后再试 e = " + e);
            returnMap.put("data", data);
        }
        return returnMap;
    }

    /**
     * 获取植物租赁商家的List内容
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/PlantLeaseList", produces = {"application/json;charset=UTF-8"})
    public Object PlantLease() {
        PageData pd = this.getPageData();
        Map<String, Object> data = new HashMap<>();
        Map<String, Object> returnMap = new HashMap<>();
        try {
            int pageNum = Integer.valueOf(pd.getString("page"));
            pd.put("numS", (pageNum - 1) * 10);
            pd.put("numE", 10);
            data = businessServices.getPlantLeaseList(pd);
            int count = businessServices.getPlantLeaseListCount();
            int totalPage = getTotalPage(count);
            // 设定isNext
            data.put("isNext", true);
            if (pageNum >= totalPage) {
                data.put("isNext", false);
            }
            returnMap.put("result", true);
            returnMap.put("msg", "查询成功");
            returnMap.put("data", data);
        } catch (Exception e) {
            returnMap.put("result", false);
            returnMap.put("msg", "系统异常,请稍后再试 e = " + e);
            returnMap.put("data", data);
        }
        return returnMap;
    }

    /***
     * 取得总页数
     * @return
     */
    private int getTotalPage(int totalResult) {
        int totalPage = 0;
        if (totalResult % 10 == 0)
            totalPage = totalResult / 10;
        else
            totalPage = totalResult / 10 + 1;
        return totalPage;
    }
}

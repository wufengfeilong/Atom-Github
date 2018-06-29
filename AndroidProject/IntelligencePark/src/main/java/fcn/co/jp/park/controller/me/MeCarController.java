package fcn.co.jp.park.controller.me;

import fcn.co.jp.park.controller.BaseController;
import fcn.co.jp.park.service.me.MeCarService;
import fcn.co.jp.park.util.PageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 *  我的车辆
 */
@Controller
@RequestMapping(value ="/CarInfo")
public class MeCarController extends BaseController{

    @Autowired
    private MeCarService meCarServices;

    /**
     * 我的车辆-获取车辆列表
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value ="/CarList", produces = {"application/json;charset=UTF-8"})
    public Object CarList(){
        Map<String, Object> data = new HashMap<>();
        Map<String, Object> returnMap = new HashMap<>();
        try {
            data = meCarServices.getCarList();
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
     * 我的车辆-编辑车辆列表
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value ="/CarEditor", produces = {"application/json;charset=UTF-8"})
    public Object CarEditor(){

        PageData pd = this.getPageData();
        pd.put("carId",pd.getString("carId"));
        pd.put("CarOwner",pd.getString("CarOwner"));
        pd.put("PlateNumber",pd.getString("PlateNumber"));
        Map<String, Object> returnMap = meCarServices.getCarEditor(pd);
        return returnMap;
    }
    /**
     * 我的车辆-追加列表车辆
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value ="/AddCar", produces = {"application/json;charset=UTF-8"})
    public Object getAddCar(){
        PageData pd = this.getPageData();
        pd.put("CarOwner",pd.getString("CarOwner"));
        pd.put("PlateNumber",pd.getString("PlateNumber"));
        pd.put("Phone",pd.getString("Phone"));
        Map<String, Object> returnMap = meCarServices.getAddCar(pd);
        return returnMap;
    }
    /**
     * 我的车辆-删除列表车辆
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value ="/deleteCar", produces = {"application/json;charset=UTF-8"})
    public Object deleteCar(){
        PageData pd = this.getPageData();
        Map<String, Object> returnMap = new HashMap<>();
        int deleteResult = -1;
        try {
            pd.put("carId",pd.getString("carId"));
            deleteResult = meCarServices.deleteCarById(pd);
            returnMap.put("data", deleteResult);
            returnMap.put("result", true);
            returnMap.put("msg", "删除成功！");
        } catch (Exception e) {
            returnMap.put("result", false);
            returnMap.put("data", deleteResult);
            returnMap.put("msg", "系统异常,请稍后再试");
        }
        return returnMap;
    }
}

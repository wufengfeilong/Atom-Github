package fcn.co.jp.park.controller.manager;

import fcn.co.jp.park.controller.BaseController;
import fcn.co.jp.park.service.manager.AdvertisingFeeEditService;
import fcn.co.jp.park.util.PageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * 广告套餐类型数据的取得与更新
 */
@Controller
@RequestMapping(value = "/advertisingInfo")
public class AdvertisingFeeEditController extends BaseController {

    @Autowired
    private AdvertisingFeeEditService advertisingFeeEditService;

    /**
     * 根据广告Id，获取广告的详情内容
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getAdvertisingSetFeeData", produces = {"application/json;charset=UTF-8"})
    public Object getAdvertisingDetailInfo() {
        Map<String, Object> returnMap = new HashMap<>();
        Map<String, Object> setFeeMap = new HashMap<>();
        try {
            returnMap = advertisingFeeEditService.getAdvertisingFeeEditData();
            setFeeMap.put("data", returnMap);
            setFeeMap.put("result", true);
        } catch (Exception e) {
            setFeeMap.put("result", false);
            setFeeMap.put("msg", "系统异常,请稍后再试");
        }
        return setFeeMap;
    }

    /**
     * 更新广告套餐费用的信息
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateAdvertisingSetFeeData", produces = {"application/json;charset=UTF-8"})
    public Object updateAdvertisingSetFeeData() {
        PageData pd = this.getPageData();
        PageData pd1 = new PageData();
        PageData pd2 = new PageData();
        PageData pd3 = new PageData();
        Map<String, Object> returnMap = new HashMap<>();
        try {
            // 更新广告套餐一费用
            pd1.put("advertiseSet1Fee", pd.get("advertiseSet1Fee"));
            int updateSet1Fee = advertisingFeeEditService.updateAdvertisingSet1FeeData(pd1);
            // 更新广告套餐二费用
            pd2.put("advertiseSet2Fee", pd.get("advertiseSet2Fee"));
            int updateSet2Fee = advertisingFeeEditService.updateAdvertisingSet2FeeData(pd2);
            // 更新广告套餐三费用
            pd3.put("advertiseSet3Fee", pd.get("advertiseSet3Fee"));
            int updateSet3Fee = advertisingFeeEditService.updateAdvertisingSet3FeeData(pd3);
            if (updateSet1Fee > 0 && updateSet2Fee > 0 && updateSet3Fee > 0) {
                returnMap.put("data", updateSet1Fee);
                returnMap.put("msg", "更新成功");
                returnMap.put("result", true);
            } else {
                returnMap.put("msg", "更新失败");
                returnMap.put("result", true);
            }
        } catch (Exception e) {
            returnMap.put("result", false);
            returnMap.put("msg", "系统异常,请稍后再试");
        }
        return returnMap;
    }
}

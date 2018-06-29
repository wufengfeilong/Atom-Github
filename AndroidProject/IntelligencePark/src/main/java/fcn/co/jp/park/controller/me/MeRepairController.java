package fcn.co.jp.park.controller.me;

import fcn.co.jp.park.controller.BaseController;
import fcn.co.jp.park.service.me.MeRepairService;
import fcn.co.jp.park.util.PageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.HashMap;
import java.util.Map;
/**
 * Created by 860117066 on 2018/04/17.
 * 查看个人报修
 */
@Controller
@RequestMapping(value = "/RepairRecord")
public class MeRepairController extends BaseController{

    @Autowired
    private MeRepairService repairService;
    /**
     * 报修列表
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/RepairRecordList", produces = {"application/json;charset=UTF-8"})
    public Object selectRepairRecordList(@RequestParam("userId") String userId) {
        PageData pd = this.getPageData();
        pd.put("userId",userId);
        Map<String, Object> returnMap = new HashMap<>();
        Map<String, Object> repairRecordListMap = null;
        try {
            repairRecordListMap =repairService.selectRepairRecordList(pd);
            returnMap.put("data", repairRecordListMap);
            returnMap.put("result", true);
            returnMap.put("msg", "查询成功");
        } catch (Exception e) {
            returnMap.put("result", false);
            returnMap.put("msg", "系统异常,请稍后再试");
        }
        return returnMap;
    }
    /**
     * 根据repairId获取报修详情内容
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/RepairRecordDetail", produces = {"application/json;charset=UTF-8"})
    public Object getRepairDetailById() {
        PageData pd = this.getPageData();
        Map<String, Object> returnMap = new HashMap<>();
        try {
            returnMap = repairService.getRepairDetailById(pd);
            returnMap.put("result", true);
        } catch (Exception e) {
            returnMap.put("result", false);
            returnMap.put("msg", "系统异常,请稍后再试");
        }
        return returnMap;
    }
}

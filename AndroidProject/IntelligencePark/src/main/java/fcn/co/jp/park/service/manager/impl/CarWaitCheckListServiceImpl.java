package fcn.co.jp.park.service.manager.impl;
import fcn.co.jp.park.mapper.manager.CarWaitCheckListMapper;
import fcn.co.jp.park.service.manager.car.CarWaitCheckListService;
import fcn.co.jp.park.util.PageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(value = "carWaitCheckListService")
public class CarWaitCheckListServiceImpl implements CarWaitCheckListService {

    @Autowired
    private CarWaitCheckListMapper carWaitCheckListMapper;

    /**
     * 获取月租车辆待审核列表
     * @return
     */
    @Override
    public Map<String, Object> selectCarWaitCheckList() {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        Map<String, Object> data = new HashMap<String, Object>();
        List<PageData> result = carWaitCheckListMapper.selectCarWaitCheckList();
        if (result != null) {
            returnMap.put("carWaitCheckList", result);
            return returnMap;
        } else {
            returnMap.put("result", false);
            returnMap.put("msg", "查询失败");
            return returnMap;
        }
    }

    /**
     * 根据车牌号的parkPay_id，获取待审批的月租车辆详情内容
     *
     * @param pd：
     * @return
     */
    @Override
    public Map<String, Object> getCarWaitCheckDetailById(PageData pd) {
        Map<String, Object> returnMap = new HashMap<>();
        Map<String, Object> data = new HashMap<>();
        PageData param = new PageData();

        String parkPay_id = pd.getString("parkPay_id");
        param.put("parkPay_id", parkPay_id); // 此处的parkPay_id要和给Mapper.xml中传值的字段名保持一致
        PageData result = carWaitCheckListMapper.getCarWaitCheckDetailById(param);
        if (result != null) {
            returnMap.put("result", true);
            returnMap.put("msg", "查询成功");
            returnMap.put("data", result);
            return returnMap;
        } else {
            returnMap.put("result", false);
            returnMap.put("msg", "查询失败");
            returnMap.put("data", data);
            return returnMap;
        }
    }

    /**
     * 月租车辆审核通过
     * @param pd
     * @return
     */
    @Override
    public int updateCarWaitCheckPassStatus(PageData pd) {
        Map<String, Object> returnMap = new HashMap<>();
        Map<String, Object> data = new HashMap<>();
        PageData param = new PageData();
        String parkPay_id = pd.getString("parkPay_id");
        param.put("parkPay_id", parkPay_id);
        param.put("parkStatus", "3");
        int result = carWaitCheckListMapper.updateCarWaitCheckPassStatus(param);
        int resultPassMessage = -1;
        String user_id = pd.getString("UserId");
        String updateUserId = pd.getString("updateUserId");
        param.put("user_id",user_id );
        param.put("updateUserId",updateUserId );
        param.put("title", "月租车辆审核通过");
        param.put("content", "您的车辆已审核成功！");
        param.put("parkPay_id",parkPay_id );
        resultPassMessage=carWaitCheckListMapper.insertPassMessage(param);
        return result;
    }

    /**
     * 月租车辆审核驳回
     * @param pd
     * @return
     */
    @Override
    public int onTurnClick(PageData pd) {
        Map<String, Object> returnMap = new HashMap<>();
        Map<String, Object> data = new HashMap<>();
        PageData param = new PageData();
        String parkPay_id = pd.getString("parkPay_id");
        String checkinfo= pd.getString("checkinfo");
        param.put("parkPay_id", parkPay_id);
        param.put("parkStatus", "2");
        param.put("checkinfo", checkinfo);
        int result = carWaitCheckListMapper.onTurnClick(param);
        int insertTurnMessage = -1;
        String user_id = pd.getString("UserId");
        String updateUserId = pd.getString("updateUserId");
        param.put("user_id",user_id );
        param.put("updateUserId",updateUserId );
        param.put("parkPay_id",parkPay_id );
        param.put("title", "月租车辆审核驳回");
        param.put("content", checkinfo);
        insertTurnMessage=carWaitCheckListMapper.insertTurnMessage(param);
        return result;
    }
}
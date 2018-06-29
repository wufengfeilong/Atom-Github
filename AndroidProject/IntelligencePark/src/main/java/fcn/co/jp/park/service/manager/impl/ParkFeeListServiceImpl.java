package fcn.co.jp.park.service.manager.impl;

import fcn.co.jp.park.mapper.manager.CarWaitCheckListMapper;
import fcn.co.jp.park.mapper.manager.ParkFeeListMapper;
import fcn.co.jp.park.service.manager.car.CarWaitCheckListService;
import fcn.co.jp.park.service.manager.car.ParkFeeListService;
import fcn.co.jp.park.util.PageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(value = "parkFeeListService")
public class ParkFeeListServiceImpl implements ParkFeeListService {

    @Autowired
    private ParkFeeListMapper parkFeeListMapper;

    @Override
    public Map<String, Object> selectParkFeeList() {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        Map<String, Object> data = new HashMap<String, Object>();
        List<PageData> result = parkFeeListMapper.selectParkFeeList();
        if (result != null) {
            returnMap.put("parkFeeList", result);
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
    public Map<String, Object> getParkFeeListDetailById(PageData pd) {
        Map<String, Object> returnMap = new HashMap<>();
        Map<String, Object> data = new HashMap<>();
        PageData param = new PageData();
      /*  String parkPay_id = pd.getString("parkPay_id");*/
        param.put("parkPay_id", 1); // 此处的carNumber要和给Mapper.xml中传值的字段名保持一致
        PageData result = parkFeeListMapper.getParkFeeListDetailById(param);
        if (result != null) {
            data.put("parkPay_id", result.get("parkPay_id"));
            data.put("carNumber", result.get("carNumber"));
            data.put("company", result.get("company"));
            returnMap.put("data", result);
            return returnMap;
        } else {
            returnMap.put("result", false);
            returnMap.put("msg", "查询失败");
            return returnMap;
        }
    }

}

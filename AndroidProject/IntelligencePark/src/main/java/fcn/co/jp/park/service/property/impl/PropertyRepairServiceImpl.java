package fcn.co.jp.park.service.property.impl;

import fcn.co.jp.park.mapper.property.PropertyRepairMapper;
import fcn.co.jp.park.service.property.PropertyRepairService;
import fcn.co.jp.park.util.PageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service(value = "PropertyRepairService")
public class PropertyRepairServiceImpl implements PropertyRepairService {

    @Autowired
    private PropertyRepairMapper propertyRepairMapper;

    /**
     * 发送报修
     * @param pd
     * @return
     */
    @Override
    public Map<String, Object> saveRepair(PageData pd) {

        int result = propertyRepairMapper.insertRepair(pd);
        Map<String, Object> returnMap = new HashMap<String, Object>();
        if (result > 0) {
            returnMap.put("result", true);
            returnMap.put("msg", "报修已提交");
        } else {
            returnMap.put("result", false);
            returnMap.put("msg", "报修提交失败");
        }
        return returnMap;
    }
}

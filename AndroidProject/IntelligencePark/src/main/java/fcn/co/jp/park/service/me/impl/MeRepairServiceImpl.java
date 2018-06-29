package fcn.co.jp.park.service.me.impl;

import fcn.co.jp.park.mapper.me.MeRepairMapper;
import fcn.co.jp.park.service.me.MeRepairService;
import fcn.co.jp.park.util.PageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * Created by 860117066 on 2018/04/17.
 * 查看个人报修列表
 */
@Service(value = "repairService")
public class MeRepairServiceImpl implements MeRepairService {
    @Autowired
    private MeRepairMapper repairMapper ;
    @Override
    public Map<String, Object> selectRepairRecordList(PageData pd) {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        Map<String, Object> data = new HashMap<String, Object>();
        List<PageData> result = repairMapper.selectRepairRecordList(pd);
        if (result != null) {
            returnMap.put("listRecordBean", result);
            return returnMap;
        } else {
            returnMap.put("result", false);
            returnMap.put("msg", "查询失败");
            return returnMap;
        }
    }
    /*
    * 查看报修列表详情
    * */
    @Override
    public Map<String, Object> getRepairDetailById(PageData pd) {
        Map<String, Object> returnMap = new HashMap<>();
        Map<String, Object> data = new HashMap<>();
        PageData param = new PageData();
        String repairId = pd.get("repairId").toString();
        param.put("repairId", repairId); // 此处的repairId要和给Mapper.xml中传值的字段名保持一致
        PageData result = repairMapper.getRepairDetailById(param);
        if (result != null) {
            data.put("repairId", result.get("repairId"));
            data.put("repairAddress", result.get("repairAddress"));
            data.put("repairName", result.get("repairName"));
            data.put("repairTime", result.get("repairTime"));
            data.put("repairContent", result.get("repairContent"));
            data.put("repairPhone", result.get("repairPhone"));
            data.put("repairPicture", result.get("repairPicture"));

            returnMap.put("result", true);
            returnMap.put("msg", "查询成功");
            returnMap.put("data", data);
            return returnMap;
        } else {
            returnMap.put("result", false);
            returnMap.put("msg", "查询失败");
            returnMap.put("data", data);
            return returnMap;
        }
    }
}

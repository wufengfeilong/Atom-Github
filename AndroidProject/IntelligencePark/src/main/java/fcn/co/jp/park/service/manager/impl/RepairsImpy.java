package fcn.co.jp.park.service.manager.impl;

import fcn.co.jp.park.mapper.manager.RepairsMapper;
import fcn.co.jp.park.service.manager.RepairsService;
import fcn.co.jp.park.util.PageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 丁胜胜  on  2018/04/16.
 * 管理中心的报修一览功能Service接口的实现类
 */
@Service(value = "Repairs")
public class RepairsImpy implements RepairsService{

    @Autowired
    private RepairsMapper mRepairsMapper;

    /**
     * 根据报修的repairsId，获取报修详情
     * @param pd：报修ID
     * @return
     */
    @Override
    public Map<String, Object> getRepairsInfoById(PageData pd) {
        Map<String, Object> returnMap = new HashMap<>();
        Map<String, Object> data = new HashMap<>();
        PageData param = new PageData();

        String repairId = pd.get("repairId").toString();
        param.put("repairId", repairId);

        PageData result = mRepairsMapper.getRepairsInfoById(param);
        if (result != null) {
            data.put("repairId", result.get("repairId"));
            data.put("repairName", result.get("repairName"));
            data.put("repairPhone", result.get("repairPhone"));
            data.put("userName", result.get("userName"));
            data.put("repairAddress", result.get("repairAddress"));
            data.put("repairPic1", result.get("repairPic1"));
            data.put("repairPic2", result.get("repairPic2"));
            data.put("repairPic3", result.get("repairPic3"));
            data.put("repairContent", result.get("repairContent"));
            data.put("repairTime", result.get("repairTime"));

            returnMap.put("result", true);
            returnMap.put("msg", "查询报修详情成功");
            returnMap.put("data", data);
            return returnMap;
        } else {
            returnMap.put("result", false);
            returnMap.put("msg", "查询报修详情失败");
            returnMap.put("data", data);
            return returnMap;
        }
    }
    /**
     * 根据报修的repairsId，删除相关报修信息
     * @param pd
     * @return
     */
    @Override
    public int deleteRepairsById(PageData pd) {
        Map<String, Object> returnMap = new HashMap<>();
        HashMap<String, Object> data;
        data = new HashMap<>();
        PageData param = new PageData();
        String repairId = pd.getString("id");
        param.put("repairId", repairId);
        int result = mRepairsMapper.deleteRepairsById(param);
        return result;
    }

    /**
     * 描述：获取报修一览里所有的List内容
     * @return
     */
    @Override
    public List<PageData> getRepairsList(PageData pd) {

        return mRepairsMapper.getRepairsList(pd);
    }

    @Override
    public Integer getRepairsListCount() {
        Integer result = mRepairsMapper.getRepairsListCount();
        return result;
    }
}
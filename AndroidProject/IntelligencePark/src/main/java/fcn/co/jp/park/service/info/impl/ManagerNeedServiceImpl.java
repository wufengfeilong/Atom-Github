package fcn.co.jp.park.service.info.impl;

import fcn.co.jp.park.mapper.info.InfoDemandMapper;
import fcn.co.jp.park.mapper.info.ManagerNeedMapper;
import fcn.co.jp.park.service.info.ManagerNeedService;
import fcn.co.jp.park.util.PageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(value = "managerNeedService")
public class ManagerNeedServiceImpl implements ManagerNeedService {
    @Autowired
    private ManagerNeedMapper managerNeedMapper;//这里会报错，但是并不会影响

    /**
     * 企业需求列表
     * @param pd
     * @return
     * @throws Exception
     */
    @Override
    public List<PageData> getDemandList(PageData pd) throws Exception {
        return (List<PageData>) managerNeedMapper.findDemandList(pd);
    }

    /**
     * 企业需求数量
     * @param pd
     * @return
     * @throws Exception
     */
    @Override
    public int getDemandCount(PageData pd) throws Exception {
        return (Integer) managerNeedMapper.findDemandCount(pd);
    }


    /**
     * 根据需求id，删除相应的需求条目
     * @param pd
     * @return
     */
    @Override
    public int deleteDemandById(PageData pd) {
        return managerNeedMapper.deleteDemandById(pd);
    }

    /**
     * 发布需求增加页面
     * @param pd
     * @return
     */
    @Override
    public int insertDemand(PageData pd) {
        int result = managerNeedMapper.insertDemand(pd);
        return  result;
    }

    /**
     * 发布需求中需求详情
     * @param pd
     * @return
     * @throws Exception
     */
    @Override
    public Map<String, Object> getDemandDetail(PageData pd) throws Exception {
        return managerNeedMapper.getDemandDetail(pd);
    }

    /**
     * 发布需求详情编辑页面
     * @param pd
     * @return
     */
    @Override
    public int managerDemandEdit(PageData pd) {
        int result = managerNeedMapper.managerDemandEdit(pd);
        return  result;
    }


}

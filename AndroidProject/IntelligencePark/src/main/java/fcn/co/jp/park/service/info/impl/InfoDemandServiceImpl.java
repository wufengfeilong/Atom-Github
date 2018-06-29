package fcn.co.jp.park.service.info.impl;

import fcn.co.jp.park.mapper.info.InfoDemandMapper;
import fcn.co.jp.park.mapper.info.InfoEnterpriseMapper;
import fcn.co.jp.park.service.info.InfoDemandService;
import fcn.co.jp.park.util.PageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service(value = "demandAppService")
public class InfoDemandServiceImpl implements InfoDemandService {
    @Autowired
    private InfoDemandMapper infoDemandMapper;//这里会报错，但是并不会影响

    /**
     * 企业需求列表
     * @param pd
     * @return
     * @throws Exception
     */
    @Override
    public List<PageData> getDemandList(PageData pd) throws Exception {
        return (List<PageData>) infoDemandMapper.findDemandList(pd);
    }

    /**
     * 企业需求数量
     * @param pd
     * @return
     * @throws Exception
     */
    @Override
    public int getDemandCount(PageData pd) throws Exception {
        return (Integer) infoDemandMapper.findDemandCount(pd);
    }

    /**
     * 企业需求详情
     * @param pd
     * @return
     * @throws Exception
     */
    @Override
    public Map<String, Object> getDemandInfo(PageData pd) throws Exception {
        return infoDemandMapper.getDemandInfo(pd);
    }

}

package fcn.co.jp.park.service.info.impl;

import fcn.co.jp.park.mapper.info.InfoEnterpriseMapper;
import fcn.co.jp.park.mapper.info.InfoNewsMapper;
import fcn.co.jp.park.service.info.InfoEnterpriseService;
import fcn.co.jp.park.util.PageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service(value = "enterpriseAppService")
public class InfoEnterpriseServiceImpl implements InfoEnterpriseService {

    @Autowired
    private InfoEnterpriseMapper infoEnterpriseMapper;//这里会报错，但是并不会影响

    /**
     * 获取企业列表
     * @param pd
     * @return
     * @throws Exception
     */
    @Override
    public List<PageData> getBusinessList(PageData pd) throws Exception {
        return (List<PageData>) infoEnterpriseMapper.findBussinessList(pd);
    }

    /**
     * 获取企业数量
     * @param pd
     * @return
     * @throws Exception
     */
    @Override
    public int getBusinesCount(PageData pd) throws Exception {
        return (Integer) infoEnterpriseMapper.findBusinessCount(pd);
    }

    /**
     * 企业信息详情
     * @param pd
     * @return
     * @throws Exception
     */
    @Override
    public Map<String, Object> getBusinessInfo(PageData pd) throws Exception {
        return infoEnterpriseMapper.getBusinessInfo(pd);
    }

    /**
     * 获取公司上传的照片
     * @param pd
     * @return
     * @throws Exception
     */
    @Override
    public List<PageData> getComPictUpload(PageData pd) throws Exception {
        return (List<PageData>)infoEnterpriseMapper.getComPictUpload(pd);
    }
}

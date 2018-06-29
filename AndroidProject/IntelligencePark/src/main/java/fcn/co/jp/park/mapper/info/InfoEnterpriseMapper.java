package fcn.co.jp.park.mapper.info;

import fcn.co.jp.park.util.PageData;

import java.util.List;
import java.util.Map;

public interface InfoEnterpriseMapper {

    /**
     * 企业列表
     * @param pd
     * @return
     */
    List<PageData> findBussinessList(PageData pd);

    /**
     * 企业数量
     * @param pd
     * @return
     */
    int findBusinessCount(PageData pd);

    /**
     * 企业详情
     * @param pd
     * @return
     */
    Map<String, Object> getBusinessInfo(PageData pd);

    /**
     * 获取公司上传的照片
     * @param pd
     * @return
     */
    List<PageData> getComPictUpload(PageData pd);
}

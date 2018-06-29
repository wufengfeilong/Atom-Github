package fcn.co.jp.park.service.info;

import fcn.co.jp.park.util.PageData;

import java.util.List;
import java.util.Map;

public interface InfoEnterpriseService {
    /**
     * 获取企业列表
     * @param pd
     * @return
     * @throws Exception
     */
    List<PageData> getBusinessList (PageData pd) throws Exception;

    /**
     * 获取企业数量
     * @param pd
     * @return
     * @throws Exception
     */
    int getBusinesCount (PageData pd) throws Exception;

    /**
     * 企业详情
     * @param pd
     * @return
     * @throws Exception
     */
    Map<String, Object> getBusinessInfo(PageData pd) throws Exception;

    /**
     * 获取公司上传的照片
     * @param pd
     * @return
     * @throws Exception
     */
    List<PageData> getComPictUpload(PageData pd)throws Exception;
}

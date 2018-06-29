package fcn.co.jp.park.service.manager;
import fcn.co.jp.park.util.PageData;

import java.util.Map;

public interface EnterpriseCheckService {

    /**
     *企业审核列表
     * @return
     */
    Map<String, Object> selectEnterpriseCheckList();

    /**
     * 根据企业ID，获取企业审核详情
     * @param pd
     * @return
     */
    Map<String, Object> getEnterpriseCheckDetailById(PageData pd);

    /**
     * 企业审核通过
     * @param pd
     * @return
     */
    int updateEnterpriseCheckParkStatus(PageData pd);

    /**
     *企业驳回
     * @param pd
     * @return
     */
    int enterpriseCheckTurnClick(PageData pd);
}

package fcn.co.jp.park.service.info;

import fcn.co.jp.park.util.PageData;

import java.util.List;
import java.util.Map;

public interface ManagerNeedService {
    /**
     * 获取需求列表
     * @param pd
     * @return
     * @throws Exception
     */
    List<PageData> getDemandList (PageData pd) throws Exception;

    /**
     * 获取需求数量
     * @param pd
     * @return
     * @throws Exception
     */
    int getDemandCount (PageData pd) throws Exception;

    /**
     * 根据需求Id删除相应的企业需求条目
     * @param pd
     * @return
     */
    int deleteDemandById(PageData pd);

    /**
     * 追加需求
     * @param pd
     * @return
     */
    int insertDemand(PageData pd);

    /**
     * 发布需求详情
     * @param pd
     * @return
     * @throws Exception
     */
    Map<String, Object> getDemandDetail(PageData pd) throws Exception;

    /**
     * 需求详情编辑
     * @param pd
     * @return
     * @throws Exception
     */
    int managerDemandEdit(PageData pd) throws Exception;




}

package fcn.co.jp.park.mapper.info;

import fcn.co.jp.park.util.PageData;

import java.util.List;
import java.util.Map;

public interface ManagerNeedMapper {
    /**
     * 企业需求列表
     * @param pd
     * @return
     */
    List<PageData> findDemandList(PageData pd);

    /**
     * 企业需求数量
     * @param pd
     * @return
     */
    int findDemandCount(PageData pd);

    /**
     *根据id，删除相应的条目
     * @param pd
     * @return
     */
    int deleteDemandById(PageData pd);

    /**
     * 插入需求
     * @param pd
     * @return
     */
    int insertDemand(PageData pd);

    /**
     * 发布需求中需求详情
     * @param pd
     * @return
     */
    Map<String, Object> getDemandDetail(PageData pd);

    /**
     * 需求详情编辑
     * @param pd
     * @return
     */
    int managerDemandEdit(PageData pd);



}

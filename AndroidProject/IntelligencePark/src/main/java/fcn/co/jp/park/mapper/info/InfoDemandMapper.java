package fcn.co.jp.park.mapper.info;

import fcn.co.jp.park.util.PageData;

import java.util.List;
import java.util.Map;

public interface InfoDemandMapper {

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
     * 企业需求详情
     * @param pd
     * @return
     */
    Map<String, Object> getDemandInfo(PageData pd);

}

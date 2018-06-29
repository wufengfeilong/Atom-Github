package fcn.co.jp.park.service.info;

import fcn.co.jp.park.util.PageData;

import java.util.List;
import java.util.Map;

public interface InfoDemandService {
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
     * 需求详情
     * @param pd
     * @return
     * @throws Exception
     */
    Map<String, Object> getDemandInfo(PageData pd) throws Exception;
}

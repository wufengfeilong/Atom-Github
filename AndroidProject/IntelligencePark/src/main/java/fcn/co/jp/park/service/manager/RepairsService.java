package fcn.co.jp.park.service.manager;

import fcn.co.jp.park.util.PageData;

import java.util.List;
import java.util.Map;

/**
 * Created by 丁胜胜 on 2018/04/16.
 * 描述：管理中心的报修一览用Service接口
 */
public interface RepairsService {

    /**
     * 获取报修一览列表信息
     * @return
     */
    List<PageData> getRepairsList(PageData pd);

    /**
     * 根据报修Id获取报修详情内容
     * @param pd
     * @return
     */
    Map<String, Object> getRepairsInfoById(PageData pd);

    /**
     * 根据报修Id删除相应的报修信息
     * @param pd
     * @return
     */
    int deleteRepairsById(PageData pd);

    /**
     * 获取报修列表总件数
     * @return
     */
    Integer getRepairsListCount();

}

package fcn.co.jp.park.mapper.manager;


import fcn.co.jp.park.util.PageData;

import java.util.List;

/**
 * Created by 丁胜胜 on 2018/04/16.
 * 描述：管理中心报修一览用Mapper
 */
public interface RepairsMapper {

    /**
     *根据报修的id，删除相应的报修条目
     * @param pd
     * @return
     */
    int deleteRepairsById(PageData pd);

    /**
     * 获取报修一览列表信息
     * @return
     */
    List<PageData> getRepairsList(PageData pd);

    /**
     * 根据报修的id，获取报修详情
     * @param pd
     * @return
     */
    PageData getRepairsInfoById(PageData pd);

    /**
     * 获取报修列表总件数
     * @return
     */
    Integer getRepairsListCount( );
}

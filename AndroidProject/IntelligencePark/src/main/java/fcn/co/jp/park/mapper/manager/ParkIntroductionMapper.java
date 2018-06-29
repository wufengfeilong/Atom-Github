package fcn.co.jp.park.mapper.manager;

import fcn.co.jp.park.util.PageData;

public interface ParkIntroductionMapper {

    /**
     * 获取园区简介内容
     * @param pd
     * @return
     */
    PageData selectParkInfo(PageData pd);

    /**
     * 更新园区简介内容
     * @param pd
     * @return
     */
    int updateParkInfo(PageData pd);

    /**
     * 新建园区简介内容
     * @param pd
     * @return
     */
    int insertParkInfo(PageData pd);
}

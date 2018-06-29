package fcn.co.jp.park.mapper.property;

import fcn.co.jp.park.util.PageData;

public interface PropertyRepairMapper {

    /**
     * 追加报修内容
     * @return
     */
    int insertRepair(PageData pd);
}

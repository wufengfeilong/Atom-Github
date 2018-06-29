package fcn.co.jp.park.mapper.me;

import fcn.co.jp.park.util.PageData;

import java.util.List;

public interface MeCarMapper {

    /**
     * 查询我的车辆列表内容
     *
     * @return
     */
    List<PageData> getCarList();

    /**
     * 编辑我的车辆列表内容
     *
     * @return
     */
    int updateCarList(PageData pd);

    /**
     * 追加我的车辆列表内容
     *
     * @return
     */
    int insertCarList(PageData pd);

    /**
     * 删除我的车辆列表内容
     *
     * @return
     */
    int deleteCarById(PageData pd);

    /**
     * 获取物业费列表总件数
     *
     * @return
     */
    int getCarListCount();
}

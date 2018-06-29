package fcn.co.jp.park.service.manager.impl;

import fcn.co.jp.park.mapper.manager.AdvertisingFeeEditMapper;
import fcn.co.jp.park.service.manager.AdvertisingFeeEditService;
import fcn.co.jp.park.util.PageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 广告套餐类型数据的取得与更新
 */
@Service(value = "advertisingFeeEditService")
public class AdvertisingFeeEditServiceImpl implements AdvertisingFeeEditService {

    @Autowired
    private AdvertisingFeeEditMapper mAdvertisingFeeEditMapper;

    /**
     * 查询套餐类型的内容
     *
     * @return
     */
    @Override
    public Map<String, Object> getAdvertisingFeeEditData() {
        Map<String, Object> returnMap = new HashMap<>();
        List<PageData> result = mAdvertisingFeeEditMapper.getAdvertisingFeeEditData();
        returnMap.put("advertisingFeeList", result);
        return returnMap;
    }

    /**
     * 更新广告套餐一费用的信息
     *
     * @param pd
     * @return
     */
    @Override
    public int updateAdvertisingSet1FeeData(PageData pd) {
        return mAdvertisingFeeEditMapper.updateAdvertisingSet1FeeData(pd);
    }

    /**
     * 更新广告套餐二费用的信息
     *
     * @param pd
     * @return
     */
    @Override
    public int updateAdvertisingSet2FeeData(PageData pd) {
        return mAdvertisingFeeEditMapper.updateAdvertisingSet2FeeData(pd);
    }

    /**
     * 更新广告套餐三费用的信息
     *
     * @param pd
     * @return
     */
    @Override
    public int updateAdvertisingSet3FeeData(PageData pd) {
        return mAdvertisingFeeEditMapper.updateAdvertisingSet3FeeData(pd);
    }
}

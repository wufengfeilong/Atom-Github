package fcn.co.jp.park.service.manager.impl;

import fcn.co.jp.park.mapper.manager.AdvertisingInfoMapper;
import fcn.co.jp.park.service.manager.AdvertisingInfoService;
import fcn.co.jp.park.util.AppUtil;
import fcn.co.jp.park.util.Const;
import fcn.co.jp.park.util.PageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 管理中心的广告管理功能用Service接口的实现类
 */
@Service(value = "advertisingInfoService")
public class AdvertisingInfoServiceImpl implements AdvertisingInfoService{

    @Autowired
    private AdvertisingInfoMapper advertisingInfoMapper;

    /**
     * 获取待审批的广告列表内容
     * @return
     */
    @Override
    public Map<String, Object> getAdvertisingApprovalList(PageData pd) {
        Map<String,Object> result = new HashMap<>();
        Map<String,Object> returnMap = new HashMap<>();
        // 待审批广告列表内容暂时先不用分页功能
//        int pageNum = Integer.valueOf(pd.getString("pageNum"));
//        pd.put("numS", (pageNum - 1) * Const.SHOWCOUNT);
//        pd.put("numE",Const.SHOWCOUNT);
        int count = advertisingInfoMapper.selectAdvertisingApprovalCount();

        int totalPage = AppUtil.getTotalPage(count);
//        if(pageNum >=  totalPage){
//            result.put("isNext", false);
//        }else{
//            result.put("isNext", true);
//        }

        List<PageData> advertisingApprovalList =  advertisingInfoMapper.getAdvertisingApprovalList(pd);
        result.put("listAdvertisingApproval", advertisingApprovalList);
        returnMap.put("result", true);
        if (advertisingApprovalList.size() > 0) {
            returnMap.put("data", result);
            returnMap.put("totalPage", String.valueOf(totalPage));
            returnMap.put("msg", "获取待审批广告位列表数据成功");
        } else {
            returnMap.put("msg", "暂时没有广告信息");
        }
        return returnMap;
    }

    /**
     * 获取审批完了的广告列表内容
     * @return
     */
    @Override
    public Map<String, Object> getAdvertisingList(PageData pd) {
        Map<String,Object> result = new HashMap<>();
        Map<String,Object> returnMap = new HashMap<>();
        int pageNum = Integer.valueOf(pd.getString("pageNum"));
        pd.put("numS", (pageNum - 1) * Const.SHOWCOUNT);
        pd.put("numE",Const.SHOWCOUNT);
        int count = advertisingInfoMapper.getAdvertisementInfoCount();

        int totalPage = AppUtil.getTotalPage(count);
        if(pageNum >=  totalPage){
            result.put("isNext", false);
        }else{
            result.put("isNext", true);
        }

        List<PageData> advertisingList =  advertisingInfoMapper.getAdvertisingList();
        result.put("listAdvertisingApproval", advertisingList);
        returnMap.put("result", true);
        if (advertisingList.size() > 0) {
            returnMap.put("data", result);
            returnMap.put("totalPage", String.valueOf(totalPage));
            returnMap.put("msg", "获取房屋列表数据成功");
        } else {
            returnMap.put("msg", "暂时没有广告信息");
        }
        return returnMap;
    }

    /**
     * 根据广告Id，获取广告的详情内容
     * @param pd
     * @return
     */
    @Override
    public Map<String, Object> getAdvertisingDetailInfoById(PageData pd) {
        Map<String, Object> returnMap = new HashMap<>();
        PageData result = advertisingInfoMapper.getAdvertisingDetailInfoById(pd);
        if (result != null) {
            returnMap.put("result", true);
            returnMap.put("msg", "查询成功");
            returnMap.put("data", result);
        } else {
            returnMap.put("result", false);
            returnMap.put("msg", "查询失败");
            returnMap.put("data", result);
        }
        return returnMap;
    }

    /**
     * 追加广告
     * @param pd
     * @return
     */
    @Override
    public Map<String, Object> addAdvertisement(PageData pd) {
        Map<String, Object> returnMap = new HashMap<>();
        int result = advertisingInfoMapper.addAdvertisement(pd);
        if (result > 0) {
            returnMap.put("result", true);
            returnMap.put("msg", "提交成功，请等待管理员审核");
            returnMap.put("data", result);
        } else {
            returnMap.put("result", false);
            returnMap.put("msg", "提交失败，请重新提交");
        }
        return returnMap;
    }

    /**
     * 点击“通过”按钮后，更新广告的信息、并向Message表中插入展示给用户的信息
     * @return
     */
    @Override
    public int updateAdvertisingInfoByPassOn(PageData pd) {
        int result1= advertisingInfoMapper.updateAdvertisingInfoByPassOn(pd);
        int result2 = advertisingInfoMapper.insertOKMsg(pd);
        if (result1 > 0 && result2 > 0) {
            return 1;
        } else {
            return -1;
        }
    }

    /**
     * 点击“拒绝”按钮后，更新广告的信息
     * @return
     */
    @Override
    public int updateAdvertisingInfoByRefuse(PageData pd) {
        int result1= advertisingInfoMapper.updateAdvertisingInfoByRefuse(pd);
        int result2 = advertisingInfoMapper.insertNGMsg(pd);
        if (result1 > 0 && result2 > 0) {
            return 1;
        } else {
            return -1;
        }
    }
}
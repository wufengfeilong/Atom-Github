package fcn.co.jp.park.controller.manager;

import fcn.co.jp.park.controller.BaseController;
import fcn.co.jp.park.service.manager.AdvertisingInfoService;
import fcn.co.jp.park.util.Const;
import fcn.co.jp.park.util.FileUtil;
import fcn.co.jp.park.util.PageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 管理中心的广告管理功能用Controller
 */
@Controller
@RequestMapping(value = "/advertisingInfo")
public class AdvertisingInfoController extends BaseController {

    @Autowired
    private AdvertisingInfoService advertisingInfoService;

    @Autowired
    private FileUtil fileUtil;

    /**
     * 获取待审批的广告列表
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getAdvertisingApprovalList", produces = {"application/json;charset=UTF-8"})
    public Object getAdvertisingApprovalList() {
        PageData pd = this.getPageData();
        List<PageData> advertisingList = null;
        Map<String, Object> returnMap = new HashMap<>();
        try {
            returnMap = advertisingInfoService.getAdvertisingApprovalList(pd);
        } catch (Exception e) {
            returnMap.put("result", false);
            returnMap.put("msg", "系统异常,请稍后再试 e = " + e);
            returnMap.put("data", advertisingList);
        }
        return returnMap;
    }

    /**
     * 获取已审批完了的广告列表
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getAdvertisingList", produces = {"application/json;charset=UTF-8"})
    public Object getAdvertisingList() {
        PageData pd = this.getPageData();
        Map<String, Object> data = new HashMap<String, Object>();
        List<PageData> advertisingList = null;
        Map<String, Object> returnMap = new HashMap<>();
        try {
            returnMap = advertisingInfoService.getAdvertisingList(pd);
        } catch (Exception e) {
            returnMap.put("result", false);
            returnMap.put("msg", "系统异常,请稍后再试 e = " + e);
            returnMap.put("data", advertisingList);
        }
        return returnMap;
    }

    /**
     * 根据广告Id，获取广告的详情内容
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getAdvertisingInfo", produces = {"application/json;charset=UTF-8"})
    public Object getAdvertisingDetailInfo() {
        PageData pd = this.getPageData();
        Map<String, Object> returnMap = new HashMap<>();
        try {
            returnMap = advertisingInfoService.getAdvertisingDetailInfoById(pd);
            returnMap.put("result", true);
        } catch (Exception e) {
            returnMap.put("result", false);
            returnMap.put("msg", "系统异常,请稍后再试 e = " + e);
        }
        return returnMap;
    }

    /**
     * 追加广告
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addAdvertisement", produces = {"application/json;charset=UTF-8"})
    public Object addAdvertisement(HttpServletRequest request) {
        MultiValueMap<String, MultipartFile> files = ((StandardMultipartHttpServletRequest) request).getMultiFileMap();
        PageData pd = this.getPageData();
        String fileId = null;
        if (files != null) {
            for (String key : files.keySet()) {
                if ("advertisementPicture".equals(key)) {
                    fileId = fileUtil.fileUp(Const.ADVERTISEMENT_PICTURE, (MultipartFile) files.get(key).get(0));
                    pd.put("advertising_img", fileId);
                    break;
                }
            }
        }
        Map<String, Object> resultMap = advertisingInfoService.addAdvertisement(pd);
        return resultMap;
    }

    /**
     * 点击“通过”按钮后，更新广告的信息
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateAdvertisingInfoByPassOn", produces = {"application/json;charset=UTF-8"})
    public Object updateAdvertisingInfoByPassOn() {
        PageData pd = this.getPageData();
        Map<String, Object> returnMap = new HashMap<>();
        int updateResult = -1;
        try {
            updateResult = advertisingInfoService.updateAdvertisingInfoByPassOn(pd);
            if (updateResult > 0) {
                returnMap.put("data", updateResult);
                returnMap.put("msg", "通过");
                returnMap.put("result", true);
            } else {
                returnMap.put("data", updateResult);
                returnMap.put("msg", "操作失败，请重新操作。");
                returnMap.put("result", false);
            }
        } catch (Exception e) {
            returnMap.put("data", updateResult);
            returnMap.put("result", false);
            returnMap.put("msg", "系统异常,请稍后再试");
        }
        return returnMap;
    }

    /**
     * 点击“拒绝”按钮后，更新广告的信息
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateAdvertisingInfoByRefuse", produces = {"application/json;charset=UTF-8"})
    public Object updateAdvertisingInfoByRefuse() {
        PageData pd = this.getPageData();
        Map<String, Object> returnMap = new HashMap<>();
        int updateResult = -1;
        try {
            updateResult = advertisingInfoService.updateAdvertisingInfoByRefuse(pd);
            returnMap.put("data", updateResult);
            returnMap.put("msg", "拒绝");
            returnMap.put("result", true);
        } catch (Exception e) {
            returnMap.put("data", updateResult);
            returnMap.put("msg", "系统异常,请稍后再试");
            returnMap.put("result", false);
        }
        return returnMap;
    }
}

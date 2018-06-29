package fcn.co.jp.park.controller.me;

import fcn.co.jp.park.controller.BaseController;
import fcn.co.jp.park.model.Picture;
import fcn.co.jp.park.service.me.CompanyService;
import fcn.co.jp.park.util.Const;
import fcn.co.jp.park.util.FileUtil;
import fcn.co.jp.park.util.PageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * create by 860115039
 * date      2018/04/13
 * time      13:46
 * 个人中心-企业认证，企业信息编辑
 */
@Controller
@RequestMapping(value = "/company")
public class CompanyController extends BaseController {
    @Autowired
    CompanyService companyService;
    @Autowired
    private FileUtil fileUtil;

    /**
     * 企业信息上传认证用
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/saveCompanyAuthInfo", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Object saveCompanyAuthInfo(HttpServletRequest request) {
        MultiValueMap<String, MultipartFile> files = ((StandardMultipartHttpServletRequest) request).getMultiFileMap();
        PageData pd = this.getPageData();
        for (String key : files.keySet()) {
            if ("license1".equals(key)) {
                String fileid = fileUtil.fileUp(Const.COMPANY_PICTURE, files.get(key).get(0));
                pd.put("imgId", fileid);
            } else {
                String fileid = fileUtil.fileUp(Const.COMPANY_PICTURE, files.get(key).get(0));
                pd.put("logoId", fileid);
            }
        }

        Map<String, Object> resultMap = null;
        //申请失败，重新申请
        if ("2".equals(pd.get("flg"))) {
            resultMap = companyService.updateCompanyInfo(pd);
        } else {
            resultMap = companyService.insertCompanyInfo(pd);
        }
        return resultMap;
    }


    /**
     * 企业认证结果查询
     *
     * @return
     */
    @RequestMapping(value = "/selectAuthResult")
    @ResponseBody
    public Object selectAuthResult(@RequestParam("userId") String userId) {
        int flg = companyService.selectCompanyAuthResult(userId);
        Map<String, Object> returnMap = new HashMap();

        returnMap.put("result", true);
        returnMap.put("msg", flg);
        return returnMap;

    }

    /**
     * 企业信息编辑-企业轮播图片上传
     *
     * @return
     */
    @RequestMapping(value = "/saveBannersInfo", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Object saveBannersInfo(HttpServletRequest request) {
        MultiValueMap<String, MultipartFile> files = ((StandardMultipartHttpServletRequest) request).getMultiFileMap();
        PageData pd = this.getPageData();
        int result = 0;
        Map<String, Object> resultMap = new HashMap<>();
        for (String key : files.keySet()) {
            String path = fileUtil.fileUpReturnPath(Const.COMPANY_PICTURE, files.get(key).get(0));
            pd.put("picture_path", path);
            if ("0".equals(key)) {
                result = companyService.insertBanners(pd);
            } else {
                pd.put("id", key);
                result = companyService.updateBanners(pd);
            }
        }
        if (result > 0) {
            resultMap.put("result", true);
            resultMap.put("msg", "保存成功");
        } else {
            resultMap.put("result", false);
            resultMap.put("msg", "保存失败");
        }
        return resultMap;
    }

    /**
     * 更新企业介绍
     *
     * @return
     */
    @RequestMapping(value = "/updateCompanyIntroduce", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Object updateCompanyIntroduce(@RequestParam("userId") String userId, @RequestParam("introduce") String introduce) {
        PageData pd = this.getPageData();
        pd.put("userId", userId);
        pd.put("introduce", introduce);
        Map<String, Object> resultMap = new HashMap<>();
        int result = companyService.updateIntroduce(pd);
        if (result > 0) {
            resultMap.put("result", true);
            resultMap.put("msg", "保存成功");
        } else {
            resultMap.put("result", false);
            resultMap.put("msg", "保存失败");
        }
        return resultMap;
    }


    /**
     * 获取公司详细介绍
     *
     * @return
     */
    @RequestMapping(value = "/getCompanyIntroduceInfo")
    @ResponseBody
    public Object getCompanyIntroduceInfo(@RequestParam("userId") String userId) {
        String introduce = companyService.getCompanyIntroduceInfo(userId);
        Map<String, Object> returnMap = new HashMap();
        returnMap.put("result", true);
        returnMap.put("data", introduce);
        return returnMap;
    }


    /**
     * 获取公司图片
     *
     * @return
     */
    @RequestMapping(value = "/getCompanyPictureInfo")
    @ResponseBody
    public Object getCompanyPictureInfo(@RequestParam("userId") String userId) {
        List<Picture> list = companyService.getCompanyPictureInfo(userId);
        Map<String, Object> returnMap = new HashMap();
        returnMap.put("result", true);
        returnMap.put("data", list);
        return returnMap;

    }
}

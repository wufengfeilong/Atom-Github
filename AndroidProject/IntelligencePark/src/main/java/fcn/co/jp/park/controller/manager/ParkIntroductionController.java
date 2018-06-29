package fcn.co.jp.park.controller.manager;

import fcn.co.jp.park.controller.BaseController;
import fcn.co.jp.park.service.manager.ParkIntroductionService;
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
import java.util.Map;

@Controller
@RequestMapping(value = "/parkInfo")
public class ParkIntroductionController extends BaseController {

    @Autowired
    private FileUtil fileUtil;
    @Autowired
    private ParkIntroductionService parkIntroductionService;

    /**
     * 查询园区简介的内容
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getParkIntroductionInfo", produces = {"application/json;charset=UTF-8"})
    public Object getParkIntroductionInfo() {
        PageData pd = this.getPageData();
        Map<String, Object> returnMap = new HashMap<>();
        try {
            returnMap = parkIntroductionService.selectParkInfo(pd);
            returnMap.put("result", true);
            returnMap.put("msg", "查询成功");
        } catch (Exception e) {
            returnMap.put("result", false);
            returnMap.put("msg", "系统异常,请稍后再试");
        }
        return returnMap;
    }

    /**
     * 上传并保存园区简介的图片、内容
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/uploadParkInfoImg", produces = {"application/json;charset=UTF-8"})
    public Object uploadParkImg(HttpServletRequest request) {
        MultiValueMap<String, MultipartFile> files = ((StandardMultipartHttpServletRequest) request).getMultiFileMap();
        PageData pd = this.getPageData();

        String fileId = null;
        if (files != null) {
            for (String key : files.keySet()) {
                if ("img1".equals(key)) {
                    fileId = fileUtil.fileUp(Const.COMPANY_PICTURE, files.get(key).get(0));
                    pd.put("img1", fileId);
                } else if ("img2".equals(key)) {
                    fileId = fileUtil.fileUp(Const.COMPANY_PICTURE, (MultipartFile) files.get(key).get(0));
                    pd.put("img2", fileId);
                } else if ("img3".equals(key)) {
                    fileId = fileUtil.fileUp(Const.COMPANY_PICTURE, (MultipartFile) files.get(key).get(0));
                    pd.put("img3", fileId);
                }
            }
        }
        int updateResult = parkIntroductionService.updateParkInfo(pd);
        Map<String, Object> resultMap = new HashMap<>();
        if (updateResult > 0) {
            resultMap.put("data", updateResult);
            resultMap.put("result", true);
            resultMap.put("msg", "更新成功！");
        } else {
            resultMap.put("data", updateResult);
            resultMap.put("result", false);
            resultMap.put("msg", "更新失败！");
        }
        return resultMap;
    }
}
package fcn.co.jp.park.controller.property;

import fcn.co.jp.park.controller.BaseController;
import fcn.co.jp.park.service.property.PropertyRepairService;
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

/**
 * 报修用controller
 */
@Controller
@RequestMapping(value = "/RepairInfo")
public class PropertyRepairController extends BaseController {

    @Autowired
    private FileUtil fileUtil;

    @Autowired
    private PropertyRepairService PropertyRepairService;

    /**
     * 追加报修信息
     *
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/sendRepair", produces = {"application/json;charset=UTF-8"})
    public Object sendRepair(HttpServletRequest request) {
        MultiValueMap<String, MultipartFile> files = ((StandardMultipartHttpServletRequest) request).getMultiFileMap();
        PageData pd = this.getPageData();
        String fileid = null;
        if (files != null) {
            for (String key : files.keySet()) {
                if ("repairPicture1".equals(key)) {
                    fileid = fileUtil.fileUp(Const.REPAIR_PICTURE, (MultipartFile) files.get(key).get(0));
                    pd.put("repairPicture1", fileid);
                } else if ("repairPicture2".equals(key)) {
                    fileid = fileUtil.fileUp(Const.REPAIR_PICTURE, (MultipartFile) files.get(key).get(0));
                    pd.put("repairPicture2", fileid);
                } else if ("repairPicture3".equals(key)) {
                    fileid = fileUtil.fileUp(Const.REPAIR_PICTURE, (MultipartFile) files.get(key).get(0));
                    pd.put("repairPicture3", fileid);
                }
            }
        }
        Map<String, Object> resultMap = PropertyRepairService.saveRepair(pd);
        return resultMap;
    }
}

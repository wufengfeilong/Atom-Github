package fcn.co.jp.park.controller.common;

import fcn.co.jp.park.controller.BaseController;
import fcn.co.jp.park.service.common.FileService;
import fcn.co.jp.park.util.DateUtil;
import fcn.co.jp.park.util.FileUtil;
import fcn.co.jp.park.util.PageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * create by 860115039
 * date      2018/04/13
 * time      13:46
 */
@Controller
@RequestMapping(value = "/file")
public class FileController extends BaseController {
    @Autowired
    private FileService fileService;

    /**
     * 文件上传
     * @return
     */
    @RequestMapping(value = "/fileUpload")
    @ResponseBody
    public Object fileUpload(@RequestParam("file") MultipartFile file, HttpServletRequest request) {

        Map<String, Object> returnMap = new HashMap();
        PageData pd = this.getPageData();
        String filePath = request.getSession().getServletContext().getRealPath("imgupload/");
        String fileName = DateUtil.getTimeNoSign()+"_"+file.getOriginalFilename();
        String fileRealPath = filePath+fileName;
        pd.put("path",fileRealPath);
        if (!file.isEmpty()) {
            try {
                FileUtil.uploadFile(file.getBytes(), filePath);
            } catch (Exception e) {
                returnMap.put("result", false);
                returnMap.put("msg", "上传失败," + e.getMessage());
            }
            System.out.print("上传成功");

            fileService.addImg(pd);
            String fileid=String.valueOf(pd.get("id"));
            returnMap.put("result", true);
            returnMap.put("data",pd.get("id"));

        } else {
            returnMap.put("result", false);
            returnMap.put("msg", "上传失败，因为文件是空的.");
        }
        return returnMap;

    }



}

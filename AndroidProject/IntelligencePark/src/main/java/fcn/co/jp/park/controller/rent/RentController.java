package fcn.co.jp.park.controller.rent;

import fcn.co.jp.park.controller.BaseController;
import fcn.co.jp.park.service.rent.RentService;
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
@RequestMapping(value = "/rent")
public class RentController extends BaseController {

    @Autowired
    private FileUtil fileUtil;

    @Autowired
    private RentService rentService;

    /**
     * 获取房屋添加页面初始化数据
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getRentAddInitData", produces = {"application/json;charset=UTF-8"})
    public Object getRentAddInitData(){
        Map<String, Object> resultMap = rentService.getRentAddInitData();
        return resultMap;
    }

    /**
     * 添加房屋信息(有文件)
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/rentInfoAdd", produces = {"application/json;charset=UTF-8"})
    public Object rentInfoAdd(HttpServletRequest request){
        MultiValueMap<String, MultipartFile> files= ((StandardMultipartHttpServletRequest) request).getMultiFileMap();
        PageData pd = this.getPageData();
        String fileidSrt=pd.get("house_img").toString();
        for (String key : files.keySet()){
            String fileid =fileUtil.fileUp(Const.REPAIR_PICTURE,files.get(key).get(0));
            if(!"".equals(fileidSrt)){
                fileidSrt = fileidSrt.replaceAll(key,fileid);
            }
        }
        pd.put("house_img",fileidSrt);

        Map<String, Object> resultMap;

        if("none".equals(pd.get("houseId"))){
            resultMap = rentService.rentInfoAdd(pd);
        }else{
            resultMap = rentService.rentInfoEdit(pd);
        }
        return resultMap;
    }

    /**
     * 添加房屋信息(没有文件)
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/rentAddNoImage", produces = {"application/json;charset=UTF-8"})
    public Object rentAddNoImage(HttpServletRequest request){
        PageData pd = this.getPageData();
        if("none".equals(pd.get("houseId"))){
            Map<String, Object> resultMap = rentService.rentInfoAdd(pd);
            return resultMap;
        }else{
            Map<String, Object> resultMap = rentService.rentInfoEdit(pd);
            return resultMap;
        }
    }

    /**
     * 房屋列表获取
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/rentHouseList", produces = {"application/json;charset=UTF-8"})
    public Object rentHouseList(){
        PageData pd = this.getPageData();
        Map<String, Object> resultMap = rentService.rentHouseList(pd);
        return resultMap;
    }

    /**
     * 房屋详情获取
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/rentHouseDetail", produces = {"application/json;charset=UTF-8"})
    public Object rentHouseDetail(){
        PageData pd = this.getPageData();
        Map<String, Object> resultMap = rentService.rentHouseDetail(pd);
        return resultMap;
    }

    /**
     * 房屋编辑初始化数据获取
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/rentHouseEditInit", produces = {"application/json;charset=UTF-8"})
    public Object rentHouseEditInit(){
        PageData pd = this.getPageData();
        Map<String, Object> resultMap = rentService.rentHouseEditInit(pd);
        return resultMap;
    }

    /**
     * 获取已发布房屋列表
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/rentReleasedHouseList", produces = {"application/json;charset=UTF-8"})
    public Object rentReleasedHouseList(){
        PageData pd = this.getPageData();
        Map<String, Object> resultMap = rentService.rentReleasedHouse(pd);
        return resultMap;
    }
}
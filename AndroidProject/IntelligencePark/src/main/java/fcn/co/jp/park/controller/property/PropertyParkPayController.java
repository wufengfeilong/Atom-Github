package fcn.co.jp.park.controller.property;

import fcn.co.jp.park.controller.BaseController;
import fcn.co.jp.park.service.property.PropertyParkPayService;
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
@RequestMapping(value = "/parkPay")
public class PropertyParkPayController extends BaseController {

    @Autowired
    private FileUtil fileUtil;

    @Autowired
    private PropertyParkPayService propertyParkPayService;


    /**
     * 获取月租车辆申请画面初始化数据
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getParkPayInitData", produces = {"application/json;charset=UTF-8"})
    public Object parkPayApply(){
        Map<String, Object> resultMap = propertyParkPayService.getInitData();
        return resultMap;
    }

    /**
     * 月租车辆申请提交
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/parkPayApply", produces = {"application/json;charset=UTF-8"})
    public Object parkPayApply(HttpServletRequest request){
        MultiValueMap<String, MultipartFile> files= ((StandardMultipartHttpServletRequest) request).getMultiFileMap();
        PageData pd = this.getPageData();
        String carNumber = pd.get("carNumber").toString();

        //车辆审核中或在使用中check
        int applyFlg = propertyParkPayService.checkCarNumber(carNumber);
        if(applyFlg>0){
            Map<String,Object> resultMap = new HashMap<String,Object>();
            resultMap.put("result", false);
            resultMap.put("msg", "该车辆正在审批中");
            return resultMap;
        }else{
            //上传的文件处理
            String fileid=null;
            for (String key : files.keySet()){
                if("onJob".equals(key)){
                    //在职证明处理
                    fileid =fileUtil.fileUp(Const.MONTH_CAR_APPLY_PICTURE, files.get(key).get(0));
                    pd.put("onJobProImage",fileid);
                }else if("driver".equals(key)){
                    //驾驶证处理
                    fileid =fileUtil.fileUp(Const.MONTH_CAR_APPLY_PICTURE, (MultipartFile)files.get(key).get(0));
                    pd.put("driverCardImage",fileid);
                }else if("drivering".equals(key)){
                    //行驶证处理
                    fileid =fileUtil.fileUp(Const.MONTH_CAR_APPLY_PICTURE, (MultipartFile)files.get(key).get(0));
                    pd.put("driveringCardImage",fileid);
                }
            }
            Map<String,Object> resultMap = propertyParkPayService.addparkPayApply(pd);
            return resultMap;
        }
    }

    /**
     * 获取月租车辆驳回画面初始化数据
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getParkPayRejectInitData", produces = {"application/json;charset=UTF-8"})
    public Object getParkPayRejectInitData(){
        PageData pd = this.getPageData();
        String fileName="";
        Map<String, Object> resultMap = propertyParkPayService.getRejectInitData(pd);
        return resultMap;
    }
}
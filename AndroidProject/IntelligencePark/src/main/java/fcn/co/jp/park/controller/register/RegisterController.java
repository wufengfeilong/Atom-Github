package fcn.co.jp.park.controller.register;

import fcn.co.jp.park.controller.BaseController;
import fcn.co.jp.park.service.UserService;
import fcn.co.jp.park.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/userReg")
public class RegisterController extends BaseController {

    @Autowired
    private UserService userService;

    /**
     * 注册手机验证码发送
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/sendMsgByTel")
    public Object sendMsgByTel() {
        PageData pd = this.getPageData();
        Map<String, Object> returnMap = new HashMap<String, Object>();
        try {
            if (userService.findByPhone(pd) > 0)
            {
                returnMap.put("result", false);
                returnMap.put("msg", "手机已经注册");
            }else{
                String random = AppUtil.createRandom(true,6);
                Map<String, Object> data = new HashMap<String, Object>();
//                SendSmsMsg.smsCodeSend(pd.getString("phone"), MessageFormat.format(SendSmsMsg.verifyCodeMsg, random));
                pd.put("valType", pd.getString("phone"));
                pd.put("random", random);
                userService.saveCode(pd);
                data.put("random", random);
                returnMap.put("result", true);
                returnMap.put("msg", "发送成功");
                returnMap.put("data", data);
            }

        } catch (Exception e) {
            returnMap.put("result", false);
            returnMap.put("msg", "系统异常,请稍后再试");
        }
        return returnMap;
    }

    /**
     * 用户注册
     * @return
     */
    @RequestMapping(value = "/userReg")
    @ResponseBody
    public Map<String, Object> userRegister(){
        Map<String, Object> returnMap = new HashMap<String, Object>();
        PageData pd = this.getPageData();
        try {
            //	导入信息
            pd.put("user_id", this.get32UUID());
            pd.put("password", MD5.md5(pd.getString("pwd")));

            // 查询验证码
            pd.put("valType", pd.getString("phone"));
            PageData returnPd = userService.verifyCode(pd);
            if(returnPd != null){
                long diff = DateUtil.dateDiff(String.valueOf(returnPd.get("valTime")),DateUtil.getTime(),"yyyy-MM-dd HH:mm:ss","m");
                if(diff > 5){
                    returnMap.put("result", false);
                    returnMap.put("msg", "验证码失效，请重新发送");
                } else {
                    returnMap = userService.addUser(pd);
                }
            }else{
                returnMap.put("result", false);
                returnMap.put("msg", "验证码错误");
            }

        }catch (Exception e) {
            returnMap.put("msg", "系统异常,请稍后再试");
            returnMap.put("result", false);
            return returnMap;
        }
        return returnMap;
    }

    /**
     *  去注册条款页面
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/goRegistration")
    @ResponseBody
    public Map<String, Object> goRegistration() {
        Map<String, Object> rerturnmap = new HashMap<String, Object>();
        PageData pd = this.getPageData();
        try {
            PageData data = userService.findTerms(pd);
            rerturnmap.put("data", data);
            rerturnmap.put("msg", "注册条款");
            rerturnmap.put("result", true);
        }catch (Exception e) {
            rerturnmap.put("msg", "系统异常,请稍后再试");
            rerturnmap.put("result", false);
            return rerturnmap;
        }
        return rerturnmap;
    }
}

package fcn.co.jp.park.controller.manager;

import com.sun.mail.util.MailSSLSocketFactory;
import fcn.co.jp.park.controller.BaseController;
import fcn.co.jp.park.service.manager.RentFeeService;
import fcn.co.jp.park.util.PageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping(value = "/rentFee")
public class RentFeeController extends BaseController {

    @Autowired
    private RentFeeService rentFeeService;

    @ResponseBody
    @RequestMapping(value = "/edit", produces = {"application/json;charset=UTF-8"})
    public Object rentFeeEdit(){
        PageData pd = this.getPageData();
        Map<String, Object> returnMap = null;
        try {
            returnMap = rentFeeService.rentFeeEdit(pd);
        } catch (Exception e) {
            returnMap.put("result", false);
            returnMap.put("msg", "系统异常,请稍后再试");
        }
        return returnMap;
    }

    /**
     * 获取租赁费列表的List内容
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/rentList", produces = {"application/json;charset=UTF-8"})
    public Object rentFeeList() {
        PageData pd = this.getPageData();
        Map<String, Object> data = new HashMap<>();
        Map<String, Object> returnMap = new HashMap<>();
        try {
            int pageNum = Integer.valueOf(pd.getString("pageNum"));
            pd.put("numS", (pageNum - 1)*10);
            pd.put("numE",10);
            List<PageData> rentFeeList = rentFeeService.getRentFeeList(pd);
            int count = rentFeeService.getRentFeeListCount();
            int totalPage = getTotalPage(count);

            // 设定isNext
            data.put("isNext", true);
            if(pageNum >= totalPage){
                data.put("isNext", false);
            }
            // 设定租赁费列表的查询结果
            data.put("listRent", rentFeeList);
            // 设定总页数
            data.put("totalPage", String.valueOf(totalPage));

            // 设定返回值
            returnMap.put("result", true);
            returnMap.put("msg", "查询成功");
            returnMap.put("data", data);
        } catch (Exception e) {
            returnMap.put("result", false);
            returnMap.put("msg", "系统异常,请稍后再试");
            returnMap.put("data", data);
        }
        return returnMap;
    }

    @ResponseBody
    @RequestMapping(value = "/sendMail", produces = {"application/json;charset=UTF-8"})
    public  Object send() throws Exception {
        Map<String, Object> returnMap = new HashMap<>();
        PageData pd = this.getPageData();
        try{
            String email = pd.getString("email");
            String startDate = pd.getString("startDate");
            String endDate = pd.getString("endDate");
            String fee = pd.getString("fee");

            // 配置信息
            Properties pro = new Properties();
            pro.put("mail.smtp.localhost", "mail.digu.com");
            pro.put("mail.smtp.host", "smtp.qiye.163.com");
            pro.put("mail.smtp.auth", "true");
            // SSL加密
            MailSSLSocketFactory sf = null;
            sf = new MailSSLSocketFactory();
            // 设置信任所有的主机
            sf.setTrustAllHosts(true);
            pro.put("mail.smtp.ssl.enable", "true");
            pro.put("mail.smtp.ssl.socketFactory", sf);
            String user = "service@jinansourcing.com";
            String passwd = "admdUAH28x2ff6s2";
            MailAuthenticator authenticator = new MailAuthenticator(user, passwd);
            Session session = Session.getInstance(pro, authenticator);
            // 根据Session 构建邮件信息
            Message message = new MimeMessage(session);
            // 创建邮件发送者地址
            Address from = new InternetAddress("service@jinansourcing.com");
            // 设置邮件消息的发送者
            message.setFrom(from);
            // 验证收件人邮箱地址
            List<String> toAddressList = new ArrayList<>();
            toAddressList.add(email);
            StringBuffer buffer = new StringBuffer();
            if (!toAddressList.isEmpty()) {
                String regEx = "(([0-9a-zA-Z]+)|([0-9a-zA-Z]+[_.0-9a-zA-Z-]*[0-9a-zA-Z]+))@([a-zA-Z0-9-]+[.])+([a-zA-Z]{2}|net|NET|com|COM|gov|GOV|mil|MIL|org|ORG|edu|EDU|int|INT)";
                Pattern p = Pattern.compile(regEx);
                for (int i = 0; i < toAddressList.size(); i++) {
                    Matcher match = p.matcher(toAddressList.get(i));
                    if (match.matches()) {
                        buffer.append(toAddressList.get(i));
                        if (i < toAddressList.size() - 1) {
                            buffer.append(",");
                        }
                    }
                }
            }
            String toAddress = buffer.toString();
            if (!toAddress.isEmpty()) {
                // 创建邮件的接收者地址
                Address[] to = InternetAddress.parse(toAddress);
                // 设置邮件接收人地址
                message.setRecipients(Message.RecipientType.TO, to);
                // 邮件主题
                message.setSubject("租赁费收取");
                // 邮件容器
                MimeMultipart mimeMultiPart = new MimeMultipart();
                // 设置HTML
                BodyPart bodyPart = new MimeBodyPart();
                // 邮件内容
                bodyPart.setContent(getMailContent(startDate, endDate, fee), "text/html;charset=utf-8");
                mimeMultiPart.addBodyPart(bodyPart);
                message.setContent(mimeMultiPart);
                message.setSentDate(new Date());
                // 保存邮件
                message.saveChanges();
                // 发送邮件
                Transport.send(message);
                // 设定返回值
                returnMap.put("result", true);
                returnMap.put("msg", "催缴费成功");
            }
        } catch (Exception e){
            returnMap.put("result", false);
            returnMap.put("msg", "系统异常,请稍后再试");
            returnMap.put("data", "催缴费失败");
        }
        return returnMap;
    }

    /***
     * 取得邮件发送内容
     * @return
     */
    private String getMailContent(String startDate, String endDate, String fee){
        String html ="<style> *{margin: 0; padding: 0;} body{font-family: 'Microsoft YaHei', '微软雅黑'; font-size: 13px;} .mail{width: 750px; border-radius: 10px; margin: 0 auto 0;} .empty{height: 20px;} .header{height: 70px; margin: 0 30px; border-top-left-radius: 10px; border-top-right-radius: 10px;} .header img{margin-left: 30px;} .content{background-color: #ffffff; margin: 0 30px; border-bottom-left-radius: 10px; border-bottom-right-radius: 10px; font-weight: 500;} .content h1{font-size: 18px; color: #333333; line-height: 66px; text-align: center; font-weight: 500;} .content hr{border: 1px solid #999999; margin: 0 30px 20px;} .content div{margin: 0 30px 20px;} .content .first span{color: #333333;} .content .second span:first-child, .content .third span{color: #333333; font-weight: bold;} .content .third span.verify-code{font-size: 18px; color: #00a0e9; font-weight: bold;} .content .tip{margin-top: 60px;} .tip span{color: #666666;} .content .company{margin-top: 40px;}footer{margin: 0 0 0 30px; text-align: center;} .footer span{color: #999999; margin-right: 25px;} .footer div{text-align: center; margin-bottom: 24px; padding: 20px 0;} .footer span.link{color: #00a0e9; margin-right: 0;} .footer span.line{color: #999999; margin: 0 20px;} a{text-decoration: none; color: #00a0e9; font-size: 13px;} </style>";
        html +="	<div/>";
        html +="	<div>尊敬的业主您好：</span></div>";
        html +="	<div><span>您于</span><span>" +
                startDate + "</span>到<span>" +
                endDate + "</span>共计<span>" +
                fee + "</span>元租赁费请及时缴纳。</span></div>";
        html +="	<div>本邮件由系统自动发送，</span><span class=red>请勿直接回复。</span> </div></div></div>";
        html +="	<div></div></includetail></div>";
        return  html;
    }

    /***
     * 取得总页数
     * @return
     */
    private int getTotalPage(int totalResult){
        int totalPage = 0;
        if(totalResult%10==0)
            totalPage = totalResult/10;
        else
            totalPage = totalResult/10+1;
        return totalPage;
    }

    class MailAuthenticator extends Authenticator {
        //用户名
        private String username;
        //密码
        private String password;

        /**
         * 创建一个新的实例 MailAuthenticator.
         * @param username
         * @param password
         */
        public MailAuthenticator(String username, String password) {
            this.username = username;
            this.password = password;
        }

        public String getPassword() {
            return password;
        }

        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(username, password);
        }

        public String getUsername() {
            return username;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public void setUsername(String username) {
            this.username = username;
        }

    }
}

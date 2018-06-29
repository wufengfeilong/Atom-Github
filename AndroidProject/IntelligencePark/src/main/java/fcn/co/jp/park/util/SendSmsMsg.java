package fcn.co.jp.park.util;


import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpClientParams;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URLDecoder;
public class SendSmsMsg {

    // 验证码信息发送模板
    public static String verifyCodeMsg = "验证码是: {0}，为了保护您或者您企业的帐号安全，验证短信请勿转发给其他人。";

    /**
     * 发送手机验证码
     * @param phone
     *            手机号码，多个号码使用","分割
     * @param msg
     *            短信内容
     * @return 返回值定义参见HTTP协议文档
     * @throws Exception
     */
    public static String smsCodeSend(String phone, String msg) throws Exception {
        HttpClient client = new HttpClient(new HttpClientParams(),
                new SimpleHttpConnectionManager(true));
        GetMethod method = new GetMethod();
        InputStream in = null;
        try {
            // 参数收集
            // 应用地址，类似于http://ip:port/msg/
            URI base = new URI(Const.SMURL, false);
            method.setURI(new URI(base, "send", false));
            method.setQueryString(new NameValuePair[] {
                    // 账号
                    new NameValuePair("un", Const.SMACCOUNT),
                    // 密码
                    new NameValuePair("pw", Const.SMPSWD),
                    // 手机号码
                    new NameValuePair("phone", phone),
                    // 是否需要状态报告，需要1，不需要0
                    new NameValuePair("rd", Const.rd),
                    // 短信内容
                    new NameValuePair("msg", msg),
                    // 附加信息
                    new NameValuePair("ex", null), });
            // GET方式请求接口 返回状态码
            int result = client.executeMethod(method);
            // 200 请求成功 进行信息解析
            if (result == HttpStatus.SC_OK) {
                in = method.getResponseBodyAsStream();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int len = 0;
                while ((len = in.read(buffer)) != -1) {
                    baos.write(buffer, 0, len);
                }
                return URLDecoder.decode(baos.toString(), "UTF-8");
            } else {
                // 请求失败处理
                throw new Exception("HTTP ERROR Status: "
                        + method.getStatusCode() + ":" + method.getStatusText());
            }
        } finally {
            method.releaseConnection();
            if (in != null) {
                in.close();
            }
        }
    }

    /**
     * 创建指定数量的随机字符串
     *
     * @param numberFlag
     *            是否是数字(true:是 false:否)
     * @param length 验证码长度
     * @return 验证码
     */
    public static String createRandom(boolean numberFlag, int length) {
        String retStr = "";
        // 三目运算符：根据numberFlag指定生成规则
        String strTable = numberFlag ? "1234567890"
                : "1234567890abcdefghijkmnpqrstuvwxyz";
        // 获取规则的长度
        int len = strTable.length();
        // 是否继续循环的标志
        boolean bDone = true;
        do {
            // 根据所需验证码位数与规则生成验证码字符串
            retStr = "";
            int count = 0;
            // 循环取得随机数
            for (int i = 0; i < length; i++) {
                double dblR = Math.random() * len;
                int intR = (int) Math.floor(dblR);
                char c = strTable.charAt(intR);
                if (('0' <= c) && (c <= '9')) {
                    count++;
                }
                retStr += strTable.charAt(intR);
            }
            if (count >= 2) {
                bDone = false;
            }
        } while (bDone);
        return retStr;
    }
}

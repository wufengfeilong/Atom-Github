package com.fujisoft.pone;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.Character.UnicodeBlock;
import java.net.URLEncoder;

import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * create by 張風武 2017/08/16 9:03:35
 */
@RestController
public class PhoneComingController {
    // war包用
//    @GetMapping("/phoneComing")
//    public String call(@RequestParam(value = "telNm", required = false, defaultValue = "陌生人") String telNm,
//            @RequestParam(value = "telNo", required = false, defaultValue = "00000") String telNo) {
//        
//        String vbsPath = "/static/msg.vbs";
//        ClassPathResource resource = new ClassPathResource(vbsPath);
//        String absPath = null;
//        try {
//            absPath = resource.getURL().getPath().replace("20%", "");
//        } catch (Exception e) {
//            System.out.println(e.toString());
//        }
//        absPath = absPath.substring(1, absPath.length());
//        String cmdStr = "cmd /c start " + absPath;
//        // 这种方式获取的路径不能访问
////        String absPath = getClass().getResource("/").getPath();
//        // 获取的路径中首个字符是/
//        // C:\tools\phone-listener-tomcat\webapps\
////        absPath = absPath.substring(1, absPath.length());
////        String cmdStr = "cmd /c start " + absPath + "/static/msg.vbs";
//
//        System.out.println(cmdStr);
//
//        String content = "msgbox\"" + telNm + "(" + telNo + ") is calling you!!!\",64,\"提醒\"";
//
//        File vbsFile = new File(absPath);
//        if (vbsFile.exists()) {
//            vbsFile.delete();
//        }
//        try {
//            vbsFile.createNewFile();
//            // UTF-8 Shift-JIS gbk 最好的显示效果： Shift-JIS gbk gbk
//            String str = new String(content.getBytes("Shift-JIS"), "gbk");
//            OutputStream bos = new FileOutputStream(absPath);
//            bos.write(str.getBytes("gbk"));
//            bos.close();
//            // 打成war包的路径
//            Runtime.getRuntime().exec(cmdStr);
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//
//        System.out.println(telNm + "(" + telNo + ")来电");
//        return "姓名：" + telNm + "  电话：" + telNo;
//    }

    // 开发环境用
     @GetMapping("phone/phoneComing")
     public String call(@RequestParam(value = "telNm",required = false,defaultValue = "陌生人") String telNm,
                         @RequestParam(value = "telNo",required = false,defaultValue = "00000") String telNo) {
    
     String content = "msgbox\""+telNm+"("+telNo+") is calling you!!!\",64,\"提醒\"";
     String path = "C:/Users/860115039/Desktop/msg.vbs";
     File vbsFile = new File(path);
     if (vbsFile.exists()) {
     vbsFile.delete();
     }
     System.out.println(content);
    
     try {
     vbsFile.createNewFile();
     // 方式一
    // PrintWriter pw=new PrintWriter(new FileWriter(path));
    // pw.print(content);
    // pw.flush();
    // pw.close();
     // 方式二
    // FileWriter fw = new FileWriter(path);
    // BufferedWriter bw = new BufferedWriter(fw);
    // bw.write(content);
    // bw.close();
     // 方式三
     // UTF-8 Shift-JIS gbk 最好的显示效果： Shift-JIS gbk gbk
     String str = new String(content.getBytes("Shift-JIS"),"gbk");
     OutputStream bos = new FileOutputStream(path);
     bos.write(str.getBytes("gbk"));
    // bos.write(content.getBytes("gbk"));
     bos.close();
     Runtime.getRuntime().exec("cmd /c start C:/Users/860115039/Desktop/msg.vbs");
     } catch (IOException e) {
     // TODO Auto-generated catch block
     e.printStackTrace();
     }
    
     return "姓名："+telNm+" 电话："+telNo;
     }
}

package com.fujisoft.crypto.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fujisoft.crypto.bean.Result;
import com.fujisoft.crypto.bean.User;
import com.fujisoft.crypto.util.MD5Util;

/**
 * create by 張風武 2018/02/11 13:26:55
 */
@RestController
public class CryptoController {
    
    @GetMapping("/testStr")
    public String Test(){
        System.out.println("string");
//        Result result = new Result();
//        result.setCode(0);
//        result.setMsg("success001");
//        return result;
        String str = MD5Util.encrypt("123");
        String sss = "\"crypto test\"";
        System.out.println(sss);
        System.out.println(str);
        return sss;
    }
    
    @GetMapping("/testObj")
    public Result Test123(){
        System.out.println("object");
        Result result = new Result();
        result.setCode(0);
        result.setMsg("success001");
        return result;
    }
    
    @PostMapping("/decrypt")
    public String deCrypt() {
        System.out.println("deCrypt");
        return "解密";
    }

    @PostMapping("/encrypt")
    public String enCrypt() {

        return "加密";
    }
    
    @PostMapping("/login")
    public Result login(@RequestBody User user){
        String name = user.getName();
        String pwd = user.getPwd();
        System.out.println(name);
        System.out.println(pwd);
        Result result = new Result();
        if ("abc".equals(name)&&"123".equals(pwd)){
            System.out.println("success");
            result.setCode(0);
            result.setMsg("success");
        } else{
            System.out.println("fail");
            result.setCode(1);
            result.setMsg("fail"); 
        }
        return result;
    }
}

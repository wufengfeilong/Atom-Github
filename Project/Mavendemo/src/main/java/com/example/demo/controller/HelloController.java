package com.example.demo.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.property.WebProperty;

@RestController
//@RequestMapping("/hi")
public class HelloController {
    
    
    
//    //application.yml中定义的常量
//    @Value("${webName}")
//    private String webName;
    
    @Autowired
    private WebProperty webProperty;
    
    // http://172.29.140.35:8080/hello/张三
//    @GetMapping(value = {"hello/{name}","hi"})
    // http://172.29.140.35:8080/hello?name=张三
    @GetMapping({"hello","hi"})
    public String hello(
//            @PathVariable("name") String nm
            @RequestParam(value = "name",required = false,defaultValue = "no name") String nm
            ){
//        return "hello spring boot!";
        //json格式
//        return "{'result': 'true','msg': 'Sunny'}";
        //application.yml中定义的常量
//        return webName;
        return webProperty.getWebName()+" "+webProperty.getStructureWay()+" 输入的姓名是："+nm;
    }
    
    @GetMapping("/zeroException")
    public int zeroException(){
        return 1/0;
    }
}

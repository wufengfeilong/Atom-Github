package com.fujisoft.upload.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/** 
* create by 張風武 
* 2017/09/05 16:52:11 
*/
@RestController
public class FileController {
    
    @PostMapping("/")
    public void getFiles(@RequestParam("directory") String directory,@RequestParam("file") MultipartFile[] files){
        
        String fileName = null;
        String fileBasePath = "D:/"+directory+"/";
        System.out.println(fileBasePath);
        if (files != null && files.length >0) {
            for(int i =0 ;i< files.length; i++){
                try {
                    // ファイル名を取得
                    fileName = files[i].getOriginalFilename();
                    String filePath = fileBasePath + fileName;
                    File file = new File(filePath);
                    // ルートが存在しない場合、新規する
                    if (!file.getParentFile().exists()) {  
                        file.getParentFile().mkdirs();
                    }
                    // ファイルが存在しない場合、新規する
                    if (!file.exists()) {
                        file.createNewFile();
                    }
                    byte[] bytes = files[i].getBytes();
                    BufferedOutputStream buffStream = 
                            new BufferedOutputStream(new FileOutputStream(file));
                    buffStream.write(bytes);
                    buffStream.close();
                    System.out.println("成功受信 ：" + fileName);
                } catch (Exception e) {
                    System.out.println("受信失敗" + fileName + ": " + e.getMessage() +"<br/>");
                }
            }
        } else {
            System.out.println("空のファイルです！");
        }
    }
    
    @GetMapping("/lists")
    public String getFileList(@RequestParam(value = "directory") String directory) {
        String fileDir = "D:/" + directory;
        File file = new File(fileDir);
        if (!file.exists()) {
            System.out.println("このディレクトリは存在しない！");
            return null;
        }
        //[{name:'zhangsan',age:12},{name:'lisi',age:12}]
        String fileInfo = "[";
        File fa[] = file.listFiles();
        for (int i = 0; i < fa.length; i++) {
            File fs = fa[i];
            String name = fs.getName();
            long length = fs.length();
            fileInfo += "{name:"+name+",length:"+length+"},";
        }
        fileInfo = fileInfo.substring(0, fileInfo.length()-1);
        fileInfo += "]";
        System.out.println(fileInfo);
        return fileInfo;
    }
    
    
    
}

package com.fujisoft.gps.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/** 
* create by 張風武 
* 2017/08/10 10:12:44 
*/
@RestController
public class GpsController {
    
    @PostMapping("/sendGps")
    public void sendGps(@RequestParam(value = "longitude", required = true) String longitude,
                        @RequestParam(value = "latitude", required = true) String latitude,
                        @RequestParam(value = "altitude", required = true) String altitude,  
                        @RequestParam(value = "bearing", required = true) String bearing,
                        @RequestParam(value = "speed", required = true) String speed,
                        @RequestParam(value = "stampTime", required = true) String stampTime){
        String gpsInfo = "GPS情報 : {  "
                            + "経度 ： "+ longitude
                            + " 緯度 ： "+ latitude
                            + " 高度 ： "+ altitude
                            +" 方角 ： "+ bearing
                            +" 速度 ： "+ speed
                            +" 現在時刻 ： "+ stampToDate(stampTime)
                            +" 系统時刻 ： "+ new Date()
                            +" }";
        System.out.println(gpsInfo);
        System.out.println("-----------------------------------------------------------------------------------");
    }
    
    /**
     * タイムスタンプを時間に変換
     */
    private String stampToDate(String s) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        long slong = Long.parseLong(s);
        Date date = new Date(slong);
        return simpleDateFormat.format(date);
    }
}

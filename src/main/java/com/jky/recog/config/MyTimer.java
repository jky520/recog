package com.jky.recog.config;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 定时类
 * Created by DT人 on 2017/8/18 15:41.
 */
@Component
public class MyTimer {

    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

    // 每3秒执行一次该方法
    @Scheduled(fixedRate = 3000)
    public void timerRate() {
        System.out.println(sdf.format(new Date()));
    }

    // 第一次延迟1秒执行，当执行完后3秒再执行
    @Scheduled(initialDelay = 1000, fixedDelay = 3000)
    public void timeInit() {
        System.out.println("init : " + sdf.format(new Date()));
    }

    // 每天23点27分50秒时执行
    @Scheduled(cron = "50 27 23 * * ?")
    public void timeCorn() {
        System.out.println("current time : " + sdf.format(new Date()));
    }
}

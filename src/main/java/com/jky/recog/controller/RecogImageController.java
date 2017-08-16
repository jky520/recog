package com.jky.recog.controller;

import com.jky.recog.util.CLibrary;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 图片识别控制器
 *
 * Created by DT人 on 2017/8/16 16:07.
 */
@RestController
@RequestMapping("/image")
public class RecogImageController {

    @GetMapping("/recogImage")
    public String recogImage() {
        String[] args = {};
        CLibrary.INSTANCE.printf("Hello World/n");
        for (int i = 0; i < args.length; i++) {
            CLibrary.INSTANCE.printf("Argument %d: %s%n", i, args[i]);
        }
        return "hello";
    }
}

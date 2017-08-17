package com.jky.recog.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 图片识别控制器
 *
 * Created by DT人 on 2017/8/16 16:07.
 */
@RestController
@RequestMapping("/image")
public class RecogImageController {

    @GetMapping("/recogImage")
    public String recogImage(MultipartFile[] files) {
        return "hello";
    }
}

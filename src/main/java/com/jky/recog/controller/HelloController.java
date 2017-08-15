package com.jky.recog.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * hello控制器
 *
 * Created by DT人 on 2017/8/15 15:45.
 */
@RestController
public class HelloController {

    @GetMapping("/index")
    public String index() {
        return "hello index";
    }
 }

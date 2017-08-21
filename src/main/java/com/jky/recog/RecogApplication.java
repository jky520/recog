package com.jky.recog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 在入口程序加上注解@EnableScheduling即可启动定时任务
 */
@SpringBootApplication
//@EnableScheduling
public class RecogApplication {

	public static void main(String[] args) {
		SpringApplication.run(RecogApplication.class, args);
	}
}

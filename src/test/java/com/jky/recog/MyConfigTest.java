package com.jky.recog;

import com.jky.recog.config.PropertiesConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 自定义配置类的测试
 * Created by DT人 on 2017/8/15 16:20.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MyConfigTest {

    /**
     * 自动注入
     */
    @Autowired
    private PropertiesConfig pc;

    @Test
    public void testConfig() {
        System.out.println("项目名称:" + pc.getName() +
                ", 项目版本: "+ pc.getVersion()+", 项目作者: "+pc.getAuthor());
    }
}

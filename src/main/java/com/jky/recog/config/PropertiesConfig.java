package com.jky.recog.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * properties配置类
 * Created by DT人 on 2017/8/15 15:59.
 */
@Component // 注解组件对对象的管理
@ConfigurationProperties(prefix = "web") // spring boot 1.5.1以后的版本取消了locations属性，然后使用@PropertySource来指定自定义的资源目录
@PropertySource("classpath:config/my-web.properties")
public class PropertiesConfig {
    // alt + insert可以快速生成getter和setter函数

    private String name;

    private String version;

    private String author;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "PropertiesConfig{" +
                "name='" + name + '\'' +
                ", version='" + version + '\'' +
                ", author='" + author + '\'' +
                '}';
    }
}

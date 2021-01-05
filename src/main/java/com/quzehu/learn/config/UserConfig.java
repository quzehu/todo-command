package com.quzehu.learn.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 用户配置文件
 *
 * @ProjectName 项目名称
 * @ClassName com.quzehu.learn.config.UserConfig
 * @Author Qu.ZeHu
 * @Date 2021/1/5 21:23
 * @Version 1.0
 */
@Configuration
@ConfigurationProperties(prefix = "user")
@Data
public class UserConfig {

    private String fileName;

    private Integer pwCheckNum;

}

package com.quzehu.learn.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 配置文件
 *
 * @ProjectName 项目名称
 * @ClassName com.quzehu.learn.config.TodoConfig
 * @Author Qu.ZeHu
 * @Date 2021/1/5 19:14
 * @Version 1.0
 */

@Configuration
@ConfigurationProperties(prefix = "todo")
@Data
public class TodoConfig {

    private String persistence;

    private String basePath;

    private String fileName;

    private String initFile;

    private String exportPath;

    private String exportType;

}

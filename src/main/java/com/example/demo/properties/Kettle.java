package com.example.demo.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author YM
 * @date 2019/11/1 16:09
 */
@Data
@ConfigurationProperties("kettle")
public class Kettle {

    private String resourceName;
    private String type;
    private String access;
    private String host;
    private String dbName;
    private String port;
    private String dbUser;
    private String dbPassword;
    private String resourceUsername;
    private String resourcePassword;
}

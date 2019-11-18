package com.example.demo;

import com.example.demo.properties.Kettle;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(basePackages = "com.example.demo.dao")
@EnableConfigurationProperties(Kettle.class)
@EnableCaching
public class CowApplication {

    public static void main(String[] args) {
        SpringApplication.run(CowApplication.class, args);
    }

}

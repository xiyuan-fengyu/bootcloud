package com.xiyuan.bootcloud;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
@MapperScan("com.xiyuan.*")
public class BootcloudApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootcloudApplication.class, args);
    }

}

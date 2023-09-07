package com.shiro.soj;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@MapperScan("com.shiro.soj.mapper")
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
public class SojBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(SojBackendApplication.class, args);
    }

}

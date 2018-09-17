package com.iteason.springboot_mybatis2;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan("com.iteason.springboot_mybatis2.mapper")
@EnableTransactionManagement
public class SpringbootMybatis2Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootMybatis2Application.class, args);
    }
}

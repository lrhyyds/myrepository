package com.nb.yyds220118;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.nb.yyds220118.dao")
public class Yyds220118Application {

    public static void main(String[] args) {
        SpringApplication.run(Yyds220118Application.class, args);
    }

}

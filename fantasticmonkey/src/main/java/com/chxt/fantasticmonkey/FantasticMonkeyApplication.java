package com.chxt.fantasticmonkey;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@MapperScan("com.chxt.fantasticmonkey.dao")
public class FantasticMonkeyApplication {

    public static void main(String[] args) {
        SpringApplication.run(FantasticMonkeyApplication.class, args);
    }

    @RequestMapping("/hello")
    public String hello(){
        return "hello world!";
    }
}

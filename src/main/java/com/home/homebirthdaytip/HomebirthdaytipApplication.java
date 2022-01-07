package com.home.homebirthdaytip;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
@MapperScan("com.home.homebirthdaytip.mapper")
public class HomebirthdaytipApplication {

    public static void main(String[] args) {
        SpringApplication.run(HomebirthdaytipApplication.class, args);
    }

}

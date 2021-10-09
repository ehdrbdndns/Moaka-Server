package com.moaka;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@MapperScan(basePackages = "com.moaka")
public class SpringServerApplication {

    public static final String APPLICATION_LOCATIONS = "spring.config.location="
            + "classpath:application.yml,"
            + "classpath:jwt.yml,"
            + "classpath:aws.yml";

    public static void main(String[] args) {
        new SpringApplicationBuilder(SpringServerApplication.class).properties(APPLICATION_LOCATIONS).run(args);
    }
}

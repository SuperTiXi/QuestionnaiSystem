package com.neu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.neu.dao")
public class QuestionnaireSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuestionnaireSystemApplication.class, args);
    }

}

package com.education.admin.api;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/4/11 9:51
 */
@SpringBootApplication(scanBasePackages = "com.education")
@MapperScan("com.education.mapper")
public class EducationAdminApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(EducationAdminApiApplication.class);
    }
}

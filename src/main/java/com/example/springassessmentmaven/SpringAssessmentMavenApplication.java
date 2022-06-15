package com.example.springassessmentmaven;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages={  "com.example.springassessmentmaven"})
public class SpringAssessmentMavenApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringAssessmentMavenApplication.class, args);
    }

}

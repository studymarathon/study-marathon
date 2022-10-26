package com.github.studym.studymarathon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaAuditing // BaseEntity date 입력을 위해서 사용
@EnableScheduling
public class StudyMarathonApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudyMarathonApplication.class, args);
    }

}

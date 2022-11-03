package com.github.studym.studymarathon;


import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@EnableAsync
public class Schedule {

    @Scheduled(fixedDelay = 1000)
    public void scheduleFixedDelayTask() throws InterruptedException {
        log.info("스케쥴링 펀치");
        log.info("Fixed delay task - {}", System.currentTimeMillis() / 1000);
        Thread.sleep(1000000);
    }
}
